package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.core.util.IHasClickableButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class PacketClick extends PacketCoords {	
	int id;
	
	public PacketClick() { }
	public PacketClick(int x, int y, int z, int id) {
		super(x, y, z);
		this.id = id;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		super.encodeInto(ctx, buffer);
		buffer.writeInt(id);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		super.decodeInto(ctx, buffer);
		id = buffer.readInt();
	}
	

	@Override
	public void handleClientSide(EntityPlayer player) {}

	@Override
	public void handleServerSide(EntityPlayer player) {
		TileEntity tile = player.worldObj.getTileEntity(x, y, z);
		if(tile != null && tile instanceof IHasClickableButton) {
			((IHasClickableButton)tile).handleButtonClick(id);
		}
	}

}
