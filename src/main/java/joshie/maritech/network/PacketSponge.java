package joshie.maritech.network;

import io.netty.buffer.ByteBuf;
import joshie.lib.helpers.ClientHelper;
import joshie.mariculture.core.network.PacketCoords;
import joshie.mariculture.core.network.PacketHandler;
import joshie.maritech.tile.TileSponge;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSponge extends PacketCoords implements IMessageHandler<PacketSponge, IMessage> {
    private int stored, max;
    private boolean isClient;

    public PacketSponge() {}

    public PacketSponge(int x, int y, int z, boolean isClient) {
        super(x, y, z);
        this.isClient = isClient;
    }

    private PacketSponge(int stored, int max, boolean isClient) {
        this.stored = stored;
        this.max = max;
        this.isClient = isClient;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeBoolean(isClient);
        if (isClient) {
            super.toBytes(buffer);
        } else {
            buffer.writeInt(stored);
            buffer.writeInt(max);
        }
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        isClient = buffer.readBoolean();
        if (isClient) {
            super.fromBytes(buffer);
        } else {
            stored = buffer.readInt();
            max = buffer.readInt();
        }
    }

    @Override
    public IMessage onMessage(PacketSponge message, MessageContext ctx) {
        if (!message.isClient) {
            ClientHelper.addToChat(message.stored + " / " + message.max + " RF");
        } else {
            TileEntity tile = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
            if (tile != null && tile instanceof TileSponge) {
                TileSponge sponge = (TileSponge) tile;
                stored = sponge.getEnergyStored(ForgeDirection.UNKNOWN);
                max = sponge.getMaxEnergyStored(ForgeDirection.UNKNOWN);
                PacketHandler.sendToClient(new PacketSponge(stored, max, false), ctx.getServerHandler().playerEntity);
            }
        }
        return null;
    }
}
