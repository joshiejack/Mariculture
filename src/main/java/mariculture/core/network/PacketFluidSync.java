package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import mariculture.core.util.ITank;
import mariculture.lib.helpers.ClientHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketFluidSync extends PacketCoords implements IMessageHandler<PacketFluidSync, IMessage> {
    private byte tank;
    private FluidStack fluid;

    public PacketFluidSync() {}
    public PacketFluidSync(int x, int y, int z, FluidStack fluid, byte tank) {
        super(x, y, z);
        this.fluid = fluid;
        this.tank = tank;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        super.toBytes(buffer);
        buffer.writeByte(tank);
        if (fluid == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            NBTTagCompound tag = new NBTTagCompound();
            fluid.writeToNBT(tag);
            ByteBufUtils.writeTag(buffer, tag);
        }
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        super.fromBytes(buffer);
        tank = buffer.readByte();
        if (buffer.readBoolean()) {
            NBTTagCompound tag = ByteBufUtils.readTag(buffer);
            fluid = FluidStack.loadFluidStackFromNBT(tag);
        }
    }

    @Override
    public IMessage onMessage(PacketFluidSync message, MessageContext ctx) {
        TileEntity te = ClientHelper.getPlayer().worldObj.getTileEntity(message.x, message.y, message.z);
        if (te instanceof ITank) {
            ((ITank) te).setFluid(message.fluid, message.tank);
        }

        ClientHelper.updateRender(message.x, message.y, message.z);

        return null;
    }
}
