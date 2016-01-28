package vapourdrive.genloader.events;

import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vapourdrive.genloader.config.ConfigOptions;

public class VanillaGenDisabler
{
	@SubscribeEvent
	public void genDisabler(OreGenEvent.GenerateMinable event)
	{
		EventType type = event.type;
		if (type == EventType.COAL || type == EventType.DIAMOND || type == EventType.GOLD || type == EventType.IRON
				|| type == EventType.LAPIS || type == EventType.QUARTZ || type == EventType.REDSTONE || type == EventType.EMERALD)
		{
			if(ConfigOptions.disableVanillaOres)
			{
				event.setResult(Result.DENY);
			}
		}
		
		else if (type == EventType.ANDESITE || type == EventType.DIORITE || type == EventType.GRANITE || type == EventType.GRAVEL || type == EventType.DIRT)
		{
			if(ConfigOptions.disableVanillaOthers)
			{
				event.setResult(Result.DENY);
			}
		}
	}
}
