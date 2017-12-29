/*
 * Copyright (C) 2004-2013 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package zones.AdenArea;

import java.util.Collection;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.GeoData;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.NpcStringId;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.NpcSay;
import com.l2jserver.gameserver.util.Util;

/**
 * Giant Scouts AI.
 * @author Gnacik
 */
public final class GiantScouts extends AbstractNpcAI
{
	private static final int[] SCOUTS =
	{
		22668, // Gamlin (Scout)
		22669, // Leogul (Scout)
	};
	
	private GiantScouts()
	{
		super(GiantScouts.class.getSimpleName(), "zones/AdenArea/");
		addAttackId(SCOUTS);
		addSeeCreatureId(SCOUTS);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("ATTACK") && (npc != null) && !npc.isDead())
		{
			if (npc.getNpcId() == SCOUTS[1]) // Gamlin
			{
				npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_SHOUT, npc.getNpcId(), NpcStringId._INTRUDER_DETECTED));
			}
			else
			{
				npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_SHOUT, npc.getNpcId(), NpcStringId.OH_GIANTS_AN_INTRUDER_HAS_BEEN_DISCOVERED));
			}
			
			attackPlayer((L2Attackable) npc, player);
			Collection<L2Object> objs = npc.getKnownList().getKnownObjects().values();
			for (L2Object obj : objs)
			{
				if ((obj != null) && (obj instanceof L2Attackable) && Util.checkIfInRange(450, player, obj, true) && GeoData.getInstance().canSeeTarget(npc, obj) && (getRandomBoolean()))
				
				{
					L2Attackable monster = (L2Attackable) obj;
					monster.setTarget(player);
					attackPlayer(monster, player);
					
				}
			}
		}
		else if (event.equals("CLEAR") && (npc != null) && !npc.isDead())
		{
			npc.setScriptValue(0);
		}
		return super.onAdvEvent(event, npc, player);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isSummon)
	{
		if (npc.isScriptValue(0))
		{
			npc.setScriptValue(1);
			startQuestTimer("ATTACK", 6000, npc, null);
			startQuestTimer("CLEAR", 120000, npc, null);
		}
		return super.onAttack(npc, attacker, damage, isSummon);
	}
	
	@Override
	public String onSeeCreature(L2Npc npc, L2Character creature, boolean isSummon)
	{
		if (creature.isPlayer() && npc.isScriptValue(0))
		{
			npc.setScriptValue(1);
			if (getRandomBoolean())
			{
				npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getNpcId(), NpcStringId.YOU_GUYS_ARE_DETECTED));
			}
			else
			{
				npc.broadcastPacket(new NpcSay(npc.getObjectId(), Say2.NPC_ALL, npc.getNpcId(), NpcStringId.WHAT_KIND_OF_CREATURES_ARE_YOU));
			}
			startQuestTimer("ATTACK", 6000, npc, null);
			startQuestTimer("CLEAR", 120000, npc, null);
		}
		return super.onSeeCreature(npc, creature, isSummon);
	}
	
	public static void main(String[] args)
	{
		new GiantScouts();
	}
}