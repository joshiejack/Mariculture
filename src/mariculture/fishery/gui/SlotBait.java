package mariculture.fishery.gui;

import mariculture.api.fishery.Fishing;
import mariculture.fishery.items.ItemBait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBait extends Slot {
	private EntityPlayer thePlayer;
	private int field_75228_b;

	public SlotBait(final IInventory inventory, final int par2, final int par3, final int par4) {
		super(inventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if (Fishing.bait.getEffectiveness(stack) != -1) {
			return true;
		}

		return false;
	}

	@Override
	public ItemStack decrStackSize(final int par1) {
		if (this.getHasStack()) {
			this.field_75228_b += Math.min(par1, this.getStack().stackSize);
		}

		return super.decrStackSize(par1);
	}
}