package vapourdrive.genloader.utils;

import net.minecraft.block.state.IBlockState;

import com.google.common.base.Predicate;

public class IBlockStateHelper implements Predicate<IBlockState>
{
	private final IBlockState state;
	
	public IBlockStateHelper(IBlockState State)
	{
		this.state = State;
	}
	
	@Override
	public boolean apply(IBlockState input)
	{
		return input != null && this.state == input;
	}

}
