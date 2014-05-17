package mariculture.fishery;

import static mariculture.core.lib.ItemLib.*;

import java.util.Arrays;
import java.util.List;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.Loot;
import mariculture.api.fishery.Loot.Rarity;
import mariculture.api.fishery.RodType;
import mariculture.core.Core;
import mariculture.core.lib.FluidContainerMeta;
import mariculture.core.lib.Modules;
import mariculture.factory.Factory;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomFishable;

public class FishingLoot {
	public static void add() {
		addOverworldLoot();
		addNetherLoot();
		addEndLoot();
		
		Fishing.fishing.addLoot(new Loot(new ItemStack(xpBottle), 35D, Rarity.GOOD));
		Fishing.fishing.addLoot(new Loot(ironWheel, 25D, Rarity.JUNK));
	}

	private static void addEndLoot() {
		Fishing.fishing.addLoot(new Loot(voidBottle, 25D, Rarity.JUNK, 1, RodType.DIRE));
		Fishing.fishing.addLoot(new Loot(new ItemStack(enderPearl), 15D, Rarity.JUNK, 1, RodType.OLD));
		Fishing.fishing.addLoot(new Loot(new ItemStack(eyeOfEnder), 10D, Rarity.JUNK, 1, RodType.OLD));
		Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_11), 5D, Rarity.GOOD, 1, RodType.GOOD));
		Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_mellohi), 5D, Rarity.GOOD, 1, RodType.GOOD));
		Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_stal), 5D, Rarity.GOOD, 1, RodType.GOOD));
	}

	private static void addNetherLoot() {
		Fishing.fishing.addLoot(new Loot(new ItemStack(Items.gold_nugget), 45D, Rarity.JUNK, -1, RodType.DIRE));
		Fishing.fishing.addLoot(new Loot(new ItemStack(Items.ghast_tear), 15D, Rarity.JUNK, -1, RodType.OLD));
		Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_13), 5D, Rarity.GOOD, -1, RodType.GOOD));
		Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_blocks), 5D, Rarity.GOOD, -1, RodType.GOOD));
		Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_chirp), 5D, Rarity.GOOD, -1, RodType.GOOD));
		Fishing.fishing.addLoot(new Loot(new ItemStack(Items.potionitem, 1, 8195), 5D, Rarity.JUNK, -1, RodType.GOOD));
		Fishing.fishing.addLoot(new Loot(new ItemStack(Items.nether_star), 1D, Rarity.RARE, -1, RodType.SUPER));
	}

	private static void addOverworldLoot() {		
		for (int i = 0; i < 12; i++) {
			Fishing.fishing.addLoot(new Loot(new ItemStack(Core.pearls, 1, i), 2.5D, Rarity.JUNK, 0, RodType.OLD));
			Fishing.fishing.addLoot(new Loot(new ItemStack(Core.pearls, 1, i), 5D, Rarity.GOOD, 0, RodType.OLD));
			Fishing.fishing.addLoot(new Loot(new ItemStack(Core.pearls, 1, i), 2.5D, Rarity.RARE, 0, RodType.OLD));
		}
		
		Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_far), 5D, Rarity.GOOD, 0, RodType.GOOD));
		Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_strad), 5D, Rarity.GOOD, 0, RodType.GOOD));
		Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_mall), 5D, Rarity.GOOD, 0, RodType.GOOD));
		Fishing.fishing.addLoot(new Loot(new ItemStack(Items.record_mall), 5D, Rarity.GOOD, 0, RodType.GOOD));
		
		if(Modules.isActive(Modules.factory))
		Fishing.fishing.addLoot(new Loot(new ItemStack(Factory.fludd), 1D, Rarity.RARE, 0, RodType.SUPER));
	}
}
