package vapourdrive.genloader.commands;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import vapourdrive.genloader.api.GenLoaderAPI;
import vapourdrive.genloader.commands.subcommands.ClearBlocks;
import vapourdrive.genloader.commands.subcommands.CountBlocks;

public class GenLoaderCommand implements ICommand
{
	@Override
	public int compareTo(ICommand o)
	{
		return 0;
	}

	@Override
	public String getCommandName()
	{
		return "genloader";
	}

	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "genloader <text>";
	}

	@Override
	public List<String> getCommandAliases()
	{
		ArrayList<String> aliases = new ArrayList<String>();
		aliases.add(getCommandName());
		return aliases;
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
		ArrayList<String> argsList = new ArrayList<String>();
		if(args.length == 1)
		{
			argsList.add("countblocks");
			argsList.add("clearblocks");
		}
		if(args[0].contains("countblocks"))
		{
			argsList = CountBlocks.addTabCompletionOptions(sender, args);
			GenLoaderAPI.log.log(Level.INFO, "counblocks");
		}
		if(args[0].contains("clearblocks"))
		{
			argsList = ClearBlocks.addTabCompletionOptions(sender, args);
			GenLoaderAPI.log.log(Level.INFO, "clearblocks");
		}

		return argsList;
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException
	{
		if (args.length > 0)
		{
			if(args[0].contains("countblocks"))
			{
				CountBlocks.processCommand(sender, args);
			}
			if(args[0].contains("clearblocks"))
			{
				ClearBlocks.processCommand(sender, args);
			}
		}
	}
}
