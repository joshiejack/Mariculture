package mariculture.core.gui;

import mariculture.core.Core;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.FluidContainerMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class SlotFluidContainer extends Slot {
	private EntityPlayer thePlayer;
	private int field_75228_b;

	public SlotFluidContainer(final IInventory inventory, final int par2, final int par3, final int par4) {
		super(inventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(final ItemStack itemstack) {
		return FluidHelper.isFluidOrEmpty(itemstack);
	}

	@Override
	public ItemStack decrStackSize(final int par1) {
		if (this.getHasStack()) {
			this.field_75228_b += Math.min(par1, this.getStack().stackSize);
		}

		return super.decrStackSize(par1);
	}
}
