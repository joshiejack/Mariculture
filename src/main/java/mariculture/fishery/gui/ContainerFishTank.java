package mariculture.fishery.gui;

import mariculture.api.core.ISpecialSorting;
import mariculture.core.gui.ContainerMachine;
import mariculture.core.gui.SlotFake;
import mariculture.fishery.items.ItemFishy;
import mariculture.fishery.tile.TileFishTank;
import mariculture.lib.helpers.ClientHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFishTank extends ContainerMachine {
    public TileFishTank tank;

    public ContainerFishTank(TileFishTank tile, InventoryPlayer playerInventory) {
        super(tile);
        this.tank = tile;

        int i = (6 - 4) * 18;
        int j;
        int k;

        for (j = 0; j < 6; ++j) {
            for (k = 0; k < 9; ++k) {
                addSlotToContainer(new SlotFishTank(tile, k + j * 9, 8 + k * 18, 16 + j * 18));
            }
        }

        bindPlayerInventory(playerInventory, 52);
    }

    @Override
    public int getSizeInventory() {
        return ((IInventory) tile).getSizeInventory();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return ((IInventory) tile).isUseableByPlayer(player);
    }

    public static boolean isFishEqual(ItemStack fish1, ItemStack fish2) {
        if (fish1.getItem() instanceof ISpecialSorting) {
            if (((ISpecialSorting) fish1.getItem()).isSame(fish1, fish2, true)) return true;
        }

        return false;
    }

    long lastClick = 0L;

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        Slot slot = ((Slot) inventorySlots.get(slotID));
        ItemStack itemShifting = ((Slot) inventorySlots.get(slotID)).getStack();
        //So we look for the same fish item stack first, if we fail then we do the loop again, but look for an empty slot
        if (itemShifting != null && itemShifting.getItem() instanceof ItemFishy) {
            long thisClick = System.currentTimeMillis();
            long difference = thisClick - lastClick;
            lastClick = thisClick;
            //This is for slots outside the inventory
            if (slotID >= 54) {
                for (int i = 0; i < tile.getInventory().length; i++) {
                    ItemStack inSlot = tile.getInventory()[i];
                    if (inSlot == null) continue; //First time we go through, we search for the same stuff
                    if (isFishEqual(itemShifting, inSlot)) {
                        ItemStack cloned = inSlot.copy();
                        cloned.stackSize += itemShifting.stackSize;
                        tank.setInventorySlotContents(i, cloned);
                        slot.putStack(null);
                        return null;
                    }
                }

                //New fishies
                for (int i = 0; i < tile.getInventory().length; i++) {
                    ItemStack inSlot = tile.getInventory()[i];
                    if (inSlot != null) continue; //First time we go through, we search for the same stuff
                    ItemStack cloned = itemShifting.copy();
                    cloned.stackSize = itemShifting.stackSize;
                    tank.setInventorySlotContents(i, cloned);
                    slot.putStack(null);
                    return null;
                }
            } else {
                //54 > 89
                for (int i = 54; i <= 89; i++) {
                    Slot thisSlot = ((Slot) inventorySlots.get(i));
                    ItemStack inSlot = thisSlot.getStack();
                    if (inSlot == null) continue;
                    if (isFishEqual(itemShifting, inSlot)) {
                        int maxReduce = Math.min(64, itemShifting.stackSize);
                        int maxAccept = Math.max(0, 64 - inSlot.stackSize);
                        int reduce = Math.min(maxAccept, maxReduce);
                        if (reduce > 0) {
                            ItemStack cloned = inSlot.copy();
                            cloned.stackSize += reduce;
                            thisSlot.putStack(cloned);
                            thisSlot.onSlotChanged();

                            ItemStack inventoryStack = itemShifting.copy();
                            inventoryStack.stackSize -= reduce;
                            if (inventoryStack.stackSize <= 0) inventoryStack = null;
                            slot.putStack(inventoryStack);
                            return null;
                        }
                    }
                }

                //Step two attempt to place the item in empty slot
                for (int i = 54; i <= 89; i++) {
                    Slot thisSlot = ((Slot) inventorySlots.get(i));
                    ItemStack inSlot = thisSlot.getStack();
                    if (inSlot != null) continue;
                    int reduce = Math.min(64, itemShifting.stackSize);
                    if (reduce > 0) {
                        ItemStack cloned = itemShifting.copy();
                        cloned.stackSize -= reduce;
                        if (cloned.stackSize <= 0) cloned = null;
                        slot.putStack(cloned);
                        slot.onSlotChanged();

                        ItemStack inventoryStack = itemShifting.copy();
                        inventoryStack.stackSize = reduce;
                        thisSlot.putStack(inventoryStack);
                        return null;
                    }
                }
            }
        }

        return itemShifting;
    }

    private class SlotFishTank extends SlotFake {
        private TileFishTank tank;
        
        public SlotFishTank(TileFishTank invent, int slot, int x, int y) {
            super(invent, slot, x, y);
            this.tank = invent;
        }

        @Override
        public ItemStack getStack() {
            return super.getStack();
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem() instanceof ItemFishy;
        }

        @Override
        public ItemStack handle(EntityPlayer player, int mouseButton, Slot slot) {
            if (ClientHelper.isShiftPressed()) return slot.getStack();
            ItemStack held = player.inventory.getItemStack();
            ItemStack inSlot = slot.getStack();

            if (held != null && inSlot != null) {
                if (isFishEqual(held, inSlot)) {
                    ItemStack cloned = held.copy();
                    cloned.stackSize = held.stackSize + inSlot.stackSize;
                    slot.putStack(cloned);
                    slot.onSlotChanged();
                    player.inventory.setItemStack(null);
                }
            } else if (inSlot == null && held != null) {
                slot.putStack(held.copy());
                slot.onSlotChanged();
                player.inventory.setItemStack(null);
            } else if (inSlot != null && held == null) {
                int take = Math.min(64, inSlot.stackSize);
                if (mouseButton == 1) take /= 2;

                ItemStack cloned = inSlot.copy();
                cloned.stackSize = take;
                ItemStack newSlot = inSlot.copy();
                newSlot.stackSize -= take;
                if (newSlot.stackSize <= 0) newSlot = null;
                slot.putStack(newSlot);
                slot.onSlotChanged();
                player.inventory.setItemStack(cloned);
            }

            return null;
        }
    }
}