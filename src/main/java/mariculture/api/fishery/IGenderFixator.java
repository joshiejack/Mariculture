package mariculture.api.fishery;

import net.minecraft.item.ItemStack;

/** Guarantees the gender of a fish when this item is worn
 *  Helmet takes priority, boots lowest */
public interface IGenderFixator {
	/** Return 0 for male, or 1 for female **/
	public Integer getGender(ItemStack stack);
}
