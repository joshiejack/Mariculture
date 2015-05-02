package mariculture.fishery.tile;

import java.util.ArrayList;

import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.NBTHelper;
import mariculture.core.network.PacketFishTankSync;
import mariculture.core.network.PacketHandler;
import mariculture.core.util.IFaceable;
import mariculture.core.util.IMachine;
import mariculture.fishery.gui.ContainerFishTank;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileFishTank extends TileEntity implements IInventory, IMachine, IFaceable, ISidedInventory {
    public ForgeDirection orientation = ForgeDirection.UNKNOWN;
    protected ItemStack[] inventory;
    
    public TileFishTank() {
        inventory = new ItemStack[54];
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (inventory[slot] != null) {
            ItemStack stack;

            if (inventory[slot].stackSize <= amount) {
                stack = inventory[slot];
                inventory[slot] = null;
                if (!worldObj.isRemote) {
                    onInventoryChange(slot);
                }

                markDirty();
                return stack;
            } else {
                stack = inventory[slot].splitStack(amount);

                if (inventory[slot].stackSize == 0) {
                    inventory[slot] = null;
                }

                if (!worldObj.isRemote) {
                    onInventoryChange(slot);
                }

                markDirty();
                return stack;
            }
        }

        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (inventory[slot] != null) {
            ItemStack stack = inventory[slot];
            inventory[slot] = null;
            return stack;
        }

        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;

        if (!worldObj.isRemote) {
            onInventoryChange(slot);
        }

        markDirty();
    }

    public void onInventoryChange(int slot) {
        return;
    }

    @Override
    public String getInventoryName() {
        return "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return stack.getItem() instanceof ItemFishy;
    }
    
    @Override
    public Packet getDescriptionPacket() {
        return PacketHandler.getPacket(new PacketFishTankSync(getStackInSlot(0), getStackInSlot(1), getStackInSlot(2), xCoord, yCoord, zCoord, orientation));
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        NBTTagList tagList = nbt.getTagList("Inventory", 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < inventory.length) {
                inventory[slot] = NBTHelper.getItemStackFromNBT(tag);
            }
        }
        
        orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < inventory.length; i++) {
            ItemStack stack = inventory[i];
            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                NBTHelper.writeItemStackToNBT(tag, stack);
                itemList.appendTag(tag);
            }
        }

        nbt.setTag("Inventory", itemList);
        nbt.setInteger("Orientation", orientation.ordinal());
    }

    @Override
    public boolean hasChanged() {
        return false;
    }

    @Override
    public void setGUIData(int id, int value) {}

    @Override
    public ArrayList<Integer> getGUIData() {
        return null;
    }

    @Override
    public ItemStack[] getInventory() {
        return inventory;
    }

    @Override
    public boolean rotate() {
        setFacing(BlockHelper.rotate(orientation, 4));
        return true;
    }

    @Override
    public ForgeDirection getFacing() {
        return orientation;
    }

    @Override
    public void setFacing(ForgeDirection dir) {
        orientation = dir;
        if (!worldObj.isRemote) {
            PacketHandler.updateOrientation(this);
        }
    }
    
    private static final int[] all_slots = new int[] { 0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
        23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53 }; 

    @Override
    public int[] getAccessibleSlotsFromSide(int slot) {
        return all_slots;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        if (!(stack.getItem() instanceof ItemFishy)) return false;
        if (inventory[slot] == null) return true;
        if (inventory[slot] != null) return ContainerFishTank.isFishEqual(inventory[slot], stack);
        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return true;
    }
}