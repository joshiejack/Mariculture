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
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

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
