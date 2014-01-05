package mariculture.diving;

import java.util.ArrayList;

import mariculture.core.blocks.base.TileMultiBlock;
import mariculture.core.blocks.base.TileMultiBlock.MultiPart;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.network.Packet110CustomTileUpdate;
import mariculture.core.network.Packet117AirCompressorUpdate;
import mariculture.core.network.Packet118InitAirCompressor;
import mariculture.core.network.Packets;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class TileAirCompressor extends TileMultiBlock implements IEnergyHandler {
	private int machineTick;
	public ForgeDirection facing = ForgeDirection.UNKNOWN;
	public static final int max = 480;
	public int storedAir = 0;
	public EnergyStorage energyStorage;
	
	public TileAirCompressor() {
		energyStorage = new EnergyStorage(10000);
	}
	
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return getMaster() != null? ((TileAirCompressor)getMaster()).energyStorage.receiveEnergy(maxReceive, simulate): 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return getMaster() != null? ((TileAirCompressor)getMaster()).energyStorage.extractEnergy(maxExtract, simulate): 0;
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return getMaster() != null? ((TileAirCompressor)getMaster()).energyStorage.getEnergyStored(): 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return getMaster() != null? ((TileAirCompressor)getMaster()).energyStorage.getMaxEnergyStored(): 0;
	}
	
	public int getAirStored() {
		return storedAir;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		energyStorage.readFromNBT(nbt);
		storedAir = nbt.getInteger("StoredAir");
		facing = ForgeDirection.values()[nbt.getInteger("Facing")];
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		energyStorage.writeToNBT(nbt);
		nbt.setInteger("StoredAir", storedAir);
		nbt.setInteger("Facing", facing.ordinal());
	}
	
	public boolean onTick(int i) {
		return machineTick % i == 0;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	private boolean init = false;
	
	@Override
	public void updateMaster() {
		machineTick++;
				
		if(!init && !worldObj.isRemote) {
			//Init Master
			Packets.updateTile(this, 32, new Packet118InitAirCompressor(xCoord, yCoord, zCoord, master.xCoord, master.yCoord, master.zCoord, facing).build());
			//Now Init your slaves :D
			for(MultiPart slave: slaves) {
				TileEntity te = worldObj.getBlockTileEntity(slave.xCoord, slave.yCoord, slave.zCoord);
				if(te != null && te instanceof TileAirCompressor) {
					Packets.updateTile(te, 32, 
							new Packet118InitAirCompressor(te.xCoord, te.yCoord, te.zCoord, master.xCoord, master.yCoord, master.zCoord, 
									((TileAirCompressor)te).facing).build());
				}
			}
			
			init = true;
		}
		
		//System.out.println("i am master and i am built");
		
		if(onTick(20)) {
			if(energyStorage.extractEnergy(1000, true) >= 1000) {				
				energyStorage.extractEnergy(1000, false);
				if(storedAir < max) {
					storedAir++;
					
					Packets.updateTile(this, 64, new Packet117AirCompressorUpdate(xCoord, yCoord, zCoord, storedAir, getEnergyStored(ForgeDirection.UP)).build());
				}
			}
		}
	}
	
	public float getWheelAngle() {
		return 0;
	}
	
	//Sets the facing direction of this block
	public void setFacing(ForgeDirection dir) {
		this.facing = dir;
	}
	
	private MultiPart setAsSlave(MultiPart master, int x, int y, int z, ForgeDirection dir) {
		((TileAirCompressor) worldObj.getBlockTileEntity(x, y, z)).setFacing(dir);
		return super.setAsSlave(master, x, y, z);
	}
	
	protected void setAsMaster(MultiPart mstr, ArrayList<MultiPart> parts, ForgeDirection dir) {
		((TileAirCompressor) worldObj.getBlockTileEntity(mstr.xCoord, mstr.yCoord, mstr.zCoord)).setFacing(dir);
		super.setAsMaster(mstr, parts);
	}
	
//Master Stuff
	public boolean isPartnered(int x, int y, int z) {
		TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
		return tile instanceof TileAirCompressor?  ((TileAirCompressor)tile).master != null : false;
	}
	
	public boolean isBaseBlock(int x, int y, int z) {
		return worldObj.getBlockId(x, y, z) == this.getBlockType().blockID 
				&& worldObj.getBlockMetadata(x, y, z) == DoubleMeta.COMPRESSOR_BASE;
	}
	
	public boolean isTopBlock(int x, int y, int z) {
		return worldObj.getBlockId(x, y, z) == this.getBlockType().blockID 
				&& worldObj.getBlockMetadata(x, y, z) == DoubleMeta.COMPRESSOR_TOP;
	}
	
	public boolean isBase(int x, int y, int z) {
		return isBaseBlock(x, y, z) && !isPartnered(x, y, z);
	}
	
	public boolean isTop(int x, int y, int z) {
		return isTopBlock(x, y, z) && !isPartnered(x, y , z);
	}
	
	@Override
	public void onBlockPlaced() {
		if(this.getBlockMetadata() == DoubleMeta.COMPRESSOR_BASE)
			onBlockPlacedBase(xCoord, yCoord, zCoord);
		else if(this.getBlockMetadata() == DoubleMeta.COMPRESSOR_TOP)
			onBlockPlacedTop(xCoord, yCoord, zCoord);
		Packets.updateTile(this, 32, getDescriptionPacket());
        worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
	
	@Override
	public void onBlockBreak() {
		if(master != null) {
			TileMultiBlock mstr = (TileMultiBlock) worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord);
			if(mstr != null) {
				for(MultiPart part: mstr.slaves) {
					if(worldObj.getBlockTileEntity(part.xCoord, part.yCoord, part.zCoord) != null) {
						TileMultiBlock te = (TileMultiBlock) worldObj.getBlockTileEntity(part.xCoord, part.yCoord, part.zCoord);
						te.clearMaster();
						((TileAirCompressor) te).setFacing(ForgeDirection.UNKNOWN);
						Packets.updateTile(te, 32, te.getDescriptionPacket());
					}
				}
				
				mstr.clearMaster();
				((TileAirCompressor) mstr).setFacing(ForgeDirection.UNKNOWN);
			}
			
			Packets.updateTile(this, 32, getDescriptionPacket());
		}
	}
	
	//Base Setting of Master Block
	public void onBlockPlacedBase(int x, int y, int z) {
		if(isBase(x, y, z) && isBase(x + 1, y, z) && isTop(x, y + 1, z) && isTop(x + 1, y + 1, z)) {
			MultiPart mstr = new MultiPart(x, y, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x + 1, y, z, ForgeDirection.WEST));
			parts.add(setAsSlave(mstr, x + 1, y + 1, z, ForgeDirection.WEST));
			parts.add(setAsSlave(mstr, x, y + 1, z, ForgeDirection.EAST));
			setAsMaster(mstr, parts, ForgeDirection.EAST);
		}
		
		if(isBase(x, y, z) && isBase(x - 1, y, z) && isTop(x, y + 1, z) && isTop(x - 1, y + 1, z)) {
			MultiPart mstr = new MultiPart(x - 1, y, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.WEST));
			parts.add(setAsSlave(mstr, x - 1, y + 1, z, ForgeDirection.EAST));
			parts.add(setAsSlave(mstr, x, y + 1, z, ForgeDirection.WEST));
			setAsMaster(mstr, parts, ForgeDirection.EAST);
		}
		
		if(isBase(x, y, z) && isBase(x, y, z + 1) && isTop(x, y + 1, z) && isTop(x, y + 1, z + 1)) {
			MultiPart mstr = new MultiPart(x, y, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z + 1, ForgeDirection.SOUTH));
			parts.add(setAsSlave(mstr, x, y + 1, z + 1, ForgeDirection.SOUTH));
			parts.add(setAsSlave(mstr, x, y + 1, z, ForgeDirection.NORTH));
			setAsMaster(mstr, parts, ForgeDirection.NORTH);
		}
		
		if(isBase(x, y, z) && isBase(x, y, z - 1) && isTop(x, y + 1, z) && isTop(x, y + 1, z - 1)) {
			MultiPart mstr = new MultiPart(x, y, z - 1);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.SOUTH));
			parts.add(setAsSlave(mstr, x, y + 1, z - 1, ForgeDirection.NORTH));
			parts.add(setAsSlave(mstr, x, y + 1, z, ForgeDirection.SOUTH));
			setAsMaster(mstr, parts, ForgeDirection.NORTH);
		}
	}
	
	//Top Setting of Master Block
	public void onBlockPlacedTop(int x, int y, int z) {
		if(isBase(x, y - 1, z) && isBase(x + 1, y - 1, z) && isTop(x, y, z) && isTop(x + 1, y, z)) {
			MultiPart mstr = new MultiPart(x, y - 1, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.EAST));
			parts.add(setAsSlave(mstr, x + 1, y, z, ForgeDirection.WEST));
			parts.add(setAsSlave(mstr, x + 1, y - 1, z, ForgeDirection.WEST));
			setAsMaster(mstr, parts, ForgeDirection.EAST);
		}
		
		if(isBase(x, y - 1, z) && isBase(x - 1, y - 1, z) && isTop(x, y, z) && isTop(x - 1, y, z)) {
			MultiPart mstr = new MultiPart(x - 1, y - 1, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.WEST));
			parts.add(setAsSlave(mstr, x - 1, y, z, ForgeDirection.EAST));
			parts.add(setAsSlave(mstr, x, y - 1, z, ForgeDirection.WEST));
			setAsMaster(mstr, parts, ForgeDirection.EAST);
		}
		
		if(isBase(x, y - 1, z) && isBase(x, y - 1, z + 1) && isTop(x, y, z) && isTop(x, y, z + 1)) {
			MultiPart mstr = new MultiPart(x, y - 1, z);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z + 1, ForgeDirection.SOUTH));
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.NORTH));
			parts.add(setAsSlave(mstr, x, y - 1, z + 1, ForgeDirection.SOUTH));
			setAsMaster(mstr, parts, ForgeDirection.NORTH);
		}
		
		if(isBase(x, y - 1, z) && isBase(x, y - 1, z - 1) && isTop(x, y, z) && isTop(x, y, z - 1)) {
			MultiPart mstr = new MultiPart(x, y - 1, z - 1);
			ArrayList<MultiPart> parts = new ArrayList<MultiPart>();
			parts.add(setAsSlave(mstr, x, y, z, ForgeDirection.SOUTH));
			parts.add(setAsSlave(mstr, x, y, z - 1, ForgeDirection.NORTH));
			parts.add(setAsSlave(mstr, x, y - 1, z, ForgeDirection.SOUTH));
			setAsMaster(mstr, parts, ForgeDirection.NORTH);
		}
	}
}