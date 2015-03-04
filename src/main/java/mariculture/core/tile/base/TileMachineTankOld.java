package mariculture.core.tile.base;

import java.util.ArrayList;
import java.util.Arrays;

import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.config.Machines.Ticks;
import mariculture.core.gui.feature.Feature;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockTransferHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.util.IEjectable;
import mariculture.core.util.IMachine;
import mariculture.core.util.IProgressable;
import mariculture.core.util.IRedstoneControlled;
import mariculture.core.util.Tank;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public abstract class TileMachineTankOld extends TileStorageTank implements IUpgradable, IMachine, ISidedInventory, IRedstoneControlled, IEjectable, IProgressable {
    protected BlockTransferHelper helper;
    //General Tick
    private int machineTick = 0;
    //Upgrade Stats
    protected int purity = 0;
    protected int heat = 0;
    protected int storage = 0;
    protected int speed = 0;
    protected int rf = 0;
    //Tile Configuration
    protected EjectSetting setting;
    protected RedstoneMode mode;
    //GUI INT offset
    protected int offset = 6;
    //Machine vars
    protected int max;
    protected boolean canWork;
    protected int processed = 0;
    protected int[] output;
    protected int[] rate;

    public TileMachineTankOld() {
        inventory = new ItemStack[5];
        tank = new Tank(getTankCapacity(0));
        mode = RedstoneMode.LOW;
        setting = EjectSetting.NONE;
        output = new int[0];
        rate = new int[0];
    }

    @Override
    public ItemStack[] getInventory() {
        return inventory;
    }

    private int[] getOutputSlots() {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        super.setInventorySlotContents(slot, stack);
        updateUpgrades();
        updateCanWork();
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack stack = super.decrStackSize(slot, amount);
        updateCanWork();
        return stack;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int fill = super.fill(from, resource, doFill);
        if (doFill) {
            updateCanWork();
        }

        return fill;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        FluidStack stack = super.drain(from, maxDrain, doDrain);
        if (doDrain) {
            updateCanWork();
        }

        return stack;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    public boolean onTick(int i) {
        return machineTick % i == 0;
    }

    @Override
    public ItemStack[] getUpgrades() {
        return new ItemStack[] { inventory[0], inventory[1], inventory[2] };
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

    protected void updateCanWork() {
        canWork = canMachineWork();
    }

    public int getTankCapacity(int storage) {
        int tankRate = FluidContainerRegistry.BUCKET_VOLUME;
        return tankRate * 20 + storage * tankRate;
    }

    @Override
    public void updateEntity() {
        if (helper == null) {
            helper = new BlockTransferHelper(this);
        }

        machineTick++;
        autoeject();
        emptyContainers();
        updateMachine();
    }

    public abstract boolean canMachineWork();

    public abstract void updateMachine();

    public void autoeject() {
        if (output.length > 0 && onTick(Ticks.ITEM_EJECT_TICK)) if (setting.canEject(EjectSetting.ITEM)) {
            for (int i : output)
                if (inventory[i] != null) {
                    ItemStack ejecting = inventory[i].copy();
                    inventory[i] = null;
                    if (ejecting != null) {
                        helper.insertStack(ejecting, output);
                    }
                }
        }

        if (rate.length > 0 && onTick(Ticks.FLUID_EJECT_TICK)) {
            helper.ejectFluid(rate);
        }
    }

    public void emptyContainers() {
        FluidHelper.process(tank, this, 3, 4);
    }

    @Override
    public void setGUIData(int id, int value) {
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
                break;
            case 4:
                tank.setFluidAmount(value);
                break;
            case 5:
                tank.setCapacity(value);
                break;
        }
    }

    protected int lastMode;
    protected int lastSetting;
    protected int lastProcessed;
    protected int lastFluidID;
    protected int lastFluidAmount;
    protected int lastFluidCapacity;

    @Override
    public boolean hasChanged() {
        return lastMode != mode.ordinal() || lastSetting != setting.ordinal() || lastProcessed != processed || lastFluidID != tank.getFluidID() || lastFluidAmount != tank.getFluidAmount() || lastFluidCapacity != tank.getCapacity();
    }

    @Override
    public ArrayList<Integer> getGUIData() {
        lastMode = mode.ordinal();
        lastSetting = setting.ordinal();
        lastProcessed = processed;
        lastFluidID = tank.getFluidID();
        lastFluidAmount = tank.getFluidAmount();
        lastFluidCapacity = tank.getCapacity();
        return new ArrayList(Arrays.asList(new Integer[] { lastMode, lastSetting, lastProcessed, lastFluidID, lastFluidAmount, lastFluidCapacity }));
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
