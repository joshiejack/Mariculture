package mariculture.fishery;

import java.util.ArrayList;

import mariculture.core.blocks.base.TileStorageTank;
import mariculture.core.network.Packet118FluidUpdate;
import mariculture.core.network.Packet120ItemSync;
import mariculture.core.network.Packets;
import mariculture.factory.blocks.Tank;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class TileFishTank extends TileStorageTank implements ISidedInventory {
	public TileFishTank() {
		inventory = new ItemStack[27];
		tank = new Tank(1000);
	}
	
	public ArrayList<ItemStack> fish = new ArrayList();
	public boolean loaded;
	
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		
		if(!worldObj.isRemote) {
			 Packets.updateTile(this, 64, new Packet120ItemSync(xCoord, yCoord, zCoord, inventory).build());
		}
	}
	
	public float getFluidAmountScaled() {
		return (float) (tank.getFluid().amount) / (float) (tank.getCapacity() * 1.01F);
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		int amount =  tank.fill(resource, doFill);
        if (amount > 0 && doFill)
        	Packets.updateTile(this, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid()).build());
        return amount;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack amount = tank.drain(maxDrain, doDrain);
        if (amount != null && doDrain)
        	Packets.updateTile(this, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid()).build());
        return amount;
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
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return stack.getItem() instanceof ItemFishy;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return true;
	}
	
	@Override
	public void setFluid(FluidStack fluid) {
		tank.setFluid(fluid);
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		fish.add(stack);
		
        this.onInventoryChanged();
	}
	
	public void setSlot(int slot, ItemStack stack) {
		super.setInventorySlotContents(slot, stack);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt); 
		
		fish = new ArrayList();
		NBTTagList tagList = nbt.getTagList("FishList");
		for (int i = 0; i < tagList.tagCount(); i++) {
			fish.add(ItemStack.loadItemStackFromNBT((NBTTagCompound) tagList.tagAt(i)));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList itemList = new NBTTagList();
		
		for(ItemStack stack: fish) {
			NBTTagCompound tag = new NBTTagCompound();
			stack.writeToNBT(tag);
			itemList.appendTag(tag);
		}

		nbt.setTag("FishList", itemList);
	}

	public void loadFish(int page) {	
		for(int i = 0; i < 27; i++) {
			int index = (page * 27) + i; 
			if(index >= fish.size())
				inventory[i] = null;
			else
				inventory[i] = fish.get(index);
		}
			
		this.onInventoryChanged();
			
		loaded = true;
	}
}
