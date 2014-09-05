package joshie.mariculture.core.network;

import io.netty.buffer.ByteBuf;
import joshie.lib.helpers.ClientHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketParticle implements IMessage, IMessageHandler<PacketParticle, IMessage> {
    public Particle part;
    public byte loop;
    public double x, y, z;

    public PacketParticle() {}

    public PacketParticle(Particle part, int loop, double x, double y, double z) {
        this.part = part;
        this.loop = (byte) loop;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        part = Particle.values()[buf.readByte()];
        loop = buf.readByte();
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(part.ordinal());
        buf.writeByte(loop);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    @Override
    public IMessage onMessage(PacketParticle message, MessageContext ctx) {
        for (int i = 0; i < message.loop; i++) {
            ClientHelper.getPlayer().worldObj.spawnParticle(message.part.getParticle(), message.x + 0.5D, message.y + 0.85D, message.z + 0.5D, 0, 0, 0);
        }

        return null;
    }

    public static enum Particle {
        SPLASH("splash"), EXPLODE_SML("explode"), EXPLODE_LRG("hugeexplosion");

        public final String string;

        private Particle(String string) {
            this.string = string;
        }

        public String getParticle() {
            return string;
        }
    }
}
