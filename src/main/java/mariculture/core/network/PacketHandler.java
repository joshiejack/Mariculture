package mariculture.core.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

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
