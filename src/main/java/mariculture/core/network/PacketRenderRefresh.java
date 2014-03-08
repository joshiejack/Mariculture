package mariculture.core.network;

import mariculture.core.helpers.ClientHelper;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;

public class PacketRenderRefresh extends PacketCoords {
	public PacketRenderRefresh(){}
	public PacketRenderRefresh(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public void handle(Side side, EntityPlayer player) {
		ClientHelper.updateRender(x, y, z);
	}
}
