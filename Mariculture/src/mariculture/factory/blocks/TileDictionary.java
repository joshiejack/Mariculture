package mariculture.factory.blocks;

import mariculture.core.helpers.DictionaryHelper;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.Compatibility;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.util.IItemDropBlacklist;
import mariculture.core.util.PacketIntegerUpdate;
import mariculture.factory.gui.ContainerDictionary;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileDictionary extends TileEntity implements IInventory, ISidedInventory, IItemDropBlacklist {
	private ItemStack[] inventory = new ItemStack[21];
	private int furnaceCookTime = 0;
	private int totalCookTime = MachineSpeeds.getDictionarySpeed();

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory");

		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

			byte slot = tag.getByte("Slot");

			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}

		this.furnaceCookTime = tagCompound.getShort("CookTime");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setShort("CookTime", (short) this.furnaceCookTime);

		NBTTagList itemList = new NBTTagList();

		for (int i = 0; i < inventory.length; i++) {
			final ItemStack stack = inventory[i];

			if (stack != null) {
				final NBTTagCompound tag = new NBTTagCompound();

				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}

		tagCompound.setTag("Inventory", itemList);
	}

	public void updateEntity() {
		boolean updated = false;

		if (!this.worldObj.isRemote) {
			if (swap(false)) {
				this.furnaceCookTime++;
				if (this.furnaceCookTime >= totalCookTime) {
					this.furnaceCookTime = 0;
					this.swap(true);
					updated = true;
				}
			} else {
				this.furnaceCookTime = 0;
			}
		}

		if (updated) {
			this.onInventoryChanged();
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound tagCompound = new NBTTagCompound();
		this.writeToNBT(tagCompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, tagCompound);
	}

	@Override
	public void onDataPacket(final INetworkManager netManager, final Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	}

	private boolean swap(boolean doSwap) {
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 3; i++) {
				if (getStackInSlot(9 + (i + (j * 3))) != null) {
					if(swap(9 + (i + (j * 3)), doSwap) && !doSwap) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	private int isInFilter(String name) {
		for (int j = 0; j < Compatibility.BLACKLIST.length; j++) {
			if (Compatibility.BLACKLIST[j].equals(name)) {
				return -1;
			}
		}
		for (int i = 0; i < 9; i++) {
			if (getStackInSlot(i) != null) {
				ItemStack stack = getStackInSlot(i);
				if (DictionaryHelper.isInDictionary(stack)) {
					String check = DictionaryHelper.getDictionaryName(stack);
					if (check.equals(name)) {
						return i;
					}
					
					if(checkException(check, name)) {
						return i;
					}
				}
			}
		}
		return -1;
	}
	
	private boolean checkException(String check, String name) {
		for(int i = 0; i < Compatibility.EXCEPTIONS.length; i++) {
			String[] names = Compatibility.EXCEPTIONS[i].split("\\s*:\\s*");
			if((check.equals(names[0]) && name.equals(names[1])) ||
					(check.equals(names[1]) && name.equals(names[0]))) {
				return true;
			}
		}
		
		return false;
	}

	private boolean swap(int slot, boolean doSwap) {
		ItemStack stack = getStackInSlot(slot);
		if (DictionaryHelper.isInDictionary(stack) && isInFilter(DictionaryHelper.getDictionaryName(stack)) != -1) {
			ItemStack newStack = getStackInSlot(isInFilter(DictionaryHelper.getDictionaryName(stack))).copy();
			return moveStack(newStack, slot, doSwap);
		} 
		
		return moveStack(getStackInSlot(slot), slot, doSwap);
	}

	private boolean moveStack(ItemStack stack, int origSlot, boolean doSwap) {
		
		ItemStack newStack = stack.copy();
		newStack.stackSize = 1;
		
		if (getNextSlot(stack) != -1) {
			if(doSwap) {				
				if(!InventoryHelper.addToInventory(0, worldObj, xCoord, yCoord, zCoord, newStack, new int[]{ 2, 3, 4, 5 })) {
					int newSlot = getNextSlot(stack);
					if (getStackInSlot(newSlot) != null) {
						getStackInSlot(newSlot).stackSize++;
					} else {
						setInventorySlotContents(newSlot, newStack);
					}
				}
	
				decrStackSize(origSlot, 1);
			}
			
			return true;
		}
		return false;
	}

	private int getNextSlot(ItemStack stack) {
		if (stack == null) {
			return -1;
		}
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 3; i++) {
				ItemStack itemstack = getStackInSlot(15 + (i + (j * 3)));
				if (itemstack != null) {
					if (itemstack.isItemEqual(stack) && itemstack.stackSize < itemstack.getMaxStackSize()) {
						return 15 + (i + (j * 3));
					}
				}
			}
		}
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 3; i++) {
				ItemStack itemstack = getStackInSlot(15 + (i + (j * 3)));
				if (itemstack == null) {
					return 15 + (i + (j * 3));
				}
			}
		}

		return -1;
	}

	public void getGUINetworkData(int i, int j) {
		switch (i) {
		case 1:
			furnaceCookTime = j;
			break;
		}
	}

	public void sendGUINetworkData(ContainerDictionary container, EntityPlayer player) {
		PacketIntegerUpdate.send(container, 1, this.furnaceCookTime, player);
	}

	public int getFreezeProgressScaled(int par1) {
		return (furnaceCookTime * par1) / totalCookTime;
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return inventory[slotIndex];
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int amount) {
		ItemStack stack = getStackInSlot(slotIndex);

		if (stack != null) {
			if (stack.stackSize <= amount) {
				setInventorySlotContents(slotIndex, null);
			}

			else {
				stack = stack.splitStack(amount);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slotIndex, null);
				}
			}
		}

		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		ItemStack stack = getStackInSlot(slotIndex);

		if (stack != null) {
			setInventorySlotContents(slotIndex, null);
		}

		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "TileEntityDictionary";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
				&& player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return(i < 9)? false: true;
	}
	
	private static final int[] slots_sides = new int[] { 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 };

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slots_sides;
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return i > 8 && i < 15 && j < 2;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return i > 14 && j > 1;
	}

	@Override
	public boolean doesDrop(int slot) {
		return slot > 8;
	}
}
