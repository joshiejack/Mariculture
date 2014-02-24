package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.Mariculture;
import mariculture.core.blocks.base.TileMultiBlock;
import mariculture.core.blocks.base.TileMultiBlock.MultiPart;
import mariculture.core.helpers.ClientHelper;
import mariculture.diving.TileAirCompressor;
import mariculture.factory.blocks.TileSponge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

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
	public void handleClientSide(EntityPlayer player) {
		TileEntity tile = player.worldObj.getTileEntity(x, y, z);
		if(tile != null && tile instanceof TileAirCompressor) {
			((TileAirCompressor)tile).storedAir = air;
			((TileAirCompressor)tile).energyStorage.setEnergyStored(power);
			player.worldObj.markBlockForUpdate(x, y, z);
		}
	}
	
	@Override
	public void handleServerSide(EntityPlayer player) {}
}
