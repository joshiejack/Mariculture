package mariculture.core.network;

import net.minecraft.entity.player.EntityPlayer;

public class PacketRenderRefresh extends PacketCoords {
	public PacketRenderRefresh(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		player.worldObj.markBlockRangeForRenderUpdate(x, y, z, x, y, z);
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}
}
