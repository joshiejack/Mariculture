package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.util.IRedstoneControlled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Packet114RedstoneControlled extends PacketMariculture {
	
	public int x, y, z, mode;

	public Packet114RedstoneControlled() {}
	public Packet114RedstoneControlled(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void handle(World world, EntityPlayer player) {
		IRedstoneControlled tile = (IRedstoneControlled) world.getBlockTileEntity(x, y, z);
		if(tile != null) {
			tile.setRSMode(RedstoneMode.toggle(tile.getRSMode()));
		}
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
