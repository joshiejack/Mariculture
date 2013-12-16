package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.helpers.InventoryHelper;
import mariculture.factory.blocks.TileFishSorter;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiFishSorter extends GuiMariculture {

	public TileFishSorter tile;
	
	public GuiFishSorter(InventoryPlayer player, TileFishSorter tile) {
		super(new ContainerFishSorter(tile, player), "fishsorter");
		this.tile = tile;
	}

	@Override
	public void drawForeground() {
		this.fontRenderer.drawString(InventoryHelper.getName(tile), 40, 6, 4210752);
	}

	@Override
	public void drawBackground(int x, int y) {				
		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 7; ++k) {
				this.drawTexturedModalRect(x + 37 + k * 18, y + 20 + j * 18, 176, tile.getSide(k + j * 7), 18, 18);
			}
		}
		
		this.drawTexturedModalRect(x + 8, y + 53, 194, tile.getDefault(), 18, 18);
	}
}
