package joshie.mariculture.modules.diving.item;

import joshie.mariculture.modules.diving.item.ItemDivingComponent.DivingComponent;
import joshie.mariculture.util.ItemMCEnum;
import joshie.mariculture.util.MCTab;
import net.minecraft.util.IStringSerializable;

public class ItemDivingComponent extends ItemMCEnum<ItemDivingComponent, DivingComponent> {
    public enum DivingComponent implements IStringSerializable {
        LENS;

        @Override
        public String getName() {
            return name().toLowerCase();
        }
    }

    public ItemDivingComponent() {
        super(MCTab.getTab("exploration"), DivingComponent.class);
    }
}
