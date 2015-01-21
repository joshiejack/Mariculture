package maritech.handlers;

import java.util.HashMap;

import mariculture.core.events.ItemEvent;
import maritech.util.IItemExtension;
import net.minecraft.item.Item;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ItemEvents {
    public static HashMap<Class, IItemExtension> items = new HashMap();
    
    public static void register(Class clazz, IItemExtension extension) {
        items.put(clazz, extension);
    }

    @SubscribeEvent
    public void getName(ItemEvent.GetItemName event) {
        IItemExtension extension = items.get(event.item.getClass());
        if (extension != null) {
            event.name = extension.getName(event.meta, event.name);
        }
    }

    @SubscribeEvent
    public void getModName(ItemEvent.GetModName event) {
        IItemExtension extension = items.get(event.item.getClass());
        if (extension != null) {
            event.mod = extension.getMod(event.meta, event.mod);
        }
    }
}
