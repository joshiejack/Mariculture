package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import mariculture.magic.MirrorHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketDamageJewelry implements IMessage, IMessageHandler<PacketDamageJewelry, IMessage> {
    public int enchant, damage;

    public PacketDamageJewelry() {}

    public PacketDamageJewelry(int enchant, int damage) {
        this.enchant = enchant;
        this.damage = damage;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(enchant);
        buffer.writeInt(damage);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        enchant = buffer.readInt();
        damage = buffer.readInt();
    }

    @Override
    public IMessage onMessage(PacketDamageJewelry message, MessageContext ctx) {
        MirrorHandler.handleDamage(ctx.getServerHandler().playerEntity, message.enchant, message.damage);
        return null;
    }
}
