package maritech.network;

import io.netty.buffer.ByteBuf;
import mariculture.Mariculture;
import mariculture.core.network.PacketHandler;
import mariculture.lib.helpers.ClientHelper;
import maritech.entity.EntityFLUDDSquirt;
import maritech.handlers.FLUDDEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketFLUDD implements IMessage, IMessageHandler<PacketFLUDD, IMessage> {
    public static final int DAMAGEPREVENT = -44;
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
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(type);
        buffer.writeInt(mode);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        type = buffer.readInt();
        mode = buffer.readInt();
    }

    @Override
    public IMessage onMessage(PacketFLUDD message, MessageContext ctx) {
        Side side = FMLCommonHandler.instance().getEffectiveSide();
        EntityPlayer player = side == Side.CLIENT ? ClientHelper.getPlayer() : ctx.getServerHandler().playerEntity;
        World world = player.worldObj;
        if (message.type == SQUIRT) {
            world.playSoundAtEntity(player, Mariculture.modid + ":fludd", 1.0F, 1.0F);
            EntityFLUDDSquirt rocket = new EntityFLUDDSquirt(world, player, true);
            rocket.posY += 0.35F;
            world.spawnEntityInWorld(rocket);
        }

        if (message.type == DAMAGEPREVENT) {
            ctx.getServerHandler().playerEntity.fallDistance = 0F;
        } else if (message.type != ANIMATE) {
            FLUDDEvents.damageFLUDD(player, message.mode);
        } else if (side == Side.SERVER) {
            PacketHandler.sendAround(new PacketFLUDD(player.getEntityId(), message.mode), world.provider.dimensionId, player.posX, player.posY, player.posZ);
        } else {
            FLUDDEvents.playSmoke(mode, (EntityPlayer) world.getEntityByID(message.type), false);
        }

        return null;
    }
}
