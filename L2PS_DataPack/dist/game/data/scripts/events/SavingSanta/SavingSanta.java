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
package events.SavingSanta;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javolution.util.FastMap;

import com.l2jserver.gameserver.datatables.ItemTable;
import com.l2jserver.gameserver.datatables.SkillTable;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.skills.L2Skill;
import com.l2jserver.gameserver.model.zone.ZoneId;
import com.l2jserver.gameserver.network.SystemMessageId;
import com.l2jserver.gameserver.network.serverpackets.ActionFailed;
import com.l2jserver.gameserver.network.serverpackets.MagicSkillUse;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.network.serverpackets.SocialAction;
import com.l2jserver.gameserver.network.serverpackets.SystemMessage;
import com.l2jserver.gameserver.util.Broadcast;
import com.l2jserver.gameserver.util.Util;
import com.l2jserver.util.Rnd;

/**
 * @author RobíkBobík
 */
public class SavingSanta extends Quest
{
	private static final int[] RequiredPieces =
	{
		5556,
		5557,
		5558,
		5559
	};
	private static final int[] RequiredQty =
	{
		4,
		4,
		10,
		1
	};
	private static final int SANTA = 31863;
	private static final int HOLIDAY_SANTA = 4;
	private static final int THOMAS = 13183;
	private static final long MIN_TIME_BETWEEN_2_REWARDS = 43200000;
	private static final int HOLIDAYSANTASREWARD = 20101;
	private static final int HOLIDAYBUFFID = 23017;
	private static final int[] RANDOM_A_PLUS_10_WEAPON =
	{
		81,
		151,
		164,
		213,
		236,
		270,
		289,
		2500,
		7895,
		7902,
		5706
	};
	// 0: Santas Helper Auto buff, 1: Saving Santa part
	private static final boolean[] CONFIG =
	{
		false,
		true
	};
	private static final int[] THOMAS_LOC =
	{
		117935,
		-126003,
		-2585,
		54625
	};
	private static final int[] SantaMageBuffs =
	{
		7055,
		7054,
		7051
	};
	private static final int[] SantaFighterBuffs =
	{
		7043,
		7057,
		7051
	};
	
	private static final int[] StartDate =
	{
		2009,
		11,
		29
	};
	private static final int[] EndDate =
	{
		2015,
		12,
		30
	};
	
	private static final int[] SantaX =
	{
		147698,
		147443,
		82218,
		82754,
		15064,
		111067,
		-12965,
		87362,
		-81037,
		117412,
		43983,
		-45907,
		12153,
		-84458,
		114750,
		-45656,
		-117195
	};
	private static final int[] SantaY =
	{
		-56025,
		26942,
		148605,
		53573,
		143254,
		218933,
		122914,
		-143166,
		150092,
		76642,
		-47758,
		49387,
		16753,
		244761,
		-178692,
		-113119,
		46837
	};
	private static final int[] SantaZ =
	{
		-2775,
		-2205,
		-3470,
		-1496,
		-2668,
		-3543,
		-3117,
		-1293,
		-3044,
		-2695,
		-797,
		-3060,
		-4584,
		-3730,
		-820,
		-240,
		367
	};
	
	private static final int[] TreeSpawnX =
	{
		83254,
		83278,
		83241,
		83281,
		84304,
		84311,
		82948,
		80905,
		80908,
		82957,
		147849,
		147580,
		147581,
		147847,
		149085,
		146340,
		147826,
		147584,
		146235,
		147840,
		147055,
		148694,
		147733,
		147197,
		147266,
		147646,
		147456,
		148078,
		147348,
		117056,
		116473,
		115785,
		115939,
		116833,
		116666,
		-13130,
		-13165,
		-13126,
		15733,
		16208
	};
	private static final int[] TreeSpawnY =
	{
		148340,
		147900,
		148898,
		149343,
		149133,
		148101,
		147658,
		147659,
		149556,
		149554,
		-55119,
		-55117,
		-57244,
		-57261,
		-55826,
		-55829,
		-54095,
		-54070,
		25921,
		25568,
		25568,
		25929,
		27366,
		27364,
		29065,
		29065,
		27664,
		-55960,
		-55939,
		75627,
		75352,
		76111,
		76544,
		77400,
		76210,
		122533,
		122425,
		122806,
		142767,
		142710
	};
	private static final int[] TreeSpawnZ =
	{
		-3405,
		-3405,
		-3405,
		-3405,
		-3402,
		-3402,
		-3469,
		-3469,
		-3469,
		-3469,
		-2734,
		-2734,
		-2781,
		-2781,
		-2781,
		-2781,
		-2735,
		-2735,
		-2013,
		-2013,
		-2013,
		-2013,
		-2205,
		-2205,
		-2269,
		-2269,
		-2204,
		-2781,
		-2781,
		-2726,
		-2712,
		-2715,
		-2719,
		-2697,
		-2730,
		-3117,
		-2989,
		-3117,
		-2706,
		-2706
	};
	
