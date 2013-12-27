package mariculture.fishery.gui;

import mariculture.api.fishery.ItemBaseRod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFishingRod extends Slot {
	private EntityPlayer thePlayer;
	public SlotFishingRod(IInventory inventory, int par2, int par3, int par4) {
		super(inventory, par2, par3, par4);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem() instanceof ItemBaseRod;
	}
}
