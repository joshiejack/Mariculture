package joshie.mariculture.api.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEvent extends Event {
    public final ItemStack stack;
    public final ItemRenderType type;
    
    public RenderEvent(ItemStack stack, ItemRenderType type) {
        this.stack = stack;
        this.type = type;
    }
}
