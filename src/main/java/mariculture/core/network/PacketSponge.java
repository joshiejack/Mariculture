package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.Mariculture;
import mariculture.core.helpers.ClientHelper;
import mariculture.factory.blocks.TileSponge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;

public class PacketSponge extends PacketCoords {
	int stored, max;
	boolean isClient;
	
	public PacketSponge(){}
	public PacketSponge(int x, int y, int z, boolean isClient) {
		super(x, y, z);
		this.isClient = isClient;
	}
	
	public PacketSponge(int stored, int max, boolean isClient) {
		this.stored = stored;
		this.max = max;
		this.isClient = isClient;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeBoolean(isClient);
		
		if(isClient) {
			super.encodeInto(ctx, buffer);
		} else {
			buffer.writeInt(stored);
			buffer.writeInt(max);
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		isClient = buffer.readBoolean();
		
		if(isClient) {
			super.decodeInto(ctx, buffer);
		} else {
			stored = buffer.readInt();
			max = buffer.readInt();
		}
	}
	
	@Override
	public void handle(Side side, EntityPlayer player) {
		if(side == Side.CLIENT) ClientHelper.addToChat(stored + " / " + max + " RF");
		if(side == Side.SERVER) {
			TileEntity tile = player.worldObj.getTileEntity(x, y, z);
			if(tile != null && tile instanceof TileSponge) {
				TileSponge sponge = (TileSponge) tile;
				stored = sponge.getEnergyStored(ForgeDirection.UNKNOWN);
				max = sponge.getMaxEnergyStored(ForgeDirection.UNKNOWN);
				Mariculture.packets.sendTo(new PacketSponge(stored, max, false), (EntityPlayerMP) player);
			}
		}
	}
}
