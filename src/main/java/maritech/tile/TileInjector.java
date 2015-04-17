package maritech.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.network.PacketHandler;
import mariculture.core.util.Fluids;
import mariculture.core.util.ITank;
import mariculture.core.util.Tank;
import mariculture.fishery.Fishery;
import maritech.items.ItemFishDNA;
import maritech.tile.base.TileMachinePowered;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileInjector extends TileMachinePowered implements IFluidHandler, ITank {
    public static final int FLUID_REQUIRED = 5000;
    public static final int FISH = 8;
    public static final int[] DNA = new int[] { 10, 11, 12 };
    public Tank tank;
    public Tank tank2;
    protected int[] rate;

    public TileInjector() {
        inventory = new ItemStack[13];
        max = MachineSpeeds.getDNAMachineSpeed() * 5;
        output = new int[] { 9 };
        rate = new int[0];
        tank = new Tank(getTankCapacity(0));
        tank2 = new Tank(getTankCapacity(0));
    }

    @Override
    public int getRFCapacity() {
        return 250000;
    }

    public int getTankCapacity(int size) {
        return (1 + size) * 5000;
    }

    @Override
    public void updatePowerPerTick() {
        if (rf <= 300000) {
            double modifier = 1D - (rf / 300000D) * 0.75D;
            usage = (int) (modifier * (400 + ((speed - 1) * 400)));
        } else usage = 1;
    }

    @Override
    public void updateUpgrades() {
        super.updateUpgrades();
        tank.setCapacity(getTankCapacity(storage));
        if (tank.getFluidAmount() > tank.getCapacity()) {
            tank.setFluidAmount(tank.getCapacity());
        }

        tank2.setCapacity(getTankCapacity(storage));
        if (tank2.getFluidAmount() > tank.getCapacity()) {
            tank2.setFluidAmount(tank2.getCapacity());
        }
    }

    @Override
    public void update() {
        super.update();
        FluidHelper.process(tank, this, 4, 6);
        FluidHelper.process(tank2, this, 5, 7);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 4, 5, 6, 7, 8, 9 };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, int side) {
        if (slot == 4 || slot == 5) {
            return FluidHelper.isFluidOrEmpty(stack);
        } else if (slot == FISH) {
            return stack.getItem() == Fishery.fishy;
        } else return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack stack, int side) {
        return slot == 6 || slot == 7 || slot == 9;
    }

    @Override
    public EjectSetting getEjectType() {
        return EjectSetting.ITEM;
    }

    private boolean hasFish() {
        return inventory[FISH] != null;
    }

    private boolean hasBlood() {
        return hasBlood(tank) || hasBlood(tank2);
    }

    private boolean hasBlood(Tank tank) {
        return tank.getFluid() != null && Fluids.isBlood(tank.getFluid().getFluid().getName()) && tank.getFluidAmount() >= FLUID_REQUIRED;
    }

    private boolean hasGoo() {
        return hasGoo(tank) || hasGoo(tank2);
    }

    private boolean hasGoo(Tank tank) {
        return tank.getFluid() != null && tank.getFluid().getFluid() == Fluids.getFluid("flux") && tank.getFluidAmount() >= FLUID_REQUIRED;
    }

    private boolean hasPower() {
        return energyStorage.getEnergyStored() > 10000;
    }

    @Override
    public boolean canWork() {
        return RedstoneMode.canWork(this, mode) && hasFish() && hasPower() && hasBlood() && hasGoo() && hasRoom(null);
    }

    @Override
    public void process() {
        ItemStack fish = null;
        ItemStack clone = inventory[FISH].copy();
        clone.stackSize = 1;
        
        boolean used = false;
        for (int slot = 10; slot <= 12; slot++) {
            ItemStack dna = inventory[slot];
            if (dna != null && dna.hasTagCompound()) {
                if (dna.attemptDamageItem(1, worldObj.rand)) {
                    inventory[slot] = null;
                }
                
                if (fish == null) {
                    fish = ItemFishDNA.add(dna, clone);
                } else fish = ItemFishDNA.add(dna, fish);
            }
        }

        if (fish != null) {
            decrStackSize(FISH, 1);
            helper.insertStack(fish, output);
            tank.drain(5000, true);
            tank2.drain(5000, true);
        }
    }

    @Override
    public FluidStack getFluid(int transfer) {
        if (tank.getFluid() == null) return null;
        if (tank.getFluidAmount() - transfer < 0) return null;

        return new FluidStack(tank.getFluidID(), transfer);
    }

    @Override
    public FluidStack getFluid() {
        return getFluid((byte) 1);
    }

    @Override
    public FluidStack getFluid(byte id) {
        if (id == 1) return tank.getFluid();
        else if (id == 2) return tank2.getFluid();
        return null;
    }

    @Override
    public void setFluid(FluidStack fluid) {
        setFluid(fluid, (byte) 1);
    }

    @Override
    public void setFluid(FluidStack fluid, byte id) {
        if (id == (byte) 1) {
            tank.setFluid(fluid);
        } else if (id == (byte) 2) {
            tank2.setFluid(fluid);
        }
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        FluidStack ret = tank2.drain(maxDrain, doDrain);
        if (ret != null) {
            if (doDrain) {
                PacketHandler.syncFluidTank(this, getFluid((byte) 2), (byte) 2);
            }
        } else {
            ret = tank.drain(maxDrain, doDrain);
            if (ret != null) if (doDrain) {
                PacketHandler.syncFluidTank(this, getFluid((byte) 1), (byte) 1);
            }
        }

        return ret;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int ret = tank.fill(resource, doFill, tank2);
        if (ret > 0) {
            if (doFill) {
                PacketHandler.syncFluidTank(this, getFluid((byte) 1), (byte) 1);
            }
        } else {
            ret = tank2.fill(resource, doFill, tank);
            if (ret > 0) if (doFill) {
                PacketHandler.syncFluidTank(this, getFluid((byte) 2), (byte) 2);
            }
        }

        return ret;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { tank.getInfo(), tank2.getInfo() };
    }

    @Override
    public void setGUIData(int id, int value) {
        super.setGUIData(id, value);
        switch (id) {
            case 6:
                tank.setFluidID(value);
                break;
            case 7:
                tank.setFluidAmount(value);
                break;
            case 8:
                tank.setCapacity(value);
                break;
            case 9:
                tank2.setFluidID(value);
                break;
            case 10:
                tank2.setFluidAmount(value);
                break;
            case 11:
                tank2.setCapacity(value);
                break;
        }
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    private int lastFluidID;
    private int lastFluidAmount;
    private int lastFluidCapacity;
    private int lastFluid2ID;
    private int lastFluid2Amount;
    private int lastFluid2Capacity;

    @Override
    public boolean hasChanged() {
        return super.hasChanged() || lastFluidID != tank.getFluidID() || lastFluidAmount != tank.getFluidAmount() || lastFluidCapacity != tank.getCapacity() || lastFluid2ID != tank2.getFluidID() || lastFluid2Amount != tank2.getFluidAmount() || lastFluidCapacity != tank2.getCapacity();
    }

    @Override
    public ArrayList<Integer> getGUIData() {
        lastFluidID = tank.getFluidID();
        lastFluidAmount = tank.getFluidAmount();
        lastFluidCapacity = tank.getCapacity();

        ArrayList list = super.getGUIData();
        list.addAll(Arrays.asList(new Integer[] { lastFluidID, lastFluidAmount, lastFluidCapacity, tank2.getFluidID(), tank2.getFluidAmount(), tank2.getCapacity() }));
        return list;
    }

    @Override
    public int getTankScaled(int i) {
        return 0;
    } //NEVER CALLED

    @Override
    public String getFluidName() {
        return null;
    } //NEVER CALLED

    @Override
    public List getFluidQty(List tooltip) {
        return null;
    } //NEVER CALLLED

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return drain(from, resource.amount, doDrain);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        tank.readFromNBT(nbt.getCompoundTag("Tank1"));
        tank2.readFromNBT(nbt.getCompoundTag("Tank2"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagCompound t1 = new NBTTagCompound();
        NBTTagCompound t2 = new NBTTagCompound();
        tank.writeToNBT(t1);
        tank2.writeToNBT(t2);
        nbt.setTag("Tank1", t1);
        nbt.setTag("Tank2", t2);
    }
}
