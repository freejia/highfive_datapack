/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package events.MasterOfEnchanting;

import java.util.Date;

import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.itemcontainer.Inventory;
import com.l2jserver.gameserver.model.itemcontainer.PcInventory;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;

/**
 * @author RobíkBobík
 */
public class MasterOfEnchanting extends Quest
{
	private static final int _master_yogi = 32599;
	private static final int _master_yogi_staff = 13539;
	private static final int _master_yogi_scroll = 13540;
	
	private static final int _staff_price = 1000000;
	private static final int _scroll_24_price = 5000000;
	private static final int _scroll_24_time = 6;
	
	private static final int _scroll_1_price = 500000;
	private static final int _scroll_10_price = 5000000;
	
	private static final int[] _hat_shadow_reward =
	{
		13074,
		13075,
		13076
	};
	private static final int[] _hat_event_reward =
	{
		13518,
		13519,
		13522
	};
	private static final int[] _crystal_reward =
	{
		13071,
		13072,
		13073
	};
	
	@SuppressWarnings("deprecation")
	private static final Date _eventStart = new Date(2011, 7, 1);
	
	private static final Location[] _spawns =
	{
		new Location(16198, 142820, -2707, 16000),
		new Location(17339, 145045, -3037, 25000),
		new Location(83031, 149399, -3470, 44000),
		new Location(82152, 148513, -3468, 0),
		new Location(81750, 146432, -3534, 32768),
		new Location(-81010, 149987, -3042, 0),
		new Location(-83134, 151048, -3127, 0),
		new Location(-13788, 122095, -2990, 16384),
		new Location(-14066, 123834, -3118, 40959),
		new Location(-84467, 244773, -3730, 57343),
		new Location(-84024, 242971, -3730, 4096),
		new Location(46800, 50965, -2997, 8192),
		new Location(45600, 48370, -3061, 18000),
		new Location(9911, 16244, -4576, 62999),
		new Location(11442, 17635, -4586, 46900),
		new Location(81990, 53804, -1497, 0),
		new Location(81083, 56118, -1562, 32768),
		new Location(147272, 25603, -2014, 16384),
		new Location(148559, 26730, -2206, 32768),
		new Location(117356, 76708, -2695, 49151),
		new Location(115887, 76382, -2714, 0),
		new Location(-117164, 46857, 367, 49151),
		new Location(-119420, 44931, 367, 24576),
		new Location(110965, 218987, -3544, 16384),
		new Location(108426, 221876, -3600, 49151),
		new Location(-45197, -112826, -241, 0),
		new Location(-45225, -114139, -241, 16384),
		new Location(115098, -178421, -891, 0),
		new Location(116199, -182694, -1506, 0),
		new Location(86886, -142859, -1341, 26000),
		new Location(85592, -142560, -1343, 0),
		new Location(147332, -55484, -2736, 49151),
		new Location(148175, -55845, -2782, 61439),
		new Location(43081, -48469, -797, 17000),
		new Location(43695, -47711, -798, 49999)
	};
	
