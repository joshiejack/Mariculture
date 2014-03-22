package mariculture.core.tile;

import mariculture.core.helpers.BlockHelper;
import mariculture.core.network.Packets;
import mariculture.core.tile.base.TileStorage;
import mariculture.core.util.IFaceable;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileOyster extends TileStorage implements ISidedInventory, IFaceable {
	public ForgeDirection orientation = ForgeDirection.WEST;

	public TileOyster() {
		inventory = new ItemStack[1];
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0 };
	}

	@Override
	public boolean canInsertItem(int side, ItemStack stack, int slot) {
		return stack.getItem() == Item.getItemFromBlock(Blocks.sand) && inventory[0] == null;
	}

	@Override
	public boolean canExtractItem(int side, ItemStack stack, int slot) {
		return stack.getItem() != Item.getItemFromBlock(Blocks.sand);
	}
	
	@Override
	public void markDirty() {
		super.markDirty();
		
		if(!worldObj.isRemote) {
			Packets.syncInventory(this, inventory);
		}
	}
	
	@Override
	public boolean rotate() {
		setFacing(BlockHelper.rotate(orientation, 4));
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
			Packets.updateOrientation(this);
		}
	}
	
	public Packet getDescriptionPacket()  {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }
	
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Orientation", orientation.ordinal());
	}

	public boolean hasSand() {
		return inventory[0] != null && inventory[0].getItem() == Item.getItemFromBlock(Blocks.sand);
	}
}
