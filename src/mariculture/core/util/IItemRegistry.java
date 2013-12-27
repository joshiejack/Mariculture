package mariculture.core.util;

import net.minecraft.item.ItemStack;

public interface IItemRegistry {
	public void register();
	public int getMetaCount();
	public String getName(ItemStack stack);
}
