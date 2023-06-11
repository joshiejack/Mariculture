package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import mariculture.core.util.IHasClickableButton;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketClick extends PacketCoords implements IMessageHandler<PacketClick, IMessage> {
    int id;

    public PacketClick() {}

    public PacketClick(int x, int y, int z, int id) {
        super(x, y, z);
        this.id = id;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        super.toBytes(buffer);
        buffer.writeInt(id);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        super.fromBytes(buffer);
        id = buffer.readInt();
    }

    @Override
    public IMessage onMessage(PacketClick message, MessageContext ctx) {
        TileEntity tile = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
        if (tile instanceof IHasClickableButton) {
            ((IHasClickableButton) tile).handleButtonClick(message.id);
        }

        return null;
    }
}
