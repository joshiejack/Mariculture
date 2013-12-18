package mariculture.fishery.gui;

import java.util.List;

import mariculture.core.Mariculture;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.Extra;
import mariculture.fishery.blocks.TileAutofisher;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;

public class GuiAutofisher extends GuiContainer {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Mariculture.modid, "textures/gui/autofisher.png");
	private final TileAutofisher tile;

	public GuiAutofisher(InventoryPlayer player, TileAutofisher tile_entity) {
		super(new ContainerAutofisher(tile_entity, player));
		tile = tile_entity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
        this.fontRenderer.drawString(InventoryHelper.getName(tile), 91, 4, 4210752);
    }
	
	public List<String> handleTooltip(int mousex, int mousey, List<String> currenttip) {
		final int x = (width - xSize) / 2;
		final int y = (height - ySize) / 2;

		if (mousex >= x + 161 && mousex <= x + 165 && mousey >= y + 14 && mousey <= y + 73) {
			currenttip.add(tile.getEnergyStored(ForgeDirection.UP) + " / " + tile.getMaxEnergyStored(ForgeDirection.UP) + " RF");
		}

		return currenttip;
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
		
		int power = tile.getPowerScaled(60);
		this.drawTexturedModalRect(x + 161, y + 14 + 60 - power, 208, 60 - power, 5, power);
	}
}