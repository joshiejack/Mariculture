package joshie.mariculture.fishery.gui;

import joshie.mariculture.core.gui.GuiMariculture;
import joshie.mariculture.core.gui.feature.FeatureBubbles;
import joshie.mariculture.core.gui.feature.FeatureEject;
import joshie.mariculture.core.gui.feature.FeatureNotifications;
import joshie.mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import joshie.mariculture.core.gui.feature.FeaturePower;
import joshie.mariculture.core.gui.feature.FeatureRedstone;
import joshie.mariculture.core.gui.feature.FeatureUpgrades;
import joshie.mariculture.fishery.tile.TileIncubator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

public class GuiIncubator extends GuiMariculture {
    public GuiIncubator(InventoryPlayer player, TileIncubator tile) {
        super(new ContainerIncubator(tile, player), "incubator", 10);
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