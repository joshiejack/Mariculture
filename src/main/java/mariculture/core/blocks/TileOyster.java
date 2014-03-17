package mariculture.core.blocks;

import mariculture.core.Core;
import mariculture.core.blocks.base.TileStorage;
import mariculture.core.network.Packet103Oyster;
import mariculture.core.network.Packets;
import net.minecraft.block.Block;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;

public class TileOyster extends TileStorage implements ISidedInventory {
	public TileOyster() {
		inventory = new ItemStack[1];
	}
	
	@Override
	public boolean canUpdate() {
		return false;
	}

	public boolean hasSand() {
		return hasContents() && inventory[0].itemID == Block.sand.blockID;
	}

    public boolean hasContents() {
        return inventory[0] != null;
    }
	
	public ItemStack getCurrentPearl() {
		return inventory[0];
	}
	
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
	public void onInventoryChanged() {
		super.onInventoryChanged();
		
		if(!worldObj.isRemote) {
			int id = getCurrentPearl() != null ? getCurrentPearl().itemID : -1;
			int meta = getCurrentPearl() != null ? getCurrentPearl().getItemDamage() : 0;
			Packets.updateTile(this, new Packet103Oyster(xCoord, yCoord, zCoord, id, meta).build());
		}
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		return stack.itemID == Block.sand.blockID || stack.itemID == Core.pearls.itemID || stack.itemID == Item.enderPearl.itemID;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int i, ItemStack stack, int j) {
		return stack.itemID == Block.sand.blockID && inventory[0] == null;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack stack, int j) {
		return stack.itemID == Core.pearls.itemID || stack.itemID == Item.enderPearl.itemID;
	}
}
