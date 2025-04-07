package net.timardo.contentcreator.event;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.timardo.contentcreator.capabilities.ICapabilityWrapper;;

public class ExtendedCapabilityAttachEvent extends Event {
    private final ICapabilityWrapper wrapper;
    private final Object object;
    private final AttachOrigin source;
    private final NBTBase nbt;
    
    /**
     * Listen to this event to attach extended capabilities to objects. This event
     * doesn't contain object of interest, yet.
     * 
     * @param wrapper - actual capability in which your extended capabilities will be wrapped
     * @param obj - object of which capability is being processed, null if no reference could be set
     * @param origin - source of event
     */
    public ExtendedCapabilityAttachEvent(ICapabilityWrapper wrapper, @Nullable Object obj, AttachOrigin origin, @Nullable NBTBase nbtIn) {
        this.wrapper = wrapper;
        this.object = obj;
        this.source = origin;
        this.nbt = nbtIn;
    }
    
    /**
     * Contains wrapper to register your very own extended capabilities
     * 
     * @return The wrapper instance of the object
     */
    public ICapabilityWrapper getWrapper() {
        return this.wrapper;
    }
    
    /**
     * This is the parent object of wrapper capability. Currently not parameterized, use instanceof check
     * CAN BE NULL if the object hasn't fired an event including its reference!
     * 
     * @return parent of the wrapper, also present in the wrapper
     */
    public @Nullable Object getObject() {
        return this.object;
    }
    
    /**
     * Since you are not able to determine capability parent when reading NBT, this will help you choose
     * whether you really want to process given event
     * 
     * @return whole custom NBT tag of object processed consisting of <b>ONLY</b> wrapper capability tags 
     */
    public @Nullable NBTBase getNBT() {
        return this.nbt;
    }
    
    /**
     * Origin of this event, use to distinguish between several occurrences when this event is posted
     * 
     * @return source type of this event, see {@link AttachOrigin} for all available types
     */
    public AttachOrigin getOrigin() {
        return this.source;
    }
    
    public enum AttachOrigin {
        READ_NBT,
        ENTITY_FIRST_TICK
    }

}
