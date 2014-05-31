package mariculture.fishery.tile;

import mariculture.core.helpers.BlockHelper;
import mariculture.core.network.PacketHandler;
import mariculture.core.tile.base.TileStorage;
import mariculture.core.util.IFaceable;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileSift extends TileStorage implements ISidedInventory, IFaceable {
	public boolean hasInventory;
	public ForgeDirection orientation = ForgeDirection.EAST;
	
	public TileSift() {
		inventory = new ItemStack[10];
	}
	
	public int getSuitableSlot(ItemStack item) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == null) {
				return i;
			}

			if ((inventory[i].getItemDamage() == item.getItemDamage() && inventory[i].getItem() == item.getItem() 
					&& (inventory[i].stackSize + item.stackSize) <= inventory[i].getMaxStackSize())) {
				return i;
			}
		}

		return 10;
	}
	
	@Override
	public boolean rotate() {
		setFacing(BlockHelper.rotate(orientation, 2));
		return true;
	}
	
	@Override
	public ForgeDirection getFacing() {
		return this.orientation;
	}
	
	@Override
	public void setFacing(ForgeDirection dir) {
		this.orientation = dir;
		if(!worldObj.isRemote) {
			PacketHandler.updateOrientation(this);
		}
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return this.hasInventory;
	}
	
	@Override
	public Packet getDescriptionPacket()  {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		hasInventory = nbt.getBoolean("HasInventory");
		orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("HasInventory", hasInventory);
		nbt.setInteger("Orientation", orientation.ordinal());
	}
}
