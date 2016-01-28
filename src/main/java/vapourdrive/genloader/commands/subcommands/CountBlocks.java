package vapourdrive.genloader.commands.subcommands;

import java.util.ArrayList;
import java.util.HashMap;

import vapourdrive.genloader.api.GenLoaderAPI;
import vapourdrive.genloader.api.utils.BlockUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.registry.GameData;

public class CountBlocks
{

	public static void processCommand(ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length < 6)
		{
			sender.addChatMessage(new ChatComponentTranslation("genloader.notenoughargs"));
			return;
		}
		int CXmin = CommandBase.parseInt(args[1]);
		int CXmax = CommandBase.parseInt(args[2]);
		int CZmin = CommandBase.parseInt(args[3]);
		int CZmax = CommandBase.parseInt(args[4]);

		if ((CXmax - CXmin) * (CZmax - CZmin) > 25)
		{
			sender.addChatMessage(new ChatComponentTranslation("genloader.areatoolarge"));
			return;
		}

		String toCount = args[5];
		Block block = null;
		IBlockState state = null;
		
		if(toCount.contentEquals("valuable"))
		{
			for(IBlockState valuable : GenLoaderAPI.getValuableBlockStates())
			{
				int Count = countObjects(CXmin, CXmax, CZmin, CZmax, sender, block, valuable);
				sender.addChatMessage(new ChatComponentText(valuable.toString() + " " + String.valueOf(Count)));
			}
			return;
		}

		if (args.length == 6)
		{
			block = GameData.getBlockRegistry().getObject(new ResourceLocation(toCount));
		}
		
		HashMap<String, String> properties = new HashMap<String, String>();
		for (int i = 6; i < args.length; i = i + 2)
		{
			if (args[i] != null && args[i + 1] != null)
			{
				properties.put(args[i], args[i + 1]);
			}
		}

		state = BlockUtils.createState(toCount, properties);

		int Count = countObjects(CXmin, CXmax, CZmin, CZmax, sender, block, state);
		sender.addChatMessage(new ChatComponentText(" " + state.toString() + " " + String.valueOf(Count)));
	}
	
	public static int countObjects(int CXmin, int CXmax, int CZmin, int CZmax, ICommandSender sender, Block block, IBlockState state)
	{
		int Count = 0;
		for (int i = CXmin; i <= CXmax; i++)
		{
			for (int j = CZmin; j <= CZmax; j++)
			{
				World world = sender.getEntityWorld();
				for (int k = i * 16; k < (i * 16) + 16; k++)
				{
					for (int l = j * 16; l < (j * 16) + 16; l++)
					{
						int maxheight = world.getChunkFromBlockCoords(new BlockPos(k, 0, l)).getTopFilledSegment() + 16;
						for (int m = 0; m < maxheight; m++)
						{
							if (block != null && world.getBlockState(new BlockPos(k, m, l)).getBlock() == block)
							{
								Count++;
							}
							else if (world.getBlockState(new BlockPos(k, m, l)) == state)
							{
								Count++;
							}
						}
					}
				}
			}
		}
		return Count;
	}

	public static ArrayList<String> addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		Chunk chunk = sender.getEntityWorld().getChunkFromBlockCoords(sender.getPosition());
		ArrayList<String> argsList = new ArrayList<String>();
		argsList.add(String.valueOf(chunk.xPosition - 2) + " " + String.valueOf(chunk.xPosition + 2) + " "
				+ String.valueOf(chunk.zPosition - 2) + " " + String.valueOf(chunk.zPosition + 2) + " valuable");

		return argsList;
	}

}
