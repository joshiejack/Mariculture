package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.helpers.InventoryHelper;
import mariculture.factory.blocks.TileFishSorter;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiFishSorter extends GuiMariculture {
	private TileFishSorter tile;
	
	public GuiFishSorter(InventoryPlayer player, TileFishSorter tile) {
		super(new ContainerFishSorter(tile, player), "fishsorter");
		this.tile = tile;
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

	@Override
	public String getName() {
		return InventoryHelper.getName(tile);
	}

	@Override
	public int getX() {
		return 40;
	}
}
