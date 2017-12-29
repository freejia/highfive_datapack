package zones.GraciaContinents;

import java.util.Calendar;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;

/**
 * @author RobíkBobík
 */
public class Enira extends AbstractNpcAI
{
	private static final int ENIRA = 25625;
	
	public Enira(String name, String descr)
	{
		super(name, descr);
		
		eniraSpawn();
	}
	
	private void eniraSpawn()
	{
		Calendar _date = Calendar.getInstance();
		int newSecond = _date.get(13);
		int newMinute = _date.get(12);
		int newHour = _date.get(10);
		
		int targetHour = (24 - newHour) * 60 * 60 * 1000;
		int extraMinutesAndSeconds = (((60 - newMinute) * 60) + (60 - newSecond)) * 1000;
		int timerDuration = targetHour + extraMinutesAndSeconds;
		
		startQuestTimer("enira_spawn", timerDuration, null, null);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equalsIgnoreCase("enira_spawn"))
		{
			addSpawn(ENIRA, -181989, 208968, 4030, 0, false, 3600000L);
			eniraSpawn();
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Enira(Enira.class.getSimpleName(), "zones/GraciaContinents");
	}
}