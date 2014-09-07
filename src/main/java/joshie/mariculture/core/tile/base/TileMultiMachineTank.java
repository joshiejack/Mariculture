package joshie.mariculture.core.tile.base;

import java.util.ArrayList;
import java.util.Arrays;

import joshie.mariculture.api.core.IUpgradable;
import joshie.mariculture.api.core.MaricultureHandlers;
import joshie.mariculture.core.gui.feature.Feature;
import joshie.mariculture.core.gui.feature.FeatureEject.EjectSetting;
import joshie.mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import joshie.mariculture.core.helpers.BlockTransferHelper;
import joshie.mariculture.core.helpers.FluidHelper;
import joshie.mariculture.core.util.IEjectable;
import joshie.mariculture.core.util.IMachine;
import joshie.mariculture.core.util.IProgressable;
import joshie.mariculture.core.util.IRedstoneControlled;
import joshie.mariculture.core.util.Tank;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidContainerRegistry;

public abstract class TileMultiMachineTank extends TileMultiStorageTank implements IUpgradable, IMachine, ISidedInventory, IRedstoneControlled, IEjectable, IProgressable {
    protected BlockTransferHelper helper;
    //General Tick
    private int machineTick = 0;
    //Upgrade Stats
    protected int purity = 0;
    protected int heat = 0;
    protected int storage = 0;
    public int speed = 0;
    protected int rf = 0;
    //Tile Configuration
    public EjectSetting setting;
    public RedstoneMode mode;
    //GUI INT offset
    protected int offset = 9;
    //Machine vars
    protected int max;
    protected boolean canWork;
    protected int processed = 0;

    public TileMultiMachineTank() {
        inventory = new ItemStack[5];
        tank = new Tank(getTankCapacity(storage));
        mode = RedstoneMode.LOW;
        setting = EjectSetting.NONE;
    }

    @Override
    public ItemStack[] getInventory() {
        return getMasterInventory();
    }

    public boolean onTick(int i) {
        return machineTick % i == 0;
    }

    @Override
    public ItemStack[] getUpgrades() {
        return new ItemStack[] { inventory[0], inventory[1], inventory[2] };
    }

    public int[] getInputSlots() {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        TileMultiMachineTank mstr = getMaster() != null ? (TileMultiMachineTank) getMaster() : null;
        if (mstr == null) return;
        mstr.inventory[slot] = stack;

        if (stack != null && stack.stackSize > mstr.getInventoryStackLimit()) {
            stack.stackSize = mstr.getInventoryStackLimit();
        }

        int[] inputs = getInputSlots();
        if (inputs != null) {
            for (int i : inputs)
                if (slot == i) {
                    mstr.canWork = mstr.canWork();
                }
        }

        mstr.markDirty();
    }

    @Override
    public void updateUpgrades() {
        purity = MaricultureHandlers.upgrades.getData("purity", this);
        heat = MaricultureHandlers.upgrades.getData("temp", this);
        storage = MaricultureHandlers.upgrades.getData("storage", this);
        speed = MaricultureHandlers.upgrades.getData("speed", this);
        rf = MaricultureHandlers.upgrades.getData("rf", this);

        tank.setCapacity(getTankCapacity(storage));
        if (tank.getFluidAmount() > tank.getCapacity()) {
            tank.setFluidAmount(tank.getCapacity());
        }
    }

