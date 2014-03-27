package mariculture.fishery;

import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.core.Core;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.Modules;
import mariculture.factory.Factory;
import mariculture.magic.Magic;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FishingLoot {

	public static void add() {
		addOverworldLoot();
		addNetherLoot();
		addEndLoot();

		Fishing.loot.addLoot(new ItemStack(Item.expBottle), new Object[] { RodQuality.OLD, 100 });
		Fishing.loot.addLoot(new ItemStack(Item.book), new Object[] { RodQuality.OLD, 120 });
		Fishing.loot.addLoot(new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL), new Object[] { RodQuality.OLD, 200 });
		Fishing.loot.addLoot(new ItemStack(Item.nameTag), new Object[] { RodQuality.OLD, 850 });
	}

	private static void addEndLoot() {
		Fishing.loot.addLoot(new ItemStack(Core.liquidContainers, 1, FluidContainerMeta.BOTTLE_VOID), new Object[] { RodQuality.OLD, 75, 1 });
		Fishing.loot.addLoot(new ItemStack(Item.enderPearl), new Object[] { RodQuality.OLD, 300, 1 });
		Fishing.loot.addLoot(new ItemStack(Item.eyeOfEnder), new Object[] { RodQuality.OLD, 250, 1 });
		Fishing.loot.addLoot(new ItemStack(Item.record11), new Object[] { RodQuality.GOOD, 2000, 1 });
		Fishing.loot.addLoot(new ItemStack(Item.recordMellohi), new Object[] { RodQuality.GOOD, 2000, 1 });
		Fishing.loot.addLoot(new ItemStack(Item.recordStal), new Object[] { RodQuality.GOOD, 2000, 1 });
	}

	private static void addNetherLoot() {
		Fishing.loot.addLoot(new ItemStack(Item.goldNugget), new Object[] { RodQuality.OLD, 65, -1 });
		Fishing.loot.addLoot(new ItemStack(Item.ghastTear), new Object[] { RodQuality.GOOD, 550, -1 });
		Fishing.loot.addLoot(new ItemStack(Item.record13), new Object[] { RodQuality.GOOD, 2000, -1 });
		Fishing.loot.addLoot(new ItemStack(Item.recordBlocks), new Object[] { RodQuality.GOOD, 2000, -1 });
		Fishing.loot.addLoot(new ItemStack(Item.recordChirp), new Object[] { RodQuality.GOOD, 2000, -1 });
		Fishing.loot.addLoot(new ItemStack(Item.potion, 1, 8195), new Object[] { RodQuality.GOOD, 1000, -1 });
		Fishing.loot.addLoot(new ItemStack(Item.netherStar), new Object[] { RodQuality.SUPER, 30000, -1 });
	}

	private static void addOverworldLoot() {
		for (int i = 0; i < 12; i++) {
			Fishing.loot.addLoot(new ItemStack(Core.pearls, 1, i), new Object[] { RodQuality.GOOD, 150, 0 });
		}
		
		Fishing.loot.addLoot(new ItemStack(Item.fishRaw), new Object[] { RodQuality.OLD, 10, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.saddle), new Object[] { RodQuality.OLD, 1000, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.recordFar), new Object[] { RodQuality.GOOD, 2000, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.recordMall), new Object[] { RodQuality.GOOD, 2000, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.recordStrad), new Object[] { RodQuality.GOOD, 2000, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.recordWard), new Object[] { RodQuality.GOOD, 2000, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.recordCat), new Object[] { RodQuality.GOOD, 2000, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.bootsLeather), new Object[] { RodQuality.OLD, 50, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.bow), new Object[] { RodQuality.OLD, 1500, 0 });
		Fishing.loot.addLoot(new ItemStack(Block.waterlily), new Object[] { RodQuality.OLD, 55, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.bowlEmpty), new Object[] { RodQuality.OLD, 30, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.leather), new Object[] { RodQuality.OLD, 45, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.rottenFlesh), new Object[] { RodQuality.OLD, 60, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.stick), new Object[] { RodQuality.OLD, 15, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.silk), new Object[] { RodQuality.OLD, 40, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.potion), new Object[] { RodQuality.OLD, 55, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.bone), new Object[] { RodQuality.OLD, 65, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.dyePowder, 1, Dye.INK), new Object[] { RodQuality.OLD, 35, 0 });
		
		if(Modules.factory.isActive()) {
			Fishing.loot.addLoot(new ItemStack(Factory.fludd), new Object[] { RodQuality.SUPER, 10000, 0 });
		}
	}
}
