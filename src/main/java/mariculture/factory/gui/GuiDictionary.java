package mariculture.factory.gui;

import java.util.List;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureArrow;
import mariculture.core.gui.feature.FeatureEject;
import mariculture.core.gui.feature.FeatureRedstone;
import mariculture.factory.blocks.TileDictionary;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class GuiDictionary extends GuiMariculture {
	public GuiDictionary(InventoryPlayer player, TileDictionary tile) {
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
