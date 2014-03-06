package mariculture.fishery;

import java.util.Arrays;
import java.util.List;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ILootHandler.LootQuality;
import mariculture.core.Core;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.Modules;
import mariculture.factory.Factory;
import mariculture.magic.Magic;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomFishable;

public class FishingLoot {
	public static void add() {
		addOverworldLoot();
		addNetherLoot();
		addEndLoot();
		
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.experience_bottle), 1), null);
		Fishing.loot.addLoot(LootQuality.BAD, new WeightedRandomFishable(new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL), 4), null);
	}

	private static void addEndLoot() {
		List end = Arrays.asList(new EnumBiomeType[] { EnumBiomeType.ENDER });
		Fishing.loot.addLoot(LootQuality.BAD, new WeightedRandomFishable(new ItemStack(Core.liquidContainers, 1, FluidContainerMeta.BOTTLE_VOID), 4), end);
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.ender_pearl), 1), end);
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.ender_eye), 4), end);
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.record_11), 1), end);
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.record_mellohi), 1), end);
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.record_stal), 1), end);
	}

	private static void addNetherLoot() {
		List nether = Arrays.asList(new EnumBiomeType[] { EnumBiomeType.HELL });
		Fishing.loot.addLoot(LootQuality.BAD, new WeightedRandomFishable(new ItemStack(Items.gold_nugget), 7), nether);
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.ghast_tear), 2), nether);
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.record_13), 1), nether);
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.record_blocks), 1), nether);
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.record_chirp), 1), nether);
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.potionitem, 1, 8195), 1), nether);
		Fishing.loot.addLoot(LootQuality.RARE, new WeightedRandomFishable(new ItemStack(Items.nether_star), 1), nether);
	}

	private static void addOverworldLoot() {
		List overworld = Arrays.asList(new EnumBiomeType[] { EnumBiomeType.ARID, EnumBiomeType.COLD, EnumBiomeType.FROZEN, EnumBiomeType.FROZEN_OCEAN,
				EnumBiomeType.HOT, EnumBiomeType.MUSHROOM, EnumBiomeType.NORMAL, EnumBiomeType.OCEAN});
		List ocean = Arrays.asList(new EnumBiomeType[] { EnumBiomeType.OCEAN, EnumBiomeType.FROZEN_OCEAN });
		List mushroom = Arrays.asList(new EnumBiomeType[] { EnumBiomeType.MUSHROOM });
		for (int i = 0; i < 12; i++) {
			Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Core.pearls, 1, i), 2), ocean);
		}
		
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.record_far), 1), overworld);
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.record_mall), 1), overworld);
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.record_strad), 1), overworld);
		Fishing.loot.addLoot(LootQuality.GOOD, new WeightedRandomFishable(new ItemStack(Items.record_cat), 1), overworld);
		if(Modules.factory.isActive()) Fishing.loot.addLoot(LootQuality.RARE, new WeightedRandomFishable(new ItemStack(Factory.fludd), 1), mushroom);
	}
}
