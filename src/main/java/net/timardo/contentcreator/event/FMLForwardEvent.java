package net.timardo.contentcreator.event;

import net.minecraftforge.fml.common.event.FMLEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class FMLForwardEvent extends Event {
    
    private final FMLEvent event;

    public FMLForwardEvent(FMLEvent e) {
        this.event = e;
    }

    public FMLEvent getEvent() {
        return this.event;
    }
}
