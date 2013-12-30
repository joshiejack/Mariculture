package mariculture.fishery.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.fishery.blocks.TileSift;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiSift extends GuiMariculture {	
	public GuiSift(InventoryPlayer player, TileSift tile) {
		super(new ContainerSift(tile, player), "sift_storage");
	}
	
	@Override
	public int getX() {
		return 72;
	}
}