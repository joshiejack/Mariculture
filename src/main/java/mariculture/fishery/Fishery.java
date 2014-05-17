package mariculture.fishery;

import java.util.Arrays;

import mariculture.Mariculture;
import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RecipeSifter;
import mariculture.api.fishery.RodType;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.items.ItemBattery;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.ItemLib;
import mariculture.core.lib.MachineMeta;
import mariculture.core.lib.MachineMultiMeta;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.TransparentMeta;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.util.Fluids;
import mariculture.fishery.blocks.BlockItemNet;
import mariculture.fishery.blocks.BlockNeonLamp;
import mariculture.fishery.items.ItemBait;
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
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class Fishery extends RegistrationModule {	
	public static Block lampsOff;
	public static Block lampsOn;

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
	public static Fluid dirt;
	
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
	public void registerBlocks() {
		lampsOff = new BlockNeonLamp(true, "lamp_on_").setBlockName("lamps.off");
		lampsOn = new BlockNeonLamp(false, "lamp_off_").setBlockName("lamps.on");
		RegistryHelper.registerBlocks(new Block[] { lampsOff, lampsOn });
		RegistryHelper.registerTiles(new Class[] { TileAutofisher.class, TileSift.class, TileIncubator.class, TileFeeder.class, TileFishTank.class });
		
		Fluids.fish_food = Core.addFluid("fishfood", fishFood, 512, FluidContainerMeta.BOTTLE_FISH_FOOD);
		Fluids.fish_oil = Core.addFluid("fishoil", fishOil, 2000, FluidContainerMeta.BOTTLE_FISH_OIL);
		Fluids.milk = Core.addFluid("milk", milk, 2000, FluidContainerMeta.BOTTLE_MILK);
		Fluids.custard = Core.addFluid("custard", custard, 2000, FluidContainerMeta.BOTTLE_CUSTARD);
		
		Core.registerVanillaBottle(Fluids.fish_food, 256, FluidContainerMeta.BOTTLE_NORMAL_FISH_FOOD);
		Core.registerVanillaBottle(Fluids.fish_oil, 1000, FluidContainerMeta.BOTTLE_NORMAL_FISH_OIL);
		Core.registerVanillaBottle(Fluids.milk, 1000, FluidContainerMeta.BOTTLE_NORMAL_MILK);
		Core.registerVanillaBottle(Fluids.custard, 1000, FluidContainerMeta.BOTTLE_NORMAL_CUSTARD);
		
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(Fluids.custard, 250), new ItemStack(Core.food, 1, FoodMeta.CUSTARD), new ItemStack(Items.bowl));
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(Fluids.milk, 1000), new ItemStack(Items.milk_bucket), new ItemStack(Items.bucket));
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

		RegistryHelper.registerItems(new Item[] { bait, rodWood, rodReed, rodTitanium, fishy, net, rodFlux, scanner });
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
		
		//Fishing Rods
		RecipeHelper.addFishingRodRecipe(new ItemStack(rodReed), Items.reeds);
		RecipeHelper.addFishingRodRecipe(new ItemStack(rodWood), new ItemStack(Core.crafting, 1, CraftingMeta.POLISHED_STICK));
		RecipeHelper.addFishingRodRecipe(new ItemStack(rodTitanium), new ItemStack(Core.crafting, 1, CraftingMeta.POLISHED_TITANIUM));
		
		//Flux Rod
		RecipeHelper.addShapedRecipe(ItemBattery.make(new ItemStack(rodFlux), 0), new Object[] {
			"  R", " RS", "B S", 'R', rodTitanium, 'S', Items.string, 'B', new ItemStack(Core.batteryTitanium, 1, OreDictionary.WILDCARD_VALUE)
		});
		
		// Log > 30000mB of Fish Oil > 45 Seconds = 1 Polished Log (or 30000mB for 8 Sticks)
		RecipeHelper.addVatItemRecipe(new ItemStack(Blocks.planks), Fluids.fish_oil, 30000, 
				new ItemStack(Core.woods, 1, WoodMeta.POLISHED_LOG), 45);
		//1 Plank = 9000mB > 30 Seconds = 1 Polished Plank (or 36000mB for 8 Sticks)
		RecipeHelper.addVatItemRecipe(new ItemStack(Blocks.planks), Fluids.fish_oil, 10000, 
				new ItemStack(Core.woods, 1, WoodMeta.POLISHED_PLANK), 30);
		//1 Stick = 5000mB > 15 Seconds or (40000mB for 8 Sticks)
		RecipeHelper.addVatItemRecipe(new ItemStack(Items.stick), Fluids.fish_oil, 5000, 
				new ItemStack(Core.crafting, 1, CraftingMeta.POLISHED_STICK), 15);
				
		//1 Polished Log = 4 Polished Planks
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.woods, 4, WoodMeta.POLISHED_PLANK), new Object[] {
			new ItemStack(Core.woods, 1, WoodMeta.POLISHED_LOG)
		});
				
		//2 Polished Planks = 4 Polished Sticks
		RecipeHelper.addShapedRecipe(new ItemStack(Core.crafting, 4, CraftingMeta.POLISHED_STICK), new Object[] {
			"S", "S", 'S', new ItemStack(Core.woods, 1, WoodMeta.POLISHED_PLANK)
		});
				
		//Polished Titanium Rod > 6500mB Fish Oil + Titanium Rod
		RecipeHelper.addVatItemRecipe(new ItemStack(Core.crafting, 1, CraftingMeta.TITANIUM_ROD), 
				Fluids.fish_oil, 6500, new ItemStack(Core.crafting, 1, CraftingMeta.POLISHED_TITANIUM), 30);
		
		/* Fishing Net, Autofisher and Sift */
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(net, 4, 0), new Object[] { "SWS", "WWW", "SWS",
					Character.valueOf('S'), "stickWood", 
					Character.valueOf('W'), Items.string }));
		
		RecipeHelper.addShapedRecipe(new ItemStack(Core.renderedMachines, 1, MachineRenderedMeta.SIFTER), new Object[] {
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
		RecipeHelper.addShapedRecipe(new ItemStack(Core.renderedMachines, 1, MachineRenderedMeta.FISH_FEEDER), new Object[] { 
			"WFW", "WCW", "WFW",  'F', new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), 
				'W', new ItemStack(Core.crafting, 1, CraftingMeta.WICKER), 'C', Blocks.chest });

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.multiMachines, 1, MachineMultiMeta.INCUBATOR_TOP), new Object[] {"DFD", "CHC", 
					Character.valueOf('F'), new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE), 
					Character.valueOf('D'), "dyeBrown", 
					Character.valueOf('C'), new ItemStack(Blocks.stained_hardened_clay, 1, 0), 
					Character.valueOf('H'), new ItemStack(Core.crafting, 1, CraftingMeta.HEATER) }));

		//Incubator Base
		RecipeHelper.addShapedRecipe(new ItemStack(Core.multiMachines, 1, MachineMultiMeta.INCUBATOR_BASE), new Object[] {
			"DBD", "CHC",
			Character.valueOf('C'), new ItemStack(Blocks.stained_hardened_clay, 1, 3),
			Character.valueOf('B'), new ItemStack(Core.batteryCopper, 1, OreDictionary.WILDCARD_VALUE),
			Character.valueOf('D'), "dyeLightBlue",
			Character.valueOf('H'), new ItemStack(Core.crafting, 1, CraftingMeta.HEATER)
		});
		
		//FishTank
		RecipeHelper.addShapedRecipe(new ItemStack(Core.tanks, 1, TankMeta.FISH), new Object[] {
			"AGA", "GFG", "AGA", 'A', "ingotAluminum", 'G', "glass",
			'F', new ItemStack(Items.fish, 1, OreDictionary.WILDCARD_VALUE)
		});
		
		//Milk + 2 Sugar = Custard
		RecipeHelper.addVatItemRecipeResultFluid(new ItemStack(Items.sugar, 2, 0), FluidRegistry.getFluidStack(Fluids.milk, 1000),
				FluidRegistry.getFluidStack(Fluids.custard, 1000), 15);
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
		Fishing.fishing.addBaitForQuality(new ItemStack(Items.bread), Arrays.asList(RodType.DIRE, RodType.OLD, RodType.FLUX));
		Fishing.fishing.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.HOPPER), Arrays.asList(RodType.OLD, RodType.GOOD, RodType.FLUX));
		Fishing.fishing.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.MAGGOT), Arrays.asList(RodType.GOOD, RodType.FLUX));
		Fishing.fishing.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.WORM), Arrays.asList(RodType.GOOD, RodType.SUPER, RodType.FLUX));
		Fishing.fishing.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.BEE), Arrays.asList(RodType.SUPER, RodType.FLUX));
		Fishing.fishing.addBaitForQuality(new ItemStack(Items.fish, 1, Fish.minnow.getID()), Arrays.asList(RodType.SUPER, RodType.FLUX));
	}
	
	private void addDropletRecipes() {
		
		// Water Droplet to Water In Liquifier		
		RecipeHelper.addMelting(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 1, 
				FluidRegistry.getFluidStack("water", FluidContainerRegistry.BUCKET_VOLUME));
		
		// Aqua Droplet to Pressurised Water
		RecipeHelper.addMelting(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_AQUA), 1, 
				FluidRegistry.getFluidStack(Fluids.hp_water, FluidContainerRegistry.BUCKET_VOLUME / 4));

		// Nether Droplet to Lava in Liquifier Only
		RecipeHelper.addMelting(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_NETHER), 1000, 
				FluidRegistry.getFluidStack("lava", FluidContainerRegistry.BUCKET_VOLUME / 10));

		// Ender Droplet to Ender Pearl
		GameRegistry.addRecipe(new ItemStack(Items.ender_pearl), new Object[] { "DDD", "DDD", "DDD", 
				Character.valueOf('D'), new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ENDER) });

		// Destruction Droplet to TNT
		GameRegistry.addRecipe(new ItemStack(Blocks.tnt), new Object[] { "DS ", "SD ", 
				Character.valueOf('D'), new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK), 
				Character.valueOf('S'), Blocks.sand });
		
		if(FluidRegistry.getFluid("life essence") != null) {
			RecipeHelper.addMelting(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_HEALTH), 100, FluidRegistry.getFluidStack("life essence", 250));
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
		
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.food, 1, FoodMeta.FISH_N_CUSTARD), new Object[] { 
			 new ItemStack(Core.food, 1, FoodMeta.CUSTARD), new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER),
			 new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER), new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER)
		});
		
		// Tetra > Neon Lamp
		for (int i = 0; i < 12; i++) {
			GameRegistry.addRecipe(new ItemStack(lampsOn, 4, i), new Object[] { "GDG", "PFP", "GRG", 
					Character.valueOf('P'), new ItemStack(Core.pearls, 1, i),
					Character.valueOf('G'), new ItemStack(Core.transparent, 1, TransparentMeta.PLASTIC),
					Character.valueOf('R'), Items.redstone, 
					Character.valueOf('D'), Items.glowstone_dust,
					Character.valueOf('F'), new ItemStack(Items.fish, 1, Fish.tetra.getID()) });
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
		MaricultureHandlers.smelter.addFuel(new ItemStack(Items.fish, 1, Fish.nether.getID()), new FuelInfo(2000, 16, 2400));
	}
}
