package mariculture.core.util;

import mariculture.core.gui.ContainerMariculture;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;

public interface IMachine extends IHasGUI {
	public void getGUINetworkData(int id, int value);
	public void sendGUINetworkData(ContainerMariculture container, ICrafting crafting);
	public ItemStack[] getInventory();
}
