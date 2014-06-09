package mariculture.core.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTHelper {
    public static NBTTagCompound getPlayerData(EntityPlayer player) {
        NBTTagCompound data = player.getEntityData();
        if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
            data.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
        }

        return data.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
    }

    public static NBTTagCompound writeItemStackToNBT(NBTTagCompound tag, ItemStack stack) {
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        tag.setString("Name", Item.itemRegistry.getNameForObject(stack.getItem()));
        tag.setInteger("Count", (byte) stack.stackSize);
        tag.setInteger("Damage", (short) stack.getItemDamage());

        if (stack.stackTagCompound != null) {
            tag.setTag("tag", stack.stackTagCompound);
        }

        return tag;
    }

    public static ItemStack getItemStackFromNBT(NBTTagCompound tag) {
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        Item item = (Item) Item.itemRegistry.getObject(tag.getString("Name"));
        int count = tag.getInteger("Count");
        int damage = tag.getInteger("Damage");
        if (damage < 0) {
            damage = 0;
        }

        ItemStack stack = new ItemStack(item, count, damage);
        if (tag.hasKey("tag", 10)) {
            stack.stackTagCompound = tag.getCompoundTag("tag");
        }

        return stack;
    }
}
