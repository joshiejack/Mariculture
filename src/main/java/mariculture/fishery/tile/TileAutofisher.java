package mariculture.fishery.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import mariculture.api.fishery.Fishing;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.tile.base.TileMachinePowered;

public class TileAutofisher extends TileMachinePowered {
	//Slot Helper variables
	public static final int rod = 4;
	public static final int[] rod_slot = new int[] { 4 };
	public static final int[] bait = new int[] { 5, 6, 7, 8, 9, 10 };
	public static final int[] all = new int[] { 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
	
	private int baitChance;
	
	public TileAutofisher() {
		max = MachineSpeeds.getAutofisherSpeed();
		inventory = new ItemStack[20];
		setting = EjectSetting.ITEM;
		output = new int [] { 11, 12, 13, 14, 15, 16, 17, 18, 19 };
	}
	
	@Override
	public int getRFCapacity() {
		return 20000;
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side <= 1? rod_slot: all;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		if(slot == rod) return Fishing.fishing.getRodType(stack) != null;
		else if (slot >= 5 && slot <= 10) return Fishing.fishing.getBaitQuality(stack) > 0;
		else return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 4 || slot > 10;
	}

	@Override
	public EjectSetting getEjectType() {
		return EjectSetting.ITEM;
	}

	@Override
	public void updateMachine() {
		if(canWork) {
			//Since the machine can work, let's take off some lovely energy :)
			energyStorage.extractEnergy(20 + (speed * 20), false);
			processed+=speed;
			
			if(processed >= max) {
				processed = 0;
			}
		} else {
			processed = 0;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		baitChance = nbt.getInteger("BaitQuality");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("BaitQuality", baitChance);
	}
}
