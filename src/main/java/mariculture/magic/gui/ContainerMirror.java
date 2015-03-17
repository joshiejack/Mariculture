package mariculture.magic.gui;

import java.util.List;

import mariculture.core.Core;
import mariculture.core.gui.ContainerStorage;
import mariculture.core.gui.InventoryStorage;
import mariculture.core.items.ItemPearl;
import mariculture.magic.MirrorEnchantHelper;
import mariculture.magic.jewelry.ItemJewelry;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerMirror extends ContainerStorage {
    ItemStack mirror;

    public ContainerMirror(IInventory inventory, InventoryStorage storage, World world, ItemStack stack) {
        super(inventory, storage, world, 0);
        mirror = stack;
    }

    @Override
    public boolean enchantItem(EntityPlayer player, int levelToEnchant) {
        ItemStack stack = storage.getStackInSlot(3);
        if (levelToEnchant > 0 && stack != null && (player.experienceLevel >= levelToEnchant || player.capabilities.isCreativeMode)) {
            if (!player.worldObj.isRemote) {
                List list = MirrorEnchantHelper.buildEnchantmentList(player.worldObj.rand, stack, levelToEnchant, 0);
                boolean flag = stack.getItem() == Items.book;

                if (list != null) {
                    player.addExperienceLevel(-levelToEnchant);

                    if (flag) {
                        stack.func_150996_a(Items.enchanted_book);
                    }

                    int j = flag && list.size() > 1 ? player.worldObj.rand.nextInt(list.size()) : -1;
                    for (int k = 0; k < list.size(); ++k) {
                        EnchantmentData enchantmentdata = (EnchantmentData) list.get(k);
                        if (!flag || k != j) if (flag) {
                            Items.enchanted_book.addEnchantment(stack, enchantmentdata);
                        } else if (stack.getItem() == Core.pearls) {
                            ItemPearl pearl = (ItemPearl) stack.getItem();
                            Enchantment enchant = pearl.getBiasedEnchantment(player.worldObj.rand, levelToEnchant, stack.getItemDamage());
                            if (enchant != null) {
                                stack.addEnchantment(enchant, player.worldObj.rand.nextInt(enchant.getMaxLevel()) + 1);
                                break;
                            } else {
                                stack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
                            }
                        } else {
                            stack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
                        }
                    }

                    onCraftMatrixChanged(storage);
                }
            }

            return true;
        } else return false;
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

        if (!player.worldObj.isRemote) {
            ItemStack var2 = storage.getStackInSlotOnClosing(3);

            if (var2 != null) {
                player.dropPlayerItemWithRandomChoice(var2, false);
            }
            
            storage.setInventorySlotContents(3, null);
        }

        storage.closeInventory();
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        int size = 4;
        int low = size + 27;
        int high = low + 9;
        ItemStack newStack = null;
        final Slot slot = (Slot) inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            newStack = stack.copy();

            if (slotID < size) {
                if (!mergeItemStack(stack, size, high, true)) return null;
            } else if (stack.isItemEnchantable()) {
                if (((Slot) inventorySlots.get(3)).getHasStack()) return null;

                if (stack.hasTagCompound() && stack.stackSize == 1) {
                    ((Slot) inventorySlots.get(3)).putStack(stack.copy());
                    stack.stackSize = 0;
                } else if (stack.stackSize >= 1) {
                    ((Slot) inventorySlots.get(3)).putStack(new ItemStack(stack.getItem(), 1, stack.getItemDamage()));
                    --stack.stackSize;
                }
            } else if (stack.getItem() instanceof ItemJewelry && ((ItemJewelry) stack.getItem()).getType() == JewelryType.RING) {
                if (!mergeItemStack(stack, 0, 1, false)) return null;
            } else if (stack.getItem() instanceof ItemJewelry && ((ItemJewelry) stack.getItem()).getType() == JewelryType.BRACELET) {
                if (!mergeItemStack(stack, 1, 2, false)) return null;
            } else if (stack.getItem() instanceof ItemJewelry && ((ItemJewelry) stack.getItem()).getType() == JewelryType.NECKLACE) {
                if (!mergeItemStack(stack, 2, 3, false)) return null;
            } else if (slotID >= size && slotID < low) {
                if (!mergeItemStack(stack, low, high, false)) return null;
            } else if (slotID >= low && slotID < high && !mergeItemStack(stack, size, low, false)) return null;

            if (stack.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (stack.stackSize == newStack.stackSize) return null;

            slot.onPickupFromSlot(player, stack);
        }

        return newStack;
    }

    @Override
    public boolean shouldClose(int slotID, EntityPlayer player) {
        return false;
    }
}
