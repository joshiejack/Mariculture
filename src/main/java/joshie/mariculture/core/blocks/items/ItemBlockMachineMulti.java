package joshie.mariculture.core.blocks.items;

import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.events.MaricultureEvents;
import joshie.mariculture.core.lib.MachineMultiMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockMachineMulti extends ItemBlockMariculture {
    public ItemBlockMachineMulti(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        int meta = stack.getItemDamage();
        switch (meta) {
            case MachineMultiMeta.CRUCIBLE:
                return "crucible";
            case MachineMultiMeta.INCUBATOR_BASE:
                return "incubatorBase";
            case MachineMultiMeta.INCUBATOR_TOP:
                return "incubatorTop";
        }
        
        return MaricultureEvents.getItemName(field_150939_a, meta, "machinesDouble");
    }
}
