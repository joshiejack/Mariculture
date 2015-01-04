package mariculture.factory.gui;

import mariculture.core.gui.ContainerStorage;
import mariculture.core.gui.SlotFake;
import mariculture.core.handlers.OreDicHandler;
import mariculture.factory.items.ItemFilter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class SlotDictionary extends SlotFake {
    public SlotDictionary(IInventory inv, int id, int x, int y) {
        super(inv, id, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return OreDicHandler.isInDictionary(stack) || stack.getItem() instanceof ItemFilter;
    }

    @Override
    public ItemStack decrStackSize(int par1) {
        if (getHasStack()) {
            Math.min(par1, getStack().stackSize);
        }

        return super.decrStackSize(par1);
    }

    @Override
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
        NBTTagList lore = display.getTagList("Lore", 8);
        if (lore != null && lore.tagCount() > 0) {
            old = lore.getStringTagAt(0);
        }

        if (old != null) {
            String next = OreDicHandler.getNextString(stack, old);
            display.setTag("Lore", OreDicHandler.addAllTags(stack, next));
            stack.stackTagCompound = tag;
        }
    }

    @Override
    public ItemStack handle(EntityPlayer player, int mouseButton, Slot slot) {
        ItemStack filterCheck = player.inventory.getItemStack();
        ItemStack filterInSlot = slot.getStack();
        if (filterCheck != null && filterCheck.getItem() instanceof ItemFilter) return player.openContainer instanceof ContainerStorage ? null : filterCheck;
        if (filterInSlot != null && filterInSlot.getItem() instanceof ItemFilter) return player.openContainer instanceof ContainerStorage ? null : filterInSlot;

        if (mouseButton == 1 && filterCheck == null) {
            slot.putStack(null);
        } else if (mouseButton == 2 && filterCheck == null) {
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

            if (stackSlot == null && stackHeld != null) if (OreDicHandler.isInDictionary(stackHeld)) {
                ItemStack copy = stackHeld.copy();
                copy.stackSize = 1;
                updateOreDisplayTag(copy);
                slot.putStack(copy);
            }

            if (stackSlot != null && stackHeld == null) {
                slot.putStack(OreDicHandler.getNextValidEntry(stackSlot));
            }
        }

        return null;
    }
}