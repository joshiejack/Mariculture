package mariculture.api.fishery;

import java.util.List;

import net.minecraft.item.ItemStack;

public interface IRodQuality {
	/**
	 * Helper for adding your own EnumRodQuality
	 * @param Name of rod quality
	 * @param Maximum usage until it breaks  **/
	public EnumRodQuality addRodQuality(String name, int maxUses);

	/**
	 * Lets you add bait to specific qualities of rods
	 * 
	 * @param The bait
	 * @param the rod quality type, ensure if you add a new one, you have done that first! **/
	public void addBaitForQuality(ItemStack bait, EnumRodQuality quality);

	/**
	 * Same as above except you can add multiple at once
	 * 
	 * Example: addBaitForQuality(new ItemStack(Items.fishRaw),
	 * Arrays.asList(EnumRodQuality.OLD, EnumRodQuality.GOOD,
	 * EnumRodQuality.SUPER));  **/
	public void addBaitForQuality(ItemStack bait, List<EnumRodQuality> rods);

	/**
	 * As before, but let's you remove them
	 * 
	 * @param The bait to remove
	 * @param The quality you wish to remove the bait from **/
	public void removeBaitForQuality(ItemStack bait, EnumRodQuality quality);

	/** Same again but multiple **/
	public void removeBaitForQuality(ItemStack bait, List<EnumRodQuality> rods);

	/**
	 * @param Itemstack to compare
	 * @param to  compare
	 * @return returns true if this quality rod can use the current bait  */
	public boolean canUseBait(ItemStack stack, EnumRodQuality quality);
}
