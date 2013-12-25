package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.util.IRedstoneControlled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Packet114RedstoneControlled extends PacketMariculture {
	
	public int x;
	public int y;
	public int z;
	public int mode;

	public Packet114RedstoneControlled() {}
	public Packet114RedstoneControlled(int x, int y, int z, RedstoneMode mode) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.mode = mode.ordinal();
	}
	
	@Override
	public void handle(World world, EntityPlayer player) {
		IRedstoneControlled tile = (IRedstoneControlled) world.getBlockTileEntity(x, y, z);
		if(tile != null) {
			tile.setMode(RedstoneMode.values()[mode]);
		}
	}

	@Override
	public void read(DataInputStream is) throws IOException {
		x = is.readInt();
		y = is.readInt();
		z = is.readInt();
		mode = is.readInt();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(x);
		os.writeInt(y);
		os.writeInt(z);
		os.writeInt(mode);
	}

}
