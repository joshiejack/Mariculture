package mariculture.core.blocks.base;

import mariculture.core.gui.ContainerMariculture;
import mariculture.core.network.Packets;
import mariculture.core.util.IPowered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;

public abstract class TileMultiInvMachinePowered extends TileMultiInvMachine implements IEnergyHandler, IPowered {
	protected EnergyStorage energyStorage;
	
	public TileMultiInvMachinePowered() {
		energyStorage = new EnergyStorage(getRFCapacity());
		inventory = new ItemStack[4];
		offset = 4;
	}
	
	public abstract int getRFCapacity();
	
	@Override
	public void updateUpgrades() {
		super.updateUpgrades();
		energyStorage.setCapacity(getRFCapacity() + rf);
	}

	
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return mstr.built? ((TileMultiInvMachinePowered)worldObj.getBlockTileEntity(mstr.x, mstr.y, mstr.z)).energyStorage.receiveEnergy(maxReceive, simulate): 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return mstr.built? ((TileMultiInvMachinePowered)worldObj.getBlockTileEntity(mstr.x, mstr.y, mstr.z)).energyStorage.extractEnergy(maxExtract, simulate): 0;
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return mstr.built? ((TileMultiInvMachinePowered)worldObj.getBlockTileEntity(mstr.x, mstr.y, mstr.z)).energyStorage.getEnergyStored(): 0;
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return mstr.built? ((TileMultiInvMachinePowered)worldObj.getBlockTileEntity(mstr.x, mstr.y, mstr.z)).energyStorage.getMaxEnergyStored(): 0;
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
	public void updateMaster() {
		super.updateMaster();
		
		if(inventory[3] != null) {
			int rf = (inventory[3] != null)? ((IEnergyContainerItem)inventory[3].getItem()).extractEnergy(inventory[3], 3000, true): 0;
			if(rf > 0) {
				int drain = receiveEnergy(ForgeDirection.UP, rf, true);
				if(drain > 0) {
					((IEnergyContainerItem)inventory[3].getItem()).extractEnergy(inventory[3], drain, false);
					receiveEnergy(ForgeDirection.UP, drain, false);
				}
			}
		}
	}
	
	@Override
	public void getGUINetworkData(int id, int value) {
		super.getGUINetworkData(id, value);
		switch (id) {
		case 3:
			energyStorage.setEnergyStored(value);
			break;
		case 4: 
			energyStorage.setCapacity(value);
			break;
		}
	}
	
	@Override
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		super.sendGUINetworkData(container, player);
		Packets.updateGUI(player, container, 3, energyStorage.getEnergyStored());
		Packets.updateGUI(player, container, 4, energyStorage.getMaxEnergyStored());
	}
}
