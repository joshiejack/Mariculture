package mariculture.fishery.blocks;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mariculture.Mariculture;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.network.Packets;
import mariculture.core.util.IHasClickableButton;
import mariculture.core.util.IMachine;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileFishTank extends TileEntity implements IInventory, IHasClickableButton, IMachine {

	public ForgeDirection orientation = ForgeDirection.UNKNOWN;
	public HashMap<Integer, ItemStack> fish;
	public static final int MAX_PAGES = 250;
	
	public int previous = -2;
	public int next = -1;
	public int thePage = 0;

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
		return stack.getItem() instanceof ItemFishy;
	}
	
	@Override
	public void handleButtonClick(int id) {
		int page = thePage;
		if(id == next) {
			page+=1;
			if(page >= MAX_PAGES)
				page = 0;
		} else if (id == previous) {
			page-=1;
			if(page < 0)
				page = MAX_PAGES - 1;
		}
		
		thePage = page;
		this.onInventoryChanged();
		
		if(id >= 0) {
			EntityPlayer player = (EntityPlayer) worldObj.getEntityByID(id);
			player.openGui(Mariculture.instance, -1, worldObj, xCoord, yCoord, zCoord);
		}
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
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		Packets.updateGUI(player, container, 0, thePage);
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)
			thePage = value;
	}

	@Override
	public ItemStack[] getInventory() {
		return null;
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
		
		orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
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
		nbt.setInteger("Orientation", orientation.ordinal());
	}
}
