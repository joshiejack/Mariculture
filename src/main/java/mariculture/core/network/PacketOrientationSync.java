package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import mariculture.core.util.IFaceable;
import mariculture.lib.helpers.ClientHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketOrientationSync extends PacketCoords implements IMessageHandler<PacketOrientationSync, IMessage> {
    ForgeDirection dir;

    public PacketOrientationSync() {}

    public PacketOrientationSync(int x, int y, int z, ForgeDirection dir) {
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
    public IMessage onMessage(PacketOrientationSync message, MessageContext ctx) {
        EntityPlayer player = ClientHelper.getPlayer();
        TileEntity tile = player.worldObj.getTileEntity(message.x, message.y, message.z);
        if (tile instanceof IFaceable) {
            ((IFaceable) tile).setFacing(message.dir);
            ClientHelper.updateRender(message.x, message.y, message.z);
        }

        return null;
    }
}
