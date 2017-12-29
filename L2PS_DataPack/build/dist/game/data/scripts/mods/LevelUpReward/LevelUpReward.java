package mods.LevelUpReward;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;

import com.l2jserver.gameserver.engines.DocumentParser;
import com.l2jserver.gameserver.model.PlayerVariables;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.holders.ItemHolder;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.scripting.scriptengine.events.PlayerLevelChangeEvent;
import com.l2jserver.gameserver.scripting.scriptengine.impl.L2Script;

public final class LevelUpReward extends L2Script
{
	// Misc
	private static final String HTML_PATH = "data/scripts/mods/LevelUpReward/";
	protected static boolean rewardAll = false;
	protected static final Map<Integer, LevelData> REWARDS = new HashMap<>();
	
	private LevelUpReward(String name, String descr)
	{
		super(name, descr);
		addPlayerLevelNotify(null);
		new LevelUpRewardData();
	}
	
	@Override
	public void onPlayerLevelChange(PlayerLevelChangeEvent event)
	{
		final L2PcInstance player = event.getPlayer();
		if (player == null)
		{
			return;
		}
		final int newLevel = event.getNewLevel();
		for (int oldLevel = (rewardAll) ? 1 : (event.getOldLevel() + 1); oldLevel <= newLevel; oldLevel++)
		{
			if (!REWARDS.containsKey(oldLevel))
			{
				continue;
			}
			final PlayerVariables vars = player.getVariables();
			if (vars.getBool("LEVEL_UP_REWARD_" + oldLevel, false))
			{
				continue;
			}
			final LevelData rewards = REWARDS.get(oldLevel);
			for (ItemHolder item : rewards.getItems())
			{
				player.addItem("Quest", item, player, true);
			}
			vars.set("LEVEL_UP_REWARD_" + oldLevel, true);
			if (rewards.getMessage() != "")
			{
				player.sendMessage(rewards.getMessage());
			}
			if (rewards.getHtmlFile() != "")
			{
				final NpcHtmlMessage html = new NpcHtmlMessage(player.getObjectId());
				html.setFile(player.getHtmlPrefix(), HTML_PATH + rewards.getHtmlFile());
				player.sendPacket(html);
			}
		}
	}
	
	protected final class LevelUpRewardData extends DocumentParser
	{
		public LevelUpRewardData()
		{
			load();
		}
		
		@Override
		public void load()
		{
			parseDatapackFile("data/xml/LevelReward/LevelReward.xml");
		}
		
		@Override
		protected void parseDocument()
		{
			for (Node n = getCurrentDocument().getFirstChild(); n != null; n = n.getNextSibling())
			{
				if ("list".equals(n.getNodeName()))
				{
					for (Node d = n.getFirstChild(); d != null; d = d.getNextSibling())
					{
						if ("rewards".equals(d.getNodeName()))
						{
							LevelData data = new LevelData();
							for (Node e = d.getFirstChild(); e != null; e = e.getNextSibling())
							{
								if ("item".equals(e.getNodeName()))
								{
									data.addItem(parseInteger(e.getAttributes(), "id"), parseLong(e.getAttributes(), "count"));
								}
							}
							data.setMessage(parseString(d.getAttributes(), "message"));
							data.setHtmlFile(parseString(d.getAttributes(), "htmlFile"));
							REWARDS.put(parseInteger(d.getAttributes(), "level"), data);
						}
					}
					rewardAll = parseBoolean(n.getAttributes(), "rewardAll");
				}
			}
		}
	}
	
	protected final class LevelData
	{
		private String html;
		private final List<ItemHolder> items;
		private String message;
		
		public LevelData()
		{
			html = "";
			items = new ArrayList<>();
			message = "";
		}
		
		public void addItem(int itemId, long itemCount)
		{
			items.add(new ItemHolder(itemId, itemCount));
		}
		
		public void setHtmlFile(String htmlFile)
		{
			html = htmlFile;
		}
		
		public void setMessage(String message)
		{
			this.message = message;
		}
		
		public String getHtmlFile()
		{
			return html;
		}
		
		public List<ItemHolder> getItems()
		{
			return items;
		}
		
		public String getMessage()
		{
			return message;
		}
	}
	
	public static void main(String[] args)
	{
		new LevelUpReward(LevelUpReward.class.getSimpleName(), "mods");
	}
}