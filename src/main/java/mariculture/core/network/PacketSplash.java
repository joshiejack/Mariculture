package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import mariculture.core.helpers.ClientHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSplash implements IMessage, IMessageHandler<PacketSplash, IMessage> {
    public double x, y, z;

    public PacketSplash() {}

    public PacketSplash(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    @Override
    public IMessage onMessage(PacketSplash message, MessageContext ctx) {
        for (int i = 0; i < 8; i++) {
            ClientHelper.getPlayer().worldObj.spawnParticle("splash", message.x + 0.5D, message.y + 0.85D, message.z + 0.5D, 0, 0, 0);
        }

        return null;
    }

}
