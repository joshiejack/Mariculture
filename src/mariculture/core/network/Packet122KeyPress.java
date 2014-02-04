package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.helpers.KeyHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Packet122KeyPress extends PacketMariculture {
	public static enum KeyType {
		ACTIVATE
	}
	
	public int data;
	public KeyType type;
	
	public Packet122KeyPress(){}
	public Packet122KeyPress(KeyType type, int data) {
		this.type = type;
		this.data = data;
	}

	@Override
	public void handle(World world, EntityPlayer player) {
		if(type.equals(KeyType.ACTIVATE)) {
			KeyHelper.ACTIVATE_PRESSED = data > 0;
		}
	}

	@Override
	public void read(DataInputStream is) throws IOException {
		data = is.readInt();
		type = KeyType.values()[is.readInt()];
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(data);
		os.writeInt(type.ordinal());
	}

}
