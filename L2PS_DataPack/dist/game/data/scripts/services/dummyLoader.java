package services;

import l2f.gameserver.Config;
import l2f.gameserver.dao.RefferalDao;
import l2f.gameserver.dao.SfDDenialDAO;
import l2f.gameserver.scripts.Functions;
import l2f.gameserver.scripts.ScriptFile;

public class dummyLoader extends Functions implements ScriptFile
{
	
	@Override
	public void onLoad()
	{
		if (Config.ALLOW_REFFERAL_SYSTEM)
		{
			RefferalDao.getInstance().loadRefferals();
			RefferalDao.getInstance().startSaveTask();
		}
		SfDDenialDAO.LoadAllIps();
		SfDDenialDAO.startSaveIP();
	}
	
	@Override
	public void onReload()
	{
	}
	
	@Override
	public void onShutdown()
	{
		if (Config.ALLOW_REFFERAL_SYSTEM)
		{
			RefferalDao.getInstance().SaveRef();
		}
	}
}