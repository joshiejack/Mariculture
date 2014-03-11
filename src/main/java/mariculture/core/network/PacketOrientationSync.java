package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.core.helpers.ClientHelper;
import mariculture.core.util.IFaceable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;

public class PacketOrientationSync extends PacketCoords {
	ForgeDirection dir;
	public PacketOrientationSync() {}
	public PacketOrientationSync(int x, int y, int z, ForgeDirection dir) {
		super(x, y, z);
		this.dir = dir;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		super.encodeInto(ctx, buffer);
		buffer.writeInt(dir.ordinal());
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		super.decodeInto(ctx, buffer);
		this.dir = ForgeDirection.getOrientation(buffer.readInt());
	}

	@Override
	public void handle(Side side, EntityPlayer player) {
		((IFaceable)player.worldObj.getTileEntity(x, y, z)).setFacing(dir);
		ClientHelper.updateRender(x, y, z);
	}
}
