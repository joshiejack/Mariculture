package joshie.mariculture.core.blocks.items;

import joshie.mariculture.api.events.MaricultureEvents;
import joshie.mariculture.core.blocks.base.ItemBlockMariculture;
import joshie.mariculture.core.lib.MachineMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockMachine extends ItemBlockMariculture {
    public ItemBlockMachine(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        int meta = stack.getItemDamage();
        switch (meta) {
            case MachineMeta.BOOKSHELF:
                return "bookshelf";
            case MachineMeta.DICTIONARY_ITEM:
                return "dictionary";
            case MachineMeta.SAWMILL:
                return "sawmill";
            case MachineMeta.FISH_SORTER:
                return "fishSorter";
            case MachineMeta.UNPACKER:
                return "unpacker";
        }

        return MaricultureEvents.getItemName(field_150939_a, meta, "machines");
    }
}
