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
package zones.HeineArea.IsleOfPrayer.CrystalCaverns;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author RobíkBobík
 */
public class Kechi extends AbstractNpcAI
{
	private static final int KECHI = 25714;
	
	public int keshiStatus;
	
	public Kechi(int id, String name, String descr)
	{
		super(name, descr);
		addKillId(KECHI);
		addAttackId(KECHI);
		addSpawnId(KECHI);
	}
	
	@Override
	public String onSpawn(L2Npc npc)
	{
		if (npc.getNpcId() == KECHI)
		{
			keshiStatus = 0;
		}
		
		return super.onSpawn(npc);
	}
	
	private void SpawnMobs(L2Npc npc)
	{
		addSpawn(25533, 154184, 149230, -12151, 0, false, 0, false, npc.getInstanceId());
		addSpawn(25533, 153975, 149823, -12152, 0, false, 0, false, npc.getInstanceId());
		addSpawn(25533, 154364, 149665, -12151, 0, false, 0, false, npc.getInstanceId());
		addSpawn(25533, 153786, 149367, -12151, 0, false, 0, false, npc.getInstanceId());
		addSpawn(25533, 154188, 149825, -12152, 0, false, 0, false, npc.getInstanceId());
		addSpawn(25533, 153945, 149224, -12151, 0, false, 0, false, npc.getInstanceId());
		addSpawn(25533, 154374, 149399, -12152, 0, false, 0, false, npc.getInstanceId());
		addSpawn(25533, 153796, 149646, -12159, 0, false, 0, false, npc.getInstanceId());
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		if (npc.getNpcId() == KECHI)
		{
			final int maxHp = npc.getMaxHp();
			final double nowHp = npc.getStatus().getCurrentHp();
			
			// When 80%, 60%, 40%, 30%, 20%, 10% and 5% spawn minions
			switch (keshiStatus)
			{
				case 0:
					if (nowHp < (maxHp * 0.8))
					{
						keshiStatus = 1;
						SpawnMobs(npc);
						npc.broadcastNpcSay("Guards, lets kill them!");
					}
					break;
				case 1:
					if (nowHp < (maxHp * 0.6))
					{
						keshiStatus = 2;
						SpawnMobs(npc);
						npc.broadcastNpcSay("Guards, lets kill them!");
					}
					break;
				case 2:
					if (nowHp < (maxHp * 0.4))
					{
						keshiStatus = 3;
						SpawnMobs(npc);
						npc.broadcastNpcSay("Guards, safe me!");
					}
					break;
				case 3:
					if (nowHp < (maxHp * 0.3))
					{
						keshiStatus = 4;
						SpawnMobs(npc);
						npc.broadcastNpcSay("Guards, lets kill them!");
					}
					break;
				case 4:
					if (nowHp < (maxHp * 0.2))
					{
						keshiStatus = 5;
						SpawnMobs(npc);
						npc.broadcastNpcSay("Guards, safe me!");
					}
					break;
				case 5:
					if (nowHp < (maxHp * 0.1))
					{
						keshiStatus = 6;
						SpawnMobs(npc);
						npc.broadcastNpcSay("Guards, safe me!");
					}
					break;
				case 6:
					if (nowHp < (maxHp * 0.05))
					{
						keshiStatus = 7;
						SpawnMobs(npc);
						npc.broadcastNpcSay("Guards, safe me!");
					}
					break;
			}
		}
		
		return null;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		return super.onKill(npc, killer, isPet);
	}
	
	public static void main(String[] args)
	{
		new Kechi(-1, Kechi.class.getSimpleName(), "zones/HeineArea/IsleOfPrayer/");
	}
}
