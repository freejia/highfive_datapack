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
package events.NinjaAdventures;

import com.l2jserver.FunEvents;
import com.l2jserver.gameserver.ai.CtrlIntention;
import com.l2jserver.gameserver.model.L2Clan;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * @author RobíkBobík
 */
public class NinjaAdventures extends Quest
{
	private static final int Ninja_Master = 7102;
	private static final int[] Konoha_Mobs =
	{
		22257,
		22258,
		22259,
		22260,
		22261,
		22262,
		22263,
		22264,
		22265,
		22266,
		22267
	};
	private static final int[] Kusa_Mobs =
	{
		21394,
		21376,
		21377,
		21378,
		21652,
		21379,
		21653,
		21381,
		21380,
		21384,
		21654,
		21383,
		21382,
		21655,
		21385,
		21386
	};
	private static final int[] Kiri_Mobs =
	{
		21523,
		21526,
		21520,
		21524,
		21521,
		21524,
		21529,
		21530
	};
	private static final int[] Suna_Mobs =
	{
		21570,
		21572,
		21574,
		21578,
		21561,
		21559,
		21598,
		21597,
		21557,
		21567,
		21565,
		21553,
		21547,
		21563,
		21596
	};
	private static final int[] Yuki_Mobs =
	{
		21394,
		21376,
		21377,
		21378,
		21652,
		21379,
		21653,
		21381,
		21380,
		21384,
		21654,
		21383,
		21382,
		21655,
		21385,
		21386
	};
	private static final int[] Taki_Mobs =
	{
		21298,
		21299,
		21304,
		21305,
		21303,
		21307,
		21308,
		21309,
		21310,
		21311
	};
	private static final int[] Oto_Mobs =
	{
		21396,
		21397,
		21398,
		21399,
		21400,
		21401,
		21402,
		21403,
		21404,
		21405,
		21406,
		21407,
		21408,
		21409,
		21410,
		21411,
		21412,
		21413,
		21414,
		21415,
		21416,
		21417,
		21418,
		21419,
		21420,
		21421,
		21422,
		21423,
		21424,
		21425,
		21426,
		21427,
		21428,
		21429,
		21430,
		21431
	};
	private static final int Konoha_Ninja = 70001;
	private static final int Kusa_Ninja = 70002;
	private static final int Kiri_Ninja = 70003;
	private static final int Suna_Ninja = 70004;
	private static final int Yuki_Ninja = 70005;
	private static final int Taki_Ninja = 70006;
	private static final int Oto_Ninja = 70007;
	public int Konoha_Points;
	public int Kusa_Points;
	public int Kiri_Points;
	public int Suna_Points;
	public int Yuki_Points;
	public int Taki_Points;
	public int Oto_Points;
	
