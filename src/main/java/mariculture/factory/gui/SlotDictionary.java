package mariculture.factory.gui;

import mariculture.core.gui.ContainerStorage;
import mariculture.core.gui.SlotFake;
import mariculture.core.handlers.OreDicHandler;
import mariculture.factory.Factory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class SlotDictionary extends SlotFake {
	public SlotDictionary(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		System.out.println("can insert");
		return (OreDicHandler.isInDictionary(stack) && OreDicHandler.isWhitelisted(stack)) || stack.itemID == Factory.filter.itemID;
	}

	@Override
	public ItemStack decrStackSize(int par1) {
		if (this.getHasStack()) {
			Math.min(par1, this.getStack().stackSize);
		}

		return super.decrStackSize(par1);
	}

	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return true;
	}
	
	public void updateOreDisplayTag(ItemStack stack) {
		NBTTagCompound tag = stack.stackTagCompound;
		if (tag == null) {
			tag = new NBTTagCompound();
		}
		
		if (!tag.hasKey("OreDictionaryDisplay")) {
			tag.setTag("OreDictionaryDisplay", new NBTTagCompound());
		}
		
		String old = "";
		NBTTagCompound display = tag.getCompoundTag("OreDictionaryDisplay");
		NBTTagList lore = display.getTagList("Lore");
		if(lore != null && lore.tagCount() > 0) {
			old = ((NBTTagString)lore.tagAt(0)).data;
		}

		if(old != null) {
			String next = OreDicHandler.getNextString(stack, old);
			display.setTag("Lore", OreDicHandler.addAllTags(stack, next));
			stack.stackTagCompound = tag;
		}
	}

	public ItemStack handle(EntityPlayer player, int mouseButton, Slot slot) {		
		ItemStack filterCheck = player.inventory.getItemStack();
		ItemStack filterInSlot = slot.getStack();
		if(filterCheck != null && filterCheck.itemID == Factory.filter.itemID) return player.openContainer instanceof ContainerStorage? null: filterCheck;
		if(filterInSlot != null && filterInSlot.itemID == Factory.filter.itemID) return player.openContainer instanceof ContainerStorage? null: filterInSlot;

		if (mouseButton == 1 && filterCheck == null) {
			slot.putStack(null);
		} else if(mouseButton == 2 && filterCheck == null) {
			ItemStack stackSlot = slot.getStack();
			ItemStack stackHeld = player.inventory.getItemStack();
			if (stackSlot != null && stackHeld == null) {
				updateOreDisplayTag(stackSlot);
			}
		} else {
			ItemStack stack;
			InventoryPlayer playerInv = player.inventory;
			slot.onSlotChanged();
			ItemStack stackSlot = slot.getStack();
			ItemStack stackHeld = playerInv.getItemStack();

			if (stackSlot == null && stackHeld != null) {
				if (OreDicHandler.isInDictionary(stackHeld)) {
					if (OreDicHandler.isWhitelisted(stackHeld)) {
						ItemStack copy = stackHeld.copy();
						copy.stackSize = 1;
						updateOreDisplayTag(copy);
						slot.putStack(copy);
					}
				}
			}

			if (stackSlot != null && stackHeld == null) {
				slot.putStack(OreDicHandler.getNextValidEntry(stackSlot));
			}
		}

		return null;
	}
}