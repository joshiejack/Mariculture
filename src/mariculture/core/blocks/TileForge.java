package mariculture.core.blocks;

import mariculture.core.blocks.base.TileMulti;
import mariculture.core.blocks.base.TileMultiInvTank;
import mariculture.core.network.Packets;
import mariculture.factory.blocks.Tank;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;

public class TileForge extends TileMultiInvTank {
	public int blocksInStructure = 1;
	private int machineTick = 0;
	private int coolTimer;
	
	public TileForge() {
		inventory = new ItemStack[1];
		tank = new Tank(getTankCapacity(0));
	}
	
	public boolean onTick(int i) {
		return machineTick % i == 0;
	}
	
	public int getTankCapacity(int storage) {
		int tankRate = FluidContainerRegistry.BUCKET_VOLUME;
		return blocksInStructure * tankRate;
	}
	
	@Override
	public boolean setMaster() {
		World world = worldObj;
		int x = xCoord;
		int y = yCoord;
		int z = zCoord;
		
		int[] coords = getNeighbourMaster(x, y, z);
		if(coords.length == 3) {
			TileForge master  = (TileForge) world.getBlockTileEntity(coords[0], coords[1], coords[2]);
			if(master != null) {
				if(!mstr.built) {
					master.blocksInStructure++;
					addBlock(xCoord, yCoord, zCoord, coords);
				}
			}
			
			return mstr.set(true, coords[0], coords[1], coords[2]);
		} else {
			return mstr.set(true, x, y, z);
		}
	}
	
	public int[][] tiles = new int[81][3];
	
	private void addBlock(int x, int y, int z, int[] mstr) {
		TileForge master = (TileForge) worldObj.getBlockTileEntity(mstr[0], mstr[1], mstr[2]);
		this.blocksInStructure = master.blocksInStructure;
		this.tiles = master.tiles;
	}
	
	private int[] getNeighbourMaster(int x, int y, int z) {
		if(isComponent(x + 1, y, z))
			return getCoords(x + 1, y, z);
		if(isComponent(x - 1, y, z))
			return getCoords(x - 1, y, z);
		if(isComponent(x, y, z + 1))
			return getCoords(x, y, z + 1);
		if(isComponent(x, y, z - 1))
			return getCoords(x, y, z - 1);
			
		return new int[] { 0 };
	}
	
	private int[] getCoords(int x, int y, int z) {
		TileMulti tile = (TileMulti) worldObj.getBlockTileEntity(x, y, z);
		return new int[] { tile.mstr.x, tile.mstr.y, tile.mstr.z };
	}
	
	@Override
	public void updateMaster() {
		ItemStack stack = inventory[0];
		if(stack != null && tank.getFluid() != null) {
			
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.blocksInStructure = tagCompound.getInteger("BlocksInStructure");
		
		NBTTagList tagList = tagCompound.getTagList("Coordinates");

		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

			int id = tag.getInteger("ID");
			int x = tag.getInteger("X");
			int y = tag.getInteger("Y");
			int z = tag.getInteger("z");
			
			tiles[id][0] = x;
			tiles[id][1] = y;
			tiles[id][2] = z;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("BlocksInStructure", this.blocksInStructure);
		
		NBTTagList itemList = new NBTTagList();

		for (int i = 0; i < tiles.length; i++) {
			int x = tiles[i][0];
			int y = tiles[i][1];
			int z = tiles[i][2];
			NBTTagCompound tag = new NBTTagCompound();
			tag.setInteger("ID", i);
			tag.setInteger("X", x);
			tag.setInteger("Y", y);
			tag.setInteger("Z", z);
			itemList.appendTag(tag);
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {		
		NBTTagCompound tagCompound = new NBTTagCompound();
		this.writeToNBT(tagCompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, tagCompound);
	}

	@Override
	public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet) {
		this.readFromNBT(packet.data);
	}
	
	@Override
	public void updateAll() {
		machineTick++;
		
		if(onTick(30)) {
			Packets.updateTile(this, 32, getDescriptionPacket());
		}
	}
}
