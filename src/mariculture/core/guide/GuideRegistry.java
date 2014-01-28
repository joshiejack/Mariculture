package mariculture.core.guide;

import mariculture.api.core.MaricultureRegistry;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.api.guide.Guides;
import mariculture.api.guide.IGuideHandler;
import mariculture.core.Core;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.UtilMeta;
import mariculture.core.lib.WoodMeta;
import mariculture.core.util.FluidDictionary;
import mariculture.fishery.Fishery;
import mariculture.magic.Magic;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
	}
	
	public static void registerHandlers() {
		guide.registerPageHandler("crafting", new PageCrafting());
		guide.registerPageHandler("paragraph", new PageParagraph());
		guide.registerPageHandler("img", new PageImage());
		guide.registerPageHandler("stack", new PageStack());
		guide.registerPageHandler("vat", new PageVat());
		guide.registerPageHandler("hr", new PageUnderline());
	}
	
	public static void registerIcons() {
		/** Rotatables **/
		guide.registerCyclingMetaIcon("wool", new ItemStack(Core.pearls, 1, PearlColor.COUNT), 16);
		guide.registerCyclingMetaIcon("pearl", new ItemStack(Core.pearls, 1, PearlColor.COUNT), PearlColor.COUNT);
		guide.registerOreDicIcon("plankWood", new ItemStack(Block.planks, 1, 1));
		guide.registerOreDicIcon("ingotGold", new ItemStack(Item.ingotGold));
		guide.registerOreDicIcon("ingotIron", new ItemStack(Item.ingotIron));
		guide.registerOreDicIcon("ingotCopper", new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_COPPER));
		guide.registerOreDicIcon("ingotAluminum", new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_ALUMINUM));
		guide.registerOreDicIcon("stickWood", new ItemStack(Item.stick));
		guide.registerOreDicIcon("logWood", new ItemStack(Block.wood));
		
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
		
		/** These are replaced when modules are added **/
		guide.registerIcon("magicDroplet", new ItemStack(Item.ghastTear));
		/** Ignore replaceables **/
		
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
	}
}
