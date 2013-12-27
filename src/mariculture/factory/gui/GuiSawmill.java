package mariculture.factory.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.gui.feature.FeatureArrow;
import mariculture.core.gui.feature.FeatureEject;
import mariculture.core.gui.feature.FeatureNotifications;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone;
import mariculture.core.gui.feature.FeatureUpgrades;
import mariculture.factory.blocks.TileSawmill;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiSawmill extends GuiMariculture {	
	public TileSawmill tile;
	
	public GuiSawmill(InventoryPlayer player, TileSawmill tile) {
		super(new ContainerSawmill(tile, player), "sawmill", 10);
		this.tile = tile;
		features.add(new FeatureUpgrades());
		features.add(new FeatureArrow(tile, 119, 39));
		features.add(new FeatureNotifications(tile, new NotificationType[] { 
				NotificationType.NO_PLAN, NotificationType.MISSING_SIDE 
		}));
		features.add(new FeatureRedstone(tile));
		features.add(new FeatureEject(tile));
	}

	@Override
	public String getName() {
		return "tile.utilBlocks.sawmill.name";
	}
	
	@Override
	public int getX() {
		return 46;
	}
	
	@Override
	public void drawBackground(int x, int y) {
		int y2 = 18 + ((tile.selected - 3) * 20);
		this.drawTexturedModalRect(x + 9, y + y2, 0, 207, 20, 20);
	}
}

/*
public class GuiSawmill extends GuiMariculture {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Mariculture.modid, "textures/gui/sawmill.png");
	private final TileSawmill tile;

	public GuiSawmill(InventoryPlayer player, TileSawmill tile_entity) {
		super(new ContainerSawmill(tile_entity, player));
		tile = tile_entity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRenderer.drawString(InventoryHelper.getName(tile), 76, 4, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture(TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		int progress = tile.getProcessedScaled(24);
		this.drawTexturedModalRect(x + 85, y + 34, 176, 74, progress + 1, 16);
	}
}*/