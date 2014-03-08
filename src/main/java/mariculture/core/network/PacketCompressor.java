package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.diving.TileAirCompressor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.relauncher.Side;

public class PacketCompressor extends PacketCoords {
	public int air, power;
	public PacketCompressor(){}
	public PacketCompressor(int x, int y, int z, int air, int power) {
		super(x, y, z);
		this.air = air;
		this.power = power;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		super.encodeInto(ctx, buffer);
		buffer.writeInt(air);
		buffer.writeInt(power);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		super.decodeInto(ctx, buffer);
		air = buffer.readInt();
		power = buffer.readInt();
	}
	
	@Override
	public void handle(Side side, EntityPlayer player) {
		TileEntity tile = player.worldObj.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileAirCompressor) {
			((TileAirCompressor)tile).storedAir = air;
			((TileAirCompressor)tile).energyStorage.setEnergyStored(power);
			player.worldObj.markBlockForUpdate(x, y, z);
		}
	}
}
