package joshie.mariculture.factory;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

public class DummyCrafting extends InventoryCrafting {
    private ItemStack[] stackList;

    public DummyCrafting(int par2, int par3) {
        super(null, par2, par3);

        int k = par2 * par3;
        stackList = new ItemStack[k];
    }

    @Override
    public int getSizeInventory() {
        return stackList.length;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return par1 >= this.getSizeInventory() ? null : stackList[par1];
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        stackList[par1] = par2ItemStack;
    }
}
