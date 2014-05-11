package mariculture.fishery;

import static mariculture.core.lib.Items.autofisher;
import static mariculture.core.lib.Items.baseWood;
import static mariculture.core.lib.Items.blueWool;
import static mariculture.core.lib.Items.bowl;
import static mariculture.core.lib.Items.bread;
import static mariculture.core.lib.Items.calamari;
import static mariculture.core.lib.Items.chest;
import static mariculture.core.lib.Items.copperBattery;
import static mariculture.core.lib.Items.diamond;
import static mariculture.core.lib.Items.dragonEgg;
import static mariculture.core.lib.Items.dropletAqua;
import static mariculture.core.lib.Items.dropletDestroy;
import static mariculture.core.lib.Items.dropletEarth;
import static mariculture.core.lib.Items.dropletEnder;
import static mariculture.core.lib.Items.dropletFlux;
import static mariculture.core.lib.Items.dropletFrozen;
import static mariculture.core.lib.Items.dropletNether;
import static mariculture.core.lib.Items.dropletPlant;
import static mariculture.core.lib.Items.dropletPoison;
import static mariculture.core.lib.Items.dropletRegen;
import static mariculture.core.lib.Items.dropletWater;
import static mariculture.core.lib.Items.dustMagnesium;
import static mariculture.core.lib.Items.emeraldBlock;
import static mariculture.core.lib.Items.enderPearl;
import static mariculture.core.lib.Items.eternalFemale;
import static mariculture.core.lib.Items.eternalMale;
import static mariculture.core.lib.Items.fish;
import static mariculture.core.lib.Items.fishFeeder;
import static mariculture.core.lib.Items.fishFinger;
import static mariculture.core.lib.Items.fishMeal;
import static mariculture.core.lib.Items.fishNCustard;
import static mariculture.core.lib.Items.fishTank;
import static mariculture.core.lib.Items.glowstone;
import static mariculture.core.lib.Items.grass;
import static mariculture.core.lib.Items.heating;
import static mariculture.core.lib.Items.ice;
import static mariculture.core.lib.Items.incubatorBase;
import static mariculture.core.lib.Items.incubatorTop;
import static mariculture.core.lib.Items.pearls;
import static mariculture.core.lib.Items.pinkWool;
import static mariculture.core.lib.Items.polishedLog;
import static mariculture.core.lib.Items.polishedPlank;
import static mariculture.core.lib.Items.rawFish;
import static mariculture.core.lib.Items.sand;
import static mariculture.core.lib.Items.snowball;
import static mariculture.core.lib.Items.string;
import static mariculture.core.lib.Items.thermometer;
import static mariculture.core.lib.Items.titaniumBattery;
import static mariculture.core.lib.Items.transparent;
import static mariculture.core.lib.Items.wicker;

import java.util.Arrays;

