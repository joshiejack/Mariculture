package joshie.mariculture.core.events;

import net.minecraft.item.Item;
import cpw.mods.fml.common.eventhandler.Event;

public class ItemEvent extends Event {
    public final Item item;
  
    public ItemEvent(Item item) {
        this.item = item;
    }
    
    public static class GetModName extends ItemEvent {
        public final int meta;
        public String mod;
        public GetModName(Item item, int meta, String mod) {
            super(item);
            this.mod = mod;
            this.meta = meta;
        }
    }
    
    public static class GetItemName extends ItemEvent {
        public final int meta;
        public String name;
        public GetItemName(Item item, int meta, String name) {
            super(item);
            this.name = name;
            this.meta = meta;
        }
    }
}
