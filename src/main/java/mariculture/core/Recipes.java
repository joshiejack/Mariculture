package mariculture.core;

import static mariculture.core.lib.Items.aluminumSheet;
import static mariculture.core.lib.Items.bait;
import static mariculture.core.lib.Items.blockAluminum;
import static mariculture.core.lib.Items.blockClay;
import static mariculture.core.lib.Items.blockTitanium;
import static mariculture.core.lib.Items.bluePearl;
import static mariculture.core.lib.Items.bone;
import static mariculture.core.lib.Items.bottleGas;
import static mariculture.core.lib.Items.bottleGas2;
import static mariculture.core.lib.Items.burntBrick;
import static mariculture.core.lib.Items.carbide;
import static mariculture.core.lib.Items.carrot;
import static mariculture.core.lib.Items.chest;
import static mariculture.core.lib.Items.coal;
import static mariculture.core.lib.Items.cooling;
import static mariculture.core.lib.Items.dandelion;
import static mariculture.core.lib.Items.fermentedEye;
import static mariculture.core.lib.Items.fish;
import static mariculture.core.lib.Items.ghastTear;
import static mariculture.core.lib.Items.glassLens;
import static mariculture.core.lib.Items.goldPlastic;
import static mariculture.core.lib.Items.goldSilk;
import static mariculture.core.lib.Items.goldThread;
import static mariculture.core.lib.Items.ice;
import static mariculture.core.lib.Items.life;
import static mariculture.core.lib.Items.lily;
import static mariculture.core.lib.Items.neoprene;
import static mariculture.core.lib.Items.netherWart;
import static mariculture.core.lib.Items.netherrack;
import static mariculture.core.lib.Items.pearls;
import static mariculture.core.lib.Items.plastic;
import static mariculture.core.lib.Items.plasticLens;
import static mariculture.core.lib.Items.polishedStick;
import static mariculture.core.lib.Items.potato;
import static mariculture.core.lib.Items.reeds;
import static mariculture.core.lib.Items.regen;
import static mariculture.core.lib.Items.rose;
import static mariculture.core.lib.Items.rubber;
import static mariculture.core.lib.Items.sand;
import static mariculture.core.lib.Items.snow;
import static mariculture.core.lib.Items.snowball;
import static mariculture.core.lib.Items.string;
import static mariculture.core.lib.Items.titaniumRod;
import static mariculture.core.lib.Items.transparent;
import static mariculture.core.lib.Items.whitePearl;
import static mariculture.core.lib.Items.wicker;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.items.ItemBattery;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.Extra;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.MachineMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules;
import mariculture.core.lib.MultiMeta;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.RenderMeta;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.util.FluidDictionary;
import mariculture.fishery.Fish;
import mariculture.fishery.Fishery;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
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
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.bottles, 8, FluidContainerMeta.BOTTLE_VOID), new Object[] {
			new ItemStack(Core.bottles, 1, FluidContainerMeta.BOTTLE_EMPTY), "dustRedstone", new ItemStack(Item.dyePowder, 1, Dye.INK)
		});
		
		//Oyster and Beef Pie
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.food, 1, FoodMeta.OYSTER), new Object[] {
			"foodSalt", "foodSalt", Item.beefRaw, new ItemStack(Core.oyster, 1, 0), Item.wheat, Item.egg, "dustSalt", Item.porkRaw, Item.wheat
		});
		
		//Heat Resistant Bottles
		RecipeHelper.addShapedRecipe(new ItemStack(Core.bottles, 3, FluidContainerMeta.BOTTLE_EMPTY), new Object[] {
			"G G", " G ", 'G', new ItemStack(Core.glass, 1, GlassMeta.HEAT)
		});

		//Base Brick
		RecipeHelper.addShapedRecipe(new ItemStack(Core.ores, 1, OresMeta.BASE_BRICK), new Object[] {
			"IGI", "G G", "IGI", 'I', burntBrick, 'G', Block.fenceIron
		});
		
		//Base Iron
		RecipeHelper.addShapedRecipe(new ItemStack(Core.ores, 1, OresMeta.BASE_IRON), new Object[] {
			"IGI", "G G", "IGI", 'I', "ingotIron", 'G', Block.thinGlass
		});
		
		//Base Wood
		RecipeHelper.addShapedRecipe(new ItemStack(Core.wood, 1, WoodMeta.BASE_WOOD), new Object[] {
			"IGI", "G G", "IGI", 'I', "logWood", 'G', Block.fence
		});
		
	//Machines
		//Air Pump
		RecipeHelper.addShapedRecipe(new ItemStack(Core.rendered, 1, RenderMeta.AIR_PUMP), new Object[] {
			"WGW", "PRP", "PMP", 'G', "glass", 'R', "dustRedstone", 'P', "plankWood", 'M', Block.pistonBase,
			'W', new ItemStack(Core.crafting, 1, CraftingMeta.WHEEL)
		});
		
		//Copper Tank
		RecipeHelper.addShapedRecipe(new ItemStack(Core.tanks, 2, TankMeta.TANK), new Object[] {
			"CWC", "WGW", "CWC", 'C', "ingotCopper", 'W', "plankWood", 'G', "glass"
		});
				
		//Storage Bookshelf
		RecipeHelper.addShapedRecipe(new ItemStack(Core.machines, 1, MachineMeta.BOOKSHELF), new Object[] {
			"SPS", "PCP", "SSS", 'P', "plankWood", 'S', Block.bookShelf, 'C', Block.chest
		});
		
		//Crucible Furnace
		RecipeHelper.addShapedRecipe(new ItemStack(Core.machines, 1, MachineMeta.LIQUIFIER), new Object[] {
			" L ", "BGB", "HCH", 
			'B', burntBrick,
			'L', Item.bucketLava, 
			'G', new ItemStack(Core.tanks, 1, TankMeta.TANK), 
			'H', new ItemStack(Core.crafting, 1, CraftingMeta.HEATER),
			'C', new ItemStack(Core.ores, 1, OresMeta.BASE_BRICK)
		});
		
		//Anvil Recipe
		RecipeHelper.addShapedRecipe(new ItemStack(Core.rendered, 1, RenderMeta.ANVIL_1), new Object[] {
			"CCC", " N ", "BBB",
			'C', new ItemStack(Core.ores, 1, OresMeta.BASE_BRICK),
			'B', burntBrick,
			'N', Block.netherBrick
		});

		//VAT Recipe
		RecipeHelper.addShapedRecipe(new ItemStack(Core.multi, 1, MultiMeta.VAT), new Object[] {
			"C C", "C C", "CCC", 'C', "ingotCopper"
		});
		
		//Ingot Caster
		RecipeHelper.addShapedRecipe(new ItemStack(Core.rendered, 1, RenderMeta.INGOT_CASTER), new Object[] {
			" B ", "BBB", " B ", 'B', burntBrick
		});
		
		RecipeHelper.addShapedRecipe(new ItemStack(Core.rendered, 1, RenderMeta.BLOCK_CASTER), new Object[] {
			"BBB", "B B", "BBB", 'B', burntBrick
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
			'P', burntBrick, 'S', Block.netherBrick
		});

		//Pearl Bricks
		for (int i = 0; i < 12; i++) {
			RecipeHelper.add2x2Recipe(new ItemStack(Core.pearl, 1, i), new ItemStack(Core.pearls, 1, i));
			RecipeHelper.addUncraftingRecipe(new ItemStack(Core.pearls, 4, i), new ItemStack(Core.pearl, 1, i));
		}
		
		//Piston
		RecipeHelper.addShapedRecipe(new ItemStack(Block.pistonBase, 1), new Object[] {
			"TTT", "#X#", "#R#", '#', "cobblestone", 'X', "ingotAluminum", 'R', "dustRedstone", 'T', "plankWood"
		});
	}

	private static void addCraftingItems() {
		RecipeHelper.addShapedRecipe(life, new Object[] {"DSR", "FHB", "PAC", 'D', dandelion, 'S', "treeSapling", 'R', rose, 'F', fish, 'H', regen, 'B', bait, 'P', potato, 'A', lily, 'C', carrot});
		RecipeHelper.addVatItemRecipe(new ItemStack(string), FluidDictionary.gold, MetalRates.INGOT * 4, goldSilk, 5);
		RecipeHelper.addShapedRecipe(goldThread, new Object[] {"ABA", "ABA", 'B', polishedStick, 'A', goldSilk});
		RecipeHelper.addShapedRecipe(neoprene,  new Object[] {"IPI", "PEP", "IPI", 'I', rubber, 'P', pearls, 'E', bottleGas});
		RecipeHelper.addShapedRecipe(new ItemStack(Core.crafting, 2, CraftingMeta.NEOPRENE),  new Object[] {"IPI", "PEP", "IPI", 'I', rubber, 'P', pearls, 'E', bottleGas2});
		RecipeHelper.addVatItemRecipe(new ItemStack(Core.ores, 4, OresMeta.LIMESTONE), FluidDictionary.natural_gas, 5000, plastic, 45);
		if(FluidRegistry.getFluid("bioethanol") != null) RecipeHelper.addVatItemRecipe(new ItemStack(Core.ores, 4, OresMeta.LIMESTONE), "bioethanol", 10000, plastic, 60);
		RecipeHelper.addShapedRecipe(plasticLens, new Object[] {" N ", "NGN", " N ", 'N', neoprene, 'G', transparent});
		RecipeHelper.addShapedRecipe(glassLens, new Object[] {" P ", "PGP", " P ", 'P', "plankWood", 'G', "glass"});
		RecipeHelper.addAnvilRecipe(blockAluminum, new ItemStack(Core.crafting, 8, CraftingMeta.ALUMINUM_SHEET), 50);
		RecipeHelper.addShapedRecipe(new ItemStack(Core.crafting, 2, CraftingMeta.HEATER), new Object[] {"CCC", "CCC", 'C', carbide});
		RecipeHelper.addShapedRecipe(cooling, new Object[] {"  P", "PI ", "  P", 'P', "plankWood", 'I', "ingotIron"});
		RecipeHelper.addShapedRecipe(cooling, new Object[] {" P ", " I ", "P P", 'P', "plankWood", 'I', "ingotIron"});
		RecipeHelper.addShapedRecipe(cooling, new Object[] {"P  ", " IP", "P  ", 'P', "plankWood", 'I', "ingotIron"});
		RecipeHelper.addShapedRecipe(cooling, new Object[] {"P P", " I ", " P ", 'P', "plankWood", 'I', "ingotIron"});
		RecipeHelper.addShapedRecipe(carbide, new Object[] {" S ", "FBF", " S ", 'F', coal, 'S', sand, 'B', blockClay});
		RecipeHelper.addWheelRecipe(new ItemStack(Core.crafting, 3, CraftingMeta.WHEEL), "ingotIron", "slabWood");
		RecipeHelper.addCrossHatchRecipe(wicker, "stickWood", reeds);
		RecipeHelper.addVatItemRecipe(new ItemStack(Core.crafting, 4, CraftingMeta.PLASTIC), FluidDictionary.gold, MetalRates.BLOCK, goldPlastic, 60 * 5);
		RecipeHelper.addAnvilRecipe(blockTitanium, new ItemStack(Core.crafting, 8, CraftingMeta.TITANIUM_SHEET), 150);
		RecipeHelper.addVatItemRecipe(new ItemStack(Item.netherrackBrick), "lava", 250, burntBrick, 8);
		if(Extra.OVERWORLD) {
			RecipeHelper.addVatItemRecipe(new ItemStack(Item.brick), "lava", 500, burntBrick, 16);
		}
		
		RecipeHelper.addVatItemRecipe(new ItemStack(Item.stick),  FluidDictionary.titanium, MetalRates.INGOT * 3, titaniumRod, 60);
	}
	
	private static void addMetalRecipes() {		
		RecipeHelper.add3x3Recipe(new ItemStack(Item.ingotIron), "nuggetIron");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.NUGGET_IRON), "ingotIron");
		RecipeHelper.add3x3Recipe(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_MAGNESIUM), "nuggetMagnesium");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.NUGGET_MAGNESIUM), "ingotMagnesium");
		RecipeHelper.add3x3Recipe(new ItemStack(Core.ores, 1, OresMeta.MAGNESIUM_BLOCK), "ingotMagnesium");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.INGOT_MAGNESIUM), "blockMagnesium");
		RecipeHelper.add3x3Recipe(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_TITANIUM), "nuggetTitanium");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.NUGGET_TITANIUM), "ingotTitanium");
		RecipeHelper.add3x3Recipe(new ItemStack(Core.ores, 1, OresMeta.TITANIUM_BLOCK), "ingotTitanium");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.INGOT_TITANIUM), "blockTitanium");
		RecipeHelper.add3x3Recipe(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_ALUMINUM), "nuggetAluminum");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.NUGGET_ALUMINUM), "ingotAluminum");
		RecipeHelper.add3x3Recipe(new ItemStack(Core.ores, 1, OresMeta.ALUMINUM_BLOCK), "ingotAluminum");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.INGOT_ALUMINUM), "blockAluminum");
		RecipeHelper.add3x3Recipe(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_COPPER), "nuggetCopper");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.NUGGET_COPPER), "ingotCopper");
		RecipeHelper.add3x3Recipe(new ItemStack(Core.ores, 1, OresMeta.COPPER_BLOCK), "ingotCopper");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.INGOT_COPPER), "blockCopper");
		RecipeHelper.add3x3Recipe(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_RUTILE), "nuggetRutile");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.NUGGET_RUTILE), "ingotRutile");
		RecipeHelper.add3x3Recipe(new ItemStack(Core.ores, 1, OresMeta.RUTILE_BLOCK), "ingotRutile");
		RecipeHelper.addUncraftingRecipe(new ItemStack(Core.materials, 9, MaterialsMeta.INGOT_RUTILE), "blockRutile");
		RecipeHelper.addSmelting(Core.ores.blockID, OresMeta.COPPER, new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_COPPER), 0.5F);
	}
	
	private static void addUpgradeRecipes() {
		//Storage
		ItemStack previous = RecipeHelper.addUpgrade(UpgradeMeta.BASIC_STORAGE, new Object[] {"WPW", "DCD", "WPW", 'D', "ingotCopper", 'P', "plankWood", 'C', chest, 'W', wicker});
		previous = RecipeHelper.addUpgrade(UpgradeMeta.STANDARD_STORAGE, new Object[] {"WCW", "CUC", "STS", 'C', "ingotCopper", 'S', "slabWood", 'T', chest, 'W', wicker, 'U', previous});
		previous = RecipeHelper.addUpgrade(UpgradeMeta.ADVANCED_STORAGE, new Object[] {"CSC", "AUA", "WTW", 'C', "ingotCopper", 'S', "slabWood", 'T', chest, 'A', aluminumSheet, 'W', wicker, 'U', previous});
		RecipeHelper.addUpgrade(UpgradeMeta.ULTIMATE_STORAGE, new Object[] {"GWG", "WUW", "ATA", 'G', "ingotGold", 'T', chest, 'A', aluminumSheet, 'W', wicker, 'U', previous});

		//Cooling
		previous = RecipeHelper.addUpgrade(UpgradeMeta.BASIC_COOLING, new Object[] {" S ", "CBC", " S ", 'S', snowball, 'B', snow, 'C', cooling});
		previous = RecipeHelper.addUpgrade(UpgradeMeta.STANDARD_COOLING, new Object[] {"ACA", "SUS", "CAC", 'S', snowball, 'A', "ingotAluminum", 'C', cooling, 'U', previous});
		previous = RecipeHelper.addUpgrade(UpgradeMeta.ADVANCED_COOLING, new Object[] {"CTC", "IUI", "TRT", 'I', ice, 'R', "ingotIron", 'T', "ingotAluminum", 'C', cooling, 'U', previous});
		RecipeHelper.addUpgrade(UpgradeMeta.ULTIMATE_COOLING, new Object[] {"TCT", "IUI", "CDC", 'I', ice, 'D', "ingotAluminum", 'T', "ingotTitanium", 'C', cooling, 'U', previous});
		
		//Basic Heating
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_HEATING), new Object[] {
			"HIH", 'I', "ingotIron", 'H', new ItemStack(Core.crafting, 1, CraftingMeta.HEATER)
		});
		
		//Standard Heating
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_HEATING), new Object[] {
			"A A", "HUH", " A ", 'A', "ingotAluminum",
			'H', new ItemStack(Core.crafting, 1, CraftingMeta.HEATER),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_HEATING)
		});
		
		//Advanced Heating
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_HEATING), new Object[] {
			"IHI", "TUT", "IHI", 'T', "ingotTitanium", 'I', "ingotIron",
			'H', new ItemStack(Core.crafting, 1, CraftingMeta.HEATER),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_HEATING)
		});
	
		//Ultimate Heating
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_HEATING), new Object[] {
			"TDT", "HUH", "GTG", 'G', "ingotGold", 'T', "ingotTitanium", 'D', Item.blazeRod,
			'H', new ItemStack(Core.crafting, 1, CraftingMeta.HEATER),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_HEATING)
		});
		
		ItemStack heart = (Modules.isActive(Modules.fishery))? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_HEALTH): new ItemStack(Item.potion, 1, 8197);
		
		//Purity and Filtrator
		previous = RecipeHelper.addUpgrade(UpgradeMeta.BASIC_PURITY, new Object[] {"MPM", "PIP", "MPM", 'I', "dyeRed", 'M', "ingotAluminum",'P', whitePearl});
		ItemStack previous2 = RecipeHelper.addUpgrade(UpgradeMeta.FILTER, new Object[] {"MPM", "CUC", "AMA", 'A', "ingotAluminum", 'M', "ingotMagnesium", 'P', bluePearl, 'C', cooling, 'U', previous});
		RecipeHelper.addUpgrade(UpgradeMeta.FILTER_2, new Object[] {"UAU", 'A', "ingotAluminum", 'U', previous2});
		RecipeHelper.addUpgrade(UpgradeMeta.STANDARD_PURITY, new Object[] {"PHP", "NUN", "MHM", 'H', heart, 'N', "ingotTitanium", 'M', "ingotAluminum", 'P', whitePearl, 'U', previous});
		
		//Advanced Purity
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_PURITY), new Object[] {
			"PSP", "AUA", "TPT", 'A', heart, 'T', "ingotTitanium",
			'P', new ItemStack(Core.pearls, 1, PearlColor.WHITE),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_PURITY),
			'S', new ItemStack(Core.crafting, 1, CraftingMeta.GOLDEN_SILK)
		});
		
		//Ultimate Purity
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_PURITY), new Object[] {
			"PSP", "TUT", "PAP", 'A', heart, 'T', "ingotTitanium",
			'P', new ItemStack(Core.pearls, 1, PearlColor.WHITE),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_PURITY),
			'S', new ItemStack(Core.crafting, 1, CraftingMeta.GOLDEN_THREAD)
		});
		
		ItemStack attack = (Modules.isActive(Modules.fishery))? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK): new ItemStack(Item.potion, 1, 8204);
		ItemStack poison = (Modules.isActive(Modules.fishery))? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_POISON): new ItemStack(Item.potion, 1, 8228);
		ItemStack night = (Modules.isActive(Modules.fishery))? new ItemStack(Fishery.fishyFood, 1, Fish.night.getID()): new ItemStack(Item.enderPearl);
		ItemStack ender = (Modules.isActive(Modules.fishery))? new ItemStack(Fishery.fishyFood, 1, Fish.ender.getID()): new ItemStack(Item.eyeOfEnder);
		
		//Impurity and salinator
		previous = RecipeHelper.addUpgrade(UpgradeMeta.BASIC_IMPURITY, new Object[] {"NWN", "ESE", "NGN", 'N', netherrack, 'W', netherWart, 'E', fermentedEye, 'S', bone, 'G', "ingotGold"});
		previous2 = RecipeHelper.addUpgrade(UpgradeMeta.SALINATOR, new Object[] {"ASA", "SUS", "MAM", 'A', "ingotAluminum", 'S', "foodSalt", 'M', "ingotMagnesium", 'U', previous});
		RecipeHelper.addUpgrade(UpgradeMeta.SALINATOR_2, new Object[] {"UAU", 'A', "ingotAluminum", 'U', previous2});
		previous = RecipeHelper.addUpgrade(UpgradeMeta.STANDARD_IMPURITY, new Object[] {"TGT", "SUS", "PFP", 'T', ghastTear, 'F', fermentedEye, 'P', poison, 'S', attack, 'G', goldSilk, 'U', previous});
		previous = RecipeHelper.addUpgrade(UpgradeMeta.ADVANCED_IMPURITY, new Object[] {"HGH", "FUF", "SPS", 'F', night, 'P', poison, 'H', attack, 'S', fermentedEye, 'G', "blockGold", 'U', previous});
		RecipeHelper.addUpgrade(UpgradeMeta.ULTIMATE_IMPURITY, new Object[] {"SGS", "PUP", "FSF", 'F', ender, 'P', poison, 'S', attack, 'G', goldThread, 'U', previous});
		
		//Ethereal
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ETHEREAL), new Object[] {
			"PUP", "GEG", "PDP", 'P', Item.enderPearl, 'G', "ingotGold", 
			'E', Item.eyeOfEnder, 'D', Item.diamond, 'U', Block.torchRedstoneActive
		});
		
		//Basic Speed Upgrade
		RecipeHelper.addWheelRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_SPEED), "ingotIron", Item.sugar);
		
		//Standard Speed Upgrade
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_SPEED), new Object[] {
			" S ", "AUA", " S ", 'A', "ingotAluminum", 'S', Item.sugar,
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_SPEED)
		});
		
		//Advanced Speed Upgrade
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_SPEED), new Object[] {
			"ASA", "TUT", "SNS", 'A', "ingotAluminum", 'S', Item.sugar, 'T', "ingotTitanium", 'N', Block.ice,
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_SPEED)
		});
		
		//Ultimate Speed Upgrade
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_SPEED), new Object[] {
			"TRT", "SUS", "ARA", 'A', new ItemStack(Core.crafting, 1, CraftingMeta.ALUMINUM_SHEET), 'S', Item.sugar, 
			'T', "ingotTitanium", 'R', Block.railPowered,
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_SPEED)
		});
		
		//Basic Capacitor
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_RF), new Object[] {
			" T ", "CRC", 'T', Block.torchRedstoneActive, 'C', "ingotCopper", 'R', "dustRedstone"
		});
		
		//Standard Capacitor
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_RF), new Object[] {
			"CTC", "QUQ", "RCR", 'C', "ingotCopper", 'T', Block.torchRedstoneActive, 'Q', Item.netherQuartz, 'R', Item.redstoneRepeater,
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_RF)
		});
		
		//Advanced Capacitor
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_RF), new Object[] {
			" D ", "RUR", "QCQ", 'D', "dustRedstone", 'Q', Item.netherQuartz, 'R', Item.redstoneRepeater,
			'C', new ItemStack(Core.batteryCopper, 1, OreDictionary.WILDCARD_VALUE),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_RF)
		});
		
		//Ultimate Capacitor
		RecipeHelper.addShapedRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_RF), new Object[] {
			" C ", "RUR", "QBQ", 'Q', Item.comparator, 'R', "dustRedstone", 'B', "blockRedstone",
			'C', new ItemStack(Core.batteryTitanium, 1, OreDictionary.WILDCARD_VALUE),
			'U', new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_RF)
		});
	}
	
	public static void addAnvilRecipes() {
		RecipeHelper.addAnvilRecipe(new ItemStack(Item.bone), new ItemStack(Item.dyePowder, 4, Dye.BONE), 10);
		RecipeHelper.addAnvilRecipe(new ItemStack(Block.plantRed), new ItemStack(Item.dyePowder, 3, Dye.RED), 10);
		RecipeHelper.addAnvilRecipe(new ItemStack(Block.plantYellow), new ItemStack(Item.dyePowder, 3, Dye.YELLOW), 10);
		RecipeHelper.addAnvilRecipe(new ItemStack(Block.cobblestone), new ItemStack(Block.gravel), 25);
		RecipeHelper.addAnvilRecipe(new ItemStack(Block.gravel), new ItemStack(Block.sand), 25);
	}
}
