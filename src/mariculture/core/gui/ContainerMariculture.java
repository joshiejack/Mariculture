package mariculture.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMariculture extends Container {	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return false;
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventory) {
		bindPlayerInventory(inventory, 0);
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventory, int yOffset) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, (84 + i * 18) + yOffset));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142 + yOffset));
		}
	}
	
	@Override
    public ItemStack slotClick(int slotID, int mouseButton, int modifier, EntityPlayer player) {
		Slot slot = (slotID < 0 || slotID > this.inventorySlots.size())? null: (Slot)this.inventorySlots.get(slotID);

	    if(slot instanceof SlotFake) {
	    	return ((SlotFake)slot).handle(player, mouseButton, slot);
	    }

        return super.slotClick(slotID, mouseButton, modifier, player);
    }
}
