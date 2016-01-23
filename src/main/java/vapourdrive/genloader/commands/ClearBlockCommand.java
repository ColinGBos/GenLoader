package vapourdrive.genloader.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.registry.GameData;

import org.apache.logging.log4j.Level;

import vapourdrive.genloader.api.GenLoaderAPI;
import vapourdrive.genloader.api.utils.BlockUtils;

public class ClearBlockCommand implements ICommand
{
	@Override
	public int compareTo(ICommand o)
	{
		return 0;
	}

	@Override
	public String getCommandName()
	{
		return "GL_ClearBlock";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "Gl_ClearBlocks <text>";
	}

	@Override
	public List<String> getCommandAliases()
	{
		ArrayList<String> aliases = new ArrayList<String>();
		aliases.add(getCommandName());
		return aliases;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length < 5)
		{
			sender.addChatMessage(new ChatComponentTranslation("genloader.notenoughargs"));
			return;
		}
		int CXmin = CommandBase.parseInt(args[0]);
		int CXmax = CommandBase.parseInt(args[1]);
		int CZmin = CommandBase.parseInt(args[2]);
		int CZmax = CommandBase.parseInt(args[3]);
		
		if((CXmax - CXmin) * (CZmax - CZmin) > 25)
		{
			sender.addChatMessage(new ChatComponentTranslation("genloader.areatoolarge"));
			return;
		}

		String toRemove = args[4];
		Block block = null;
		IBlockState state = null;
		ArrayList<Block> junkArray = new ArrayList<Block>();

		boolean haveJunk = false;

		if (!toRemove.contentEquals("junk"))
		{
			if (args.length == 5)
			{
				block = GameData.getBlockRegistry().getObject(new ResourceLocation(toRemove));
			}
			HashMap<String, String> properties = new HashMap<String, String>();
			for (int i = 5; i < args.length; i = i + 2)
			{
				if (args[5] != null && args[6] != null)
				{
					properties.put(args[5], args[6]);
				}
			}

			state = BlockUtils.createState(toRemove, properties);
		}
		if (toRemove.contentEquals("junk"))
		{
			haveJunk = true;
			GenLoaderAPI.log.log(Level.INFO, "Preparing to remove junk");
			junkArray.add(Blocks.stone);
			junkArray.add(Blocks.dirt);
			junkArray.add(Blocks.gravel);
			junkArray.add(Blocks.grass);
			junkArray.add(Blocks.cobblestone);
			junkArray.add(Blocks.lava);
			junkArray.add(Blocks.water);
			junkArray.add(Blocks.flowing_lava);
			junkArray.add(Blocks.flowing_water);
			junkArray.add(Blocks.sandstone);
			junkArray.add(Blocks.log);
			junkArray.add(Blocks.log2);
			junkArray.add(Blocks.leaves);
			junkArray.add(Blocks.leaves2);
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
							if (haveJunk && junkArray.contains(world.getBlockState(new BlockPos(k, m, l)).getBlock()))
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

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender)
	{
		if (sender.getCommandSenderEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
			if (player.capabilities.isCreativeMode)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
	{
		Chunk chunk = sender.getEntityWorld().getChunkFromBlockCoords(sender.getPosition());
		ArrayList<String> argsList = new ArrayList<String>();
		argsList.add(String.valueOf(chunk.xPosition - 2) + " " + String.valueOf(chunk.xPosition + 2) + " "
				+ String.valueOf(chunk.zPosition - 2) + " " + String.valueOf(chunk.zPosition + 2) + " junk");

		return argsList;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}

}
