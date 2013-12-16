package mariculture.core.gui;

import java.util.List;

import org.lwjgl.input.Keyboard;

import mariculture.core.Mariculture;
import mariculture.core.blocks.TileLiquifier;
import mariculture.core.lib.GuiIds;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class GuiItemToolTip {
	@ForgeSubscribe
	public void addToolTip(ItemTooltipEvent event) {
		EntityPlayer player = event.entityPlayer;
		ItemStack stack = event.itemStack;
		List list = event.toolTip;
		Container container = player.openContainer;
		
		if(container instanceof ContainerLiquifier) {
			ContainerLiquifier liquifier = (ContainerLiquifier) container;
			TileLiquifier tile = (TileLiquifier) liquifier.tile;
			Gui gui = (Gui) Mariculture.proxy.getClientGuiElement(GuiIds.LIQUIFIER, player, tile.worldObj, tile.xCoord, tile.yCoord, tile.zCoord);
			if(gui instanceof GuiMariculture) {
				GuiMariculture mari = (GuiMariculture) gui;
				mari.addItemToolTip(stack, list);
			}
		}
	}
}
