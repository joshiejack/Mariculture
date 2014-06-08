package mariculture.core.tile.base;

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

public abstract class TileMachineOld extends TileStorage implements IUpgradable, IMachine, ISidedInventory, IRedstoneControlled, IEjectable, IProgressable {
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
	protected int offset = 3;
	//Machine vars
	protected int max;
	protected boolean canWork;
	protected int processed = 0;
	protected int[] output;
	
	public TileMachineOld() {
		inventory = new ItemStack[3];
		mode = RedstoneMode.LOW;
		setting = EjectSetting.NONE;
		output = new int[0];
	}
	
	public ItemStack[] getInventory() {
		return inventory;
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
		

	public void updateUpgrades() {
		purity = MaricultureHandlers.upgrades.getData("purity", this);
		heat = MaricultureHandlers.upgrades.getData("temp", this);
		storage = MaricultureHandlers.upgrades.getData("storage", this);
		speed = MaricultureHandlers.upgrades.getData("speed", this);
		rf = MaricultureHandlers.upgrades.getData("rf", this);
	}
	
	protected void updateCanWork() {
		canWork = canMachineWork();
	}

	@Override
	public void updateEntity() {		
		if(helper == null) {
			helper = new BlockTransferHelper(this);
		}
		
		machineTick++;
		autoeject();
		updateMachine();
	}
	
	public void autoeject() {
		if(output.length > 0 && onTick(Ticks.ITEM_EJECT_TICK)) {
			if(setting.canEject(EjectSetting.ITEM)) {
				for(int i: output) {
					if(inventory[i] != null) {
						ItemStack ejecting = inventory[i].copy();
						inventory[i] = null;
						if (ejecting != null) {
							helper.insertStack(ejecting, output);
						}
					}
				}
			}
		}
	}
	
	public abstract boolean canMachineWork();
	public abstract void updateMachine();

	@Override
	public void getGUINetworkData(int id, int value) {
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
		}
	}
	
	@Override
	public void sendGUINetworkData(ContainerMariculture container, ICrafting crafting) {
		crafting.sendProgressBarUpdate(container, 0, mode.ordinal());
		crafting.sendProgressBarUpdate(container, 1, setting.ordinal());
		crafting.sendProgressBarUpdate(container, 2, processed);
	}
	
	@Override
	public void handleButtonClick(int id) {
		if(id == Feature.REDSTONE) setRSMode(RedstoneMode.toggle(getRSMode()));
		if(id == Feature.EJECT) setEjectSetting(EjectSetting.toggle(getEjectType(), getEjectSetting()));
	}
	
	@Override
	public RedstoneMode getRSMode() {
		return mode != null? mode: RedstoneMode.DISABLED;
	}
	
	@Override
	public void setRSMode(RedstoneMode mode) {
		this.mode = mode;
	}
	
	@Override
	public EjectSetting getEjectSetting() {
		return setting != null? setting: EjectSetting.NONE;
	}

	@Override
	public void setEjectSetting(EjectSetting setting) {
		this.setting = setting;
	}
	
	@Override
	public int getProgressScaled(int scale) {
		return (processed * scale) / max;
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
