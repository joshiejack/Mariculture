package mariculture.fishery.blocks;

import java.util.Random;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ItemBaseRod;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.MachineSpeeds;
import mariculture.fishery.gui.ContainerAutofisher;
import mariculture.fishery.items.ItemBait;
import mariculture.fishery.items.ItemRod;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.TileEnergyHandler;

public class TileAutofisher extends TileEnergyHandler implements IInventory, IEnergyHandler, ISidedInventory, IPowerReceptor {

	private final ItemStack[] inventory = new ItemStack[16];
	private int catchingLength = MachineSpeeds.getAutofisherSpeed();
	private int baitGradeUsed = 0;

	private PowerHandler powerHandler;

	private int catchingTime = 0;
	private int currentCatchingTime = 0;

	public TileAutofisher() {
		storage = new EnergyStorage(5000);
		powerHandler = new PowerHandler(this, Type.MACHINE);
		powerHandler.configure(2, 100, 50, 500);
		powerHandler.configurePowerPerdition(0, 0);
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return inventory[slotIndex];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int amount) {
		ItemStack stack = getStackInSlot(slotIndex);

		if (stack != null) {
			if (stack.stackSize <= amount) {
				setInventorySlotContents(slotIndex, null);
			}

			else {
				stack = stack.splitStack(amount);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slotIndex, null);
				}
			}
		}

		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		final ItemStack stack = getStackInSlot(slotIndex);

		if (stack != null) {
			setInventorySlotContents(slotIndex, null);
		}

		return stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public boolean isCatching() {
		return this.catchingTime > 0;
	}
	
	public void addEnergy() {
		if(!worldObj.isRemote) {
			if(powerHandler.getEnergyStored() > 0) {
				storage.receiveEnergy((int) (powerHandler.getEnergyStored() * 10), false);
				powerHandler.setEnergy(0F);
			}
		}
	}

	@Override
	public void updateEntity() {
		this.addEnergy();
		
		if(this.canAutoFish()) {
			if (storage.extractEnergy(20, true) < 20) {
				return;
			} else {
				storage.extractEnergy(20, false);
			}
			
			final boolean checkTime = this.catchingTime > 0;
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
							ItemStack lootResult = Fishing.loot.getLoot(rand, quality, this.worldObj, this.xCoord,
									this.yCoord, this.zCoord);
	
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
										EntityItem dropped = new EntityItem(this.worldObj, this.xCoord, this.yCoord + 1,
												this.zCoord, lootResult);
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

	private int getSuitableSlot(final ItemStack item) {
		for (int i = 7; i < inventory.length; i++) {
			if (inventory[i] == null) {
				return i;
			}

			if ((inventory[i].getItemDamage() == item.getItemDamage() && inventory[i].itemID == item.itemID && (inventory[i].stackSize + item.stackSize) <= inventory[i]
					.getMaxStackSize())) {
				return i;
			}
		}

		return 16;
	}

	public int getCatchTimeRemainingScaled(final int scale) {
		return (currentCatchingTime * scale) / catchingLength;
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

		final NBTTagList tagList = tagCompound.getTagList("Inventory");

		for (int i = 0; i < tagList.tagCount(); i++) {
			final NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

			final byte slot = tag.getByte("Slot");

			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}

		catchingTime = tagCompound.getInteger("CatchTime");
		currentCatchingTime = tagCompound.getInteger("CurrentCatchTime");
		baitGradeUsed = tagCompound.getInteger("BaitGradeUsed");
		powerHandler.readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		powerHandler.writeToNBT(tagCompound);
		tagCompound.setInteger("CatchTime", this.catchingTime);
		tagCompound.setInteger("CurrentCatchTime", this.currentCatchingTime);
		tagCompound.setInteger("BaitGradeUsed", this.baitGradeUsed);

		final NBTTagList itemList = new NBTTagList();

		for (int i = 0; i < inventory.length; i++) {
			final ItemStack stack = inventory[i];

			if (stack != null) {
				final NBTTagCompound tag = new NBTTagCompound();

				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}

		tagCompound.setTag("Inventory", itemList);
	}

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

	public void sendGUINetworkData(final ContainerAutofisher fisher, final ICrafting iCrafting) {
		iCrafting.sendProgressBarUpdate(fisher, 0, this.catchingTime);
		iCrafting.sendProgressBarUpdate(fisher, 1, this.currentCatchingTime);
		iCrafting.sendProgressBarUpdate(fisher, 2, this.baitGradeUsed);
		iCrafting.sendProgressBarUpdate(fisher, 3, this.storage.getEnergyStored());
		iCrafting.sendProgressBarUpdate(fisher, 4, this.storage.getMaxEnergyStored());
	}

	@Override
	public String getInvName() {
		return "TileEntityAutofisher";
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
				&& player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
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
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	public int getPowerScaled(int i) {
		return (storage.getEnergyStored() * i) / storage.getMaxEnergyStored();
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return powerHandler.getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider) {
		return;
	}

	@Override
	public World getWorld() {
		return this.worldObj;
	}
}
