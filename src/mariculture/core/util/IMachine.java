package mariculture.core.util;

import mariculture.core.gui.ContainerMariculture;
import net.minecraft.entity.player.EntityPlayer;

public interface IMachine {
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player);
	public void getGUINetworkData(int id, int value);
}
