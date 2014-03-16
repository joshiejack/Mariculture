package mariculture.api.fishery;

import java.util.List;
import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomFishable;
import net.minecraft.world.World;

public interface ILootHandler {
	public static enum LootQuality {
		BAD, GOOD, RARE, FISH
	}
	
	/** Add new loots to the Mariculture fishing system
	 * 
	 * @param quality - The loot list it should be added to
	 * @param loot - The loot itself
	 * @param biomes - list of biome types you can catch this in, return null for all biomes (Also adds to the vanilla loot list). */
	public void addLoot(LootQuality quality, WeightedRandomFishable loot, List<EnumBiomeType> biomes);
	
	/** Adds special loot for different rods
	 * 
	 * @param item - The rod Item
	 * @param chance - This is a chance in %, set to 100 for 100% catch chance
	 * @param baitQuality - The minimum bait quality, set to -1 for the quality to not matter
	 * @param biome - leave this as null if you don't care which biome type this is caught in */
	public void addSpecialLoot(Item item, int chance, int baitQuality, EnumBiomeType biome, ItemStack[] loot);

	/** This will generate random loot making use of the parameters specified (Player can be null) **/
	public ItemStack getLoot(EntityPlayer player, ItemStack rod, int baitQuality, Random rand, World world, int x, int y, int z);
	
	/** This returns a fish for the location
	 * 
	 * @param player - The player catching (can be null)
	 * @param biome - The biome type
	 * @param rand - random
	 * @param quality - The loot quality you want to return
	 * @returns an item that can be caught in this biome type */
	public ItemStack getCatch(EntityPlayer player, EnumBiomeType biome, Random rand, LootQuality quality);
	
	/** This is called before any other fishing catch methods are called with loot, you can use it for special catches
	 * 
	 * @param player - The player
	 * @param rod - The rod stack
	 * @param biome - The biome they're fishing in
	 * @param rand - Rand
	 * @return  */
	public ItemStack getSpecialCatch(EntityPlayer player, Item rod, int baitQuality, EnumBiomeType biome, Random rand);
}
