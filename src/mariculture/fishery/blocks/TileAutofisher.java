package mariculture.fishery.blocks;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ItemBaseRod;
import mariculture.core.blocks.base.TileMachinePowered;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.Rand;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cofh.api.energy.IEnergyContainerItem;

public class TileAutofisher extends TileMachinePowered implements IHasNotification {
	//Data, Vars	
	private int baitQuality = -1;
	
	public TileAutofisher() {
		max = MachineSpeeds.getAutofisherSpeed();
		inventory = new ItemStack[20];
		setting = EjectSetting.ITEM;
	}
	
	@Override
	public int getRFCapacity() {
		return 20000;
	}
	
	//Slot Var Helpers
	public static final int rod = 4;
	public static final int[] bait = new int[] { 5, 6, 7, 8, 9, 10 };
	public static final int[] out = new int [] { 11, 12, 13, 14, 15, 16, 17, 18, 19 };
	
	//Sided Inventory
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(side <= 1)
			return new int[] { rod };
		return new int[] { 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		if(slot == rod && stack.getItem() instanceof ItemBaseRod)
			return true;
		if(slot >= 5 && slot <= 10 && Fishing.bait.getEffectiveness(stack) > -1)
			return true;
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot >= 11;
	}

	@Override
	public void updateMachine() {
		if(!worldObj.isRemote) {
			if(canWork) {
				energyStorage.extractEnergy(getRFUsage(), false);
				
				if(baitQuality == -1 && canWork()) {
					baitQuality = getBaitQualityAndDelete();
				} else {
					processed+=speed;
					if(processed >= max) {
						baitQuality = -1;
						processed = 0;
						if (Rand.nextInt(getChance(baitQuality) + 1) && canWork())
							catchFish();
					}
					
					if(processed <= 0)
						processed = 0;
				}
			} else {
				baitQuality = -1;
				processed = 0;
			}
		}
	}
	
	//Can do stuff / has it
	public boolean canWork() {
		return  (hasRod() && isFishable() && hasPower() && RedstoneMode.canWork(this, mode) && (baitQuality != -1 || (hasBait() && canUseRod())));
	}
	
	private boolean canUseRod() {
		EnumRodQuality quality = ((ItemBaseRod) inventory[rod].getItem()).getQuality();
		
		for(int i: bait) {
			if(inventory[i] != null) {
				return Fishing.quality.canUseBait(inventory[i], quality);
			}
		}
		
		return false;
	}

	private boolean hasBait() {
		for(int i: bait) {
			if(inventory[i] != null) {
				return Fishing.bait.getEffectiveness(inventory[i]) > -1;
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
		if(inventory[rod] != null && inventory[rod].getItem() instanceof ItemBaseRod) {
			if(inventory[rod].getItem() instanceof IEnergyContainerItem) {
				return ((IEnergyContainerItem)inventory[rod].getItem()).extractEnergy(inventory[rod], 100, true) >= 100;
			}
			
			return true;
		}
		
		return false;
	}
	
	//Get Stuff
	private int getChance(int quality) {
		switch(quality) {
			case 5: return Extra.bait5;
			case 4: return Extra.bait4;
			case 3: return Extra.bait3;
			case 2: return Extra.bait2;
			case 1: return Extra.bait1;
			case 0: return Extra.bait0;
		}
		
		return 0;
	}
	
	private int getSuitableSlot(ItemStack item) {
		for(int i: out) {
			if(inventory[i] == null)
				return i;
			if(ItemStack.areItemStacksEqual(inventory[i], item))
				return i;
		}
		
		return -1;
	}

	private int getBaitQualityAndDelete() {
		EnumRodQuality quality = ((ItemBaseRod) inventory[rod].getItem()).getQuality();
		
		for(int i: bait) {
			if(inventory[i] != null) {
				if(Fishing.quality.canUseBait(inventory[i], quality)) {
					int qual = Fishing.bait.getEffectiveness(inventory[i]);
					decrStackSize(i, 1);
					return qual;
				}
			}
		}
		
		return -1;
	}
	
	public int getRFUsage() {
		return 12 + (speed * 8);
	}
	
	//Process
	private void catchFish() {
		EnumRodQuality quality = ((ItemBaseRod) inventory[rod].getItem()).getQuality();
		ItemStack lootResult = Fishing.loot.getLoot(Rand.rand, quality, worldObj, xCoord, yCoord, zCoord);

		if (lootResult != null) {
			helper.insertStack(lootResult, out);
		}
		
		if(inventory[rod].getItem() instanceof IEnergyContainerItem) {
			((IEnergyContainerItem)inventory[rod].getItem()).extractEnergy(inventory[rod], 100, false);
		} else {
			inventory[rod].attemptDamageItem(1, Rand.rand);
			if(inventory[rod].getItemDamage() > inventory[rod].getMaxDamage())
				inventory[rod] = null;
				canWork = canWork();
		}
	}

//Gui Feature Helpers
	@Override
	public boolean isNotificationVisible(NotificationType type) {
		switch(type) {
			case NO_ROD:
				return !hasRod();
			case NO_BAIT:
				return !(hasBait() && hasRod() && canUseRod());
			case NOT_FISHABLE:
				return !isFishable();
			case NO_RF:
				return !hasPower();
			default:
				return false;
		}
	}
	
	@Override
	public String getProcess() {
		return "fished";
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
