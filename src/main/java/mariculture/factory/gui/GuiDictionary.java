package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureEject;
import mariculture.factory.tile.TileDictionaryItem;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiDictionary extends GuiMariculture {
	public GuiDictionary(InventoryPlayer player, TileDictionaryItem tile) {
		super(new ContainerDictionary(tile, player), "dictionary", 14);
		features.add(new FeatureEject(tile));
		nameHeight = 5;
		inventOffset = 4;
	}

	@Override
	public int getX() {
		return 10;
	}
}
