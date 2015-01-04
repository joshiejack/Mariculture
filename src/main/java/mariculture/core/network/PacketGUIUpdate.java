package mariculture.core.network;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

import mariculture.core.gui.ContainerMariculture;
import mariculture.lib.helpers.ClientHelper;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketGUIUpdate implements IMessage, IMessageHandler<PacketGUIUpdate, IMessage> {
    public int id;
    public int size;
    public int[] data;

    public PacketGUIUpdate() {}

    public PacketGUIUpdate(int id, ArrayList<Integer> list) {
        this.id = id;
        this.size = list.size();
        this.data = new int[size];
        for(int i = 0; i < size; i++) {
            this.data[i] = list.get(i);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        id = buf.readInt();
        size = buf.readInt();
        data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = buf.readInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(id);
        buf.writeInt(size);
        for (int i = 0; i < size; i++) {
            buf.writeInt(data[i]);
        }
    }

    @Override
    public IMessage onMessage(PacketGUIUpdate message, MessageContext ctx) {
        EntityPlayer player = ClientHelper.getPlayer();
        if (player.openContainer.windowId == message.id && player.openContainer.isPlayerNotUsingContainer(player)) {
            if (player.openContainer instanceof ContainerMariculture) {
                ContainerMariculture container = (ContainerMariculture) player.openContainer;
                for (int i = 0; i < message.size; i++) {
                    container.updateProgressBar(i, message.data[i]);
                }
            }
        }

        return null;
    }
}
