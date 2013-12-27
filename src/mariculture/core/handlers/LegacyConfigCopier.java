package mariculture.core.handlers;

import java.io.File;
import java.util.logging.Level;

import cpw.mods.fml.common.FMLLog;
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
import mariculture.core.lib.config.Category;
import mariculture.core.lib.config.Comment;
import net.minecraftforge.common.Configuration;

public class LegacyConfigCopier {
	public static void copy(String dir) {
		initIDs(new Configuration(new File(dir, "ids.cfg")));
		initMachines(new Configuration(new File(dir, "mechanics.cfg")));
		initModules(new Configuration(new File(dir, "modules.cfg")));
		initOther(new Configuration(new File(dir, "other.cfg")));
		initWorld(new Configuration(new File(dir, "worldgen.cfg")));
	}
	
	private static void initOther(Configuration config) {
		try {
			config.load();
			config.get(Category.DIFF, "Hardcore Diving Setting", Extra.HARDCORE_DIVING, Comment.HARDCORE);
			config.get(Category.CLIENT, "Client Refresh Rate", Extra.REFRESH_CLIENT_RATE, Comment.REFRESH);
			config.get(Category.EXTRA, "Debug Mode Enabled", Extra.DEBUG_ON);
			config.get(Category.EXTRA, "Molten Metal Nugget mB Value", Extra.METAL_RATE, Comment.METAL);
			config.get(Category.CLIENT, "Enable FLUDD Animations", Extra.FLUDD_WATER_ON, Comment.FLUDD);
			config.get(Category.EXTRA, "Enable Ender Dragon Spawning", Extra.ENABLE_ENDER_SPAWN, Comment.ENDERDRAGON);
			
			config.get(Category.DICTIONARY, "Blacklist", Compatibility.BLACKLIST);
			config.get(Category.DICTIONARY, "Exceptions", Compatibility.EXCEPTIONS);
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Mariculture had a problem loading the other settings");
		} finally {
			config.save();
		}
		
	}

	private static void initWorld(Configuration config) {
		try {
			config.load();
			config.addCustomCategoryComment(Category.ORE, Comment.ORE);
			
			config.get(Category.BIOME, "River Biomes", Extra.RIVER_BIOMES, Comment.RIVER);
			config.get(Category.BIOME, "Ocean Biomes", Extra.OCEAN_BIOMES, Comment.OCEAN);
			
			config.get(Category.ORE, "Bauxite > Generation", OreGeneration.BAUXITE_ON);
			config.get(Category.ORE, "Bauxite > Number of Veins", OreGeneration.BAUXITE_TOTAL);
			config.get(Category.ORE, "Bauxite > Maximum Vein Size", OreGeneration.BAUXITE_VEIN);
			config.get(Category.ORE, "Bauxite > Minimum Y Height", OreGeneration.BAUXITE_MIN);
			config.get(Category.ORE, "Bauxite > Maximum Y Height", OreGeneration.BAUXITE_MAX);
			config.get(Category.ORE, "Copper > Generation", OreGeneration.COPPER_ON);
			config.get(Category.ORE, "Copper > Number of Veins", OreGeneration.COPPER_TOTAL);
			config.get(Category.ORE, "Copper > Maximum Vein Size", OreGeneration.COPPER_VEIN);
			config.get(Category.ORE, "Copper > Minimum Y Height", OreGeneration.COPPER_MIN);
			config.get(Category.ORE, "Copper > Maximum Y Height", OreGeneration.COPPER_MAX);
			config.get(Category.ORE, "Rutile > Generation", OreGeneration.RUTILE);
			config.get(Category.ORE, "Rutile > 1 Vein Per This Many Limestone", OreGeneration.RUTILE_CHANCE);
			config.get(Category.ORE, "Limestone > Generation", OreGeneration.LIMESTONE);
			config.get(Category.ORE, "Limestone > 1 Vein Per This Many Chunks", OreGeneration.LIMESTONE_CHANCE);
			config.get(Category.ORE, "Limestone > Maximum Vein Size", OreGeneration.LIMESTONE_VEIN);
			config.get(Category.ORE, "Natural Gas > Generation", OreGeneration.NATURAL_GAS_ON);
			config.get(Category.ORE, "Natural Gas > 1 Pocket Per This Many Chunks", OreGeneration.NATURAL_GAS_CHANCE);
			config.get(Category.ORE, "Natural Gas > Maximum Vein Size", OreGeneration.NATURAL_GAS_VEIN);
			config.get(Category.ORE, "Natural Gas > Minimum Y Height", OreGeneration.NATURAL_GAS_MIN);
			config.get(Category.ORE, "Natural Gas > Maximum Y Height", OreGeneration.NATURAL_GAS_MAX);

			config.get(Category.WORLD, "Coral > 1 Reef Per this Many Chunks", WorldGeneration.CORAL_CHANCE);
			config.get(Category.WORLD, "Coral > Maximum Depth", WorldGeneration.CORAL_DEPTH);
			config.get(Category.WORLD, "Kelp > 1 Forest Per This Many Chunks", WorldGeneration.KELP_CHANCE);
			config.get(Category.WORLD, "Kelp > Maximum Depth", WorldGeneration.KELP_DEPTH);
			config.get(Category.WORLD, "Kelp > Maximum World Gen Height", WorldGeneration.KELP_HEIGHT);
			config.get(Category.WORLD, "Kelp > Single Density", WorldGeneration.KELP_PATCH_DENSITY);
			config.get(Category.WORLD, "Kelp > Forest Density", WorldGeneration.KELP_FOREST_DENSITY);
			config.get(Category.WORLD, "Treasure Chest Per This Many Kelp", WorldGeneration.KELP_CHEST_CHANCE);
			config.get(Category.WORLD, "Deep Oceans", WorldGeneration.DEEP_OCEAN);
			config.get(Category.WORLD, "Water Filled Caves in Oceans", WorldGeneration.WATER_CAVES);
			config.get(Category.WORLD, "Water Filled Ravines in Oceans", WorldGeneration.WATER_RAVINES);
			config.get(Category.WORLD, "Water Ravine Chance (Lower = More Common)", WorldGeneration.RAVINE_CHANCE);
			config.get(Category.WORLD, "Remove Mineshafts in Oceans", WorldGeneration.NO_MINESHAFTS);
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Oh dear, there was a problem with Mariculture loading it's world configuration");
		} finally {
			config.save();
		}
	}

