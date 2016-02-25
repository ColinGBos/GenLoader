package vapourdrive.genloader.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import vapourdrive.genloader.api.serializeable.IWeightedBlockState;
import vapourdrive.genloader.utils.IndexedWeightedBlockState;

import com.google.common.base.Predicate;

public class WeightedWorldGenMinable extends WorldGenerator
{
	private ArrayList<IndexedWeightedBlockState> indexedBlocks = new ArrayList<IndexedWeightedBlockState>();
	private int totalWeight = 0;
	private final int numberOfBlocks;
	private final Predicate<IBlockState> toReplace;
	
	public WeightedWorldGenMinable(IWeightedBlockState[] WeightedBlocks, int NumberOfBlocks, Predicate<IBlockState> ToReplace)
	{
		this.numberOfBlocks = NumberOfBlocks;
		this.toReplace = ToReplace;
		for (IWeightedBlockState weightedBlock : WeightedBlocks)
		{
			int BlockWeight = weightedBlock.getWeight();
			indexedBlocks.add(new IndexedWeightedBlockState(totalWeight, totalWeight + BlockWeight, weightedBlock.getState()));
			this.totalWeight = totalWeight + BlockWeight;
		}
	}
	
	public WeightedWorldGenMinable(IWeightedBlockState[] WeightedBlocks, int NumberOfBlocks)
	{
		this(WeightedBlocks, NumberOfBlocks, BlockHelper.forBlock(Blocks.stone));
	}
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position)
	{
		float f = rand.nextFloat() * (float)Math.PI;
        double d0 = (double)((float)(position.getX() + 8) + MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
        double d1 = (double)((float)(position.getX() + 8) - MathHelper.sin(f) * (float)this.numberOfBlocks / 8.0F);
        double d2 = (double)((float)(position.getZ() + 8) + MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
        double d3 = (double)((float)(position.getZ() + 8) - MathHelper.cos(f) * (float)this.numberOfBlocks / 8.0F);
        double d4 = (double)(position.getY() + rand.nextInt(3) - 2);
        double d5 = (double)(position.getY() + rand.nextInt(3) - 2);

        for (int i = 0; i < this.numberOfBlocks; ++i)
        {
            float f1 = (float)i / (float)this.numberOfBlocks;
            double d6 = d0 + (d1 - d0) * (double)f1;
            double d7 = d4 + (d5 - d4) * (double)f1;
            double d8 = d2 + (d3 - d2) * (double)f1;
            double d9 = rand.nextDouble() * (double)this.numberOfBlocks / 16.0D;
            double d10 = (double)(MathHelper.sin((float)Math.PI * f1) + 1.0F) * d9 + 1.0D;
            double d11 = (double)(MathHelper.sin((float)Math.PI * f1) + 1.0F) * d9 + 1.0D;
            int j = MathHelper.floor_double(d6 - d10 / 2.0D);
            int k = MathHelper.floor_double(d7 - d11 / 2.0D);
            int l = MathHelper.floor_double(d8 - d10 / 2.0D);
            int i1 = MathHelper.floor_double(d6 + d10 / 2.0D);
            int j1 = MathHelper.floor_double(d7 + d11 / 2.0D);
            int k1 = MathHelper.floor_double(d8 + d10 / 2.0D);

            for (int l1 = j; l1 <= i1; ++l1)
            {
                double d12 = ((double)l1 + 0.5D - d6) / (d10 / 2.0D);

                if (d12 * d12 < 1.0D)
                {
                    for (int i2 = k; i2 <= j1; ++i2)
                    {
                        double d13 = ((double)i2 + 0.5D - d7) / (d11 / 2.0D);

                        if (d12 * d12 + d13 * d13 < 1.0D)
                        {
                            for (int j2 = l; j2 <= k1; ++j2)
                            {
                                double d14 = ((double)j2 + 0.5D - d8) / (d10 / 2.0D);

                                if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D)
                                {
                                    BlockPos blockpos = new BlockPos(l1, i2, j2);

                                    if (worldIn.getBlockState(blockpos).getBlock().isReplaceableOreGen(worldIn, blockpos, this.toReplace) && (this.toReplace).apply(worldIn.getBlockState(blockpos)))
                                    {
                                        setBlock(worldIn, rand, blockpos);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
	}

	private boolean setBlock(World worldIn, Random rand, BlockPos blockpos)
	{
		if(indexedBlocks.size() == 1)
		{
			return worldIn.setBlockState(blockpos, indexedBlocks.get(0).getBlockState(), 2);
		}
		
		int number = rand.nextInt(totalWeight);
		Iterator<IndexedWeightedBlockState> iterator = indexedBlocks.iterator();
		while (iterator.hasNext())
		{
			IndexedWeightedBlockState indexedBlock = iterator.next();
			if(indexedBlock.inRange(number))
			{
				return worldIn.setBlockState(blockpos, indexedBlock.getBlockState(), 2);
			}
		}
		return false;
	}

}
