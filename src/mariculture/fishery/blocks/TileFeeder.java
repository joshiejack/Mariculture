package mariculture.fishery.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.PrefixColor;
import mariculture.core.util.PacketIntegerUpdate;
import mariculture.fishery.FishFoodHandler;
import mariculture.fishery.FishHelper;
import mariculture.fishery.Fishery;
import mariculture.fishery.TankHelper;
import mariculture.fishery.gui.ContainerFeeder;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import cofh.api.energy.IEnergyHandler;

public class TileFeeder extends TileEntity implements IInventory, IUpgradable, ISidedInventory {
	private final ItemStack[] inventory = new ItemStack[7];

	private Random rand = new Random();
	private int tick = 0;
	private int foodTick = 0;
	private int tankCheck = 0;
	private int tankSize;
	private int tankType;
	private int currentBreedingTime = 0;
	private int breedingLength = MachineSpeeds.getFeederSpeed();
	private int maxFood = 640;
	private int foodAmount = 0;
	private int maleAlive = 0;
	private int femaleAlive = 0;

	@Override
	public void updateEntity() {
		tick++;

		if (tick > 20 || this.worldObj.provider.isHellWorld) {
			tick = 0;
			pickUpFishFood();
			updateStorage();
		}

		if (tankCheck > 1200) {
			tankCheck = 0;
			setTankSize();
		}

		if (!this.worldObj.isRemote && tankSize > 0) {
			int both = 0;
			if (hasFish(0)) {
				both++;
			}

			if (hasFish(1)) {
				both++;
			}

			if (both == 2) {
				if (tick == 0) {
					doEffect(0);
				}

				if (tick == 10) {
					doEffect(1);
				}

				this.currentBreedingTime++;
				

				if (this.currentBreedingTime >= breedingLength) {
					foodTick++;
					if (foodTick >= 10) {
						foodTick = 0;
						
						for (int i = 0; i < Fishery.production.getDNA(inventory[0]); i++) {
							makeProduct(0);
						}

						for (int i = 0; i < Fishery.production.getDNA(inventory[1]); i++) {
							makeProduct(1);
						}

						useFood(0);
						useFood(1);
					}

					damageFish(1);
					damageFish(0);
					this.currentBreedingTime = 0;
				}
			} else {
				this.currentBreedingTime = 0;
			}
		}
	}

	private void makeProduct(int slot) {
		ItemStack fish = inventory[slot];
		if (fish != null && fish.hasTagCompound()) {
			if (!Fishing.fishHelper.isEgg(fish)) {
				int gender = Fishery.gender.getDNA(fish);
				int speciesID = Fishery.species.getDNA(fish);
				
				ItemStack product = Fishing.fishHelper.getSpecies(speciesID).getProduct(rand);

				if (product != null) {
					addItemToWorld(product);
				}

				// If they have the eternal female, chance to produce egg/extra
				// product!

				if (MaricultureHandlers.upgrades.hasUpgrade("female", this) && gender == FishHelper.FEMALE) {
					int fertility = 150 - Fishing.fishHelper.getSpecies(speciesID).getFertility();

					fertility = (fertility <= 0) ? 1 : fertility;

					if (rand.nextInt(fertility) == 0) {
						final ItemStack male = inventory[0];
						if (male != null && male.hasTagCompound()) {
							if (!Fishing.fishHelper.isEgg(male)) {
								ItemStack egg = Fishing.fishHelper.generateEgg(inventory[0], inventory[1]);
								addItemToWorld(egg);
							}
						}
					}
				}

				if (MaricultureHandlers.upgrades.hasUpgrade("male", this) && gender == FishHelper.MALE) {
					product = Fishing.fishHelper.getSpecies(speciesID).getProduct(rand);

					if (product != null) {
						addItemToWorld(product);
					}
				}
			}
		}
	}

	private void useFood(final int slot) {
		int foodUsage = 0;
		final ItemStack fish = inventory[slot];
		if (fish != null && fish.hasTagCompound()) {
			if (!Fishing.fishHelper.isEgg(fish)) {
				foodUsage = Fishery.foodUsage.getDNA(fish);
			}
		}

		if (foodAmount > 0) {
			this.foodAmount = this.foodAmount - foodUsage;
		}
	}

	private void damageFish(int slot) {
		ItemStack fish = inventory[slot];
		if (fish != null && fish.hasTagCompound()) {
			if (!Fishing.fishHelper.isEgg(fish) && fish.stackTagCompound.hasKey("SpeciesID")) {
				int purity = MaricultureHandlers.upgrades.getData("purity", this);
				int gender = Fishery.gender.getDNA(fish);
				if (MaricultureHandlers.upgrades.hasUpgrade("female", this) && gender == FishHelper.FEMALE) {
					return;
				}

				if (MaricultureHandlers.upgrades.hasUpgrade("male", this) && gender == FishHelper.MALE) {
					return;
				}

				int reduce = breedingLength - (purity * 15);
				fish.stackTagCompound.setInteger("CurrentLife", fish.stackTagCompound.getInteger("CurrentLife")
						- reduce);
				if (fish.stackTagCompound.getInteger("CurrentLife") <= 0) {
					killFish(slot, true);
				}

			}
		}
	}

