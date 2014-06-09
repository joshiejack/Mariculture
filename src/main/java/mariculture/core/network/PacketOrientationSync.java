package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import mariculture.core.helpers.ClientHelper;
import mariculture.core.util.IFaceable;
import net.minecraft.entity.player.EntityPlayer;
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
        if (player.worldObj.getTileEntity(message.x, message.y, message.z) instanceof IFaceable) {
            ((IFaceable) player.worldObj.getTileEntity(message.x, message.y, message.z)).setFacing(message.dir);
            ClientHelper.updateRender(message.x, message.y, message.z);
        }

        return null;
    }
}
