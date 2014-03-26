package mariculture.magic.gui;

import java.util.List;

import mariculture.core.gui.ContainerStorage;
import mariculture.core.gui.InventoryStorage;
import mariculture.core.lib.Jewelry;
import mariculture.core.util.Rand;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerMirror extends ContainerStorage {
	ItemStack mirror;
	public ContainerMirror(IInventory inventory, InventoryStorage storage, World world, ItemStack stack) {
		super(inventory, storage, world);
		this.mirror = stack;
	}
	
	@Override
	public boolean enchantItem(EntityPlayer player, int levelToEnchant) {
		ItemStack itemToEnchant = storage.getStackInSlot(3);
		if ((levelToEnchant > 0) && itemToEnchant != null 
				&& (player.experienceLevel >= levelToEnchant || player.capabilities.isCreativeMode)) {

			if (!player.worldObj.isRemote) {
				List var4 = MirrorEnchantmentHelper.buildEnchantmentList(Rand.rand, itemToEnchant, levelToEnchant, mirror);
				boolean var5 = itemToEnchant.itemID == Item.book.itemID;

				if (var4 != null) {
					player.addExperienceLevel(-levelToEnchant);

					if (var5) {
						itemToEnchant.itemID = Item.enchantedBook.itemID;
					}

					int var6 = var5 ? Rand.rand.nextInt(var4.size()) : -1;

					for (int var7 = 0; var7 < var4.size(); ++var7) {
						EnchantmentData enchantData = (EnchantmentData) var4.get(var7);

						if (!var5 || var7 == var6) {
							if (var5) {
								Item.enchantedBook.addEnchantment(itemToEnchant, enchantData);
							} else {
								itemToEnchant.addEnchantment(enchantData.enchantmentobj, enchantData.enchantmentLevel);
							}
						}
					}

					onCraftMatrixChanged(storage);
				}
			}

			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void onContainerClosed(final EntityPlayer player) {
		super.onContainerClosed(player);

		if (!player.worldObj.isRemote) {
			ItemStack var2 = storage.getStackInSlotOnClosing(3);

			if (var2 != null) {
				player.dropPlayerItem(var2);
				storage.setInventorySlotContents(3, null);
			}
		}

		storage.onInventoryChanged();
	}
	
	@Override
	public ItemStack transferStackInSlot(final EntityPlayer player, final int slotID) {
		int size = 4;
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
				if (stack.isItemEnchantable()) {
					if (!this.mergeItemStack(stack, 3, 4, false)) {
						return null;
					}
				} else if (stack.getItem() instanceof ItemJewelry
						&& ((ItemJewelry) stack.getItem()).getType() == Jewelry.RING) {
					if (!this.mergeItemStack(stack, 0, 1, false)) {
						return null;
					}
				} else if (stack.getItem() instanceof ItemJewelry
						&& ((ItemJewelry) stack.getItem()).getType() == Jewelry.BRACELET) {
					if (!this.mergeItemStack(stack, 1, 2, false)) {
						return null;
					}
				} else if (stack.getItem() instanceof ItemJewelry
						&& ((ItemJewelry) stack.getItem()).getType() == Jewelry.NECKLACE) {
					if (!this.mergeItemStack(stack, 2, 3, false)) {
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
	public boolean shouldClose(int slotID, EntityPlayer player) {
		return false;
    }
}