	private static void initModules(Configuration config) {
		try {
			config.load();
			config.get(Category.MODULES, "Diving", Modules.diving.isActive());
			config.get(Category.MODULES, "Factory", Modules.factory.isActive());
			config.get(Category.MODULES, "Fishery", Modules.fishery.isActive());
			config.get(Category.MODULES, "Magic", Modules.magic.isActive());
			config.get(Category.MODULES, "Transport", Modules.transport.isActive());
			config.get(Category.MODULES, "World Plus", Modules.world.isActive());
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Problem with Mariculture when copying over the module data");
		} finally {
			config.save();
		}
	}

	private static void initMachines(Configuration config) {	
		try {
			config.load();
			
			config.get(Category.SPEED, "Autofisher", MachineSpeeds.autofisher);
			config.get(Category.SPEED, "Autodictionary", MachineSpeeds.dictionary);
			config.get(Category.SPEED, "Fish Feeder", MachineSpeeds.feeder);
			config.get(Category.SPEED, "Incubator", MachineSpeeds.incubator);
			config.get(Category.SPEED, "Industrial Smelter", MachineSpeeds.liquifier);
			config.get(Category.SPEED, "Fishing Net", MachineSpeeds.net);
			config.get(Category.SPEED, "Sawmill", MachineSpeeds.sawmill);
			config.get(Category.SPEED, "Industrial Freezer", MachineSpeeds.settler);
			
			config.get(Category.EXTRA, "Air Pump - Manual Power Enabled", Extra.ACTIVATE_PUMP, Comment.PUMP_MANUAL);
			config.get(Category.EXTRA, "Air Pump - Redstone Power Enabled", Extra.REDSTONE_PUMP, Comment.PUMP_REDSTONE);
			config.get(Category.EXTRA, "Air Pump - MJ/RF Power Enabled", Extra.BUILDCRAFT_PUMP, Comment.PUMP_RF);
			
			config.get(Category.PROD, "Bait Quality 0 Chance", Extra.bait0, "Ant - " + Comment.BAIT);
			config.get(Category.PROD, "Bait Quality 1 Chance", Extra.bait1, "Bread - " + Comment.BAIT);
			config.get(Category.PROD, "Bait Quality 2 Chance", Extra.bait2, "Maggot/Grasshopper - " + Comment.BAIT);
			config.get(Category.PROD, "Bait Quality 3 Chance", Extra.bait3, "Worm - " + Comment.BAIT);
			config.get(Category.PROD, "Bait Quality 4 Chance", Extra.bait4, Comment.BAIT);
			config.get(Category.PROD, "Bait Quality 5 Chance", Extra.bait5, "Minnow - " + Comment.BAIT);
			config.get(Category.PROD, "Coral Spread Chance", Extra.CORAL_SPREAD_CHANCE, Comment.CORAL_SPREAD);
			config.get(Category.PROD, "Kelp Spread Moss Chance", Extra.KELP_SPREAD_CHANCE, Comment.KELP_SPREAD);
			config.get(Category.PROD, "Kelp Growth Chance", Extra.KELP_GROWTH_CHANCE, Comment.KELP_GROWTH);
			config.get(Category.PROD, "Pearl Oyster Speed", Extra.PEARL_GEN_SPEED, Comment.PEARL_RATE);
			config.get(Category.PROD, "Pearl Oyster Pearl Chance", Extra.PEARL_GEN_CHANCE, Comment.PEARL_CHANCE);
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "There was an issue with Mariculture when adjusting machine settings");
		} finally {
			config.save();
		}
	}

	private static void initIDs(Configuration config) {
		try {
			config.load();
			/** BEGIN BLOCK IDS **/
			//Basic Blocks
			config.get("block", "Coral & Kelp", BlockIds.coral);
			config.get("block", "Ore Blocks", BlockIds.ores);
			config.get("block", "Machine Blocks", BlockIds.util);
			config.get("block", "Oyster", BlockIds.oyster);
			config.get("block", "Sifter", BlockIds.sift);
			config.get("block", "Double Blocks", BlockIds.doubleBlocks);
			config.get("block", "Single Blocks", BlockIds.singleBlocks);
			config.get("block", "Legacy > Limestone Stairs", BlockIds.limestoneStairs);
			config.get("block", "Legacy > Limestone Brick Stairs", BlockIds.limestoneBrickStairs);
			config.get("block", "Legacy > Limestone Smooth Stairs", BlockIds.limestoneSmoothStairs);
			config.get("block", "Legacy > Limestone Chiseled Stairs", BlockIds.limestoneChiseledStairs);
			config.get("block", "Legacy > Limestone Slabs Single", BlockIds.limestoneSlabs);
			config.get("block", "Legacy > Limestone Slabs Double", BlockIds.limestoneDoubleSlabs);
			config.get("block", "Neon Lamps On", BlockIds.lampsOn);
			config.get("block", "Neon Lamps Off", BlockIds.lampsOff);
			config.get("block", "Pearl Bricks", BlockIds.pearlBrick);
			config.get("block", "Air Blocks", BlockIds.airBlocks);
			config.get("block", "Glass Blocks", BlockIds.glassBlocks);
			
			//Custom Blocks
			config.get("block", "Custom Flooring", BlockIds.customFlooring);
			config.get("block", "Custom Blocks", BlockIds.customBlock);
			config.get("block", "Custom Stairs", BlockIds.customStairs);
			config.get("block", "Custom Slabs Single", BlockIds.customSlabs);
			config.get("block", "Custom Slabs Double", BlockIds.customSlabsDouble);
			config.get("block", "Custom Fence", BlockIds.customFence);
			config.get("block", "Custom Gate", BlockIds.customGate);
			config.get("block", "Custom Wall", BlockIds.customWall);
			config.get("block", "Custom Light", BlockIds.customLight);
			config.get("block", "Custom RF Block", BlockIds.customRFBlock);
			
			//Molten/Liquid Blocks
			config.get("block", "Molten Aluminum", BlockIds.moltenAluminum);
			config.get("block", "Molten Titanium", BlockIds.moltenTitanium);
			config.get("block", "Molten Iron", BlockIds.moltenIron);
			config.get("block", "Molten Magnesium", BlockIds.moltenMagnesium);
			config.get("block", "Molten Tin", BlockIds.moltenTin);
			config.get("block", "Molten Gold", BlockIds.moltenGold);
			config.get("block", "Molten Copper", BlockIds.moltenCopper);
			config.get("block", "Molten Bronze", BlockIds.moltenBronze);
			config.get("block", "Molten Lead", BlockIds.moltenLead);
			config.get("block", "Molten Silver", BlockIds.moltenSilver);
			config.get("block", "Molten Steel", BlockIds.moltenSteel);
			config.get("block", "Molten Nickel", BlockIds.moltenNickel);
			config.get("block", "Fish Oil", BlockIds.fishOil);
			config.get("block", "Fish Food", BlockIds.fishFood);
			config.get("block", "High Pressure Water", BlockIds.highPressureWater);
			config.get("block", "Molten Glass", BlockIds.moltenGlass);
			config.get("block", "Molten Impure Titanium", BlockIds.moltenRutile);
			
			/** END BLOCK IDS, BEGIN ITEM IDS **/
			config.get("item", "Liquid Containers", ItemIds.liquidContainers);
			config.get("item", "Basic Mirror", ItemIds.basicMirror);
			config.get("item", "Magic Mirror", ItemIds.mirror);
			config.get("item", "Celestial Mirror", ItemIds.celestialMirror);
			config.get("item", "Pearls", ItemIds.pearl);
			config.get("item", "Battery", ItemIds.batteryFull);
			config.get("item", "Materials", ItemIds.metals);
			config.get("item", "Food", ItemIds.food);
			config.get("item", "Bait", ItemIds.bait);
			config.get("item", "Fishing Rod - Wood", ItemIds.rodWood);
			config.get("item", "Fishing Rod - Titanium", ItemIds.rodTitanium);
			config.get("item", "Fishing Rod - Reed", ItemIds.rodReed);
			config.get("item", "Fish - Living", ItemIds.fishy);
			config.get("item", "Fish - Raw", ItemIds.fishyFood);
			config.get("item", "Speed Boat", ItemIds.speedBoat);
			config.get("item", "General Items", ItemIds.items);
			config.get("item", "Aquatic Backpack T1", ItemIds.aquaBPT1);
			config.get("item", "Aquatic Backpack T2", ItemIds.aquaBPT2);
			config.get("item", "Rings", ItemIds.ring);
			config.get("item", "Bracelets", ItemIds.bracelet);
			config.get("item", "Necklaces", ItemIds.necklace);
			config.get("item", "Diving - Helmet", ItemIds.divingHelmet);
			config.get("item", "Diving - Breathing Tube", ItemIds.divingTop);
			config.get("item", "Diving - Suit", ItemIds.divingPants);
			config.get("item", "Diving - Boots", ItemIds.divingBoots);
			config.get("item", "Scuba - Mask", ItemIds.scubaMask);
			config.get("item", "Scuba - Tank", ItemIds.scubaTank);
			config.get("item", "Scuba - Wetsuit", ItemIds.scubaSuit);
			config.get("item", "Scuba - Flippers", ItemIds.swimfin);
			config.get("item", "Upgrades", ItemIds.upgrade);
			config.get("item", "Fishing Net", ItemIds.net);
			config.get("item", "Plans", ItemIds.plans);
			config.get("item", "FLUDD", ItemIds.fludd);
			config.get("item", "Paintbrush", ItemIds.paintbrush);
			
			//Titanium Parts
			ItemIds.titanium_part_1 = config.get("item", "TiC Titanium - Arrowhead", ItemIds.titanium_parts[0]).getInt();
			ItemIds.titanium_part_2 = config.get("item", "TiC Titanium - Axe Head", ItemIds.titanium_parts[1]).getInt();
			ItemIds.titanium_part_3 = config.get("item", "TiC Titanium - Sign Head", ItemIds.titanium_parts[2]).getInt();
			ItemIds.titanium_part_4 = config.get("item", "TiC Titanium - Binding", ItemIds.titanium_parts[3]).getInt();
			ItemIds.titanium_part_5 = config.get("item", "TiC Titanium - Chisel Head", ItemIds.titanium_parts[4]).getInt();
			ItemIds.titanium_part_6 = config.get("item", "TiC Titanium - Shard", ItemIds.titanium_parts[5]).getInt();
			ItemIds.titanium_part_7 = config.get("item", "TiC Titanium - Crossbar", ItemIds.titanium_parts[6]).getInt();
			ItemIds.titanium_part_8 = config.get("item", "TiC Titanium - Excavator Head", ItemIds.titanium_parts[7]).getInt();
			ItemIds.titanium_part_9 = config.get("item", "TiC Titanium - Pan", ItemIds.titanium_parts[8]).getInt();
			ItemIds.titanium_part_10 = config.get("item", "TiC Titanium - Full Guard", ItemIds.titanium_parts[9]).getInt();
			ItemIds.titanium_part_11 = config.get("item", "TiC Titanium - Hammer Head", ItemIds.titanium_parts[10]).getInt();
			ItemIds.titanium_part_12 = config.get("item", "TiC Titanium - Knife Blade", ItemIds.titanium_parts[11]).getInt();
			ItemIds.titanium_part_13 = config.get("item", "TiC Titanium - Wide Guard", ItemIds.titanium_parts[12]).getInt();
			ItemIds.titanium_part_14 = config.get("item", "TiC Titanium - Large Sword Blade", ItemIds.titanium_parts[13]).getInt();
			ItemIds.titanium_part_15 = config.get("item", "TiC Titanium - Large Plate", ItemIds.titanium_parts[14]).getInt();
			ItemIds.titanium_part_16 = config.get("item", "TiC Titanium - Broad Axe Head", ItemIds.titanium_parts[15]).getInt();
			ItemIds.titanium_part_17 = config.get("item", "TiC Titanium - Hand Guard", ItemIds.titanium_parts[16]).getInt();
			ItemIds.titanium_part_18 = config.get("item", "TiC Titanium - Pickaxe Head", ItemIds.titanium_parts[17]).getInt();
			ItemIds.titanium_part_19 = config.get("item", "TiC Titanium - Scythe Head", ItemIds.titanium_parts[18]).getInt();
			ItemIds.titanium_part_20 = config.get("item", "TiC Titanium - Shovel Head", ItemIds.titanium_parts[19]).getInt();
			ItemIds.titanium_part_21 = config.get("item", "TiC Titanium - Sword Blade", ItemIds.titanium_parts[20]).getInt();
			ItemIds.titanium_part_22 = config.get("item", "TiC Titanium - Tool Rod", ItemIds.titanium_parts[21]).getInt();
			ItemIds.titanium_part_23 = config.get("item", "TiC Titanium - Tough Binding", ItemIds.titanium_parts[22]).getInt();
			ItemIds.titanium_part_24 = config.get("item", "TiC Titanium - Tough Rod", ItemIds.titanium_parts[23]).getInt();
			
			/** END ITEM IDS BEGIN ENCHANT IDS **/
			config.get(Category.ENCHANT, "Blink", EnchantIds.blink);
			config.get(Category.ENCHANT, "Timelord", EnchantIds.clock);
			config.get(Category.ENCHANT, "Fall Resistance", EnchantIds.fall);
			config.get(Category.ENCHANT, "Inferno", EnchantIds.fire);
			config.get(Category.ENCHANT, "Superman", EnchantIds.flight);
			config.get(Category.ENCHANT, "Paraglide", EnchantIds.glide);
			config.get(Category.ENCHANT, "1 Up", EnchantIds.health);
			config.get(Category.ENCHANT, "Leapfrog", EnchantIds.jump);
			config.get(Category.ENCHANT, "Never Hungry", EnchantIds.hungry);
			config.get(Category.ENCHANT, "The One Ring", EnchantIds.oneRing);
			config.get(Category.ENCHANT, "Poison Ivy", EnchantIds.poison);
			config.get(Category.ENCHANT, "Power Punch", EnchantIds.punch);
			config.get(Category.ENCHANT, "Restoration", EnchantIds.repair);
			config.get(Category.ENCHANT, "Reaper", EnchantIds.resurrection);
			config.get(Category.ENCHANT, "Sonic the Hedgehog", EnchantIds.speed);
			config.get(Category.ENCHANT, "Spiderman", EnchantIds.spider);
			
		} catch (Exception e) {
			FMLLog.log(Level.SEVERE, e, "Mariculture had a serious issue loading it's block/item/enchant ids");
		} finally {
			config.save();
		}
	}
}
