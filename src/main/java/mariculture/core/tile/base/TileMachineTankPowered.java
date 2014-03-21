package mariculture.core.tile.base;

import mariculture.core.gui.ContainerMariculture;
import mariculture.core.network.Packets;
import mariculture.core.util.IPowered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;

public abstract class TileMachineTankPowered extends TileMachineTank implements IEnergyHandler, IPowered {
	protected EnergyStorage energyStorage;
	
	public TileMachineTankPowered() {
		energyStorage = new EnergyStorage(getRFCapacity());
		inventory = new ItemStack[6];
		offset = 8;
	}
	
	public abstract int getRFCapacity();
	
	@Override
	public void updateUpgrades() {
		super.updateUpgrades();
		energyStorage.setCapacity(getRFCapacity() + rf);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return energyStorage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return energyStorage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energyStorage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return energyStorage.getMaxEnergyStored();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		energyStorage.readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		energyStorage.writeToNBT(nbt);
	}
	
	@Override
	public String getPowerText() {
		return getEnergyStored(ForgeDirection.UNKNOWN) + " / " + getMaxEnergyStored(ForgeDirection.UNKNOWN) + " RF";
	}

	@Override
	public int getPowerScaled(int i) {
		return (energyStorage.getEnergyStored() * i) / energyStorage.getMaxEnergyStored();
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		doBattery();
	}
	
	public void doBattery() {
		if(inventory[5] != null) {
			int rf = (inventory[5] != null && inventory[5].getItem() instanceof IEnergyContainerItem)? 
					((IEnergyContainerItem)inventory[5].getItem()).extractEnergy(inventory[5], 1000, true): 0;
			if(rf > 0) {
				int drain = receiveEnergy(ForgeDirection.UP, rf, true);
				if(drain > 0) {
					((IEnergyContainerItem)inventory[5].getItem()).extractEnergy(inventory[5], drain, false);
					receiveEnergy(ForgeDirection.UP, drain, false);
				}
			}
		}
	}
	
	@Override
	public void getGUINetworkData(int id, int value) {
		super.getGUINetworkData(id, value);
		switch (id) {
		case 6:
			energyStorage.setEnergyStored(value);
			break;
		case 7: 
			energyStorage.setCapacity(value);
			break;
		}
	}
	
	@Override
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		super.sendGUINetworkData(container, player);
		Packets.updateGUI(player, container, 6, energyStorage.getEnergyStored());
		Packets.updateGUI(player, container, 7, energyStorage.getMaxEnergyStored());
	}
}
