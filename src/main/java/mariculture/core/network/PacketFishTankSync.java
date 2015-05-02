package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import mariculture.fishery.tile.TileFishTank;
import mariculture.lib.helpers.ClientHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketFishTankSync extends PacketCoords implements IMessageHandler<PacketFishTankSync, IMessage> {
    private ForgeDirection dir;
    private ItemStack stack0;
    private ItemStack stack1;
    private ItemStack stack2;

    public PacketFishTankSync() {}

    public PacketFishTankSync(ItemStack stack0, ItemStack stack1, ItemStack stack2, int x, int y, int z, ForgeDirection dir) {
        super(x, y, z);
        this.dir = dir;
        this.stack0 = stack0;
        this.stack1 = stack1;
        this.stack2 = stack2;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        super.toBytes(buffer);
        buffer.writeInt(dir.ordinal());

        if (stack0 == null) buffer.writeBoolean(false);
        else buffer.writeBoolean(true);
        ByteBufUtils.writeItemStack(buffer, stack0);

        if (stack1 == null) buffer.writeBoolean(false);
        else buffer.writeBoolean(true);
        ByteBufUtils.writeItemStack(buffer, stack1);

        if (stack2 == null) buffer.writeBoolean(false);
        else buffer.writeBoolean(true);
        ByteBufUtils.writeItemStack(buffer, stack2);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        super.fromBytes(buffer);
        dir = ForgeDirection.getOrientation(buffer.readInt());
        if (buffer.readBoolean()) stack0 = ByteBufUtils.readItemStack(buffer);
        if (buffer.readBoolean()) stack1 = ByteBufUtils.readItemStack(buffer);
        if (buffer.readBoolean()) stack2 = ByteBufUtils.readItemStack(buffer);
    }

    @Override
    public IMessage onMessage(PacketFishTankSync message, MessageContext ctx) {
        EntityPlayer player = ClientHelper.getPlayer();
        if (player.worldObj.getTileEntity(message.x, message.y, message.z) instanceof TileFishTank) {
            TileFishTank tank = (TileFishTank) player.worldObj.getTileEntity(message.x, message.y, message.z);
            tank.setFacing(message.dir);
            tank.setInventorySlotContents(0, message.stack0);
            tank.setInventorySlotContents(1, message.stack1);
            tank.setInventorySlotContents(2, message.stack2);
            ClientHelper.updateRender(message.x, message.y, message.z);
        }

        return null;
    }
}