	private void killFish(int slot, boolean giveProduct) {
		if (giveProduct) {
			ItemStack fish = inventory[slot];
			if (fish != null && fish.hasTagCompound()) {
				if (!Fishing.fishHelper.isEgg(fish)) {
					int gender = Fishery.gender.getDNA(fish);
					int species = Fishery.species.getDNA(fish);

					ItemStack rawFish = new ItemStack(Fishery.fishyFood, 1, species);
					rawFish.setItemDamage(species);

					if (rawFish != null) {
						addItemToWorld(rawFish);
					}

					if (gender == FishHelper.FEMALE) {
						ItemStack male = inventory[0];
						if (male != null && male.hasTagCompound()) {
							if (!Fishing.fishHelper.isEgg(male)) {
								ItemStack egg = Fishing.fishHelper.generateEgg(inventory[0], inventory[1]);
								addItemToWorld(egg);
							}
						}
					}
				}
			}
		}

		decrStackSize(slot, 1);
	}

	private boolean addItemToWorld(ItemStack stack) {

		if (InventoryHelper.addToInventory(TankHelper.getDistance(getTankSize()), worldObj, xCoord, yCoord, zCoord,
				stack,
				null)) {
			return true;
		}

		if (inventory[2] == null) {
			setInventorySlotContents(2, stack);
			return true;
		}

		if (inventory[2].isItemEqual(stack) && inventory[2].stackSize < inventory[2].getMaxStackSize()) {
			inventory[2].stackSize++;
			return true;
		}

		if (inventory[3] == null) {
			setInventorySlotContents(3, stack);
			return true;
		}

		if (inventory[3].isItemEqual(stack) && inventory[3].stackSize < inventory[3].getMaxStackSize()) {
			inventory[3].stackSize++;
			return true;
		}

		final EntityItem dropped = new EntityItem(this.worldObj, this.xCoord, this.yCoord + 1, this.zCoord, stack);
		this.worldObj.spawnEntityInWorld(dropped);

		return false;
	}

	private boolean canLive(ItemStack fish) {
		if (MaricultureHandlers.upgrades.hasUpgrade("debugLive", this)) {
			return true;
		}

		if (this.foodAmount <= 0) {
			return false;
		}

		if (!Fishing.fishHelper.getSpecies(Fishery.species.getDNA(fish)).canLive(this.worldObj, this.xCoord, this.yCoord, this.zCoord)) {
			return false;
		}

		if (Fishery.tankSize.getDNA(fish) > tankType) {
			return false;
		}

		return true;
	}

	private boolean hasFish(int slot) {
		final ItemStack fish = inventory[slot];
		if (fish != null && fish.hasTagCompound()) {
			if (!Fishing.fishHelper.isEgg(fish)) {
				if (canLive(fish)) {
					if (slot == 0) {
						maleAlive = 1;
					}

					if (slot == 1) {
						femaleAlive = 1;
					}

					return true;
				}
			}
		}

		if (slot == 0) {
			maleAlive = 0;
		}

		if (slot == 1) {
			femaleAlive = 0;
		}

		return false;
	}

	private boolean hasOtherFish(final int slot) {
		final boolean bothFish = false;
		final int otherSlot = (slot == 0) ? 1 : 0;
		final ItemStack other = inventory[otherSlot];
		if (other != null && other.hasTagCompound()) {
			if (!Fishing.fishHelper.isEgg(other) && other.stackTagCompound.hasKey("SpeciesID")) {
				return true;
			}
		}

		return false;
	}

	public int getFishLifeScaled(final int slot, final int scale) {
		final ItemStack fish = inventory[slot];
		if (fish != null && fish.hasTagCompound()) {
			if (!Fishing.fishHelper.isEgg(fish) && fish.stackTagCompound.hasKey("SpeciesID")) {
				final int maxLife = fish.stackTagCompound.getInteger("Lifespan");
				final int currentLife = fish.stackTagCompound.getInteger("CurrentLife");

				if (!hasOtherFish(slot)) {
					return -1;
				}

				if ((slot == 0 && maleAlive == 1) || (slot == 1 && femaleAlive == 1)) {
					return (currentLife * scale) / maxLife;
				}

				return -1;
			}
		}

		return 0;
	}

