package mariculture.diving;

import mariculture.core.blocks.base.TileMultiBlock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class TileAirCompressor extends TileMultiBlock implements IEnergyHandler {
	private int machineTick;
	
	private static final int max = 480;
	private int storedAir;
	protected EnergyStorage energyStorage;
	
	public TileAirCompressor() {
		energyStorage = new EnergyStorage(10000);
	}
	
	public int getAirStoredScaled(int scale) {
		return (storedAir * scale) / max;
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
	public void updateMaster() {
		super.updateMaster();
		machineTick++;
		
		
		if(onTick(20)) {
			
		}
	}
}