package vapourdrive.genloader.api.generation;

public class GenerationCategory
{
	private final String name;
	private final boolean defaultEnabled;
	
	public GenerationCategory(String Name)
	{
		this.name = Name;
		this.defaultEnabled = true;
	}
	
	public GenerationCategory(String Name, boolean DefaultEnabled)
	{
		this.name = Name;
		this.defaultEnabled = DefaultEnabled;
	}
	
	public String getCategoryName()
	{
		return this.name;
	}
	
	public boolean getIsDefaultEnabled()
	{
		return this.defaultEnabled;
	}
}
