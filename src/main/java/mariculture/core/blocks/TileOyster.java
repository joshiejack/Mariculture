package mariculture.core.blocks;

import mariculture.core.Core;
import mariculture.core.blocks.base.TileStorage;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TileOyster extends TileStorage implements ISidedInventory {
	public TileOyster() {
		inventory = new ItemStack[1];
	}
	
	@Override
	public boolean canUpdate() {
		return false;
	}

	public boolean hasSand() {
		return hasContents() && inventory[0].getItem() == Item.getItemFromBlock(Blocks.sand);
	}

    public boolean hasContents() {
        return inventory[0] != null;
    }
	
	public ItemStack getCurrentPearl() {
		return inventory[0];
	}
	
	//TODO: PACKET Oyster Packet Updates
	/*
	@Override
	public Packet getDescriptionPacket() {		
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 2, nbt);
	}
	
	@Override
	public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	}
	
	@Override
	public void markDirty() {
		super.markDirty();
		
		if(!worldObj.isRemote) {
			int id = getCurrentPearl() != null ? getCurrentPearl().itemID : -1;
			int meta = getCurrentPearl() != null ? getCurrentPearl().getItemDamage() : 0;
			Packets.updateTile(this, 128, new Packet103Oyster(xCoord, yCoord, zCoord, id, meta).build());
		}
	} */

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return stack.getItem() == Item.getItemFromBlock(Blocks.sand) || stack.getItem() == Core.pearls || stack.getItem() == Items.ender_pearl;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack stack, int j) {
		return stack.getItem() == Item.getItemFromBlock(Blocks.sand) && inventory[0] == null;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		return stack.getItem() == Core.pearls || stack.getItem() == Items.ender_pearl;
	}
}
