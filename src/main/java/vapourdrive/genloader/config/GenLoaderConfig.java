package vapourdrive.genloader.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class GenLoaderConfig
{
	public static Configuration config;
	public static String CatEnable = "Enable";
	public static String CatVanilla = "Vanilla";
	
	public static void preInit(File file)
	{
		config = new Configuration(file);
		config.load();
		
		ConfigOptions.disableVanillaOres = config.getBoolean("Override Vanilla Ores", CatEnable + "." + CatVanilla, false, ConfigOptions.disableVanillaOresComment);
		ConfigOptions.disableVanillaOthers = config.getBoolean("Override Vanilla Others", CatEnable + "." + CatVanilla, false, ConfigOptions.disableVanillaOthersComment);
		
		config.save();
	}

}
