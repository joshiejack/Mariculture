package mariculture.core.network;

import mariculture.lib.helpers.ClientHelper;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketCrack implements IMessage, IMessageHandler<PacketCrack, IMessage> {
    public int id, meta;
    public double x, y, z;

    public PacketCrack() {}

    public PacketCrack(int id, int meta, double x, double y, double z) {
        this.id = id;
        this.meta = meta;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        id = buf.readInt();
        meta = buf.readInt();
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(id);
        buf.writeInt(meta);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    @Override
    public IMessage onMessage(PacketCrack message, MessageContext ctx) {
        for (int i = 0; i < 8; i++) {
            ClientHelper.getPlayer().worldObj.spawnParticle("blockcrack_" + message.id + "_" + message.meta, message.x + 0.5D, message.y + 0.85D, message.z + 0.5D, 0, 0, 0);
        }

        return null;
    }

}
