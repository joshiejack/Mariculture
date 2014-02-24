package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.core.gui.ContainerMariculture;
import net.minecraft.entity.player.EntityPlayer;

public class PacketGUI extends AbstractPacket {
	public int windowID;
	public int dataID;
	public int dataVal;
	
	public PacketGUI() {}
	public PacketGUI(int windowID, int id, int val) {
		this.windowID = windowID;
		this.dataID = id;
		this.dataVal = val;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(windowID);
		buffer.writeInt(dataID);
		buffer.writeInt(dataVal);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		windowID = buffer.readInt();
		dataID = buffer.readInt();
		dataVal = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		if (player.openContainer.windowId == windowID && player.openContainer.isPlayerNotUsingContainer(player)) {
			if (player.openContainer instanceof ContainerMariculture) {
				ContainerMariculture container = (ContainerMariculture) player.openContainer;
				container.updateProgressBar(dataID, dataVal);
			}
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

}
