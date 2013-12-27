package mariculture.core.gui;

import mariculture.core.blocks.TileBookshelf;
import mariculture.core.helpers.InventoryHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GuiBookshelf extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/dispenser.png");
	private final TileBookshelf tile;

	public GuiBookshelf(InventoryPlayer player, TileBookshelf tile) {
		super(new ContainerBookshelf(tile, player));
		this.tile = tile;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRenderer.drawString(InventoryHelper.getName(tile), 42, 6, 4210752);
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