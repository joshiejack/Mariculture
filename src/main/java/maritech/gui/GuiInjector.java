package maritech.gui;

import java.util.List;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureEject;
import mariculture.core.gui.feature.FeaturePower;
import mariculture.core.gui.feature.FeatureRedExtras;
import mariculture.core.gui.feature.FeatureRedstone;
import mariculture.core.gui.feature.FeatureTank;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.gui.feature.FeatureUpgrades;
import maritech.lib.MTModInfo;
import maritech.tile.TileInjector;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class GuiInjector extends GuiMariculture {
    public static final ResourceLocation texture = new ResourceLocation(MTModInfo.MODPATH, "textures/gui/injector.png");
    private TileInjector tile;

    public GuiInjector(InventoryPlayer player, TileInjector tile) {
        super(new ContainerInjector(tile, player), texture, 10);
        this.tile = tile;
        features.add(new FeatureUpgrades());
        features.add(new FeatureRedstone(tile));
        features.add(new FeatureEject(tile));
        features.add(new FeaturePower(tile, 9, 17));
        features.add(new FeatureTank(tile.tank, 48, 19, TankSize.SINGLE));
        features.add(new FeatureTank(tile.tank2, 66, 19, TankSize.SINGLE));
    }

    @Override
    public void addToolTip() {
        return;
    }

    @Override
    public void addItemToolTip(ItemStack stack, List<String> list) {
        return;
    }

    @Override
    public void drawBackground(int x, int y) {
        int progress = tile.getProgressScaled(53);
        drawTexturedModalRect(x + 107, y + 44, 194, 25, progress, 44);
    }
}
