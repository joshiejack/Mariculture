package mariculture.core.gui;

import mariculture.api.core.MaricultureHandlers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFuel extends Slot {
	private EntityPlayer thePlayer;
	private int field_75228_b;

	public SlotFuel(IInventory inventory, int par2, int par3, int par4) {
		super(inventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return (MaricultureHandlers.crucible.getFuelInfo(stack) != null) ? true : false;
	}

	@Override
	public ItemStack decrStackSize(final int par1) {
		if (this.getHasStack()) {
			this.field_75228_b += Math.min(par1, this.getStack().stackSize);
		}

		return super.decrStackSize(par1);
	}
}