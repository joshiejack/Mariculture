package mariculture.core.blocks.base;

import java.util.ArrayList;

import mariculture.core.network.Packet110CustomTileUpdate;
import mariculture.core.network.Packets;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

public class TileMultiBlock extends TileEntity {
	public ArrayList<MultiPart> slaves = new ArrayList<MultiPart>();
	public MultiPart master;
	
	public static class MultiPart {	
		public int xCoord;
		public int yCoord;
		public int zCoord;

		public MultiPart(int x, int y, int z) {
			xCoord = x;
			yCoord = y;
			zCoord = z;
		}

		public boolean isSame(int x, int y, int z) {
			return x == xCoord && y == yCoord && z == zCoord;
		}
	}
	
	protected MultiPart setAsSlave(MultiPart master, int x, int y, int z) {
		TileMultiBlock slave = (TileMultiBlock) worldObj.getBlockTileEntity(x, y, z);
		slave.setMaster(master.xCoord, master.yCoord, master.zCoord);
		return new MultiPart(x, y, z);
	}
	
	protected void setAsMaster(MultiPart mstr, ArrayList<MultiPart> parts) {
		TileMultiBlock master = (TileMultiBlock) worldObj.getBlockTileEntity(mstr.xCoord, mstr.yCoord, mstr.zCoord);
		master.setMaster(mstr);
		master.setSlaves(parts);
	}
	
	public void clearMaster() {
		master = null;
	}
	
	public void setMaster(MultiPart mstr) {
		master = mstr;
	}
	
	public void setSlaves(ArrayList<MultiPart> parts) {
		slaves = parts;
	}
	
	public void setMaster(int x, int y, int z) {
		master = new MultiPart(x, y, z);
	}
	
	public boolean isPart(int x, int y, int z) {
		return worldObj.getBlockTileEntity(x, y, z) instanceof TileMultiBlock;
	}
	
	public boolean isMaster() {
		return master!= null && master.isSame(xCoord, yCoord, zCoord);
	}
	
	public TileMultiBlock getMaster() {
		if(master == null)
			return null;
		TileEntity tile = worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord);
		return tile != null && tile instanceof TileMultiBlock? (TileMultiBlock)tile: null;
	}
	
	public ArrayList<MultiPart> getSlaves() {
		return this.slaves;
	}
	
	protected void addSlave(MultiPart slave) {
		this.slaves.add(slave);
	}
	
	public void removeSlave(MultiPart part) {
		slaves.remove(part);
	}
	
	//Sets the Master and the Slave Blocks
	public void onBlockPlaced() {
		if(isPart(xCoord, yCoord + 1, zCoord) && !isPart(xCoord, yCoord - 1, zCoord) && !isPart(xCoord, yCoord + 2, zCoord)) {
			MultiPart mstr = new MultiPart(xCoord, yCoord, zCoord);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, xCoord, yCoord + 1, zCoord));
			setAsMaster(mstr, parts);
		}
		
		if(isPart(xCoord, yCoord - 1, zCoord) && !isPart(xCoord, yCoord + 1, zCoord) && !isPart(xCoord, yCoord - 2, zCoord)) {
			MultiPart mstr = new MultiPart(xCoord, yCoord - 1, zCoord);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, xCoord, yCoord, zCoord));
			setAsMaster(mstr, parts);
		}
		
		Packets.updateTile(this, 32, getDescriptionPacket());
	}
	
	public void onBlockBreak() {
		if(master != null) {
			TileMultiBlock mstr = (TileMultiBlock) worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord);
			if(mstr != null) {
				if(mstr.slaves.size() > 0) {
					for(MultiPart part: mstr.slaves) {
						if(worldObj.getBlockTileEntity(part.xCoord, part.yCoord, part.zCoord) != null) {
							TileMultiBlock te = (TileMultiBlock) worldObj.getBlockTileEntity(part.xCoord, part.yCoord, part.zCoord);
							te.clearMaster();
							Packets.updateTile(te, 32, te.getDescriptionPacket());
						}
					}
				}
				
				mstr.clearMaster();
			}
			
			Packets.updateTile(this, 32, getDescriptionPacket());
		}
	}
	
	@Override
	public boolean canUpdate() {
		return false;
	}
	
	@Override
	public void updateEntity() {
		if(master != null){
			if(isMaster()) {
				updateMaster();
			} else {
				updateSlaves();
			}
		}
	}
	
	public void updateMaster() {
		return;
	}
	
	public void updateSlaves() {
		return;
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		boolean built = nbt.getBoolean("Built");
		int mstrX = nbt.getInteger("MasterX");
		int mstrY = nbt.getInteger("MasterY");
		int mstrZ = nbt.getInteger("MasterZ");
		
		if(built) {		
			slaves = new ArrayList<MultiPart>();
			
			NBTTagList tagList = nbt.getTagList("Slaves");
			for (int i = 0; i < tagList.tagCount(); i++) {
				NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
				int x = tag.getInteger("xCoord");
				int y = tag.getInteger("yCoord");
				int z = tag.getInteger("zCoord");
				slaves.add(new MultiPart(x, y, z));
			}
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if(master != null) {
			nbt.setInteger("MasterX", master.xCoord);
			nbt.setInteger("MasterY", master.yCoord);
			nbt.setInteger("MasterZ", master.zCoord);
			nbt.setBoolean("Built", true);
			NBTTagList itemList = new NBTTagList();
			for (MultiPart part: slaves) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("xCoord", part.xCoord);
				tag.setInteger("yCoord", part.yCoord);
				tag.setInteger("zCoord", part.zCoord);
				itemList.appendTag(tag);
			}

			nbt.setTag("Slaves", itemList);
		} else {
			nbt.setBoolean("Built", false);
		}
	}
}
