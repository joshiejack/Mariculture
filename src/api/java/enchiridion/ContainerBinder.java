package enchiridion;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ContainerBinder extends Container {
	protected final InventoryBinder storage;

	public ContainerBinder(IInventory inventory, InventoryBinder storage) {
		this.storage = storage;

		for (int i = 0; i < 7; i++) {
			this.addSlotToContainer(new BookSlot(storage, i, 17 + (i * 21), 11));
		}

		for (int i = 0; i < 7; i++) {
			this.addSlotToContainer(new BookSlot(storage, i + 7, 17 + (i * 21), 36));
		}

		for (int i = 0; i < 7; i++) {
			this.addSlotToContainer(new BookSlot(storage, i + 14, 17 + (i * 21), 61));
		}

		bindPlayerInventory(inventory, 10);
	}

	private void bindPlayerInventory(IInventory inventory, int yOffset) {
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
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		int size = 21;
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
				if (isBook(stack)) {
					for (int i = 0; i < 21; i++) {
						if (((Slot) this.inventorySlots.get(i)).getHasStack()) {
							continue;
						}

						if (stack.hasTagCompound() && stack.stackSize == 1) {
							((Slot) this.inventorySlots.get(i)).putStack(stack.copy());
							stack.stackSize = 0;
							break;
						} else if (stack.stackSize >= 1) {
							((Slot) this.inventorySlots.get(i)).putStack(new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
							--stack.stackSize;
							break;
						}
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
	public boolean canInteractWith(EntityPlayer player) {
		return this.storage.isUseableByPlayer(player);
	}

	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory) {
		this.detectAndSendChanges();
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		this.storage.closeInventory();
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

	public static boolean isBook(ItemStack stack) {
		String name = Item.itemRegistry.getNameForObject(stack.getItem());
		if(name != null) {
			for(String bad: Config.removals) {
				if(name.equals(bad)) return false;
			}
			
			for(String good: Config.additions) {
				if(name.contains(good)) return true;
			}
		}

		return stack.getItem() instanceof ItemEnchiridion && stack.getItemDamage() == ItemEnchiridion.GUIDE;
	}

	@Override
	public ItemStack slotClick(int slotID, int button, int par3, EntityPlayer player) {
		Slot slot = (slotID < 0 || slotID > this.inventorySlots.size()) ? null : (Slot) this.inventorySlots.get(slotID);
		if (slot != null && shouldClose(slot, player)) return null;
		if (slot != null && slot.getStack() != null && button == 1 && slotID < 21 && par3 == 0) {
			ItemStack stack = slot.getStack().copy();
			if (stack != null && isBook(stack) && player.worldObj.isRemote) {
				ItemStack held = player.getCurrentEquippedItem().copy();
				player.setCurrentItemOrArmor(0, stack);
				player.inventoryContainer.detectAndSendChanges();
				player.getCurrentEquippedItem().getItem().onItemRightClick(player.getCurrentEquippedItem(), player.worldObj, player);
				player.setCurrentItemOrArmor(0, held);
				player.inventoryContainer.detectAndSendChanges();
			}

			return null;
		}

		return super.slotClick(slotID, button, par3, player);
	}

	public boolean shouldClose(Slot slot, EntityPlayer player) {
		if (player.getCurrentEquippedItem() != null && slot.getHasStack() && slot.getStack().areItemStacksEqual(slot.getStack(), player.getCurrentEquippedItem())) {
			return true;
		} else
			return false;
	}

	public static class BookSlot extends Slot {
		public BookSlot(IInventory inventory, int id, int x, int y) {
			super(inventory, id, x, y);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return isBook(stack);
		}

		@Override
		public int getSlotStackLimit() {
			return 1;
		}
	}
}
