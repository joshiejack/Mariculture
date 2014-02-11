package mariculture.core.blocks.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileMultiStorage extends TileMultiBlock implements IInventory {
	protected ItemStack[] inventory;
	
	@Override
	public int getSizeInventory() {
		return inventory.length;
	}
	
	public ItemStack[] getMasterInventory() {
		if(master == null)
			return null;
		
		TileMultiStorage tile = (TileMultiStorage) worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord);
		return tile != null? tile.inventory: inventory;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return getMasterInventory() != null? getMasterInventory()[slot]: inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		TileMultiStorage mstr = getMaster() != null? ((TileMultiStorage)getMaster()): null;
		if(mstr == null)
			return null;
		if (mstr.inventory[slot] != null) {
            ItemStack stack;

            if (mstr.inventory[slot].stackSize <= amount) {
                stack = mstr.inventory[slot];
                mstr.inventory[slot] = null;
                mstr.markDirty();
                return stack;
            } else {
                stack = mstr.inventory[slot].splitStack(amount);

                if (mstr.inventory[slot].stackSize == 0) {
                	mstr.inventory[slot] = null;
                }

                mstr.markDirty();
                return stack;
            }
        }

		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		TileMultiStorage mstr = getMaster() != null? ((TileMultiStorage)getMaster()): null;
		if(mstr == null)
			return null;
		if (mstr.inventory[slot] != null) {
            ItemStack stack = mstr.inventory[slot];
            mstr.inventory[slot] = null;
            return stack;
        }

		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		TileMultiStorage mstr = getMaster() != null? ((TileMultiStorage)getMaster()): null;
		if(mstr == null)
			return;
		mstr.inventory[slot] = stack;

        if (stack != null && stack.stackSize > mstr.getInventoryStackLimit()) {
        	stack.stackSize = mstr.getInventoryStackLimit();
        }

        mstr.markDirty();
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
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this
				&& player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 128;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList tagList = nbt.getTagList("Inventory", 10);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
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
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}
		
		nbt.setTag("Inventory", itemList);
	}
}
