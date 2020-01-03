package net.timardo.contentcreator.loader;

import javax.annotation.Nullable;

import net.minecraft.command.ICommandSender;

/**
 * Base interface which represents an addon
 * 
 * @author Timardo
 *
 */
public interface IContentAddon {
    
    /**
     * Method called when the addon is being loaded
     * 
     * @param initGuy - <strong>can be null</strong> if loaded from code, be sure to check for it, otherwise contains the sender of the load command
     * @param contentManager - provides fast access to basic register methods like {@link ContentLoaderManager#registerEventClass(Object)}
     * 
     */
    public void load(@Nullable ICommandSender initGuy, ContentLoaderManager contentManager);
    
    /**
     * Gets called before the addon is being unloaded.
     */
    public void unload();
    
    /**
     * 
     * @return The name of the addon in user friendly format
     */
    public String getName();
    
    /**
     * Even though I don't care about the format, <strong>PLEASE</strong> include the minecraft version
     * 
     * @return The version of this addon
     */
    public String getVersion();
    
    /**
     * You know what ids are for, be as unique as possible
     * 
     * @return Unique identifier of this addon
     */
    public String getAddonID();
}
