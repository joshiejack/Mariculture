package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.core.util.ITank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

public class PacketFluidSync extends PacketCoords {
	byte tank;
	FluidStack fluid;
	public PacketFluidSync(){}
	public PacketFluidSync(int x, int y, int z, FluidStack fluid, byte tank) {
		super(x, y, z);
		this.fluid = fluid;
		this.tank = tank;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		super.encodeInto(ctx, buffer);
		if(fluid == null) {
			buffer.writeByte(tank);
			buffer.writeInt(0);
		} else {
			buffer.writeInt(fluid.fluidID);
			buffer.writeInt(fluid.amount);
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		super.decodeInto(ctx, buffer);
		tank = buffer.readByte();
		int id = buffer.readInt();
		if(id == 0) {
			fluid = null;
		} else {
			fluid = new FluidStack(id, buffer.readInt());
		}
	}
	
	@Override
	public void handleClientSide(EntityPlayer player) {
		TileEntity te = player.worldObj.getTileEntity(x, y, z);
		if(te instanceof ITank) {
			((ITank)te).setFluid(fluid, tank);
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		
		
	}
}
