package vapourdrive.genloader.world;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.registry.GameRegistry;
import vapourdrive.genloader.api.EnumGenerationType;
import vapourdrive.genloader.api.Generation;
import vapourdrive.genloader.utils.IBlockStateHelper;

public class GL_WorldGenerator implements IWorldGenerator
{
	public GL_WorldGenerator(File configPath)
	{
		GameRegistry.registerWorldGenerator(this, 0);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		Iterator<Generation> iterator = GenerationManager.finalGenerators.iterator();
		while (iterator.hasNext())
		{
			Generation generation = iterator.next();
			if (generation.getDimensions().contains(world.provider.getDimensionName()))
			{
				if (generation.getBiomeTypes().isEmpty() || isBiomeofType(world, chunkX, chunkZ, generation.getBiomeTypes()))
				{
					if (generation.getGeneratorType() == EnumGenerationType.WEIGHTEDSTANDARDCLUSTER)
					{
						IBlockState toReplace = generation.getBlockToReplace();
						WeightedWorldGenMinable generator = new WeightedWorldGenMinable(generation.getWeightedBlocks(),
								generation.getSize(), new IBlockStateHelper(toReplace));
						generateStandardOre(random, chunkX, chunkZ, world, generation.getFrequency(), generator, generation.getMinY(),
								generation.getMaxY());
					}
				}
			}
		}
	}

	private boolean isBiomeofType(World world, int chunkX, int chunkZ, ArrayList<Type> biomes)
	{
		for (Type type : BiomeDictionary.getTypesForBiome(world.getBiomeGenForCoords(new BlockPos(chunkX * 16, 0, chunkZ * 16))))
		{
			if (biomes.contains(type))
			{
				return true;
			}
		}
		return false;
	}

	private void generateStandardOre(Random rand, int chunkX, int chunkZ, World world, int iterations, WorldGenerator gen, int minY,
			int maxY)
	{
		for (int i = 0; i < iterations; i++)
		{
			int x = chunkX * 16 + rand.nextInt(16);
			int y = rand.nextInt(maxY - minY) + minY;
			int z = chunkZ * 16 + rand.nextInt(16);

			gen.generate(world, rand, new BlockPos(x, y, z));
		}
	}
}
