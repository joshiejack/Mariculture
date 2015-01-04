package mariculture.core.network;

import io.netty.buffer.ByteBuf;
import mariculture.fishery.Fishery;
import mariculture.fishery.tile.TileFishTank;
import mariculture.lib.helpers.ClientHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketFishTankAdd implements IMessage, IMessageHandler<PacketFishTankAdd, IMessage> {
    public NBTTagCompound nbt;

    public PacketFishTankAdd() {}

    public PacketFishTankAdd(int x, int y, int z, NBTTagCompound fish, int amount) {
        nbt = new NBTTagCompound();
        nbt.setInteger("x", x);
        nbt.setInteger("y", y);
        nbt.setInteger("z", z);
        nbt.setInteger("amount", amount);
        if (fish != null) {
            nbt.setTag("fish", fish);
        }
        
        System.out.println("CONST");
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        try {
            new PacketBuffer(buffer).writeNBTTagCompoundToBuffer(nbt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        try {
            nbt = new PacketBuffer(buffer).readNBTTagCompoundFromBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IMessage onMessage(PacketFishTankAdd message, MessageContext ctx) {
        World world = ClientHelper.getPlayer().worldObj;
        int x = message.nbt.getInteger("x");
        int y = message.nbt.getInteger("y");
        int z = message.nbt.getInteger("z");

        TileFishTank tile = (TileFishTank) world.getTileEntity(x, y, z);
        if (tile != null) {
            ItemStack stack = null;
            if (message.nbt.hasKey("fish")) {
                stack = new ItemStack(Fishery.fishy, message.nbt.getInteger("amount"));
                stack.setTagCompound(message.nbt.getCompoundTag("fish"));
            }

            tile.addFish(stack);
            ctx.getServerHandler().playerEntity.inventory.setItemStack(null);
        }

        return null;
    }
}
