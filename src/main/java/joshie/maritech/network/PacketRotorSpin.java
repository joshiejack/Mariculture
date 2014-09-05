package joshie.maritech.network;

import io.netty.buffer.ByteBuf;
import joshie.lib.helpers.ClientHelper;
import joshie.mariculture.core.network.PacketCoords;
import joshie.maritech.tile.TileRotor;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketRotorSpin extends PacketCoords implements IMessageHandler<PacketRotorSpin, IMessage> {
    public ForgeDirection dir;

    public PacketRotorSpin() {}

    public PacketRotorSpin(int x, int y, int z, ForgeDirection dir) {
        super(x, y, z);
        this.dir = dir;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        super.toBytes(buffer);
        buffer.writeInt(dir.ordinal());
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        super.fromBytes(buffer);
        dir = ForgeDirection.getOrientation(buffer.readInt());
    }

    @Override
    public IMessage onMessage(PacketRotorSpin message, MessageContext ctx) {
        TileEntity tile = ClientHelper.getPlayer().worldObj.getTileEntity(message.x, message.y, message.z);
        if (tile != null && tile instanceof TileRotor) {
            ((TileRotor) tile).setAnimating(message.dir);
        }

        return null;
    }
}
