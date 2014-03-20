package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public abstract class PacketNBT extends AbstractPacket {
	public NBTTagCompound nbt;
	public PacketNBT(){}
	public PacketNBT(NBTTagCompound nbt) {
		this.nbt = nbt;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		try {
			PacketBuffer packetbuffer = new PacketBuffer(buffer);
			packetbuffer.writeNBTTagCompoundToBuffer(nbt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		try {
			PacketBuffer packetbuffer = new PacketBuffer(buffer);
			nbt = packetbuffer.readNBTTagCompoundFromBuffer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
