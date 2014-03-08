package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.magic.MirrorHandler;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;

public class PacketDamageJewelry extends AbstractPacket {
	public int enchant, damage;
	public PacketDamageJewelry() {}
	public PacketDamageJewelry(int enchant, int damage) {
		this.enchant = enchant;
		this.damage = damage;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(enchant);
		buffer.writeInt(damage);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.enchant = buffer.readInt();
		this.damage = buffer.readInt();
	}

	@Override
	public void handle(Side side, EntityPlayer player) {
		MirrorHandler.handleDamage(player, enchant, damage);
	}
}
