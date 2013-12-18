package mariculture.fishery.gui;

import mariculture.core.Mariculture;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.Extra;
import mariculture.fishery.blocks.TileIncubator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class GuiIncubator extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Mariculture.modid, "textures/gui/incubator.png");
	private final TileIncubator tile;

	public GuiIncubator(InventoryPlayer player, TileIncubator tile_entity) {
		super(new ContainerIncubator(tile_entity, player));
		tile = tile_entity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRenderer.drawString(StatCollector.translateToLocal("tile.utilBlocks.incubator.name"), 18, 4, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture(TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		int progress = tile.getCatchTimeRemainingScaled(60);
		this.drawTexturedModalRect(x + 73, y + 9 + 60 - progress, 176, 60 - progress, 28, progress + 2);
	}
}