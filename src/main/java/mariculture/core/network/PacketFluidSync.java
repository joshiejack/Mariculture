package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.core.helpers.ClientHelper;
import mariculture.core.util.ITank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;

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
		buffer.writeByte(tank);
		if(fluid == null) {
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
	public void handle(Side side, EntityPlayer player) {
		TileEntity te = player.worldObj.getTileEntity(x, y, z);
		if(te instanceof ITank) {
			((ITank)te).setFluid(fluid, tank);
		}
		
		ClientHelper.updateRender(x, y, z);
	}
}
