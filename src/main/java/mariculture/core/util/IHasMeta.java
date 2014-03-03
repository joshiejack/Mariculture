package mariculture.core.util;

import net.minecraft.item.ItemBlock;

public interface IHasMeta {
	public Class<? extends ItemBlock> getItemClass();
}
