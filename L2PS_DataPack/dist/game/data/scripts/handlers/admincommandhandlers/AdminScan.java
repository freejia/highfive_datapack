/*
 * Copyright (C) 2004-2014 L2J DataPack
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
package handlers.admincommandhandlers;

import java.util.StringTokenizer;

import com.l2jserver.gameserver.datatables.SpawnTable;
import com.l2jserver.gameserver.handler.ActionShiftHandler;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.instancemanager.RaidBossSpawnManager;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Object.InstanceType;
import com.l2jserver.gameserver.model.L2Spawn;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.util.Util;

/**
 * @author UnAfraid (.js)
 * @author Nos
 * @author JOJO (.java)
 */
public class AdminScan implements IAdminCommandHandler
{
	@Override
	public String[] getAdminCommandList()
	{
		return new String[]
		{
			"admin_scan",
			"admin_deleteNpcByObjectId",
			"admin_targetNpcByObjectId",
			"admin_shiftTargetNpcByObjectId",
		};
	}

	private static final int DEFAULT_RADIUS = 500;

	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		final StringTokenizer st = new StringTokenizer(command, " ");
		final String actualCommand = st.nextToken();
		switch (actualCommand)
		{
			case "admin_scan":
			{
				int radius = DEFAULT_RADIUS;
				if (st.hasMoreElements())
				{
					try
					{
						radius = Integer.parseInt(st.nextToken());
					}
					catch (NumberFormatException e)
					{
						activeChar.sendMessage("Usage: //scan [radius]");
						return false;
					}
				}

				sendNpcList(activeChar, radius);
				break;
			}
			case "admin_deleteNpcByObjectId":
			{
				L2Npc npc = nextTokenNpc(st, activeChar, actualCommand);
				if (npc == null)
				{
					return false;
				}

				npc.deleteMe();

				final L2Spawn spawn = npc.getSpawn();
				if (spawn != null)
				{
					spawn.stopRespawn();

					if (RaidBossSpawnManager.getInstance().isDefined(spawn.getNpcid()))
					{
						RaidBossSpawnManager.getInstance().deleteSpawn(spawn, true);
					}
					else
					{
						SpawnTable.getInstance().deleteSpawn(spawn, true);
					}
				}
				activeChar.sendMessage(npc.getName() + " have been deleted.");

				String radius = st.hasMoreElements() ? " " + st.nextToken() : "";
				useAdminCommand("admin_scan" + radius, activeChar);
				break;
			}
			case "admin_targetNpcByObjectId":
			{
				L2Npc npc = nextTokenNpc(st, activeChar, actualCommand);
				if (npc == null)
				{
					return false;
				}

				activeChar.setTarget(npc);
				break;
			}
			case "admin_shiftTargetNpcByObjectId":
			{
				L2Npc npc = nextTokenNpc(st, activeChar, actualCommand);
				if (npc == null)
				{
					return false;
				}

				activeChar.setTarget(npc);
				ActionShiftHandler.getInstance().getHandler(InstanceType.L2Npc).action(activeChar, npc, false);
				break;
			}
		}
		return true;
	}

	private L2Npc nextTokenNpc(StringTokenizer st, L2PcInstance activeChar, String actualCommand)
	{
		if (!st.hasMoreElements())
		{
			activeChar.sendMessage("Usage: //" + actualCommand.substring(5) + " <object_id>");
			return null;
		}

		final int objectId;
		try
		{
			objectId = Integer.parseInt(st.nextToken());
		}
		catch (NumberFormatException e)
		{
			activeChar.sendMessage("object_id must be a number.");
			return null;
		}

		L2Object target = L2World.getInstance().findObject(objectId);
		if (!(target instanceof L2Npc))
		{
			activeChar.sendMessage("NPC does not exist or object_id does not belong to an NPC");
			return null;
		}
		return (L2Npc) target;
	}

	private void sendNpcList(L2PcInstance activeChar, int radius)
	{
		final NpcHtmlMessage html = new NpcHtmlMessage(0, 1);
		html.setFile(activeChar.getHtmlPrefix(), "data/html/admin/scan.htm");
		final StringBuilder sb = new StringBuilder();
		for (L2Object o : activeChar.getKnownList().getKnownObjects().values())
		{
			if ((o instanceof L2Npc) && Util.checkIfInRange(radius, activeChar, o, true))
			{
				L2Npc character = (L2Npc) o;
				sb.append("<tr>");
				sb.append("<td width=30>" + character.getNpcId() + "</td>");
				sb.append("<td>" + character.getName() + "</td>");
				sb.append("<td width=30 align=right>" + Math.round(Util.calculateDistance(activeChar, character, false)) + "</td>");
				sb.append("<td width=8 align=center><a action=\"bypass -h admin_deleteNpcByObjectId " + character.getObjectId() + " " + radius + "\"><font color=LEVEL>D</font></a></td>");
				sb.append("<td width=8 align=center><a action=\"bypass -h admin_move_to " + character.getX() + " " + character.getY() + " " + character.getZ() + "\"><font color=LEVEL>G</font></a></td>");
				sb.append("<td width=8 align=center><a action=\"bypass -h admin_targetNpcByObjectId " + character.getObjectId() + "\"><font color=LEVEL>T</font></a></td>");
				sb.append("<td width=8 align=center><a action=\"bypass -h admin_shiftTargetNpcByObjectId " + character.getObjectId() + "\"><font color=LEVEL>S</font></a></td>");
				sb.append("</tr>");
			}
		}
		html.replace("%radius%", radius);
		html.replace("%data%", sb.toString());
		activeChar.sendPacket(html);
	}
}