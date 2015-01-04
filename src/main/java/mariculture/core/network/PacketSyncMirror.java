package mariculture.core.network;

import mariculture.api.core.MaricultureHandlers;
import mariculture.lib.helpers.ClientHelper;
import mariculture.magic.MirrorHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncMirror extends PacketNBT {
    public PacketSyncMirror() {}

    public PacketSyncMirror(ItemStack[] inventory) {
        super(inventory);
    }

    @Override
    public IMessage onMessage(PacketNBT message, MessageContext ctx) {
        EntityPlayer player = ClientHelper.getPlayer();
        World world = player.worldObj;
        int length = message.nbt.getInteger("length");
        ItemStack[] inventory = MaricultureHandlers.mirror.getMirrorContents(player);
        NBTTagList tagList = message.nbt.getTagList("Inventory", 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            byte slot = tag.getByte("Slot");
            if (tag.getBoolean("NULLItemStack") == true) {
                inventory[slot] = null;
            } else if (slot >= 0 && slot < inventory.length) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }

        MirrorHelper.saveClient(player, inventory);
        return null;
    }

}