	public MasterOfEnchanting(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(_master_yogi);
		addFirstTalkId(_master_yogi);
		addTalkId(_master_yogi);
		for (Location loc : _spawns)
		{
			addSpawn(_master_yogi, loc, false, 0);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(getName());
		if (event.equalsIgnoreCase("buy_staff"))
		{
			if (!st.hasQuestItems(_master_yogi_staff) && (st.getQuestItemsCount(PcInventory.ADENA_ID) > _staff_price))
			{
				st.takeItems(PcInventory.ADENA_ID, _staff_price);
				st.giveItems(_master_yogi_staff, 1);
				htmltext = "32599-staffbuyed.htm";
			}
			else
			{
				htmltext = "32599-staffcant.htm";
			}
		}
		else if (event.equalsIgnoreCase("buy_scroll_24"))
		{
			long _curr_time = System.currentTimeMillis();
			String value = loadGlobalQuestVar(player.getAccountName());
			long _reuse_time = value == "" ? 0 : Long.parseLong(value);
			if (player.getCreateDate().after(_eventStart))
			{
				return "32599-bidth.htm";
			}
			
			if (_curr_time > _reuse_time)
			{
				if (st.getQuestItemsCount(PcInventory.ADENA_ID) > _scroll_24_price)
				{
					st.takeItems(PcInventory.ADENA_ID, _scroll_24_price);
					st.giveItems(_master_yogi_scroll, 24);
					saveGlobalQuestVar(player.getAccountName(), Long.toString(System.currentTimeMillis() + (_scroll_24_time * 3600000)));
					htmltext = "32599-scroll24.htm";
				}
				else
				{
					htmltext = "32599-s24-no.htm";
				}
			}
			else
			{
				long _remaining_time = (_reuse_time - _curr_time) / 1000;
				int hours = (int) _remaining_time / 3600;
				int minutes = ((int) _remaining_time % 3600) / 60;
				if (hours > 0)
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.ITEM_PURCHASABLE_IN_S1_HOURS_S2_MINUTES);
					sm.addNumber(hours);
					sm.addNumber(minutes);
					player.sendPacket(sm);
					htmltext = "32599-scroll24.htm";
				}
				else if (minutes > 0)
				{
					SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.ITEM_PURCHASABLE_IN_S1_MINUTES);
					sm.addNumber(minutes);
					player.sendPacket(sm);
					htmltext = "32599-scroll24.htm";
				}
				else
				{
					// Little glitch. There is no SystemMessage with seconds only.
					// If time is less than 1 minute player can buy scrolls
					if (st.getQuestItemsCount(PcInventory.ADENA_ID) > _scroll_24_price)
					{
						st.takeItems(PcInventory.ADENA_ID, _scroll_24_price);
						st.giveItems(_master_yogi_scroll, 24);
						saveGlobalQuestVar(player.getAccountName(), Long.toString(System.currentTimeMillis() + (_scroll_24_time * 3600000)));
						htmltext = "32599-scroll24.htm";
					}
					else
					{
						htmltext = "32599-s24-no.htm";
					}
				}
			}
		}
		else if (event.equalsIgnoreCase("buy_scroll_1"))
		{
			if (st.getQuestItemsCount(PcInventory.ADENA_ID) > _scroll_1_price)
			{
				st.takeItems(PcInventory.ADENA_ID, _scroll_1_price);
				st.giveItems(_master_yogi_scroll, 1);
				htmltext = "32599-scroll-ok.htm";
			}
			else
			{
				htmltext = "32599-s1-no.htm";
			}
		}
		else if (event.equalsIgnoreCase("buy_scroll_10"))
		{
			if (st.getQuestItemsCount(PcInventory.ADENA_ID) > _scroll_10_price)
			{
				st.takeItems(PcInventory.ADENA_ID, _scroll_10_price);
				st.giveItems(_master_yogi_scroll, 10);
				htmltext = "32599-scroll-ok.htm";
			}
			else
			{
				htmltext = "32599-s10-no.htm";
			}
		}
		else if (event.equalsIgnoreCase("receive_reward"))
		{
			if ((st.getItemEquipped(Inventory.PAPERDOLL_RHAND) == _master_yogi_staff) && (st.getEnchantLevel(_master_yogi_staff) > 3))
			{
				switch (st.getEnchantLevel(_master_yogi_staff))
				{
					case 4:
						st.giveItems(6406, 3); // Firework
						break;
					case 5:
						st.giveItems(6406, 5); // Firework
						st.giveItems(6407, 3); // Large Firework
						break;
					case 6:
						st.giveItems(6406, 10); // Firework
						st.giveItems(6407, 6); // Large Firework
						break;
					case 7:
						st.giveItems(_hat_shadow_reward[getRandom(3)], 1);
						break;
					case 8:
						st.giveItems(730, 1); // Scroll: Enchant Armor (A)
						break;
					case 9:
						st.giveItems(729, 1); // Scroll: Enchant Weapon (A)
						st.giveItems(730, 1); // Scroll: Enchant Armor (A)
						break;
					case 10:
						st.giveItems(960, 1); // Scroll: Enchant Armor (S)
						break;
					case 11:
						st.giveItems(959, 1); // Scroll: Enchant Weapon (S)
						st.giveItems(960, 1); // Scroll: Enchant Armor (S)
						break;
					case 12:
						st.giveItems(6570, 1); // Blessed Scroll: Enchant Armor (A)
						break;
					case 13:
						st.giveItems(6569, 1); // Blessed Scroll: Enchant Weapon (A)
						st.giveItems(6570, 1); // Blessed Scroll: Enchant Armor (A)
						break;
					case 14:
						st.giveItems(_hat_event_reward[getRandom(3)], 1);
						break;
					case 15:
						st.giveItems(13992, 1); // Grade S Accessory Chest (Event)
						break;
					case 16:
						st.giveItems(9576, 1); // Top-Grade Life Stone: level 80
						break;
					case 17:
						st.giveItems(6577, 1); // Blessed Scroll: Enchant Weapon (S)
						st.giveItems(6578, 1); // Blessed Scroll: Enchant Armor (S)
						break;
					case 18:
						st.giveItems(13991, 1); // Grade S Armor Chest (Event)
						break;
					case 19:
						st.giveItems(13990, 1); // Grade S Weapon Chest (Event)
						break;
					case 20:
						st.giveItems(_crystal_reward[getRandom(3)], 1); // Red/Blue/Green Soul Crystal - Stage 16
						break;
					case 21:
						st.giveItems(10486, 1); // Top-Grade Life Stone: level 82
						st.giveItems(10485, 1); // High-Grade Life Stone: level 82
						st.giveItems(_crystal_reward[getRandom(3)], 1); // Red/Blue/Green Soul Crystal - Stage 16
						break;
					case 22:
						st.giveItems(13989, 1); // S80 Grade Armor Chest (Event)
						break;
					case 23:
						st.giveItems(13988, 1); // S80 Grade Weapon Chest (Event)
					default:
						if (st.getEnchantLevel(_master_yogi_staff) > 23)
						{
							st.giveItems(13988, 1); // S80 Grade Weapon Chest (Event)
						}
						break;
				}
				st.takeItems(_master_yogi_staff, 1);
				htmltext = "32599-rewardok.htm";
			}
			else
			{
				htmltext = "32599-rewardnostaff.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		if (player.getQuestState(getName()) == null)
		{
			newQuestState(player);
		}
		return npc.getNpcId() + ".htm";
	}
	
	public static void main(String[] args)
	{
		new MasterOfEnchanting(-1, MasterOfEnchanting.class.getSimpleName(), "events");
	}
}
