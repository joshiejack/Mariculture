package mariculture.core.network.old;

import java.util.HashMap;

public class PacketRegistry {
	public static HashMap<Byte, PacketMariculture> idz = new HashMap<Byte, PacketMariculture>();

	public static void register(PacketMariculture packet) {
		Byte id = Byte.parseByte(packet.getClass().getSimpleName().toLowerCase().substring(6, 9));
		idz.put(id, packet);
	}
	
	public static PacketMariculture get(byte bite) {		
		return idz.get(bite);
	}
}
