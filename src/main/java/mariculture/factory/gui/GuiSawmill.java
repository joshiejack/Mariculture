package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureArrow;
import mariculture.core.gui.feature.FeatureEject;
import mariculture.core.gui.feature.FeatureNotifications;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone;
import mariculture.core.gui.feature.FeatureUpgrades;
import mariculture.factory.tile.TileSawmill;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiSawmill extends GuiMariculture {
    public TileSawmill tile;

    public GuiSawmill(InventoryPlayer player, TileSawmill tile) {
        super(new ContainerSawmill(tile, player), "sawmill", 10);
        this.tile = tile;
        features.add(new FeatureUpgrades());
        features.add(new FeatureArrow(tile, 119, 39));
        features.add(new FeatureNotifications(tile, new NotificationType[] { NotificationType.NO_PLAN, NotificationType.MISSING_SIDE }));
        features.add(new FeatureRedstone(tile));
        features.add(new FeatureEject(tile));
    }

    @Override
    public int getX() {
        return 54;
    }

    @Override
    public void drawBackground(int x, int y) {
        int y2 = 18 + (tile.selected - 3) * 20;
        drawTexturedModalRect(x + 9, y + y2, 0, 207, 20, 20);
    }
}