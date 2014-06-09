package mariculture.core.network;

import mariculture.core.helpers.ClientHelper;
import mariculture.core.tile.TileAirPump;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketAirPump extends PacketCoords implements IMessageHandler<PacketCoords, IMessage> {
    public PacketAirPump() {}

    public PacketAirPump(int x, int y, int z) {
        super(x, y, z);
    }

    @Override
    public IMessage onMessage(PacketCoords message, MessageContext ctx) {
        if (ClientHelper.getPlayer().worldObj.getTileEntity(message.x, message.y, message.z) instanceof TileAirPump) {
            ((TileAirPump) ClientHelper.getPlayer().worldObj.getTileEntity(message.x, message.y, message.z)).animate = true;
        }

        return null;
    }
}
