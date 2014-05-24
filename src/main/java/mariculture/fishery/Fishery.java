package mariculture.fishery;

import static mariculture.core.lib.ItemLib.*;
import static mariculture.core.helpers.RecipeHelper.*;

import java.util.Arrays;

import mariculture.Mariculture;
import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RecipeSifter;
import mariculture.api.fishery.RodType;
import mariculture.core.Core;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.items.ItemBattery;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.BottleMeta;
import mariculture.core.lib.BucketMeta;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.Extra;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.ItemLib;
import mariculture.core.lib.MachineMeta;
import mariculture.core.lib.MachineMultiMeta;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.util.Fluids;
import mariculture.fishery.blocks.BlockCustard;
import mariculture.fishery.blocks.BlockFishOil;
import mariculture.fishery.blocks.BlockItemNet;
import mariculture.fishery.blocks.BlockNeonLamp;
import mariculture.fishery.items.ItemBait;
import mariculture.fishery.items.ItemEgg;
import mariculture.fishery.items.ItemFishy;
import mariculture.fishery.items.ItemFluxRod;
import mariculture.fishery.items.ItemRod;
import mariculture.fishery.items.ItemScanner;
import mariculture.fishery.tile.TileAutofisher;
import mariculture.fishery.tile.TileFeeder;
import mariculture.fishery.tile.TileFishTank;
import mariculture.fishery.tile.TileIncubator;
import mariculture.fishery.tile.TileSift;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class Fishery extends RegistrationModule {	
	public static Block lampsOff;
	public static Block lampsOn;

	public static Item fishEggs;
	public static Item bait;
	public static Item rodWood;
	public static Item rodTitanium;
	public static Item rodReed;
	public static Item rodFlux;
	public static Item fishy;
	public static Item net;
	public static Item scanner;

	public static Fluid fishFood;
	public static Fluid fishOil;
	public static Fluid milk;
	public static Fluid custard;
	public static Fluid moltenDirt;
	
	public static Block fishOilBlock;
	public static Block custardBlock;
	
	@Override
	public void registerHandlers() {
		if(Fishing.fishing == null) Fishing.fishing = new FishingHandler();
		if(Fishing.fishHelper == null) Fishing.fishHelper = new FishyHelper();
		Fishing.mutation = new FishMutationHandler();
		Fishing.food = new FishFoodHandler();
		Fishing.sifter = new SifterHandler();
		MinecraftForge.EVENT_BUS.register(new FisheryEventHandler());
		MinecraftForge.EVENT_BUS.register(new BaitListingsHandler());
	}
	
	@Override
	public void registerItems() {
		bait = new ItemBait().setUnlocalizedName("bait");
		rodReed = new ItemRod(96, 24).setUnlocalizedName("rod.reed");
		rodWood = new ItemRod(320, 10).setUnlocalizedName("rod.wood");
		rodTitanium = new ItemRod(640, 16).setUnlocalizedName("rod.titanium");
		rodFlux = new ItemFluxRod().setUnlocalizedName("rod.flux");
		fishy = new ItemFishy().setUnlocalizedName("fishy").setCreativeTab(MaricultureTab.tabFish);
		net = new BlockItemNet().setUnlocalizedName("net");
		scanner = new ItemScanner().setUnlocalizedName("scanner");
		fishEggs = new ItemEgg().setUnlocalizedName("eggs.fish");

		RegistryHelper.registerItems(new Item[] { bait, rodWood, rodReed, rodTitanium, fishy, net, rodFlux, scanner, fishEggs });
	}
	
	@Override
	public void registerFluids() {
		fishFood = FluidHelper.addFluid("fish_food", "fishfood", 512, BottleMeta.FISH_FOOD);
		fishOil = FluidHelper.addFluid("fish_oil", "fishoil", 2000, BottleMeta.FISH_OIL);
		milk = FluidHelper.addFluid("milk", 2000, BottleMeta.MILK);
		custard = FluidHelper.addFluid("custard", 2000, BottleMeta.CUSTARD);
		moltenDirt = FluidHelper.addFluid("dirt", 1000, -1);
		FluidHelper.registerBucket(moltenDirt, 1000, BucketMeta.DIRT);
		FluidHelper.registerBucket(custard, 1000, BucketMeta.CUSTARD);
		FluidHelper.registerBucket(fishOil, 1000, BucketMeta.FISH_OIL);
		FluidHelper.registerVanillaBottle(fishFood, 256, BottleMeta.FISH_FOOD_BASIC);
		FluidHelper.registerVanillaBottle(fishOil, 1000, BottleMeta.FISH_OIL_BASIC);
		FluidHelper.registerVanillaBottle(milk, 1000, BottleMeta.MILK_BASIC);
		FluidHelper.registerVanillaBottle(custard, 1000, BottleMeta.CUSTARD_BASIC);
		FluidContainerRegistry.registerFluidContainer(new FluidStack(custard, 250), new ItemStack(Core.food, 1, FoodMeta.CUSTARD), new ItemStack(Items.bowl));
		FluidContainerRegistry.registerFluidContainer(new FluidStack(milk, 1000), new ItemStack(Items.milk_bucket), new ItemStack(Items.bucket));
	}

	@Override
	public void registerBlocks() {
		fishOilBlock = new BlockFishOil(fishOil, Material.water).setBlockName("fish.oil");
		custardBlock = new BlockCustard(custard, Material.water).setBlockName("custard");
		lampsOff = new BlockNeonLamp(true, "lamp_on_").setBlockName("lamps.off");
		lampsOn = new BlockNeonLamp(false, "lamp_off_").setBlockName("lamps.on");
		RegistryHelper.registerBlocks(new Block[] { lampsOff, lampsOn, fishOilBlock, custardBlock });
		RegistryHelper.registerTiles(new Class[] { TileAutofisher.class, TileSift.class, TileIncubator.class, TileFeeder.class, TileFishTank.class });
		
		fishOil.setBlock(fishOilBlock);
		custard.setBlock(custardBlock);
	}
	@Override
	public void registerOther() {
		RecipeSorter.INSTANCE.register("mariculture:caviar", ShapelessFishRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
		registerEntities();
		Fish.init();
		registerRods();
		MaricultureTab.tabFish.icon = new ItemStack(Items.fish);
	}
	
	private void registerEntities() {
		EntityRegistry.registerModEntity(EntityBass.class, "BassBomb", EntityIds.BASS, Mariculture.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityHook.class, "FishingHook", EntityIds.FISHING, Mariculture.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityItemFireImmune.class, "EntityItemImmune", EntityIds.ITEM, Mariculture.instance, 80, 30, true);
	}
	
	private void registerRods() {
		Fishing.fishing.registerRod(Items.fishing_rod, RodType.DIRE);
		Fishing.fishing.registerRod(rodReed, RodType.OLD);
		Fishing.fishing.registerRod(rodWood, RodType.GOOD);
		Fishing.fishing.registerRod(rodTitanium, RodType.SUPER);
		Fishing.fishing.registerRod(rodFlux, RodType.FLUX);
	}

	@Override
	public void registerRecipes() {		
		addBait();
		addDropletRecipes();
		addFishRecipes();
		FishingLoot.add();
		
		addFishingRodRecipe(_(rodReed), reeds);
		addFishingRodRecipe(_(rodWood), polishedStick);
		addFishingRodRecipe(_(rodTitanium), polishedTitanium);
		addShaped(ItemBattery.make(_(rodFlux), 0), new Object[] {"  R", " RS", "B S", 'R', rodTitanium, 'S', string, 'B', titaniumBattery});
		addVatItemRecipe(_(log), Fluids.fish_oil, 30000, polishedLog, 45);
		addVatItemRecipe(_(planks), Fluids.fish_oil, 10000, polishedPlank, 30);
		addVatItemRecipe(_(stick), Fluids.fish_oil, 5000, polishedStick, 15);
		addShapeless(_(polishedPlank, 4), new Object[] {polishedLog});
		addShaped(_(polishedStick, 4), new Object[] {"S", "S", 'S', polishedPlank});
		addVatItemRecipe(titaniumRod, Fluids.fish_oil, 6500, polishedTitanium, 30);
		addShapeless(thermometer, new Object[] { fish, compass });
		addShaped(_(scanner), new Object[] {"WPE", "NFR", "JBO", 'N', dropletNether, 'P', pearls, 'W', dropletWater, 'R', dropletEarth, 'F', fish, 'O', dropletEnder, 'E', dropletFrozen, 'B', copperBattery, 'J', dropletPoison});
		
		if(!Extra.DISABLE_DIRT_CRAFTING) {
			addBlockCasting(new FluidStack(moltenDirt, 1000), new ItemStack(dirt));
		}
		
		addMelting(new ItemStack(dirt), 333, new FluidStack(moltenDirt, 1000));
		
		/* Fishing Net, Autofisher and Sift */
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(net, 4, 0), new Object[] { "SWS", "WWW", "SWS",
					Character.valueOf('S'), "stickWood", 
					Character.valueOf('W'), Items.string }));
		
		RecipeHelper.addShaped(new ItemStack(Core.renderedMachines, 1, MachineRenderedMeta.SIFTER), new Object[] {
			"PNP", "S S", 'S', "stickWood", 'P', "plankWood", 'N', net
		});
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.machines, 1, MachineMeta.AUTOFISHER), new Object[] { " F ", "RPR", "WBW", 
					Character.valueOf('W'), "logWood", 
					Character.valueOf('R'), new ItemStack(rodWood), 
					Character.valueOf('F'), new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), 
					Character.valueOf('B'), new ItemStack(Core.woods, 1, WoodMeta.BASE_WOOD),
					Character.valueOf('P'), "plankWood"}));
		
		/* Fish Feeder and Incubator Blocks */
		RecipeHelper.addShaped(new ItemStack(Core.renderedMachines, 1, MachineRenderedMeta.FISH_FEEDER), new Object[] { 
			"WFW", "WCW", "WFW",  'F', new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), 
				'W', new ItemStack(Core.crafting, 1, CraftingMeta.WICKER), 'C', Blocks.chest });

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.machinesMulti, 1, MachineMultiMeta.INCUBATOR_TOP), new Object[] {"DFD", "CHC", 
					Character.valueOf('F'), new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), 
					Character.valueOf('D'), "dyeBrown", 
					Character.valueOf('C'), new ItemStack(Blocks.stained_hardened_clay, 1, 0), 
					Character.valueOf('H'), new ItemStack(Core.crafting, 1, CraftingMeta.HEATER) }));

		//Incubator Base
		RecipeHelper.addShaped(new ItemStack(Core.machinesMulti, 1, MachineMultiMeta.INCUBATOR_BASE), new Object[] {
			"DBD", "CHC",
			Character.valueOf('C'), new ItemStack(Blocks.stained_hardened_clay, 1, 3),
			Character.valueOf('B'), new ItemStack(Core.batteryCopper, 1, OreDictionary.WILDCARD_VALUE),
			Character.valueOf('D'), "dyeLightBlue",
			Character.valueOf('H'), new ItemStack(Core.crafting, 1, CraftingMeta.HEATER)
		});
		
		//FishTank
		RecipeHelper.addShaped(new ItemStack(Core.tanks, 1, TankMeta.FISH), new Object[] {
			"AGA", "GFG", "AGA", 'A', "ingotAluminum", 'G', "glass",
			'F', new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE)
		});
		
		//Milk + 2 Sugar = Custard
		RecipeHelper.addVatItemRecipeResultFluid(new ItemStack(Items.sugar, 2, 0), FluidRegistry.getFluidStack(Fluids.milk, 1000),
				FluidRegistry.getFluidStack(Fluids.custard, 1000), 15);
		
		CraftingManager.getInstance().getRecipeList().add(new ShapelessFishRecipe(new ItemStack(Core.food, 1, FoodMeta.CAVIAR), new ItemStack(fishEggs)));
	}

	private void addBait() {
		Fishing.fishing.addBait(new ItemStack(bait, 1, BaitMeta.ANT), 10);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.ANT), new ItemStack(Blocks.dirt), 2, 3, 40));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.ANT), new ItemStack(Blocks.grass), 1, 2, 50));
		
		Fishing.fishing.addBait(new ItemStack(Items.bread), 30);
		
		Fishing.fishing.addBait(new ItemStack(bait, 1, BaitMeta.HOPPER), 40);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.grass), 1, 2, 35));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.sapling), 2, 3, 35));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.leaves), 1, 2, 15));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.tallgrass, 0, 1), 4, 5, 45));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.tallgrass, 0, 2), 2, 3, 40));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.tallgrass, 0, 0), 1, 2, 10));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.yellow_flower), 2, 5, 20));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.red_flower), 3, 4, 25));
		
		Fishing.fishing.addBait(new ItemStack(bait, 1, BaitMeta.MAGGOT), 55);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Items.rotten_flesh), 1, 2, 60));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Items.beef), 14, 22, 20));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Items.chicken), 6, 14, 30));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Items.porkchop), 10, 18, 25));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), ItemLib.zombie, 8, 15, 80));
		
		Fishing.fishing.addBait(new ItemStack(bait, 1, BaitMeta.BEE), 70);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.BEE), new ItemStack(Blocks.yellow_flower), 2, 3, 25));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.BEE), new ItemStack(Blocks.red_flower), 1, 2, 30));
		
		Fishing.fishing.addBait(new ItemStack(bait, 1, BaitMeta.WORM), 75);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.WORM), new ItemStack(Items.apple), 1, 3, 15));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.WORM), new ItemStack(Blocks.dirt), 2, 3, 10));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.WORM), new ItemStack(Blocks.grass), 3, 5, 20));
		
		Fishing.fishing.addBait(new ItemStack(Items.fish, 1, Fish.minnow.getID()), 100);
		
		Fishing.fishing.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.ANT), Arrays.asList(RodType.DIRE, RodType.OLD, RodType.FLUX));
		Fishing.fishing.addBaitForQuality(new ItemStack(bread), Arrays.asList(RodType.DIRE, RodType.OLD, RodType.FLUX));
		Fishing.fishing.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.HOPPER), Arrays.asList(RodType.OLD, RodType.GOOD, RodType.FLUX));
		Fishing.fishing.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.MAGGOT), Arrays.asList(RodType.GOOD, RodType.FLUX));
		Fishing.fishing.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.WORM), Arrays.asList(RodType.GOOD, RodType.SUPER, RodType.FLUX));
		Fishing.fishing.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.BEE), Arrays.asList(RodType.SUPER, RodType.FLUX));
		Fishing.fishing.addBaitForQuality(new ItemStack(Items.fish, 1, Fish.minnow.getID()), Arrays.asList(RodType.SUPER, RodType.FLUX));
	}
	
	private void addDropletRecipes() {
		if(!Extra.DISABLE_GRASS) addShaped(_(grass), new Object[] {"HHH", "EEE", "EEE", 'H', dropletPlant, 'E', dropletEarth});
		add2x2Recipe(_(snowball), dropletFrozen);
		add3x3Recipe(_(ice), dropletFrozen);
		addMelting(dropletEarth, 333, new FluidStack(moltenDirt, 100));
		addMelting(dropletWater, 1, new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME));
		addMelting(dropletAqua, 1, new FluidStack(Core.hpWater, FluidContainerRegistry.BUCKET_VOLUME / 4));
		addMelting(dropletNether, 1000, new FluidStack(FluidRegistry.LAVA, FluidContainerRegistry.BUCKET_VOLUME / 10));
		addShaped(new ItemStack(enderPearl), new Object[] {"DDD", "DDD", "DDD", 'D', dropletEnder});
		addShaped(_(tnt), new Object[] {"DS ", "SD ", 'D', dropletDestroy, 'S', sand });

		if(FluidRegistry.getFluid("life essence") != null) {
			RecipeHelper.addMelting(dropletRegen, 100, FluidRegistry.getFluidStack("life essence", 250));
		}
	}
	
	@Override
	public void postInit() {
		super.postInit();
		Fish.addRecipes();
	}
	
	private void addFishRecipes() {
		//Food to feed a fish in a tank
		Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.ANT), 1);
		Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.MAGGOT), 2);
		Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.HOPPER), 2);
		Fishing.food.addFishFood(new ItemStack(Items.bread), 8);
		Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.WORM), 3);
		Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.BEE), 3);
		Fishing.food.addFishFood(new ItemStack(Core.materials, 1, MaterialsMeta.FISH_MEAL), 12);
		
		// Squid > Calamari
		GameRegistry.addShapelessRecipe(new ItemStack(Core.food, 1, FoodMeta.CALAMARI), new Object[] {
				new ItemStack(Items.fish, 1, Fish.squid.getID()),
				new ItemStack(Items.bowl) });
		
		//Smoked Salmon
		RecipeHelper.addSmelting(new ItemStack(Core.food, 1, FoodMeta.SMOKED_SALMON), new ItemStack(Items.fish, 1, Fish.salmon.getID()), 0.1F);

		// Cod > Fish Finger
		GameRegistry.addRecipe(new ItemStack(Core.food, 16, FoodMeta.FISH_FINGER), new Object[] { " B ", "BFB", " B ",
				Character.valueOf('F'), new ItemStack(Items.fish, 1, Fish.cod.getID()),
				Character.valueOf('B'), Items.bread });
		
		RecipeHelper.addShapeless(new ItemStack(Core.food, 1, FoodMeta.FISH_N_CUSTARD), new Object[] { 
			 new ItemStack(Core.food, 1, FoodMeta.CUSTARD), new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER),
			 new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER), new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER)
		});
		
		// Tetra > Neon Lamp
		for (int i = 0; i < 12; i++) {
			RecipeHelper.addShaped(new ItemStack(lampsOn, 8, i), new Object[] { "PGP", "GFG", "PGP", 'P', new ItemStack(Core.pearls, 1, i), 'G', "glass", 'F', new ItemStack(Items.fish, 1, Fish.tetra.getID()) });
			RecipeHelper.addShaped(new ItemStack(lampsOn, 4, i), new Object[] { "PGP", "GFG", "PGP", 'P', new ItemStack(Core.pearls, 1, i), 'G', "glass", 'F', dropletFlux });
		}
		
		//Dragonfish to Eternal Upgrades
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ETERNAL_MALE), new Object[] {"WEW", "FRF", "DWD", 
					Character.valueOf('W'), new ItemStack(Blocks.wool, 1, 11),
					Character.valueOf('E'), Blocks.emerald_block, 
					Character.valueOf('F'), new ItemStack(Items.fish, 1, Fish.dragon.getID()),
					Character.valueOf('R'), Blocks.dragon_egg, 
					Character.valueOf('D'), Items.diamond }));

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ETERNAL_FEMALE), new Object[] {"WEW", "FRF", "DWD", 
					Character.valueOf('W'), new ItemStack(Blocks.wool, 1, 6),
					Character.valueOf('E'), Blocks.emerald_block, 
					Character.valueOf('F'), new ItemStack(Items.fish, 1, Fish.dragon.getID()),
					Character.valueOf('R'), Blocks.dragon_egg, 
					Character.valueOf('D'), Items.diamond }));
		
		//Netherfish as Liquifier fuel		
		MaricultureHandlers.crucible.addFuel(new ItemStack(Items.fish, 1, Fish.nether.getID()), new FuelInfo(2000, 16, 2400));
	}
}
