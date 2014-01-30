package mariculture.fishery.blocks;

import java.util.ArrayList;
import java.util.List;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.EnumSalinityType;
import mariculture.core.Core;
import mariculture.core.blocks.base.TileMachineTank;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.Text;
import mariculture.core.util.FluidDictionary;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.Rand;
import mariculture.fishery.FishFoodHandler;
import mariculture.fishery.FishHelper;
import mariculture.fishery.Fishery;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;

public class TileFeeder extends TileMachineTank implements IHasNotification {

	private EnumBiomeType theBiome;
	private boolean swap = false;
	public int tankSize = 0;
	
	public TileFeeder() {
		max = MachineSpeeds.getFeederSpeed();
		inventory = new ItemStack[13];
	}
	
	@Override
	public int getTankCapacity(int storage) {
		return ((32 * tankSize) * (storage + 1));
	}
	
	//Slot Vars
	public static final int fluid = 3;
	public static final int male = 5;
	public static final int female = 6;
	public static final int[] out = new int[] { 7, 8, 9, 10, 11, 12 };
	
	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return new int[] { male, female, 3, 7, 8, 9, 10, 11, 12 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		if(slot == male)
			return Fishing.fishHelper.isMale(stack);
		if(slot == female)
			return Fishing.fishHelper.isFemale(stack);
		if(slot == fluid)
			return FluidHelper.isFluidOrEmpty(stack);
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot > female;
	}

	@Override
	public void updateMachine() {	
		if(theBiome == null)
			theBiome = MaricultureHandlers.biomeType.getBiomeType(worldObj.getBiomeGenForCoords(xCoord, zCoord));
		
		//Every 5 Seconds update the tank size
		if(!worldObj.isRemote) {
			if(Extra.TANK_UPDATE > 0) {
				if(onTick(Extra.TANK_UPDATE * 20)) {
					updateTankSize();
				}
			}
			
			if(Extra.FISH_FOOD_TICK > 0) {
				if(onTick(Extra.FISH_FOOD_TICK) || worldObj.provider.isHellWorld)
					addFishFood();
			}
			
			if(onTick(30)) {
				processContainers();
			}

			if(canWork) {
				processed++;
				if(onTick(Extra.EFFECT_TICK)) {
					if(swap) {
						doEffect(male);
						swap = false;
					} else {
						doEffect(female);
						swap = true;
					}
				}
				
				if(processed >= max) {
					processed = 0;
					if(swap) {
						makeProduct(female);
						useFood(male);
					} else {
						makeProduct(male);
						useFood(female);
					}
					
					damageFish(female, true);
					damageFish(male, true);
					
					canWork = canWork();
				}
			} else {
				if(Extra.DEATH_TICKER > 0) {
					if(onTick(Extra.DEATH_TICKER) && hasMale() && hasFemale()) {
						if(!fishCanLive(male))
							damageFish(male, false);
						if(!fishCanLive(female))
							damageFish(female, false);
					}
				}
				
				processed = 0;
			}
		}
	}

	@Override
	public boolean canWork() {
		return hasMale() && hasFemale() && RedstoneMode.canWork(this, mode) && outputHasRoom() && fishCanLive(male) && fishCanLive(female);
	}
	
	//Booleans
	private boolean outputHasRoom()  {
		if(setting.canEject(EjectSetting.ITEM))
			return true;
		for(Integer i: out) {
			if(inventory[i] == null);
				return true;
		}
		
		return false;
	}
	
	private boolean hasMale() {
		return inventory[male] != null && Fishing.fishHelper.isMale(inventory[male]);
	}
	
	private boolean hasFemale() {
		return inventory[female] != null && Fishing.fishHelper.isFemale(inventory[female]);
	}

	private boolean fishCanLive(int slot) {
		if (MaricultureHandlers.upgrades.hasUpgrade("debugLive", this)) {
			return true;
		} else if (tank.getFluid() == null || (tank.getFluid() != null && tank.getFluid().fluidID != FluidRegistry.getFluidID(FluidDictionary.fish_food))) {
			return false;
		}
		
		ItemStack fish = inventory[slot];
		if (fish != null && fish.hasTagCompound()) {
			if (!Fishing.fishHelper.isEgg(fish)) {
				if (Fishery.tankSize.getDNA(fish) > tankSize) {
					return false;
				}
				
				return Fishing.fishHelper.getSpecies(Fishery.species.getDNA(fish)).canLive(worldObj, xCoord, yCoord, zCoord);
			}
		}

		return false;
	}

