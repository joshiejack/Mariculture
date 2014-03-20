package mariculture.core.blocks.base;

import java.util.ArrayList;

import mariculture.core.network.PacketMultiInit;
import mariculture.core.network.Packets;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileMultiBlock extends TileEntity {
	protected boolean needsInit = false;
	private boolean init = false;
	public ForgeDirection facing = ForgeDirection.UNKNOWN;
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
		TileMultiBlock slave = (TileMultiBlock) worldObj.getTileEntity(x, y, z);
		slave.setMaster(new MultiPart(master.xCoord, master.yCoord, master.zCoord));
		return new MultiPart(x, y, z);
	}
	
	protected void setAsMaster(MultiPart mstr, ArrayList<MultiPart> parts) {
		TileMultiBlock master = (TileMultiBlock) worldObj.getTileEntity(mstr.xCoord, mstr.yCoord, mstr.zCoord);
		master.setMaster(mstr);
		master.setSlaves(parts);
	}
	
	protected MultiPart setAsSlave(MultiPart master, int x, int y, int z, ForgeDirection dir) {
		((TileMultiBlock) worldObj.getTileEntity(x, y, z)).setFacing(dir);
		return setAsSlave(master, x, y, z);
	}
	
	protected void setAsMaster(MultiPart mstr, ArrayList<MultiPart> parts, ForgeDirection dir) {
		((TileMultiBlock) worldObj.getTileEntity(mstr.xCoord, mstr.yCoord, mstr.zCoord)).setFacing(dir);
		setAsMaster(mstr, parts);
	}
	
	public void setMaster(MultiPart mstr) {
		master = mstr;
	}
	
	public void setSlaves(ArrayList<MultiPart> parts) {
		slaves = parts;
	}
	
	public boolean isPart(int x, int y, int z) {
		return worldObj.getTileEntity(x, y, z) instanceof TileMultiBlock;
	}
	
	public boolean isMaster() {
		return master != null && master.isSame(xCoord, yCoord, zCoord);
	}
	
	protected boolean isInit() {
		return init;
	}
	
	public TileMultiBlock getMaster() {
		if(master == null)
			return null;
		TileEntity tile = worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord);
		return tile != null && tile instanceof TileMultiBlock? (TileMultiBlock)tile: null;
	}
	
	public ArrayList<MultiPart> getSlaves() {
		return slaves;
	}
	
	protected void addSlave(MultiPart slave) {
		this.slaves.add(slave);
	}
	
	public void removeSlave(MultiPart part) {
		slaves.remove(part);
	}
	
	public void setFacing(ForgeDirection dir) {
		this.facing = dir;
	}
	
	protected void setInit(boolean bool) {
		this.init = bool;
	}
	
	public boolean isPartnered(int x, int y, int z) {
		TileEntity tile = worldObj.getTileEntity(x, y, z);
		return tile instanceof TileMultiBlock?  ((TileMultiBlock)tile).master != null : false;
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
		
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public void onBlockBreak() {
		if(master != null) {
			//Get the Master Tile Entity
			TileMultiBlock mstr = (TileMultiBlock) worldObj.getTileEntity(master.xCoord, master.yCoord, master.zCoord);
			if(mstr != null) {
				//Clear out the slaves
				if(mstr.slaves != null) {
					for(MultiPart part: mstr.slaves) {
						if(worldObj.getTileEntity(part.xCoord, part.yCoord, part.zCoord) != null) {
							TileMultiBlock te = (TileMultiBlock) worldObj.getTileEntity(part.xCoord, part.yCoord, part.zCoord);
							te.setMaster(null);
							te.setInit(false);
							((TileMultiBlock) te).setFacing(ForgeDirection.UNKNOWN);
							Packets.updateAround(te, new PacketMultiInit(te.xCoord, te.yCoord, te.zCoord, 0, -1, 0, ForgeDirection.UNKNOWN));
						}
					}
				}
				
				//Clear out the slaves and the master for the master tile, and the direction
				mstr.setSlaves(null);
				mstr.setMaster(null);
				mstr.setInit(false);
				((TileMultiBlock) mstr).setFacing(ForgeDirection.UNKNOWN);
				Packets.updateAround(mstr, new PacketMultiInit(mstr.xCoord, mstr.yCoord, mstr.zCoord,  0, -1, 0, ForgeDirection.UNKNOWN));
			}
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
				if(!worldObj.isRemote) {
					if(needsInit && !init) {
						init();
					}
				}
				
				updateMaster();
			} else {
				updateSlaves();
			}
		}
	}
	
	//Initialize
	public void init() {
		//Init Master
		Packets.updateAround(this, new PacketMultiInit(xCoord, yCoord, zCoord, master.xCoord, master.yCoord, master.zCoord, facing));
		for(MultiPart slave: slaves) {
			TileEntity te = worldObj.getTileEntity(slave.xCoord, slave.yCoord, slave.zCoord);
			if(te != null && te.getClass().equals(getTEClass())) {
				Packets.updateAround(te, new PacketMultiInit(te.xCoord, te.yCoord, te.zCoord, master.xCoord, master.yCoord, master.zCoord, ((TileMultiBlock)te).facing));
			}
		}
			
		this.setInit(true);
	}
	
	public Class getTEClass() {
		return this.getClass();
	}

	public void updateMaster() {
		return;
	}
	
	public void updateSlaves() {
		return;
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
		
		boolean built = nbt.getBoolean("Built");
		if(built) {		
			facing = ForgeDirection.values()[nbt.getInteger("Facing")];
			int mstrX = nbt.getInteger("MasterX");
			int mstrY = nbt.getInteger("MasterY");
			int mstrZ = nbt.getInteger("MasterZ");
			master = new MultiPart(mstrX, mstrY, mstrZ);
			if(master.isSame(xCoord, yCoord, zCoord)) {
				slaves = new ArrayList<MultiPart>();
				NBTTagList tagList = nbt.getTagList("Slaves", 10);
				for (int i = 0; i < tagList.tagCount(); i++) {
					NBTTagCompound tag = (NBTTagCompound) tagList.getCompoundTagAt(i);
					int x = tag.getInteger("xCoord");
					int y = tag.getInteger("yCoord");
					int z = tag.getInteger("zCoord");
					slaves.add(new MultiPart(x, y, z));
				}
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
			nbt.setInteger("Facing", facing.ordinal());
			
			NBTTagList itemList = new NBTTagList();
			if(slaves != null) {
				for (MultiPart part: slaves) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("xCoord", part.xCoord);
					tag.setInteger("yCoord", part.yCoord);
					tag.setInteger("zCoord", part.zCoord);
					itemList.appendTag(tag);
				}
			}

			nbt.setTag("Slaves", itemList);
		} else {
			nbt.setBoolean("Built", false);
			nbt.setInteger("Facing", ForgeDirection.UNKNOWN.ordinal());
		}
	}
}
