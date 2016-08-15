package joshie.mariculture.core.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class SingleStackHandler extends ItemStackHandler {
    private final TileMC tile;
    private final int stackLimit;

    public SingleStackHandler(TileMC tile, int stackLimit) {
        this.tile = tile;
        this.stackLimit = stackLimit;
    }

    public SingleStackHandler(TileMC tile) {
        this(tile, -1);
    }

    public boolean isValidForInsertion(int slot, ItemStack stack) { return true; }
    public boolean isValidForExtraction(int slot, ItemStack stack) { return true; }

    @Override
    protected int getStackLimit(int slot, ItemStack stack) {
        if (stackLimit == -1) return super.getStackLimit(slot, stack);
        else return stackLimit;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!isValidForInsertion(slot, stack)) return stack;
        else return super.insertItem(slot, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        validateSlotIndex(slot); //Validate the slot
        if (stacks[slot] != null && !isValidForExtraction(slot, stacks[slot])) return null;
        return super.extractItem(slot, amount, simulate);
    }

    @Override
    protected void onContentsChanged(int slot) {
        tile.markDirty();
    }
}
