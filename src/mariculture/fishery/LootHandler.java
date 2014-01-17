package mariculture.fishery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ILootHandler;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class LootHandler implements ILootHandler {
	private final ArrayList<FishingLoot> fishingLoot = new ArrayList<FishingLoot>();

	@Override
	public void addLoot(ItemStack stack, Object... args) {
		switch (args.length) {
		case 2:
			fishingLoot.add(new FishingLoot(stack, (EnumRodQuality) args[0], (Integer) args[1], OreDictionary.WILDCARD_VALUE));
			break;
		case 3:
			fishingLoot.add(new FishingLoot(stack, (EnumRodQuality) args[0], (Integer) args[1], (Integer) args[2]));
			break;
		}
	}

	@Override
	public ItemStack getLoot(Random rand, EnumRodQuality quality, World world, int x, int y, int z) {
		Collections.shuffle(fishingLoot);
		Iterator it = fishingLoot.iterator();
		while (it.hasNext()) {
			FishingLoot loot = (FishingLoot) it.next();
			int rarity = loot.rarity;
			if (quality.getRank() >= loot.quality.getRank()) {
				if (world.provider.dimensionId == loot.dimension
						|| (loot.dimension == OreDictionary.WILDCARD_VALUE)
						|| (loot.dimension == 0 && (world.provider.dimensionId == 0 || world.provider.dimensionId > 1 || world.provider.dimensionId < -1))) {
					if (rand.nextInt(rarity) == 0) {
						ItemStack ret = loot.loot;
						if (ret.isItemEnchantable() && !ret.isItemEnchanted()) {
							if (quality.getRank() > quality.GOOD.getRank()) {
								int enchant = quality.getEnchantability();
								enchant *= ret.itemID == Item.book.itemID ? 2 : 1;
								EnchantmentHelper.addRandomEnchantment(rand, ret, enchant);
							}
						}

						if (ret.isItemStackDamageable()) {
							if (quality.getRank() < quality.GOOD.getRank()) {
								int dmg = rand.nextInt(ret.getMaxDamage());
								ret.setItemDamage(dmg);
							}
						}

						return ret;
					}
				}
			}
		}

		return getFishForLocation(rand, quality, world, x, y, z);
	}

	private ItemStack getFishForLocation(Random rand, EnumRodQuality quality, World world, int x, int y, int z) {
		for (int i = 0; i < FishSpecies.speciesList.size(); i++) {
			FishSpecies fish = FishSpecies.speciesList.get(i);
			if (fish != null) {
				if (fish.canCatch(rand, world, x, y, z, quality)) {
					if(fish.caughtAsRaw())
						return new ItemStack(Fishery.fishyFood, 1, fish.fishID);
					return Fishing.fishHelper.makePureFish(FishSpecies.speciesList.get(i));
				}
			}
		}

		return getFishForLocation(rand, quality, world, x, y, z);
	}

	public class FishingLoot {
		private int rarity;
		private ItemStack loot;
		private EnumRodQuality quality;
		private int dimension;

		private FishingLoot(ItemStack output, EnumRodQuality quality, int rarity, int dimension) {
			this.rarity = rarity;
			this.loot = output;
			this.quality = quality;
			this.dimension = dimension;
		}
	}
}
