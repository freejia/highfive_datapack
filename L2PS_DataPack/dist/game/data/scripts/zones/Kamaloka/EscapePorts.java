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
package zones.Kamaloka;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author RobíkBobík
 */
public class EscapePorts extends AbstractNpcAI
{
	// TODO: USE int[] :D Later I will use, now I don't have much time
	private static final int BOSS_1 = 18554;
	private static final int BOSS_2 = 18555;
	private static final int BOSS_3 = 29129;
	private static final int BOSS_4 = 18558;
	private static final int BOSS_5 = 18559;
	private static final int BOSS_6 = 29132;
	private static final int BOSS_7 = 18562;
	private static final int BOSS_8 = 18564;
	private static final int BOSS_9 = 29135;
	private static final int BOSS_10 = 18566;
	private static final int BOSS_11 = 18568;
	private static final int BOSS_12 = 29138;
	private static final int BOSS_13 = 18571;
	private static final int BOSS_14 = 18573;
	private static final int BOSS_15 = 29141;
	private static final int BOSS_16 = 18577;
	private static final int BOSS_17 = 29144;
	private static final int BOSS_18 = 29147;
	private static final int BOSS_19 = 25710;
	
	private static final int ESCAPE_PORT = 32496;
	private static final int DESPAWN_DELAY = 300000; // 5min
	
	public EscapePorts(String name, String descr)
	{
		super(name, descr);
		addKillId(BOSS_1, BOSS_2, BOSS_3, BOSS_4, BOSS_5, BOSS_6, BOSS_7, BOSS_8, BOSS_9, BOSS_10, BOSS_11, BOSS_12, BOSS_13, BOSS_14, BOSS_15, BOSS_16, BOSS_17, BOSS_18, BOSS_19);
		addAttackId(BOSS_1, BOSS_2, BOSS_3, BOSS_4, BOSS_5, BOSS_6, BOSS_7, BOSS_8, BOSS_9, BOSS_10, BOSS_11, BOSS_12, BOSS_13, BOSS_14, BOSS_15, BOSS_16, BOSS_17, BOSS_18, BOSS_19);
		addTalkId(ESCAPE_PORT);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		switch (npc.getNpcId())
		{
			case BOSS_1:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_2:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_3:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_4:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_5:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_6:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_7:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_8:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_9:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_10:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_11:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_12:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_13:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_14:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_15:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_16:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_17:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_18:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
			case BOSS_19:
				addSpawn(ESCAPE_PORT, npc.getX() + getRandom(-50, 50), npc.getY() + getRandom(-50, 50), npc.getZ() + 10, 0, false, DESPAWN_DELAY, true, npc.getInstanceId());
				break;
		}
		return super.onKill(npc, player, isPet);
	}
	
	public static void main(String[] args)
	{
		new EscapePorts(EscapePorts.class.getSimpleName(), "zones/Kamaloka/");
	}
}
