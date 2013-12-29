package mariculture.core.blocks.base;

import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import net.minecraft.item.ItemStack;

public abstract class TileExample extends TileMachine {
	//Data Vars
	
	//Initializers
	public TileExample() {
		
	}

	//Slot Vars and ISided
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return null;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return false;
	}
	
	//Machine Updates/Upgrades/Master/Slave etc.
	@Override
	public void updateMachine() {
		
	}
	
	//Boolean Checks, TOP = CanWork
	@Override
	public boolean canWork() {
		return false;
	}
	
	//Getters
	
	//Void, Do stuff Called from updateMachine

	//GUI Stuff
	//Get Then Send GUI Data
	
	//Notifications
	public boolean isNotificationVisible(NotificationType type) {
		switch(type) {
			case NO_ROD:
				return !canWork();
			default:
				return false;
		}
	}
	
	//Process Name
	@Override
	public String getProcess() {
		return null;
	}
	
	//Eject Type
	@Override
	public EjectSetting getEjectType() {
		return null;
	}

	//Read/Write NBT
}
