package net.timardo.contentcreator.event;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.timardo.contentcreator.ContentCreator;
import net.timardo.contentcreator.capabilities.CapabilityWrapperProvider;
import net.timardo.contentcreator.capabilities.ICapabilityWrapper;
import net.timardo.contentcreator.event.ExtendedCapabilityAttachEvent.AttachOrigin;
import net.timardo.contentcreator.item.ItemWrapper;

public class BasicEventHandler {
    
    public static final ResourceLocation WRAPPER_CAP = new ResourceLocation(ContentCreator.MOD_ID, "wrappercapability");
    public static final Item ITEM_WRAPPER = new ItemWrapper();
    
    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> e) {
        if (!e.getObject().hasCapability(CapabilityWrapperProvider.WRAPPER, null)) {
            e.addCapability(WRAPPER_CAP, new CapabilityWrapperProvider());
        }
    }
    
    @SubscribeEvent
    public void onEntityTick(EntityEvent e) { //TODO check if we can achieve same or better behavior in EntityJoinWorldEvent
        Entity entity = e.getEntity();
        if (entity != null && entity.hasCapability(CapabilityWrapperProvider.WRAPPER, null)) {
            ICapabilityWrapper capability = entity.getCapability(CapabilityWrapperProvider.WRAPPER, null);
            
            if (!capability.hasCapParentInitialized()) {
                capability.setCapParent(entity);
                MinecraftForge.EVENT_BUS.post(new ExtendedCapabilityAttachEvent(capability, entity, AttachOrigin.ENTITY_FIRST_TICK, null));
                capability.setInitialized();
            }
            
            if (!capability.isInitialized()) { // just for safety reasons
                MinecraftForge.EVENT_BUS.post(new ExtendedCapabilityAttachEvent(capability, entity, AttachOrigin.ENTITY_FIRST_TICK, null));
                capability.setInitialized();
            }
        }
    }
    
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //event.getRegistry().registerAll(tutorialItem);
    }
}
