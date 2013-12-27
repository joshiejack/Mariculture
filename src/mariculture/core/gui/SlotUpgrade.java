package mariculture.core.gui;

import mariculture.api.core.IItemUpgrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotUpgrade extends Slot {
	private EntityPlayer thePlayer;
	private int field_75228_b;

	public SlotUpgrade(final IInventory inventory, final int par2, final int par3, final int par4) {
		super(inventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return (stack.getItem() instanceof IItemUpgrade) ? true : false;
	}

	@Override
	public ItemStack decrStackSize(final int par1) {
		if (this.getHasStack()) {
			this.field_75228_b += Math.min(par1, this.getStack().stackSize);
		}

		return super.decrStackSize(par1);
	}
}