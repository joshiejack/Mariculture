package joshie.mariculture.core.gui;

import java.util.Random;

import joshie.mariculture.core.items.ItemMCStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerStorage extends ContainerMariculture {
    protected final InventoryStorage storage;
    private final Random rand = new Random();

    public ContainerStorage(IInventory inventory, InventoryStorage storage, World world, int offset) {
        this.storage = storage;

        ItemMCStorage item = (ItemMCStorage) storage.player.getCurrentEquippedItem().getItem();
        for (int i = 0; i < item.size; i++) {
            addSlotToContainer(item.getSlot(storage, i));
        }

        bindPlayerInventory(inventory, offset);
    }

    private void bindPlayerInventory(IInventory playerInventory, int yOffset) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + yOffset));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142 + yOffset));
        }
    }

    @Override
    public ItemStack slotClick(int slotID, int par2, int par3, EntityPlayer player) {
        if (shouldClose(slotID, player)) {
            player.openContainer = player.inventoryContainer;
            return null;
        }

        return super.slotClick(slotID, par2, par3, player);
    }

    public boolean shouldClose(int slotID, EntityPlayer player) {
        boolean stop = false;
        Slot slot = slotID < 0 || slotID > inventorySlots.size() ? null : (Slot) inventorySlots.get(slotID);

        if (slot != null) {
            slot.getStack();
            if (player.getCurrentEquippedItem() != null && slot.getHasStack() && ItemStack.areItemStacksEqual(slot.getStack(), player.getCurrentEquippedItem())) {
                stop = true;
            }
        }

        return stop;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return storage.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        int size = storage.getSizeInventory();
        int low = size + 27;
        int high = low + 9;
        ItemStack newStack = null;
        final Slot slot = (Slot) inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            newStack = stack.copy();

            if (slotID < size) {
                if (!mergeItemStack(stack, size, high, true)) return null;
            } else if (!(stack.getItem() instanceof ItemMCStorage) && ((ItemMCStorage) player.getCurrentEquippedItem().getItem()).isItemValid(stack)) {
                if (!mergeItemStack(stack, 0, storage.getSizeInventory(), false)) return null;
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
    public void onCraftMatrixChanged(IInventory par1IInventory) {
        detectAndSendChanges();
    }

    @Override
    public void onContainerClosed(final EntityPlayer player) {
        super.onContainerClosed(player);
        storage.closeInventory();
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
