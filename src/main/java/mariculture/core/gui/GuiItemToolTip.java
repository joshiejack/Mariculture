package mariculture.core.gui;

import java.util.List;

import mariculture.Mariculture;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class GuiItemToolTip {
	@SubscribeEvent
	public void addToolTip(ItemTooltipEvent event) {
		EntityPlayer player = event.entityPlayer;
		ItemStack stack = event.itemStack;
		List list = event.toolTip;
		Container container = player.openContainer;
		
		if(container instanceof ContainerMachine) {
			TileEntity tile = (TileEntity) ((ContainerMachine) container).tile;
			Gui gui = (Gui) Mariculture.proxy.getClientGuiElement(-1, player, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
			if(gui instanceof GuiMariculture)
				((GuiMariculture) gui).addItemToolTip(stack, list);
		} 
	}
}
