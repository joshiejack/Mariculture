package mariculture.fishery.blocks;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ItemBaseRod;
import mariculture.api.fishery.RodQuality;
import mariculture.core.blocks.base.TileMachinePowered;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.Rand;
import mariculture.magic.Magic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
		if(slot >= 5 && slot <= 10 && Fishing.bait.getBaitQuality(stack) > 0)
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
				
				if(baitQuality == -1 && canWork()) {
					if(inventory[rod].itemID == Item.fishingRod.itemID) {
						baitQuality = 33;
					} else baitQuality = getBaitQualityAndDelete();
				} else {
					processed+=speed;
					if(processed >= max) {
						processed = 0;
						int bonusQuality = baitQuality + (EnchantHelper.getLevel(Magic.luck, inventory[rod]) * 3);
						if (Rand.rand.nextInt(100) < bonusQuality && canWork())
							catchFish();
						baitQuality = -1;
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
		return  (hasRod() && isFishable() && hasPower() && RedstoneMode.canWork(this, mode) && hasRoom() && (baitQuality != -1 || (hasBait() && canUseRod())));
	}
	
	public boolean hasRoom() {
		if(setting.canEject(EjectSetting.ITEM))
			return true;
		for(Integer i: out) {
			if(inventory[i] == null);
				return true;
		}
		
		return false;
	}
	
	private boolean canUseRod() {
		if(inventory[rod].itemID == Item.fishingRod.itemID) return true;
		RodQuality quality = ((ItemBaseRod) inventory[rod].getItem()).getQuality();
		for(int i: bait) {
			if(inventory[i] != null) {
				return Fishing.quality.canUseBait(inventory[i], quality);
			}
		}
		
		return false;
	}

	private boolean hasBait() {
		if(inventory[rod] != null && inventory[rod].itemID == Item.fishingRod.itemID) return true;
		for(int i: bait) {
			if(inventory[i] != null) {
				return Fishing.bait.getBaitQuality(inventory[i]) > 0;
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
			if(inventory[rod].itemID == Item.fishingRod.itemID) return true;
			else if(inventory[rod].getItem() instanceof ItemBaseRod) {
				return ((ItemBaseRod)inventory[rod].getItem()).canFish(worldObj, xCoord, yCoord, zCoord, null, inventory[rod]);
			}
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
		RodQuality quality = ((ItemBaseRod) inventory[rod].getItem()).getQuality();
		for(int i: bait) {
			if(inventory[i] != null) {
				if(Fishing.quality.canUseBait(inventory[i], quality)) {
					int qual = Fishing.bait.getBaitQuality(inventory[i]);
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
		ItemStack lootResult = null;
		if(inventory[rod].itemID == Item.fishingRod.itemID) {
			lootResult = new ItemStack(Item.fishRaw);
			inventory[rod].attemptDamageItem(1, Rand.rand);
		} else {
			RodQuality quality = ((ItemBaseRod) inventory[rod].getItem()).getQuality();
			lootResult = Fishing.loot.getLoot(Rand.rand, quality, worldObj, xCoord, yCoord - 1, zCoord);
			inventory[rod] = ((ItemBaseRod)inventory[rod].getItem()).damage(worldObj, null, inventory[rod], 0);
		}
		

		if (lootResult != null) {
			helper.insertStack(lootResult, out);
		}
		
		if(inventory[rod] == null || inventory[rod].getItemDamage() > inventory[rod].getMaxDamage()) {
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