	private static GregorianCalendar calendar;
	private static GregorianCalendar startCalendar;
	private static GregorianCalendar endCalendar;
	
	private static boolean ChristmasEvent = false;
	private static boolean isSantaFree = false;
	private static boolean isWaitingForPlayerSkill = false;
	private static List<L2Npc> SantaHelpers = new ArrayList<>();
	private static List<L2Npc> specialTrees = new ArrayList<>();
	private final Map<String, Long> rewardedPlayers = new FastMap<>();
	
	public SavingSanta(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(SANTA);
		addFirstTalkId(SANTA);
		addTalkId(SANTA);
		addFirstTalkId(THOMAS);
		addFirstTalkId(HOLIDAY_SANTA);
		addSkillSeeId(THOMAS);
		addSpellFinishedId(THOMAS);
		addSpawnId(13007);
		
		this.startQuestTimer("ChristmasCheck", 1800000, null, null);
		
		calendar = new GregorianCalendar();
		startCalendar = new GregorianCalendar(StartDate[0], (StartDate[1] - 1), StartDate[2]);
		endCalendar = new GregorianCalendar(EndDate[0], (EndDate[1] - 1), EndDate[2]);
		
		if (calendar.after(startCalendar) && calendar.before(endCalendar))
		{
			ChristmasEvent = true;
		}
		
		if (ChristmasEvent)
		{
			System.out.println("Christmas Event - ON");
			
			for (int i = 0; i < TreeSpawnX.length; i++)
			{
				this.addSpawn(13006, TreeSpawnX[i], TreeSpawnY[i], TreeSpawnZ[i], 0, false, 0);
			}
			
			for (int i = 0; i < SantaX.length; i++)
			{
				final L2Npc mob = this.addSpawn(SANTA, SantaX[i], SantaY[i], SantaZ[i], 0, false, 0);
				SantaHelpers.add(mob);
			}
			if (CONFIG[0])
			{
				this.startQuestTimer("SantaBlessings", 5000, null, null);
			}
			this.startQuestTimer("SpecialTreeHeal", 5000, null, null);
			if (CONFIG[1])
			{
				this.startQuestTimer("ThomasQuest", 120000, null, null);
			}
		}
		else
		{
			System.out.println("Christmas Event - OFF");
			
			final GregorianCalendar endWeek = (GregorianCalendar) endCalendar.clone();
			endWeek.add(Calendar.DAY_OF_MONTH, 7);
			if (calendar.after(endCalendar) && calendar.before(endWeek))
			{
				for (int i = 0; i < SantaX.length; i++)
				{
					this.addSpawn(SANTA, SantaX[i], SantaY[i], SantaZ[i], 0, false, 0);
				}
			}
		}
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		specialTrees.add(npc);
		return super.onSpawn(npc);
	}
	
	@Override
	public String onSkillSee(L2Npc npc, L2PcInstance caster, L2Skill skill, L2Object[] targets, boolean isPet)
	{
		if (isWaitingForPlayerSkill && (skill.getId() > 21013) && (skill.getId() < 21017))
		{
			caster.broadcastPacket(new MagicSkillUse(caster, caster, 23019, skill.getId() - 21013, 3000, 1));
			SkillTable.getInstance().getInfo(23019, skill.getId() - 21013).getEffects(caster, caster);
		}
		return "";
	}
	
