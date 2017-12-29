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
package events.TrickorTransmutation;

import com.l2jserver.FunEvents;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * @Fixed by L2Ps Team
 * www.l2ps.tode.cz
 */
public class TrickorTransmutation extends Quest
{
	private static final String qn = "TrickorTransmutation";
	private static final int SPECIAL_CHEST = 13036;
	private static final int[] EventMonsters = 
	{ 
		7000,7001,7002,7003,7004,7005,7006,7007,7008,7009,
		7010,7011,7012,7013,7014,7015,7016,7017,7018,7019,
		7020,7021,7022,7023
	};
	
	@Override
	public final String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(qn);
		if (st == null)
		{
			st = newQuestState(player);
			st.setState(State.STARTED);
		}
		String htmltext = "";
		if (FunEvents.TOT_STARTED)
		{
			htmltext = "start.htm";
		}
		else
		{
			htmltext = FunEvents.EVENT_DISABLED;
		}
		return htmltext;
	}
	@Override
	public final String onAdvEvent (String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);	
		}
		String htmltext = "";
		if (event.equalsIgnoreCase("key"))
		{
	           if(st.getQuestItemsCount(FunEvents.TOT_KEY) >= 1)
	           {
	                int n = Rnd.get(100);
	                st.takeItems(FunEvents.TOT_KEY,1);
	                htmltext = "<html><title>Alchemist Chest</title><body><center><img src=\"L2UI_CH3.herotower_deco\" width=256 height=32><br><tr><td><img src=\"L2UI.SquareWhite\" width=260 height=1></tr></td><br><font color=FF0000>Trick or Transmutation:</font></center><br>I think you know the letters? At the moment each monster has the chance to drop a key and you can collect the following Philosophers Stone to get some rewards: Red, Blue, Orange, Black, White and Green.<br1><center><table width=64><tr><td align=center><img src=\"icon.etc_old_key_i02\" width=32 height=32></td><td align=center><img src=\"icon.etc_sign_i00\" width=32 height=32></td></tr></table><br><tr><td><br>&nbsp;</td></tr><tr><td><button action=\"bypass -h Quest TrickorTransmutation key\" value=\"Open Chest\" back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\" width=65 height=20></td></tr><tr><td><img src=\"L2UI.SquareWhite\" width=260 height=1></tr></td><br></center></body></html>";
	                if (n > 0 && n <= 1)
	                {
	                    st.giveItems(FunEvents.TOT_RED_STONE,1);
	                }
	                else if (n > 1 && n <= 2)
	                {
	                    st.giveItems(FunEvents.TOT_BLUE_STONE,1);
	                }
	                else if (n > 2 && n <= 20)
	                {
	                    st.giveItems(FunEvents.TOT_ORANGE_STONE,1);
	                }
	                else if (n > 20 && n <= 30)
	                {
	                    st.giveItems(FunEvents.TOT_BLACK_STONE,1);
	                }
	                else if (n > 30 && n <= 50)
	                {
	                    st.giveItems(FunEvents.TOT_WHITE_STONE,1);
	                }
	                else if (n > 50 && n <= 70)
	                {
	                    st.giveItems(FunEvents.TOT_GREEN_STONE,1);
	                }
	                else if (n > 70 && n <= 100)
	                {
	                    int n2 = Rnd.get(3);
	                    if (n2 == 1)
	                    {
	                        st.giveItems(FunEvents.TOT_STONE_ORE,3);
	                    }
	                    else if (n2 == 2)
	                    {
	                        st.giveItems(FunEvents.TOT_STONE_FORMULA,3);
	                    }
	                    else
	                    {
	                        st.giveItems(FunEvents.TOT_MAGIC_REAGENTS,5);
	                    }
	                }
	           }
	           else
	           {
	        	   htmltext = "<html><body><font color=FF0000>Trick or Transmutation:</font><br> You don't have a <font color=LEVEL>Alchemist Key</font>.</body></html>";
	           }
		}
		if (event.equalsIgnoreCase("info"))
		{
			htmltext = "info.htm";
		}
		if (event.equalsIgnoreCase("back"))
		{
			htmltext = "start.htm";
		}
       	return htmltext;
	}
	@Override
	public final String onKill(L2Npc npc,L2PcInstance player, boolean isPet)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);	
		}
		int npcId = npc.getNpcId();
		if (FunEvents.TOT_ACTIVE_DROP)
		{
			for(int ID : EventMonsters)
			{ 
				if (npcId == ID)
				{
					st.giveItems(FunEvents.TOT_KEY, 1);
				}
			}			
		}
		return super.onKill(npc, player, isPet);
	}
	public TrickorTransmutation(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(SPECIAL_CHEST);
		addFirstTalkId(SPECIAL_CHEST);
		addTalkId(SPECIAL_CHEST);		
	}
	public static void main(String[] args)
	{
		new TrickorTransmutation(-1,qn,"events");
		if (FunEvents.TOT_STARTED)
		{
			_log.info("Event System: Trick or Transmutation Event loaded ...");
		}
	}
}