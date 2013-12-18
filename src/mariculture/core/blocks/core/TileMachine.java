package mariculture.core.blocks.core;

import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.util.IMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class TileMachine extends TileStorage implements IUpgradable, IMachine {
	private int machineTick = 0;
	protected int purity = 0;
	protected int heat = 0;
	protected int storage = 0;
	
	public boolean onTick(int i) {
		return machineTick % i == 0;
	}
	
	@Override
	public abstract ItemStack[] getUpgrades();
	
	@Override
	public void updateUpgrades() {
		purity = MaricultureHandlers.upgrades.getData("purity", this);
		heat = MaricultureHandlers.upgrades.getData("temp", this);
		storage = MaricultureHandlers.upgrades.getData("storage", this);
	}

	public void updateEntity() {
		super.updateEntity();
		
		machineTick++;
		if(machineTick %20 == 0) {
			updateUpgrades();
		}
		
		updateMachine();
	}
	
	public abstract void updateMachine();
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		purity = tagCompound.getInteger("Purity");
		heat = tagCompound.getInteger("Heat");
		storage = tagCompound.getInteger("Storage");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("Purity", purity);
		tagCompound.setInteger("Heat", heat);
		tagCompound.setInteger("Storage", storage);
	}
}
