package net.timardo.contentcreator.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Basic class for all register thingies
 * 
 * @author Timardo
 *
 */
public class ContentLoaderManager {
    public List<String> addons = new ArrayList<>();
    public Map<String, List<Object>> eventClasses = new HashMap<>();
    public List<ICommand> registeredCommands = new ArrayList<>();
    
    
    /**
     * Registers a class containing events just like you would in a mod.
     * When unloading an addon, all event classes will be unregistered automatically
     * 
     * @param eventClass - the class that contains events with {@link SubscribeEvent} annotation
     */
    public void registerEventClass(Object eventClass) {
        MinecraftForge.EVENT_BUS.register(eventClass);
        String currentAddon = addons.get(addons.size() - 1);
        
        if (this.eventClasses.containsKey(currentAddon))
            this.eventClasses.get(currentAddon).add(eventClass);
        
        else {
            List<Object> newList = new ArrayList<>();
            newList.add(eventClass);
            this.eventClasses.put(currentAddon, newList);
        }
    }
    
    /**
     * Registers a command to the internal map of <strong>server</strong>. Do not use it on clients playing on server
     * 
     * @param cmd - The command to register
     */
    public void registerCommand(ICommand cmd) {
        CommandHandler ch = (CommandHandler) FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager();
        ch.registerCommand(cmd);
        this.registeredCommands.add(cmd);
    }
}
