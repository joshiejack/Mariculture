package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.factory.blocks.TileTurbineBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Packet119TurbineAnimate extends PacketMariculture {

	public int x, y, z;
	public boolean isAnimating;
	
	public Packet119TurbineAnimate() {}
	public Packet119TurbineAnimate(int x, int y, int z, boolean isAnimating) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.isAnimating = isAnimating;
	}
	
	@Override
	public void handle(World world, EntityPlayer player) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileTurbineBase) {
			((TileTurbineBase)tile).isAnimating = isAnimating;
		}
	}

	@Override
	public void read(DataInputStream is) throws IOException {
		x = is.readInt();
		y = is.readInt();
		z = is.readInt();
		isAnimating = is.readBoolean();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(x);
		os.writeInt(y);
		os.writeInt(z);
		os.writeBoolean(isAnimating);
	} 

}
