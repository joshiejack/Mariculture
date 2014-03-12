package mariculture.fishery;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.ILootHandler;
import mariculture.core.helpers.ReflectionHelper;
import mariculture.core.lib.Extra;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;

public class FishingLootHandler implements ILootHandler {
	public static List getFinalStatic(Field field) throws Exception {
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		return (List) field.get(field);
	}

	public static void addVanillaLoot(LootQuality quality, WeightedRandomFishable loot) {
		try {
			if(quality == LootQuality.FISH) {
				List list = new ArrayList(getFinalStatic(EntityHook.class.getField("field_146036_f")));
				list.add(loot);
				ReflectionHelper.setFinalStatic(EntityHook.class.getField("field_146036_f"), list);
			} else if(quality == LootQuality.BAD) {
				List list = new ArrayList(getFinalStatic(EntityHook.class.getField("field_146039_d")));
				list.add(loot);
				ReflectionHelper.setFinalStatic(EntityHook.class.getField("field_146039_d"), list);
			} else {
				List list = new ArrayList(getFinalStatic(EntityHook.class.getField("field_146041_e")));
				list.add(loot);
				ReflectionHelper.setFinalStatic(EntityHook.class.getField("field_146041_e"), list);
			}
		} catch (Exception e)  {
			e.printStackTrace();
		}
	}

	public static void addMaricultureLoot(LootQuality quality, WeightedRandomFishable loot) {
		for (EnumBiomeType biome : EnumBiomeType.values()) {
			if (quality == LootQuality.BAD) {
				List list = EntityHook.bad_loot.get(biome);
				list.add(loot);
				EntityHook.bad_loot.put(biome, list);
			} else if (quality == LootQuality.FISH) {
				List list = EntityHook.fish_loot.get(biome);
				list.add(loot);
				EntityHook.fish_loot.put(biome, list);
			} else if (quality == LootQuality.GOOD) {
				List list = EntityHook.good_loot.get(biome);
				list.add(loot);
				EntityHook.good_loot.put(biome, list);
			} else if (quality == LootQuality.RARE) {
				List list = EntityHook.rare_loot.get(biome);
				list.add(loot);
				EntityHook.rare_loot.put(biome, list);
			}
		}
	}

	@Override
	public void addLoot(LootQuality quality, WeightedRandomFishable loot, List<EnumBiomeType> biomes) {
		if (biomes == null) {
			addVanillaLoot(quality, loot);
			addMaricultureLoot(quality, loot);
		} else {
			for (EnumBiomeType biome : biomes) {
				if (quality == LootQuality.BAD) {
					List list = EntityHook.bad_loot.get(biome);
					list.add(loot);
					EntityHook.bad_loot.put(biome, list);
				} else if (quality == LootQuality.FISH) {
					List list = EntityHook.fish_loot.get(biome);
					list.add(loot);
					EntityHook.fish_loot.put(biome, list);
				} else if (quality == LootQuality.GOOD) {
					List list = EntityHook.good_loot.get(biome);
					list.add(loot);
					EntityHook.good_loot.put(biome, list);
				} else if (quality == LootQuality.RARE) {
					List list = EntityHook.rare_loot.get(biome);
					list.add(loot);
					EntityHook.rare_loot.put(biome, list);
				}
			}
		}
	}

	@Override
	public ItemStack getLoot(EntityPlayer player, ItemStack rod, int baitQuality, Random rand, World world, int x, int y, int z) {
		EnumRodQuality quality = Fishing.rodHandler.getRodQuality(rod);
		EnumBiomeType biome = MaricultureHandlers.biomeType.getBiomeType(world.getBiomeGenForCoords((int) x, (int) z));
		float f = world.rand.nextFloat();
		int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_151370_z.effectId, rod);
		int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.field_151369_A.effectId, rod);
		float f1 = 0.1F - (float) i * 0.025F - (float) j * 0.01F;
		float f2 = 0.05F + (float) i * 0.01F - (float) j * 0.01F;
		f1 = MathHelper.clamp_float(f1, 0.0F, 1.0F);
		f2 = MathHelper.clamp_float(f2, 0.0F, 1.0F);

		EnumRodQuality min = Extra.VANILLA_POOR? EnumRodQuality.OLD: EnumRodQuality.DIRE;
		if ((f < f1 && quality.getRank() >= min.getRank()) || (Extra.VANILLA_POOR && baitQuality == 0 && rand.nextInt(10) != 0)) {
			if (player != null)
				player.addStat(StatList.field_151183_A, 1);
			return ((WeightedRandomFishable) WeightedRandom.getRandomItem(rand, EntityHook.bad_loot.get(biome))).func_150708_a(rand);
		} else {
			f -= f1;

			if (f < f2 && quality.getRank() >= EnumRodQuality.GOOD.getRank()) {
				if (player != null)
					player.addStat(StatList.field_151184_B, 1);
				return ((WeightedRandomFishable) WeightedRandom.getRandomItem(rand, EntityHook.good_loot.get(biome))).func_150708_a(rand);
			} else if (rand.nextFloat() > 0.975F && quality.getRank() >= EnumRodQuality.SUPER.getRank()) {
				if (player != null)
					player.addStat(StatList.field_151184_B, 10);
				return ((WeightedRandomFishable) WeightedRandom.getRandomItem(rand, EntityHook.rare_loot.get(biome))).func_150708_a(rand);
			} else {
				float f3 = f - f2;
				if (player != null)
					player.addStat(StatList.fishCaughtStat, 1);
				return ((WeightedRandomFishable) WeightedRandom.getRandomItem(rand, EntityHook.fish_loot.get(biome))).func_150708_a(rand);
			}
		}
	}
}
