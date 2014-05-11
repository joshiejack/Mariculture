package mariculture.fishery;

import static mariculture.core.lib.Items.bone;
import static mariculture.core.lib.Items.book;
import static mariculture.core.lib.Items.bowl;
import static mariculture.core.lib.Items.enderPearl;
import static mariculture.core.lib.Items.eyeOfEnder;
import static mariculture.core.lib.Items.ghastTear;
import static mariculture.core.lib.Items.goldNugget;
import static mariculture.core.lib.Items.ink;
import static mariculture.core.lib.Items.ironWheel;
import static mariculture.core.lib.Items.leather;
import static mariculture.core.lib.Items.nameTag;
import static mariculture.core.lib.Items.netherStar;
import static mariculture.core.lib.Items.rottenFlesh;
import static mariculture.core.lib.Items.stick;
import static mariculture.core.lib.Items.string;
import static mariculture.core.lib.Items.vanillaFish;
import static mariculture.core.lib.Items.voidBottle;
import static mariculture.core.lib.Items.xpBottle;
import mariculture.api.fishery.Loot.Rarity;
import mariculture.api.fishery.RodQuality;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.Modules;
import mariculture.factory.Factory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FishingLoot {
	public static void add() {
		addOverworldLoot();
		addNetherLoot();
		addEndLoot();

		RecipeHelper.addLoot(xpBottle, RodQuality.OLD, Rarity.JUNK, 5);
		RecipeHelper.addLoot(xpBottle, RodQuality.OLD, Rarity.GOOD, 5);
		RecipeHelper.addLoot(nameTag, RodQuality.OLD, Rarity.GOOD, 25);
		RecipeHelper.addLoot(ironWheel, RodQuality.OLD, Rarity.JUNK, 10);
		RecipeHelper.addLoot(book, RodQuality.OLD, Rarity.JUNK, 6);
	}

	private static void addEndLoot() {
		RecipeHelper.addEndLoot(voidBottle, RodQuality.OLD, Rarity.JUNK, 7);
		RecipeHelper.addEndLoot(enderPearl, RodQuality.OLD, Rarity.GOOD, 5);
		RecipeHelper.addEndLoot(eyeOfEnder, RodQuality.OLD, Rarity.GOOD, 4);
		RecipeHelper.addEndLoot(Item.record11, RodQuality.GOOD, Rarity.GOOD, 10);
		RecipeHelper.addEndLoot(Item.recordMellohi, RodQuality.GOOD, Rarity.GOOD, 10);
		RecipeHelper.addEndLoot(Item.recordStal, RodQuality.GOOD, Rarity.GOOD, 10);
	}

	private static void addNetherLoot() {
		RecipeHelper.addNetherLoot(goldNugget, RodQuality.OLD, Rarity.JUNK, 2);
		RecipeHelper.addNetherLoot(ghastTear, RodQuality.OLD, Rarity.GOOD, 3);
		RecipeHelper.addNetherLoot(Item.record13, RodQuality.GOOD, Rarity.GOOD, 10);
		RecipeHelper.addNetherLoot(Item.recordBlocks, RodQuality.GOOD, Rarity.GOOD, 10);
		RecipeHelper.addNetherLoot(Item.recordChirp, RodQuality.GOOD, Rarity.GOOD, 10);
		RecipeHelper.addNetherLoot(new ItemStack(Item.potion, 1, 8195), RodQuality.GOOD, Rarity.GOOD, 10);
		RecipeHelper.addNetherLoot(netherStar, RodQuality.SUPER, Rarity.GOOD, 250);
	}

	private static void addOverworldLoot() {
		for (int i = 0; i < 12; i++) {
			RecipeHelper.addOverworldLoot(new ItemStack(Core.pearls, 1, i), RodQuality.GOOD, Rarity.JUNK, 15);
		}

		RecipeHelper.addOverworldLoot(bowl, RodQuality.OLD, Rarity.JUNK, 3);
		RecipeHelper.addOverworldLoot(stick, RodQuality.OLD, Rarity.JUNK, 3);
		RecipeHelper.addOverworldLoot(string, RodQuality.OLD, Rarity.JUNK, 4);
		RecipeHelper.addOverworldLoot(leather, RodQuality.OLD, Rarity.JUNK, 6);
		RecipeHelper.addOverworldLoot(rottenFlesh, RodQuality.OLD, Rarity.JUNK, 4);
		RecipeHelper.addOverworldLoot(bone, RodQuality.OLD, Rarity.JUNK, 6);
		RecipeHelper.addOverworldLoot(ink, RodQuality.OLD, Rarity.JUNK, 3);
		RecipeHelper.addOverworldLoot(Item.bootsLeather, RodQuality.OLD, Rarity.JUNK, 5);
		RecipeHelper.addOverworldLoot(Item.potion, RodQuality.OLD, Rarity.JUNK, 10);
		RecipeHelper.addOverworldLoot(Item.bow, RodQuality.OLD, Rarity.JUNK, 10);

		RecipeHelper.addOverworldLoot(Item.saddle, RodQuality.GOOD, Rarity.GOOD, 12);
		RecipeHelper.addOverworldLoot(Item.recordFar, RodQuality.GOOD, Rarity.GOOD, 15);
		RecipeHelper.addOverworldLoot(Item.recordMall, RodQuality.GOOD, Rarity.GOOD, 15);
		RecipeHelper.addOverworldLoot(Item.recordStrad, RodQuality.GOOD, Rarity.GOOD, 15);
		RecipeHelper.addOverworldLoot(Item.recordWard, RodQuality.GOOD, Rarity.GOOD, 15);
		RecipeHelper.addOverworldLoot(Item.recordCat, RodQuality.GOOD, Rarity.GOOD, 15);

		if (Modules.isActive(Modules.factory)) {
			RecipeHelper.addOverworldLoot(new ItemStack(Factory.fludd), RodQuality.SUPER, Rarity.GOOD, 250);
		}

		RecipeHelper.addOverworldLoot(vanillaFish, RodQuality.OLD, Rarity.JUNK, 5);
		RecipeHelper.addOverworldLoot(vanillaFish, RodQuality.OLD, Rarity.GOOD, 3);
		RecipeHelper.addOverworldLoot(new ItemStack(Block.waterlily), RodQuality.OLD, Rarity.JUNK, 5);
		RecipeHelper.addOverworldLoot(new ItemStack(Block.waterlily), RodQuality.OLD, Rarity.GOOD, 5);
	}
}
