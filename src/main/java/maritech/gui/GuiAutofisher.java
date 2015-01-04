package maritech.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureBubbles;
import mariculture.core.gui.feature.FeatureEject;
import mariculture.core.gui.feature.FeatureNotifications;
import mariculture.core.gui.feature.FeaturePower;
import mariculture.core.gui.feature.FeatureRedstone;
import mariculture.core.gui.feature.FeatureUpgrades;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import maritech.lib.MTModInfo;
import maritech.tile.TileAutofisher;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiAutofisher extends GuiMariculture {
    public static final ResourceLocation texture = new ResourceLocation(MTModInfo.MODPATH, "textures/gui/autofisher.png");
    
    private TileAutofisher tile;

    public GuiAutofisher(InventoryPlayer player, TileAutofisher tile) {
        super(new ContainerAutofisher(tile, player), texture, 10);
        features.add(new FeatureUpgrades());
        features.add(new FeaturePower(tile, 9, 17));
        features.add(new FeatureBubbles(tile, 87, 16));
        features.add(new FeatureNotifications(tile, new NotificationType[] { NotificationType.NO_ROD, NotificationType.NO_BAIT, NotificationType.NOT_FISHABLE, NotificationType.NO_RF }));
        features.add(new FeatureRedstone(tile));
        features.add(new FeatureEject(tile));

        this.tile = tile;
    }

    @Override
    public void drawBackground(int x, int y) {
        if (tile.getInventory()[TileAutofisher.rod] == null) {
            drawTexturedModalRect(x + 49, y + 18, 238, 18, 16, 16);
        }
    }
}