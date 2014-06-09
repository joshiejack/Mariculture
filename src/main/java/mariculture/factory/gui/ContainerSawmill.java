package mariculture.factory.gui;

import mariculture.api.core.IItemUpgrade;
import mariculture.core.gui.ContainerMachine;
import mariculture.core.gui.SlotOutput;
import mariculture.factory.blocks.BlockItemCustom;
import mariculture.factory.items.ItemPlan;
import mariculture.factory.tile.TileSawmill;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ContainerSawmill extends ContainerMachine {
    public TileSawmill tile;

    public ContainerSawmill(TileSawmill tile, InventoryPlayer playerInventory) {
        super(tile);
        this.tile = tile;

        addUpgradeSlots(tile);

        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new SlotPlan(tile, i + 3, 11, 20 + i * 20));
        }

        addSlotToContainer(new SlotBlock(tile, TileSawmill.TOP, 61, 22));
        addSlotToContainer(new SlotBlock(tile, TileSawmill.NORTH, 43, 40));
        addSlotToContainer(new SlotBlock(tile, TileSawmill.EAST, 61, 40));
        addSlotToContainer(new SlotBlock(tile, TileSawmill.SOUTH, 79, 40));
        addSlotToContainer(new SlotBlock(tile, TileSawmill.WEST, 97, 40));
        addSlotToContainer(new SlotBlock(tile, TileSawmill.BOTTOM, 61, 58));
        addSlotToContainer(new SlotOutput(tile, TileSawmill.OUT, 149, 40));

        bindPlayerInventory(playerInventory, 10);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        int size = getSizeInventory();
        int low = size + 27;
        int high = low + 9;
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(slotID);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            itemstack = stack.copy();

            if (slotID < size) {
                if (!mergeItemStack(stack, size, high, true)) return null;

                slot.onSlotChange(stack, itemstack);
            } else if (slotID >= size) {
                if (stack.getItem() instanceof ItemPlan) {
                    if (!mergeItemStack(stack, 3, 6, false)) return null;
                } else if (stack.getItem() instanceof IItemUpgrade) {
                    if (!mergeItemStack(stack, 0, 3, false)) return null;
                } else if (stack.getItem() instanceof ItemBlock && !(stack.getItem() instanceof BlockItemCustom) || stack.getItem() == Items.feather) {
                    if (!mergeItemStack(stack, 6, 12, false)) // 6-11
                    return null;
                } else if (slotID >= size && slotID < low) {
                    if (!mergeItemStack(stack, low, high, false)) return null;
                } else if (slotID >= low && slotID < high && !mergeItemStack(stack, high, low, false)) return null;
            } else if (!mergeItemStack(stack, size, high, false)) return null;

            if (stack.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (stack.stackSize == itemstack.stackSize) return null;

            slot.onPickupFromSlot(player, stack);
        }

        return itemstack;
    }

    @Override
    public ItemStack slotClick(int slotID, int mouseButton, int modifier, EntityPlayer player) {
        Slot slot = slotID < 0 || slotID > inventorySlots.size() ? null : (Slot) inventorySlots.get(slotID);
        if (mouseButton == 1 && modifier == 0 && slot instanceof SlotPlan) {
            tile.selected = slot.slotNumber;
            if (slot.getStack() != null) return null;
        }

        return super.slotClick(slotID, mouseButton, modifier, player);
    }

    public class SlotBlock extends Slot {
        private EntityPlayer thePlayer;
        private int field_75228_b;

        public SlotBlock(IInventory inv, int id, int x, int y) {
            super(inv, id, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            if (stack.getItem() instanceof BlockItemCustom) return false;

            return stack.getItem() instanceof ItemBlock || stack.getItem() == Items.feather;
        }

        @Override
        public ItemStack decrStackSize(final int par1) {
            if (getHasStack()) {
                field_75228_b += Math.min(par1, getStack().stackSize);
            }

            return super.decrStackSize(par1);
        }
    }

    private class SlotPlan extends Slot {
        public SlotPlan(IInventory inv, int id, int x, int y) {
            super(inv, id, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem() instanceof ItemPlan;
        }
    }
}
