package mariculture.fishery.gui;

import mariculture.core.Mariculture;
import mariculture.core.gui.ContainerSift;
import mariculture.core.helpers.InventoHelper;
import mariculture.core.lib.Extra;
import mariculture.fishery.blocks.TileSift;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiSift extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Mariculture.modid, "textures/gui/sift_storage.png");
	private final TileSift tile;

	public GuiSift(InventoryPlayer player, TileSift tile_entity) {
		super(new ContainerSift(tile_entity, player));
		tile = tile_entity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRenderer.drawString(InventoHelper.getName(tile), 72, 6, 4210752);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.renderEngine.bindTexture(TEXTURE);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}