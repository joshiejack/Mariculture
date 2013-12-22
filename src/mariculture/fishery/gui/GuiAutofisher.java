package mariculture.fishery.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureBubbles;
import mariculture.core.gui.feature.FeaturePower;
import mariculture.core.gui.feature.FeatureUpgrades;
import mariculture.core.helpers.InventoryHelper;
import mariculture.fishery.blocks.TileAutofisher;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiAutofisher extends GuiMariculture {
	private final TileAutofisher tile;

	public GuiAutofisher(InventoryPlayer player, TileAutofisher tile) {
		super(new ContainerAutofisher(tile, player), "autofisher", 10);
		this.tile = tile;
		features.add(new FeatureUpgrades());
		features.add(new FeaturePower(tile, 12, 18));
		features.add(new FeatureBubbles(tile, 83, 15));
	}
	
	@Override
	public void drawForeground() {
		this.fontRenderer.drawString(InventoryHelper.getName(tile), 46, nameHeight, 4210752);
	}

	@Override
	public void drawBackground(int x, int y) { }
}