package mariculture.fishery.blocks;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ItemBaseRod;
import mariculture.core.blocks.base.TileMachinePowered;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.network.Packet106SwapJewelry;
import mariculture.core.network.Packet114RedstoneControlled;
import mariculture.core.network.Packets;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.IProgressable;
import mariculture.core.util.IRedstoneControlled;
import mariculture.core.util.Rand;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileAutofisher extends TileMachinePowered implements IProgressable, IHasNotification, IRedstoneControlled {
	private static final int MAX = 4000;
	
	private RedstoneMode mode;
	private boolean canFish;
	private int baitQuality = -1;
	private int processed = 0;
	
	//Slot Var Helpers
	private static final int rod = 4;
	private static final int[] bait = new int[] { 5, 6, 7, 8, 9, 10 };
	private static final int[] out = new int [] { 11, 12, 13, 14, 15, 16, 17, 18, 19 };
	
	public TileAutofisher() {
		inventory = new ItemStack[20];
		energyStorage = new EnergyStorage(16000);
		mode = RedstoneMode.DISABLED;
	}
	
	//Sided Inventory
	@Override
	public int[] getAccessibleSlotsFromSide(int slot) {
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

	//Machine Ticks
	@Override
	public void updateUpgrades() {
		super.updateUpgrades();
		energyStorage.setCapacity((int) (20000 + (storage * 4000)));
	}
	
	@Override
	public void updateMachine() {
		if(onTick(30)) {
			canFish = hasRod() && isFishable() && hasPower() && RedstoneMode.canWork(this, mode) && hasBait() && canUseRod();
		}
		
		if(canFish) {
			energyStorage.extractEnergy(30 - purity, false);
			
			if(baitQuality == -1) {
				baitQuality = getBaitQualityAndDelete();
			} else {
				processed+=((heat/2) + 1);
				if(processed >= MAX) {
					baitQuality = -1;
					processed = 0;
					if (Rand.nextInt(getChance(baitQuality) + 1))
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

	private void catchFish() {
		EnumRodQuality quality = ((ItemBaseRod) inventory[rod].getItem()).getQuality();
		ItemStack lootResult = Fishing.loot.getLoot(Rand.rand, quality, worldObj, xCoord, yCoord, zCoord);

		if (lootResult != null) {
			if (!InventoryHelper.addToInventory(0, worldObj, xCoord, yCoord, zCoord, lootResult, null)) {
				int slot = getSuitableSlot(lootResult);
				if (slot != -1)  {
					if (getStackInSlot(slot) != null) {
						lootResult.stackSize = lootResult.stackSize + getStackInSlot(slot).stackSize;
					}
					setInventorySlotContents(slot, lootResult);
				} else {
					EntityItem dropped = new EntityItem(worldObj, xCoord, yCoord + 1, zCoord, lootResult);
					this.worldObj.spawnEntityInWorld(dropped);
				}
			}
		}
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
					if(inventory[rod].getItem() instanceof IEnergyContainerItem) {
						((IEnergyContainerItem)inventory[rod].getItem()).extractEnergy(inventory[rod], 100 - (purity * 4), false);
					} else {
						inventory[rod].attemptDamageItem(1, Rand.rand);
					}
					
					int qual = Fishing.bait.getEffectiveness(inventory[i]);
					decrStackSize(i, 1);
					return qual;
				}
			}
		}
		
		return -1;
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
		return energyStorage.extractEnergy(30, true) >= 30;
	}

	public boolean isFishable() {
		return BlockHelper.isFishable(worldObj, xCoord, yCoord - 1, zCoord);
	}
	
	private boolean hasRod() {
		if(inventory[rod] != null && inventory[rod].getItem() instanceof ItemBaseRod) {
			if(inventory[rod].getItem() instanceof IEnergyContainerItem) {
				return ((IEnergyContainerItem)inventory[rod].getItem()).extractEnergy(inventory[rod], 100 - (purity * 4), true) >= 100 - (purity * 4);
			}
			
			return true;
		}
		
		return false;
	}

//GUI Data
	//GUI Network Data
	@Override
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		super.sendGUINetworkData(container, player);
		Packets.updateGUI(player, container, 2, processed);
		Packets.updateGUI(player, container, 3, mode.ordinal());
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		super.getGUINetworkData(id, value);
		switch (id) {
		case 2:
			processed = value;
			break;
		case 3:
			mode = RedstoneMode.values()[value];
		}
	}
	
//Gui Feature Helpers
	@Override
	public void toggleMode(EntityClientPlayerMP player) {
		mode = RedstoneMode.toggle(mode);
		player.sendQueue.addToSendQueue(new Packet114RedstoneControlled(xCoord, yCoord, zCoord, mode).build());
	}
	
	@Override
	public RedstoneMode getMode() {
		return mode != null? mode: RedstoneMode.DISABLED;
	}
	
	@Override
	public void setMode(RedstoneMode mode) {
		this.mode = mode;
	}

	@Override
	public boolean isNotificationVisible(NotificationType type) {
		switch(type) {
			case NO_ROD:
				return !hasRod();
			case NO_BAIT:
				return !(hasBait() && hasRod());
			case NOT_FISHABLE:
				return !isFishable();
			case NO_RF:
				return !hasPower();
			default:
				return false;
		}
	}

	@Override
	public int getProgressScaled(int scale) {
		return (processed * scale) / MAX;
	}

	@Override
	public String getProgessText() {
		return getProgressScaled(100) + "% " + StatCollector.translateToLocal("mariculture.string.fished");
	}
	
	//Read and Write data
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		mode = RedstoneMode.readFromNBT(nbt);
		canFish = nbt.getBoolean("CanFish");
		baitQuality = nbt.getInteger("BaitQuality");
		processed = nbt.getInteger("Processed");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		RedstoneMode.writeToNBT(nbt, mode);
		nbt.setBoolean("CanFish", canFish);
		nbt.setInteger("BaitQuality", baitQuality);
		nbt.setInteger("Processed", processed);
	}
}
