package mariculture.fishery.tile;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RodType;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.tile.base.TileMachinePoweredOld;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.Rand;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileAutofisherOld extends TileMachinePoweredOld implements IHasNotification {
	private int baitQuality = -1;
	public TileAutofisherOld() {
		max = MachineSpeeds.getAutofisherSpeed();
		inventory = new ItemStack[20];
		setting = EjectSetting.ITEM;
		output = new int [] { 11, 12, 13, 14, 15, 16, 17, 18, 19 };
	}
	
	@Override
	public int getRFCapacity() {
		return 20000;
	}
	
	//Slot Var Helpers
	public static final int rod = 4;
	public static final int[] bait = new int[] { 5, 6, 7, 8, 9, 10 };
	public static final int[] all = new int[] { 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
	
	//Sided Inventory
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(side <= 1) return new int[] { rod };
		return all;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		if(slot == rod && Fishing.fishing.getRodType(stack) != null)
			return true;
		if(slot >= 5 && slot <= 10 && Fishing.fishing.getBaitQuality(stack) > 0)
			return true;
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot >= 11 || slot == rod;
	}

	@Override
	public void updateMachine() {
		if(!worldObj.isRemote) {
			if(canWork) {
				energyStorage.extractEnergy(getRFUsage(), false);
				if(baitQuality == -1 && canMachineWork()) {
					baitQuality = getBaitQualityAndDelete();
				} else {
					
					processed+=speed;
					if(processed >= max) {
						processed = 0;
						int speed = 1 + EnchantHelper.getLevel(Enchantment.field_151369_A, inventory[rod]);
						for(int i = 0; i < speed && canWork; i++) {
							int bonusQuality = baitQuality + (EnchantHelper.getLevel(Enchantment.field_151370_z, inventory[rod]) * 4);
							if (Rand.rand.nextInt(100) < bonusQuality && canMachineWork())
								catchFish();
							baitQuality = -1;
						}
					}
					
					if(processed <= 0) processed = 0;
				}
			} else {
				baitQuality = -1;
				processed = 0;
			}
		}
	}
	
	@Override
	public boolean canMachineWork() {
		return  (hasRod() && isFishable() && hasPower() && RedstoneMode.canWork(this, mode) && hasRoom() && (baitQuality != -1 || (hasBait() && canUseRod())));
	}
	
	public boolean hasRoom() {
		if(setting.canEject(EjectSetting.ITEM))
			return true;
		for(Integer i: output) {
			if(inventory[i] == null);
				return true;
		}
		
		return false;
	}
	
	private boolean canUseRod() {
		for(int i: bait) {
			if(inventory[i] != null) {
				return Fishing.fishing.canUseBait(inventory[rod], inventory[i]);
			}
		}
		
		return false;
	}

	private boolean hasBait() {
		for(int i: bait) {
			if(inventory[i] != null) {
				return Fishing.fishing.getBaitQuality(inventory[i]) > 0;
			}
		}
		
		return false;
	}
	
	private boolean hasPower() {
		return energyStorage.extractEnergy(getRFUsage(), true) >= getRFUsage();
	}

	public boolean isFishable() {
		return BlockHelper.isFishable(worldObj, xCoord, yCoord - 1, zCoord);
	}
	
	private boolean hasRod() {
		if(inventory[rod] != null) {
			RodType type = Fishing.fishing.getRodType(inventory[rod]);
			if(type != null) {
				return type.canFish(worldObj, xCoord, yCoord - 1, zCoord, null, inventory[rod]);
			}
		}

		return false;
	}
	
	private int getSuitableSlot(ItemStack item) {
		for(int i: output) {
			if(inventory[i] == null)
				return i;
			if(ItemStack.areItemStacksEqual(inventory[i], item))
				return i;
		}
		
		return -1;
	}

	private int getBaitQualityAndDelete() {
		for(int i: bait) {
			if(inventory[i] != null) {
				if(Fishing.fishing.canUseBait(inventory[rod], inventory[i])) {
					int qual = Fishing.fishing.getBaitQuality(inventory[i]);
					decrStackSize(i, 1);
					return qual;
				}
			}
		}
		
		return -1;
	}
	
	public int getRFUsage() {
		return 20 + (speed * 20);
	}
	
	/* Grabs the rod type, and then grabs a fishing result and then does what it needs to with ejecting/inserting **/
	private void catchFish() {
		RodType type = Fishing.fishing.getRodType(inventory[rod]);
		setInventorySlotContents(rod, type.damage(worldObj, null, inventory[rod], 0, Rand.rand));
		ItemStack lootResult = Fishing.fishing.getCatch(worldObj, xCoord, yCoord - 1, zCoord, null, inventory[rod]);
		if (lootResult != null) {
			helper.insertStack(lootResult, output);
		}
	}

	@Override
	public boolean isNotificationVisible(NotificationType type) {
		switch(type) {
			case NO_ROD: 		return !hasRod();
			case NO_BAIT: 		return !(hasBait() && hasRod() && canUseRod());
			case NOT_FISHABLE: 	return !isFishable();
			case NO_RF: 		return !hasPower();
			default: 			return false;
		}
	}

	@Override
	public EjectSetting getEjectType() {
		return EjectSetting.ITEM;
	}
	
	//Read and Write data
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		baitQuality = nbt.getInteger("BaitQuality");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("BaitQuality", baitQuality);
	}
}
