package mariculture.factory.blocks;

import mariculture.core.network.Packets;
import mariculture.core.network.old.Packet110CustomTileUpdate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileCustom extends TileEntity {
	private int[] theBlockIDs = new int[6];
	private int[] theBlockMetas = new int[6];
	private int[] theSides = new int[6];
	private String name = "CustomTile";
	
	public int size() {
		return theBlockIDs.length;
	}
	
	public String name() {
		return name;
	}
	
	public int[] theBlockIDs() {
		return theBlockIDs;
	}
	
	public int[] theBlockMetas() {
		return theBlockMetas;
	}
	public int[] theBlockSides() {
		return theSides;
	}

	public int theBlockIDs(int i) {
		return theBlockIDs[i];
	}
	
	public int theBlockMetas(int i) {
		return theBlockMetas[i];
	}
	
	public int theBlockSides(int i) {
		return theSides[i];
	}
	
	@Override
	public boolean canUpdate() {
		return false;
    }

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.theBlockIDs = tagCompound.getIntArray("BlockIDs");
		this.theBlockMetas = tagCompound.getIntArray("BlockMetas");
		this.theSides = tagCompound.getIntArray("BlockSides");
		this.name = tagCompound.getString("Name");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setIntArray("BlockIDs", this.theBlockIDs);
		tagCompound.setIntArray("BlockMetas", this.theBlockMetas);
		tagCompound.setIntArray("BlockSides", this.theSides);
		tagCompound.setString("Name", this.name);
	}

	//TODO: PACKET SYNC CUSTOM BLOCKS
	/*
	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound tagCompound = new NBTTagCompound();
		this.writeToNBT(tagCompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, tagCompound);
	}

	@Override
	public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	} */
	
	public void set(int[] ids, int metas[], int sides[], String name2) {
		if(theBlockIDs.length == 6) {
			theBlockIDs = ids;
			theBlockMetas = metas;
			theSides = sides;
			name = name2;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			updateRender();
		}
	}

	public boolean setSide(int side, int id, int meta, int sideTexture) {
		boolean ret = false;
		if(theBlockIDs.length == 6) {
			if(theBlockIDs[side] != id || theBlockMetas[side] != meta || theSides[side] != sideTexture) {
				ret = true;
			}
			
			theBlockIDs[side] = id;
			theBlockMetas[side] = meta;
			theSides[side] = sideTexture;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			updateRender();
		}
		
		return ret;
	}

	public void updateRender() {
		if (!worldObj.isRemote) {
			//TODO: PACKET UPDATE RENDER Packets.updateTile(this, 128, new Packet110CustomTileUpdate(xCoord, yCoord, zCoord).build());
		}
	}
}
