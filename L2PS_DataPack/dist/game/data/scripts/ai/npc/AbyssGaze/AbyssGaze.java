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
package ai.npc.AbyssGaze;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.instancemanager.gracia.SoIManager;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * @Author: RobikBobik L2PS Team
 */
public class AbyssGaze extends AbstractNpcAI
{
	public AbyssGaze(String name, String desc)
	{
		super(name, desc);
		
		addStartNpc(32540);
		addFirstTalkId(32540);
		addTalkId(32540);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);
		}
		
		if (event.equalsIgnoreCase("request_permission"))
		{
			if ((SoIManager.getCurrentStage() == 2) || (SoIManager.getCurrentStage() == 5))
			{
				htmltext = "32540-2.htm";
			}
			else if ((SoIManager.getCurrentStage() == 3) && SoIManager.isSeedOpen())
			{
				htmltext = "32540-3.htm";
			}
			else
			{
				htmltext = "32540-1.htm";
			}
		}
		else if (event.equalsIgnoreCase("enter_seed"))
		{
			if (SoIManager.getCurrentStage() == 3)
			{
				SoIManager.teleportInSeed(player);
				return null;
			}
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			st = newQuestState(player);
		}
		
		if (npc.getNpcId() == 32540)
		{
			return "32540.htm";
		}
		return "";
	}
	
	public static void main(String[] args)
	{
		new AbyssGaze(AbyssGaze.class.getSimpleName(), "ai/npc");
	}
}