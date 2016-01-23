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
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.registry.GameData;
import vapourdrive.genloader.api.utils.BlockUtils;

public class CountBlockCommand implements ICommand
{
	public String[] minecraftOres = {"minecraft:coal_ore", "minecraft:iron_ore", "minecraft:gold_ore", "minecraft:redstone_ore", "minecraft:emerald_ore", "minecraft:lapis_ore", "minecraft:diamond_ore"};
	@Override
	public int compareTo(ICommand o)
	{
		return 0;
	}

	@Override
	public String getCommandName()
	{
		return "GL_CountBlocks";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "GL_CountBlocks <text>";
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

		if ((CXmax - CXmin) * (CZmax - CZmin) > 25)
		{
			sender.addChatMessage(new ChatComponentTranslation("genloader.areatoolarge"));
			return;
		}

		String toCount = args[4];
		Block block = null;
		IBlockState state = null;
		
		if(toCount.contentEquals("ores"))
		{
			for(int i = 0; i < minecraftOres.length; i++)
			{
				block = GameData.getBlockRegistry().getObject(new ResourceLocation(minecraftOres[i]));
				int Count = countObjects(CXmin, CXmax, CZmin, CZmax, sender, block, block.getDefaultState());
				sender.addChatMessage(new ChatComponentText(" " + block.getDefaultState().toString() + " " + String.valueOf(Count)));
			}
			return;
		}

		if (args.length == 5)
		{
			block = GameData.getBlockRegistry().getObject(new ResourceLocation(toCount));
		}
		
		HashMap<String, String> properties = new HashMap<String, String>();
		for (int i = 5; i < args.length; i = i + 2)
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
	
	public int countObjects(int CXmin, int CXmax, int CZmin, int CZmax, ICommandSender sender, Block block, IBlockState state)
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
				+ String.valueOf(chunk.zPosition - 2) + " " + String.valueOf(chunk.zPosition + 2));

		return argsList;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}

}
