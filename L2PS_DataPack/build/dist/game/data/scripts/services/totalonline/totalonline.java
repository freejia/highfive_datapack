package services.totalonline;

import java.sql.Connection;
import java.sql.PreparedStatement;

import l2f.commons.dbutils.DbUtils;
import l2f.gameserver.Config;
import l2f.gameserver.ThreadPoolManager;
import l2f.gameserver.database.DatabaseFactory;
import l2f.gameserver.model.GameObjectsStorage;
import l2f.gameserver.model.Player;
import l2f.gameserver.scripts.Functions;
import l2f.gameserver.scripts.ScriptFile;
import l2f.gameserver.tables.FakePlayersTable;

/**
 * Online -> real + fake
 */
public class totalonline extends Functions implements ScriptFile
{
	@Override
	public void onLoad()
	{
		System.out.println("Loaded Service: Parse Online [" + (Config.ALLOW_ONLINE_PARSE ? "enabled]" : "disabled]"));
		if (Config.ALLOW_ONLINE_PARSE)
		{
			ThreadPoolManager.getInstance().scheduleAtFixedRate(new updateOnline(), Config.FIRST_UPDATE * 60000, Config.DELAY_UPDATE * 60000);
			
		}
	}
	
	public class updateOnline implements Runnable
	{
		@Override
		public void run()
		{
			int members = getOnlineMembers();
			int offMembers = getOfflineMembers();
			Connection con = null;
			PreparedStatement statement = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("update online set online =?, offline = ? where 'index' =0");
				statement.setInt(1, members);
				statement.setInt(2, offMembers);
				statement.execute();
				DbUtils.closeQuietly(statement);
			}
			
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				DbUtils.closeQuietly(con, statement);
			}
		}
	}
	
	protected int getOnlineMembers()
	{
		int i = 0;
		for (Player player : GameObjectsStorage.getAllPlayersForIterate())
		{
			i++;
		}
		i = i + FakePlayersTable.getFakePlayersCount();
		
		return i;
	}
	
	protected int getOfflineMembers()
	{
		int i = 0;
		for (Player player : GameObjectsStorage.getAllPlayersForIterate())
		{
			if (player.isInOfflineMode())
			{
				i++;
			}
		}
		
		return i;
	}
	
	@Override
	public void onReload()
	{
	}
	
	@Override
	public void onShutdown()
	{
	}
}