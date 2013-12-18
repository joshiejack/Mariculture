package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.blocks.TileAirPump;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Packet102AirPump extends PacketMariculture {
	public int x, y, z;
	
	public Packet102AirPump() {}
	
	public Packet102AirPump(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void handle(World world, EntityPlayer player) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileAirPump) {
			((TileAirPump) tile).animate = true;
		}
	}
	
	@Override
	public void read(DataInputStream os) throws IOException {
		x = os.readInt();
		y = os.readInt();
		z = os.readInt();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(x);
		os.writeInt(y);
		os.writeInt(z);
	}
}
