package mariculture.core.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;


public abstract class PacketMariculture {
	public abstract void handle(World world, EntityPlayer player);
	public abstract void read(DataInputStream is) throws IOException;
	public abstract void write(DataOutputStream os) throws IOException;
	
	public Packet build() {
		Byte id = Byte.parseByte(this.getClass().getSimpleName().toLowerCase().substring(6, 9));
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream os = new DataOutputStream(bos);
		try {
			os.writeByte(id);
			write(os);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		return packet;
	}

	public boolean handle(Packet250CustomPayload packet, EntityPlayer player) {
		DataInputStream os = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			os.readByte();
			read(os);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
		
		handle(player.worldObj, player);
		return true;
	}
}
