package mariculture.core.network.old;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.util.ITank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class Packet118FluidUpdate extends PacketMariculture {

	private int id, vol, x, y, z;
	private byte num;
	
	public Packet118FluidUpdate() {}
	public Packet118FluidUpdate(int x, int y, int z, FluidStack fluid) {
		this.x = x;
		this.y = y;
		this.z = z;
		
		if(fluid == null) {
			id = 0;
			vol = 0;
		} else {
			id = fluid.fluidID;
			vol = fluid.amount;
		}
		
		num = 0;
	}
	
	public Packet118FluidUpdate(int x, int y, int z, FluidStack fluid, byte tank) {
		this.x = x;
		this.y = y;
		this.z = z;
		if(fluid == null) {
			id = 0;
			vol = 0;
		} else {
			id = fluid.fluidID;
			vol = fluid.amount;
		}
		
		num = tank;
	}

	@Override
	public void handle(World world, EntityPlayer player) {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof ITank) {
			FluidStack fluid = (vol == 0 || id == 0)? null: new FluidStack(id, vol);
			((ITank)te).setFluid(fluid, num);
		}
		
		world.markBlockForUpdate(x, y, z);
	}

	@Override
	public void read(DataInputStream is) throws IOException {
		x = is.readInt();
		y = is.readInt();
		z = is.readInt();
		id = is.readInt();
		vol = is.readInt();
		num = is.readByte();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(x);
		os.writeInt(y);
		os.writeInt(z);
		os.writeInt(id);
		os.writeInt(vol);
		os.writeByte(num);
	}

}
