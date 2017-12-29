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
package handlers.effecthandlers;

import com.l2jserver.gameserver.model.CharEffectList;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.EffectTemplate;
import com.l2jserver.gameserver.model.effects.L2Effect;
import com.l2jserver.gameserver.model.effects.L2EffectType;
import com.l2jserver.gameserver.model.stats.Env;
import com.l2jserver.gameserver.network.serverpackets.ExVoteSystemInfo;

public class EffectNevitHourglass extends L2Effect
{
	public EffectNevitHourglass(Env env, EffectTemplate template)
	{
		super(env, template);
	}
	
	@Override
	public L2EffectType getEffectType()
	{
		return L2EffectType.NEVIT_HOURGLASS;
	}
	
	@Override
	public boolean onStart()
	{
		if (getEffected() instanceof L2PcInstance)
		{
			L2PcInstance player = (L2PcInstance) getEffected();
			player.setRecomBonusType(1);
			player.sendPacket(new ExVoteSystemInfo(player));
			player.setSavedRecomBonusTime(player.getRecomBonusTime());
			player.stopRecoBonusTask();
		}
		return true;
	}
	
	@Override
	public void onExit()
	{
		if (getEffected() instanceof L2PcInstance)
		{
			L2PcInstance player = (L2PcInstance) getEffected();
			player.setRecomBonusType(0);
			player.startRecoBonusTask(player.getSavedRecomBonusTime() * 1000);
			player.sendPacket(new ExVoteSystemInfo(player));
		}
	}
	
	@Override
	public boolean onActionTime()
	{
		// Simply stop the effect
		return false;
	}
	
	@Override
	public int getEffectFlags()
	{
		return CharEffectList.EFFECT_NEVIT_HOURGLASS;
	}
}
