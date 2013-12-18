package mariculture.core.gui;

import java.util.Random;

import mariculture.core.items.ItemStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerStorage extends ContainerMariculture {
	private final InventoryStorage storage;
	private final Random rand = new Random();

	public ContainerStorage(IInventory inventory, InventoryStorage storage, World world) {
		this.storage = storage;
		
		ItemStorage item = (ItemStorage) storage.player.getCurrentEquippedItem().getItem();
		for(int i = 0; i < item.size; i++) {
			this.addSlotToContainer(item.getSlot(storage, i));
		}
		
		bindPlayerInventory(inventory);
	}
	
	private void bindPlayerInventory(IInventory playerInventory) {
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
	public ItemStack slotClick(int slotID, int par2, int par3, EntityPlayer player) {
		boolean stop = false;
		Slot slot = (slotID < 0 || slotID > this.inventorySlots.size())? null: (Slot)this.inventorySlots.get(slotID);

		if(slot != null) {
			if(player.getCurrentEquippedItem() != null && slot.getHasStack() && slot.getStack().areItemStacksEqual(slot.getStack(), player.getCurrentEquippedItem())) {
				stop = true;
			}
		}

		if(stop) {
			player.closeScreen();
			return null;
		}
		
		return super.slotClick(slotID, par2, par3, player);
    }

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.storage.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		int size = storage.getSizeInventory();
		int low = size + 27;
		int high = low + 9;
		ItemStack newStack = null;
		final Slot slot = (Slot) this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			newStack = stack.copy();

			if (slotID < size) {
				if (!this.mergeItemStack(stack, size, high, true)) {
					return null;
				}
			} else {
				if (!(stack.getItem() instanceof ItemStorage) && ((ItemStorage)player.getCurrentEquippedItem().getItem()).isItemValid(stack)) {
					if (!this.mergeItemStack(stack, 0, storage.getSizeInventory(), false)) {
						return null;
					}
				} else if (slotID >= size && slotID < low) {
					if (!this.mergeItemStack(stack, low, high, false)) {
						return null;
					}
				} else if (slotID >= low && slotID < high && !this.mergeItemStack(stack, size, low, false)) {
					return null;
				}
			}

			if (stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (stack.stackSize == newStack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, stack);
		}

		return newStack;
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory) {
		this.detectAndSendChanges();
	}
	
	@Override
	public void onContainerClosed(final EntityPlayer player) {
		super.onContainerClosed(player);
		this.storage.closeChest();
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < crafters.size(); i++) {
			storage.sendGUINetworkData(this, (ICrafting) crafters.get(i));
		}
	}
	
	@Override
	public void updateProgressBar(int id, int val) {
		storage.getGUINetworkData(id, val);
	}
}
