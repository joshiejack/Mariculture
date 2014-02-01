package mariculture.core.guide;

import mariculture.api.core.MaricultureRegistry;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.PlansMeta;
import mariculture.core.lib.SingleMeta;
import mariculture.core.lib.TankMeta;
import mariculture.core.lib.TransparentMeta;
import mariculture.core.lib.UtilMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.util.FluidDictionary;
import mariculture.diving.Diving;
import mariculture.factory.Factory;
import mariculture.fishery.Fishery;
import mariculture.magic.Magic;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class GuideRegistry {
	static IGuideHandler guide = Guides.instance;
	
	public static void init() {		
		registerFluids();
		registerHandlers();
		registerIcons();
	}
	
	public static void registerFluids() {
		guide.registerFluidIcon("fishOil", FluidDictionary.fish_oil);
		guide.registerFluidIcon("quicklime", FluidDictionary.quicklime);
		guide.registerFluidIcon("metalTitanium", FluidDictionary.titanium);
		guide.registerFluidIcon("lava", "lava");
		guide.registerFluidIcon("water", "water");
		guide.registerFluidIcon("metalAluminum", FluidDictionary.aluminum);
		guide.registerFluidIcon("naturalGas", FluidDictionary.natural_gas);
		guide.registerFluidIcon("metalMagnesium", FluidDictionary.magnesium);
		guide.registerFluidIcon("metalRutile", FluidDictionary.rutile);
	}
	
	public static void registerHandlers() {
		guide.registerPageHandler("crafting", new PageCrafting());
		guide.registerPageHandler("paragraph", new PageParagraph());
		guide.registerPageHandler("img", new PageImage());
		guide.registerPageHandler("stack", new PageStack());
		guide.registerPageHandler("vat", new PageVat());
		guide.registerPageHandler("hr", new PageUnderline());
		guide.registerPageHandler("text", new PageText());
	}
	
	public static void registerIcons() {
		/** Rotatables **/
		guide.registerCyclingMetaIcon("wool", new ItemStack(Block.cloth), 16);
		guide.registerCyclingMetaIcon("pearl", new ItemStack(Core.pearls, 1, PearlColor.COUNT), PearlColor.COUNT);
		guide.registerOreDicIcon("plankWood", new ItemStack(Block.planks, 1, 1));
		guide.registerOreDicIcon("ingotGold", new ItemStack(Item.ingotGold));
		guide.registerOreDicIcon("ingotIron", new ItemStack(Item.ingotIron));
		guide.registerOreDicIcon("ingotCopper", new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_COPPER));
		guide.registerOreDicIcon("ingotAluminum", new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_ALUMINUM));
		guide.registerOreDicIcon("ingotTitanium", new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_TITANIUM));
		guide.registerOreDicIcon("stickWood", new ItemStack(Item.stick));
		guide.registerOreDicIcon("logWood", new ItemStack(Block.wood));
		guide.registerOreDicIcon("dyeLightBlue", new ItemStack(Item.dyePowder, 1, Dye.LIGHT_BLUE));
		guide.registerOreDicIcon("dyeBrown", new ItemStack(Item.dyePowder, 1, Dye.BROWN));
		guide.registerOreDicIcon("glass", new ItemStack(Block.glass));
		guide.registerOreDicIcon("dyeBlack", new ItemStack(Item.dyePowder, 1, Dye.INK));
		guide.registerOreDicIcon("dyeYellow", new ItemStack(Item.dyePowder, 1, Dye.YELLOW));
		guide.registerOreDicIcon("dyeBlue", new ItemStack(Item.dyePowder, 1, Dye.LAPIS));
		guide.registerOreDicIcon("dyeWhite", new ItemStack(Item.dyePowder, 1, Dye.BONE));
		guide.registerOreDicIcon("dyeGreen", new ItemStack(Item.dyePowder, 1, Dye.GREEN));
		guide.registerOreDicIcon("dyeRed", new ItemStack(Item.dyePowder, 1, Dye.RED));
		guide.registerOreDicIcon("dyeCyan", new ItemStack(Item.dyePowder, 1, Dye.CYAN));
		guide.registerOreDicIcon("slabWood", new ItemStack(Block.woodSingleSlab));
		
		guide.registerIcon("lifeCore", new ItemStack(Core.craftingItem, 1, CraftingMeta.LIFE_CORE));
		guide.registerIcon("goldPlastic", new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC_YELLOW));
		guide.registerIcon("paper", new ItemStack(Item.paper));
		guide.registerIcon("itemRubber", new ItemStack(Core.pearls));
		guide.registerIcon("hardPlastic", new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC));
		guide.registerIcon("neoprene", new ItemStack(Core.craftingItem, 1, CraftingMeta.NEOPRENE));
		guide.registerIcon("leather", new ItemStack(Item.leather));
		guide.registerIcon("transparentPlastic", new ItemStack(Core.transparentBlocks, 1, TransparentMeta.PLASTIC));
		guide.registerIcon("plasticLens", new ItemStack(Core.craftingItem, 1, CraftingMeta.LENS));
		guide.registerIcon("glassLens", new ItemStack(Core.craftingItem, 1, CraftingMeta.LENS_GLASS));
		guide.registerIcon("reed", new ItemStack(Item.reed));
		guide.registerIcon("blank", new ItemStack(Core.airBlocks, 1, AirMeta.FAKE_AIR));
		guide.registerIcon("glassPane", new ItemStack(Block.thinGlass));
		guide.registerIcon("vat", new ItemStack(Core.doubleBlock, 1, DoubleMeta.VAT));
		guide.registerIcon("storage", new ItemStack(Core.utilBlocks, 1, UtilMeta.BOOKSHELF));
		guide.registerIcon("enchant", new ItemStack(Block.enchantmentTable));
		guide.registerIcon("netherStar", new ItemStack(Item.netherStar));
		guide.registerIcon("goldenThread", new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_THREAD));
		guide.registerIcon("bookshelf", new ItemStack(Block.bookShelf));
		guide.registerIcon("chest", new ItemStack(Block.chest));
		guide.registerIcon("diamond", new ItemStack(Item.diamond));
		guide.registerIcon("pearlRed", new ItemStack(Core.pearls, 1, PearlColor.RED));
		guide.registerIcon("pearlWhite", new ItemStack(Core.pearls, 1, PearlColor.WHITE));
		guide.registerIcon("pearlBlack", new ItemStack(Core.pearls, 1, PearlColor.BLACK));
		guide.registerIcon("string", new ItemStack(Item.silk));
		guide.registerIcon("goldenSilk", new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_SILK));
		guide.registerIcon("goldenThread", new ItemStack(Core.craftingItem, 1, CraftingMeta.GOLDEN_THREAD));
		guide.registerIcon("titaniumBattery", new ItemStack(Core.batteryTitanium));
		guide.registerIcon("titaniumRod", new ItemStack(Core.craftingItem, 1, CraftingMeta.TITANIUM_ROD));
		guide.registerIcon("rawFish", new ItemStack(Item.fishRaw));
		guide.registerIcon("bread", new ItemStack(Item.bread));
		guide.registerIcon("baseWood", new ItemStack(Core.woodBlocks, 1, WoodMeta.BASE_WOOD));
		guide.registerIcon("wicker", new ItemStack(Core.craftingItem, 1, CraftingMeta.WICKER));
		guide.registerIcon("stainedClayWhite", new ItemStack(Block.stainedClay, 1, 0));
		guide.registerIcon("stainedClayLightBlue", new ItemStack(Block.stainedClay, 1, 3));
		guide.registerIcon("heating", new ItemStack(Core.craftingItem, 1, CraftingMeta.HEATER));
		guide.registerIcon("copperBattery", new ItemStack(Core.batteryCopper));
		guide.registerIcon("burntBrick", new ItemStack(Core.craftingItem, 1, CraftingMeta.BURNT_BRICK));
		guide.registerIcon("brick", new ItemStack(Item.brick));
		guide.registerIcon("netherBrick", new ItemStack(Item.netherrackBrick));
		guide.registerIcon("copperTank", new ItemStack(Core.tankBlocks, 1, TankMeta.TANK));
		guide.registerIcon("ironBars", new ItemStack(Block.fenceIron));
		guide.registerIcon("crucibleFurnace", new ItemStack(Core.utilBlocks, 1, UtilMeta.LIQUIFIER));
		guide.registerIcon("baseBrick", new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_BRICK));
		guide.registerIcon("bucketLava", new ItemStack(Item.bucketLava));
		guide.registerIcon("ladle", new ItemStack(Core.ladle));
		guide.registerIcon("caster", new ItemStack(Core.singleBlocks, 1, SingleMeta.INGOT_CASTER));
		guide.registerIcon("heatglass", new ItemStack(Core.glassBlocks, 1, GlassMeta.HEAT));
		guide.registerIcon("heatBottleEmpty", new ItemStack(Core.liquidContainers, 1, FluidContainerMeta.BOTTLE_EMPTY));
		guide.registerIcon("bottleVoid", new ItemStack(Core.liquidContainers, 1, FluidContainerMeta.BOTTLE_VOID));
		guide.registerIcon("redstone", new ItemStack(Item.redstone));
		guide.registerIcon("netherBrickBlock", new ItemStack(Block.netherBrick));
		guide.registerIcon("blacksmithHammer", new ItemStack(Core.hammer));
		guide.registerIcon("blacksmithAnvil", new ItemStack(Core.singleBlocks, 1, SingleMeta.ANVIL_1));
		guide.registerIcon("airPump", new ItemStack(Core.singleBlocks, 1, SingleMeta.AIR_PUMP));
		guide.registerIcon("piston", new ItemStack(Block.pistonBase));
		guide.registerIcon("ironWheel", new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL));
		guide.registerIcon("nightVisionPotion", new ItemStack(Item.potion, 1, 8198));
		guide.registerIcon("cooling", new ItemStack(Core.craftingItem, 1, CraftingMeta.COOLER));
		guide.registerIcon("aluminumSheet", new ItemStack(Core.craftingItem, 1, CraftingMeta.ALUMINUM_SHEET));
		guide.registerIcon("aluminumBlock", new ItemStack(Core.oreBlocks, 1, OresMeta.ALUMINUM_BLOCK));
		guide.registerIcon("dustMagnesite", new ItemStack(Core.materials, 1, MaterialsMeta.DUST_MAGNESITE));
		guide.registerIcon("bookNQuill", new ItemStack(Item.writableBook));
		guide.registerIcon("feather", new ItemStack(Item.feather));
		guide.registerIcon("hopper", new ItemStack(Block.hopperBlock));
		guide.registerIcon("limestone", new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE));
		guide.registerIcon("ironAxe", new ItemStack(Item.axeIron));
		guide.registerIcon("baseIron", new ItemStack(Core.oreBlocks, 1, OresMeta.BASE_IRON));
		guide.registerIcon("slabQuartz", new ItemStack(Block.stoneSingleSlab, 1, 7));
		guide.registerIcon("slabStone", new ItemStack(Block.stoneSingleSlab, 1, 0));
		guide.registerIcon("bucketWater", new ItemStack(Item.bucketWater));
		guide.registerIcon("blockLapis", new ItemStack(Block.blockLapis));
		guide.registerIcon("titaniumSheet", new ItemStack(Core.craftingItem, 1, CraftingMeta.TITANIUM_SHEET));
		
		/** These are replaced when modules are added **/
		guide.registerIcon("magicDroplet", new ItemStack(Item.ghastTear));
		guide.registerIcon("sponge", new ItemStack(Item.bucketWater));
		guide.registerIcon("fishingNet", new ItemStack(Block.chest));
		guide.registerIcon("waterDroplet", new ItemStack(Item.potion, 1, 0));
		guide.registerIcon("fish", new ItemStack(Item.fishRaw));
		guide.registerIcon("scubaTank", new ItemStack(Block.lever));
		/** Ignore replaceables **/
		
		if(Modules.diving.isActive()) {
			guide.registerIcon("snorkel", new ItemStack(Diving.snorkel));
			guide.registerIcon("divingHelmet", new ItemStack(Diving.divingHelmet));
			guide.registerIcon("divingTop", new ItemStack(Diving.divingTop));
			guide.registerIcon("divingPants", new ItemStack(Diving.divingPants));
			guide.registerIcon("divingBoots", new ItemStack(Diving.divingBoots));
			guide.registerIcon("scubaMask", new ItemStack(Diving.scubaMask));
			guide.registerIcon("scubaTank", new ItemStack(Diving.scubaTank));
			guide.registerIcon("wetsuit", new ItemStack(Diving.scubaSuit));
			guide.registerIcon("flippers", new ItemStack(Diving.swimfin));
			guide.registerIcon("compressorTop", new ItemStack(Core.doubleBlock, 1, DoubleMeta.COMPRESSOR_TOP));
			guide.registerIcon("compressorBase", new ItemStack(Core.doubleBlock, 1, DoubleMeta.COMPRESSOR_BASE));
		}
		
		if(Modules.factory.isActive()) {
			guide.registerIcon("autodictionary", new ItemStack(Core.utilBlocks, 1, UtilMeta.DICTIONARY));
			guide.registerIcon("itemFilter", new ItemStack(Factory.filter));
			guide.registerIcon("blankPlan", new ItemStack(Core.craftingItem, 1, CraftingMeta.BLANK_PLAN));
			guide.registerIcon("planningChalk", new ItemStack(Core.craftingItem, 1, CraftingMeta.CHALK));
			guide.registerIcon("floorPlan", PlansMeta.setType(new ItemStack(Factory.plans), PlansMeta.FLOOR));
			guide.registerIcon("blockPlan", PlansMeta.setType(new ItemStack(Factory.plans), PlansMeta.BLOCK));
			guide.registerIcon("stairPlan", PlansMeta.setType(new ItemStack(Factory.plans), PlansMeta.STAIRS));
			guide.registerIcon("slabPlan", PlansMeta.setType(new ItemStack(Factory.plans), PlansMeta.SLABS));
			guide.registerIcon("fencePlan", PlansMeta.setType(new ItemStack(Factory.plans), PlansMeta.FENCE));
			guide.registerIcon("gatePlan", PlansMeta.setType(new ItemStack(Factory.plans), PlansMeta.GATE));
			guide.registerIcon("lightPlan", PlansMeta.setType(new ItemStack(Factory.plans), PlansMeta.LIGHT));
			guide.registerIcon("rfPlan", PlansMeta.setType(new ItemStack(Factory.plans), PlansMeta.RF));
			guide.registerIcon("wallPlan", PlansMeta.setType(new ItemStack(Factory.plans), PlansMeta.WALL));
			guide.registerIcon("sawmill", new ItemStack(Core.utilBlocks, 1, UtilMeta.SAWMILL));
			guide.registerIcon("paintbrush", new ItemStack(Factory.paintbrush));
			guide.registerIcon("turbineManual", new ItemStack(Core.singleBlocks, 1, SingleMeta.TURBINE_HAND));
			guide.registerIcon("turbineWater", new ItemStack(Core.singleBlocks, 1, SingleMeta.TURBINE_WATER));
			guide.registerIcon("turbineGas", new ItemStack(Core.singleBlocks, 1, SingleMeta.TURBINE_GAS));
			guide.registerIcon("sluice", new ItemStack(Core.utilBlocks, 1, UtilMeta.SLUICE));
			guide.registerIcon("rotorCopper", new ItemStack(Factory.turbineCopper));
			guide.registerIcon("rotorAluminum", new ItemStack(Factory.turbineAluminum));
			guide.registerIcon("rotorTitanium", new ItemStack(Factory.turbineTitanium));
			guide.registerIcon("geyser", new ItemStack(Core.singleBlocks, 1, SingleMeta.GEYSER));
			guide.registerIcon("fishSorter", new ItemStack(Core.utilBlocks, 1, UtilMeta.FISH_SORTER));
			guide.registerIcon("mechanizedSponge", new ItemStack(Core.utilBlocks, 1, UtilMeta.SPONGE));
			guide.registerIcon("fludd", new ItemStack(Factory.fludd));
			guide.registerIcon("pressureVessel", new ItemStack(Core.doubleBlock, 1, DoubleMeta.PRESSURE_VESSEL));
		}
		
		if(Modules.fishery.isActive()) {
			guide.registerCyclingMetaIcon("fish", new ItemStack(Fishery.fishyFood, 1, Fishery.nether.fishID), FishSpecies.speciesList.size());
			guide.registerIcon("polishedLog", new ItemStack(Core.woodBlocks, 1, WoodMeta.POLISHED_LOG));
			guide.registerIcon("polishedPlank", new ItemStack(Core.woodBlocks, 1, WoodMeta.POLISHED_PLANK));
			guide.registerIcon("rodReed", new ItemStack(Fishery.rodReed));
			guide.registerIcon("rodWood", new ItemStack(Fishery.rodWood));
			guide.registerIcon("rodTitanium", new ItemStack(Fishery.rodTitanium));
			guide.registerIcon("rodRF", new ItemStack(Fishery.rodFlux));
			guide.registerIcon("polishedTitanium", new ItemStack(Core.craftingItem, 1, CraftingMeta.POLISHED_TITANIUM));
			guide.registerIcon("polishedStick", new ItemStack(Core.craftingItem, 1, CraftingMeta.POLISHED_STICK));
			guide.registerIcon("sifter", new ItemStack(Fishery.siftBlock, 1, 0));
			guide.registerIcon("fishingNet", new ItemStack(Fishery.net));
			guide.registerIcon("magicDroplet", new ItemStack(Core.materials, 1, MaterialsMeta.DROP_MAGIC));
			guide.registerIcon("ant", new ItemStack(Fishery.bait, 1, BaitMeta.ANT));
			guide.registerIcon("bee", new ItemStack(Fishery.bait, 1, BaitMeta.BEE));
			guide.registerIcon("grasshopper", new ItemStack(Fishery.bait, 1, BaitMeta.HOPPER));
			guide.registerIcon("maggot", new ItemStack(Fishery.bait, 1, BaitMeta.MAGGOT));
			guide.registerIcon("worm", new ItemStack(Fishery.bait, 1, BaitMeta.WORM));
			guide.registerIcon("minnow", new ItemStack(Fishery.fishyFood, 1, Fishery.minnow.fishID));
			guide.registerIcon("netherfish", new ItemStack(Fishery.fishyFood, 1, Fishery.nether.fishID));
			guide.registerIcon("nightfish", new ItemStack(Fishery.fishyFood, 1, Fishery.night.fishID));
			guide.registerIcon("tetra", new ItemStack(Fishery.fishyFood, 1, Fishery.tetra.fishID));
			guide.registerIcon("cod", new ItemStack(Fishery.fishyFood, 1, Fishery.cod.fishID));
			guide.registerIcon("stingray", new ItemStack(Fishery.fishyFood, 1, Fishery.stingRay.fishID));
			guide.registerIcon("damselfish", new ItemStack(Fishery.fishyFood, 1, Fishery.damsel.fishID));
			guide.registerIcon("squid", new ItemStack(Fishery.fishyFood, 1, Fishery.squid.fishID));
			guide.registerIcon("autofisher", new ItemStack(Core.utilBlocks, 1, UtilMeta.AUTOFISHER));
			guide.registerIcon("feeder", new ItemStack(Core.singleBlocks, 1, SingleMeta.FISH_FEEDER));
			guide.registerIcon("incubatorBase", new ItemStack(Core.utilBlocks, 1, UtilMeta.INCUBATOR_BASE));
			guide.registerIcon("incubatorTop", new ItemStack(Core.utilBlocks, 1, UtilMeta.INCUBATOR_TOP));
			guide.registerIcon("waterDroplet", new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER));
		}
		
		if(Modules.magic.isActive()) {
			guide.registerIcon("basicMirror", new ItemStack(Magic.basicMirror));
			guide.registerIcon("magicMirror", new ItemStack(Magic.magicMirror));
			guide.registerIcon("celestialMirror", new ItemStack(Magic.celestialMirror));
			guide.registerIcon("ringPearl", MaricultureRegistry.get("ring.pearlRed.gold"));
			guide.registerIcon("braceletPearl", MaricultureRegistry.get("bracelet.pearlBlack.goldString"));
			guide.registerIcon("necklacePearl", MaricultureRegistry.get("necklace.pearlWhite.wool"));
			guide.registerIcon("ringIron", MaricultureRegistry.get("ring.diamond.iron"));
			guide.registerIcon("braceletIron", MaricultureRegistry.get("bracelet.iron.string"));
			guide.registerIcon("necklaceIron", MaricultureRegistry.get("necklace.iron.wool"));
		}
		
		if(Modules.world.isActive()) {
			guide.registerIcon("sponge", new ItemStack(Block.sponge));
		}
		
		if(OreDictionary.getOres("itemRubber").size() > 0) {
			guide.registerOreDicIcon("itemRubber", new ItemStack(Core.pearls));
		}
	}
}
