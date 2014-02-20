package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Packet110CustomTileUpdate extends PacketMariculture {

	protected int x, y, z;
	
	public Packet110CustomTileUpdate() {}
	
	public Packet110CustomTileUpdate(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void handle(World world, EntityPlayer player) {
		Minecraft.getMinecraft().theWorld.markBlockForUpdate(x, y, z);
	}

	@Override
	public void read(DataInputStream is) throws IOException {
		x = is.readInt();
		y = is.readInt();
		z = is.readInt();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(x);
		os.writeInt(y);
		os.writeInt(z);
	}
}