	private ItemStack addFishFood(final ItemStack stack) {
		if (FishFoodHandler.isFishFood(stack)) {
			final int increase = FishFoodHandler.getValue(stack);
			final int loop = stack.stackSize;

			for (int i = 0; i < loop; i++) {
				if (this.foodAmount + increase <= maxFood) {
					this.foodAmount = this.foodAmount + increase;
					stack.stackSize--;
				}
			}
		}

		if (stack.stackSize <= 0) {
			return null;
		}

		return stack;
	}

	private void doEffect(int slot) {
		int species = Fishery.species.getDNA(inventory[slot]);

		if (!this.worldObj.isRemote && tankSize > 0) {
			Fishing.fishHelper.getSpecies(species).affectWorld(this.worldObj, this.xCoord, this.yCoord, this.zCoord,
					tankType);

			List list = this.worldObj.getEntitiesWithinAABB(
					EntityLivingBase.class,
					this.getBlockType()
							.getCollisionBoundingBoxFromPool(this.worldObj, this.xCoord, this.yCoord, this.zCoord)
							.expand(4.0D, 4.0D, 4.0D));
			if (!list.isEmpty()) {
				list = this.worldObj.getEntitiesWithinAABB(
						EntityLivingBase.class,
						this.getBlockType()
								.getCollisionBoundingBoxFromPool(this.worldObj, this.xCoord, this.yCoord, this.zCoord)
								.expand(tankType, tankType, tankType));
			}

			for (Object i : list) {
				EntityLivingBase living = (EntityLivingBase) i;
				if (living.isInWater()) {
					Fishing.fishHelper.getSpecies(species).affectLiving(living);
				}
			}
		}
	}

	private void pickUpFishFood() {
		if (!this.worldObj.isRemote && tankSize > 0 && foodAmount < maxFood) {
			List list = this.worldObj.getEntitiesWithinAABB(
					EntityItem.class,
					this.getBlockType()
							.getCollisionBoundingBoxFromPool(this.worldObj, this.xCoord, this.yCoord, this.zCoord)
							.expand(4.0D, 4.0D, 4.0D));
			if (!list.isEmpty()) {
				list = this.worldObj.getEntitiesWithinAABB(
						EntityItem.class,
						this.getBlockType()
								.getCollisionBoundingBoxFromPool(this.worldObj, this.xCoord, this.yCoord, this.zCoord)
								.expand(tankType, tankType, tankType));
			}

			for (Object i : list) {
				EntityItem entity = (EntityItem) i;
				ItemStack item = entity.getEntityItem();
				if (((entity.handleWaterMovement()) || entity.worldObj.provider.isHellWorld
						&& entity.handleLavaMovement())) {
					item = addFishFood(item);

					if (item == null) {
						entity.setDead();
					}
				}
			}
		}
	}

	public int getCatchTimeRemainingScaled(int scale) {
		return (currentBreedingTime * scale) / breedingLength;
	}

	public int getTankSize() {
		return this.tankSize;
	}

	private void updateStorage() {
		int storageCount = MaricultureHandlers.upgrades.getData("storage", this) + 1;

		this.maxFood = ((16 * tankSize) * storageCount);

		if (this.foodAmount > maxFood) {
			this.foodAmount = maxFood;
		}
	}

	public void setTankSize() {
		final ArrayList<String> array = TankHelper.getSurroundingArray(this);

		this.tankSize = TankHelper.getTankSize(array);

		this.tankType = 1;

		if (this.tankSize >= 2) {
			this.tankType = 3;
		}

		if (this.tankSize >= 4) {
			this.tankType = 5;
		}

		updateStorage();
	}

	@Override
	public void readFromNBT(final NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		final NBTTagList tagList = tagCompound.getTagList("Inventory");

		for (int i = 0; i < tagList.tagCount(); i++) {
			final NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

			final byte slot = tag.getByte("Slot");

			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}

		tick = tagCompound.getInteger("Tick");
		foodTick = tagCompound.getInteger("FoodTick");
		tankSize = tagCompound.getInteger("TankSize");
		tankType = tagCompound.getInteger("TankType");
		foodAmount = tagCompound.getInteger("FoodAmount");
		maxFood = tagCompound.getInteger("MaxFood");
		currentBreedingTime = tagCompound.getInteger("CurrentBreedingTime");
		tankCheck = tagCompound.getInteger("TankCheck");
	}

