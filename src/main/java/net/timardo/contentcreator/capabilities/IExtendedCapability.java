package net.timardo.contentcreator.capabilities;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Mother interface of all extended capabilities wrapped into the parent capability
 *  
 * @author Timardo
 *
 */
public interface IExtendedCapability {
    
    /**
     * Provides NBTTagCompound to be written in the NBT of object
     * 
     * @return NBT saved from extended capability
     */
    public NBTTagCompound writeToNBT();
    
    /**
     * Method called when parent capability reads NBT from object, set all extended capability variables here
     * 
     * @param nbtIn - your previously saved NBT of object
     */
    public void readFromNBT(NBTTagCompound nbtIn, Object object);
}
