package mariculture.api.fishery;

import net.minecraft.item.ItemStack;

public class Loot {
	public enum Rarity {
		JUNK, GOOD
	}
	
	//This is whether you are required to use the rod quality specified, otherwise it's the minimum required
	public boolean exact;
	
	//The RodQuality needed, depends on the boolean above how it functions
	public RodQuality quality;
	
	//This is literally a rand.nextInt(rarity), so higher = less common
	public int rarity;
	
	//Whether the loot should be added to the junk or the good loot list
	public Rarity type;
	
	//The item itself
	public ItemStack loot;
	
	//The dimensions this loot can be caught in, Use OreDictionary.WILDCARD_VALUE for any dimension
	public int dimension;

	public Loot(ItemStack output, RodQuality quality, Rarity type, int rarity, int dimension, boolean exact) {
		this.rarity = rarity;
		this.loot = output;
		this.quality = quality;
		this.dimension = dimension;
		this.exact = exact;
		this.type = type;
	}
}