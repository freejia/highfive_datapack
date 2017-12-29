package handlers.voicedcommandhandlers;

import java.util.StringTokenizer;
import java.util.logging.Level;

import com.l2jserver.gameserver.handler.IVoicedCommandHandler;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.effects.AbnormalEffect;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

public class Antibot implements IVoicedCommandHandler
{
	private static final String[] _voicedCommands =
	{
		"antibot"
	};
	
	@Override
	public boolean useVoicedCommand(String command, L2PcInstance activeChar, String target)
	{
		NpcHtmlMessage npcHtmlMessage = new NpcHtmlMessage(0);
		if (command.equalsIgnoreCase("antibot") && (target != null))
		{
			StringTokenizer st = new StringTokenizer(target);
			try
			{
				String newpass = null, repeatnewpass = null;
				if (st.hasMoreTokens())
				{
					newpass = st.nextToken();
				}
				repeatnewpass = activeChar.getCode();
				
				if (!((newpass == null) || (repeatnewpass == null)))
				{
					if (newpass.equals(repeatnewpass))// Right:)
					{
						npcHtmlMessage.setHtml("<html><title>Captcha Antibot System by L2PS</title><body><center><font color=\"00FF00\">Correct Captcha.<br><br></font><center><br><button value=\"Exit\" action=\"bypass -h npc_%objectId%_Quest\" width=45 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
						activeChar.sendPacket(npcHtmlMessage);
						activeChar.stopAbnormalEffect(AbnormalEffect.REAL_TARGET);
						activeChar.setIsInvul(false);
						activeChar.setIsParalyzed(false);
						activeChar.setKills(0);
						activeChar.setCodeRight(true);
						return false;
					}
					
				}
				if (!newpass.equals(repeatnewpass))// Wrong
				{
					npcHtmlMessage.setHtml("<html><title>Captcha Antibot System by L2PS</title><body><center><font color=\"FF0000\">Incorrect Captcha.<br><br></font><font color=\"66FF00\"><center></font><font color=\"FF0000\">You will be jailed for 1 min.</font><br><button value=\"Exit\" action=\"bypass -h npc_%objectId%_Quest\" width=45 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
					activeChar.sendPacket(npcHtmlMessage);
					if (activeChar.isFlyingMounted())
					{
						activeChar.untransform();
					}
					activeChar.setPunishLevel(L2PcInstance.PunishLevel.JAIL, 1);
					activeChar.stopAbnormalEffect(AbnormalEffect.REAL_TARGET);
					activeChar.setIsInvul(false);
					activeChar.setIsParalyzed(false);
					activeChar.sendPacket(npcHtmlMessage);
					activeChar.setCodeRight(true);
					return false;
				}
				else
				{
					npcHtmlMessage.setHtml("<html><title>Captcha Antibot System by L2PS</title><body><center><font color=\"FF0000\">Incorrect Captcha.<br><br></font><font color=\"66FF00\"><center></font><font color=\"FF0000\">You will be jailed for 1 min.</font><br><button value=\"Exit\" action=\"bypass -h npc_%objectId%_Quest\" width=45 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
					activeChar.sendPacket(npcHtmlMessage);
					if (activeChar.isFlyingMounted())
					{
						activeChar.untransform();
					}
					activeChar.setPunishLevel(L2PcInstance.PunishLevel.JAIL, 1);
					activeChar.stopAbnormalEffect(AbnormalEffect.REAL_TARGET);
					activeChar.setIsInvul(false);
					activeChar.setIsParalyzed(false);
					activeChar.sendPacket(npcHtmlMessage);
					activeChar.setCodeRight(true);
					return false;
				}
			}
			catch (Exception e)
			{
				activeChar.sendMessage("A problem occured while adding captcha!");
				_log.log(Level.WARNING, "", e);
			}
		}
		else
		{
			npcHtmlMessage.setHtml("<html><title>Captcha Antibot System by L2PS</title><body><center><font color=\"FF0000\">Incorrect Captcha.<br><br></font><font color=\"66FF00\"><center></font><font color=\"FF0000\">You will be jailed for 1 min.</font><br><button value=\"Exit\" action=\"bypass -h npc_%objectId%_Quest\" width=45 height=25 back=\"L2UI_CT1.Button_DF_Down\" fore=\"L2UI_ct1.button_df\"></center></body></html>");
			activeChar.sendPacket(npcHtmlMessage);
			if (activeChar.isFlyingMounted())
			{
				activeChar.untransform();
			}
			activeChar.setPunishLevel(L2PcInstance.PunishLevel.JAIL, 1);
			activeChar.stopAbnormalEffect(AbnormalEffect.REAL_TARGET);
			activeChar.setIsInvul(false);
			activeChar.setIsParalyzed(false);
			activeChar.sendPacket(npcHtmlMessage);
			activeChar.setCodeRight(true);
			return false;
		}
		return true;
	}
	
	@Override
	public String[] getVoicedCommandList()
	{
		return _voicedCommands;
	}
}