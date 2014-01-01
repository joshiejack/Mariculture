package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.util.IHasClickableButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Packet116GUIClick extends PacketMariculture {
	
	public int x, y, z, id, data;

	public Packet116GUIClick() {}
	public Packet116GUIClick(int x, int y, int z, int id) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = id;
	}
	
	@Override
	public void handle(World world, EntityPlayer player) {
		IHasClickableButton tile = (IHasClickableButton)world.getBlockTileEntity(x, y, z);
		if(tile != null) {
			tile.handleButtonClick(id);
		}
	}

	@Override
	public void read(DataInputStream is) throws IOException {
		x = is.readInt();
		y = is.readInt();
		z = is.readInt();
		id = is.readInt();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(x);
		os.writeInt(y);
		os.writeInt(z);
		os.writeInt(id);
	}

}
