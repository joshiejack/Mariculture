package mariculture.fishery.tile;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import mariculture.core.Core;
import mariculture.core.network.PacketHandler;
import mariculture.core.tile.base.TileMultiBlock;
import mariculture.core.tile.base.TileMultiStorage;
import mariculture.core.tile.base.TileMultiBlock.MultiPart;

public class TileSifter extends TileMultiStorage implements ISidedInventory {
	public boolean hasInventory;
	public ItemStack texture = new ItemStack(Blocks.planks);
	public ItemStack sifting = new ItemStack(Core.air);
	public TileSifter() {
		inventory = new ItemStack[10];
	}
	
	@Override
	public boolean canUpdate() {
		return false;
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
	public Packet getDescriptionPacket()  {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
    }
	
	public boolean isSifter(int x, int y, int z) {
		return worldObj.getTileEntity(x, y, z) instanceof TileSifter && !isPartnered(x, y, z);
	}
	
	@Override
	public TileSifter getMaster() {
		if(master == null) return null;
		TileEntity tile = worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord);
		return tile != null && tile instanceof TileSifter? (TileSifter)tile: null;
	}
	
	@Override
	public void onBlockPlaced() {
		if(onBlockPlaced(xCoord, yCoord, zCoord)) {
			TileSifter master = (TileSifter) getMaster();
			if(master != null && !master.isInit()) {
				master.init();
			}
		}
	}
	
	public boolean onBlockPlaced(int x, int y, int z) {
		if(isSifter(x, y, z) && isSifter(x + 1, y, z)) {
			MultiPart mstr = new MultiPart(x, y, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x+ 1, y, z, ForgeDirection.WEST));
			setAsMaster(mstr, parts, ForgeDirection.EAST);
			return true;
		}
		
		if(isSifter(x - 1, y, z) && isSifter(x, y, z)) {
			MultiPart mstr = new MultiPart(x - 1, y, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.WEST));
			setAsMaster(mstr, parts, ForgeDirection.EAST);
			return true;
		}
		
		if(isSifter(x, y, z) && isSifter(x, y, z + 1)) {
			MultiPart mstr = new MultiPart(x, y, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z + 1, ForgeDirection.SOUTH));
			setAsMaster(mstr, parts, ForgeDirection.NORTH);
			return true;
		}
		
		if(isSifter(x, y, z - 1) && isSifter(x, y, z)) {
			MultiPart mstr = new MultiPart(x, y, z - 1);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.SOUTH));
			setAsMaster(mstr, parts, ForgeDirection.NORTH);
			return true;
		}
		
		return false;
	}
	
	@Override
	public void onBlockBreak() {
		if(master != null) {
			//Get the Master Tile Entity
			TileMultiBlock mstr = (TileMultiBlock) worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord);
			if(mstr != null) {
				//Clear out the slaves
				if(mstr.slaves != null) {
					for(MultiPart part: mstr.slaves) {
						worldObj.setBlockToAir(part.xCoord, part.yCoord, part.zCoord);
					}
				}
			}
		}
	}
}
