package vapourdrive.genloader.utils;

import java.util.Comparator;

import vapourdrive.genloader.api.generation.Generation;

public class GeneratorComarator implements Comparator<Generation>
{

	@Override
	public int compare(Generation gen1, Generation gen2)
	{
		if(gen1.getGeneratorPriority().ordinal() > gen2.getGeneratorPriority().ordinal())
		{
            return 1;
		}
		if(gen1.getGeneratorPriority().ordinal() < gen2.getGeneratorPriority().ordinal())
		{
            return -1;
		}
        return 0;
	}

}
