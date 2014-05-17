package mariculture.api.fishery;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomFishable;

public class Loot {
	public enum Rarity {
		JUNK, GOOD, RARE
	}
	
	//The Minimum Rod Type required for this loot to be catchable
	public RodType quality;
	
	//If this is true, you can ONLY catch this loot with the specified rod quality, otherwise it's the minimum need
	public boolean exact;

	//Which list should this item be added to, Items in the Unique list can ONLY be caught by the Quality Specified
	public Rarity rarity;
	
	//The dimensions this loot can be caught in, Use the Short.MAX_VALUE for any for any dimension
	public int dimension;
	
	//The chance that you will catch this fish loot, from 0-100
	public double chance;
	
	//The loot itself
	public ItemStack loot;

	public Loot(ItemStack loot, double chance, Rarity rarity, int dimension, RodType quality, boolean exact) {
		this.loot = loot;
		this.chance = chance;
		this.rarity = rarity;
		this.quality = quality;
		this.dimension = dimension;
		this.exact = exact;
	}
	
	public Loot(ItemStack loot, double chance, Rarity rarity, int dimension, RodType quality) {
		this.loot = loot;
		this.chance = chance;
		this.rarity = rarity;
		this.quality = quality;
		this.dimension = dimension;
		this.exact = false;
	}
	
	public Loot(ItemStack loot, double chance, Rarity rarity, int dimension) {
		this.loot = loot;
		this.chance = chance;
		this.rarity = rarity;
		this.quality = null;
		this.dimension = dimension;
		this.exact = false;
	}
	
	public Loot(ItemStack loot, double chance, Rarity rarity) {
		this.loot = loot;
		this.chance = chance;
		this.rarity = rarity;
		this.quality = null;
		this.dimension = Short.MAX_VALUE;
		this.exact = false;
	}
}