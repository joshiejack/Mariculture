package joshie.mariculture.core.events;

import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.common.eventhandler.Event;

public class IconRegisterEvent extends Event {
    public final IIconRegister register;
    
    public IconRegisterEvent(IIconRegister register) {
        this.register = register;
    }
}
