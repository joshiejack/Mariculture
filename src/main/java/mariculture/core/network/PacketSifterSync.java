package mariculture.core.network;

import mariculture.core.blocks.BlockAirItem;
import mariculture.core.helpers.ClientHelper;
import mariculture.fishery.tile.TileSifter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSifterSync extends PacketNBT {
    public PacketSifterSync() {}

    public PacketSifterSync(int x, int y, int z, ItemStack stack) {
        nbt = new NBTTagCompound();
        nbt.setInteger("x", x);
        nbt.setInteger("y", y);
        nbt.setInteger("z", z);
        stack.writeToNBT(nbt);
    }

    @Override
    public IMessage onMessage(PacketNBT message, MessageContext ctx) {
        World world = ClientHelper.getPlayer().worldObj;
        int x = message.nbt.getInteger("x");
        int y = message.nbt.getInteger("y");
        int z = message.nbt.getInteger("z");

        ItemStack stack = ItemStack.loadItemStackFromNBT(message.nbt);
        if (stack.getItem() instanceof BlockAirItem) {
            stack = null;
        }

        ((TileSifter) world.getTileEntity(x, y, z)).display = stack;
        ClientHelper.updateRender(x, y, z);
        return null;
    }
}
