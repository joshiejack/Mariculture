package mariculture.core.tile.base;

import java.util.ArrayList;

import scala.actors.threadpool.Arrays;
import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.config.Machines.Ticks;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.gui.feature.Feature;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockTransferHelper;
import mariculture.core.util.IEjectable;
import mariculture.core.util.IMachine;
import mariculture.core.util.IProgressable;
import mariculture.core.util.IRedstoneControlled;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileMultiMachine extends TileMultiStorage implements IUpgradable, IMachine, ISidedInventory, IRedstoneControlled, IEjectable, IProgressable {
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

    public TileMultiMachine() {
        inventory = new ItemStack[5];
        mode = RedstoneMode.LOW;
        setting = EjectSetting.NONE;
        output = new int[0];
    }

    public boolean onTick(int i) {
        return worldObj.getWorldTime() % i == 0;
    }

    @Override
    public ItemStack[] getInventory() {
        return getMasterInventory();
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
    }

    @Override
    public ItemStack[] getUpgrades() {
        return new ItemStack[] { inventory[0], inventory[1], inventory[2] };
    }

    public boolean canWork() {
        return false;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    //Call to update canWork
    public final void updateCanWork() {
        canWork = canWork();
    }

    public boolean rsAllowsWork() {
        if (getMaster() != null) {
            TileMultiMachine mstr = (TileMultiMachine) getMaster();
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
    public void updateMaster() {
        if (!worldObj.isRemote) {
            if (helper == null) {
                helper = new BlockTransferHelper(this);
                updateCanWork();
                updateUpgrades();
            }

            updateTheMaster();
            updateMasterMachine();
        }
    }

    public abstract void process();

    public void updateMasterMachine() {
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
    public void updateTheMaster() {
        autoeject();
    }

    public void autoeject() {
        if (output.length > 0 && onTick(Ticks.ITEM_EJECT_TICK)) if (setting.canEject(EjectSetting.ITEM)) {
            for (int i : output)
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
                if (master != null) {
                    master.xCoord = value;
                }
                break;
            case 4:
                if (master != null) {
                    master.yCoord = value;
                }
                break;
            case 5:
                if (master != null) {
                    master.zCoord = value;
                }
                break;
        }
    }

    @Override
    public ArrayList<Integer> getGUIData() {
        return new ArrayList(Arrays.asList(new Integer[] { mode.ordinal(), setting.ordinal(), processed, master != null ? master.xCoord : 0, master != null ? master.yCoord : 0, master != null ? master.zCoord : 0 }));
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
