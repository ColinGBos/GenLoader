package vapourdrive.genloader.config;

import java.io.File;

public class ConfigManager
{
	public static void preInit(File ConfigPath)
	{
		GenLoaderConfig.preInit(new File(ConfigPath + "/genloader/config/GenLoaderMain.cfg"));
	}
}
