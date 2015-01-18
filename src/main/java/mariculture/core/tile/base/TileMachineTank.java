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

public abstract class TileMachineTank extends TileStorageTank implements IUpgradable, IMachine, ISidedInventory, IRedstoneControlled, IEjectable, IProgressable {
    //Transfer Helper
    protected BlockTransferHelper helper;
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
    //The rate at which tis tank tries to drain liquid
    protected int[] rate;

    public TileMachineTank() {
        inventory = new ItemStack[5];
        mode = RedstoneMode.LOW;
        setting = EjectSetting.NONE;
        output = new int[0];
        rate = new int[0];
        tank = new Tank(getTankCapacity(0));
    }

    //Returns true if a valid tick to operate
    public boolean onTick(int i) {
        return worldObj.getWorldTime() % i == 0;
    }

    @Override
    public ItemStack[] getInventory() {
        return inventory;
    }

    @Override
    public void onInventoryChange(int slot) {
        updateCanWork();
        if (slot < 3) {
            updateUpgrades();
        }
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
        return tankRate * 20 + storage * tankRate;
    }

    @Override
    public ItemStack[] getUpgrades() {
        return new ItemStack[] { inventory[0], inventory[1], inventory[2] };
    }

    //Whether this machine canWork or not, gets called everytime there is a change to an inventory slot
    public boolean canWork() {
        return false;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (helper == null) {
                helper = new BlockTransferHelper(this);
                updateCanWork();
                updateUpgrades();
            }

            update();
            updateMachine();
        }
    }

    public abstract void process();

    //Called whenever the machine is active
    public void updateMachine() {
        if (canWork) {
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

    //Called at all times
    public void update() {
        FluidHelper.process(tank, this, 3, 4);
        autoeject();
    }

    //Called by the update method, to autoeject any items that are install inside the machine
    public void autoeject() {
        if (output.length > 0 && onTick(Ticks.ITEM_EJECT_TICK)) {
            if (setting.canEject(EjectSetting.ITEM)) {
                for (int i : output) {
                    if (inventory[i] != null) {
                        ItemStack ejecting = inventory[i].copy();
                        inventory[i] = null;
                        if (ejecting != null) {
                            helper.insertStack(ejecting, output);
                            updateCanWork();
                        }
                    }
                }
            }
        }
    }

    //Updates the canWork system, called whenever something changes
    public final void updateCanWork() {
        canWork = canWork();
    }

    //Whether there is room to eject from this machine or not
    public boolean hasRoom(ItemStack stack) {
        if (setting.canEject(EjectSetting.ITEM)) return true;
        for (Integer i : output) {
            if (inventory[i] == null) {
                return true;
            }
        }

        return false;
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

    @Override
    public ArrayList<Integer> getGUIData() {
        return new ArrayList(Arrays.asList(new Integer[] { mode.ordinal(), setting.ordinal(), processed, tank.getFluidID(), tank.getFluidAmount(), tank.getCapacity() }));
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
        updateCanWork();
    }

    @Override
    public EjectSetting getEjectSetting() {
        return setting != null ? setting : EjectSetting.NONE;
    }

    @Override
    public void setEjectSetting(EjectSetting setting) {
        this.setting = setting;
        updateCanWork();
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
