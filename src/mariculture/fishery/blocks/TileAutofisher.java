package mariculture.fishery.blocks;

import java.util.Random;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ItemBaseRod;
import mariculture.core.blocks.base.TileStorage;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.network.Packets;
import mariculture.core.util.IHasGUI;
import mariculture.core.util.IMachine;
import mariculture.core.util.IPowered;
import mariculture.core.util.IProgressable;
import mariculture.fishery.items.ItemBait;
import mariculture.fishery.items.ItemRod;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class TileAutofisher extends TileStorage implements IEnergyHandler, ISidedInventory, IMachine, IHasGUI, IPowered, IProgressable {

	protected EnergyStorage storage;
	private int catchingLength = MachineSpeeds.getAutofisherSpeed();
	private int baitGradeUsed = 0;
	private int catchingTime = 0;
	private int currentCatchingTime = 0;

	public TileAutofisher() {
		inventory = new ItemStack[16];
		storage = new EnergyStorage(5000);
	}

	public boolean isCatching() {
		return this.catchingTime > 0;
	}

	@Override
	public void updateEntity() {
		if(this.canAutoFish()) {
			if (storage.extractEnergy(20, false) < 20) {
				return;
			}
			
			boolean checkTime = this.catchingTime > 0;
			boolean shouldUpdate = false;
	
			if (this.catchingTime > 0) {
				--this.catchingTime;
			}
	
			if (!this.worldObj.isRemote) {
				if (this.catchingTime == 0) {
					this.catchingTime = this.catchingLength;
	
					this.setInventorySlotContents(0, new ItemStack(inventory[0].getItem(), 1,
							inventory[0].getItemDamage() + 1));
	
					if (inventory[0].getItemDamage() > inventory[0].getMaxDamage()) {
						decrStackSize(0, 1);
					}
	
					baitGradeUsed = getBaitGradeAndDelete();
	
					if (this.catchingTime > 0) {
						shouldUpdate = true;
					}
				}
	
				if (this.isCatching() && this.canAutoFish()) {
					++this.currentCatchingTime;
	
					if (Extra.DEBUG_ON && this.currentCatchingTime < catchingLength) {
						this.currentCatchingTime += catchingLength - 10;
					}
	
					if (this.currentCatchingTime >= catchingLength) {
						this.currentCatchingTime = 0;
	
						Random rand = new Random();
						if (rand.nextInt(getChance(baitGradeUsed) + 1) == 1 && inventory[0].getItem() instanceof ItemBaseRod) {
							EnumRodQuality quality = ((ItemBaseRod) inventory[0].getItem()).getQuality();
							ItemStack lootResult = Fishing.loot.getLoot(rand, quality, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
	
							if (lootResult != null) {
								if (!InventoryHelper.addToInventory(0, worldObj, xCoord, yCoord, zCoord, lootResult, null)) {
									int slot = getSuitableSlot(lootResult);
									if (slot != 16) // if Available Slot not 16,
													// then
									// create a fishy wishy
									{
										if (getStackInSlot(slot) != null) {
											lootResult.stackSize = lootResult.stackSize + getStackInSlot(slot).stackSize;
										}
	
										setInventorySlotContents(slot, lootResult);
									} else {
										// Otherwise Spawn the item in the world :o
										EntityItem dropped = new EntityItem(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord, lootResult);
										this.worldObj.spawnEntityInWorld(dropped);
									}
								}
							}
						}
	
						shouldUpdate = true;
					}
				} else {
					this.currentCatchingTime = 0;
				}
	
				if (checkTime != this.catchingTime > 0) {
					shouldUpdate = true;
				}
			}
	
			if (shouldUpdate) {
				this.onInventoryChanged();
			}
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

	private int getSuitableSlot(ItemStack item) {
		for (int i = 7; i < inventory.length; i++) {
			if (inventory[i] == null) {
				return i;
			}

			if ((inventory[i].getItemDamage() == item.getItemDamage() && inventory[i].itemID == item.itemID 
					&& (inventory[i].stackSize + item.stackSize) <= inventory[i].getMaxStackSize())) {
				return i;
			}
		}

		return 16;
	}

	public int getBaitGradeAndDelete() {
		int rank = -1;
		int slot = 0;

		for (int i = 1; i <= 6; i++) {
			if (inventory[i] != null) {
				if (Fishing.bait.getEffectiveness(inventory[i]) > -1) {
					rank = Fishing.bait.getEffectiveness(inventory[i]);
					slot = i;
				}
			}
		}

		--this.inventory[slot].stackSize;

		if (this.inventory[slot].stackSize == 0) {
			final Item item = this.inventory[slot].getItem().getContainerItem();
			this.inventory[slot] = item == null ? null : new ItemStack(item);
		}

		return rank;
	}

	private boolean canAutoFish() {
		if (inventory[0] != null && BlockHelper.isFishable(this.worldObj, this.xCoord, this.yCoord - 1, this.zCoord)) {
			if (inventory[0].getItem() instanceof ItemBaseRod) {
				EnumRodQuality quality = ((ItemBaseRod) inventory[0].getItem()).getQuality();
				for (int i = 1; i <= 6; i++) {
					if (inventory[i] != null) {
						if (Fishing.quality.canUseBait(inventory[i], quality)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		catchingTime = tagCompound.getInteger("CatchTime");
		currentCatchingTime = tagCompound.getInteger("CurrentCatchTime");
		baitGradeUsed = tagCompound.getInteger("BaitGradeUsed");
		storage.readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("CatchTime", this.catchingTime);
		tagCompound.setInteger("CurrentCatchTime", this.currentCatchingTime);
		tagCompound.setInteger("BaitGradeUsed", this.baitGradeUsed);
		storage.writeToNBT(tagCompound);
	}

	@Override
	public void getGUINetworkData(int i, int j) {
		switch (i) {
		case 0:
			this.catchingTime = j;
			break;
		case 1:
			this.currentCatchingTime = j;
			break;
		case 2:
			this.baitGradeUsed = j;
			break;
		case 3:
			this.storage.setEnergyStored(j);
			break;
		case 4: 
			this.storage.setCapacity(j);
			break;
		}
	}
	
	@Override
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		Packets.updateGUI(player, container, 0, this.catchingTime);
		Packets.updateGUI(player, container, 1, this.currentCatchingTime);
		Packets.updateGUI(player, container, 2, this.baitGradeUsed);
		Packets.updateGUI(player, container, 3, this.storage.getEnergyStored());
		Packets.updateGUI(player, container, 4, this.storage.getMaxEnergyStored());
	}

	private static final int[] slots_top = new int[] { 0, 1, 2, 3, 4, 5, 6 };
	private static final int[] slots_sides = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };

	@Override
	public int[] getAccessibleSlotsFromSide(final int side) {
		return (side <= 1 ? slots_top : slots_sides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot > 6 ? true : false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {

		if (stack.getItem() instanceof ItemRod && slot == 0) {
			return true;
		}

		if (stack.getItem() instanceof ItemBait && slot > 0 && slot < 7) {
			return true;
		}

		return false;
	}

	@Override
	public int getPowerScaled(int i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}
	
	@Override
	public String getPowerText() {
		return getEnergyStored(ForgeDirection.UNKNOWN) + " / " + getMaxEnergyStored(ForgeDirection.UNKNOWN) + " RF";
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return from != ForgeDirection.DOWN;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}

	@Override
	public int getProgressScaled(int scale) {
		return (currentCatchingTime * scale) / catchingLength;
	}

	@Override
	public String getProgessText() {
		return getProgressScaled(100) + "% Fished";
	}
}
