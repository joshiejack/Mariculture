package mariculture.core.network;

import ibxm.Player;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.entity.player.EntityPlayer;

public class PacketHandler implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		if (packet.channel.equals("Mariculture") && packet.data.length > 1) {			
			try {
				PacketRegistry.get(data.readByte()).handle(packet, (EntityPlayer)player);
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}
	}
}
