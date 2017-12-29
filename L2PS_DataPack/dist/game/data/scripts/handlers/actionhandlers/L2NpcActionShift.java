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
package handlers.actionhandlers;

import java.text.DecimalFormat;

import com.l2jserver.Config;
import com.l2jserver.gameserver.datatables.ItemTable;
import com.l2jserver.gameserver.handler.IActionHandler;
import com.l2jserver.gameserver.instancemanager.WalkingManager;
import com.l2jserver.gameserver.model.Elementals;
import com.l2jserver.gameserver.model.L2DropCategory;
import com.l2jserver.gameserver.model.L2DropData;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.L2Object.InstanceType;
import com.l2jserver.gameserver.model.actor.L2Attackable;
import com.l2jserver.gameserver.model.actor.L2Character;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2MerchantInstance;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.items.L2Item;
import com.l2jserver.gameserver.model.stats.BaseStats;
import com.l2jserver.gameserver.model.stats.Stats;
import com.l2jserver.gameserver.network.serverpackets.MyTargetSelected;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jserver.gameserver.network.serverpackets.StatusUpdate;
import com.l2jserver.util.StringUtil;

/**
 * @author RobíkBobík
 */
public class L2NpcActionShift implements IActionHandler
{
	@Override
	public boolean action(L2PcInstance activeChar, L2Object target, boolean interact)
	{
		// Check if the L2PcInstance is a GM
		if (activeChar.getAccessLevel().isGm())
		{
			// Set the target of the L2PcInstance activeChar
			activeChar.setTarget(target);
			
			// Send a Server->Client packet MyTargetSelected to the L2PcInstance activeChar
			// The activeChar.getLevel() - getLevel() permit to display the correct color in the select window
			MyTargetSelected my = new MyTargetSelected(target.getObjectId(), activeChar.getLevel() - ((L2Character) target).getLevel());
			activeChar.sendPacket(my);
			
			// Check if the activeChar is attackable (without a forced attack)
			if (target.isAutoAttackable(activeChar))
			{
				// Send a Server->Client packet StatusUpdate of the L2NpcInstance to the L2PcInstance to update its HP bar
				StatusUpdate su = new StatusUpdate(target);
				su.addAttribute(StatusUpdate.CUR_HP, (int) ((L2Character) target).getCurrentHp());
				su.addAttribute(StatusUpdate.MAX_HP, ((L2Character) target).getMaxHp());
				activeChar.sendPacket(su);
			}
			
			NpcHtmlMessage html = new NpcHtmlMessage(0);
			html.setFile(activeChar.getHtmlPrefix(), "data/html/admin/npcinfo.htm");
			
			html.replace("%objid%", String.valueOf(target.getObjectId()));
			html.replace("%class%", target.getClass().getSimpleName());
			html.replace("%id%", String.valueOf(((L2Npc) target).getTemplate().getNpcId()));
			html.replace("%lvl%", String.valueOf(((L2Npc) target).getTemplate().getLevel()));
			html.replace("%name%", String.valueOf(((L2Npc) target).getTemplate().getName()));
			html.replace("%tmplid%", String.valueOf(((L2Npc) target).getTemplate().getNpcId()));
			html.replace("%aggro%", String.valueOf((target instanceof L2Attackable) ? ((L2Attackable) target).getAggroRange() : 0));
			html.replace("%hp%", String.valueOf((int) ((L2Character) target).getCurrentHp()));
			html.replace("%hpmax%", String.valueOf(((L2Character) target).getMaxHp()));
			html.replace("%mp%", String.valueOf((int) ((L2Character) target).getCurrentMp()));
			html.replace("%mpmax%", String.valueOf(((L2Character) target).getMaxMp()));
			
			html.replace("%patk%", String.valueOf(((L2Character) target).getPAtk(null)));
			html.replace("%matk%", String.valueOf(((L2Character) target).getMAtk(null, null)));
			html.replace("%pdef%", String.valueOf(((L2Character) target).getPDef(null)));
			html.replace("%mdef%", String.valueOf(((L2Character) target).getMDef(null, null)));
			html.replace("%accu%", String.valueOf(((L2Character) target).getAccuracy()));
			html.replace("%evas%", String.valueOf(((L2Character) target).getEvasionRate(null)));
			html.replace("%crit%", String.valueOf(((L2Character) target).getCriticalHit(null, null)));
			html.replace("%rspd%", String.valueOf(((L2Character) target).getRunSpeed()));
			html.replace("%aspd%", String.valueOf(((L2Character) target).getPAtkSpd()));
			html.replace("%cspd%", String.valueOf(((L2Character) target).getMAtkSpd()));
			html.replace("%str%", String.valueOf(((L2Character) target).getSTR()));
			html.replace("%dex%", String.valueOf(((L2Character) target).getDEX()));
			html.replace("%con%", String.valueOf(((L2Character) target).getCON()));
			html.replace("%int%", String.valueOf(((L2Character) target).getINT()));
			html.replace("%wit%", String.valueOf(((L2Character) target).getWIT()));
			html.replace("%men%", String.valueOf(((L2Character) target).getMEN()));
			html.replace("%loc%", String.valueOf(target.getX() + " " + target.getY() + " " + target.getZ()));
			html.replace("%heading%", String.valueOf(((L2Character) target).getHeading()));
			html.replace("%collision_radius%", String.valueOf(((L2Character) target).getTemplate().getfCollisionRadius()));
			html.replace("%collision_height%", String.valueOf(((L2Character) target).getTemplate().getfCollisionHeight()));
			html.replace("%dist%", String.valueOf((int) Math.sqrt(activeChar.getDistanceSq(target))));
			
			byte attackAttribute = ((L2Character) target).getAttackElement();
			html.replace("%ele_atk%", Elementals.getElementName(attackAttribute));
			html.replace("%ele_atk_value%", String.valueOf(((L2Character) target).getAttackElementValue(attackAttribute)));
			html.replace("%ele_dfire%", String.valueOf(((L2Character) target).getDefenseElementValue(Elementals.FIRE)));
			html.replace("%ele_dwater%", String.valueOf(((L2Character) target).getDefenseElementValue(Elementals.WATER)));
			html.replace("%ele_dwind%", String.valueOf(((L2Character) target).getDefenseElementValue(Elementals.WIND)));
			html.replace("%ele_dearth%", String.valueOf(((L2Character) target).getDefenseElementValue(Elementals.EARTH)));
			html.replace("%ele_dholy%", String.valueOf(((L2Character) target).getDefenseElementValue(Elementals.HOLY)));
			html.replace("%ele_ddark%", String.valueOf(((L2Character) target).getDefenseElementValue(Elementals.DARK)));
			
			if (((L2Npc) target).getSpawn() != null)
			{
				html.replace("%spawn%", ((L2Npc) target).getSpawn().getLocx() + " " + ((L2Npc) target).getSpawn().getLocy() + " " + ((L2Npc) target).getSpawn().getLocz());
				html.replace("%loc2d%", String.valueOf((int) Math.sqrt(((L2Character) target).getPlanDistanceSq(((L2Npc) target).getSpawn().getLocx(), ((L2Npc) target).getSpawn().getLocy()))));
				html.replace("%loc3d%", String.valueOf((int) Math.sqrt(((L2Character) target).getDistanceSq(((L2Npc) target).getSpawn().getLocx(), ((L2Npc) target).getSpawn().getLocy(), ((L2Npc) target).getSpawn().getLocz()))));
				if (((L2Npc) target).getSpawn().getRespawnMinDelay() == 0)
				{
					html.replace("%resp%", "None");
				}
				else if (((L2Npc) target).getSpawn().hasRespawnRandom())
				{
					html.replace("%resp%", String.valueOf(((L2Npc) target).getSpawn().getRespawnMinDelay() / 1000) + "-" + String.valueOf((((L2Npc) target).getSpawn().getRespawnMaxDelay() / 1000) + " sec"));
				}
				else
				{
					html.replace("%resp%", String.valueOf(((L2Npc) target).getSpawn().getRespawnMinDelay() / 1000) + " sec");
				}
			}
			else
			{
				html.replace("%spawn%", "<font color=FF0000>null</font>");
				html.replace("%loc2d%", "<font color=FF0000>--</font>");
				html.replace("%loc3d%", "<font color=FF0000>--</font>");
				html.replace("%resp%", "<font color=FF0000>--</font>");
			}
			
			if (((L2Npc) target).hasAI())
			{
				html.replace("%ai_intention%", "<tr><td><table width=270 border=0 bgcolor=131210><tr><td width=100><font color=FFAA00>Intention:</font></td><td align=right width=170>" + String.valueOf(((L2Npc) target).getAI().getIntention().name()) + "</td></tr></table></td></tr>");
				html.replace("%ai%", "<tr><td><table width=270 border=0><tr><td width=100><font color=FFAA00>AI</font></td><td align=right width=170>" + ((L2Npc) target).getAI().getClass().getSimpleName() + "</td></tr></table></td></tr>");
				html.replace("%ai_type%", "<tr><td><table width=270 border=0 bgcolor=131210><tr><td width=100><font color=FFAA00>AIType</font></td><td align=right width=170>" + String.valueOf(((L2Npc) target).getAiType()) + "</td></tr></table></td></tr>");
				html.replace("%ai_clan%", "<tr><td><table width=270 border=0><tr><td width=100><font color=FFAA00>Clan & Range:</font></td><td align=right width=170>" + String.valueOf(((L2Npc) target).getTemplate().getAIDataStatic().getClan()) + " " + String.valueOf(((L2Npc) target).getTemplate().getAIDataStatic().getClanRange()) + "</td></tr></table></td></tr>");
				html.replace("%ai_enemy_clan%", "<tr><td><table width=270 border=0 bgcolor=131210><tr><td width=100><font color=FFAA00>Enemy & Range:</font></td><td align=right width=170>" + String.valueOf(((L2Npc) target).getTemplate().getAIDataStatic().getEnemyClan()) + " " + String.valueOf(((L2Npc) target).getTemplate().getAIDataStatic().getEnemyRange()) + "</td></tr></table></td></tr>");
			}
			else
			{
				html.replace("%ai_intention%", "");
				html.replace("%ai%", "");
				html.replace("%ai_type%", "");
				html.replace("%ai_clan%", "");
				html.replace("%ai_enemy_clan%", "");
			}
			
			final String routeName = WalkingManager.getInstance().getRouteName((L2Npc) target);
			if (!routeName.isEmpty())
			{
				html.replace("%route%", "<tr><td><table width=270 border=0><tr><td width=100><font color=LEVEL>Route:</font></td><td align=right width=170>" + routeName + "</td></tr></table></td></tr>");
			}
			else
			{
				html.replace("%route%", "");
			}
			
			if (target instanceof L2MerchantInstance)
			{
				html.replace("%butt%", "<button value=\"Shop\" action=\"bypass -h admin_showShop " + String.valueOf(((L2Npc) target).getTemplate().getNpcId()) + "\" width=60 height=21 back=\"L2UI_ct1.button_df\" fore=\"L2UI_ct1.button_df\">");
			}
			else
			{
				html.replace("%butt%", "");
			}
			
			activeChar.sendPacket(html);
		}
		else if (Config.ALT_GAME_VIEWNPC)
		{
			// Set the target of the L2PcInstance activeChar
			activeChar.setTarget(target);
			
			// Send a Server->Client packet MyTargetSelected to the L2PcInstance activeChar
			// The activeChar.getLevel() - getLevel() permit to display the correct color in the select window
			MyTargetSelected my = new MyTargetSelected(target.getObjectId(), activeChar.getLevel() - ((L2Character) target).getLevel());
			activeChar.sendPacket(my);
			
			// Check if the activeChar is attackable (without a forced attack)
			if (target.isAutoAttackable(activeChar))
			{
				// Send a Server->Client packet StatusUpdate of the L2NpcInstance to the L2PcInstance to update its HP bar
				StatusUpdate su = new StatusUpdate(target);
				su.addAttribute(StatusUpdate.CUR_HP, (int) ((L2Character) target).getCurrentHp());
				su.addAttribute(StatusUpdate.MAX_HP, ((L2Character) target).getMaxHp());
				activeChar.sendPacket(su);
			}
			
			NpcHtmlMessage html = new NpcHtmlMessage(0);
			int hpMul = Math.round((float) (((L2Character) target).getStat().calcStat(Stats.MAX_HP, 1, (L2Character) target, null) / BaseStats.CON.calcBonus((L2Character) target)));
			if (hpMul == 0)
			{
				hpMul = 1;
			}
			final StringBuilder html1 = StringUtil.startAppend(1000, "<html><body>" + "<br><center><font color=\"LEVEL\">[ " + target.getName() + " ]</font></center><br>");
			
			if (!((L2Npc) target).getTemplate().getDropData().isEmpty())
			{
				StringUtil.append(html1, "<br><center><font color=\"LEVEL\">[Drop Informations]</font><br>Calculation with 1x rate</center>" + "<center><br>Colors: <font color=\"9933FF\">Spoil</font> - <font color=\"66FFCC\">Drop</font> - <font color=\"FF9900\">Quest</font></center>" + "<table border=0 width=\"100%\">");
				for (L2DropCategory cat : ((L2Npc) target).getTemplate().getDropData())
				{
					for (L2DropData drop : cat.getAllDrops())
					{
						final L2Item item = ItemTable.getInstance().getTemplate(drop.getItemId());
						if (item == null)
						{
							continue;
						}
						
						final String color;
						if (cat.isSweep())
						{
							color = "9933FF";
						}
						else if (item.isQuestItem())
						{
							color = "FF9900";
						}
						else
						{
							color = "66FFCC";
						}
						String getChanceFinal = new DecimalFormat("0.0000").format((drop.getChance() / 10000) + 0.1335);// TODO: FIXED rate to 2-4
						StringUtil.append(html1, "<tr>", "<td><center><img src=\"" + item.getIcon() + "\" height=32 width=32></center></td></tr>" + "<tr><td><center><font color=\"", color, "\">", item.getName() + "</font></td></tr>" + "<tr><td><center><font color=\"66FF66\"> Chance: " + getChanceFinal + "%", "</font></td></tr>");
					}
				}
				html1.append("</table>");
			}
			html1.append("</body></html>");
			
			html.setHtml(html1.toString());
			activeChar.sendPacket(html);
		}
		return true;
	}
	
	@Override
	public InstanceType getInstanceType()
	{
		return InstanceType.L2Npc;
	}
}