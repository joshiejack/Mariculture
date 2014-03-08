package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.core.blocks.base.TileMultiBlock;
import mariculture.core.blocks.base.TileMultiBlock.MultiPart;
import mariculture.core.helpers.ClientHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;

public class PacketMultiInit extends PacketCoords {
	public int mX, mY, mZ, facing;
	public PacketMultiInit(){}
	public PacketMultiInit(int x, int y, int z, int mX, int mY, int mZ, ForgeDirection facing) {
		super(x, y, z);
		this.mX = mX;
		this.mY = mY;
		this.mZ = mZ;
		this.facing = facing.ordinal();
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		super.encodeInto(ctx, buffer);
		buffer.writeInt(mX);
		buffer.writeInt(mY);
		buffer.writeInt(mZ);
		buffer.writeInt(facing);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		super.decodeInto(ctx, buffer);
		mX = buffer.readInt();
		mY = buffer.readInt();
		mZ = buffer.readInt();
		facing = buffer.readInt();
	}
	
	@Override
	public void handle(Side side, EntityPlayer player) {
		TileEntity te = player.worldObj.getTileEntity(x, y, z);
		if(te instanceof TileMultiBlock) {
			if(mY < 0) {
				((TileMultiBlock) te).setMaster(null);
			} else { 
				((TileMultiBlock) te).setMaster(new MultiPart(mX, mY, mZ));
			}
			
			((TileMultiBlock) te).setFacing(ForgeDirection.values()[facing]);
			ClientHelper.updateRender(x, y, z);
		}
	}
}
