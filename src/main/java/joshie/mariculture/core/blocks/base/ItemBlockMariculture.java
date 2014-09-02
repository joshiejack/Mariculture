package joshie.mariculture.core.blocks.base;

import joshie.lib.util.IHasMetaBlock;
import joshie.lib.util.IHasMetaItem;
import joshie.mariculture.api.core.MaricultureTab;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public abstract class ItemBlockMariculture extends ItemBlock implements IHasMetaItem {
    public ItemBlockMariculture(Block block) {
        super(block);
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return getUnlocalizedName() + "." + getName(stack);
    }

    @Override
    public int getMetaCount() {
        return ((IHasMetaBlock) Block.getBlockFromItem(this)).getMetaCount();
    }

    @Override
    public CreativeTabs[] getCreativeTabs() {
        return new CreativeTabs[] { MaricultureTab.tabCore, MaricultureTab.tabFactory, MaricultureTab.tabFishery, MaricultureTab.tabMagic, MaricultureTab.tabWorld };
    }
}
