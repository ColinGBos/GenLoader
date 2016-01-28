package vapourdrive.genloader.api.generation;

public enum EnumGenerationPriority
{
	/**
	 * This determines the priority of when your generation will generate. The later your priority, the less stone their is to be replaced
	 * If you want to get in before the dirt, gravel and stone variants (when using my override), set the priority to EARLIEST, semi precious ores 
	 * I have set to LATE in the vanilla module, and anything that is very common (iron, coal) is set to LATER. 
	 */
	EARLIEST, EARLIER, EARLY, LATE, LATER, LATEST
}
