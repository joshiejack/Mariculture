package mariculture.fishery;

import mariculture.api.fishery.EnumRodQuality;
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

		Fishing.loot.addLoot(new ItemStack(Item.expBottle), new Object[] { EnumRodQuality.OLD, 100 });
		Fishing.loot.addLoot(new ItemStack(Item.book), new Object[] { EnumRodQuality.OLD, 120 });
		Fishing.loot.addLoot(new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL), new Object[] { EnumRodQuality.OLD, 200 });
		Fishing.loot.addLoot(new ItemStack(Item.nameTag), new Object[] { EnumRodQuality.OLD, 850 });
	}

	private static void addEndLoot() {
		Fishing.loot.addLoot(new ItemStack(Core.liquidContainers, 1, FluidContainerMeta.BOTTLE_VOID), new Object[] { EnumRodQuality.OLD, 75, 1 });
		Fishing.loot.addLoot(new ItemStack(Item.enderPearl), new Object[] { EnumRodQuality.OLD, 300, 1 });
		Fishing.loot.addLoot(new ItemStack(Item.eyeOfEnder), new Object[] { EnumRodQuality.OLD, 250, 1 });
		Fishing.loot.addLoot(new ItemStack(Item.record11), new Object[] { EnumRodQuality.GOOD, 2000, 1 });
		Fishing.loot.addLoot(new ItemStack(Item.recordMellohi), new Object[] { EnumRodQuality.GOOD, 2000, 1 });
		Fishing.loot.addLoot(new ItemStack(Item.recordStal), new Object[] { EnumRodQuality.GOOD, 2000, 1 });
		
		if(Modules.magic.isActive()) {
			if(EnchantHelper.exists(Magic.flight)) {
				ItemStack feather = new ItemStack(Item.feather);
				feather.setItemName("Mystical Feather of MagicManMe");
				feather.addEnchantment(Magic.flight, 5);
				Fishing.loot.addLoot(feather, new Object[] { EnumRodQuality.SUPER, 20000, 1 });
			}
		}
	}

	private static void addNetherLoot() {
		Fishing.loot.addLoot(new ItemStack(Item.goldNugget), new Object[] { EnumRodQuality.OLD, 65, -1 });
		Fishing.loot.addLoot(new ItemStack(Item.ghastTear), new Object[] { EnumRodQuality.GOOD, 550, -1 });
		Fishing.loot.addLoot(new ItemStack(Item.record13), new Object[] { EnumRodQuality.GOOD, 2000, -1 });
		Fishing.loot.addLoot(new ItemStack(Item.recordBlocks), new Object[] { EnumRodQuality.GOOD, 2000, -1 });
		Fishing.loot.addLoot(new ItemStack(Item.recordChirp), new Object[] { EnumRodQuality.GOOD, 2000, -1 });
		Fishing.loot.addLoot(new ItemStack(Item.potion, 1, 8195), new Object[] { EnumRodQuality.GOOD, 1000, -1 });
		Fishing.loot.addLoot(new ItemStack(Item.netherStar), new Object[] { EnumRodQuality.SUPER, 30000, -1 });
	}

	private static void addOverworldLoot() {
		for (int i = 0; i < 12; i++) {
			Fishing.loot.addLoot(new ItemStack(Core.pearls, 1, i), new Object[] { EnumRodQuality.GOOD, 150, 0 });
		}
		
		Fishing.loot.addLoot(new ItemStack(Item.fishRaw), new Object[] { EnumRodQuality.OLD, 10, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.saddle), new Object[] { EnumRodQuality.OLD, 1000, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.recordFar), new Object[] { EnumRodQuality.GOOD, 2000, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.recordMall), new Object[] { EnumRodQuality.GOOD, 2000, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.recordStrad), new Object[] { EnumRodQuality.GOOD, 2000, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.recordWard), new Object[] { EnumRodQuality.GOOD, 2000, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.recordCat), new Object[] { EnumRodQuality.GOOD, 2000, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.bootsLeather), new Object[] { EnumRodQuality.OLD, 50, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.bow), new Object[] { EnumRodQuality.OLD, 1500, 0 });
		Fishing.loot.addLoot(new ItemStack(Block.waterlily), new Object[] { EnumRodQuality.OLD, 55, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.bowlEmpty), new Object[] { EnumRodQuality.OLD, 30, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.leather), new Object[] { EnumRodQuality.OLD, 45, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.rottenFlesh), new Object[] { EnumRodQuality.OLD, 60, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.stick), new Object[] { EnumRodQuality.OLD, 15, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.silk), new Object[] { EnumRodQuality.OLD, 40, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.potion), new Object[] { EnumRodQuality.OLD, 55, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.bone), new Object[] { EnumRodQuality.OLD, 65, 0 });
		Fishing.loot.addLoot(new ItemStack(Item.dyePowder, 1, Dye.INK), new Object[] { EnumRodQuality.OLD, 35, 0 });
		
		if(Modules.factory.isActive()) {
			Fishing.loot.addLoot(new ItemStack(Factory.fludd), new Object[] { EnumRodQuality.SUPER, 10000, 0 });
		}
	}
}
