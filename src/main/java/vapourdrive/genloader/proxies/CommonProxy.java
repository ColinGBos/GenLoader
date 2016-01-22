package vapourdrive.genloader.proxies;

import java.io.File;

import org.apache.logging.log4j.Level;

import vapourdrive.genloader.GenLoader;
import vapourdrive.genloader.api.GenLoaderAPI;
import vapourdrive.genloader.commands.ClearBlockCommand;
import vapourdrive.genloader.utils.BlockDump;
import vapourdrive.genloader.world.GenerationManager;
import vapourdrive.genloader.world.GL_WorldGenerator;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy
{
	public static File ConfigPath;
	
	public void preInit(FMLPreInitializationEvent event)
	{
		GenLoader.log.log(Level.INFO, "Staring PreInit");
		ConfigPath = event.getModConfigurationDirectory();
		new GenLoaderAPI();
	}

	public void init(FMLInitializationEvent event)
	{
		GenLoader.log.log(Level.INFO, "Staring Init");
		
	}

	public void postInit(FMLPostInitializationEvent event)
	{
		new GenerationManager(ConfigPath);
		new GL_WorldGenerator(ConfigPath);
	}

	public void fmlMapping(FMLModIdMappingEvent event)
	{
		BlockDump.init(ConfigPath);
	}

	public void serverLoad(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new ClearBlockCommand());
		
	}

}
