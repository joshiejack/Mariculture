package mariculture.plugins;

import net.minecraft.block.Block;
import mariculture.api.fishery.Fishing;
import mariculture.core.Core;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.PearlColor;
import mariculture.fishery.Fishery;
import mariculture.world.WorldPlus;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class PluginThaumcraft {

	public static void init() {
		//Bait
		if(Modules.fishery.isActive()) {
			ThaumcraftApi.registerObjectTag(Fishery.bait.itemID, BaitMeta.ANT, new AspectList().add(Aspect.BEAST, 1));
			ThaumcraftApi.registerObjectTag(Fishery.bait.itemID, BaitMeta.HOPPER, new AspectList().add(Aspect.BEAST, 1).add(Aspect.PLANT, 1));
			ThaumcraftApi.registerObjectTag(Fishery.bait.itemID, BaitMeta.MAGGOT, new AspectList().add(Aspect.BEAST, 1).add(Aspect.FLESH, 1));
			ThaumcraftApi.registerObjectTag(Fishery.bait.itemID, BaitMeta.WORM, new AspectList().add(Aspect.BEAST, 1).add(Aspect.EARTH, 2));
			ThaumcraftApi.registerObjectTag(Fishery.fishyFood.itemID, -1, new AspectList().add(Aspect.WATER, 1).add(Aspect.FLESH, 3).add(Aspect.HUNGER, 1));
		}
		
		//Coral
		if(Modules.world.isActive()) {
			ThaumcraftApi.registerObjectTag("coralLightBlue", new AspectList().add(Aspect.WATER, 1).add(Aspect.BEAST, 1));
			ThaumcraftApi.registerObjectTag("coralRed", new AspectList().add(Aspect.WATER, 1).add(Aspect.BEAST, 1));
			ThaumcraftApi.registerObjectTag("coralOrange", new AspectList().add(Aspect.WATER, 1).add(Aspect.BEAST, 1));
			ThaumcraftApi.registerObjectTag("coralPink", new AspectList().add(Aspect.WATER, 1).add(Aspect.BEAST, 1));
			ThaumcraftApi.registerObjectTag("coralPurple", new AspectList().add(Aspect.WATER, 1).add(Aspect.BEAST, 1));
			ThaumcraftApi.registerObjectTag("coralMagenta", new AspectList().add(Aspect.WATER, 1).add(Aspect.BEAST, 1));
			ThaumcraftApi.registerObjectTag("coralYellow", new AspectList().add(Aspect.WATER, 1).add(Aspect.BEAST, 1));
			ThaumcraftApi.registerObjectTag(WorldPlus.coral.blockID, CoralMeta.KELP, new AspectList().add(Aspect.WATER, 1).add(Aspect.PLANT, 2));
		}
		
		//Pearls
		ThaumcraftApi.registerObjectTag(Core.pearls.itemID, PearlColor.WHITE, new AspectList().add(Aspect.WATER, 1).add(Aspect.CRYSTAL, 1));
		ThaumcraftApi.registerObjectTag(Core.pearls.itemID, PearlColor.GREEN, new AspectList().add(Aspect.WATER, 1).add(Aspect.TREE, 1));
		ThaumcraftApi.registerObjectTag(Core.pearls.itemID, PearlColor.YELLOW, new AspectList().add(Aspect.WATER, 1).add(Aspect.HEAL, 1));
		ThaumcraftApi.registerObjectTag(Core.pearls.itemID, PearlColor.ORANGE, new AspectList().add(Aspect.WATER, 1).add(Aspect.ARMOR, 1));
		ThaumcraftApi.registerObjectTag(Core.pearls.itemID, PearlColor.RED, new AspectList().add(Aspect.WATER, 1).add(Aspect.ENERGY, 1));
		ThaumcraftApi.registerObjectTag(Core.pearls.itemID, PearlColor.GOLD, new AspectList().add(Aspect.WATER, 1).add(Aspect.GREED, 1));
		ThaumcraftApi.registerObjectTag(Core.pearls.itemID, PearlColor.BROWN, new AspectList().add(Aspect.WATER, 1).add(Aspect.TOOL, 1));
		ThaumcraftApi.registerObjectTag(Core.pearls.itemID, PearlColor.PURPLE, new AspectList().add(Aspect.WATER, 1).add(Aspect.MIND, 1));
		ThaumcraftApi.registerObjectTag(Core.pearls.itemID, PearlColor.BLUE, new AspectList().add(Aspect.WATER, 1).add(Aspect.BEAST, 1));
		ThaumcraftApi.registerObjectTag(Core.pearls.itemID, PearlColor.BLACK, new AspectList().add(Aspect.WATER, 1).add(Aspect.MECHANISM, 1));
		ThaumcraftApi.registerObjectTag(Core.pearls.itemID, PearlColor.PINK, new AspectList().add(Aspect.WATER, 1).add(Aspect.PLANT, 1));
		ThaumcraftApi.registerObjectTag(Core.pearls.itemID, PearlColor.SILVER, new AspectList().add(Aspect.WATER, 1).add(Aspect.LIGHT, 1));
		
		//Ore Blocks
		ThaumcraftApi.registerObjectTag("oreAluminum", new AspectList().add(Aspect.STONE, 1).add(Aspect.METAL, 1).add(Aspect.AIR, 2));
		ThaumcraftApi.registerObjectTag("oreTitanium", new AspectList().add(Aspect.STONE, 1).add(Aspect.METAL, 4).add(Aspect.DARKNESS, 1));
		ThaumcraftApi.registerObjectTag(Core.oreBlocks.blockID, OresMeta.LIMESTONE, new AspectList().add(Aspect.STONE, 1).add(Aspect.WATER, 1).add(Aspect.EARTH, 1));
		ThaumcraftApi.registerObjectTag(Core.oreBlocks.blockID, OresMeta.LIMESTONE_SMOOTH, new AspectList().add(Aspect.STONE, 1).add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag(Core.oreBlocks.blockID, OresMeta.CORAL_ROCK, new AspectList().add(Aspect.STONE, 1).add(Aspect.WATER, 2));
		ThaumcraftApi.registerObjectTag(Block.sponge.blockID, -1, new AspectList().add(Aspect.WATER, 1).add(Aspect.AIR, 2));
		ThaumcraftApi.registerObjectTag(Core.oysterBlock.blockID, -1, new AspectList().add(Aspect.WATER, 2).add(Aspect.LIFE, 1));
		
		//Crafting Items
		ThaumcraftApi.registerObjectTag(Core.craftingItem.itemID, CraftingMeta.ALUMINUM_SHEET, new AspectList().add(Aspect.METAL, 2).add(Aspect.AIR, 4));
		ThaumcraftApi.registerObjectTag(Core.craftingItem.itemID, CraftingMeta.POLISHED_STICK, new AspectList().add(Aspect.TREE, 1).add(Aspect.ORDER, 1));
		
		//Materials
		ThaumcraftApi.registerObjectTag(Core.materials.itemID, MaterialsMeta.DROP_AQUA, new AspectList().add(Aspect.WATER, 2));
		ThaumcraftApi.registerObjectTag(Core.materials.itemID, MaterialsMeta.DROP_ATTACK, new AspectList().add(Aspect.ENTROPY, 2));
		ThaumcraftApi.registerObjectTag(Core.materials.itemID, MaterialsMeta.DROP_ELECTRIC, new AspectList().add(Aspect.ENERGY, 2));
		ThaumcraftApi.registerObjectTag(Core.materials.itemID, MaterialsMeta.DROP_ENDER, new AspectList().add(Aspect.ELDRITCH, 2));
		ThaumcraftApi.registerObjectTag(Core.materials.itemID, MaterialsMeta.DROP_HEALTH, new AspectList().add(Aspect.HEAL, 2));
		ThaumcraftApi.registerObjectTag(Core.materials.itemID, MaterialsMeta.DROP_MAGIC, new AspectList().add(Aspect.MAGIC, 2));
		ThaumcraftApi.registerObjectTag(Core.materials.itemID, MaterialsMeta.DROP_NETHER, new AspectList().add(Aspect.FIRE, 2));
		ThaumcraftApi.registerObjectTag(Core.materials.itemID, MaterialsMeta.DROP_POISON, new AspectList().add(Aspect.POISON, 2));
		ThaumcraftApi.registerObjectTag(Core.materials.itemID, MaterialsMeta.DROP_WATER, new AspectList().add(Aspect.WATER, 1));
		ThaumcraftApi.registerObjectTag("ingotAluminum", new AspectList().add(Aspect.AIR, 2).add(Aspect.METAL, 1));
		ThaumcraftApi.registerObjectTag("ingotTitanium", new AspectList().add(Aspect.METAL, 4).add(Aspect.DARKNESS, 1));
		ThaumcraftApi.registerObjectTag("ingotMagnesium", new AspectList().add(Aspect.ENERGY, 1).add(Aspect.METAL, 1).add(Aspect.ENTROPY, 1));
	}
}
