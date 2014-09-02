package joshie.mariculture.factory.gui;

import joshie.mariculture.core.gui.GuiMariculture;
import joshie.mariculture.core.gui.feature.FeatureEject;
import joshie.mariculture.factory.tile.TileDictionaryItem;
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