    public int getTankCapacity(int storage) {
        int tankRate = FluidContainerRegistry.BUCKET_VOLUME;
        return tankRate * 25 + storage * tankRate * 5;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateMaster() {
        super.updateMaster();
        if (helper == null) {
            helper = new BlockTransferHelper(this);
        }

        machineTick++;

        if (onTick(20)) {
            FluidHelper.process(tank, this, 3, 4);
            updateUpgrades();
        }

        if (onTick(20)) {
            canWork = canWork();
        }

        updateMasterMachine();
    }

    @Override
    public void updateSlaves() {
        if (helper == null) {
            helper = new BlockTransferHelper(this);
        }

        machineTick++;
        updateSlaveMachine();
    }

    public abstract boolean canWork();

    public abstract void updateMasterMachine();

    public abstract void updateSlaveMachine();

    public boolean rsAllowsWork() {
        if (getMaster() != null) {
            TileMultiMachineTank mstr = (TileMultiMachineTank) getMaster();
            RedstoneMode mode = mstr.mode;
            if (mode == RedstoneMode.DISABLED) return true;
            for (MultiPart block : mstr.slaves)
                if (mode.equals(RedstoneMode.LOW)) {
                    if (!RedstoneMode.canWork(worldObj.getTileEntity(block.xCoord, block.yCoord, block.zCoord), mode)) return false;
                } else if (mode.equals(RedstoneMode.HIGH)) if (RedstoneMode.canWork(worldObj.getTileEntity(block.xCoord, block.yCoord, block.zCoord), mode)) return true;

            return RedstoneMode.canWork(getMaster(), mode);
        }

        return false;
    }

    @Override
    public void setGUIData(int id, int value) {
        if (master == null) {
            master = new MultiPart(xCoord, yCoord, zCoord);
        }
        switch (id) {
            case 0:
                mode = RedstoneMode.values()[value];
                break;
            case 1:
                setting = EjectSetting.values()[value];
                break;
            case 2:
                processed = value;
                break;
            case 3:
                tank.setFluidID(value);
                ;
                break;
            case 4:
                tank.setFluidAmount(value);
                break;
            case 5:
                tank.setCapacity(value);
                ;
                break;
            case 6:
                if (master != null) {
                    master.xCoord = value;
                }
                break;
            case 7:
                if (master != null) {
                    master.yCoord = value;
                }
                break;
            case 8:
                if (master != null) {
                    master.zCoord = value;
                }
                break;
        }
    }
    
    @Override
    public ArrayList<Integer> getGUIData() {
        return new ArrayList(Arrays.asList(new Integer[] { mode.ordinal(), setting.ordinal(), processed, tank.getFluidID(), tank.getFluidAmount(), tank.getCapacity(),
                master != null ? master.xCoord : 0, master != null ? master.yCoord : 0, master != null ? master.zCoord : 0 }));
    }

    @Override
    public void handleButtonClick(int id) {
        if (id == Feature.REDSTONE) {
            setRSMode(RedstoneMode.toggle(getRSMode()));
        }
        if (id == Feature.EJECT) {
            setEjectSetting(EjectSetting.toggle(getEjectType(), getEjectSetting()));
        }
    }

    @Override
    public RedstoneMode getRSMode() {
        return mode != null ? mode : RedstoneMode.DISABLED;
    }

    @Override
    public void setRSMode(RedstoneMode mode) {
        this.mode = mode;
    }

    @Override
    public EjectSetting getEjectSetting() {
        return setting != null ? setting : EjectSetting.NONE;
    }

    @Override
    public void setEjectSetting(EjectSetting setting) {
        this.setting = setting;
    }

    @Override
    public int getProgressScaled(int scale) {
        return processed * scale / max;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        setting = EjectSetting.readFromNBT(nbt);
        mode = RedstoneMode.readFromNBT(nbt);
        purity = nbt.getInteger("Purity");
        heat = nbt.getInteger("Heat");
        storage = nbt.getInteger("Storage");
        speed = nbt.getInteger("Speed");
        rf = nbt.getInteger("RF");
        canWork = nbt.getBoolean("CanWork");
        processed = nbt.getInteger("Processed");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        EjectSetting.writeToNBT(nbt, setting);
        RedstoneMode.writeToNBT(nbt, mode);
        nbt.setInteger("Purity", purity);
        nbt.setInteger("Heat", heat);
        nbt.setInteger("Storage", storage);
        nbt.setInteger("Speed", speed);
        nbt.setInteger("RF", rf);
        nbt.setBoolean("CanWork", canWork);
        nbt.setInteger("Processed", processed);
    }
}
