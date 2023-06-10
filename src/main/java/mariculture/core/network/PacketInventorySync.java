package mariculture.core.network;

import mariculture.lib.helpers.ClientHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketInventorySync extends PacketNBT {
    public PacketInventorySync() {}
    public PacketInventorySync(int x, int y, int z, ItemStack[] inventory) {
        super(inventory);
        nbt.setInteger("x", x);
        nbt.setInteger("y", y);
        nbt.setInteger("z", z);
    }

    @Override
    public IMessage onMessage(PacketNBT message, MessageContext ctx) {
        World world = ClientHelper.getPlayer().worldObj;
        int x = message.nbt.getInteger("x");
        int y = message.nbt.getInteger("y");
        int z = message.nbt.getInteger("z");
        int length = message.nbt.getInteger("length");

        TileEntity tile = world.getTileEntity(x, y, z);
        if (!(tile instanceof IInventory)) return null;
        ItemStack[] inventory = new ItemStack[length];
        IInventory tileInventory = (IInventory) tile;
        NBTTagList tagList = message.nbt.getTagList("Inventory", 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");
            if (tag.getBoolean("NULLItemStack")) {
                tileInventory.setInventorySlotContents(slot, null);
            } else if (slot >= 0 && slot < inventory.length) {
                tileInventory.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(tag));
            }
        }

        ClientHelper.updateRender(x, y, z);
        return null;
    }
}
