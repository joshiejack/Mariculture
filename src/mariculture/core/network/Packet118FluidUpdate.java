package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.util.ITank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class Packet118FluidUpdate extends Packet119Coordinates {

	private int id, vol;
	
	public Packet118FluidUpdate() {}
	public Packet118FluidUpdate(int x, int y, int z, FluidStack fluid) {
		super(x, y, z);
		
		if(fluid == null) {
			id = 0;
			vol = 0;
		} else {
			id = fluid.fluidID;
			vol = fluid.amount;
		}
	}

	@Override
	public void handle(World world, EntityPlayer player) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te instanceof ITank) {
			FluidStack fluid = (vol == 0 || id == 0)? null: new FluidStack(id, vol);
			((ITank)te).setFluid(fluid);
		}
		
		world.markBlockForRenderUpdate(x, y, z);
	}

	@Override
	public void read(DataInputStream is) throws IOException {
		super.read(is);
		id = is.readInt();
		vol = is.readInt();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		super.write(os);
		os.writeInt(id);
		os.writeInt(vol);
	}

}
