package mariculture.core.blocks;

import mariculture.core.blocks.base.TileStorage;
import mariculture.core.util.IHasGUI;
import net.minecraft.item.ItemStack;

public class TileBookshelf extends TileStorage implements IHasGUI {
	public TileBookshelf() {
		this.inventory = new ItemStack[9];
	}
	
	@Override
	public boolean canUpdate() {
		return false;
	}
}
