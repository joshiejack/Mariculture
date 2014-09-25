package joshie.mariculture.fishery.gui;

import joshie.mariculture.core.gui.ContainerMachine;
import joshie.mariculture.fishery.items.ItemFishy;
import joshie.mariculture.fishery.tile.FishTankData;
import joshie.mariculture.fishery.tile.TileFishTank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFishTank extends ContainerMachine {
    public final TileFishTank tile;

    public ContainerFishTank(TileFishTank tile, InventoryPlayer playerInventory) {
        super(tile);
        this.tile = tile;
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

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
        return null;

    }

    @Override
    public ItemStack slotClick(int id, int button, int method, EntityPlayer player) {
        if (id >= 54 || id < 0) return super.slotClick(id, button, method, player);
        ItemStack heldStack = player.inventory.getItemStack();
        if(button == 2) {
            tile.alphabetise();
        }
        
        if (id >= 0 && (player.capabilities.isCreativeMode || (!player.capabilities.isCreativeMode && button != 2))) {
            Slot slot = (Slot) this.inventorySlots.get(id);
            ItemStack slotStack = slot.getStack();
            if (heldStack == null) {
                if (slotStack != null) {
                    ItemStack clone = slotStack.copy();
                    ItemStack pick = clone.copy();
                    int size = button == 1? clone.getMaxStackSize() / 2: clone.getMaxStackSize();
                    if (clone.stackSize > size) {
                        clone.stackSize -= size;
                        pick.stackSize = size;
                    } else clone = null;

                    player.inventory.setItemStack(pick);
                    if(!player.capabilities.isCreativeMode || button != 2) {
                        slot.putStack(clone);
                    }
                }
            } else {
                int aSlot = tile.getSlotForFish(heldStack);
                if (aSlot != -1) {
                    FishTankData data = tile.fishies[aSlot];
                    data.add(heldStack.stackSize);
                    player.inventory.setItemStack((ItemStack) null);
                }
            }

            return slotStack;
        }

        return null;

        /*ItemStack itemstack = null;
        InventoryPlayer inventoryplayer = player.inventory;
        int i1;
        ItemStack slotStack;

        if (method == 5)
        {
            int l = this.field_94536_g;
            this.field_94536_g = func_94532_c(button);

            if ((l != 1 || this.field_94536_g != 2) && l != this.field_94536_g)
            {
                this.func_94533_d();
            }
            else if (inventoryplayer.getItemStack() == null)
            {
                this.func_94533_d();
            }
            else if (this.field_94536_g == 0)
            {
                this.field_94535_f = func_94529_b(button);

                if (func_94528_d(this.field_94535_f))
                {
                    this.field_94536_g = 1;
                    this.field_94537_h.clear();
                }
                else
                {
                    this.func_94533_d();
                }
            }
            else if (this.field_94536_g == 1)
            {
                Slot slot = (Slot)this.inventorySlots.get(id);

                if (slot != null && func_94527_a(slot, inventoryplayer.getItemStack(), true) && slot.isItemValid(inventoryplayer.getItemStack()) && inventoryplayer.getItemStack().stackSize > this.field_94537_h.size() && this.canDragIntoSlot(slot))
                {
                    this.field_94537_h.add(slot);
                }
            }
            else if (this.field_94536_g == 2)
            {
                if (!this.field_94537_h.isEmpty())
                {
                    slotStack = inventoryplayer.getItemStack().copy();
                    i1 = inventoryplayer.getItemStack().stackSize;
                    Iterator iterator = this.field_94537_h.iterator();

                    while (iterator.hasNext())
                    {
                        Slot slot1 = (Slot)iterator.next();

                        if (slot1 != null && func_94527_a(slot1, inventoryplayer.getItemStack(), true) && slot1.isItemValid(inventoryplayer.getItemStack()) && inventoryplayer.getItemStack().stackSize >= this.field_94537_h.size() && this.canDragIntoSlot(slot1))
                        {
                            ItemStack itemstack1 = slotStack.copy();
                            int j1 = slot1.getHasStack() ? slot1.getStack().stackSize : 0;
                            func_94525_a(this.field_94537_h, this.field_94535_f, itemstack1, j1);

                            if (itemstack1.stackSize > itemstack1.getMaxStackSize())
                            {
                                itemstack1.stackSize = itemstack1.getMaxStackSize();
                            }

                            if (itemstack1.stackSize > slot1.getSlotStackLimit())
                            {
                                itemstack1.stackSize = slot1.getSlotStackLimit();
                            }

                            i1 -= itemstack1.stackSize - j1;
                            
                            System.out.println("SETTING SLOT 1");
                            slot1.putStack(itemstack1);
                        }
                    }

                    slotStack.stackSize = i1;

                    if (slotStack.stackSize <= 0)
                    {
                        slotStack = null;
                    }

                    inventoryplayer.setItemStack(slotStack);
                }

                this.func_94533_d();
            }
            else
            {
                this.func_94533_d();
            }
        }
        else if (this.field_94536_g != 0)
        {
            this.func_94533_d();
        }
        else
        {
            Slot slot2;
            int l1;
            ItemStack itemstack5;

            if ((method == 0 || method == 1) && (button == 0 || button == 1))
            {
                if (id == -999)
                {
                    if (inventoryplayer.getItemStack() != null && id == -999)
                    {
                        if (button == 0)
                        {
                            player.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), true);
                            inventoryplayer.setItemStack((ItemStack)null);
                        }

                        if (button == 1)
                        {
                            player.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack().splitStack(1), true);

                            if (inventoryplayer.getItemStack().stackSize == 0)
                            {
                                inventoryplayer.setItemStack((ItemStack)null);
                            }
                        }
                    }
                }
                else if (method == 1)
                {
                    if (id < 0)
                    {
                        return null;
                    }

                    slot2 = (Slot)this.inventorySlots.get(id);

                    if (slot2 != null && slot2.canTakeStack(player))
                    {
                        slotStack = this.transferStackInSlot(player, id);

                        if (slotStack != null)
                        {
                            Item item = slotStack.getItem();
                            itemstack = slotStack.copy();

                            if (slot2.getStack() != null && slot2.getStack().getItem() == item)
                            {
                                this.retrySlotClick(id, button, true, player);
                            }
                        }
                    }
                }
                else
                {
                    if (id < 0)
                    {
                        return null;
                    }

                    
                    System.out.println("METHOD 0");
                    slot2 = (Slot)this.inventorySlots.get(id);

                    if (slot2 != null)
                    {
                        slotStack = slot2.getStack();
                        ItemStack heldStack = inventoryplayer.getItemStack();

                        if (slotStack != null)
                        {
                            itemstack = slotStack.copy();
                        }
                        
                        if (slotStack == null)
                        {
                            if (heldStack != null && slot2.isItemValid(heldStack))
                            {
                                l1 = button == 0 ? heldStack.stackSize : 1;

                                if (l1 > slot2.getSlotStackLimit())
                                {
                                    l1 = slot2.getSlotStackLimit();
                                }
                                
                                System.out.println(l1);
                                System.out.println(heldStack.stackSize);

                                if (heldStack.stackSize >= l1)
                                {
                                    ItemStack put = heldStack.splitStack(l1);
                                    
                                    System.out.println("SETTING SLOT 2");
                                    slot2.putStack(put);
                                }

                                if (heldStack.stackSize == 0)
                                {
                                    inventoryplayer.setItemStack((ItemStack)null);
                                }
                            }
                            
                            System.out.println("PUDOWN");
                        }
                        else if (slot2.canTakeStack(player))
                        {
                            System.out.println("PCIKUP");
                            
                            if (heldStack == null)
                            {
                                System.out.println("HOLDING NOTHING");
                                
                                int stackSize = Math.min(64, slotStack.stackSize);
                                l1 = button == 0 ? stackSize : (stackSize + 1) / 2;
                                itemstack5 = slot2.decrStackSize(l1);
                                inventoryplayer.setItemStack(itemstack5);

                                if (slotStack.stackSize - stackSize == 0)
                                {
                                    System.out.println("SETTING SLOT 3");
                                    slot2.putStack((ItemStack)null);
                                }

                                slot2.onPickupFromSlot(player, inventoryplayer.getItemStack());
                            }
                            else if (slot2.isItemValid(heldStack))
                            {
                                if (slotStack.getItem() == heldStack.getItem() && slotStack.getItemDamage() == heldStack.getItemDamage() && ItemStack.areItemStackTagsEqual(slotStack, heldStack))
                                {
                                    l1 = button == 0 ? heldStack.stackSize : 1;

                                    if (l1 > slot2.getSlotStackLimit() - slotStack.stackSize)
                                    {
                                        l1 = slot2.getSlotStackLimit() - slotStack.stackSize;
                                    }

                                    if (l1 > heldStack.getMaxStackSize() - slotStack.stackSize)
                                    {
                                        l1 = heldStack.getMaxStackSize() - slotStack.stackSize;
                                    }

                                    heldStack.splitStack(l1);

                                    if (heldStack.stackSize == 0)
                                    {
                                        inventoryplayer.setItemStack((ItemStack)null);
                                    }

                                    slotStack.stackSize += l1;
                                }
                                else if (heldStack.stackSize <= slot2.getSlotStackLimit())
                                {
                                    System.out.println("SETTING SLOT 4");
                                    slot2.putStack(heldStack);
                                    inventoryplayer.setItemStack(slotStack);
                                }
                            }
                            else if (slotStack.getItem() == heldStack.getItem() && heldStack.getMaxStackSize() > 1 && (!slotStack.getHasSubtypes() || slotStack.getItemDamage() == heldStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(slotStack, heldStack))
                            {
                                l1 = slotStack.stackSize;

                                if (l1 > 0 && l1 + heldStack.stackSize <= heldStack.getMaxStackSize())
                                {
                                    heldStack.stackSize += l1;
                                    slotStack = slot2.decrStackSize(l1);

                                    if (slotStack.stackSize == 0)
                                    {
                                        System.out.println("SETTING SLOT 5");
                                        slot2.putStack((ItemStack)null);
                                    }

                                    slot2.onPickupFromSlot(player, inventoryplayer.getItemStack());
                                }
                            }
                        }

                        slot2.onSlotChanged();
                    }
                }
            }
            else if (method == 2 && button >= 0 && button < 9)
            {
                slot2 = (Slot)this.inventorySlots.get(id);

                if (slot2.canTakeStack(player))
                {
                    slotStack = inventoryplayer.getStackInSlot(button);
                    boolean flag = slotStack == null || slot2.inventory == inventoryplayer && slot2.isItemValid(slotStack);
                    l1 = -1;

                    if (!flag)
                    {
                        l1 = inventoryplayer.getFirstEmptyStack();
                        flag |= l1 > -1;
                    }

                    if (slot2.getHasStack() && flag)
                    {
                        itemstack5 = slot2.getStack();
                        inventoryplayer.setInventorySlotContents(button, itemstack5.copy());

                        if ((slot2.inventory != inventoryplayer || !slot2.isItemValid(slotStack)) && slotStack != null)
                        {
                            if (l1 > -1)
                            {
                                inventoryplayer.addItemStackToInventory(slotStack);
                                slot2.decrStackSize(itemstack5.stackSize);
                                System.out.println("SETTING SLOT 6");
                                slot2.putStack((ItemStack)null);
                                slot2.onPickupFromSlot(player, itemstack5);
                            }
                        }
                        else
                        {
                            slot2.decrStackSize(itemstack5.stackSize);
                            System.out.println("SETTING SLOT 7");
                            slot2.putStack(slotStack);
                            slot2.onPickupFromSlot(player, itemstack5);
                        }
                    }
                    else if (!slot2.getHasStack() && slotStack != null && slot2.isItemValid(slotStack))
                    {
                        inventoryplayer.setInventorySlotContents(button, (ItemStack)null);
                        System.out.println("SETTING SLOT 8");
                        slot2.putStack(slotStack);
                    }
                }
            }
            else if (method == 3 && player.capabilities.isCreativeMode && inventoryplayer.getItemStack() == null && id >= 0)
            {
                slot2 = (Slot)this.inventorySlots.get(id);

                if (slot2 != null && slot2.getHasStack())
                {
                    slotStack = slot2.getStack().copy();
                    slotStack.stackSize = slotStack.getMaxStackSize();
                    inventoryplayer.setItemStack(slotStack);
                }
            }
            else if (method == 4 && inventoryplayer.getItemStack() == null && id >= 0)
            {
                slot2 = (Slot)this.inventorySlots.get(id);

                if (slot2 != null && slot2.getHasStack() && slot2.canTakeStack(player))
                {
                    slotStack = slot2.decrStackSize(button == 0 ? 1 : slot2.getStack().stackSize);
                    slot2.onPickupFromSlot(player, slotStack);
                    player.dropPlayerItemWithRandomChoice(slotStack, true);
                }
            }
            else if (method == 6 && id >= 0)
            {
                slot2 = (Slot)this.inventorySlots.get(id);
                slotStack = inventoryplayer.getItemStack();

                if (slotStack != null && (slot2 == null || !slot2.getHasStack() || !slot2.canTakeStack(player)))
                {
                    i1 = button == 0 ? 0 : this.inventorySlots.size() - 1;
                    l1 = button == 0 ? 1 : -1;

                    for (int i2 = 0; i2 < 2; ++i2)
                    {
                        for (int j2 = i1; j2 >= 0 && j2 < this.inventorySlots.size() && slotStack.stackSize < slotStack.getMaxStackSize(); j2 += l1)
                        {
                            Slot slot3 = (Slot)this.inventorySlots.get(j2);

                            if (slot3.getHasStack() && func_94527_a(slot3, slotStack, true) && slot3.canTakeStack(player) && this.func_94530_a(slotStack, slot3) && (i2 != 0 || slot3.getStack().stackSize != slot3.getStack().getMaxStackSize()))
                            {
                                int k1 = Math.min(slotStack.getMaxStackSize() - slotStack.stackSize, slot3.getStack().stackSize);
                                ItemStack itemstack2 = slot3.decrStackSize(k1);
                                slotStack.stackSize += k1;

                                if (itemstack2.stackSize <= 0)
                                {
                                    System.out.println("SETTING SLOT 9");
                                    slot3.putStack((ItemStack)null);
                                }

                                slot3.onPickupFromSlot(player, itemstack2);
                            }
                        }
                    }
                }

                this.detectAndSendChanges();
            }
        } 

        return itemstack;*/
    }

    private class SlotFishTank extends Slot {
        public SlotFishTank(TileFishTank invent, int slot, int x, int y) {
            super(invent, slot, x, y);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return stack.getItem() instanceof ItemFishy;
        }
    }
}
