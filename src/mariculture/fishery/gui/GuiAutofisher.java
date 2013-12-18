package mariculture.fishery.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.core.helpers.InventoryHelper;
import mariculture.fishery.blocks.TileAutofisher;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraftforge.common.ForgeDirection;

public class GuiAutofisher extends GuiMariculture {
	private final TileAutofisher tile;

	public GuiAutofisher(InventoryPlayer player, TileAutofisher tile_entity) {
		super(new ContainerAutofisher(tile_entity, player), "autofisher");
		tile = tile_entity;
	}
	
	@Override
	public void addToolTip() {		
		if (mouseX >= 161 && mouseX <= 165 && mouseY >= 14 && mouseY <= 73) {
			tooltip.add(tile.getEnergyStored(ForgeDirection.UNKNOWN) + " / " + tile.getMaxEnergyStored(ForgeDirection.UNKNOWN) + " RF");
		}
	}

	@Override
	public void drawForeground() {
		this.fontRenderer.drawString(InventoryHelper.getName(tile), 91, 4, 4210752);
	}

	@Override
	public void drawBackground(int x, int y) {
		int progress = tile.getCatchTimeRemainingScaled(60);
		this.drawTexturedModalRect(x + 73, y + 9 + 60 - progress, 176, 60 - progress, 28, progress + 2);
		
		int power = tile.getPowerScaled(60);
		this.drawTexturedModalRect(x + 161, y + 14 + 60 - power, 208, 60 - power, 5, power);
	}
}