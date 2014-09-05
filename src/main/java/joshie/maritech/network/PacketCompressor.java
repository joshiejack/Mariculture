package joshie.maritech.network;

import io.netty.buffer.ByteBuf;
import joshie.lib.helpers.ClientHelper;
import joshie.mariculture.core.network.PacketCoords;
import joshie.maritech.tile.TileAirCompressor;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketCompressor extends PacketCoords implements IMessageHandler<PacketCompressor, IMessage> {
    public int air, power;

    public PacketCompressor() {}

    public PacketCompressor(int x, int y, int z, int air, int power) {
        super(x, y, z);
        this.air = air;
        this.power = power;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        super.toBytes(buffer);
        buffer.writeInt(air);
        buffer.writeInt(power);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        super.fromBytes(buffer);
        air = buffer.readInt();
        power = buffer.readInt();
    }

    @Override
    public IMessage onMessage(PacketCompressor message, MessageContext ctx) {
        TileEntity tile = ClientHelper.getPlayer().worldObj.getTileEntity(message.x, message.y, message.z);
        if (tile != null && tile instanceof TileAirCompressor) {
            ((TileAirCompressor) tile).storedAir = message.air;
            ((TileAirCompressor) tile).energyStorage.setEnergyStored(message.power);
            ClientHelper.updateRender(message.x, message.y, message.z);
        }

        return null;
    }
}
