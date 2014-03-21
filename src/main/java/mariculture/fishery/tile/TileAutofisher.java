package mariculture.fishery.tile;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.tile.base.TileMachinePowered;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.Rand;
import net.minecraft.enchantment.Enchantment;
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
		if(side <= 1) return new int[] { rod };
		return new int[] { 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		if(slot == rod && Fishing.rodHandler.getRodQuality(stack) != null)
			return true;
		if(slot >= 5 && slot <= 10 && Fishing.bait.getBaitQuality(stack) > 0)
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
						processed = 0;
						//For looping catches with the speed enchantment
						int speed = 1 + EnchantHelper.getLevel(Enchantment.field_151369_A, inventory[rod]);
						//Bonus for Catching fish with the vanilla luck enchantment
						for(int i = 0; i < speed && canWork; i++) {
							int bonusQuality = baitQuality + (EnchantHelper.getLevel(Enchantment.field_151370_z, inventory[rod]) * 4);
							if (Rand.rand.nextInt(100) < bonusQuality && canWork())
								catchFish(bonusQuality);
							baitQuality = -1;
						}
					}
					
					if(processed <= 0)
						processed = 0;
				}
			} else {
				baitQuality = -1;
				processed = 0;
			}
			
			//Auto-Eject Item Stacks that are already in the system
			if(onTick(30)) {
				if(setting.canEject(EjectSetting.ITEM)) {
					for(int i: out) {
						if(inventory[i] != null) {
							ItemStack ejecting = inventory[i].copy();
							inventory[i] = null;
							if (ejecting != null) {
								helper.insertStack(ejecting, out);
							}
						}
					}
				}
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
		EnumRodQuality quality = Fishing.rodHandler.getRodQuality(inventory[rod]);
		for(int i: bait) {
			if(inventory[i] != null) {
				return Fishing.rodHandler.canUseBait(inventory[i], quality);
			}
		}
		
		return false;
	}

	private boolean hasBait() {
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
		if(inventory[rod] != null && Fishing.rodHandler.getRodQuality(inventory[rod]) != null) {
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
		EnumRodQuality quality = Fishing.rodHandler.getRodQuality(inventory[rod]);
		for(int i: bait) {
			if(inventory[i] != null) {
				if(Fishing.rodHandler.canUseBait(inventory[i], quality)) {
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
	private void catchFish(int baitQuality) {
		EnumRodQuality quality = Fishing.rodHandler.getRodQuality(inventory[rod]);
		ItemStack lootResult = Fishing.loot.getLoot(null, inventory[rod], baitQuality, Rand.rand, worldObj, xCoord, yCoord, zCoord);
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
