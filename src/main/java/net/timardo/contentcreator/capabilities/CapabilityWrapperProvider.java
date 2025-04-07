package net.timardo.contentcreator.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityWrapperProvider implements ICapabilitySerializable<NBTBase> {
    
    @CapabilityInject(ICapabilityWrapper.class)
    public static final Capability<ICapabilityWrapper> WRAPPER = null;
    private ICapabilityWrapper instance = WRAPPER.getDefaultInstance();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == WRAPPER;
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        return capability == WRAPPER ? WRAPPER.<T> cast(this.instance) : null;
    }

    @Override
    public NBTBase serializeNBT() {
        return WRAPPER.getStorage().writeNBT(WRAPPER, this.instance, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        WRAPPER.getStorage().readNBT(WRAPPER, this.instance, null, nbt);
    }
}
