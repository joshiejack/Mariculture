package mariculture.core.tile.base;

import mariculture.core.gui.ContainerMariculture;
import mariculture.core.util.IPowered;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;

public abstract class TileMachinePowered extends TileMachine implements IEnergyHandler, IPowered {
    protected EnergyStorage energyStorage;

    public TileMachinePowered() {
        energyStorage = new EnergyStorage(getRFCapacity());
        inventory = new ItemStack[4];
        offset = 4;
    }

    public abstract int getRFCapacity();

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        int ret = energyStorage.receiveEnergy(maxReceive, simulate);
        if (!canWork) if (energyStorage.getEnergyStored() >= getRFUsage() * 2) {
            updateCanWork();
        }

        return ret;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        int ret = energyStorage.extractEnergy(maxExtract, simulate);
        if (ret <= 0) {
            updateCanWork();
        }

        return ret;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
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
    public void updateUpgrades() {
        super.updateUpgrades();
        energyStorage.setCapacity(getRFCapacity() + rf);
    }

    public abstract int getRFUsage();

    @Override
    public void updateMachine() {
        if (canWork) {
            extractEnergy(ForgeDirection.DOWN, getRFUsage(), false);
            processed += speed;
            if (processed >= max) {
                process();
                updateCanWork();
                processed = 0;
            }
        } else {
            processed = 0;
        }
    }

    @Override
    public void update() {
        super.update();
        if (inventory[3] != null) {
            int rf = inventory[3] != null && inventory[3].getItem() instanceof IEnergyContainerItem ? ((IEnergyContainerItem) inventory[3].getItem()).extractEnergy(inventory[3], 1000, true) : 0;
            if (rf > 0) {
                int drain = receiveEnergy(ForgeDirection.UP, rf, true);
                if (drain > 0) {
                    ((IEnergyContainerItem) inventory[3].getItem()).extractEnergy(inventory[3], drain, false);
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
    public void sendGUINetworkData(ContainerMariculture container, ICrafting crafting) {
        super.sendGUINetworkData(container, crafting);
        crafting.sendProgressBarUpdate(container, 3, energyStorage.getEnergyStored());
        crafting.sendProgressBarUpdate(container, 4, energyStorage.getMaxEnergyStored());
    }

    @Override
    public String getPowerText() {
        return getEnergyStored(ForgeDirection.UNKNOWN) + " / " + getMaxEnergyStored(ForgeDirection.UNKNOWN) + " RF";
    }

    @Override
    public int getPowerScaled(int i) {
        return energyStorage.getEnergyStored() * i / energyStorage.getMaxEnergyStored();
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
}