	//Adding Fish Food to the Tank if Enabled
	private void addFishFood() {
		for(CachedCoords cord: cords) {
			List list = worldObj.getEntitiesWithinAABB(EntityItem.class,
					Block.stone.getCollisionBoundingBoxFromPool(worldObj, cord.x, cord.y, cord.z));
			if(!list.isEmpty()) {
				for (Object i : list) {
					EntityItem entity = (EntityItem) i;
					ItemStack item = entity.getEntityItem();
					if (((entity.handleWaterMovement()) || entity.worldObj.provider.isHellWorld && entity.handleLavaMovement())) {
						item = addFishFood(item);
	
						if (item == null) {
							entity.setDead();
						}
					}
				}
			}
		}
	}
	
	private ItemStack addFishFood(ItemStack stack) {
		if (FishFoodHandler.isFishFood(stack)) {
			int increase = FishFoodHandler.getValue(stack);
			int loop = stack.stackSize;

			for (int i = 0; i < loop; i++) {
				int fill = fill(ForgeDirection.UP, FluidRegistry.getFluidStack(FluidDictionary.fish_food, increase), false);
				if(fill > 0) {
					fill(ForgeDirection.UP, FluidRegistry.getFluidStack(FluidDictionary.fish_food, increase), true);
					stack.stackSize--;
				}
			}
		}

		if (stack.stackSize <= 0) {
			return null;
		}

		return stack;
	}
	
	private void processContainers() {
		ItemStack result = FluidHelper.getFluidResult(this, inventory[3], inventory[4]);
		if (result != null) {
			decrStackSize(3, 1);
			if(result.itemID != Core.airBlocks.blockID) {
				if (this.inventory[4] == null) {
					this.inventory[4] = result.copy();
				} else if (this.inventory[4].itemID == result.itemID) {
					++this.inventory[4].stackSize;
				}
			}
		}
	}

	//Cached Coordinates of blocks that are considered water, Rather than checking for water everytime
	public ArrayList<CachedCoords> cords = new ArrayList<CachedCoords>();
	public class CachedCoords {
		public int x;
		public int y;
		public int z;
		
