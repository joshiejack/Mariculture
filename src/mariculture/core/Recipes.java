package mariculture.core;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter;
import mariculture.api.core.RecipeSmelter.SmelterOutput;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.items.ItemBattery;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.Modules;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.SingleMeta;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.lib.UtilMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.util.FluidDictionary;
import mariculture.fishery.Fishery;
import mariculture.plugins.PluginTConstruct;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes {
	public static void add() {
		RecipesSmelting.add();
		addCraftingItems();
		addMetalRecipes();
		addUpgradeRecipes();

	//Items
		//Copper Battery
		RecipeHelper.addShapedRecipe(ItemBattery.make(new ItemStack(Core.batteryCopper), 10000), new Object[] {
			" I ", "TRT", "TRT", 'I', "ingotIron", 'R', "dustRedstone", 'T', "ingotCopper"
		});

		//Titanium Battery
		RecipeHelper.addShapedRecipe(ItemBattery.make(new ItemStack(Core.batteryTitanium), 50000), new Object[] {
			" I ", "TRT", "TRT", 'I', "ingotIron", 'R', "dustRedstone", 'T', "ingotTitanium"
		});

		//Void Bottle
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.liquidContainers, 8, FluidContainerMeta.BOTTLE_VOID), new Object[] {
			Item.glassBottle, "dustRedstone", new ItemStack(Item.dyePowder, 1, Dye.INK)
		});

	//Basic Blocks
		//Limestone Brick
		RecipeHelper.add4x4Recipe(new ItemStack(Core.oreBlocks, 4, OresMeta.LIMESTONE_BRICK), Core.oreBlocks, OresMeta.LIMESTONE);
		//Limestone Smooth
		RecipeHelper.add4x4Recipe(new ItemStack(Core.oreBlocks, 4, OresMeta.LIMESTONE_CHISELED), Core.oreBlocks, OresMeta.LIMESTONE_CHISELED);
		
		//Base Brick
		RecipeHelper.addShapedRecipe(new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_BRICK), new Object[] {
			"IGI", "G G", "IGI", 'I', new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK), 'G', Block.fenceIron
		});
		
		//Base Iron
		RecipeHelper.addShapedRecipe(new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_IRON), new Object[] {
			"IGI", "G G", "IGI", 'I', "ingotIron", 'G', Block.thinGlass
		});
		
		//Base Wood
		RecipeHelper.addShapedRecipe(new ItemStack(Core.woodBlocks, 1, WoodMeta.BASE_WOOD), new Object[] {
			"IGI", "G G", "IGI", 'I', "logWood", 'G', Block.fence
		});
		
	//Machines
		//Copper Tank
		RecipeHelper.addShapedRecipe(new ItemStack(Core.tankBlocks, 1, TankMeta.TANK), new Object[] {
			"CWC", "WGW", "CWC", 'C', "ingotCopper", 'W', "plankWood", 'G', "glass"
		});
				
		//Storage Bookshelf
		RecipeHelper.addShapedRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.BOOKSHELF), new Object[] {
			"SPS", "PCP", "SSS", 'P', "plankWood", 'S', Block.bookShelf, 'C', Block.chest
		});
		
		//Crucible Furnace
		RecipeHelper.addShapedRecipe(new ItemStack(Core.utilBlocks, 1, UtilMeta.LIQUIFIER), new Object[] {
			" L ", "BGB", "HCH", 
			'B', new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK),
			'L', Item.bucketLava, 
			'G', "glass", 
			'H', new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER),
			'C', new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_BRICK)
		});
		
		//Anvil Recipe
		RecipeHelper.addShapedRecipe(new ItemStack(Core.singleBlocks, 1, SingleMeta.ANVIL_1), new Object[] {
			"CCC", " B ", "BBB",
			'C', new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_BRICK),
			'B', new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK)
		});
		
		//Pearl Hammer
		RecipeHelper.addShapedRecipe(new ItemStack(Core.hammer), new Object[] {
			"PP ", " SP", "S  ",
			'P', new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK),
			'S', "stickWood"
		});
		
		//VAT Recipe
		RecipeHelper.addShapedRecipe(new ItemStack(Core.doubleBlock, 1, DoubleMeta.VAT), new Object[] {
			"C C", "C C", "CCC", 'C', "ingotCopper"
		});
		
		//Ingot Caster
		RecipeHelper.addShapedRecipe(new ItemStack(Core.doubleBlock, 1, DoubleMeta.VAT), new Object[] {
			" B ", "BBB", " B ", 'B', new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK)
		});

		FurnaceRecipes.smelting().addSmelting(Core.oreBlocks.blockID, OresMeta.LIMESTONE,
				new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE_SMOOTH), 0.1F);

		for (int i = 0; i < 12; i++) {
			GameRegistry.addRecipe(new ItemStack(Core.pearlBrick, 1, i),
					new Object[] { "PP ", "PP ", Character.valueOf('P'), new ItemStack(Core.pearls, 1, i) });
			
			GameRegistry.addShapelessRecipe(new ItemStack(Core.pearls, 4, i),
					new Object[] { new ItemStack(Core.pearlBrick, 1, i) });
		}

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Block.pistonBase, 1), new Object[] { "TTT", "#X#", "#R#",
						Character.valueOf('#'), Block.cobblestone, Character.valueOf('X'), "ingotAluminum",
						Character.valueOf('R'), Item.redstone, Character.valueOf('T'), "plankWood" }));
	}

	private static void addCraftingItems() {
		//Golden Silk > 5 Seconds, 1 x Ingot Gold(mB) + String
		RecipeHelper.addVatItemRecipe(new ItemStack(Item.silk), FluidDictionary.gold, MetalRates.INGOT, 
				new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_SILK), 5);
		
		//Golden Thread
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_THREAD), new Object[] {
			"ABA", "ABA",
			'A', new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_SILK),
			'B', new ItemStack(Core.craftingItem, 1, CraftingMeta.POLISHED_STICK)
		});
	
		// Polished Stick > 15 Seconds > 5000mB Fish Oil + Stick
		RecipeHelper.addVatItemRecipe(new ItemStack(Item.stick), FluidDictionary.fish_oil, 5000, 
				new ItemStack(Core.craftingItem, 1, CraftingMeta.POLISHED_STICK), 15);
		
		//Titanium Rod >> 30 Seconds >> With Tinkers(6500mB Fish Oil + Tough Rod, without 2 Ingots Titanium + 2 Polished Sticks)
		if(Loader.isModLoaded("TConstruct")) {
			PluginTConstruct.addRod = true;
		} else {
			RecipeHelper.addVatItemRecipe(new ItemStack(Core.craftingItem, 2, CraftingMeta.POLISHED_STICK), 
					FluidDictionary.titanium, MetalRates.INGOT * 2,  
					new ItemStack(Core.craftingItem, 1, CraftingMeta.ROD_TITANIUM), 30);
		}
		
		//Neoprene
		GameRegistry.addRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.NEOPRENE), new Object[] { "IPI", "PEP", "IPI", 
				Character.valueOf('I'), new ItemStack(Item.dyePowder, 1, Dye.INK), 
				Character.valueOf('P'), new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE), 
				Character.valueOf('E'), new ItemStack(Core.liquidContainers, 1, FluidContainerMeta.BOTTLE_GAS) });
		//Plastic > 60 Seconds > 32 Buckets of Molten Glass + 16 Limestone
		RecipeHelper.addVatItemRecipe(new ItemStack(Block.glass), FluidDictionary.glass, 32000, 
				new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC), 60);
		//Plastic Lens
		GameRegistry.addRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.LENS), new Object[] { " N ", "NGN", " N ", 
				Character.valueOf('N'), new ItemStack(Core.craftingItem, 1, CraftingMeta.NEOPRENE), 
				Character.valueOf('G'), new ItemStack(Core.glassBlocks, 1, GlassMeta.PLASTIC) });
		//Glass Lens
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.LENS_GLASS), new Object[] {
			" P ", "PGP", " P ", 'P', "plankWood", 'G', "glass"
		});
		
		//Aluminum Sheet
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 3, CraftingMeta.ALUMINUM_SHEET), new Object[] {
			"I I", " F ", "I I", 'I', "ingotAluminum", 'F', Item.flintAndSteel
		});

		//Heating
		GameRegistry.addRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER), new Object[] { "CCC", "CCC",
				Character.valueOf('C'), new ItemStack(Core.craftingItem, 1, CraftingMeta.CARBIDE) });
		//Cooling
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER), new Object[] { "  P", "PI ", "  P", 
					Character.valueOf('P'), "plankWood", 
					Character.valueOf('I'), "ingotIron" }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER), new Object[] { " P ", " I ", "P P", 
					Character.valueOf('P'), "plankWood", 
					Character.valueOf('I'), "ingotIron" }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER), new Object[] { "P  ", " IP", "P  ", 
					Character.valueOf('P'), "plankWood", 
					Character.valueOf('I'), "ingotIron" }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER), new Object[] { "P P", " I ", " P ", 
					Character.valueOf('P'), "plankWood", 
					Character.valueOf('I'), "ingotIron" }));
		//Carbide
		GameRegistry.addRecipe(new ItemStack(Core.craftingItem, 3, CraftingMeta.CARBIDE), new Object[] { "CSF", "FBS", "SFC", 
				Character.valueOf('C'), Item.clay, 
				Character.valueOf('F'), new ItemStack(Item.coal, 1, 0),
				Character.valueOf('S'), Block.sand,
				Character.valueOf('B'), Block.blockClay});
		//Wheel
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL), new Object[] { " I ", "ISI", " I ", 
					Character.valueOf('S'), "slabWood", 
					Character.valueOf('I'), "ingotIron" }));
		//Wicker
		CraftingManager
			.getInstance()
			.getRecipeList()
			.add(new ShapedOreRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER), new Object[] { "CAC", "ACA", "CAC", 
				Character.valueOf('A'), "stickWood", 
				Character.valueOf('C'), Item.reed }));
		//Yellow Plastic > 5 Minutes > 1 Block of Gold(mB) + 4 Plastic
		RecipeHelper.addVatItemRecipe(new ItemStack(Core.craftingItem, 4, CraftingMeta.PLASTIC), 
				FluidDictionary.gold, MetalRates.BLOCK,  new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC_YELLOW), 60 * 5);
		//Melt Yellow/Gold plastic back to a block of gold and 1 piece of plastic
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC_YELLOW), RecipesSmelting.gold, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.gold, MetalRates.BLOCK), 
							new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC), 1)));
		
		//Titanium Sheet
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 3, CraftingMeta.TITANIUM_SHEET), new Object[] {
			"I I", " F ", "I I",
			Character.valueOf('I'), "ingotTitanium",
			Character.valueOf('F'), Item.flintAndSteel
		});
		
		//Burnt Brick > (Netherbrick > Burnt Brick)
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK), new Object[] {
			new ItemStack(Item.coal, 1, OreDictionary.WILDCARD_VALUE), Item.netherrackBrick
		});
	
		//Burnt Brick > Brick + Molten Coal + Molten Copper
		RecipeHelper.addShapedRecipe(new ItemStack(Core.craftingItem, 2, CraftingMeta.BURNT_BRICK), new Object[] {
			" C ", "FBF", " S ",
			Character.valueOf('C'), new ItemStack(Item.coal, 1, OreDictionary.WILDCARD_VALUE),
			Character.valueOf('F'), Item.flint,
			Character.valueOf('B'), Item.brick,
			Character.valueOf('S'), Item.flintAndSteel
		});
	}
	
	private static void addMetalRecipes() {		
		RecipeHelper.add9x9Recipe(new ItemStack(Core.oreBlocks, 1, OresMeta.MAGNESIUM_BLOCK), "ingotMagnesium");
		RecipeHelper.add9x9RecipeUndo(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_MAGNESIUM), "blockMagnesium");
		RecipeHelper.add9x9Recipe(new ItemStack(Core.oreBlocks, 1, OresMeta.TITANIUM_BLOCK), "ingotTitanium");
		RecipeHelper.add9x9RecipeUndo(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_TITANIUM), "blockTitanium");
		RecipeHelper.add9x9Recipe(new ItemStack(Core.oreBlocks, 1, OresMeta.ALUMINUM_BLOCK), "ingotAluminum");
		RecipeHelper.add9x9RecipeUndo(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_ALUMINUM), "blockAluminum");
		RecipeHelper.add9x9Recipe(new ItemStack(Core.oreBlocks, 1, OresMeta.COPPER_BLOCK), "ingotCopper");
		RecipeHelper.add9x9RecipeUndo(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_COPPER), "blockCopper");
		RecipeHelper.addSmelting(Core.oreBlocks.blockID, OresMeta.COPPER, 
									new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_COPPER), 0.5F);
	}
	
	private static void addUpgradeRecipes() {
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_STORAGE), new Object[] {
						"WPW", "DCD", "WPW", Character.valueOf('D'), 
						Block.dirt, Character.valueOf('P'), "plankWood",
						Character.valueOf('C'), Block.chest, 
						Character.valueOf('W'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_STORAGE), new Object[] {
						"PWP", "SUS", "PWP", 
						Character.valueOf('S'), "ingotCopper", 
						Character.valueOf('P'), "slabWood",
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_STORAGE),
						Character.valueOf('W'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_STORAGE), new Object[] {
						"AWA", "MUM", "WAW", 
						Character.valueOf('A'), "ingotCopper", 
						Character.valueOf('M'), "ingotAluminum", 
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_STORAGE), 
						Character.valueOf('W'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_STORAGE), new Object[] {
						"ATA", "WUW", "TCT", 
						Character.valueOf('A'), "ingotCopper", 
						Character.valueOf('T'), "ingotAluminum", 
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_STORAGE), 
						Character.valueOf('W'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER), 
						Character.valueOf('C'), new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_COOLING), new Object[] {
						" S ", "CBC", " S ", 
						Character.valueOf('S'), Item.snowball, 
						Character.valueOf('B'), Block.blockSnow, 
						Character.valueOf('C'), new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_COOLING), new Object[] {
						"ACA", "SUS", "CAC", 
						Character.valueOf('S'), Item.snowball, 
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_COOLING), 
						Character.valueOf('C'), new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER), 
						Character.valueOf('A'), "ingotAluminum" }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_COOLING), new Object[] {
						"CTC", "IUI", "TRT", 
						Character.valueOf('I'), Block.ice, 
						Character.valueOf('R'), "ingotIron",
						Character.valueOf('C'), new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER),
						Character.valueOf('T'), "ingotTitanium", 
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_COOLING) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_COOLING), new Object[] {
						"TCT", "IUI", "GDG", 
						Character.valueOf('I'), Block.ice, 
						Character.valueOf('G'), Item.ingotGold,
						Character.valueOf('C'), new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER),
						Character.valueOf('T'), "ingotTitanium", 
						Character.valueOf('D'), Item.diamond,
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_COOLING) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_HEATING), new Object[] {
						"HIH", 
						Character.valueOf('I'), "ingotIron", 
						Character.valueOf('H'), new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_HEATING), new Object[] {
						"A A", "HUH", " A ", 
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_HEATING), 
						Character.valueOf('H'), new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER), 
						Character.valueOf('A'), "ingotAluminum" }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_HEATING), new Object[] {
						"IHI", "TUT", "IHI", 
						Character.valueOf('T'), "ingotTitanium", 
						Character.valueOf('I'), "ingotIron", 
						Character.valueOf('H'), new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER), 
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_HEATING) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_HEATING), new Object[] {
						"TDT", "HUH", "GTG", 
						Character.valueOf('G'), Item.ingotGold, 
						Character.valueOf('H'), new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER), 
						Character.valueOf('T'),
						"ingotTitanium", 
						Character.valueOf('D'), Item.diamond, 
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_HEATING) }));
		
		ItemStack heart = (Modules.fishery.isActive())? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_HEALTH): new ItemStack(Item.potion, 1, 8197);
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_PURITY), new Object[] {
						"MPM", "PIP", "MPM", 
						Character.valueOf('P'), new ItemStack(Core.pearls, 1, PearlColor.WHITE),
						Character.valueOf('I'), heart, 
						Character.valueOf('M'), "ingotAluminum" }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_PURITY), new Object[] {
						"PHP", "NUN", "MHM", 
						Character.valueOf('P'), new ItemStack(Core.pearls, 1, PearlColor.WHITE),
						Character.valueOf('H'), heart, 
						Character.valueOf('M'), "ingotAluminum",
						Character.valueOf('N'), "ingotTitanium",
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_PURITY) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_PURITY), new Object[] {
						"PSP", "AUA", "TPT", 
						Character.valueOf('P'), new ItemStack(Core.pearls, 1, PearlColor.WHITE),
						Character.valueOf('S'), new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_SILK),
						Character.valueOf('T'), "ingotTitanium", 
						Character.valueOf('A'), heart,
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_PURITY) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_PURITY), new Object[] {
						"PSP", "TUT", "PAP", 
						Character.valueOf('P'), new ItemStack(Core.pearls, 1, PearlColor.WHITE),
						Character.valueOf('S'), new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_THREAD),
						Character.valueOf('T'), "ingotTitanium", 
						Character.valueOf('A'), heart,
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_PURITY) }));
		
		ItemStack attack = (Modules.fishery.isActive())? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK): new ItemStack(Item.potion, 1, 8204);
		ItemStack poison = (Modules.fishery.isActive())? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_POISON): new ItemStack(Item.potion, 1, 8228);
		ItemStack night = (Modules.fishery.isActive())? new ItemStack(Fishery.fishyFood, 1, Fishery.night.fishID): new ItemStack(Item.enderPearl);
		ItemStack ender = (Modules.fishery.isActive())? new ItemStack(Fishery.fishyFood, 1, Fishery.ender.fishID): new ItemStack(Item.eyeOfEnder);
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_IMPURITY), new Object[] {
						"NWN", "ESE", "NGN", 
						Character.valueOf('N'), Block.netherrack, 
						Character.valueOf('W'), Item.netherStalkSeeds, 
						Character.valueOf('E'), Item.fermentedSpiderEye, 
						Character.valueOf('S'), attack,
						Character.valueOf('G'), Item.ingotGold }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_IMPURITY), new Object[] {
						"TGT", "SUS", "PFP", 
						Character.valueOf('T'), Item.ghastTear, 
						Character.valueOf('F'), Item.fermentedSpiderEye,
						Character.valueOf('P'), poison, 
						Character.valueOf('S'), attack, 
						Character.valueOf('G'),
						new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_SILK), 
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_IMPURITY) }));
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_IMPURITY), new Object[] {
						"HGH", "FUF", "SPS", 
						Character.valueOf('F'), night, 
						Character.valueOf('P'), poison, 
						Character.valueOf('H'), attack, 
						Character.valueOf('S'), Item.fermentedSpiderEye,
						Character.valueOf('G'), Block.blockGold, 
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.STANDARD_IMPURITY) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ULTIMATE_IMPURITY), new Object[] {
						"SGS", "PUP", "FSF", 
						Character.valueOf('F'), ender, 
						Character.valueOf('P'), poison, 
						Character.valueOf('S'), attack,
						Character.valueOf('G'), new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_THREAD),
						Character.valueOf('U'), new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_IMPURITY) }));
		
		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Core.upgrade, 1, UpgradeMeta.ETHEREAL), new Object[] { "PUP",
						"GEG", "PDP", 
						Character.valueOf('P'), Item.enderPearl, 
						Character.valueOf('G'), Item.ingotGold,
						Character.valueOf('E'), Item.eyeOfEnder, 
						Character.valueOf('D'), Item.diamond,
						Character.valueOf('U'), Block.torchRedstoneActive }));
	}
}
