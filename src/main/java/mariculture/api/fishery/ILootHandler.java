package mariculture.api.fishery;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ILootHandler {
	/** Add some fishing loot **/
	public void addLoot(Loot loot);
	
	/** This will generate random loot making use of the parameters specified **/
	public ItemStack getLoot(Random rand, RodType quality, World world, int x, int y, int z);
}
