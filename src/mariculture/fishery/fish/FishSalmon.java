package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.fishery.Fishery;
import mariculture.fishery.FisheryEventHandler;
import mariculture.fishery.items.ItemFishyFood;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;

public class FishSalmon extends FishSpecies {
	public FishSalmon(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.RIVER;
	}

	@Override
	public int getLifeSpan() {
		return 25;
	}

	@Override
	public int getFertility() {
		return 83;
	}

	@Override
	public boolean isDominant() {
		return true;
	}
	
	@Override
	public ItemStack getProduct(Random rand) {
		return (rand.nextInt(100) == 0)? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER): null;
	}

	@Override
	public boolean canCatch(Random rand, World world, int x, int y, int z, EnumRodQuality quality) {
		if (world.provider.isSurfaceWorld()) {
			if (quality.getRank() >= EnumRodQuality.GOOD.getRank()) {
			if (rand.nextInt(8) == 128) {
				if (!Fishing.fishHelper.biomeMatches(world.getWorldChunkManager().getBiomeGenAt(x, z),
						new EnumBiomeType[] { EnumBiomeType.ARID })) {
					return true;
				}
			}
		}}

		return false;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.900;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 8 };
	}
	
	@Override
	public int getFishMealSize() {
		return 5;
	}
}
