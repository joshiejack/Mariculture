package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.util.IEjectable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Packet115EjectSetting extends PacketMariculture {
	
	public int x, y, z;

	public Packet115EjectSetting() {}
	public Packet115EjectSetting(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void handle(World world, EntityPlayer player) {
		IEjectable tile = (IEjectable) world.getBlockTileEntity(x, y, z);
		if(tile != null) {
			tile.setEjectSetting(EjectSetting.toggle(tile.getEjectType(), tile.getEjectSetting()));
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
