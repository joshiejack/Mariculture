package joshie.mariculture.util;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMC extends ItemBlock implements CreativeSorted {
    private final BlockMCEnum block;

    public ItemBlockMC(BlockMCEnum block) {
        super(block);
        this.block = block;
        setHasSubtypes(true);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return block.getItemStackDisplayName(stack);
    }

    @Override
    public BlockMCEnum getBlock() {
        return block;
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return block.getEnumFromMeta(stack.getItemDamage()).name().toLowerCase();
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return block.getSortValue(stack);
    }
}