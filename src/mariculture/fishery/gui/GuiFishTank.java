package mariculture.fishery.gui;

import mariculture.core.gui.GuiMariculture;
import mariculture.fishery.blocks.TileFishTank;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiFishTank extends GuiMariculture {
	public GuiFishTank(InventoryPlayer player, TileFishTank tile) {
		super(new ContainerFishTank(tile, player), "fishtank", 52);
	}
}
