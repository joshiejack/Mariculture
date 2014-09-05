package joshie.maritech.handlers;

import java.util.HashMap;

import joshie.mariculture.core.events.IconEvent.GetInventoryIIcon;
import joshie.mariculture.core.events.IconEvent.GetWorldIIcon;
import joshie.maritech.util.IIconExtension;
import net.minecraft.block.Block;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class IconEvents {
public static HashMap<Block, IIconExtension> icons = new HashMap();
    
    public static void register(Block block, IIconExtension extension) {
        icons.put(block, extension);
    }
    
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void getInventoryIcon(GetInventoryIIcon event) {
        IIconExtension extension = icons.get(event.block);
        if (extension != null) {
            event.icon = extension.getInventoryIcon(event.meta, event.side, event.icon);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void getWorldIcon(GetWorldIIcon event) {
        IIconExtension extension = icons.get(event.block);
        if (extension != null) {
            event.icon = extension.getWorldIcon(event.world, event.x, event.y, event.z, event.side, event.icon);
        }
    }
}
