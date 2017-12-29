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
package handlers.voicedcommandhandlers;

import com.l2jserver.gameserver.handler.IVoicedCommandHandler;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.ExShowScreenMessage;

/**
 * @author RobíkBobík 19.8.2013 11:44
 */
public class Voiced_Experience implements IVoicedCommandHandler
{
	private final String[] _voicedCommands =
	{
		"expon",
		"xpon",
		"expoff",
		"xpoff",
		"spon",
		"spoff"
	};
	
	@Override
	public boolean useVoicedCommand(final String command, final L2PcInstance activeChar, final String target)
	{
		if (command.equalsIgnoreCase("expon") || command.equalsIgnoreCase("xpon"))
		{
			activeChar.setExpDisabled(false);
			activeChar.stopSpecialEffect(0x000010);
			activeChar.sendPacket(new ExShowScreenMessage("Experience points enabled", 5000));
		}
		else if (command.equalsIgnoreCase("expoff") || command.equalsIgnoreCase("xpoff"))
		{
			activeChar.setExpDisabled(true);
			activeChar.startSpecialEffect(0x000010);
			activeChar.sendPacket(new ExShowScreenMessage("Experience points disabled", 5000));
		}
		
		else if (command.equalsIgnoreCase("spon"))
		{
			activeChar.setSpDisabled(false);
			activeChar.stopSpecialEffect(0x000020);
			activeChar.sendPacket(new ExShowScreenMessage("Spell points enabled", 5000));
		}
		else if (command.equalsIgnoreCase("spoff"))
		{
			activeChar.setSpDisabled(true);
			activeChar.startSpecialEffect(0x000020);
			activeChar.sendPacket(new ExShowScreenMessage("Spell points disabled", 5000));
		}
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return _voicedCommands;
	}
}