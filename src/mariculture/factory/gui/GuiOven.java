package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureArrow;
import mariculture.core.gui.feature.FeatureEject;
import mariculture.core.gui.feature.FeatureNotifications;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone;
import mariculture.core.gui.feature.FeatureTank;
import mariculture.core.gui.feature.FeatureTank.TankSize;
import mariculture.core.gui.feature.FeatureUpgrades;
import mariculture.factory.blocks.TileOven;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiOven extends GuiMariculture {	
	public TileOven tile;
	private int tick;
	boolean eh = true;
	
	public GuiOven(InventoryPlayer player, TileOven tile) {
		super(new ContainerOven(tile, player), "gasoven", 10);
		this.tile = tile;
		features.add(new FeatureUpgrades());
		features.add(new FeatureArrow(tile, 122, 42));
		features.add(new FeatureNotifications(tile, new NotificationType[] { 
				NotificationType.NO_PLAN, NotificationType.MISSING_SIDE 
		}));
		features.add(new FeatureRedstone(tile));
		features.add(new FeatureEject(tile));
		features.add(new FeatureTank(tile, 13, 19, TankSize.SINGLE));
	}

	@Override
	public int getX() {
		return 54;
	}
	
	@Override
	public void drawBackground(int x, int y) {
		if(tile.isBurning()) {	
			if(eh) {
				tick++;
				if(tick > 200) {
					eh = false;
				}
			} else {
				tick--;
				if(tick < 1) {
					eh = true;
				}
			}
			
			int draw = tick/30;
			
			drawTexturedModalRect(x + 70, y + 70, 242, 68, 14, 20);
			drawTexturedModalRect(x + 85, y + 70, 242, 68, 14, 20);
			drawTexturedModalRect(x + 100, y + 70, 242, 68, 14, 20);
			
			drawTexturedModalRect(x + 70, y + 63 + draw, 242, 60 + draw, 14, 20);
			drawTexturedModalRect(x + 85, y + 63 + draw, 242, 60 + draw, 14, 20);
			drawTexturedModalRect(x + 100, y + 63 + draw, 242, 60 + draw, 14, 20);
		}
	}
}