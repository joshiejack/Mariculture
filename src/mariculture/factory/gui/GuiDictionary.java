package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.helpers.InventoHelper;
import mariculture.factory.blocks.TileDictionary;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiDictionary extends GuiMariculture {
	private TileDictionary tile;

	public GuiDictionary(InventoryPlayer player, TileDictionary tile) {
		super(new ContainerDictionary(tile, player), "dictionary");
		this.tile = tile;
	}

	@Override
	public void drawBackground(int x, int y) {
		int progress = tile.getFreezeProgressScaled(24);
		this.drawTexturedModalRect(x + 77, y + 50, 176, 74, progress + 1, 16);
	}

	@Override
	public String getName() {
		return InventoHelper.getName(tile);
	}

	@Override
	public int getX() {
		return 10;
	}
}
