package mariculture.core.blocks;

import mariculture.core.blocks.base.ItemBlockMariculture;
import mariculture.core.lib.MachineMeta;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class BlockMachineItem extends ItemBlockMariculture {
    public BlockMachineItem(Block block) {
        super(block);
    }

    @Override
    public String getName(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case MachineMeta.BOOKSHELF:
                return "bookshelf";
            case MachineMeta.DICTIONARY_ITEM:
                return "dictionary";
            case MachineMeta.SAWMILL:
                return "sawmill";
            case MachineMeta.SLUICE:
                return "sluice";
            case MachineMeta.SPONGE:
                return "sponge";
            case MachineMeta.AUTOFISHER:
                return "autofisher";
            case MachineMeta.FISH_SORTER:
                return "fishSorter";
            case MachineMeta.UNPACKER:
                return "unpacker";
            case MachineMeta.SLUICE_ADVANCED:
                return "sluiceAdvanced";
            default:
                return null;
        }
    }
}
