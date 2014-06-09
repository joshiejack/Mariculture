package mariculture.core.network;

import mariculture.core.helpers.ClientHelper;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketRenderRefresh extends PacketCoords implements IMessageHandler<PacketCoords, IMessage> {
    public PacketRenderRefresh() {}

    public PacketRenderRefresh(int x, int y, int z) {
        super(x, y, z);
    }

    @Override
    public IMessage onMessage(PacketCoords message, MessageContext ctx) {
        ClientHelper.updateRender(message.x, message.y, message.z);
        return null;
    }
}
