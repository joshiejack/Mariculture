package mariculture.magic.gui;

import java.util.List;

import mariculture.core.Core;
import mariculture.core.gui.ContainerStorage;
import mariculture.core.gui.InventoryStorage;
import mariculture.core.items.ItemPearl;
import mariculture.core.util.Rand;
import mariculture.magic.jewelry.ItemJewelry;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
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
		if ((levelToEnchant > 0) && itemToEnchant != null  && (player.experienceLevel >= levelToEnchant || player.capabilities.isCreativeMode)) {
			if (!player.worldObj.isRemote) {
				List var4 = EnchantmentHelper.buildEnchantmentList(Rand.rand, itemToEnchant, levelToEnchant);
				boolean var5 = itemToEnchant.getItem() == Items.book;
				if (var4 != null) {
					player.addExperienceLevel(-levelToEnchant);

					if (var5) {
						itemToEnchant = new ItemStack(Items.enchanted_book);
					}

					int var6 = var5 ? Rand.rand.nextInt(var4.size()) : -1;

					for (int var7 = 0; var7 < var4.size(); ++var7) {
						EnchantmentData enchantData = (EnchantmentData) var4.get(var7);

						if (!var5 || var7 == var6) {
							if (var5) {
								Items.enchanted_book.addEnchantment(itemToEnchant, enchantData);
							} else {
								if(itemToEnchant.getItem() == Core.pearls) {
									ItemPearl pearl = (ItemPearl) itemToEnchant.getItem();
									Enchantment enchant = pearl.getBiasedEnchantment(Rand.rand, levelToEnchant, itemToEnchant.getItemDamage());
									if(enchant != null) {
										itemToEnchant.addEnchantment(enchant, enchant.getMaxLevel()); break;
									} else itemToEnchant.addEnchantment(enchantData.enchantmentobj, enchantData.enchantmentLevel);
								} else itemToEnchant.addEnchantment(enchantData.enchantmentobj, enchantData.enchantmentLevel);
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
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);

		if (!player.worldObj.isRemote) {
			ItemStack var2 = storage.getStackInSlotOnClosing(3);

			if (var2 != null) {
				player.dropPlayerItemWithRandomChoice(var2, false);
			}
		}

		storage.closeInventory();
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
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
					if(((Slot)this.inventorySlots.get(3)).getHasStack()) {
						return null;
					}
					
					if (stack.hasTagCompound() && stack.stackSize == 1) {
	                    ((Slot)this.inventorySlots.get(3)).putStack(stack.copy());
	                    stack.stackSize = 0;
	                }
	                else if (stack.stackSize >= 1) {
	                    ((Slot)this.inventorySlots.get(3)).putStack(new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
	                    --stack.stackSize;
	                }
				} else if (stack.getItem() instanceof ItemJewelry
						&& ((ItemJewelry) stack.getItem()).getType() == JewelryType.RING) {
					if (!this.mergeItemStack(stack, 0, 1, false)) {
						return null;
					}
				} else if (stack.getItem() instanceof ItemJewelry
						&& ((ItemJewelry) stack.getItem()).getType() == JewelryType.BRACELET) {
					if (!this.mergeItemStack(stack, 1, 2, false)) {
						return null;
					}
				} else if (stack.getItem() instanceof ItemJewelry
						&& ((ItemJewelry) stack.getItem()).getType() == JewelryType.NECKLACE) {
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
