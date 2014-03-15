package mariculture.core.util;

import net.minecraft.item.ItemBlock;

public interface IHasMeta {
	public int getMetaCount();
	public Class<? extends ItemBlock> getItemClass();
}
