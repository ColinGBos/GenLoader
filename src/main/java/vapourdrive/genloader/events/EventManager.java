package vapourdrive.genloader.events;

import net.minecraftforge.common.MinecraftForge;

public class EventManager
{
	public static void init()
	{
		MinecraftForge.ORE_GEN_BUS.register(new VanillaGenDisabler());
	}
}
