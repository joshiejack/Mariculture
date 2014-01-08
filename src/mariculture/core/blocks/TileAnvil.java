package mariculture.core.blocks;

import mariculture.core.blocks.base.TileStorage;
import mariculture.core.network.Packet120ItemSync;
import mariculture.core.network.Packets;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraftforge.common.ForgeDirection;

public class TileAnvil extends TileStorage implements ISidedInventory {
	public TileAnvil() {
		inventory = new ItemStack[1];
	}
	
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		
		if(!worldObj.isRemote) {
			 Packets.updateTile(this, 64, new Packet120ItemSync(xCoord, yCoord, zCoord, inventory).build());
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {		
		return new Packet120ItemSync(xCoord, yCoord, zCoord, inventory).build();
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(side != ForgeDirection.UP.ordinal())
			return new int[] { };
		else
			return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return side == ForgeDirection.UP.ordinal();
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return side == ForgeDirection.UP.ordinal();
	}
}
