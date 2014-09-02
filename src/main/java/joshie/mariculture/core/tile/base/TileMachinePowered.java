package joshie.mariculture.core.tile.base;

import java.util.ArrayList;
import java.util.Arrays;

import joshie.mariculture.core.util.IPowered;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;

public abstract class TileMachinePowered extends TileMachine implements IEnergyHandler, IPowered {
    protected EnergyStorage energyStorage;
    protected int usage = -1;

    public TileMachinePowered() {
        energyStorage = new EnergyStorage(getRFCapacity());
        inventory = new ItemStack[4];
        offset = 4;
    }

    public abstract int getRFCapacity();

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        if (!worldObj.isRemote) {
            if (usage == -1) updatePowerPerTick();
            int ret = energyStorage.receiveEnergy(maxReceive, simulate);
            if (!canWork) {
                if (energyStorage.getEnergyStored() >= getPowerPerTick() * 2) {
                    updateCanWork();
                }
            }

            return ret;
        } else return 0;
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
    public int getPowerPerTick() {
        return usage;
    }

    @Override
    public void updatePowerPerTick() {
        usage = (int) (1.05D - (rf / 300000 * 0.75D));
    }

    @Override
    public boolean isConsumer() {
        return true;
    }

    @Override
    public void updateUpgrades() {
        super.updateUpgrades();
        energyStorage.setCapacity(getRFCapacity() + rf);
        updatePowerPerTick();
    }

    @Override
    public void updateMachine() {
        if (canWork) {
            extractEnergy(ForgeDirection.DOWN, getPowerPerTick(), false);
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
                    if (inventory[3] == null || inventory[3].stackSize <= 0) {
                        decrStackSize(3, 1);
                    }

                    receiveEnergy(ForgeDirection.UP, drain, false);
                }
            }
        }
    }

    @Override
    public void setGUIData(int id, int value) {
        super.setGUIData(id, value);
        switch (id) {
            case 3:
                energyStorage.setEnergyStored(value);
                break;
            case 4:
                energyStorage.setCapacity(value);
                break;
            case 5:
                usage = value;
                break;
        }
    }

    @Override
    public ArrayList<Integer> getGUIData() {
        ArrayList<Integer> list = super.getGUIData();
        list.addAll(new ArrayList(Arrays.asList(new Integer[] { energyStorage.getEnergyStored(), energyStorage.getMaxEnergyStored(), canWork ? getPowerPerTick() : 0 })));
        return list;
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
