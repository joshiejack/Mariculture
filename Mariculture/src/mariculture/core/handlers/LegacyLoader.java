package mariculture.core.handlers;

import java.util.logging.Level;

import mariculture.core.Config;
import mariculture.core.lib.BlockIds;
import mariculture.core.lib.Compatibility;
import mariculture.core.lib.EnchantIds;
import mariculture.core.lib.Extra;
import mariculture.core.lib.ItemIds;
import mariculture.core.lib.MachineSpeeds;
import mariculture.core.lib.Modules;
import mariculture.core.lib.OreGeneration;
import mariculture.core.lib.WorldGeneration;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.FMLLog;

public class LegacyLoader {

	private static final String CATEGORY_COMPAT = "compatability";
	private static final String CATEGORY_MODULES = "modules";
	private static final String CATEGORY_ENCHANT = "enchantments";
	private static final String CATEGORY_EXTRA = "extra";
	private static final String CATEGORY_SPEEDS = "machinespeeds";
	private static final String CATEGORY_ORE_GEN = "oregen";
	private static final String CATEGORY_WORLD_GEN = "worldgen";
	
	@Deprecated
	public static void load(Configuration config) {
		config.addCustomCategoryComment(CATEGORY_MODULES, "Turn on and off modules here, Disabling world will not disable generation of ores or oysters, but will turn off coral and kelp generation");
		config.addCustomCategoryComment(CATEGORY_WORLD_GEN, "Turn on and off world generation options here");
		config.addCustomCategoryComment(CATEGORY_SPEEDS, "Here you can adjust the speeds of all the machines, take note that if debug mode is on, these numbers are not called.");

		try {
			config.load();
			Config.legacy = config.get(CATEGORY_EXTRA, "legacyConfigInit", true).getBoolean(true);
			if(Config.legacy) {
				Modules.core.setActive(true);
				Modules.diving.setActive(config.get(CATEGORY_MODULES, "diving", true).getBoolean(true));
				Modules.factory.setActive(config.get(CATEGORY_MODULES, "factory", true).getBoolean(true));
				Modules.fishery.setActive(config.get(CATEGORY_MODULES, "fishery", true).getBoolean(true));
				Modules.magic.setActive(config.get(CATEGORY_MODULES, "magic", true).getBoolean(true));
				Modules.sealife.setActive(false);
				Modules.transport.setActive(config.get(CATEGORY_MODULES, "transport", true).getBoolean(true));
				Modules.world.setActive(config.get(CATEGORY_MODULES, "worldPlus", true).getBoolean(true));
				
                Compatibility.BLACKLIST = config.get(CATEGORY_EXTRA, "dictionaryBlacklist", Extra.BLACKLIST_DEFAULT).getStringList();
                Compatibility.EXCEPTIONS = config.get(CATEGORY_EXTRA, "dictionaryExceptions", Extra.EXCEPTIONS_DEFAULT).getStringList();
				
				Extra.HARDCORE_DIVING = config.get(CATEGORY_EXTRA, "hardcoreDivingSetting", 0).getInt(0);
				Extra.ACTIVATE_PUMP = config.get(CATEGORY_EXTRA, "manualPoweredPump", true).getBoolean(true);
				Extra.REDSTONE_PUMP = config.get(CATEGORY_EXTRA, "redstonePoweredPump", false).getBoolean(false);
				Extra.BUILDCRAFT_PUMP = config.get(CATEGORY_EXTRA, "buildcraftPoweredPump", true).getBoolean(true);
				Extra.REFRESH_CLIENT_RATE = config.get(CATEGORY_EXTRA, "clientRefreshRate", 30).getInt(30);
				Extra.DEBUG_ON = config.get(CATEGORY_EXTRA, "debugModeOn", false).getBoolean(false);
				Extra.PEARL_GEN_SPEED = config.get(CATEGORY_EXTRA, "oysterPearlGenRate", 6000).getInt(6000);
				Extra.PEARL_GEN_CHANCE = config.get(CATEGORY_EXTRA, "oysterPearlGenBaseChance", 3).getInt(3);
				Extra.METAL_RATE = config.get(CATEGORY_EXTRA, "metalNuggetMicroBucketValue", 16).getInt(16);
				Extra.FLUDD_WATER_ON = config.get(CATEGORY_EXTRA, "fluddWaterAnimationsOn", true).getBoolean(true);
				Extra.RIVER_BIOMES = config.get(CATEGORY_EXTRA, "biomesRiver", Extra.RIVERS_DEFAULT).getIntList();
				Extra.OCEAN_BIOMES = config.get(CATEGORY_EXTRA, "biomesOcean", Extra.OCEANS_DEFAULT).getIntList();
				Extra.bait0 = config.get(CATEGORY_EXTRA, "baitQualityZeroChance", 20).getInt(20);
				Extra.bait1 = config.get(CATEGORY_EXTRA, "baitQualityOneChance", 16).getInt(16);
				Extra.bait2 = config.get(CATEGORY_EXTRA, "baitQualityTwoChance", 11).getInt(11);
				Extra.bait3 = config.get(CATEGORY_EXTRA, "baitQualityThreeChance", 6).getInt(6);
				Extra.bait4 = config.get(CATEGORY_EXTRA, "baitQualityFourChance", 3).getInt(3);
				Extra.bait5 = config.get(CATEGORY_EXTRA, "baitQualityFiveChance", 1).getInt(1);
				Extra.CORAL_SPREAD_CHANCE = config.get(CATEGORY_EXTRA, "coralSpreadSpeed", 75).getInt(75);
				Extra.KELP_SPREAD_CHANCE = config.get(CATEGORY_EXTRA, "kelpSpreadMossSpeed", 65).getInt(65);
				Extra.KELP_GROWTH_CHANCE = config.get(CATEGORY_EXTRA, "kelpGrowthSpeed", 80).getInt(80);
				Extra.ENABLE_ENDER_SPAWN = config.get(CATEGORY_EXTRA, "enableEnderDragonSpawn", true).getBoolean(true);
				
				config.get(CATEGORY_EXTRA, "hardcoreDivingSetting", 0).comment = "This is how much air your player loses as extra (makes you breathe underwater for less time without breathing gear)";
				config.get(CATEGORY_EXTRA, "manualPoweredPump", true).comment = "Whether you can activate the air pump by right clicking on it";
				config.get(CATEGORY_EXTRA, "redstonePoweredPump", true).comment = "Whether you can activate the air pump by supplying it with a redstone signal";
				config.get(CATEGORY_EXTRA, "buildcraftPoweredPump", true).comment = "Whether you can activate the air pump by supplying it with buildcraft power";
				config.get(CATEGORY_EXTRA, "clientRefreshRate", 30).comment = "How often an update should be sent to the client in minecraft ticks";
				config.get(CATEGORY_EXTRA, "debugModeOn", true).comment = "Turning this on could cause some issues, mainly used for modding, making machines faster etc";
				config.get(CATEGORY_EXTRA, "oysterPearlGenRate", 6000).comment = "How many ticks until there is a chance for a pearl to be generated, lower = more often";
				config.get(CATEGORY_EXTRA, "oysterPearlGenBaseChance", 30).comment = "The chance that an oyster will generate a pearl, lower = better chance";
				config.get(CATEGORY_EXTRA, "metalNuggetMicroBucketValue", 100).comment = "This is how many microbuckets a nugget is worth, default is 100";
				config.get(CATEGORY_EXTRA, "fluddWaterAnimationsOn", true).comment = "Turn this off and the fludd will no longer produce the water effects";
				config.get(CATEGORY_EXTRA, "dictionaryBlacklist", Extra.BLACKLIST_DEFAULT).comment = "Place Ore Dictionary Names of stuff you do not wish to be able to convert with the dictionary block";
				config.get(CATEGORY_EXTRA, "dictionaryExceptions", Extra.EXCEPTIONS_DEFAULT).comment = "Place names that are practically equivalent and should be converted between each other by dictionary, format - item1: item2";
				config.get(CATEGORY_EXTRA, "biomesRiver", Extra.RIVERS_DEFAULT).comment = "The biomes ID's that are considered River Biomes";
				config.get(CATEGORY_EXTRA, "biomesOcean", Extra.OCEANS_DEFAULT).comment = "The biomes ID's that are considered Ocean Biomes(no need to add biomes o plenty, they are counted by default)";
				
				MachineSpeeds.autofisher = config.get(CATEGORY_SPEEDS, "autofisher", 500).getInt(500);
				MachineSpeeds.dictionary = config.get(CATEGORY_SPEEDS, "autodictionary", 1).getInt(1);
				MachineSpeeds.feeder = config.get(CATEGORY_SPEEDS, "fishfeeder", 200).getInt(200);
				MachineSpeeds.incubator = config.get(CATEGORY_SPEEDS, "incubator", 400).getInt(400);
				MachineSpeeds.liquifier = config.get(CATEGORY_SPEEDS, "smelter", 40000).getInt(40000);
				MachineSpeeds.net = config.get(CATEGORY_SPEEDS, "fishingNet", 1000).getInt(1000);
				MachineSpeeds.sawmill = config.get(CATEGORY_SPEEDS, "sawmill", 500).getInt(500);
				MachineSpeeds.settler = config.get(CATEGORY_SPEEDS, "freezer", 60000).getInt(60000);
	
				OreGeneration.BAUXITE_ON = config.get(CATEGORY_ORE_GEN, "bauxiteGenEnabled", true).getBoolean(true);
				OreGeneration.BAUXITE_TOTAL = config.get(CATEGORY_ORE_GEN, "bauxiteTotalNumVeins", 20).getInt(20);
				OreGeneration.BAUXITE_VEIN = config.get(CATEGORY_ORE_GEN, "bauxiteMaxVeinSize", 8).getInt(8);
				OreGeneration.BAUXITE_MIN = config.get(CATEGORY_ORE_GEN, "bauxiteMinHeight", 60).getInt(60); 
				OreGeneration.BAUXITE_MAX = config.get(CATEGORY_ORE_GEN, "bauxiteMaxHeight", 256).getInt(256); 
				OreGeneration.COPPER_ON = config.get(CATEGORY_ORE_GEN, "copperGenEnabled", false).getBoolean(false);
				OreGeneration.COPPER_TOTAL = config.get(CATEGORY_ORE_GEN, "copperTotalNumVeins", 8).getInt(8);
				OreGeneration.COPPER_TOTAL = config.get(CATEGORY_ORE_GEN, "copperMaxVeinSize", 7).getInt(7);
				OreGeneration.COPPER_MIN = config.get(CATEGORY_ORE_GEN, "copperMinHeight", 1).getInt(1); 
				OreGeneration.COPPER_MAX = config.get(CATEGORY_ORE_GEN, "copperMaxHeight", 64).getInt(64); 
				OreGeneration.RUTILE = config.get(CATEGORY_ORE_GEN, "rutileGenEnabled", true).getBoolean(true);
				OreGeneration.RUTILE_CHANCE = config.get(CATEGORY_ORE_GEN, "rutileGenChance", 128).getInt(128);
				OreGeneration.LIMESTONE = config.get(CATEGORY_ORE_GEN, "limestoneGenEnabled", true).getBoolean(true);
				OreGeneration.LIMESTONE_CHANCE = config.get(CATEGORY_ORE_GEN, "limestoneGenChance", 3).getInt(3);
				OreGeneration.LIMESTONE_VEIN = config.get(CATEGORY_ORE_GEN, "limestoneMaxVeinSize", 48).getInt(48);
				OreGeneration.NATURAL_GAS_ON = config.get(CATEGORY_ORE_GEN, "naturalgasGenEnabled", true).getBoolean(true);
				OreGeneration.NATURAL_GAS_CHANCE = config.get(CATEGORY_ORE_GEN, "naturalgasGenChance", 20).getInt(20);
				OreGeneration.NATURAL_GAS_VEIN = config.get(CATEGORY_ORE_GEN, "naturalgasMaxVeinSize", 64).getInt(64);
				OreGeneration.NATURAL_GAS_MIN = config.get(CATEGORY_ORE_GEN, "naturalGasMinHeight", 16).getInt(16); 
				OreGeneration.NATURAL_GAS_MAX = config.get(CATEGORY_ORE_GEN, "naturalGasMaxHeight", 26).getInt(26); 
	
				WorldGeneration.CORAL_CHANCE = config.get(CATEGORY_WORLD_GEN, "coralChance", 32).getInt(32);
				WorldGeneration.CORAL_DEPTH = config.get(CATEGORY_WORLD_GEN, "coralDepth", 20).getInt(20);
				WorldGeneration.KELP_CHANCE = config.get(CATEGORY_WORLD_GEN, "kelpChance", 450).getInt(450);
				WorldGeneration.KELP_DEPTH = config.get(CATEGORY_WORLD_GEN, "kelpDepth", 30).getInt(30);
				WorldGeneration.KELP_HEIGHT = config.get(CATEGORY_WORLD_GEN, "kelpHeight", 25).getInt(25);
				WorldGeneration.KELP_PATCH_DENSITY = config.get(CATEGORY_WORLD_GEN, "kelpSingleDensity", 10).getInt(10);
				WorldGeneration.KELP_FOREST_DENSITY = config.get(CATEGORY_WORLD_GEN, "kelpForestDensity", 25).getInt(25);
				WorldGeneration.KELP_CHEST_CHANCE = config.get(CATEGORY_WORLD_GEN, "kelpForestChestChance", 1024).getInt(1024);
				WorldGeneration.DEEP_OCEAN = config.get(CATEGORY_WORLD_GEN, "deepOceanEnabled", true).getBoolean(true);
				WorldGeneration.WATER_CAVES = config.get(CATEGORY_WORLD_GEN, "waterCaves", false).getBoolean(false);
				WorldGeneration.WATER_RAVINES = config.get(CATEGORY_WORLD_GEN, "waterRavines", true).getBoolean(true);
				WorldGeneration.RAVINE_CHANCE = config.get(CATEGORY_WORLD_GEN, "waterRavineChance", 25).getInt(25);
				WorldGeneration.NO_MINESHAFTS = config.get(CATEGORY_WORLD_GEN, "noMineshaftsInOcean", true).getBoolean(true);
	
				BlockIds.coral = config.getBlock("coral", BlockIds.dft_coral).getInt(BlockIds.dft_coral);
				BlockIds.ores = config.getBlock("ores", BlockIds.dft_Ores).getInt(BlockIds.dft_Ores);
				BlockIds.util = config.getBlock("utility", BlockIds.dft_Util).getInt(BlockIds.dft_Util);
				BlockIds.oyster = config.getBlock("oyster", BlockIds.dft_oyster).getInt(BlockIds.dft_oyster);
				BlockIds.sift = config.getBlock("sift", BlockIds.dft_sift).getInt(BlockIds.dft_sift);
				BlockIds.doubleBlocks = config.getBlock("doubleBlocks", BlockIds.dft_doubleBlocks).getInt(BlockIds.dft_doubleBlocks);
				BlockIds.singleBlocks = config.getBlock("singleBlocks", BlockIds.dft_singleBlocks).getInt(BlockIds.dft_singleBlocks);
				BlockIds.limestoneStairs = config.getBlock("limestoneStairs", BlockIds.dft_limestoneStairs).getInt(BlockIds.dft_limestoneStairs);
				BlockIds.limestoneBrickStairs = config.getBlock("limestoneBrickStairs", BlockIds.dft_limestoneBrickStairs).getInt(BlockIds.dft_limestoneBrickStairs);
				BlockIds.limestoneSmoothStairs = config.getBlock("limestoneSmoothStairs", BlockIds.dft_limestoneSmoothStairs).getInt(BlockIds.dft_limestoneSmoothStairs);
				BlockIds.limestoneChiseledStairs = config.getBlock("limestoneChiseledStairs", BlockIds.dft_limestoneChiseledStairs).getInt(BlockIds.dft_limestoneChiseledStairs);
				BlockIds.limestoneSlabs = config.getBlock("limestoneSlabs", BlockIds.dft_limestoneSlabs).getInt(BlockIds.dft_limestoneSlabs);
				BlockIds.limestoneDoubleSlabs = config.getBlock("limestoneSlabsDouble", BlockIds.dft_limestoneDoubleSlabs).getInt(BlockIds.dft_limestoneDoubleSlabs);
				BlockIds.lampsOn = config.getBlock("neonLampOn", BlockIds.dft_lampsOn).getInt(BlockIds.dft_lampsOn);
				BlockIds.lampsOff = config.getBlock("neonLampOff", BlockIds.dft_lampsOff).getInt(BlockIds.dft_lampsOff);
				BlockIds.pearlBrick = config.getBlock("pearlBrick", BlockIds.dft_pearlBrick).getInt(BlockIds.dft_pearlBrick);
				BlockIds.airBlocks = config.getBlock("airBlocks", BlockIds.dft_airBlocks).getInt(BlockIds.dft_airBlocks);
				
				BlockIds.customFlooring = config.getBlock("customFlooring", BlockIds.dft_customFlooring).getInt(BlockIds.dft_customFlooring);
				BlockIds.customBlock = config.getBlock("customBlock", BlockIds.dft_customBlock).getInt(BlockIds.dft_customBlock);
				BlockIds.customStairs = config.getBlock("customStairs", BlockIds.dft_customStairs).getInt(BlockIds.dft_customStairs);
				BlockIds.customSlabs = config.getBlock("customSlabs", BlockIds.dft_customSlabs).getInt(BlockIds.dft_customSlabs);
				BlockIds.customSlabsDouble = config.getBlock("customSlabsDouble", BlockIds.dft_customSlabsDouble).getInt(BlockIds.dft_customSlabsDouble);
				BlockIds.customFence = config.getBlock("customFence", BlockIds.dft_customFence).getInt(BlockIds.dft_customFence);
				BlockIds.customGate = config.getBlock("customGate", BlockIds.dft_customGate).getInt(BlockIds.dft_customGate);
				BlockIds.customWall = config.getBlock("customWall", BlockIds.dft_customWall).getInt(BlockIds.dft_customWall);
				BlockIds.customLight = config.getBlock("customLight", BlockIds.dft_customLight).getInt(BlockIds.dft_customLight);
				BlockIds.customRFBlock = config.getBlock("customRFBlock", BlockIds.dft_customRFBlock).getInt(BlockIds.dft_customRFBlock);
				
				BlockIds.glassBlocks = config.getBlock("glassBlocks", BlockIds.dft_glassBlocks).getInt(BlockIds.dft_glassBlocks);
				BlockIds.moltenAluminum = config.getBlock("moltenAluminumBlock", BlockIds.dft_moltenAluminum).getInt(BlockIds.dft_moltenAluminum);
				BlockIds.moltenTitanium = config.getBlock("moltenTitaniumBlock", BlockIds.dft_moltenTitanium).getInt(BlockIds.dft_moltenTitanium);
				BlockIds.moltenIron = config.getBlock("moltenIronBlock", BlockIds.dft_moltenIron).getInt(BlockIds.dft_moltenIron);
				BlockIds.moltenMagnesium = config.getBlock("moltenMagnesiumBlock", BlockIds.dft_moltenMagnesium).getInt(BlockIds.dft_moltenMagnesium);
				BlockIds.moltenTin = config.getBlock("moltenTinBlock", BlockIds.dft_moltenTin).getInt(BlockIds.dft_moltenTin);
				BlockIds.moltenGold = config.getBlock("moltenGoldBlock", BlockIds.dft_moltenGold).getInt(BlockIds.dft_moltenGold);
				BlockIds.moltenCopper = config.getBlock("moltenCopperBlock", BlockIds.dft_moltenCopper).getInt(BlockIds.dft_moltenCopper);
				BlockIds.moltenBronze = config.getBlock("moltenBronzeBlock", BlockIds.dft_moltenBronze).getInt(BlockIds.dft_moltenBronze);
				BlockIds.moltenLead = config.getBlock("moltenLeadBlock", BlockIds.dft_moltenLead).getInt(BlockIds.dft_moltenLead);
				BlockIds.moltenSilver = config.getBlock("moltenSilverBlock", BlockIds.dft_moltenSilver).getInt(BlockIds.dft_moltenSilver);
				BlockIds.moltenSteel = config.getBlock("moltenSteelBlock", BlockIds.dft_moltenSteel).getInt(BlockIds.dft_moltenSteel);
				BlockIds.moltenNickel = config.getBlock("moltenNickelBlock", BlockIds.dft_moltenNickel).getInt(BlockIds.dft_moltenNickel);
				BlockIds.fishOil = config.getBlock("fishOilBlock", BlockIds.dft_fishOil).getInt(BlockIds.dft_fishOil);
				BlockIds.fishFood = config.getBlock("fishFoodBlock", BlockIds.dft_fishFood).getInt(BlockIds.dft_fishFood);
				BlockIds.highPressureWater = config.getBlock("highPressureWater", BlockIds.dft_highPressureWater).getInt(BlockIds.dft_highPressureWater);
				BlockIds.moltenGlass = config.getBlock("moltenGlassBlock", BlockIds.dft_moltenGlass).getInt(BlockIds.dft_moltenGlass);
				BlockIds.moltenRutile = config.getBlock("moltenRutile", BlockIds.dft_moltenRutile).getInt(BlockIds.dft_moltenRutile);
	
				ItemIds.liquidContainers = config.getItem("liquidContainers", ItemIds.dft_liquidContainers).getInt(ItemIds.dft_liquidContainers);
				ItemIds.basicMirror = config.getItem("basicMirror", ItemIds.dft_basicMirror).getInt(ItemIds.dft_basicMirror);
				ItemIds.mirror = config.getItem("magicMirror", ItemIds.dft_mirror).getInt(ItemIds.dft_mirror);
				ItemIds.celestialMirror = config.getItem("celestialMirror", ItemIds.dft_celestialMirror).getInt(ItemIds.dft_celestialMirror);
				ItemIds.pearl = config.getItem("pearls", ItemIds.dft_pearl).getInt(ItemIds.dft_pearl);
				ItemIds.batteryFull = config.getItem("batteryFull", ItemIds.dft_batteryFull).getInt(ItemIds.dft_batteryFull);
				ItemIds.metals = config.getItem("metals", ItemIds.dft_metals).getInt(ItemIds.dft_metals);
				ItemIds.food = config.getItem("food", ItemIds.dft_food).getInt(ItemIds.dft_food);
				ItemIds.bait = config.getItem("bait", ItemIds.dft_bait).getInt(ItemIds.dft_bait);
				ItemIds.rodWood = config.getItem("fishingRodWood", ItemIds.dft_rodWood).getInt(ItemIds.dft_rodWood);
				ItemIds.rodTitanium = config.getItem("fishingRodTitanium", ItemIds.dft_rodTitanium).getInt(ItemIds.dft_rodTitanium);
				ItemIds.rodReed = config.getItem("fishingRodReed", ItemIds.dft_rodReed).getInt(ItemIds.dft_rodReed);
				ItemIds.fishy = config.getItem("fish", ItemIds.dft_fishy).getInt(ItemIds.dft_fishy);
				ItemIds.fishyFood = config.getItem("fishAsFood", ItemIds.dft_fishyFood).getInt(ItemIds.dft_fishyFood);
				ItemIds.speedBoat = config.getItem("boatSpeed", ItemIds.dft_speedBoat).getInt(ItemIds.dft_speedBoat);
				ItemIds.items = config.getItem("maricultureItems", ItemIds.dft_items).getInt(ItemIds.dft_items);
				ItemIds.aquaBPT1 = config.getItem("aquaBackpackT1", ItemIds.dft_aquaBPT1).getInt(ItemIds.dft_aquaBPT1);
				ItemIds.aquaBPT2 = config.getItem("aquaBackpackT2", ItemIds.dft_aquaBPT2).getInt(ItemIds.dft_aquaBPT2);
				ItemIds.ring = config.getItem("rings", ItemIds.dft_ring).getInt(ItemIds.dft_ring);
				ItemIds.bracelet = config.getItem("bracelets", ItemIds.dft_bracelet).getInt(ItemIds.dft_bracelet);
				ItemIds.necklace = config.getItem("necklaces", ItemIds.dft_necklace).getInt(ItemIds.dft_necklace);
				ItemIds.speedBoat = config.getItem("boatSpeed", ItemIds.dft_speedBoat).getInt(ItemIds.dft_speedBoat);
				ItemIds.divingHelmet = config.getItem("divingHelmet", ItemIds.dft_divingHelmet).getInt(ItemIds.dft_divingHelmet);
				ItemIds.divingTop = config.getItem("divingTop", ItemIds.dft_divingTop).getInt(ItemIds.dft_divingTop);
				ItemIds.divingPants = config.getItem("divingPants", ItemIds.dft_divingPants).getInt(ItemIds.dft_divingPants);
				ItemIds.divingBoots = config.getItem("divingBoots", ItemIds.dft_divingBoots).getInt(ItemIds.dft_divingBoots);
				ItemIds.scubaMask = config.getItem("scubaMask", ItemIds.dft_scubaMask).getInt(ItemIds.dft_scubaMask);
				ItemIds.scubaTank = config.getItem("scubaTank", ItemIds.dft_scubaTank).getInt(ItemIds.dft_scubaTank);
				ItemIds.scubaSuit = config.getItem("scubaSuit", ItemIds.dft_scubaSuit).getInt(ItemIds.dft_scubaSuit);
				ItemIds.swimfin = config.getItem("scubaFlippers", ItemIds.dft_swimfin).getInt(ItemIds.dft_swimfin);
				ItemIds.upgrade = config.getItem("siftUpgrade", ItemIds.dft_upgrade).getInt(ItemIds.dft_upgrade);
				ItemIds.net = config.getItem("fishingNet", ItemIds.dft_net).getInt(ItemIds.dft_net);
				ItemIds.plans = config.getItem("plans", ItemIds.dft_plans).getInt(ItemIds.dft_plans);
				ItemIds.fludd = config.getItem("fludd", ItemIds.dft_fludd).getInt(ItemIds.dft_fludd);
				ItemIds.paintbrush = config.getItem("paintbrush", ItemIds.dft_paintbrush).getInt(ItemIds.dft_paintbrush);
				ItemIds.titanium_parts = config.get("Item", "TiCTitaniumPartsIds", ItemIds.dft_titanium_parts).getIntList();
	
				EnchantIds.blink = config.get(CATEGORY_ENCHANT, "blink", EnchantIds.dft_blink).getInt(EnchantIds.dft_blink);
				EnchantIds.clock = config.get(CATEGORY_ENCHANT, "clock", EnchantIds.dft_clock).getInt(EnchantIds.dft_clock);
				EnchantIds.fall = config.get(CATEGORY_ENCHANT, "fallDamage", EnchantIds.dft_fallDamage).getInt(EnchantIds.dft_fallDamage);
				EnchantIds.fire = config.get(CATEGORY_ENCHANT, "fire", EnchantIds.dft_firePunch).getInt(EnchantIds.dft_firePunch);
				EnchantIds.flight = config.get(CATEGORY_ENCHANT, "flight", EnchantIds.dft_flight).getInt(EnchantIds.dft_flight);
				EnchantIds.glide = config.get(CATEGORY_ENCHANT, "glide", EnchantIds.dft_glide).getInt(EnchantIds.dft_glide);
				EnchantIds.health = config.get(CATEGORY_ENCHANT, "health", EnchantIds.dft_health).getInt(EnchantIds.dft_health);
				EnchantIds.jump = config.get(CATEGORY_ENCHANT, "jump", EnchantIds.dft_jump).getInt(EnchantIds.dft_jump);
				EnchantIds.hungry = config.get(CATEGORY_ENCHANT, "neverHungry", EnchantIds.dft_neverHungry).getInt(EnchantIds.dft_neverHungry);
				EnchantIds.oneRing = config.get(CATEGORY_ENCHANT, "oneRing", EnchantIds.dft_oneRing).getInt(EnchantIds.dft_oneRing);
				EnchantIds.poison = config.get(CATEGORY_ENCHANT, "poison", EnchantIds.dft_poison).getInt(EnchantIds.dft_poison);
				EnchantIds.punch = config.get(CATEGORY_ENCHANT, "punch", EnchantIds.dft_punch).getInt(EnchantIds.dft_punch);
				EnchantIds.repair = config.get(CATEGORY_ENCHANT, "repair", EnchantIds.dft_restoration).getInt(EnchantIds.dft_restoration);
				EnchantIds.resurrection = config.get(CATEGORY_ENCHANT, "resurrection", EnchantIds.dft_resurrection).getInt(EnchantIds.dft_resurrection);
				EnchantIds.speed = config.get(CATEGORY_ENCHANT, "speed", EnchantIds.dft_speed).getInt(EnchantIds.dft_speed);
				EnchantIds.spider = config.get(CATEGORY_ENCHANT, "spiderman", EnchantIds.dft_spiderman).getInt(EnchantIds.dft_spiderman);
				config.getCategory(CATEGORY_EXTRA).remove("legacyConfigInit");
				config.get(CATEGORY_EXTRA, "legacyConfigInit", false).getBoolean(false);
			}

		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Mariculture has had a problem loading its configuration");
		} finally {
			config.save();
		}
	}

}
