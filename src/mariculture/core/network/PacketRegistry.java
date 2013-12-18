package mariculture.core.network;

import java.util.HashMap;

public class PacketRegistry {
	public static HashMap<PacketMariculture, Byte> packetz = new HashMap<PacketMariculture, Byte>();
	public static HashMap<Byte, PacketMariculture> idz = new HashMap<Byte, PacketMariculture>();

	public static void register(byte id, PacketMariculture packet) {
		packetz.put(packet, id);
		idz.put(id, packet);
	}
	
	public static void register(PacketMariculture packet) {
		Byte id = Byte.parseByte(packet.getClass().getSimpleName().toLowerCase().substring(6, 9));
		packetz.put(packet, id);
		idz.put(id, packet);
	}

	public static byte get(PacketMariculture packet) {
		return packetz.get(packet);
	}

	public static PacketMariculture get(byte bite) {		
		return idz.get(bite);
	}
}
