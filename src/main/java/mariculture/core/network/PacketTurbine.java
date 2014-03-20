package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.factory.blocks.TileTurbineBase;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;

public class PacketTurbine extends PacketCoords {
	boolean isAnimating;
	public PacketTurbine(){}
	public PacketTurbine(int x, int y, int z, boolean isAnimating) {
		super(x, y, z);
		this.isAnimating = isAnimating;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		super.encodeInto(ctx, buffer);
		buffer.writeBoolean(isAnimating);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		super.decodeInto(ctx, buffer);
		isAnimating = buffer.readBoolean();
	}

	@Override
	public void handle(Side side, EntityPlayer player) {
		if(player.worldObj.getTileEntity(x, y, z) instanceof TileTurbineBase) {
			((TileTurbineBase)player.worldObj.getTileEntity(x, y, z)).isAnimating = isAnimating;
		}
	}

}
