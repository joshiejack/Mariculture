package mariculture.core.gui;

import mariculture.core.blocks.TileOldOyster;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerOyster extends Container {
	private final TileOldOyster tileEntityDispenser;

	public ContainerOyster(TileOldOyster tileOyster, InventoryPlayer playerInventory) {
		this.tileEntityDispenser = tileOyster;

		addSlotToContainer(new Slot(tileOyster, 0, 77, 41));
		bindPlayerInventory(playerInventory);
	}

	private void bindPlayerInventory(final InventoryPlayer playerInventory) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(final EntityPlayer par1EntityPlayer) {
		return this.tileEntityDispenser.isUseableByPlayer(par1EntityPlayer);
	}

	@Override
	public ItemStack transferStackInSlot(final EntityPlayer par1EntityPlayer, final int par2) {
		return null;
	}
}