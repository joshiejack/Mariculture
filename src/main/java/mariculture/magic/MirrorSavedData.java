package mariculture.magic;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;

public class MirrorSavedData extends WorldSavedData {
    public static final String name = "Mariculture-Mirror";
    ItemStack[] inventory;

    public MirrorSavedData() {
        super(name);
    }

    public MirrorSavedData(String str) {
        super(str);
    }

    public ItemStack[] getJewelry() {
        if (inventory == null) {
            inventory = new ItemStack[4];
        }
        markDirty();
        return inventory;
    }

    public ItemStack[] setJewelry(ItemStack[] inventory) {
        if (this.inventory == null) {
            this.inventory = new ItemStack[4];
        }
        this.inventory = inventory;
        markDirty();
        return inventory;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if (inventory == null) {
            inventory = new ItemStack[4];
        }
        NBTTagList tagList = nbt.getTagList("Inventory", 10);
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);

            byte slot = tag.getByte("Slot");

            if (slot >= 0 && slot < inventory.length) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }

        markDirty();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < inventory.length; i++) {
            ItemStack stack = inventory[i];
            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();

                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }

        nbt.setTag("Inventory", itemList);
    }
}