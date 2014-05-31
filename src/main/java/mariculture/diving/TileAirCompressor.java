package mariculture.diving;

import java.util.ArrayList;

import mariculture.core.lib.MachineRenderedMultiMeta;
import mariculture.core.network.PacketCompressor;
import mariculture.core.network.PacketHandler;
import mariculture.core.tile.base.TileMultiBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class TileAirCompressor extends TileMultiBlock implements IEnergyHandler {
	private int machineTick;
	public static final int max = 480;
	public int storedAir = 0;
	public EnergyStorage energyStorage;
	
	public TileAirCompressor() {
		energyStorage = new EnergyStorage(10000);
		needsInit = true;
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
	public boolean canConnectEnergy(ForgeDirection from) {
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
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		energyStorage.writeToNBT(nbt);
		nbt.setInteger("StoredAir", storedAir);
	}
	
	public boolean onTick(int i) {
		return machineTick % i == 0;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
		
	@Override
	public void updateMaster() {
		machineTick++;
		if(onTick(20)) {
			if(energyStorage.extractEnergy(1000, true) >= 1000) {				
				energyStorage.extractEnergy(1000, false);
				if(storedAir < max) {
					storedAir++;
					PacketHandler.sendAround(new PacketCompressor(xCoord, yCoord, zCoord, storedAir, getEnergyStored(ForgeDirection.UP)), this);
				}
			}
		}
	}
	
	public float getWheelAngle() {
		return 0;
	}
	
	@Override
	public Class getTEClass() {
		return this.getClass();
	}

//Master Stuff
	@Override
	public boolean isPartnered(int x, int y, int z) {
		TileEntity tile = worldObj.getTileEntity(x, y, z);
		return tile instanceof TileAirCompressor?  ((TileAirCompressor)tile).master != null : false;
	}
	
	public boolean isBaseBlock(int x, int y, int z) {
		return worldObj.getBlock(x, y, z) == this.getBlockType() && worldObj.getBlockMetadata(x, y, z) == MachineRenderedMultiMeta.COMPRESSOR_BASE;
	}
	
	public boolean isTopBlock(int x, int y, int z) {
		return worldObj.getBlock(x, y, z) == this.getBlockType() && worldObj.getBlockMetadata(x, y, z) == MachineRenderedMultiMeta.COMPRESSOR_TOP;
	}
	
	public boolean isBase(int x, int y, int z) {
		return isBaseBlock(x, y, z) && !isPartnered(x, y, z);
	}
	
	public boolean isTop(int x, int y, int z) {
		return isTopBlock(x, y, z) && !isPartnered(x, y , z);
	}
	
	@Override
	public void onBlockPlaced() {
		if(this.getBlockMetadata() == MachineRenderedMultiMeta.COMPRESSOR_BASE)
			onBlockPlacedBase(xCoord, yCoord, zCoord);
		else if(this.getBlockMetadata() == MachineRenderedMultiMeta.COMPRESSOR_TOP)
			onBlockPlacedTop(xCoord, yCoord, zCoord);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
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