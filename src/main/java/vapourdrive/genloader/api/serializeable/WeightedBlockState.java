package vapourdrive.genloader.api.serializeable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.registry.GameData;

import com.google.common.collect.ImmutableMap;

public class WeightedBlockState
{
	private final int weight;
	private final String block;
	private final HashMap<String, String> properties;
	private final IBlockState state;
	
	public WeightedBlockState(int Weight, IBlockState State)
	{
		this.weight = Weight;
		this.block = GameData.getBlockRegistry().getNameForObject(State.getBlock()).toString();
		ImmutableMap<IProperty, Comparable> stateProps = State.getProperties();
		Iterator<Entry<IProperty, Comparable>> propIterater = stateProps.entrySet().iterator();
		HashMap<String, String> statePropsMap = new HashMap<String, String>();
		while(propIterater.hasNext())
		{
			Entry<IProperty, Comparable> entry = propIterater.next();
			statePropsMap.put(entry.getKey().getName(), State.getValue(entry.getKey()).toString());
		}
		this.properties = statePropsMap;
		this.state = State;
	}

	public int getWeight()
	{
		return weight;
	}
	
	public IBlockState getState()
	{
		return this.state;
	}
	
	public String getBlockName()
	{
		return this.block;
	}
	
	public HashMap<String, String> getProperties()
	{
		return this.properties;
	}
}
