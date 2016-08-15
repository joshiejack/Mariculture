package joshie.mariculture.core.util;

import joshie.mariculture.modules.EventAPIContainer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventAPIContainer(events = true)
public class CapabilityAttach {
    @SubscribeEvent
    public void AttachCapability(AttachCapabilitiesEvent.TileEntity event) {
        net.minecraft.tileentity.TileEntity tile = event.getTileEntity();
        if (tile instanceof TileMC) {
            //((TileMC)event.getTileEntity()).addCapabilities(event);
        }
    }
}
