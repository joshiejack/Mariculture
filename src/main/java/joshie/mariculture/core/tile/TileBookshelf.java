package joshie.mariculture.core.tile;

import joshie.mariculture.core.tile.base.TileStorage;
import joshie.mariculture.core.util.IHasGUI;
import net.minecraft.item.ItemStack;

public class TileBookshelf extends TileStorage implements IHasGUI {
    public TileBookshelf() {
        inventory = new ItemStack[9];
    }
}
