package mariculture.api.fishery;

import java.util.List;
import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import net.minecraft.entity.player.EntityPlayer;
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
	 * @param biomes - list of biome types you can catch this in, return null for all biomes (Also adds to the vanilla loot list).
	 */
	public void addLoot(LootQuality quality, WeightedRandomFishable loot, List<EnumBiomeType> biomes);

	/** This will generate random loot making use of the parameters specified (Player can be null) **/
	public ItemStack getLoot(EntityPlayer player, ItemStack rod, int baitQuality, Random rand, World world, int x, int y, int z);
}
