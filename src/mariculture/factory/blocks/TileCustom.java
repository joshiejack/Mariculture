package mariculture.factory.blocks;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import mariculture.core.network.Packet110CustomTileUpdate;
import mariculture.core.network.Packets;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileCustom extends TileEntity {
	private int[] theBlockIDs = new int[6];
	private int[] theBlockMetas = new int[6];
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

	public int theBlockIDs(int i) {
		return theBlockIDs[i];
	}
	
	public int[] theBlockMetas() {
		return theBlockMetas;
	}
	
	public int theBlockMetas(int i) {
		return theBlockMetas[i];
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
		this.name = tagCompound.getString("Name");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setIntArray("BlockIDs", this.theBlockIDs);
		tagCompound.setIntArray("BlockMetas", this.theBlockMetas);
		tagCompound.setString("Name", this.name);
	}

	@Override
	public Packet getDescriptionPacket() {
		final NBTTagCompound tagCompound = new NBTTagCompound();
		this.writeToNBT(tagCompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, tagCompound);
	}

	@Override
	public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	}
	
	public void set(int[] ids, int metas[], String name2) {
		if(theBlockIDs.length == 6) {
			theBlockIDs = ids;
			theBlockMetas = metas;
			name = name2;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			updateRender();
		}
	}

	public boolean setSide(int side, int id, int meta) {
		boolean ret = false;
		if(theBlockIDs.length == 6) {
			if(theBlockIDs[side] != id || theBlockMetas[side] != meta) {
				ret = true;
			}
			
			theBlockIDs[side] = id;
			theBlockMetas[side] = meta;
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			updateRender();
		}
		
		return ret;
	}

	public void updateRender() {
		if (!worldObj.isRemote) {
			Packets.updateTile(this, 128, new Packet110CustomTileUpdate(xCoord, yCoord, zCoord).build());
		}
	}

	public static void handleUpdateRender(Packet250CustomPayload packet, World world) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		int id;
		int x;
		int y;
		int z;

		try {
			id = inputStream.readInt();
			x = inputStream.readInt();
			y = inputStream.readInt();
			z = inputStream.readInt();

		} catch (IOException e) {
			e.printStackTrace(System.err);
			return;
		}

		if (world.isRemote) {
			Minecraft.getMinecraft().theWorld.markBlockForRenderUpdate(x, y, z);
		}
	}
}
