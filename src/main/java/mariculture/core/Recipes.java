package mariculture.core;

import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.VanillaReplacementHelper;
import mariculture.core.items.ItemBattery;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.Extra;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.SingleMeta;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.TransparentMeta;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.lib.UtilMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.util.FluidDictionary;
import mariculture.fishery.Fishery;
import mariculture.fishery.items.ItemRod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBookshelf;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Recipes {
	public static void add() {
		RecipesSmelting.add();
		addCraftingItems();
		addMetalRecipes();
		addUpgradeRecipes();
		addAnvilRecipes();

	//Items
		//Copper Battery
		RecipeHelper.addShapedRecipe(ItemBattery.make(new ItemStack(Core.batteryCopper), 0), new Object[] {
			" I ", "TRT", "TRT", 'I', "ingotIron", 'R', "dustRedstone", 'T', "ingotCopper"
		});

		//Titanium Battery
		RecipeHelper.addShapedRecipe(ItemBattery.make(new ItemStack(Core.batteryTitanium), 0), new Object[] {
			" I ", "TRT", "TRT", 'I', "ingotIron", 'R', "dustRedstone", 'T', "ingotTitanium"
		});

		//Void Bottle
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.liquidContainers, 8, FluidContainerMeta.BOTTLE_VOID), new Object[] {
			new ItemStack(Core.liquidContainers, 1, FluidContainerMeta.BOTTLE_EMPTY), "dustRedstone", new ItemStack(Items.dye, 1, Dye.INK)
		});
		
		//Oyster and Beef Pie
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.food, 1, FoodMeta.OYSTER), new Object[] {
			"foodSalt", "foodSalt", Items.beef, new ItemStack(Core.waterBlocks, 1, 0), Items.wheat, Items.egg, "dustSalt", Items.porkchop, Items.wheat
		});
		
		//Processing Book
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.guides, 1, GuideMeta.PROCESSING), new Object[] {
			Items.book, new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK)
		});
		
		//Heat Resistant Bottles
		RecipeHelper.addShapedRecipe(new ItemStack(Core.liquidContainers, 3, FluidContainerMeta.BOTTLE_EMPTY), new Object[] {
			"G G", " G ", 'G', new ItemStack(Core.glassBlocks, 1, GlassMeta.HEAT)
		});

	//Basic Blocks
		//Limestone Brick
		RecipeHelper.add4x4Recipe(new ItemStack(Core.oreBlocks, 4, OresMeta.LIMESTONE_BRICK), Core.oreBlocks, OresMeta.LIMESTONE);
		//Chiseled Limestone
		RecipeHelper.add4x4Recipe(new ItemStack(Core.oreBlocks, 4, OresMeta.LIMESTONE_CHISELED), Core.oreBlocks, OresMeta.LIMESTONE_SMOOTH);
		//Smooth Limestone
		RecipeHelper.addSmelting(new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE), new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE_SMOOTH), 0.1F);
		//Thin Limestone Brick
		RecipeHelper.addShapedRecipe(new ItemStack(Core.oreBlocks, 4, OresMeta.LIMESTONE_THIN), new Object[] {
			"CX", "XC", 'C', new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE_BRICK), 'X', new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE_CHISELED)
		});
		
		//Base Brick
		RecipeHelper.addShapedRecipe(new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_BRICK), new Object[] {
			"IGI", "G G", "IGI", 'I', new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK), 'G', Blocks.iron_bars
		});
		
		//Base Iron
		RecipeHelper.addShapedRecipe(new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_IRON), new Object[] {
			"IGI", "G G", "IGI", 'I', "ingotIron", 'G', Blocks.glass_pane
		});
		
		//Base Wood
		RecipeHelper.addShapedRecipe(new ItemStack(Core.woodBlocks, 1, WoodMeta.BASE_WOOD), new Object[] {
			"IGI", "G G", "IGI", 'I', "logWood", 'G', Blocks.fence
		});
		
	//Machines
		//Air Pump
		RecipeHelper.addShapedRecipe(new ItemStack(Core.singleBlocks, 1, SingleMeta.AIR_PUMP), new Object[] {
			"WGW", "PRP", "PMP", 'G', "glass", 'R', "dustRedstone", 'P', "plankWood", 'M', Blocks.piston,
			'W', new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL)
		});
		
		//Copper Tank
		RecipeHelper.addShapedRecipe(new ItemStack(Core.tankBlocks, 2, TankMeta.TANK), new Object[] {
			"CWC", "WGW", "CWC", 'C', "ingotCopper", 'W', "plankWood", 'G', "glass"
		});
				
		//Storage Bookshelf
		RecipeHelper.addShapedRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.BOOKSHELF), new Object[] {
			"SPS", "PCP", "SSS", 'P', "plankWood", 'S', Blocks.bookshelf, 'C', Blocks.chest
		});
		
		//Crucible Furnace
		RecipeHelper.addShapedRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.LIQUIFIER), new Object[] {
			" L ", "BGB", "HCH", 
			'B', new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK),
			'L', Items.lava_bucket, 
			'G', new ItemStack(Core.tankBlocks, 1, TankMeta.TANK), 
			'H', new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER),
			'C', new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_BRICK)
		});
		
		//Anvil Recipe
		RecipeHelper.addShapedRecipe(new ItemStack(Core.singleBlocks, 1, SingleMeta.ANVIL_1), new Object[] {
			"CCC", " N ", "BBB",
			'C', new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_BRICK),
			'B', new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK),
			'N', Blocks.nether_brick
		});

		//VAT Recipe
		RecipeHelper.addShapedRecipe(new ItemStack(Core.doubleBlock, 1, DoubleMeta.VAT), new Object[] {
			"C C", "C C", "CCC", 'C', "ingotCopper"
		});
		
		//Ingot Caster
		RecipeHelper.addShapedRecipe(new ItemStack(Core.singleBlocks, 1, SingleMeta.INGOT_CASTER), new Object[] {
			" B ", "BBB", " B ", 'B', new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK)
		});
		
		//Ladle
		RecipeHelper.addShapedRecipe(new ItemStack(Core.ladle), new Object[] {
			" C ", " C ", "C  ", 'C', "ingotCopper"
		});
		
		//Bucket
		RecipeHelper.addShapedRecipe(new ItemStack(Core.bucket), new Object[] {
			"T T", " T ", 'T', "ingotTitanium"
		});
		
		//Hammer
		RecipeHelper.addShapedRecipe(new ItemStack(Core.hammer), new Object[] {
			"PP ", " SP", "S  ",
			'P', new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK), 'S', Blocks.nether_brick
		});

		//Pearl Bricks
		for (int i = 0; i < 12; i++) {
			RecipeHelper.add4x4Recipe(new ItemStack(Core.pearlBlock, 1, i), new ItemStack(Core.pearls, 1, i));
			RecipeHelper.addUncraftingRecipe(new ItemStack(Core.pearls, 4, i), new ItemStack(Core.pearlBlock, 1, i));
		}
		
		//Piston
		RecipeHelper.addShapedRecipe(new ItemStack(Blocks.piston, 1), new Object[] {
			"TTT", "#X#", "#R#", '#', "cobblestone", 'X', "ingotAluminum", 'R', "dustRedstone", 'T', "plankWood"
		});
	}

	private static void addCraftingItems() {
		//Golden Silk > 5 Seconds, 1 x Ingot Gold(mB) + String
		RecipeHelper.addVatItemRecipe(new ItemStack(Items.string), FluidDictionary.gold, MetalRates.INGOT, 
				new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_SILK), 5);
		
		//Golden Thread
		Object stick = (Modules.fishery.isActive())? new ItemStack(Core.craftingItem, 1, CraftingMeta.POLISHED_STICK): "plankWood";
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_THREAD), new Object[] {
			"ABA", "ABA", 'B', stick, 'A', new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_SILK),
		});

		//Neoprene
		Object rubber = (OreDictionary.getOres("itemRubber").size() > 0)? "itemRubber": new ItemStack(Items.dye, 1, Dye.INK);
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.NEOPRENE),  new Object[] { 
			"IPI", "PEP", "IPI", 
			'I', rubber, 
			'P', new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE), 
			'E', new ItemStack(Core.liquidContainers, 1, FluidContainerMeta.BOTTLE_GAS) 
		});
		
		//Plastic > 60 Seconds > 30 Buckets of Natural Gas + 16 Limestone
		RecipeHelper.addVatItemRecipe(new ItemStack(Core.oreBlocks, 16, OresMeta.LIMESTONE), FluidDictionary.natural_gas, 30000, 
				new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC), 60);
		
		//Plastic Lens
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.LENS), new Object[] { 
			" N ", "NGN", " N ", 
			'N', new ItemStack(Core.craftingItem, 1, CraftingMeta.NEOPRENE), 
			'G', new ItemStack(Core.transparentBlocks, 1, TransparentMeta.PLASTIC) 
		});
		
		//Glass Lens
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.LENS_GLASS), new Object[] {
			" P ", "PGP", " P ", 'P', "plankWood", 'G', "glass"
		});
		
		//Aluminum Sheet
		RecipeHelper.addAnvilRecipe(new ItemStack(Core.oreBlocks, 1, OresMeta.ALUMINUM_BLOCK), 
				new ItemStack(Core.craftingItem, 8, CraftingMeta.ALUMINUM_SHEET), 50);

		//Heating
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER), new Object[] {
			"CCC", "CCC", 'C', new ItemStack(Core.craftingItem, 1, CraftingMeta.CARBIDE)
		});

		//Cooling
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER), new Object[] {
			"  P", "PI ", "  P", 'P', "plankWood", 'I', "ingotIron"
		});
		
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER), new Object[] {
			" P ", " I ", "P P", 'P', "plankWood", 'I', "ingotIron"
		});
		
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER), new Object[] {
			"P  ", " IP", "P  ", 'P', "plankWood", 'I', "ingotIron"
		});
		
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER), new Object[] {
			"P P", " I ", " P ", 'P', "plankWood", 'I', "ingotIron"
		});

		//Carbide
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.CARBIDE), new Object[] {
			"CSF", "FBS", "SFC", 'C', Items.clay_ball, 'F', new ItemStack(Items.coal, 1, 0), 'S', Blocks.sand, 'B', Blocks.clay
		});

		//Wheel
		RecipeHelper.addWheelRecipe(new ItemStack(Core.craftingItem, 3, CraftingMeta.WHEEL), "ingotIron", "slabWood");
		
		//Wicker
		RecipeHelper.addCrossHatchRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER), "stickWood", Items.sugar);
		
		//Yellow Plastic > 5 Minutes > 1 Block of Gold(mB) + 4 Plastic
		RecipeHelper.addVatItemRecipe(new ItemStack(Core.craftingItem, 4, CraftingMeta.PLASTIC), 
				FluidDictionary.gold, MetalRates.BLOCK,  new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC_YELLOW), 60 * 5);
		
		//Titanium Sheet, 500 hits in an Anvil
		RecipeHelper.addAnvilRecipe(new ItemStack(Core.oreBlocks, 1, OresMeta.TITANIUM_BLOCK), 
				new ItemStack(Core.craftingItem, 8, CraftingMeta.TITANIUM_SHEET), 150);
		
		
		if(Extra.OVERWORLD) {
			//Burnt Brick > Brick + 1000mB of Lava = Burnt Brick
			RecipeHelper.addVatItemRecipe(new ItemStack(Items.brick), "lava", 1000, 
													new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK), 16);
		}
		
		//Burnt Brick > Nether Brick + 500mB of Lava
		RecipeHelper.addVatItemRecipe(new ItemStack(Items.netherbrick), "lava", 500, 
												new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK), 8);
		
		//Titanium Rod > 3 Ingots worth of Molten Metal + a Stick
		RecipeHelper.addVatItemRecipe(new ItemStack(Items.stick),  FluidDictionary.titanium, MetalRates.INGOT * 3, 
													new ItemStack(Core.craftingItem, 1, CraftingMeta.TITANIUM_ROD), 60);
	}
	
	private static void addMetalRecipes() {		
		RecipeHelper.add9x9Recipe(new ItemStack(Core.oreBlocks, 1, OresMeta.MAGNESIUM_BLOCK), "ingotMagnesium");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.INGOT_MAGNESIUM), "blockMagnesium");
		RecipeHelper.add9x9Recipe(new ItemStack(Core.oreBlocks, 1, OresMeta.TITANIUM_BLOCK), "ingotTitanium");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.INGOT_TITANIUM), "blockTitanium");
		RecipeHelper.add9x9Recipe(new ItemStack(Core.oreBlocks, 1, OresMeta.ALUMINUM_BLOCK), "ingotAluminum");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.INGOT_ALUMINUM), "blockAluminum");
		RecipeHelper.add9x9Recipe(new ItemStack(Core.oreBlocks, 1, OresMeta.COPPER_BLOCK), "ingotCopper");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.INGOT_COPPER), "blockCopper");
		RecipeHelper.addSmelting(new ItemStack(Core.oreBlocks, 1, OresMeta.COPPER), new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_COPPER), 0.5F);
	}
	
	private static void addUpgradeRecipes() {
		//Basic Storage
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_STORAGE), new Object[] {
			"WPW", "DCD", "WPW", 'D', "ingotCopper", 'P', "plankWood", 'C', Blocks.chest,
			'W', new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER)
		});
		
		//Standard Storage
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_STORAGE), new Object[] {
			"WCW", "CUC", "STS", 'C', "ingotCopper", 'S', "slabWood", 'T', Blocks.trapped_chest,
			'W', new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER), 
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_STORAGE)
		});
		
		//Advanced Storage
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_STORAGE), new Object[] {
			"CSC", "AUA", "WTW", 'C', "ingotCopper", 'S', "slabWood", 'T', Blocks.trapped_chest,
			'A', new ItemStack(Core.craftingItem, 1, CraftingMeta.ALUMINUM_SHEET),
			'W', new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER), 
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_STORAGE)
		});
		
		//Ultimate Storage
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_STORAGE), new Object[] {
			"GWG", "WUW", "ATA", 'G', "ingotGold", 'T', Blocks.trapped_chest,
			'A', new ItemStack(Core.craftingItem, 1, CraftingMeta.ALUMINUM_SHEET),
			'W', new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER), 
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_STORAGE)
		});
		
		//Basic Cooling
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_COOLING), new Object[] {
			" S ", "CBC", " S ", 'S', Items.snowball, 'B', Blocks.snow, 
			'C', new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER)
		});
		
		//Standard Cooling
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_COOLING), new Object[] {
			"ACA", "SUS", "CAC", 'S', Items.snowball, 'A', "ingotAluminum", 
			'C', new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_COOLING)
		});
		
		//Advanced Cooling
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_COOLING), new Object[] {
			"CTC", "IUI", "TRT", 'I', Blocks.ice, 'R', "ingotIron", 'T', "ingotAluminum",
			'C', new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_COOLING)
		});
		
		//Ultimate Cooling
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_COOLING), new Object[] {
			"TCT", "IUI", "CDC", 'I', Blocks.ice, 'D', "ingotAluminum", 'T', "ingotTitanium",
			'C', new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_COOLING)
		});
		
		//Basic Heating
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_HEATING), new Object[] {
			"HIH", 'I', "ingotIron", 'H', new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER)
		});
		
		//Standard Heating
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_HEATING), new Object[] {
			"A A", "HUH", " A ", 'A', "ingotAluminum",
			'H', new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_HEATING)
		});
		
		//Advanced Heating
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_HEATING), new Object[] {
			"IHI", "TUT", "IHI", 'T', "ingotTitanium", 'I', "ingotIron",
			'H', new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_HEATING)
		});
	
		//Ultimate Heating
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_HEATING), new Object[] {
			"TDT", "HUH", "GTG", 'G', "ingotGold", 'T', "ingotTitanium", 'D', Items.blaze_rod,
			'H', new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_HEATING)
		});
		
		ItemStack heart = (Modules.fishery.isActive())? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_HEALTH): new ItemStack(Items.potionitem, 1, 8197);
		
		//Basic Purity
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_PURITY), new Object[] {
			"MPM", "PIP", "MPM", 'I', "dyeRed", 'M', "ingotAluminum",
			'P', new ItemStack(Core.pearls, 1, PearlColor.WHITE)
		});
		
		//Standard Purity
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_PURITY), new Object[] {
			"PHP", "NUN", "MHM", 'H', heart, 'N', "ingotTitanium", 'M', "ingotAluminum",
			'P', new ItemStack(Core.pearls, 1, PearlColor.WHITE),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_PURITY)
		});
		
		//Advanced Purity
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_PURITY), new Object[] {
			"PSP", "AUA", "TPT", 'A', heart, 'T', "ingotTitanium",
			'P', new ItemStack(Core.pearls, 1, PearlColor.WHITE),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_PURITY),
			'S', new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_SILK)
		});
		
		//Ultimate Purity
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_PURITY), new Object[] {
			"PSP", "TUT", "PAP", 'A', heart, 'T', "ingotTitanium",
			'P', new ItemStack(Core.pearls, 1, PearlColor.WHITE),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_PURITY),
			'S', new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_THREAD)
		});
		
		ItemStack attack = (Modules.fishery.isActive())? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK): new ItemStack(Items.potionitem, 1, 8204);
		ItemStack poison = (Modules.fishery.isActive())? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_POISON): new ItemStack(Items.potionitem, 1, 8228);
		ItemStack night = (Modules.fishery.isActive())? new ItemStack(Fishery.fishyFood, 1, Fishery.night.fishID): new ItemStack(Items.ender_pearl);
		ItemStack ender = (Modules.fishery.isActive())? new ItemStack(Fishery.fishyFood, 1, Fishery.ender.fishID): new ItemStack(Items.ender_eye);
		
		//Basic Impurity
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_IMPURITY), new Object[] {
			"NWN", "ESE", "NGN", 'N', Blocks.netherrack, 'W', Items.nether_wart, 
			'E', Items.spider_eye, 'S', Items.bone, 'G', "ingotGold"
		});
		
		//Standard Impurity
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_IMPURITY), new Object[] {
			"TGT", "SUS", "PFP", 'T', Items.ghast_tear, 'F', Items.spider_eye, 'P', poison, 'S', attack,
			'G', new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_SILK),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_IMPURITY),
		});
		
		//Advanced Impurity
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_IMPURITY), new Object[] {
			"HGH", "FUF", "SPS", 'F', night, 'P', poison, 'H', attack, 'S', Items.spider_eye, 'G', "blockGold",
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_IMPURITY),
		});
		
		//Ultimate Impurity
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_IMPURITY), new Object[] {
			"SGS", "PUP", "FSF", 'F', ender, 'P', poison, 'S', attack,
			'G', new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_THREAD),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_IMPURITY),
		});
		
		//Ethereal
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ETHEREAL), new Object[] {
			"PUP", "GEG", "PDP", 'P', Items.ender_pearl, 'G', "ingotGold", 
			'E', Items.ender_eye, 'D', Items.diamond, 'U', Blocks.redstone_torch
		});
		
		//Salinator
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.SALINATOR), new Object[] {
			"ASA", "SUS", "MAM", 'A', "ingotAluminum", 'S', "foodSalt", 'M', "ingotMagnesium",
			 'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_IMPURITY)
		});
		
		//Filtrator
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.FILTER), new Object[] {
			"MPM", "CUC", "AMA", 'A', "ingotAluminum", 'M', "ingotMagnesium",
			'P', new ItemStack(Core.pearls, 1, PearlColor.BLUE),
			'C', new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_PURITY)
		});
		
		//Basic Speed Upgrade
		RecipeHelper.addWheelRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_SPEED), "ingotIron", Items.sugar);
		
		//Standard Speed Upgrade
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_SPEED), new Object[] {
			" S ", "AUA", " S ", 'A', "ingotAluminum", 'S', Items.sugar,
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_SPEED)
		});
		
		//Advanced Speed Upgrade
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_SPEED), new Object[] {
			"ASA", "TUT", "SNS", 'A', "ingotAluminum", 'S', Items.sugar, 'T', "ingotTitanium", 'N', Blocks.ice,
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_SPEED)
		});
		
		//Ultimate Speed Upgrade
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_SPEED), new Object[] {
			"TRT", "SUS", "ARA", 'A', new ItemStack(Core.craftingItem, 1, CraftingMeta.ALUMINUM_SHEET), 'S', Items.sugar, 
			'T', "ingotTitanium", 'R', Blocks.golden_rail,
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_SPEED)
		});
		
		//Basic Capacitor
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_RF), new Object[] {
			" T ", "CRC", 'T', Blocks.redstone_torch, 'C', "ingotCopper", 'R', "dustRedstone"
		});
		
		//Standard Capacitor
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_RF), new Object[] {
			"CTC", "QUQ", "RCR", 'C', "ingotCopper", 'T', Blocks.redstone_torch, 'Q', Items.quartz, 'R', Items.repeater,
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_RF)
		});
		
		//Advanced Capacitor
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_RF), new Object[] {
			" D ", "RUR", "QCQ", 'D', "dustRedstone", 'Q', Items.quartz, 'R', Items.repeater,
			'C', new ItemStack(Core.batteryCopper, 1, OreDictionary.WILDCARD_VALUE),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_RF)
		});
		
		//Ultimate Capacitor
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_RF), new Object[] {
			" C ", "RUR", "QBQ", 'Q', Items.comparator, 'R', "dustRedstone", 'B', "blockRedstone",
			'C', new ItemStack(Core.batteryTitanium, 1, OreDictionary.WILDCARD_VALUE),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_RF)
		});
	}
	
	public static void addAnvilRecipes() {
		RecipeHelper.addAnvilRecipe(new ItemStack(Items.bone), new ItemStack(Items.dye, 4, Dye.BONE), 10);
		RecipeHelper.addAnvilRecipe(new ItemStack(Blocks.red_flower), new ItemStack(Items.dye, 3, Dye.RED), 10);
		RecipeHelper.addAnvilRecipe(new ItemStack(Blocks.yellow_flower), new ItemStack(Items.dye, 3, Dye.YELLOW), 10);
	}
}
