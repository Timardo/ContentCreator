package net.timardo.contentcreator.capabilities;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import static net.timardo.contentcreator.ContentCreator.*;

public class CapabilityWrapper implements ICapabilityWrapper {
    
    public Map<String, IExtendedCapability> extendedCapMap = new HashMap<String, IExtendedCapability>();
    private boolean initialized = false;
    public NBTBase persistentTagCache = new NBTTagCompound();
    public Object parent;

    @Override
    public IExtendedCapability getExtendedCapabilityByID(String name) {
        return extendedCapMap.get(name);
    }
    
    @Override
    public void addExtendedCapability(String uniqueName, IExtendedCapability defaultCap) {
        if (extendedCapMap.containsKey(uniqueName)) {
            logger.warn("Something tried to put duplicate key to extended capability storage!");
            return;
        }
        
        extendedCapMap.put(uniqueName, defaultCap);
    }

    @Override
    public Map<String, IExtendedCapability> getAllExtendedCapabilities() {
        return extendedCapMap;
    }

    @Override
    public boolean isInitialized() {
        return this.initialized;
    }

    @Override
    public void setInitialized() {
        this.initialized = true;
    }

    @Override
    public NBTBase getPersistenceTagCache() {
        return this.persistentTagCache;
    }

    @Override
    public void setPersistentTagCache(NBTBase nbt) {
        this.persistentTagCache = nbt;
    }

    @Override
    public Object getCapParent() {
        return this.parent;
    }

    @Override
    public boolean hasCapParentInitialized() {
        return getCapParent() != null;
    }

    @Override
    public void setCapParent(Object entity) {
        this.parent = entity;
    }
}
