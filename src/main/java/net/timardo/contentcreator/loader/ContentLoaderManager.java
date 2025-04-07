package net.timardo.contentcreator.loader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBiMap;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.ICommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.timardo.contentcreator.ContentCreator;
import net.timardo.contentcreator.Util;
import net.timardo.contentcreator.event.FMLForwardEvent;

/**
 * Basic class for all register thingies
 * 
 * @author Timardo
 *
 */
public class ContentLoaderManager {
    public List<String> addons = new ArrayList<>();
    public Map<String, List<Object>> eventClasses = new HashMap<>();
    public HashBiMap<String, ICommand> registeredCommands = HashBiMap.create(new HashMap<>());
    
    public void preLoadPresentAddons() {
        for (File addon : Util.contentLibsDir.listFiles()) {
            String name = addon.getName();
            System.out.println(name);
            
            if (!name.endsWith(".jar"))
                continue;
            
            try {
                ContentCreator.logger.info("Trying to preload file " + name + " as an addon...");
                Util.loadAddon(addon.getName(), null, getClass().getClassLoader());
            } catch (AddonLoadingException ale) {
                ContentCreator.logger.warn(ale.message);
            }
        }
    }

    /**
     * Registers a class containing events just like you would in a mod.
     * When unloading an addon, all event classes will be unregistered automatically TODO
     * Also tries to hack into FML event system and register FML events here
     * FML events must be annotated with {@link EventHandler}
     * Beware that FML events that occurred before registering their handlers will obviously not be executed
     * 
     * @param eventClass - the class that contains events with {@link net.minecraftforge.fml.common.eventhandler.SubscribeEvent SubscribeEvent} and {@link EventHandler} annotation
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
    public void registerServerCommand(ICommand cmd) {
        if (this.registeredCommands.containsValue(cmd)) {
            ContentCreator.logger.warn("Addon '" + addons.get(addons.size() - 1) + "' tried to register already existing command! This command (" + cmd.getName() + ") won't be registered!");
            return;
        }

        this.registeredCommands.put(addons.get(addons.size() - 1), cmd);
        
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER || FMLCommonHandler.instance().getMinecraftServerInstance() != null) {
            registerCommandOnServer(cmd);
        }
        
        else {
            ContentCreator.logger.info("Registered server command " + cmd.getName() + " to internal map, will be added on server startup");
        }
    }

    public void registerCommandOnServer(ICommand cmd) {
        CommandHandler ch = (CommandHandler) FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager();
        
        if (ch.getCommands().containsKey(cmd.getName())) {
            ContentCreator.logger.warn("Addon '" + this.registeredCommands.inverse().get(cmd) + "' tried to register already existing command! This command (" + cmd.getName() + ") won't be registered!");
            return; //TODO: addon ID as fallback prefix?
        }
        
        ch.registerCommand(cmd);
    }

    public void registerCommands() {
        ContentCreator.logger.info("Registering " + this.registeredCommands.size() + " commands...");
        
        for (Entry<String, ICommand> entry : this.registeredCommands.entrySet()) {
            registerCommandOnServer(entry.getValue());
        }
    }

    /**
     * Method which forwards FML events to addons. The system that the FML events are using is so stupid I had to add subscribe method for each type...
     * 
     * @param e - an event which extends {@link FMLEvent}
     */
    public void postFMLEvent(FMLEvent e) {
        MinecraftForge.EVENT_BUS.post(new FMLForwardEvent(e));
    }
}
