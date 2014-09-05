package joshie.maritech.gui;

import joshie.mariculture.core.gui.GuiMariculture;
import joshie.mariculture.core.gui.feature.FeatureBubbles;
import joshie.mariculture.core.gui.feature.FeatureEject;
import joshie.mariculture.core.gui.feature.FeatureNotifications;
import joshie.mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import joshie.mariculture.core.gui.feature.FeaturePower;
import joshie.mariculture.core.gui.feature.FeatureRedstone;
import joshie.mariculture.core.gui.feature.FeatureUpgrades;
import joshie.maritech.lib.MTModInfo;
import joshie.maritech.tile.TileIncubator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiIncubator extends GuiMariculture {
    public static final ResourceLocation texture = new ResourceLocation(MTModInfo.TEXPATH, "textures/gui/incubator.png");
    
    public GuiIncubator(InventoryPlayer player, TileIncubator tile) {
        super(new ContainerIncubator(tile, player), texture, 10);
        features.add(new FeatureUpgrades());
        features.add(new FeaturePower(tile, 9, 17));
        features.add(new FeatureBubbles(tile, 87, 16));
        features.add(new FeatureNotifications(tile, new NotificationType[] { NotificationType.NO_EGG, NotificationType.NO_RF }));
        features.add(new FeatureRedstone(tile));
        features.add(new FeatureEject(tile));
    }

    @Override
    public String getName() {
        return StatCollector.translateToLocal("tile.machines.multi.incubator.name");
    }
}