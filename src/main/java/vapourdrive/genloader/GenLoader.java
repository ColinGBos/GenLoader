package vapourdrive.genloader;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLModIdMappingEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import vapourdrive.genloader.proxies.CommonProxy;

@Mod(modid = ModInfo.ModID, version = ModInfo.Version)
public class GenLoader
{
	@Instance(ModInfo.ModID)
	public static GenLoader instance;
	
	@SidedProxy(clientSide = "vapourdrive.genloader.proxies.ClientProxy", serverSide = "vapourdrive.genloader.proxies.CommonProxy")
	public static CommonProxy proxy;
	public static final Logger log = LogManager.getLogger(ModInfo.ModID);
	
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.init(event);
    }
    
    @EventHandler
    public void posInit(FMLPostInitializationEvent event)
    {
    	proxy.postInit(event);
    }
    
    @EventHandler
    public void fmlMapping(FMLModIdMappingEvent event)
    {
    	proxy.fmlMapping(event);
    }
    
    @EventHandler
    public void serverLoad(FMLServerStartingEvent event)
    {
    	proxy.serverLoad(event);
    }
}
