package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import mariculture.Mariculture;
import mariculture.core.util.Rand;
import mariculture.factory.EntityFLUDDSquirt;
import mariculture.factory.FactoryEvents;
import mariculture.factory.items.ItemArmorFLUDD;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;

public class PacketFLUDD extends AbstractPacket {
	public static final int SQUIRT = -33;
	public static final int DAMAGE = -22;
	public static final int ANIMATE = -11;
	
	int type, mode;
	public PacketFLUDD() {}	
	public PacketFLUDD(int type, int mode) {
		this.type = type;
		this.mode = mode;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(type);
		buffer.writeInt(mode);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.type = buffer.readInt();
		this.mode = buffer.readInt();
	}

	@Override
	public void handle(Side side, EntityPlayer player) {
		World world = player.worldObj;
		if(type == SQUIRT) {
			world.playSoundAtEntity(player, Mariculture.modid + ":fludd", 1.0F, 1.0F);
			EntityFLUDDSquirt rocket = new EntityFLUDDSquirt(world, player, true);
			rocket.posY += 0.35F;
			world.spawnEntityInWorld(rocket);
		}
		
		if(type != ANIMATE) FactoryEvents.damageFLUDD(player, mode);
		else {
			if(side == Side.SERVER) Mariculture.packets.sendToAllAround(new PacketFLUDD(player.getEntityId(), mode), Packets.getTarget(player));
			else FactoryEvents.playSmoke(mode, (EntityPlayer)world.getEntityByID(type), false);
		}
	}

}