import mariculture.Mariculture;
import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureTab;
import mariculture.api.core.RecipeCasting.RecipeBlockCasting;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RecipeSifter;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.items.ItemBattery;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.BlockIds;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.Extra;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.Items;
import mariculture.core.lib.MachineMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.core.lib.RenderMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.util.FluidCustom;
import mariculture.core.util.FluidDictionary;
import mariculture.fishery.blocks.BlockItemNeonLamp;
import mariculture.fishery.blocks.BlockItemNet;
import mariculture.fishery.blocks.BlockNeonLamp;
import mariculture.fishery.blocks.BlockSift;
import mariculture.fishery.blocks.TileAutofisher;
import mariculture.fishery.blocks.TileFeeder;
import mariculture.fishery.blocks.TileFishTank;
import mariculture.fishery.blocks.TileIncubator;
import mariculture.fishery.blocks.TileSift;
import mariculture.fishery.items.ItemBait;
import mariculture.fishery.items.ItemFishy;
import mariculture.fishery.items.ItemFishyFood;
import mariculture.fishery.items.ItemFluxRod;
import mariculture.fishery.items.ItemRod;
import mariculture.fishery.items.ItemScanner;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class Fishery extends RegistrationModule {	
	@Deprecated
	public static FishSpecies nether;
	@Deprecated
	public static FishSpecies glow;
	@Deprecated
	public static FishSpecies blaze;
	@Deprecated
	public static FishSpecies night;
	@Deprecated
	public static FishSpecies ender;
	@Deprecated
	public static FishSpecies dragon;
	@Deprecated
	public static FishSpecies minnow;
	@Deprecated
	public static FishSpecies salmon;
	@Deprecated
	public static FishSpecies bass;
	@Deprecated
	public static FishSpecies tetra;
	@Deprecated
	public static FishSpecies catfish;
	@Deprecated
	public static FishSpecies piranha;
	@Deprecated
	public static FishSpecies cod;
	@Deprecated
	public static FishSpecies perch;
	@Deprecated
	public static FishSpecies tuna;
	@Deprecated
	public static FishSpecies stingRay;
	@Deprecated
	public static FishSpecies mantaRay;
	@Deprecated
	public static FishSpecies electricRay;
	@Deprecated
	public static FishSpecies damsel;
	@Deprecated
	public static FishSpecies angel;
	@Deprecated
	public static FishSpecies puffer;
	@Deprecated
	public static FishSpecies squid;
	@Deprecated
	public static FishSpecies jelly;
	@Deprecated
	public static FishSpecies manOWar;
	@Deprecated
	public static FishSpecies gold;
	@Deprecated
	public static FishSpecies siamese;
	@Deprecated
	public static FishSpecies koi;
	@Deprecated
	public static FishSpecies butterfly;
	@Deprecated
	public static FishSpecies tang;
	@Deprecated
	public static FishSpecies clown;
	
	public static Block siftBlock;
	public static Block lampsOff;
	public static Block lampsOn;

	public static Item bait;
	public static Item rodWood;
	public static Item rodTitanium;
	public static Item rodReed;
	public static Item rodFlux;
	public static Item fishy;
	public static Item fishyFood;
	public static Item net;
	public static Item scanner;

	public static Fluid fishFood;
	public static Fluid fishOil;
	public static Fluid milk;
	public static Fluid custard;
	public static Fluid dirt;

	@Override
	public void registerHandlers() {
		Fishing.bait = new BaitHandler();
		Fishing.quality = new RodQualityHandler();
		Fishing.mutation = new FishMutationHandler();
		Fishing.fishHelper = new FishHelper();
		Fishing.loot = new LootHandler();
		Fishing.rodHandler = new RodRightClickHandler();
		Fishing.food = new FishFoodHandler();
		Fishing.sifter = new SifterHandler();
		MinecraftForge.EVENT_BUS.register(new FisheryEventHandler());
	}

	@Override
	public void registerBlocks() {
		siftBlock = new BlockSift(BlockIds.sift).setStepSound(Block.soundWoodFootstep).setHardness(1F).setUnlocalizedName("siftBlock");
		lampsOff = new BlockNeonLamp(BlockIds.lampsOff, true).setUnlocalizedName("lampsOff").setLightOpacity(0);
		lampsOn = new BlockNeonLamp(BlockIds.lampsOn, false).setUnlocalizedName("lampsOn").setLightOpacity(0);

		Item.itemsList[BlockIds.lampsOn] = new BlockItemNeonLamp(BlockIds.lampsOn - 256, lampsOn).setUnlocalizedName("lampsOn");
		Item.itemsList[BlockIds.lampsOff] = new BlockItemNeonLamp(BlockIds.lampsOff - 256, lampsOff).setUnlocalizedName("lampsOff");

		GameRegistry.registerBlock(siftBlock, "Mariculture_siftBlock");
		GameRegistry.registerTileEntity(TileAutofisher.class, "tileEntityAutofisher");
		GameRegistry.registerTileEntity(TileSift.class, "tileEntitySift");
		GameRegistry.registerTileEntity(TileIncubator.class, "tileIncubator");
		GameRegistry.registerTileEntity(TileFeeder.class, "tileFeeder");
		GameRegistry.registerTileEntity(TileFishTank.class, "tileFishTank");

		MinecraftForge.setBlockHarvestLevel(Core.machines, MachineMeta.INCUBATOR_BASE, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.machines, MachineMeta.INCUBATOR_TOP, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.rendered, RenderMeta.AIR_PUMP, "pickaxe", 1);
		MinecraftForge.setBlockHarvestLevel(Core.wood, WoodMeta.POLISHED_LOG, "axe", 0);
		MinecraftForge.setBlockHarvestLevel(Core.wood, WoodMeta.POLISHED_PLANK, "axe", 0);

		RegistryHelper.register(new Object[] { siftBlock, lampsOff, lampsOn });
		
		FluidDictionary.fish_food = Core.addFluid("food.fish", fishFood, 512, FluidContainerMeta.BOTTLE_FISH_FOOD);
		FluidDictionary.fish_oil = Core.addFluid("oil.fish", fishOil, 2000, FluidContainerMeta.BOTTLE_FISH_OIL);
		FluidDictionary.milk = Core.addFluid("milk", milk, 2000, FluidContainerMeta.BOTTLE_MILK);
		FluidDictionary.custard = Core.addFluid("custard", custard, 2000, FluidContainerMeta.BOTTLE_CUSTARD);
		
		//Add Molten dirt
		dirt = new FluidCustom("molten.dirt", "Molten Dirt", Block.dirt.blockID, 0).setUnlocalizedName("dirt");
		FluidRegistry.registerFluid(dirt);
		
		Core.registerVanillaBottle(FluidDictionary.fish_food, 256, FluidContainerMeta.BOTTLE_NORMAL_FISH_FOOD);
		Core.registerVanillaBottle(FluidDictionary.fish_oil, 1000, FluidContainerMeta.BOTTLE_NORMAL_FISH_OIL);
		Core.registerVanillaBottle(FluidDictionary.milk, 1000, FluidContainerMeta.BOTTLE_NORMAL_MILK);
		Core.registerVanillaBottle(FluidDictionary.custard, 1000, FluidContainerMeta.BOTTLE_NORMAL_CUSTARD);
		
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(FluidDictionary.custard, 250), 
				new ItemStack(Core.food, 1, FoodMeta.CUSTARD), new ItemStack(Item.bowlEmpty));
		
		FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(FluidDictionary.milk, 1000), 
				new ItemStack(Item.bucketMilk), new ItemStack(Item.bucketEmpty));
	}

	@Override
	public void registerItems() {
		bait = new ItemBait(ItemIds.bait).setUnlocalizedName("bait");
		rodReed = new ItemRod(ItemIds.rodReed, RodQuality.OLD).setUnlocalizedName("rodReed");
		rodWood = new ItemRod(ItemIds.rodWood, RodQuality.GOOD).setUnlocalizedName("rodWood");
		rodTitanium = new ItemRod(ItemIds.rodTitanium, RodQuality.SUPER).setUnlocalizedName("rodTitanium");
		rodFlux = new ItemFluxRod(ItemIds.rodFlux, RodQuality.FLUX).setUnlocalizedName("rodFlux");
		fishy = new ItemFishy(ItemIds.fishy).setUnlocalizedName("fishy").setCreativeTab(MaricultureTab.tabFish);
		fishyFood = new ItemFishyFood(ItemIds.fishyFood).setUnlocalizedName("fishyFood");
		net = new BlockItemNet(ItemIds.net).setUnlocalizedName("net");
		scanner = new ItemScanner(ItemIds.scanner).setUnlocalizedName("scanner");

		RegistryHelper.register(new Object[] { bait, rodReed, rodWood, rodTitanium, fishy, fishyFood, net, rodFlux, scanner });
		
	}

	@Override
	public void registerOther() {
		registerEntities();
		registerFish();

		MaricultureTab.tabFish.icon = Fishing.fishHelper.makePureFish(Fish.cod);
	}
	
	private void registerEntities() {
		EntityRegistry.registerModEntity(EntityBass.class, "BassBomb", EntityIds.BASS, Mariculture.instance, 80, 3, true);
		EntityRegistry.registerModEntity(EntityFishing.class, "NewFishing", EntityIds.FISHING, Mariculture.instance, 80, 3, true);
	}
	
	private void registerFish() {
		Fish.init();
		
		/** Deprecated fishies kept for compatibility **/
		nether = Fish.nether;
		glow = Fish.glow;
		blaze = Fish.blaze;
		night = Fish.night;
		ender = Fish.ender;
		dragon = Fish.dragon;
		minnow = Fish.minnow;
		salmon = Fish.salmon;
		bass = Fish.bass;
		tetra = Fish.tetra;
		catfish = Fish.catfish;
		piranha = Fish.piranha;
		cod = Fish.cod;
		perch = Fish.perch;
		tuna = Fish.tuna;
		stingRay = Fish.stingRay;
		mantaRay = Fish.mantaRay;
		electricRay = Fish.electricRay;
		damsel = Fish.damsel;
		angel = Fish.angel;
		puffer = Fish.puffer;
		squid = Fish.squid;
		jelly = Fish.jelly;
		manOWar = Fish.manOWar;
		gold = Fish.gold;
		siamese = Fish.siamese;
		koi = Fish.koi;
		butterfly = Fish.butterfly;
		tang = Fish.tang;
		clown = Fish.clown;
	}

	@Override
	public void registerRecipes() {
		addBait();
		addDropletRecipes();
		addFishRecipes();
		FishingLoot.add();
		
		RecipeHelper.addFishingRodRecipe(new ItemStack(rodReed), Item.reed);
		RecipeHelper.addFishingRodRecipe(new ItemStack(rodWood), new ItemStack(Core.crafting, 1, CraftingMeta.POLISHED_STICK));
		RecipeHelper.addFishingRodRecipe(new ItemStack(rodTitanium), new ItemStack(Core.crafting, 1, CraftingMeta.POLISHED_TITANIUM));
		RecipeHelper.addShapedRecipe(ItemBattery.make(new ItemStack(rodFlux), 0), new Object[] {"  R", " RS", "B S", 'R', rodTitanium, 'S', string, 'B', titaniumBattery});
		RecipeHelper.addShapedRecipe(new ItemStack(scanner), new Object[] {"WPE", "NFR", "JBO", 'N', dropletNether, 'P', pearls, 'W', dropletWater, 'R', dropletEarth, 'F', fish, 'O', dropletEnder, 'E', dropletFrozen, 'B', copperBattery, 'J', dropletPoison});
		if(!Extra.DISABLE_DIRT_CRAFTING) MaricultureHandlers.casting.addRecipe(new RecipeBlockCasting(new FluidStack(dirt, 1000), new ItemStack(Block.dirt)));
		RecipeHelper.addMelting(new ItemStack(Items.dirt), 333, new FluidStack(dirt, 1000));
		RecipeHelper.addVatItemRecipe(new ItemStack(Block.wood), FluidDictionary.fish_oil, 30000, polishedLog, 45);
		RecipeHelper.addVatItemRecipe(new ItemStack(Block.planks), FluidDictionary.fish_oil, 10000, polishedPlank, 30);
		RecipeHelper.addVatItemRecipe(new ItemStack(Item.stick), FluidDictionary.fish_oil, 5000, new ItemStack(Core.crafting, 1, CraftingMeta.POLISHED_STICK), 15);
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.wood, 4, WoodMeta.POLISHED_PLANK), new Object[] {polishedLog});
		RecipeHelper.addShapedRecipe(new ItemStack(Core.crafting, 4, CraftingMeta.POLISHED_STICK), new Object[] {"S", "S", 'S', polishedPlank});
		RecipeHelper.addVatItemRecipe(new ItemStack(Core.crafting, 1, CraftingMeta.TITANIUM_ROD), FluidDictionary.fish_oil, 6500, new ItemStack(Core.crafting, 1, CraftingMeta.POLISHED_TITANIUM), 30);
		RecipeHelper.addShapedRecipe(new ItemStack(net, 4, 0), new Object[] { "SWS", "WWW", "SWS", 'S', "stickWood", 'W', string });
		RecipeHelper.addShapedRecipe(new ItemStack(siftBlock), new Object[] { "PNP", "S S", 'S', "stickWood", 'P', "plankWood", 'N', net });
		RecipeHelper.addShapedRecipe(autofisher, new Object[] { " F ", "RPR", "WBW", 'W', "logWood", 'R', rodWood, 'F', fish, 'B', baseWood, 'P', "plankWood"});
		RecipeHelper.addShapedRecipe(fishFeeder, new Object[] { "WFW", "WCW", "WFW", 'F', fish, 'W', wicker, 'C', chest });
		RecipeHelper.addShapedRecipe(incubatorTop, new Object[] {"DFD", "CHC", 'F', fish, 'D', "dyeBrown", 'C', new ItemStack(Block.stainedClay, 1, 0), 'H', heating});
		RecipeHelper.addShapedRecipe(incubatorBase, new Object[] {"DBD", "CHC", 'C', new ItemStack(Block.stainedClay, 1, 3), 'B', copperBattery, 'D', "dyeLightBlue", 'H', heating});
		RecipeHelper.addShapedRecipe(fishTank, new Object[] {"AGA", "GFG", "AGA", 'A', "ingotAluminum", 'G', "glass", 'F', fish});
		RecipeHelper.addVatItemRecipeResultFluid(new ItemStack(Item.sugar, 2, 0), FluidRegistry.getFluidStack(FluidDictionary.milk, 1000), FluidRegistry.getFluidStack(FluidDictionary.custard, 1000), 15);
		RecipeHelper.addVatItemRecipe(dropletFlux, "water", 1000, dustMagnesium, 25);
		RecipeHelper.addShapelessRecipe(thermometer, new Object[] { Item.compass, fish });
	}

	private void addBait() {
		Fishing.bait.addBait(new ItemStack(bait, 1, BaitMeta.ANT), 10);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.ANT), new ItemStack(Block.dirt), 2, 3, 40));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.ANT), new ItemStack(Block.grass), 1, 2, 50));
		
		Fishing.bait.addBait(new ItemStack(Item.bread), 30);
		
		Fishing.bait.addBait(new ItemStack(bait, 1, BaitMeta.HOPPER), 40);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Block.grass), 1, 2, 35));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Block.sapling), 2, 3, 35));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Block.leaves), 1, 2, 15));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Block.tallGrass, 0, 1), 4, 5, 45));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Block.tallGrass, 0, 2), 2, 3, 40));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Block.tallGrass, 0, 0), 1, 2, 10));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Block.plantYellow), 2, 5, 20));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Block.plantRed), 3, 4, 25));
		
		Fishing.bait.addBait(new ItemStack(bait, 1, BaitMeta.MAGGOT), 55);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Item.rottenFlesh), 1, 2, 60));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Item.beefRaw), 14, 22, 20));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Item.chickenRaw), 6, 14, 30));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Item.porkRaw), 10, 18, 25));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), Items.zombie, 8, 15, 80));
		
		Fishing.bait.addBait(new ItemStack(bait, 1, BaitMeta.BEE), 70);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.BEE), new ItemStack(Block.plantYellow), 2, 3, 25));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.BEE), new ItemStack(Block.plantRed), 1, 2, 30));
		
		Fishing.bait.addBait(new ItemStack(bait, 1, BaitMeta.WORM), 75);
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.WORM), new ItemStack(Item.appleRed), 1, 3, 15));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.WORM), new ItemStack(Block.dirt), 2, 3, 10));
		Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.WORM), new ItemStack(Block.grass), 3, 5, 20));
		
		Fishing.bait.addBait(new ItemStack(fishyFood, 1, Fish.minnow.getID()), 100);
		
		Fishing.quality.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.ANT), Arrays.asList(RodQuality.OLD, RodQuality.FLUX));
		Fishing.quality.addBaitForQuality(new ItemStack(Item.bread), Arrays.asList(RodQuality.OLD, RodQuality.FLUX));
		Fishing.quality.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.HOPPER), Arrays.asList(RodQuality.OLD, RodQuality.GOOD, RodQuality.FLUX));
		Fishing.quality.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.MAGGOT), Arrays.asList(RodQuality.GOOD, RodQuality.FLUX));
		Fishing.quality.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.WORM), Arrays.asList(RodQuality.GOOD, RodQuality.SUPER, RodQuality.FLUX));
		Fishing.quality.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.BEE), Arrays.asList(RodQuality.SUPER, RodQuality.FLUX));
		Fishing.quality.addBaitForQuality(new ItemStack(fishyFood, 1, Fish.minnow.getID()), Arrays.asList(RodQuality.SUPER, RodQuality.FLUX));
	}
	
	private void addDropletRecipes() {
		if(!Extra.DISABLE_GRASS) RecipeHelper.addShapedRecipe(new ItemStack(grass), new Object[] {"HHH", "EEE", "EEE", 'H', dropletPlant, 'E', dropletEarth});
		RecipeHelper.add2x2Recipe(new ItemStack(snowball), dropletFrozen);
		RecipeHelper.add3x3Recipe(new ItemStack(ice), dropletFrozen);
		RecipeHelper.addMelting(dropletEarth, 333, new FluidStack(dirt, 100));
		RecipeHelper.addMelting(dropletWater, 1, FluidRegistry.getFluidStack("water", FluidContainerRegistry.BUCKET_VOLUME));
		RecipeHelper.addMelting(dropletAqua, 1, FluidRegistry.getFluidStack(FluidDictionary.hp_water, FluidContainerRegistry.BUCKET_VOLUME / 4));
		RecipeHelper.addMelting(dropletNether, 1000, FluidRegistry.getFluidStack("lava", FluidContainerRegistry.BUCKET_VOLUME / 10));
		RecipeHelper.addShapedRecipe(new ItemStack(enderPearl), new Object[] {"DDD", "DDD", "DDD", 'D', dropletEnder});
		RecipeHelper.addShapedRecipe(new ItemStack(Block.tnt), new Object[] {"DS ", "SD ", 'D', dropletDestroy, 'S', sand });
		
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
		ItemStack cod = new ItemStack(fishyFood, 1, Fish.cod.getID());
		ItemStack dragon = new ItemStack(fishyFood, 1, Fish.dragon.getID());
		ItemStack squid = new ItemStack(fishyFood, 1, Fish.squid.getID());
		ItemStack tetra = new ItemStack(fishyFood, 1, Fish.tetra.getID());
		
		//Food to feed a fish in a tank
		Fishing.food.addFishFood(new ItemStack(bait.itemID, 1, BaitMeta.ANT), 1);
		Fishing.food.addFishFood(new ItemStack(bait.itemID, 1, BaitMeta.MAGGOT), 2);
		Fishing.food.addFishFood(new ItemStack(bait.itemID, 1, BaitMeta.HOPPER), 2);
		Fishing.food.addFishFood(new ItemStack(Item.bread), 8);
		Fishing.food.addFishFood(new ItemStack(bait.itemID, 1, BaitMeta.WORM), 3);
		Fishing.food.addFishFood(new ItemStack(bait.itemID, 1, BaitMeta.BEE), 3);
		Fishing.food.addFishFood(new ItemStack(Core.materials.itemID, 1, MaterialsMeta.FISH_MEAL), 12);
		
		RecipeHelper.addShapelessRecipe(calamari, new Object[] {squid, bowl });
		RecipeHelper.addSmelting(fishyFood.itemID, Fish.salmon.getID(), new ItemStack(Core.food, 1, FoodMeta.SMOKED_SALMON), 0.1F);
		RecipeHelper.addSmelting(fishyFood.itemID, Fish.minecraft.getID(), new ItemStack(Item.fishCooked, 2, 0), 0.25F);
		
		RecipeHelper.addShapedRecipe(new ItemStack(Core.food, 16, FoodMeta.FISH_FINGER), new Object[] { " B ", "BFB", " B ", 'F', cod, 'B', bread });
		RecipeHelper.addShapelessRecipe(fishNCustard, new Object[] {Items.custard, fishFinger, fishFinger, fishFinger});

		for (int i = 0; i < 12; i++) {
			RecipeHelper.addShapedRecipe(new ItemStack(lampsOn, 4, i), new Object[] {" D ", "PGP", " F ", 'P', pearls, 'G', transparent, 'F', dropletFlux, 'D', glowstone});
		}
		
		RecipeHelper.addShapedRecipe(eternalMale, new Object[] {"WEW", "FRF", "DWD", 'W', blueWool, 'E', emeraldBlock, 'F', dragon, 'R', dragonEgg, 'D', diamond});
		RecipeHelper.addShapedRecipe(eternalFemale, new Object[] {"WEW", "FRF", "DWD", 'W', pinkWool, 'E', emeraldBlock, 'F', dragon, 'R', dragonEgg, 'D', diamond});
		
		MaricultureHandlers.smelter.addFuel(new ItemStack(fishyFood, 1, Fish.nether.getID()), new FuelInfo(2000, 16, 2400));
		CraftingManager.getInstance().getRecipeList().add(new ShapelessFishRecipe(new ItemStack(Core.food, 1, FoodMeta.CAVIAR), new ItemStack(fishy)));
		RecipeHelper.addMelting(new ItemStack(rawFish), 180, FluidRegistry.getFluidStack(FluidDictionary.fish_oil, 100));
		RecipeHelper.addShapelessRecipe(fishMeal, new Object[] { rawFish });
	}
	
	@Deprecated
	public static boolean isActive = Modules.isActive(Modules.worldplus);
}