	@Override
	public void writeToNBT(final NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setInteger("Tick", this.tick);
		tagCompound.setInteger("FoodTick", this.foodTick);
		tagCompound.setInteger("TankSize", this.tankSize);
		tagCompound.setInteger("TankType", this.tankType);
		tagCompound.setInteger("FoodAmount", this.foodAmount);
		tagCompound.setInteger("MaxFood", this.maxFood);
		tagCompound.setInteger("CurrentBreedingTime", this.currentBreedingTime);
		tagCompound.setInteger("TankCheck", this.tankCheck);

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

	public void sendGUINetworkData(ContainerFeeder feeder, final EntityPlayer player) {
		PacketIntegerUpdate.send(feeder, 0, this.foodAmount, player);
		PacketIntegerUpdate.send(feeder, 1, this.currentBreedingTime, player);
		PacketIntegerUpdate.send(feeder, 2, this.maxFood, player);
		PacketIntegerUpdate.send(feeder, 3, this.maleAlive, player);
		PacketIntegerUpdate.send(feeder, 4, this.femaleAlive, player);
	}

	public void getGUINetworkData(int i, int j) {
		switch (i) {
		case 0:
			this.foodAmount = j;
			break;
		case 1:
			this.currentBreedingTime = j;
			break;
		case 2:
			this.maxFood = j;
			break;
		case 3:
			this.maleAlive = j;
			break;
		case 4:
			this.femaleAlive = j;
			break;
		}
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(final int slotIndex) {
		return inventory[slotIndex];
	}

	@Override
	public ItemStack decrStackSize(final int slotIndex, final int amount) {
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
	public ItemStack getStackInSlotOnClosing(final int slotIndex) {
		final ItemStack stack = getStackInSlot(slotIndex);

		if (stack != null) {
			setInventorySlotContents(slotIndex, null);
		}

		return stack;
	}

	@Override
	public void setInventorySlotContents(final int slot, final ItemStack stack) {
		inventory[slot] = stack;
		int limit = getInventoryStackLimit();
		if (slot > 8) {
			limit = 1;
		}

		if (stack != null && stack.stackSize > limit) {
			stack.stackSize = limit;
		}
	}

	@Override
	public String getInvName() {
		return "TileEntityFeeder";
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(final EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
				&& player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	public List<String> getLifespan(final int slot, final List<String> currenttip) {
		boolean noBad = true;
		final ItemStack fish = inventory[slot];
		if (fish != null && fish.hasTagCompound()) {
			if (!Fishing.fishHelper.isEgg(fish) && fish.stackTagCompound.hasKey("SpeciesID")) {
				int currentLife = fish.stackTagCompound.getInteger("CurrentLife") / 20;

				if (MaricultureHandlers.upgrades.hasUpgrade("debugLive", this)) {
					if (((slot == 0 && maleAlive == 1) || (slot == 1 && femaleAlive == 1))) {
						currenttip.add(PrefixColor.DARK_GREEN + currentLife + " HP");
						return currenttip;
					}
				}

				if (!Fishing.fishHelper.getSpecies(fish.stackTagCompound.getInteger("SpeciesID")).canLive(this.worldObj,
						this.xCoord, this.yCoord, this.zCoord)) {
					currenttip.add(PrefixColor.RED + StatCollector.translateToLocal("mariculture.string.badBiome"));
					noBad = false;
				}

				if (Fishing.fishHelper.getSpecies(fish.stackTagCompound.getInteger("SpeciesID")).getTankLevel() > tankType) {
					currenttip.add(PrefixColor.RED + StatCollector.translateToLocal("mariculture.string.notAdvanced"));
					noBad = false;
				}

				if (!hasOtherFish(slot)) {
					currenttip.add(PrefixColor.RED + StatCollector.translateToLocal("mariculture.string.missingMate"));
					noBad = false;
				}

				if (foodAmount == 0) {
					currenttip.add(PrefixColor.RED + StatCollector.translateToLocal("mariculture.string.noFood"));
					noBad = false;
				}

				if (((slot == 0 && maleAlive == 1) || (slot == 1 && femaleAlive == 1)) && noBad) {
					currenttip.add(PrefixColor.DARK_GREEN + currentLife + " HP");
				}
			}
		}

		return currenttip;
	}

	public String getFishFood() {
		return (this.foodAmount + "/" + maxFood + " " + StatCollector.translateToLocal("mariculture.string.fishFood"));
	}

	public int getScaledBurnTime(final int i) {
		return (foodAmount * i) / maxFood;
	}

	@Override
	public ItemStack[] getUpgrades() {
		return new ItemStack[] { inventory[4], inventory[5], inventory[6] };
	}

	private static final int[] slots = new int[] { 0, 1, 2, 3 };

	@Override
	public int[] getAccessibleSlotsFromSide(final int side) {
		return slots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 2 || slot == 3;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {

		if (stack.getItem() instanceof ItemFishy && Fishery.gender.getDNA(stack) == FishHelper.MALE && slot == 0) {
			return true;
		}

		if (stack.getItem() instanceof ItemFishy && Fishery.gender.getDNA(stack) == FishHelper.FEMALE && slot == 1) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}
}
