package mariculture.fishery.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.fishery.tile.TileSifter;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiSift extends GuiMariculture {
    public GuiSift(InventoryPlayer player, TileSifter tile) {
        super(new ContainerSift(tile, player), "sift_storage");
    }

    @Override
    public int getX() {
        return 72;
    }
}