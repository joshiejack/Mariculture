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
				if (!Fishing.fishHelper.biomeMatches(world.getBiomeGenForCoords(x, z),
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
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(Item.leather);
	}

	@Override
	public int getLiquifiedProductChance() {
		return 5;
	}

	public static void cook() {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		for (int i = 0; i < server.worldServers.length; i++) {
			World world = server.worldServers[i];

			for (int j = 0; j < world.loadedEntityList.size(); j++) {
				if (world.loadedEntityList.get(j) instanceof EntityItem) {
					EntityItem item = (EntityItem) world.loadedEntityList.get(j);
					ItemStack stack = item.getEntityItem();

					if (stack.getItem() instanceof ItemFishyFood) {
						if (stack.getItemDamage() == Fishery.salmon.fishID) {
							if (item.worldObj.getBlockMaterial((int) item.posX, (int) item.posY - 2, (int) item.posZ) == Material.fire) {
								if (item.worldObj.getBlockLightValue((int) item.posX, (int) item.posY, (int) item.posZ) == 0F) {
									NBTTagCompound tagCompound = item.getEntityData();
									if (tagCompound.hasKey("CookTime")) {
										tagCompound.setInteger("CookTime", tagCompound.getInteger("CookTime") - 1);
										if (tagCompound.getInteger("CookTime") <= 0) {
											tagCompound.removeTag("CookTime");

											ItemStack food = new ItemStack(Core.food, stack.stackSize, FoodMeta.SMOKED_SALMON);
											FisheryEventHandler.updateStack(item.worldObj, item, 5000, food, new Random());
										}
									}

									else {
										tagCompound.setInteger("CookTime", 3);
									}
								}
							}
						}
					}
				}
			}
		}

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
