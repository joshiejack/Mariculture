package mariculture.core.tile;

import mariculture.core.tile.base.TileStorage;
import mariculture.core.util.IHasGUI;
import net.minecraft.item.ItemStack;

public class TileBookshelf extends TileStorage implements IHasGUI {
    public TileBookshelf() {
        inventory = new ItemStack[9];
    }
}
