package mariculture.core.tile;

import mariculture.core.tile.base.TileStorage;
import net.minecraft.item.ItemStack;

public class TileAutohammer extends TileStorage {
    public TileAutohammer() {
        inventory = new ItemStack[4];
    }
}
