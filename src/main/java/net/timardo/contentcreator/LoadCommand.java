package net.timardo.contentcreator;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.timardo.contentcreator.loader.AddonLoadingException;

public class LoadCommand implements ICommand {
    
    private final List<String> aliases = new ArrayList<String>();
    
    public LoadCommand() {
        this.aliases.add("load");
    }

    @Override
    public int compareTo(ICommand arg0) {
        return 0;
    }

    @Override
    public String getName() {
        return "loadfile";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/loadfile <file name>";
    }

    @Override
    public List<String> getAliases() {
        return this.aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1) {
            sender.sendMessage(new TextComponentString("Correct usage: " + getUsage(sender)));
            return;
        }
        
        try {
            Util.loadAddon(args[0], sender, getClass().getClassLoader());
        }
        
        catch (AddonLoadingException ale) {
            throw new CommandException(ale.message);
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

}