	@Override
	public String onSpellFinished(L2Npc npc, L2PcInstance player, L2Skill skill)
	{
		if (skill.getId() == 6100)
		{
			isWaitingForPlayerSkill = false;
			for (final L2PcInstance pl : npc.getKnownList().getKnownPlayersInRadius(600))
			{
				if (pl.getFirstEffect(23019) == null)
				{
					continue;
				}
				final int result = pl.getFirstEffect(23019).getSkill().getLevel() - skill.getLevel();
				if ((result == 1) || (result == -2))
				{
					final int level = (pl.getFirstEffect(23022) != null ? (pl.getFirstEffect(23022).getSkill().getLevel() + 1) : 1);
					pl.broadcastPacket(new MagicSkillUse(pl, pl, 23022, level, 3000, 1));
					SkillTable.getInstance().getInfo(23022, level).getEffects(pl, pl);
					if (level == 3)
					{
						SkillTable.getInstance().getInfo(23018, 1).getEffects(pl, pl);
					}
					else if (level == 4)
					{
						Broadcast.toAllOnlinePlayers(SystemMessage.getSystemMessage(SystemMessageId.THOMAS_D_TURKEY_DEFETED));
						
						Broadcast.announceToOnlinePlayers("Message from Santa Claus: Many blessings to " + pl.getName() + ", who saved me", true);
						this.startQuestTimer("SantaSpawn", 120000, null, null);
						npc.decayMe();
						isSantaFree = true;
						break;
					}
				}
				else if (result != 0)
				{
					pl.broadcastPacket(new MagicSkillUse(pl, pl, 23023, 1, 3000, 1));
					pl.stopSkillEffects(23022);
				}
			}
		}
		return "";
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		
		if (npc == null)
		{
			if (event.equalsIgnoreCase("ChristmasCheck"))
			{
				this.startQuestTimer("ChristmasCheck", 1800000, null, null);
				boolean Event1 = false;
				
				calendar = new GregorianCalendar();
				
				if (calendar.after(startCalendar) && calendar.before(endCalendar))
				{
					Event1 = true;
				}
				
				if (!ChristmasEvent && Event1)
				{
					ChristmasEvent = true;
					System.out.println("Christmas Event - ON");
					if (CONFIG[0])
					{
						this.startQuestTimer("SantaBlessings", 5000, null, null);
					}
					this.startQuestTimer("SpecialTreeHeal", 5000, null, null);
					if (CONFIG[1])
					{
						this.startQuestTimer("ThomasQuest", 120000, null, null);
					}
					
					for (int i = 0; i < TreeSpawnX.length; i++)
					{
						this.addSpawn(13006, TreeSpawnX[i], TreeSpawnY[i], TreeSpawnZ[i], 0, false, 0);
					}
					
					for (int i = 0; i < SantaX.length; i++)
					{
						final L2Npc mob = this.addSpawn(SANTA, SantaX[i], SantaY[i], SantaZ[i], 0, false, 0);
						SantaHelpers.add(mob);
					}
				}
				else if (ChristmasEvent && Event1)
				{
					ChristmasEvent = false;
					System.out.println("Christmas Event - OFF");
					cancelQuestTimer("SantaBlessings", null, null);
					cancelQuestTimer("SpecialTreeHeal", null, null);
					cancelQuestTimer("ThomasQuest", null, null);
					for (final L2Npc santaHelper : SantaHelpers)
					{
						santaHelper.deleteMe();
					}
				}
			}
			else if (event.equalsIgnoreCase("ThomasQuest"))
			{
				this.startQuestTimer("ThomasQuest", 14400000, null, null);
				final L2Npc thomas = this.addSpawn(THOMAS, THOMAS_LOC[0], THOMAS_LOC[1], THOMAS_LOC[2], THOMAS_LOC[3], false, 1800000);
				Broadcast.toAllOnlinePlayers(SystemMessage.getSystemMessage(SystemMessageId.THOMAS_D_TURKEY_APPEARED));
				this.startQuestTimer("ThomasCast1", 15000, thomas, null);
				isSantaFree = false;
			}
			else if (event.equalsIgnoreCase("SantaSpawn"))
			{
				if (isSantaFree)
				{
					this.startQuestTimer("SantaSpawn", 120000, null, null);
					for (final L2PcInstance pl : L2World.getInstance().getAllPlayersArray())
					{
						if ((pl.isOnline()) && (pl.getLevel() >= 20) && pl.isInCombat() && !pl.isInsideZone(ZoneId.PEACE) && !pl.isFlyingMounted())
						{
							if (rewardedPlayers.containsKey(pl.getAccountName()))
							{
								final long elapsedTimeSinceLastRewarded = System.currentTimeMillis() - rewardedPlayers.get(pl.getAccountName());
								if (elapsedTimeSinceLastRewarded < MIN_TIME_BETWEEN_2_REWARDS)
								{
									continue;
								}
							}
							else
							{
								final String data = loadGlobalQuestVar(pl.getAccountName());
								if (!data.isEmpty() && ((System.currentTimeMillis() - Long.parseLong(data)) < MIN_TIME_BETWEEN_2_REWARDS))
								{
									rewardedPlayers.put(pl.getAccountName(), Long.parseLong(data));
									continue;
								}
							}
							final int locx = (int) (pl.getX() + (Math.pow(-1, Rnd.get(1, 2)) * 50));
							final int locy = (int) (pl.getY() + (Math.pow(-1, Rnd.get(1, 2)) * 50));
							final int heading = Util.calculateHeadingFrom(locx, locy, pl.getX(), pl.getY());
							final L2Npc santa = this.addSpawn(HOLIDAY_SANTA, locx, locy, pl.getZ(), heading, false, 30000);
							rewardedPlayers.put(pl.getAccountName(), System.currentTimeMillis());
							saveGlobalQuestVar(pl.getAccountName(), String.valueOf(System.currentTimeMillis()));
							this.startQuestTimer("SantaRewarding0", 500, santa, pl);
						}
					}
				}
			}
		}
		else if (event.equalsIgnoreCase("ThomasCast1"))
		{
			if (!npc.isDecayed())
			{
				isWaitingForPlayerSkill = true;
				this.startQuestTimer("ThomasCast2", 4000, npc, null);
				npc.doCast(SkillTable.getInstance().getInfo(6116, 1));
			}
			else
			{
				Broadcast.toAllOnlinePlayers(SystemMessage.getSystemMessage(SystemMessageId.THOMAS_D_TURKEY_DISAPPEARED));
				isWaitingForPlayerSkill = false;
			}
		}
		else if (event.equalsIgnoreCase("ThomasCast2"))
		{
			if (!npc.isDecayed())
			{
				this.startQuestTimer("ThomasCast1", 13000, npc, null);
				npc.doCast(SkillTable.getInstance().getInfo(6100, Rnd.get(1, 3)));
			}
			else
			{
				Broadcast.toAllOnlinePlayers(SystemMessage.getSystemMessage(SystemMessageId.THOMAS_D_TURKEY_DISAPPEARED));
				isWaitingForPlayerSkill = false;
			}
		}
		else if (event.equalsIgnoreCase("SantaRewarding0"))
		{
			this.startQuestTimer("SantaRewarding1", 9500, npc, player);
			npc.broadcastPacket(new SocialAction(npc.getObjectId(), 3));
		}
		else if (event.equalsIgnoreCase("SantaRewarding1"))
		{
			this.startQuestTimer("SantaRewarding2", 5000, npc, player);
			npc.broadcastPacket(new SocialAction(npc.getObjectId(), 1));
			npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), "Happy holidays! Thanks to the citizens of Aden for freeing me from the clutches of that miserable turkey."));
		}
		else if (event.equalsIgnoreCase("SantaRewarding2"))
		{
			this.startQuestTimer("SantaRewarding3", 5000, npc, player);
			npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), "I have a gift for " + player.getName() + "."));
		}
		else if (event.equalsIgnoreCase("SantaRewarding3"))
		{
			QuestState st = player.getQuestState(getName());
			if (st == null)
			{
				st = newQuestState(player);
			}
			st.giveItems(HOLIDAYSANTASREWARD, 1);
			npc.broadcastPacket(new SocialAction(npc.getObjectId(), 2));
			npc.broadcastPacket(new NpcSay(npc.getObjectId(), 0, npc.getNpcId(), "Take a look in your inventory. I hope you like your present."));
		}
		
		else if (event.equalsIgnoreCase("SantaBlessings"))
		{
			if (ChristmasEvent)
			{
				this.startQuestTimer("SantaBlessings", 15000, null, null);
				for (final L2Npc santaHelper : SantaHelpers)
				{
					final Collection<L2PcInstance> playerList = santaHelper.getKnownList().getKnownPlayers().values();
					for (final L2PcInstance playerx : playerList)
					{
						if (playerx.getClassId().isMage())
						{
							for (final int buffId : SantaMageBuffs)
							{
								if (playerx.getFirstEffect(buffId) == null)
								{
									playerx.broadcastPacket(new MagicSkillUse(santaHelper, playerx, buffId, 1, 2000, 1));
									SkillTable.getInstance().getInfo(buffId, 1).getEffects(playerx, playerx);
								}
							}
						}
						else
						{
							for (final int buffId : SantaFighterBuffs)
							{
								if (playerx.getFirstEffect(buffId) == null)
								{
									playerx.broadcastPacket(new MagicSkillUse(santaHelper, playerx, buffId, 1, 2000, 1));
									SkillTable.getInstance().getInfo(buffId, 1).getEffects(playerx, playerx);
								}
							}
						}
					}
				}
			}
		}
		
		else if (event.equalsIgnoreCase("SpecialTreeHeal"))
		{
			this.startQuestTimer("SpecialTreeHeal", 9000, null, null);
			for (final L2Npc tree : specialTrees)
			{
				final Collection<L2PcInstance> playerList = tree.getKnownList().getKnownPlayers().values();
				for (final L2PcInstance playerr : playerList)
				{
					final int xxMin = tree.getX() - 60;
					final int yyMin = tree.getY() - 60;
					final int xxMax = tree.getX() + 60;
					final int yyMax = tree.getY() + 60;
					final int playerX = playerr.getX();
					final int playerY = playerr.getY();
					
					if ((playerX > xxMin) && (playerX < xxMax) && (playerY > yyMin) && (playerY < yyMax))
					{
						SkillTable.getInstance().getInfo(2139, 1).getEffects(tree, playerr);
					}
				}
			}
		}
		else if (player != null)
		{
			QuestState st = player.getQuestState(getName());
			if (st == null)
			{
				st = newQuestState(player);
			}
			if (event.equalsIgnoreCase("Tree"))
			{
				int itemsOk = 0;
				htmltext = "<html><title>Christmas Event</title><body><br><br><table width=260><tr><td></td><td width=40></td><td width=40></td></tr><tr><td><font color=LEVEL>Christmas Tree</font></td><td width=40><img src=\"Icon.etc_x_mas_tree_i00\" width=32 height=32></td><td width=40></td></tr></table><br><br><table width=260>";
				
				for (int i = 0; i < RequiredPieces.length; i++)
				{
					final long pieceCount = st.getQuestItemsCount(RequiredPieces[i]);
					if (pieceCount >= RequiredQty[i])
					{
						itemsOk = itemsOk + 1;
						htmltext = htmltext + "<tr><td>" + ItemTable.getInstance().getTemplate(RequiredPieces[i]).getName() + "</td><td width=40>" + pieceCount + "</td><td width=40><font color=0FF000>OK</font></td></tr>";
					}
					else
					{
						htmltext = htmltext + "<tr><td>" + ItemTable.getInstance().getTemplate(RequiredPieces[i]).getName() + "</td><td width=40>" + pieceCount + "</td><td width=40><font color=8ae2ffb>NO</font></td></tr>";
					}
				}
				
				if (itemsOk == 4)
				{
					htmltext = htmltext + "<tr><td><br></td><td width=40></td><td width=40></td></tr></table><table width=260>";
					htmltext = htmltext + "<tr><td><center><button value=\"Get the tree\" action=\"bypass -h Quest SavingSanta buyTree\" width=110 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></td></tr></table></body></html>";
				}
				
				else if (itemsOk < 4)
				{
					htmltext = htmltext + "</table><br><br>You do not have enough items.</center></body></html>";
				}
				
				return htmltext;
			}
			
			else if (event.equalsIgnoreCase("buyTree"))
			{
				st.playSound("ItemSound.quest_middle");
				
				for (int i = 0; i < RequiredPieces.length; i++)
				{
					if (st.getQuestItemsCount(RequiredPieces[i]) < RequiredQty[i])
					{
						return "";
					}
				}
				
				for (int i = 0; i < RequiredPieces.length; i++)
				{
					st.takeItems(RequiredPieces[i], RequiredQty[i]);
				}
				st.giveItems(5560, 1);
			}
			
			else if (event.equalsIgnoreCase("SpecialTree") && !CONFIG[1])
			{
				htmltext = "<html><title>Christmas Event</title><body><br><br><table width=260><tr><td></td><td width=40></td><td width=40></td></tr><tr><td><font color=LEVEL>Special Christmas Tree</font></td><td width=40><img src=\"Icon.etc_x_mas_tree_i00\" width=32 height=32></td><td width=40></td></tr></table><br><br><table width=260>";
				final long pieceCount = st.getQuestItemsCount(5560);
				int itemsOk = 0;
				
				if (pieceCount >= 10)
				{
					itemsOk = 1;
					htmltext = htmltext + "<tr><td>Christmas Tree</td><td width=40>" + pieceCount + "</td><td width=40><font color=0FF000>OK</font></td></tr>";
				}
				else
				{
					htmltext = htmltext + "<tr><td>Christmas Tree</td><td width=40>" + pieceCount + "</td><td width=40><font color=8ae2ffb>NO</font></td></tr>";
				}
				
				if (itemsOk == 1)
				{
					htmltext = htmltext + "<tr><td><br></td><td width=40></td><td width=40></td></tr></table><table width=260>";
					htmltext = htmltext + "<tr><td><center><button value=\"Get the tree\" action=\"bypass -h Quest SavingSanta buySpecialTree\" width=110 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></td></tr></table></body></html>";
				}
				
				else if (itemsOk == 0)
				{
					htmltext = htmltext + "</table><br><br>You do not have enough items.</center></body></html>";
				}
				
				return htmltext;
			}
			
			else if (event.equalsIgnoreCase("buySpecialTree") && !CONFIG[1])
			{
				st.playSound("ItemSound.quest_middle");
				if (st.getQuestItemsCount(5560) < 10)
				{
					return "";
				}
				st.takeItems(5560, 10);
				st.giveItems(5561, 1);
			}
			
			else if (event.equalsIgnoreCase("SantaHat"))
			{
				htmltext = "<html><title>Christmas Event</title><body><br><br><table width=260><tr><td></td><td width=40></td><td width=40></td></tr><tr><td><font color=LEVEL>Santa's Hat</font></td><td width=40><img src=\"Icon.Accessory_santas_cap_i00\" width=32 height=32></td><td width=40></td></tr></table><br><br><table width=260>";
				final long pieceCount = st.getQuestItemsCount(5560);
				int itemsOk = 0;
				
				if (pieceCount >= 10)
				{
					itemsOk = 1;
					htmltext = htmltext + "<tr><td>Christmas Tree</td><td width=40>" + pieceCount + "</td><td width=40><font color=0FF000>OK</font></td></tr>";
				}
				else
				{
					htmltext = htmltext + "<tr><td>Christmas Tree</td><td width=40>" + pieceCount + "</td><td width=40><font color=8ae2ffb>NO</font></td></tr>";
				}
				
				if (itemsOk == 1)
				{
					htmltext = htmltext + "<tr><td><br></td><td width=40></td><td width=40></td></tr></table><table width=260>";
					htmltext = htmltext + "<tr><td><center><button value=\"Get the hat\" action=\"bypass -h Quest SavingSanta buyHat\" width=110 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></td></tr></table></body></html>";
				}
				
				else if (itemsOk == 0)
				{
					htmltext = htmltext + "</table><br><br>You do not have enough items.</center></body></html>";
				}
				
				return htmltext;
			}
			
			else if (event.equalsIgnoreCase("buyHat"))
			{
				st.playSound("ItemSound.quest_middle");
				if (st.getQuestItemsCount(5560) < 10)
				{
					return "";
				}
				st.takeItems(5560, 10);
				st.giveItems(7836, 1);
			}
			else if (event.equalsIgnoreCase("SavingSantaHat") && CONFIG[1])
			{
				htmltext = "<html><title>Christmas Event</title><body><br><br><table width=260><tr><td></td><td width=40></td><td width=40></td></tr><tr><td><font color=LEVEL>Saving Santa's Hat</font></td><td width=40><img src=\"Icon.Accessory_santas_cap_i00\" width=32 height=32></td><td width=40></td></tr></table><br><br><table width=260>";
				final long pieceCount = st.getQuestItemsCount(57);
				int itemsOk = 0;
				
				if (pieceCount >= 50000)
				{
					itemsOk = 1;
					htmltext = htmltext + "<tr><td>Adena</td><td width=40>" + pieceCount + "</td><td width=40><font color=0FF000>OK</font></td></tr>";
				}
				else
				{
					htmltext = htmltext + "<tr><td>Adena</td><td width=40>" + pieceCount + "</td><td width=40><font color=8ae2ffb>NO</font></td></tr>";
				}
				
				if (itemsOk == 1)
				{
					htmltext = htmltext + "<tr><td><br></td><td width=40></td><td width=40></td></tr></table><table width=260>";
					htmltext = htmltext + "<tr><td><center><button value=\"Get the hat\" action=\"bypass -h Quest SavingSanta buySavingHat\" width=110 height=20 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\"></center></td></tr></table></body></html>";
				}
				
				else if (itemsOk == 0)
				{
					htmltext = htmltext + "</table><br><br>You do not have enough Adena.</center></body></html>";
				}
				
				return htmltext;
			}
			
			else if (event.equalsIgnoreCase("buySavingHat") && CONFIG[1])
			{
				st.playSound("ItemSound.quest_middle");
				if (st.getQuestItemsCount(57) < 50000)
				{
					return "";
				}
				st.takeItems(57, 50000);
				st.giveItems(20100, 1);
			}
			else if (event.equalsIgnoreCase("HolidayFestival") && CONFIG[1])
			{
				if (isSantaFree)
				{
					npc.broadcastPacket(new MagicSkillUse(npc, player, HOLIDAYBUFFID, 1, 2000, 1));
					SkillTable.getInstance().getInfo(HOLIDAYBUFFID, 1).getEffects(player, player);
				}
				else
				{
					return "savingsanta-nobuff.htm";
				}
			}
			else if (event.equalsIgnoreCase("getWeapon") && CONFIG[1])
			{
				if ((st.getQuestItemsCount(20107) == 0) && (st.getQuestItemsCount(20108) == 0))
				{
					return "savingsanta-noweapon.htm";
				}
				return "savingsanta-weapon.htm";
			}
			else if (event.startsWith("weapon_") && CONFIG[1])
			{
				if (st.getQuestItemsCount(20108) != 0)
				{
					st.takeItems(20108, 1);
					st.giveItems(RANDOM_A_PLUS_10_WEAPON[Rnd.get(RANDOM_A_PLUS_10_WEAPON.length)], 1, 10);
					return "";
				}
				else if ((st.getQuestItemsCount(20107) == 0) || (player.getLevel() < 20))
				{
					return "";
				}
				int grade = player.getSkillLevel(239) - 1;
				if (grade < -1)
				{
					return "";
				}
				int itemId = Integer.parseInt(event.replace("weapon_", ""));
				if ((itemId < 1) || (itemId > 14))
				{
					return "";
				}
				else if (grade > 4)
				{
					grade = 4;
				}
				itemId += (20108 + (grade * 14));
				st.takeItems(20107, 1);
				st.giveItems(itemId, 1, Rnd.get(4, 16));
			}
		}
		return "";
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);
		}
		if ((npc.getNpcId() == THOMAS) || (npc.getNpcId() == HOLIDAY_SANTA))
		{
			player.sendPacket(ActionFailed.STATIC_PACKET);
			return null;
		}
		
		if (npc.getNpcId() == SANTA)
		{
			if (CONFIG[1])
			{
				htmltext = "savingsanta.htm";
			}
			else
			{
				htmltext = "santa.htm";
			}
		}
		return htmltext;
	}
	
	@Override
	public boolean unload()
	{
		for (final L2Npc eventnpc : SantaHelpers)
		{
			eventnpc.deleteMe();
		}
		for (final L2Npc eventnpc : specialTrees)
		{
			eventnpc.deleteMe();
		}
		return super.unload();
	}
	
	public static void main(String[] args)
	{
		new SavingSanta(-1, SavingSanta.class.getSimpleName(), "events");
	}
}
