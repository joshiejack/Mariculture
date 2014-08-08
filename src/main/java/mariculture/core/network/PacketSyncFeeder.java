package mariculture.core.network;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

import mariculture.api.util.CachedCoords;
import mariculture.core.helpers.ClientHelper;
import mariculture.fishery.tile.TileFeeder;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncFeeder implements IMessage, IMessageHandler<PacketSyncFeeder, IMessage> {
    public NBTTagCompound nbt;

    public PacketSyncFeeder() {}

    public PacketSyncFeeder(int x, int y, int z, ArrayList<CachedCoords> coord) {
        nbt = new NBTTagCompound();
        nbt.setInteger("x", x);
        nbt.setInteger("y", y);
        nbt.setInteger("z", z);
        NBTTagList list = new NBTTagList();
        for (CachedCoords c : coord) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setInteger("x", c.x);
            tag.setInteger("y", c.y);
            tag.setInteger("z", c.z);
            list.appendTag(tag);
        }

        nbt.setTag("Coordinates", list);
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
    public IMessage onMessage(PacketSyncFeeder message, MessageContext ctx) {
        World world = ClientHelper.getPlayer().worldObj;
        int x = message.nbt.getInteger("x");
        int y = message.nbt.getInteger("y");
        int z = message.nbt.getInteger("z");
        ArrayList<CachedCoords> coords = new ArrayList();
        NBTTagList tagList = message.nbt.getTagList("Coordinates", 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            coords.add(new CachedCoords(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z")));
        }

        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof TileFeeder) {
            ((TileFeeder) tile).coords = coords;
        }

        return null;
    }
}
