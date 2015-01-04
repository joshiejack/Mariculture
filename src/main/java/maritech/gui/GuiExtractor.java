package maritech.gui;

import java.util.List;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureEject;
import mariculture.core.gui.feature.FeaturePower;
import mariculture.core.gui.feature.FeatureRedstone;
import mariculture.core.gui.feature.FeatureTank;
import mariculture.core.gui.feature.FeatureUpgrades;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import maritech.lib.MTModInfo;
import maritech.tile.TileExtractor;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiExtractor extends GuiMariculture {
    public static final ResourceLocation texture = new ResourceLocation(MTModInfo.MODPATH, "textures/gui/extractor.png");
    private TileExtractor tile;

    public GuiExtractor(InventoryPlayer player, TileExtractor tile) {
        super(new ContainerExtractor(tile, player), texture, 10);
        this.tile = tile;
        features.add(new FeatureUpgrades());
        //features.add(new FeatureNotifications(tile, new NotificationType[] { NotificationType.NO_FOOD, NotificationType.NO_MALE, NotificationType.NO_FEMALE, NotificationType.BAD_ENV }));
        features.add(new FeatureRedstone(tile));
        features.add(new FeatureEject(tile));
        features.add(new FeaturePower(tile, 9, 17));
        features.add(new FeatureTank(tile, 30, 19, TankSize.SINGLE));
    }

    @Override
    public int getX() {
        return 10;
    }

    @Override
    public void addItemToolTip(ItemStack stack, List<String> list) {
        return;
    }

    @Override
    public void drawBackground(int x, int y) {
        int progress = tile.getProgressScaled(47);
        drawTexturedModalRect(x + 85, y + 7, 209, 7, progress, 81);
    }
}
