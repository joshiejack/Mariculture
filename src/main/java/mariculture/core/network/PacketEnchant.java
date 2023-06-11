package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import mariculture.magic.gui.ContainerMirror;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketEnchant implements IMessage, IMessageHandler<PacketEnchant, IMessage> {
    public int windowID;
    public int level;

    public PacketEnchant() {}

    public PacketEnchant(int windowID, int level) {
        this.windowID = windowID;
        this.level = level;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(windowID);
        buffer.writeInt(level);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        windowID = buffer.readInt();
        level = buffer.readInt();
    }

    @Override
    public IMessage onMessage(PacketEnchant message, MessageContext ctx) {
        EntityPlayer player = ctx.getServerHandler().playerEntity;
        if (player.openContainer != null && player.openContainer.windowId == message.windowID && player.openContainer.isPlayerNotUsingContainer(player)) {
            ContainerMirror mirror = (ContainerMirror) player.openContainer;
            mirror.windowId = message.windowID;
            mirror.enchantItem(player, message.level);
            mirror.detectAndSendChanges();
        }

        return null;
    }
}
