package mariculture.lib.util;

import net.minecraft.item.ItemBlock;

public interface IHasMetaBlock {
    public int getMetaCount();
    public Class<? extends ItemBlock> getItemClass();
}
