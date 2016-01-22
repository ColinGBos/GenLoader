package vapourdrive.genloader.utils;

import net.minecraft.block.state.IBlockState;

public class IndexedWeightedBlockState
{
	private final int minThreshhold;
	private final int maxThreshold;
	private final IBlockState blockstate;
	
	public IndexedWeightedBlockState(int Min, int Max, IBlockState State)
	{
		this.minThreshhold = Min;
		this.maxThreshold = Max;
		this.blockstate = State;
	}
	
	public IBlockState getBlockState()
	{
		return this.blockstate;
	}
	
	public boolean inRange(int number)
	{
		if(number >= this.minThreshhold && number < this.maxThreshold)
		{
			return true;
		}
		return false;
	}
}
