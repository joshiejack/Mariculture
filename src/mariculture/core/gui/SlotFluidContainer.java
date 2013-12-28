package mariculture.core.gui;

import mariculture.core.Core;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.fishery.FishFoodHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class SlotFluidContainer extends Slot {
	private EntityPlayer thePlayer;

	public SlotFluidContainer(final IInventory inventory, final int par2, final int par3, final int par4) {
		super(inventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return FluidHelper.isFluidOrEmpty(stack) || FishFoodHandler.isFishFood(stack);
	}
}
