package vapourdrive.genloader.commands.subcommands;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.registry.GameData;
import vapourdrive.genloader.api.GenLoaderAPI;
import vapourdrive.genloader.api.utils.BlockUtils;

public class ClearBlocks
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

		String toRemove = args[5];
		Block block = null;
		IBlockState state = null;

		boolean haveJunk = false;

		if (!toRemove.contentEquals("junk"))
		{
			if (args.length == 6)
			{
				block = GameData.getBlockRegistry().getObject(new ResourceLocation(toRemove));
			}
			HashMap<String, String> properties = new HashMap<String, String>();
			for (int i = 6; i < args.length; i = i + 2)
			{
				if (args[i] != null && args[i + 1] != null)
				{
					properties.put(args[i], args[i + 1]);
				}
			}

			state = BlockUtils.createState(toRemove, properties);
		}
		if (toRemove.contentEquals("junk"))
		{
			haveJunk = true;
		}

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
							if (haveJunk && world.getBlockState(new BlockPos(k, m, l)) != Blocks.bedrock.getDefaultState() && !GenLoaderAPI.getValuableBlockStates().contains(world.getBlockState(new BlockPos(k, m, l))))
							{
								world.setBlockToAir(new BlockPos(k, m, l));
							}
							if (block != null && world.getBlockState(new BlockPos(k, m, l)).getBlock() == block)
							{
								world.setBlockToAir(new BlockPos(k, m, l));
							}
							if (world.getBlockState(new BlockPos(k, m, l)) == state)
							{
								world.setBlockToAir(new BlockPos(k, m, l));
							}
						}
					}
				}
			}
		}
	}
	
	public static ArrayList<String> addTabCompletionOptions(ICommandSender sender, String[] args)
	{
		Chunk chunk = sender.getEntityWorld().getChunkFromBlockCoords(sender.getPosition());
		ArrayList<String> argsList = new ArrayList<String>();
		argsList.add(String.valueOf(chunk.xPosition - 2) + " " + String.valueOf(chunk.xPosition + 2) + " "
				+ String.valueOf(chunk.zPosition - 2) + " " + String.valueOf(chunk.zPosition + 2) + " junk");

		return argsList;
	}

}
