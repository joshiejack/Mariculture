package mariculture.core.blocks.base;

import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockTransferHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.Extra;
import mariculture.core.network.Packets;
import mariculture.core.util.IEjectable;
import mariculture.core.util.IMachine;
import mariculture.core.util.IProgressable;
import mariculture.core.util.IRedstoneControlled;
import mariculture.factory.blocks.Tank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidContainerRegistry;

public abstract class TileMachineTank extends TileStorageTank implements IUpgradable, IMachine, ISidedInventory, IRedstoneControlled, IEjectable, IProgressable {
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
	
	public TileMachineTank() {
		inventory = new ItemStack[5];
		tank = new Tank(getTankCapacity(0));
		mode = RedstoneMode.LOW;
		setting = EjectSetting.NONE;
	}
	
	public ItemStack[] getInventory() {
		return inventory;
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
		return new ItemStack[] {
				inventory[0], inventory[1], inventory[2]
		};
	}
	
	@Override
	public void updateUpgrades() {
		purity = MaricultureHandlers.upgrades.getData("purity", this);
		heat = MaricultureHandlers.upgrades.getData("temp", this);
		storage = MaricultureHandlers.upgrades.getData("storage", this);
		speed = MaricultureHandlers.upgrades.getData("speed", this);
		rf = MaricultureHandlers.upgrades.getData("rf", this);
		
		tank.setCapacity(getTankCapacity(storage));
		if(tank.getFluidAmount() > tank.getCapacity())
			tank.setFluidAmount(tank.getCapacity());
	}
	
	public int getTankCapacity(int storage) {
		int tankRate = FluidContainerRegistry.BUCKET_VOLUME;
		return (tankRate * 20) + (storage * tankRate);
	}

	public void updateEntity() {
		super.updateEntity();
		
		if(helper == null)
			helper = new BlockTransferHelper(this);
		
		machineTick++;
		if(onTick(20)) {
			FluidHelper.process(this, 3, 4);
			updateUpgrades();
		}
		
		if(onTick(Extra.CAN_WORK_TICK)) {
			canWork = canWork();
		}
		
		updateMachine();
	}
	
	public abstract boolean canWork();
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
		case 3:
			tank.setFluidID(value);;
			break;
		case 4:
			tank.setFluidAmount(value);
			break;
		case 5:
			tank.setCapacity(value);;
			break;
		}
	}
	
	@Override
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		Packets.updateGUI(player, container, 0, mode.ordinal());
		Packets.updateGUI(player, container, 1, setting.ordinal());
		Packets.updateGUI(player, container, 2, processed);
		Packets.updateGUI(player, container, 3, tank.getFluidID());
		Packets.updateGUI(player, container, 4, tank.getFluidAmount());
		Packets.updateGUI(player, container, 5, tank.getCapacity());
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
