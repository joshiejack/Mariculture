package maritech.tile.base;

import java.util.ArrayList;
import java.util.Arrays;

import mariculture.core.tile.base.TileMultiMachine;
import mariculture.core.util.IPowered;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyReceiver;

public abstract class TileMultiMachinePowered extends TileMultiMachine implements IEnergyReceiver, IPowered {
    protected EnergyStorage energyStorage;
    protected int usage = -1;

    public TileMultiMachinePowered() {
        energyStorage = new EnergyStorage(getRFCapacity());
        inventory = new ItemStack[4];
        offset = 8;
    }

    public abstract int getRFCapacity();

    @Override
    public void updateUpgrades() {
        super.updateUpgrades();
        energyStorage.setCapacity(getRFCapacity() + rf);
        updatePowerPerTick();
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        if (!worldObj.isRemote) {
            TileEntity mstr = getMaster();
            if (mstr instanceof TileMultiMachinePowered) {
                TileMultiMachinePowered master = (TileMultiMachinePowered) mstr;
                if (master.usage == -1) updatePowerPerTick();
                int ret = master.energyStorage.receiveEnergy(maxReceive, simulate);
                if (!master.canWork) {
                    if (master.energyStorage.getEnergyStored() >= getPowerPerTick() * 2) {
                        master.updateCanWork();
                    }
                }

                return ret;
            } else return 0;
        } else return 0;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return getMaster() != null ? ((TileMultiMachinePowered) getMaster()).energyStorage.getEnergyStored() : 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return getMaster() != null ? ((TileMultiMachinePowered) getMaster()).energyStorage.getMaxEnergyStored() : 0;
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
        return energyStorage.getEnergyStored() * i / energyStorage.getMaxEnergyStored();
    }

    @Override
    public void updateMaster() {
        super.updateMaster();

        if (inventory[3] != null) {
            int rf = inventory[3] != null && inventory[3].getItem() instanceof IEnergyContainerItem ? ((IEnergyContainerItem) inventory[3].getItem()).extractEnergy(inventory[3], 3000, true) : 0;
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
            case 6:
                energyStorage.setEnergyStored(value);
                break;
            case 7:
                energyStorage.setCapacity(value);
                break;
            case 8:
                usage = value;
                break;
        }
    }
    
    protected int lastEnergy;
    protected int lastCapacity;
    protected int lastUsage;
    
    @Override
    public boolean hasChanged() {
        return super.hasChanged() || energyStorage.getEnergyStored() != lastEnergy || energyStorage.getMaxEnergyStored() != lastCapacity || lastUsage != (canWork ? getPowerPerTick() : 0);
    }
    
    @Override
    public ArrayList<Integer> getGUIData() {
        lastEnergy = energyStorage.getEnergyStored();
        lastCapacity = energyStorage.getMaxEnergyStored();
        lastUsage = canWork ? getPowerPerTick() : 0;
        
        ArrayList<Integer> list = super.getGUIData();
        list.addAll(new ArrayList(Arrays.asList(new Integer[] { lastEnergy, lastCapacity, lastUsage })));
        return list;
    }
}
