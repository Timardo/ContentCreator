package net.timardo.contentcreator.capabilities;

import java.util.Map.Entry;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.timardo.contentcreator.event.ExtendedCapabilityAttachEvent;
import net.timardo.contentcreator.event.ExtendedCapabilityAttachEvent.AttachOrigin;

public class CapabilityWrapperStorage implements IStorage<ICapabilityWrapper> {
    
    @Override
    public NBTBase writeNBT(Capability<ICapabilityWrapper> capability, ICapabilityWrapper instance, EnumFacing side) {
        NBTTagCompound ret = (NBTTagCompound) instance.getPersistenceTagCache();
        
        for (Entry<String, IExtendedCapability> entry: instance.getAllExtendedCapabilities().entrySet()) {
            ret.setTag(entry.getKey(), entry.getValue().writeToNBT());
        }
        
        return ret;
    }
    
    @Override
    public void readNBT(Capability<ICapabilityWrapper> capability, ICapabilityWrapper instance, EnumFacing side, NBTBase nbt) {
        NBTTagCompound casted = (NBTTagCompound) nbt;
        Object object = instance.getCapParent();
        
        if (!instance.isInitialized()) {
            MinecraftForge.EVENT_BUS.post(new ExtendedCapabilityAttachEvent(instance, object, AttachOrigin.READ_NBT, nbt));
            instance.setInitialized();
        }

        for (Entry<String, IExtendedCapability> entry: instance.getAllExtendedCapabilities().entrySet()) {
            String key = entry.getKey();
            
            if (casted.hasKey(key)) {
                entry.getValue().readFromNBT(casted.getCompoundTag(key), object);
            }
        }
        
        instance.setPersistentTagCache(nbt);
    }
    
}
