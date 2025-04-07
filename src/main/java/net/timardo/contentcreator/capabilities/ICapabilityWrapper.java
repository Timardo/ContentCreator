package net.timardo.contentcreator.capabilities;

import java.util.Map;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;

/**
 * Simple capability wrapper, use it to add your own capabilities to entities, items, TEs ...
 * 
 * @author Timardo
 *
 */
public interface ICapabilityWrapper {
    
    /**
     * Gets extended capability by its registered name
     * @param name - registered name
     * @return extended capability with this ID or null if key not present
     */
    public IExtendedCapability getExtendedCapabilityByID(String name);
    
    /**
     * Gets the parent object of this capability (Entity, Tile Entity..)
     * 
     * @return parent object
     */
    public @Nullable Object getCapParent();
    
    /**
     * Determines if this instance of capability has its parent already set
     * Generally, capability parent is set on the first tick of entity
     * 
     * @return false if parent object is null, true otherwise
     */
    public boolean hasCapParentInitialized();
    
    /**
     * Returns all extended capabilities
     * 
     * @return Map of all extended capabilities registered for this object
     */
    public Map<String, IExtendedCapability> getAllExtendedCapabilities();
    
    /**
     * Adds extended capability to the internal list
     * 
     * @param uniqueName - unique name to register the capability
     * @param defaultCap - any (default) instance of given capability
     */
    public void addExtendedCapability(String uniqueName, IExtendedCapability defaultCap);

    /**
     * Checks if this instance of capability wrapper has already been initialized and sent to addons
     * 
     * @return true if initialized
     */
    public boolean isInitialized();

    /**
     * Sets this capability instance to initialized state
     */
    public void setInitialized();

    /**
     * Returns cached tag. This exists to prevent resetting tags that are not being used by any addon at given moment
     * 
     * @return Cached tag
     */
    public NBTBase getPersistenceTagCache();

    /**
     * Sets cached tag to prevent resetting 
     * 
     * @param nbt - persistent NBT tag
     */
    public void setPersistentTagCache(NBTBase nbt);

    /**
     * Sets the owner/parent of this capability, forming a unique pair
     * DO NOT use this if you are not aware of the consequences!
     * This method is for internal setting of parent, it SHOUDLN'T be used outside of ContentCreator
     * 
     * @param entity - new owner of this capability
     */
    public void setCapParent(Object entity);
}
