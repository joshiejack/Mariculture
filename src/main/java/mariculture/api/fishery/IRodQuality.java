package mariculture.api.fishery;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IRodQuality {
	/**
	 * Lets you add bait to specific qualities of rods
	 * 
	 * @param The bait
	 * @param the rod quality type, ensure if you add a new one, you have done that first! **/
	public void addBaitForQuality(ItemStack bait, RodType quality);

	/**
	 * Same as above except you can add multiple at once
	 * 
	 * Example: addBaitForQuality(new ItemStack(Item.fishRaw),
	 * Arrays.asList(EnumRodQuality.OLD, EnumRodQuality.GOOD,
	 * EnumRodQuality.SUPER));  **/
	public void addBaitForQuality(ItemStack bait, List<RodType> rods);

	/**
	 * As before, but let's you remove them
	 * 
	 * @param The bait to remove
	 * @param The quality you wish to remove the bait from **/
	public void removeBaitForQuality(ItemStack bait, RodType quality);

	/** Same again but multiple **/
	public void removeBaitForQuality(ItemStack bait, List<RodType> rods);

	/**
	 * @param Itemstack to compare
	 * @param to  compare
	 * @return returns true if this quality rod can use the current bait  */
	public boolean canUseBait(ItemStack stack, RodType quality);
}
