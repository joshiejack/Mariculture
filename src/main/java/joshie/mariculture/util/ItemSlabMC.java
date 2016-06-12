package joshie.mariculture.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSlabMC extends ItemSlab implements MCItem {
    private final MCBlock mcBlock;

    public ItemSlabMC(MCBlock block, BlockSlab singleBlock, BlockSlab doubleBlock) {
        super((Block)block, singleBlock, doubleBlock);
        this.mcBlock = block;
        setHasSubtypes(true);
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        return mcBlock.getItemStackDisplayName(stack);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public int getSortValue(ItemStack stack) {
        return mcBlock.getSortValue(stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModels(Item item, String name) {
        mcBlock.registerModels(item, name);
    }
}