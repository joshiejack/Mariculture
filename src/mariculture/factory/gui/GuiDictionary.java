package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureArrow;
import mariculture.factory.blocks.TileDictionary;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiDictionary extends GuiMariculture {
	public GuiDictionary(InventoryPlayer player, TileDictionary tile) {
		super(new ContainerDictionary(tile, player), "dictionary", 10);
		features.add(new FeatureArrow(tile, 77, 50));
	}

	@Override
	public int getX() {
		return 10;
	}
}
