package mariculture.core.tile.base;

import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
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

    public TileMultiMachine() {
        inventory = new ItemStack[5];
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

    @Override
    public void updateUpgrades() {
        purity = MaricultureHandlers.upgrades.getData("purity", this);
        heat = MaricultureHandlers.upgrades.getData("temp", this);
        storage = MaricultureHandlers.upgrades.getData("storage", this);
        speed = MaricultureHandlers.upgrades.getData("speed", this);
        rf = MaricultureHandlers.upgrades.getData("rf", this);
    }

    @Override
    public boolean canUpdate() {
        return true;
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
        super.updateMaster();
        if (helper == null) {
            helper = new BlockTransferHelper(this);
        }

        machineTick++;

        if (onTick(20)) {
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

    public abstract void updateMasterMachine();

    public abstract void updateSlaveMachine();

    public abstract boolean canWork();

    @Override
    public void getGUINetworkData(int id, int value) {
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
    public void sendGUINetworkData(ContainerMariculture container, ICrafting crafting) {
        crafting.sendProgressBarUpdate(container, 0, mode.ordinal());
        crafting.sendProgressBarUpdate(container, 1, setting.ordinal());
        crafting.sendProgressBarUpdate(container, 2, processed);
        crafting.sendProgressBarUpdate(container, 3, master != null ? master.xCoord : 0);
        crafting.sendProgressBarUpdate(container, 4, master != null ? master.yCoord : 0);
        crafting.sendProgressBarUpdate(container, 5, master != null ? master.zCoord : 0);
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
