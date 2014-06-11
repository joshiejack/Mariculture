package mariculture.core.helpers.cofh;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public class InventoryHelper {

    private InventoryHelper() {}

    /**
     * Add an ItemStack to an inventory. Return true if the entire stack was added.
     * 
     * @param inventory
     *            The inventory.
     * @param stack
     *            ItemStack to add.
     * @param startIndex
     *            First slot to attempt to add into. Does not loop around fully.
     */
    private static boolean addItemStackToInventory(ItemStack[] inventory, ItemStack stack, int startIndex) {
        if (stack == null) return true;
        int openSlot = -1;
        for (int i = startIndex; i < inventory.length; i++)
            if (ItemHelper.areItemStacksEqualWithNBT(stack, inventory[i]) && inventory[i].getMaxStackSize() > inventory[i].stackSize) {
                int hold = inventory[i].getMaxStackSize() - inventory[i].stackSize;
                if (hold >= stack.stackSize) {
                    inventory[i].stackSize += stack.stackSize;
                    stack = null;
                    return true;
                } else {
                    stack.stackSize -= hold;
                    inventory[i].stackSize += hold;
                }
            } else if (inventory[i] == null && openSlot == -1) {
                openSlot = i;
            }
        if (stack != null) if (openSlot > -1) {
            inventory[openSlot] = stack;
        } else return false;
        return true;
    }

    //Copy of the above, but instead of a start index, able to specify WHICH slots
    public static boolean addItemStackToInventory(ItemStack[] inventory, ItemStack stack, int[] slots) {
        if (stack == null) return true;
        int openSlot = -1;
        for (int i : slots)
            if (ItemHelper.areItemStacksEqualWithNBT(stack, inventory[i]) && inventory[i].getMaxStackSize() > inventory[i].stackSize) {
                int hold = inventory[i].getMaxStackSize() - inventory[i].stackSize;
                if (hold >= stack.stackSize) {
                    inventory[i].stackSize += stack.stackSize;
                    stack = null;
                    return true;
                } else {
                    stack.stackSize -= hold;
                    inventory[i].stackSize += hold;
                }
            } else if (inventory[i] == null && openSlot == -1) {
                openSlot = i;
            }
        if (stack != null) if (openSlot > -1) {
            inventory[openSlot] = stack;
        } else return false;
        return true;
    }

    //Copy of the above but simulates
    public static boolean canAddItemStackToInventory(ItemStack[] inventory, ItemStack stack, int[] slots) {
        if (stack == null) return true;
        int openSlot = -1;
        for (int i : slots)
            if (ItemHelper.areItemStacksEqualWithNBT(stack, inventory[i]) && inventory[i].getMaxStackSize() > inventory[i].stackSize) return true;
            else if (inventory[i] == null && openSlot == -1) {
                openSlot = i;
            }

        if (openSlot > -1) return true;
        else return false;
    }

    /* IInventoryHandler Interaction */

    /* IIInventory Interaction */
    public static ItemStack extractItemStackFromInventory(IInventory theInventory, int side) {
        ItemStack retStack = null;

        if (theInventory instanceof ISidedInventory) {
            ISidedInventory sidedInv = (ISidedInventory) theInventory;
            int slots[] = sidedInv.getAccessibleSlotsFromSide(side);
            for (int i = 0; i < slots.length && retStack == null; i++)
                if (sidedInv.getStackInSlot(i) != null && sidedInv.canExtractItem(i, sidedInv.getStackInSlot(i), side)) {
                    retStack = sidedInv.getStackInSlot(i).copy();
                    sidedInv.setInventorySlotContents(i, null);
                }
        } else {
            for (int i = 0; i < theInventory.getSizeInventory() && retStack == null; i++)
                if (theInventory.getStackInSlot(i) != null) {
                    retStack = theInventory.getStackInSlot(i).copy();
                    theInventory.setInventorySlotContents(i, null);
                }
        }
        if (retStack != null) {
            theInventory.markDirty();
        }
        return retStack;
    }

    public static ItemStack insertItemStackIntoInventory(IInventory theInventory, ItemStack stack, int side) {
        if (stack == null) return null;

        int stackSize = stack.stackSize;

        if (theInventory instanceof ISidedInventory) {
            ISidedInventory sidedInv = (ISidedInventory) theInventory;
            int slots[] = sidedInv.getAccessibleSlotsFromSide(side);

            if (slots == null) return stack;
            for (int i = 0; i < slots.length && stack != null; i++)
                if (sidedInv.canInsertItem(slots[i], stack, side) && ItemHelper.itemsEqualWithMetadata(stack, theInventory.getStackInSlot(slots[i]), true)) {
                    stack = addToOccupiedInventorySlot(sidedInv, slots[i], stack);
                }
            for (int i = 0; i < slots.length && stack != null; i++)
                if (sidedInv.canInsertItem(slots[i], stack, side) && theInventory.getStackInSlot(slots[i]) == null) {
                    stack = addToEmptyInventorySlot(sidedInv, slots[i], stack);
                }
        } else {
            int invSize = theInventory.getSizeInventory();
            for (int i = 0; i < invSize && stack != null; i++)
                if (ItemHelper.itemsEqualWithMetadata(stack, theInventory.getStackInSlot(i), true)) {
                    stack = addToOccupiedInventorySlot(theInventory, i, stack);
                }

            for (int i = 0; i < invSize && stack != null; i++)
                if (theInventory.getStackInSlot(i) == null) {
                    stack = addToEmptyInventorySlot(theInventory, i, stack);
                }
        }

        if (stack == null || stack.stackSize != stackSize) {
            theInventory.markDirty();
        }

        return stack;
    }

    /* Slot Interaction */
    private static ItemStack addToEmptyInventorySlot(IInventory theInventory, int slot, ItemStack stack) {
        if (!theInventory.isItemValidForSlot(slot, stack)) return stack;
        int stackLimit = theInventory.getInventoryStackLimit();
        theInventory.setInventorySlotContents(slot, ItemHelper.cloneStack(stack, Math.min(stack.stackSize, stackLimit)));
        return stackLimit >= stack.stackSize ? null : stack.splitStack(stack.stackSize - stackLimit);
    }

    private static ItemStack addToOccupiedInventorySlot(IInventory theInventory, int slot, ItemStack stack) {

        ItemStack stackInSlot = theInventory.getStackInSlot(slot);
        int stackLimit = Math.min(theInventory.getInventoryStackLimit(), stackInSlot.getMaxStackSize());

        if (stack.stackSize + stackInSlot.stackSize > stackLimit) {
            int stackDiff = stackLimit - stackInSlot.stackSize;
            stackInSlot.stackSize = stackLimit;
            stack.stackSize -= stackDiff;
            theInventory.setInventorySlotContents(slot, stackInSlot);
            return stack;
        }
        stackInSlot.stackSize += Math.min(stack.stackSize, stackLimit);
        theInventory.setInventorySlotContents(slot, stackInSlot);
        return stackLimit >= stack.stackSize ? null : stack.splitStack(stack.stackSize - stackLimit);
    }

    private static ItemStack simulateAddToEmptyInventorySlot(IInventory theInventory, int slot, ItemStack stack) {
        if (!theInventory.isItemValidForSlot(slot, stack)) return stack;
        int stackLimit = theInventory.getInventoryStackLimit();
        return stackLimit >= stack.stackSize ? null : stack.splitStack(stack.stackSize - stackLimit);
    }

    private static ItemStack simulateAddToOccupiedInventorySlot(IInventory theInventory, int slot, ItemStack stack) {
        ItemStack stackInSlot = theInventory.getStackInSlot(slot);
        int stackLimit = Math.min(theInventory.getInventoryStackLimit(), stackInSlot.getMaxStackSize());

        if (stack.stackSize + stackInSlot.stackSize > stackLimit) {
            stack.stackSize -= stackLimit - stackInSlot.stackSize;
            return stack;
        }
        return stackLimit >= stack.stackSize ? null : stack.splitStack(stack.stackSize - stackLimit);
    }

}
