package mariculture.fishery.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.core.blocks.core.TileMachine;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.Text;
import mariculture.core.network.Packets;
import mariculture.fishery.FishFoodHandler;
import mariculture.fishery.FishHelper;
import mariculture.fishery.Fishery;
import mariculture.fishery.TankHelper;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class TileFeeder extends TileMachine implements ISidedInventory {
	private Random rand = new Random();
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
	
	public TileFeeder() {
		inventory = new ItemStack[7];
	}

	@Override
	public void updateMachine() {
		if(onTick(20) || this.worldObj.provider.isHellWorld) {
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
				if (onTick(20)) {
					doEffect(0);
				}

				if (onTick(15)) {
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
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		foodTick = nbt.getInteger("FoodTick");
		tankSize = nbt.getInteger("TankSize");
		tankType = nbt.getInteger("TankType");
		foodAmount = nbt.getInteger("FoodAmount");
		maxFood = nbt.getInteger("MaxFood");
		currentBreedingTime = nbt.getInteger("CurrentBreedingTime");
		tankCheck = nbt.getInteger("TankCheck");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("FoodTick", this.foodTick);
		nbt.setInteger("TankSize", this.tankSize);
		nbt.setInteger("TankType", this.tankType);
		nbt.setInteger("FoodAmount", this.foodAmount);
		nbt.setInteger("MaxFood", this.maxFood);
		nbt.setInteger("CurrentBreedingTime", this.currentBreedingTime);
		nbt.setInteger("TankCheck", this.tankCheck);
	}

	@Override
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		Packets.updateGUI(player, container, 0, this.foodAmount);
		Packets.updateGUI(player, container, 1, this.currentBreedingTime);
		Packets.updateGUI(player, container, 2, this.maxFood);
		Packets.updateGUI(player, container, 3, this.maleAlive);
		Packets.updateGUI(player, container, 4, this.femaleAlive);
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

	public List<String> getLifespan(final int slot, final List<String> currenttip) {
		boolean noBad = true;
		final ItemStack fish = inventory[slot];
		if (fish != null && fish.hasTagCompound()) {
			if (!Fishing.fishHelper.isEgg(fish) && fish.stackTagCompound.hasKey("SpeciesID")) {
				int currentLife = fish.stackTagCompound.getInteger("CurrentLife") / 20;

				if (MaricultureHandlers.upgrades.hasUpgrade("debugLive", this)) {
					if (((slot == 0 && maleAlive == 1) || (slot == 1 && femaleAlive == 1))) {
						currenttip.add(Text.DARK_GREEN + currentLife + " HP");
						return currenttip;
					}
				}

				if (!Fishing.fishHelper.getSpecies(fish.stackTagCompound.getInteger("SpeciesID")).canLive(this.worldObj,
						this.xCoord, this.yCoord, this.zCoord)) {
					currenttip.add(Text.RED + StatCollector.translateToLocal("mariculture.string.badBiome"));
					noBad = false;
				}

				if (Fishing.fishHelper.getSpecies(fish.stackTagCompound.getInteger("SpeciesID")).getTankLevel() > tankType) {
					currenttip.add(Text.RED + StatCollector.translateToLocal("mariculture.string.notAdvanced"));
					noBad = false;
				}

				if (!hasOtherFish(slot)) {
					currenttip.add(Text.RED + StatCollector.translateToLocal("mariculture.string.missingMate"));
					noBad = false;
				}

				if (foodAmount == 0) {
					currenttip.add(Text.RED + StatCollector.translateToLocal("mariculture.string.noFood"));
					noBad = false;
				}

				if (((slot == 0 && maleAlive == 1) || (slot == 1 && femaleAlive == 1)) && noBad) {
					currenttip.add(Text.DARK_GREEN + currentLife + " HP");
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

	@Override
	public void updateUpgrades() {
		
	}
}