		public CachedCoords(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
	
	//Update the Tank size
	public void updateTankSize() {
		cords = new ArrayList<CachedCoords>();
		
		int water = 0;
		for(int x = -5; x <= 5; x++) {
			for(int z = -5; z <= 5; z++) {
				for(int y = -5; y <= 5; y++) {
					if(BlockHelper.isWater(worldObj, xCoord + x, yCoord + y, zCoord + z)) {
						cords.add(new CachedCoords(xCoord + x, yCoord + y, zCoord + z));
						water++;
					}
				}
			}
		}		

		tankSize = 0;
		
		if(water >= 15 && water <= 36)
			tankSize = 1;
		if(water > 36 && water <= 82)
			tankSize = 2;
		if(water > 82 && water <= 150)
			tankSize = 3;
		if(water > 150 && water <= 240)
			tankSize = 4;
		if(water > 240)
			tankSize = 5;
	}
	
	private void doEffect(int slot) {
		if(inventory[slot] == null)
			return;
		int species = Fishery.species.getDNA(inventory[slot]);
		if (!this.worldObj.isRemote) {
			Fishing.fishHelper.getSpecies(species).affectWorld(this.worldObj, this.xCoord, this.yCoord, this.zCoord, tankSize);
			
			for(CachedCoords cord: cords) {
				List list = worldObj.getEntitiesWithinAABB(EntityLivingBase.class,
						Block.stone.getCollisionBoundingBoxFromPool(worldObj, cord.x, cord.y, cord.z));
				if(!list.isEmpty()) {
					for (Object i : list) {
						EntityLivingBase entity = (EntityLivingBase) i;
						Fishing.fishHelper.getSpecies(species).affectLiving(entity);
					}
				}
			}
		}
	}
	
	private void useFood(int slot) {
		int foodUsage = 0;
		ItemStack fish = inventory[slot];
		if (fish != null && fish.hasTagCompound()) {
			foodUsage = Fishery.foodUsage.getDNA(fish);
		}
		
		drain(ForgeDirection.UNKNOWN, FluidRegistry.getFluidStack(FluidDictionary.fish_food, foodUsage), true);
	}
	
	private void makeProduct(int slot) {
		//Be more productive!
		if(inventory[slot] == null)
			return;
		for (int i = 0; i < Fishery.production.getDNA(inventory[slot]); i++) {
			ItemStack fish = inventory[slot];
			if (fish != null && fish.hasTagCompound()) {
				if (!Fishing.fishHelper.isEgg(fish)) {
					int speciesID = Fishery.species.getDNA(fish);
					int gender = Fishery.gender.getDNA(fish);
					
					ItemStack product = Fishing.fishHelper.getSpecies(speciesID).getProduct(Rand.rand);
					if(product != null)
						helper.insertStack(product, out);

					//If we have the eternal female upgrade, we need to force generate eggs if the fish is a girl
					if (MaricultureHandlers.upgrades.hasUpgrade("female", this) && gender == FishHelper.FEMALE) {
						int fertility = 150 - Fishing.fishHelper.getSpecies(speciesID).getFertility();
						fertility = (fertility <= 0) ? 1 : fertility;
						if (Rand.nextInt(fertility)) {
							ItemStack fishMale = inventory[male];
							if (fishMale != null && fishMale.hasTagCompound()) {
								if (!Fishing.fishHelper.isEgg(fishMale)) {
									ItemStack egg = Fishing.fishHelper.generateEgg(inventory[male], inventory[female]);
									helper.insertStack(egg, out);
								}
							}
						}
					}

					//However if we have the eternal male, let's make an additional product!
					if (MaricultureHandlers.upgrades.hasUpgrade("male", this) && gender == FishHelper.MALE) {
						product = Fishing.fishHelper.getSpecies(speciesID).getProduct(Rand.rand);
						if(product != null)
							helper.insertStack(product, out);
					}
				}
			}
		}
	}
	
	private void damageFish(int slot, boolean giveProduct) {
		ItemStack fish = inventory[slot];
		if (fish != null && fish.hasTagCompound()) {
			if (!Fishing.fishHelper.isEgg(fish) && fish.stackTagCompound.hasKey("SpeciesID")) {
				//
				int gender = Fishery.gender.getDNA(fish);
				//If we have the eternal life upgrades, let's cancel damaging or killing our fishies
				if (MaricultureHandlers.upgrades.hasUpgrade("female", this) && gender == FishHelper.FEMALE) {
					return;
				}

				if (MaricultureHandlers.upgrades.hasUpgrade("male", this) && gender == FishHelper.MALE) {
					return;
				}

				//Damage our little fishies
				int reduce = max - (purity * 15);
				fish.stackTagCompound.setInteger("CurrentLife", fish.stackTagCompound.getInteger("CurrentLife") - reduce);
				if (fish.stackTagCompound.getInteger("CurrentLife") <= 0 || MaricultureHandlers.upgrades.hasUpgrade("debugKill", this)) {
					killFish(slot, giveProduct);
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

					if (rawFish != null)
						helper.insertStack(rawFish, out);

					if (gender == FishHelper.FEMALE) {
						ItemStack fishMale = inventory[male];
						if (fishMale != null && fishMale.hasTagCompound()) {
							if (!Fishing.fishHelper.isEgg(fishMale)) {
								ItemStack egg = Fishing.fishHelper.generateEgg(inventory[male], inventory[female]);
								helper.insertStack(egg, out);
							}
						}
					}
				}
			}
		}

		decrStackSize(slot, 1);
	}

	@Override
	public boolean isNotificationVisible(NotificationType type) {
		switch(type) {
		case NO_FOOD:
			return tank.getFluid() == null || (tank.getFluid() != null && tank.getFluid().fluidID != FluidRegistry.getFluidID(FluidDictionary.fish_food));
		case NO_MALE:
			return !hasMale();
		case NO_FEMALE:
			return !hasFemale();
		case BAD_ENV:
			return (hasFemale() || hasMale()) && !canWork;
		default:
			return false;
		}
	}
	
	@Override
	public EjectSetting getEjectType() {
		return EjectSetting.ITEM;
	}
	
	public ArrayList<String> getTooltip(int slot, ArrayList<String> tooltip) {
		boolean noBad = true;
		ItemStack fish = inventory[slot];
		if (fish != null && fish.hasTagCompound()) {
			if (!Fishing.fishHelper.isEgg(fish) && fish.stackTagCompound.hasKey("SpeciesID")) {
				int currentLife = fish.stackTagCompound.getInteger("CurrentLife") / 20;

				if (MaricultureHandlers.upgrades.hasUpgrade("debugLive", this)) {
					if (hasMale() && hasFemale()) {
						tooltip.add(Text.DARK_GREEN + currentLife + " HP");
						return tooltip;
					}
				}

				//Instead of 'Bad Biome', we want too hot, too cold, needs fresh or salt
				EnumBiomeType[] biomeTypes = Fishing.fishHelper.getSpecies(fish.stackTagCompound.getInteger("SpeciesID")).getGroup().getBiomes();
				int min = biomeTypes[0].minTemp();
				int max = biomeTypes[0].maxTemp();
				int temp = theBiome.baseTemp() + heat;
				for(EnumBiomeType type: biomeTypes) {
					if(type.minTemp() < min)
						min = type.minTemp();
					if(type.maxTemp() > max)
						max = type.maxTemp();
				}
				
				if(temp < min) {
					tooltip.add(Text.RED + StatCollector.translateToLocal("mariculture.string.tooCold"));
					noBad = false;
				}
				
				if(temp > max) {
					tooltip.add(Text.RED + StatCollector.translateToLocal("mariculture.string.tooHot"));
					noBad = false;
				}
				
				boolean salinityMatches = false;
				EnumSalinityType type = theBiome.getSalinity();
				if(MaricultureHandlers.upgrades.hasUpgrade("salinator", this))
					type = EnumSalinityType.SALT;
				if(MaricultureHandlers.upgrades.hasUpgrade("filter", this))
					type = EnumSalinityType.FRESH;
				if(MaricultureHandlers.upgrades.hasUpgrade("ethereal", this))
					type = EnumSalinityType.MAGIC;
				EnumSalinityType[] types = Fishing.fishHelper.getSpecies(fish.stackTagCompound.getInteger("SpeciesID")).getGroup().getSalinityRequired();
				for(EnumSalinityType salt: types) {
					if(type.equals(salt))
						salinityMatches = true;
				}
				
				if(!salinityMatches) {
					for(EnumSalinityType salt: types) {
						tooltip.add(Text.RED + StatCollector.translateToLocal("mariculture.string.salinity." + salt.toString().toLowerCase()));
					}
					
					noBad = false;
				}

				if (Fishing.fishHelper.getSpecies(fish.stackTagCompound.getInteger("SpeciesID")).getTankLevel() > tankSize) {
					tooltip.add(Text.RED + StatCollector.translateToLocal("mariculture.string.notAdvanced"));
					noBad = false;
				}

				if (!(hasMale() && hasFemale())) {
					tooltip.add(Text.RED + StatCollector.translateToLocal("mariculture.string.missingMate"));
					noBad = false;
				}

				if (tank.getFluid() == null || (tank.getFluid() != null && tank.getFluid().fluidID != FluidRegistry.getFluidID(FluidDictionary.fish_food))) {
					tooltip.add(Text.RED + StatCollector.translateToLocal("mariculture.string.noFood"));
					noBad = false;
				}

				if (hasMale() && hasFemale() && noBad) {
					tooltip.add(Text.DARK_GREEN + currentLife + " HP");
				}
			}
		}

		return tooltip;
	}
	
	public int getFishLifeScaled(int slot, int scale) {
		ItemStack fish = inventory[slot];
		if (fish != null && fish.hasTagCompound()) {
			if (!Fishing.fishHelper.isEgg(fish) && fish.stackTagCompound.hasKey("SpeciesID")) {
				int maxLife = fish.stackTagCompound.getInteger("Lifespan");
				int currentLife = fish.stackTagCompound.getInteger("CurrentLife");

				if ((slot == male && !hasFemale()) || (slot == female && !hasMale()))
					return -1;
				
				if(fishCanLive(slot))
					return (currentLife * scale) / maxLife;

				return -1;
			}
		}

		return 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tankSize = nbt.getInteger("TankSize");
		theBiome = EnumBiomeType.values()[nbt.getInteger("BiomeType")];
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("TankSize", tankSize);
		if(theBiome != null)
			nbt.setInteger("BiomeType", theBiome.ordinal());
	}
}
