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
package events.TheValentineEvent;

import com.l2jserver.gameserver.model.Location;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * @author RobíkBobík
 */
public class TheValentineEvent extends Quest
{
	private static final int _npc = 4301;
	private static final int _recipe = 20191;
	
	private static final Location[] _spawns =
	{
		new Location(87792, -142240, -1343, 44000),
		new Location(87616, -140688, -1542, 16500),
		new Location(114748, -178656, -821, 0),
		new Location(115710, -182312, -1449, 0),
		new Location(-44332, -113624, -224, 0),
		new Location(-44588, -115378, -240, 22500),
		new Location(-13069, 122883, -3117, 0),
		new Location(-13996, 121862, -2988, 32768),
		new Location(-14882, 123723, -3117, 8192),
		new Location(-80762, 151118, -3043, 28672),
		new Location(-84005, 150171, -3129, 4096),
		new Location(-82581, 151647, -3129, 49152),
		new Location(-84507, 242933, -3730, 34000),
		new Location(-85976, 243255, -3730, 60000),
		new Location(11259, 15612, -4584, 25000),
		new Location(11290, 17791, -4574, 57344),
		new Location(47144, 49486, -3059, 32000),
		new Location(79810, 55512, -1560, 0),
		new Location(83325, 55883, -1525, 32768),
		new Location(80982, 54563, -1525, 32768),
		new Location(18221, 145122, -3054, 7400),
		new Location(19213, 144430, -3097, 32768),
		new Location(19409, 145776, -3086, 48000),
		new Location(17431, 170303, -3507, 30000),
		new Location(83326, 149212, -3405, 49152),
		new Location(82274, 148644, -3467, 0),
		new Location(81641, 148757, -3467, 32768),
		new Location(81688, 145705, -3533, 32768),
		new Location(117538, 76590, -2695, 38000),
		new Location(115894, 76411, -2711, 59000),
		new Location(119475, 77016, -2275, 40960),
		new Location(147057, 27404, -2192, 40960),
		new Location(147957, 25668, -2000, 16384),
		new Location(111685, 221113, -3543, 16384),
		new Location(107914, 218042, -3675, 0),
		new Location(114917, 219961, -3632, 32768),
		new Location(147845, -58039, -2979, 49000),
		new Location(147314, -56476, -2776, 11500),
		new Location(44171, -48777, -800, 33000),
		new Location(44264, -47645, -792, 50000),
		new Location(-116677, 46824, 360, 34828)
	};
	
	public TheValentineEvent(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(_npc);
		addFirstTalkId(_npc);
		addTalkId(_npc);
		for (Location loc : _spawns)
		{
			addSpawn(_npc, loc, false, 0);
		}
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return getNoQuestMsg(player);
		}
		
		String htmltext = event;
		if (event.equalsIgnoreCase("4301-3.htm"))
		{
			if (st.isCompleted())
			{
				htmltext = "4301-4.htm";
			}
			else
			{
				st.giveItems(_recipe, 1);
				st.playSound(QuestSound.ITEMSOUND_QUEST_ITEMGET);
				st.setState(State.COMPLETED);
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
		new TheValentineEvent(-1, TheValentineEvent.class.getSimpleName(), "events");
	}
}
