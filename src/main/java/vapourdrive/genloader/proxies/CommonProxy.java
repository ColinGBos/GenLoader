package vapourdrive.genloader.proxies;

import java.io.File;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import org.apache.logging.log4j.Level;

import vapourdrive.genloader.api.GenLoaderAPI;
import vapourdrive.genloader.commands.ClearBlockCommand;
import vapourdrive.genloader.config.ConfigManager;
import vapourdrive.genloader.events.EventManager;
import vapourdrive.genloader.utils.BlockDump;
import vapourdrive.genloader.world.GL_WorldGenerator;
import vapourdrive.genloader.world.GenerationManager;

public class CommonProxy
{
	public static File ConfigPath;
	
	public void preInit(FMLPreInitializationEvent event)
	{
		GenLoaderAPI.log.log(Level.INFO, "Staring PreInit");
		ConfigPath = event.getModConfigurationDirectory();
		new GenLoaderAPI();
		ConfigManager.preInit(ConfigPath);
	}

	public void init(FMLInitializationEvent event)
	{
		GenLoaderAPI.log.log(Level.INFO, "Staring Init");
		EventManager.init();
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
