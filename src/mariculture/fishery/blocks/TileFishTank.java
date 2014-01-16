package mariculture.fishery.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mariculture.core.network.Packet121FishTankSync;
import mariculture.core.network.Packets;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileFishTank extends TileEntity implements IInventory {

	public HashMap<Integer, ItemStack> fish;

	public TileFishTank() {
		fish = new HashMap();
	}
	
	@Override
	public boolean canUpdate() {
		return false;
	}
	
	@Override
	public int getSizeInventory() {
		return fish.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if(fish.containsKey(slot))
			return (ItemStack) fish.get(slot);
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		if(fish.containsKey(slot)) {
			ItemStack stack = (ItemStack) fish.get(slot);
			fish.put(slot, null);
			this.onInventoryChanged();
			
			return stack;
		}

		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		System.out.println(slot);
		
		fish.put(slot, stack);
		this.onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return "";
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
	public void openChest() {}

	@Override
	public void closeChest() {}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		NBTTagList tagList = nbt.getTagList("FishList");
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			ItemStack stack = ItemStack.loadItemStackFromNBT(tag);
			fish.put(tag.getInteger("Key"), stack);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		NBTTagList itemList = new NBTTagList();
		Iterator it = fish.entrySet().iterator();
		while (it.hasNext()) {
			NBTTagCompound tag = new NBTTagCompound();
			Map.Entry pairs = (Map.Entry) it.next();
			tag.setInteger("Key", (Integer) pairs.getKey());
			ItemStack stack = (ItemStack) pairs.getValue();
			if(stack != null) {
				stack.writeToNBT(tag);
			}
			
			itemList.appendTag(tag);
		}

		nbt.setTag("FishList", itemList);
	}
}