	public static boolean contains(int[] array, int obj)
	{
		for (int element : array)
		{
			if (element == obj)
			{
				return true;
			}
		}
		return false;
	}
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);
		}
		String htmltext = "";
		if (st.getState() == State.STARTED)
		{
			if (FunEvents.NA_STARTED)
			{
				htmltext = "welcome.htm";
			}
			else
			{
				htmltext = FunEvents.EVENT_DISABLED;
			}
			return htmltext;
		}
		if (FunEvents.NA_STARTED)
		{
			htmltext = "select.htm";
		}
		else
		{
			htmltext = FunEvents.EVENT_DISABLED;
		}
		return htmltext;
	}
	
	@Override
	public final String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);
		}
		int village = st.getInt("village");
		int points = st.getInt("points");
		int recomend_vallue = st.getInt("recomendvalue");
		int adena_vallue = st.getInt("adenavalue");
		int aadena_vallue = st.getInt("aadenavalue");
		int clanpoints_vallue = st.getInt("clanpoints_vallue");
		int Konoha_Value = (Konoha_Points - Suna_Points - Oto_Points);
		int Kusa_Value = (Kusa_Points - Taki_Points - Oto_Points);
		int Kiri_Value = (Kiri_Points - Kusa_Points - Yuki_Points);
		int Suna_Value = (Suna_Points - Konoha_Points - Taki_Points);
		int Taki_Value = (Taki_Points - Suna_Points - Yuki_Points);
		int Yuki_Value = (Yuki_Points - Konoha_Points - Kusa_Points);
		int Oto_Value = (Oto_Points - Konoha_Points - Kusa_Points);
		int gakure_select = st.getInt("gakure");
		String htmltext = "";
		String vilage = "";
		if (event.equalsIgnoreCase("checkpoints"))
		{
			switch (village)
			{
				case 1:
					vilage = "Konoha";
					break;
				case 2:
					vilage = "Kusa";
					break;
				case 3:
					vilage = "Kiri";
					break;
				case 4:
					vilage = "Suna";
					break;
				case 5:
					vilage = "Yuki";
					break;
				case 6:
					vilage = "Taki";
					break;
				case 7:
					vilage = "Oto";
					break;
			}
			return "<html><font color=LEVEL>Ninja Master</font><br><br>Hello you fight good for <font color=LEVEL>" + vilage + " Village</font> and your kill points now: &nbsp;<br><br><font color=AC13DD> " + points + " </html>";
		}
		if (event.equalsIgnoreCase("gakure"))
		{
			gakure_select = Rnd.get(1, 7);
			switch (gakure_select)
			{
				case 1:
					st.set("village", "1");
					break;
				case 2:
					st.set("village", "2");
					break;
				case 3:
					st.set("village", "3");
					break;
				case 4:
					st.set("village", "4");
					break;
				case 5:
					st.set("village", "5");
					break;
				case 6:
					st.set("village", "6");
					break;
				case 7:
					st.set("village", "7");
					break;
			}
			st.setState(State.STARTED);
			htmltext = "welcome.htm";
			st.giveItems(FunEvents.NA_HAIRBAND, 1);
		}
		if (event.equalsIgnoreCase("prizes"))
		{
			if (village == 1)
			{
				htmltext = "prizes.htm";
			}
			if (village == 2)
			{
				htmltext = "prizes.htm";
			}
			if (village == 3)
			{
				htmltext = "prizes.htm";
			}
			if (village == 4)
			{
				htmltext = "prizes.htm";
			}
			if (village == 5)
			{
				htmltext = "prizes.htm";
			}
			if (village == 6)
			{
				htmltext = "prizes.htm";
			}
			if (village == 7)
			{
				htmltext = "prizes.htm";
			}
		}
		if (event.equalsIgnoreCase("get_recommend"))
		{
			if (village == 1)
			{
				recomend_vallue = ((Konoha_Value - 100) + points);
				if ((recomend_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					if (recomend_vallue > 100)
					{
						recomend_vallue = 100;
					}
					player.setRecomHave(recomend_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 2)
			{
				recomend_vallue = ((Kusa_Value - 100) + points);
				if ((recomend_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					if (recomend_vallue > 100)
					{
						recomend_vallue = 100;
					}
					player.setRecomHave(recomend_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 3)
			{
				recomend_vallue = ((Kiri_Value - 100) + points);
				if ((recomend_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					if (recomend_vallue > 100)
					{
						recomend_vallue = 100;
					}
					player.setRecomHave(recomend_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 4)
			{
				recomend_vallue = ((Suna_Value - 100) + points);
				if ((recomend_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					if (recomend_vallue > 100)
					{
						recomend_vallue = 100;
					}
					player.setRecomHave(recomend_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 5)
			{
				recomend_vallue = ((Yuki_Value - 100) + points);
				if ((recomend_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					if (recomend_vallue > 100)
					{
						recomend_vallue = 100;
					}
					player.setRecomHave(recomend_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 6)
			{
				recomend_vallue = ((Taki_Value - 100) + points);
				if ((recomend_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					if (recomend_vallue > 100)
					{
						recomend_vallue = 100;
					}
					player.setRecomHave(recomend_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 7)
			{
				recomend_vallue = ((Oto_Value - 100) + points);
				if ((recomend_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					if (recomend_vallue > 100)
					{
						recomend_vallue = 100;
					}
					player.setRecomHave(recomend_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
		}
		if (event.equalsIgnoreCase("give_adena"))
		{
			if (village == 1)
			{
				adena_vallue = ((Konoha_Value - 500) + (points * 2));
				if ((adena_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					st.giveItems(FunEvents.NA_ADENA, adena_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 2)
			{
				adena_vallue = ((Kusa_Value - 500) + (points * 2));
				if ((adena_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					st.giveItems(FunEvents.NA_ADENA, adena_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 3)
			{
				adena_vallue = ((Kiri_Value - 500) + (points * 2));
				if ((adena_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					st.giveItems(FunEvents.NA_ADENA, adena_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 4)
			{
				adena_vallue = ((Suna_Value - 500) + (points * 2));
				if ((adena_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					st.giveItems(FunEvents.NA_ADENA, adena_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 5)
			{
				adena_vallue = ((Yuki_Value - 500) + (points * 2));
				if ((adena_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					st.giveItems(FunEvents.NA_ADENA, adena_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 6)
			{
				adena_vallue = ((Taki_Value - 500) + (points * 2));
				if ((adena_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					st.giveItems(FunEvents.NA_ADENA, adena_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 7)
			{
				adena_vallue = ((Oto_Value - 500) + (points * 2));
				if ((adena_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					st.giveItems(FunEvents.NA_ADENA, adena_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
		}
		if (event.equalsIgnoreCase("give_aadena"))
		{
			if (village == 1)
			{
				aadena_vallue = ((Konoha_Value - 2500) + (points * 2));
				if ((aadena_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					st.giveItems(FunEvents.NA_ANCIENT_ADENA, aadena_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 2)
			{
				aadena_vallue = ((Kusa_Value - 2500) + (points * 2));
				if ((aadena_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					st.giveItems(FunEvents.NA_ANCIENT_ADENA, aadena_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 3)
			{
				aadena_vallue = ((Kiri_Value - 2500) + (points * 2));
				if ((aadena_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					st.giveItems(FunEvents.NA_ANCIENT_ADENA, aadena_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 4)
			{
				aadena_vallue = ((Suna_Value - 2500) + (points * 2));
				if ((aadena_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					st.giveItems(FunEvents.NA_ANCIENT_ADENA, aadena_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 5)
			{
				aadena_vallue = ((Yuki_Value - 2500) + (points * 2));
				if ((aadena_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					st.giveItems(FunEvents.NA_ANCIENT_ADENA, aadena_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 6)
			{
				aadena_vallue = ((Taki_Value - 2500) + (points * 2));
				if ((aadena_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					st.giveItems(FunEvents.NA_ANCIENT_ADENA, aadena_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
			if (village == 7)
			{
				aadena_vallue = ((Oto_Value - 2500) + (points * 2));
				if ((aadena_vallue < 1) || (points < 1))
				{
					player.sendMessage("You have enough killing points.");
					htmltext = "prizes.htm";
				}
				else
				{
					st.giveItems(FunEvents.NA_ANCIENT_ADENA, aadena_vallue);
					htmltext = "prizes.htm";
					st.set("points", "0");
				}
			}
		}
		if (event.equalsIgnoreCase("give_clanpoints"))
		{
			if (player.getClan() == null)
			{
				player.sendMessage("You are not in clan.");
				htmltext = "prizes.htm";
			}
			else
			{
				if (village == 1)
				{
					clanpoints_vallue = ((Konoha_Value - 3500) + (points * 2));
					L2Clan clan = player.getClan();
					if ((clan.getLevel() < 5) || (clanpoints_vallue < 1))
					{
						player.sendMessage("You are not in clan with level 5, or you have enough killing points.");
						htmltext = "prizes.htm";
					}
					else
					{
						clan.addReputationScore(clanpoints_vallue, true);
						htmltext = "prizes.htm";
						st.set("points", "0");
					}
				}
				if (village == 2)
				{
					clanpoints_vallue = ((Kusa_Value - 3500) + (points * 2));
					L2Clan clan = player.getClan();
					if ((clan.getLevel() < 5) || (clanpoints_vallue < 1))
					{
						player.sendMessage("You are not in clan with level 5, or you have enough killing points.");
						htmltext = "prizes.htm";
					}
					else
					{
						clan.addReputationScore(clanpoints_vallue, true);
						htmltext = "prizes.htm";
						st.set("points", "0");
					}
				}
				if (village == 3)
				{
					clanpoints_vallue = ((Kiri_Value - 3500) + (points * 2));
					L2Clan clan = player.getClan();
					if ((clan.getLevel() < 5) || (clanpoints_vallue < 1))
					{
						player.sendMessage("You are not in clan with level 5, or you have enough killing points.");
						htmltext = "prizes.htm";
					}
					else
					{
						clan.addReputationScore(clanpoints_vallue, true);
						htmltext = "prizes.htm";
						st.set("points", "0");
					}
				}
				if (village == 4)
				{
					clanpoints_vallue = ((Suna_Value - 3500) + (points * 2));
					L2Clan clan = player.getClan();
					if ((clan.getLevel() < 5) || (clanpoints_vallue < 1))
					{
						player.sendMessage("You are not in clan with level 5, or you have enough killing points.");
						htmltext = "prizes.htm";
					}
					else
					{
						clan.addReputationScore(clanpoints_vallue, true);
						htmltext = "prizes.htm";
						st.set("points", "0");
					}
				}
				if (village == 5)
				{
					clanpoints_vallue = ((Yuki_Value - 3500) + (points * 2));
					L2Clan clan = player.getClan();
					if ((clan.getLevel() < 5) || (clanpoints_vallue < 1))
					{
						player.sendMessage("You are not in clan with level 5, or you have enough killing points.");
						htmltext = "prizes.htm";
					}
					else
					{
						clan.addReputationScore(clanpoints_vallue, true);
						htmltext = "prizes.htm";
						st.set("points", "0");
					}
				}
				if (village == 6)
				{
					clanpoints_vallue = ((Taki_Value - 3500) + (points * 2));
					L2Clan clan = player.getClan();
					if ((clan.getLevel() < 5) || (clanpoints_vallue < 1))
					{
						player.sendMessage("You are not in clan with level 5, or you have enough killing points.");
						htmltext = "prizes.htm";
					}
					else
					{
						clan.addReputationScore(clanpoints_vallue, true);
						htmltext = "prizes.htm";
						st.set("points", "0");
					}
				}
				if (village == 7)
				{
					clanpoints_vallue = ((Oto_Value - 3500) + (points * 2));
					L2Clan clan = player.getClan();
					if ((clan.getLevel() < 5) || (clanpoints_vallue < 1))
					{
						player.sendMessage("You are not in clan with level 5, or you have enough killing points.");
						htmltext = "prizes.htm";
					}
					else
					{
						clan.addReputationScore(clanpoints_vallue, true);
						htmltext = "prizes.htm";
						st.set("points", "0");
					}
				}
			}
		}
		if (event.equalsIgnoreCase("info"))
		{
			if (village == 1)
			{
				htmltext = "info.htm";
			}
			if (village == 2)
			{
				htmltext = "info.htm";
			}
			if (village == 3)
			{
				htmltext = "info.htm";
			}
			if (village == 4)
			{
				htmltext = "info.htm";
			}
			if (village == 5)
			{
				htmltext = "info.htm";
			}
			if (village == 6)
			{
				htmltext = "info.htm";
			}
			if (village == 7)
			{
				htmltext = "info.htm";
			}
		}
		if (event.equalsIgnoreCase("back"))
		{
			htmltext = "welcome.htm";
		}
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);
		}
		else
		{
			if (st.getState() != State.STARTED)
			{
				return null;
			}
		}
		if (!FunEvents.NA_STARTED)
		{
		}
		else
		{
			int village = st.getInt("village");
			int npcId = npc.getNpcId();
			int points = st.getInt("points");
			if (village == 1)
			{
				if ((npcId == Konoha_Ninja) && FunEvents.NA_STARTED)
				{
					points = points + 2;
					st.set("points", "" + points);
					Konoha_Points = Konoha_Points + 2;
					Suna_Points = Suna_Points - 1;
					Oto_Points = Oto_Points - 1;
				}
				if (contains(Konoha_Mobs, npcId) && FunEvents.NA_STARTED)
				{
					L2Attackable newNpc = (L2Attackable) st.addSpawn(Konoha_Ninja, 60000);
					L2Character originalAttacker = isPet ? player.getSummon() : player;
					newNpc.setRunning();
					newNpc.addDamageHate(originalAttacker, 0, 600);
					newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, originalAttacker);
				}
			}
			if (village == 2)
			{
				if ((npcId == Kusa_Ninja) && FunEvents.NA_STARTED)
				{
					points = points + 2;
					st.set("points", "" + points);
					Kusa_Points = Kusa_Points + 2;
					Taki_Points = Taki_Points - 1;
					Oto_Points = Oto_Points - 1;
				}
				if (contains(Kusa_Mobs, npcId) && FunEvents.NA_STARTED)
				{
					L2Attackable newNpc = (L2Attackable) st.addSpawn(Kusa_Ninja, 60000);
					L2Character originalAttacker = isPet ? player.getSummon() : player;
					newNpc.setRunning();
					newNpc.addDamageHate(originalAttacker, 0, 600);
					newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, originalAttacker);
				}
			}
			if (village == 3)
			{
				if ((npcId == Kiri_Ninja) && FunEvents.NA_STARTED)
				{
					points = points + 2;
					st.set("points", "" + points);
					Kiri_Points = Kiri_Points + 2;
					Kusa_Points = Kusa_Points - 1;
					Yuki_Points = Yuki_Points - 1;
				}
				if (contains(Kiri_Mobs, npcId) && FunEvents.NA_STARTED)
				{
					L2Attackable newNpc = (L2Attackable) st.addSpawn(Kiri_Ninja, 60000);
					L2Character originalAttacker = isPet ? player.getSummon() : player;
					newNpc.setRunning();
					newNpc.addDamageHate(originalAttacker, 0, 600);
					newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, originalAttacker);
				}
			}
			if (village == 4)
			{
				if ((npcId == Suna_Ninja) && FunEvents.NA_STARTED)
				{
					points = points + 2;
					st.set("points", "" + points);
					Suna_Points = Suna_Points + 2;
					Konoha_Points = Konoha_Points - 1;
					Taki_Points = Taki_Points - 1;
				}
				if (contains(Suna_Mobs, npcId) && FunEvents.NA_STARTED)
				{
					L2Attackable newNpc = (L2Attackable) st.addSpawn(Suna_Ninja, 60000);
					L2Character originalAttacker = isPet ? player.getSummon() : player;
					newNpc.setRunning();
					newNpc.addDamageHate(originalAttacker, 0, 600);
					newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, originalAttacker);
				}
			}
			if (village == 5)
			{
				if ((npcId == Yuki_Ninja) && FunEvents.NA_STARTED)
				{
					points = points + 2;
					st.set("points", "" + points);
					Yuki_Points = Yuki_Points + 2;
					Konoha_Points = Konoha_Points - 1;
					Kusa_Points = Kusa_Points - 1;
				}
				if (contains(Yuki_Mobs, npcId) && FunEvents.NA_STARTED)
				{
					L2Attackable newNpc = (L2Attackable) st.addSpawn(Yuki_Ninja, 60000);
					L2Character originalAttacker = isPet ? player.getSummon() : player;
					newNpc.setRunning();
					newNpc.addDamageHate(originalAttacker, 0, 600);
					newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, originalAttacker);
				}
			}
			if (village == 6)
			{
				if ((npcId == Taki_Ninja) && FunEvents.NA_STARTED)
				{
					points = points + 2;
					st.set("points", "" + points);
					Taki_Points = Taki_Points + 2;
					Suna_Points = Suna_Points - 1;
					Yuki_Points = Yuki_Points - 1;
				}
				if (contains(Taki_Mobs, npcId) && FunEvents.NA_STARTED)
				{
					L2Attackable newNpc = (L2Attackable) st.addSpawn(Taki_Ninja, 60000);
					L2Character originalAttacker = isPet ? player.getSummon() : player;
					newNpc.setRunning();
					newNpc.addDamageHate(originalAttacker, 0, 600);
					newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, originalAttacker);
				}
			}
			if (village == 7)
			{
				if ((npcId == Oto_Ninja) && FunEvents.NA_STARTED)
				{
					points = points + 2;
					st.set("points", "" + points);
					Oto_Points = Oto_Points + 2;
					Konoha_Points = Konoha_Points - 1;
					Kusa_Points = Kusa_Points - 1;
				}
				if (contains(Oto_Mobs, npcId) && FunEvents.NA_STARTED)
				{
					L2Attackable newNpc = (L2Attackable) st.addSpawn(Oto_Ninja, 60000);
					L2Character originalAttacker = isPet ? player.getSummon() : player;
					newNpc.setRunning();
					newNpc.addDamageHate(originalAttacker, 0, 600);
					newNpc.getAI().setIntention(CtrlIntention.AI_INTENTION_ATTACK, originalAttacker);
				}
			}
		}
		return super.onKill(npc, player, isPet);
	}
	
	public NinjaAdventures(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(Ninja_Master);
		addFirstTalkId(Ninja_Master);
		addTalkId(Ninja_Master);
		for (int id : Konoha_Mobs)
		{
			addKillId(id);
		}
		for (int id : Kusa_Mobs)
		{
			addKillId(id);
		}
		for (int id : Kiri_Mobs)
		{
			addKillId(id);
		}
		for (int id : Suna_Mobs)
		{
			addKillId(id);
		}
		for (int id : Yuki_Mobs)
		{
			addKillId(id);
		}
		for (int id : Taki_Mobs)
		{
			addKillId(id);
		}
		for (int id : Oto_Mobs)
		{
			addKillId(id);
		}
		addKillId(Konoha_Ninja);
		addKillId(Kusa_Ninja);
		addKillId(Kiri_Ninja);
		addKillId(Suna_Ninja);
		addKillId(Yuki_Ninja);
		addKillId(Taki_Ninja);
		addKillId(Oto_Ninja);
	}
	
	public static void main(String[] args)
	{
		new NinjaAdventures(-1, NinjaAdventures.class.getSimpleName(), "events");
		if (FunEvents.NA_STARTED)
		{
			_log.info("Event System: Ninja Adventures Event loaded ...");
		}
	}
}