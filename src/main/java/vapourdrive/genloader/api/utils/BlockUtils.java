package vapourdrive.genloader.api.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;

import org.apache.logging.log4j.Level;

import vapourdrive.genloader.api.GenLoaderAPI;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class BlockUtils
{
	public static IBlockState createState(String block, HashMap<String, String> properties)
	{
		Block foundBlock = GameData.getBlockRegistry().getObject(new ResourceLocation(block));
		ImmutableList<IBlockState> states = foundBlock.getBlockState().getValidStates();
		Iterator<IBlockState> iterator = states.iterator();
		while (iterator.hasNext())
		{
			boolean isCorrectState = true;
			IBlockState state = iterator.next();
			ImmutableMap<IProperty, Comparable> stateProps = state.getProperties();
			Iterator<Entry<IProperty, Comparable>> propIterater = stateProps.entrySet().iterator();
			while(propIterater.hasNext())
			{
				Entry<IProperty, Comparable> entry = propIterater.next();
				IProperty property = entry.getKey();
				if(properties.containsKey(property.getName()))
				{
					if(!state.getValue(property).toString().contentEquals(properties.get(property.getName())))
					{
						isCorrectState = false;
					}
				}
			}
			if(isCorrectState)
			{
				return state;
			}
		}
		
		GenLoaderAPI.log.log(Level.WARN, "Block: *" + block + "* with properties: *" + properties + "* was not found, resorting to block's default state");
		return foundBlock.getDefaultState();
	}
	
	public static HashMap<String, String> generateProperties(IBlockState state)
	{
		ImmutableMap<IProperty, Comparable> stateProps = state.getProperties();
		Iterator<Entry<IProperty, Comparable>> propIterater = stateProps.entrySet().iterator();
		HashMap<String, String> statePropsMap = new HashMap<String, String>();
		while(propIterater.hasNext())
		{
			Entry<IProperty, Comparable> entry = propIterater.next();
			statePropsMap.put(entry.getKey().getName(), state.getValue(entry.getKey()).toString());
		}
		return statePropsMap;
	}
}
