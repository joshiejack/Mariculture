package joshie.maritech.handlers;

import java.util.HashMap;

import joshie.mariculture.core.events.ItemEvent;
import joshie.maritech.util.IItemExtension;
import net.minecraft.item.Item;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ItemEvents {
    public static HashMap<Item, IItemExtension> items = new HashMap();
    
    public static void register(Item item, IItemExtension extension) {
        items.put(item, extension);
    }

    @SubscribeEvent
    public void getName(ItemEvent.GetItemName event) {
        IItemExtension extension = items.get(event.item);
        if (extension != null) {
            event.name = extension.getName(event.meta, event.name);
        }
    }

    @SubscribeEvent
    public void getModName(ItemEvent.GetModName event) {
        IItemExtension extension = items.get(event.item);
        if (extension != null) {
            event.mod = extension.getMod(event.meta, event.mod);
        }
    }
}
