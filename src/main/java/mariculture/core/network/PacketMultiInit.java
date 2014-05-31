package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import mariculture.core.helpers.ClientHelper;
import mariculture.core.tile.base.TileMultiBlock;
import mariculture.core.tile.base.TileMultiBlock.MultiPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketMultiInit extends PacketCoords implements IMessageHandler<PacketMultiInit, IMessage> {
	public int mX, mY, mZ, facing;

	public PacketMultiInit() {}
	public PacketMultiInit(int x, int y, int z, int mX, int mY, int mZ, ForgeDirection facing) {
		super(x, y, z);
		this.mX = mX;
		this.mY = mY;
		this.mZ = mZ;
		this.facing = facing.ordinal();
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		super.toBytes(buffer);
		buffer.writeInt(mX);
		buffer.writeInt(mY);
		buffer.writeInt(mZ);
		buffer.writeInt(facing);
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		super.fromBytes(buffer);
		mX = buffer.readInt();
		mY = buffer.readInt();
		mZ = buffer.readInt();
		facing = buffer.readInt();
	}

	@Override
	public IMessage onMessage(PacketMultiInit message, MessageContext ctx) {
		TileEntity te = ClientHelper.getPlayer().worldObj.getTileEntity(message.x, message.y, message.z);
		if (te instanceof TileMultiBlock) {
			if (message.mY < 0) {
				((TileMultiBlock) te).setMaster(null);
			} else {
				((TileMultiBlock) te).setMaster(new MultiPart(message.mX, message.mY, message.mZ));
			}

			((TileMultiBlock) te).setFacing(ForgeDirection.values()[message.facing]);
			ClientHelper.updateRender(message.x, message.y, message.z);
		}

		return null;
	}
}
