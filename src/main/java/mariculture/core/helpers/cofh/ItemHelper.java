package mariculture.core.helpers.cofh;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Contains various helper functions to assist with {@link Item} and {@link ItemStack} manipulation and interaction.
 * 
 * @author King Lemming
 * 
 */
public final class ItemHelper {
    private ItemHelper() {

    }

    public static ItemStack cloneStack(Item item, int stackSize) {

        if (item == null) return null;
        ItemStack stack = new ItemStack(item, stackSize);

        return stack;
    }

    public static ItemStack cloneStack(ItemStack stack, int stackSize) {

        if (stack == null) return null;
        ItemStack retStack = stack.copy();
        retStack.stackSize = stackSize;

        return retStack;
    }

    public static ItemStack copyTag(ItemStack container, ItemStack other) {

        if (other != null && other.stackTagCompound != null) {
            container.stackTagCompound = (NBTTagCompound) other.stackTagCompound.copy();
        }
        return container;
    }

    public static NBTTagCompound setItemStackTagName(NBTTagCompound tag, String name) {

        if (name == "") return null;
        if (tag == null) {
            tag = new NBTTagCompound();
        }
        if (!tag.hasKey("display")) {
            tag.setTag("display", new NBTTagCompound());
        }
        tag.getCompoundTag("display").setString("Name", name);

        return tag;
    }

    public static NBTTagCompound setItemStackLore(NBTTagCompound tag, String str) {
        if (tag == null) {
            tag = new NBTTagCompound();
        }

        if (!tag.hasKey("display")) {
            tag.setTag("display", new NBTTagCompound());
        }

        NBTTagCompound display = tag.getCompoundTag("display");
        NBTTagList lore = display.getTagList("Lore", 8);
        if (lore.tagCount() < 1) {
            lore.appendTag(new NBTTagString(str));
        }
        display.setTag("Lore", lore);
        return tag;
    }

    /**
     * Determine if a player is holding an ItemStack of a specific Item type.
     */
    public static final boolean isPlayerHoldingItem(Item item, EntityPlayer player) {

        Item equipped = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem().getItem() : null;
        return item == null ? equipped == null : item.equals(equipped);
    }

    /**
     * Determine if a player is holding an ItemStack with a specific Item ID, Metadata, and NBT.
     */
    public static final boolean isPlayerHoldingItemStack(ItemStack stack, EntityPlayer player) {

        ItemStack equipped = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem() : null;
        return stack == null ? equipped == null : equipped != null && stack.isItemEqual(equipped) && ItemStack.areItemStackTagsEqual(stack, equipped);
    }

    public static final boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {

        return ItemStack.areItemStacksEqual(stackA, stackB);
    }

    public static final boolean areStacksEqual(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem() && stack1.getItemDamage() == stack2.getItemDamage();
    }

    public static final boolean areItemStackEqualNoNull(ItemStack stackA, ItemStack stackB) {

        return stackA.isItemEqual(stackB) && (stackA.stackTagCompound != null ? stackB.stackTagCompound != null && stackA.stackTagCompound.equals(stackB.stackTagCompound) : stackB.stackTagCompound != null ? false : true);
    }

    public static boolean areItemStacksEqualNoNBT(ItemStack stackA, ItemStack stackB) {

        if (stackB == null) return false;
        return stackA.getItem() == stackB.getItem() && (stackA.getItemDamage() == OreDictionary.WILDCARD_VALUE ? true : stackB.getItemDamage() == OreDictionary.WILDCARD_VALUE ? true : stackA.getHasSubtypes() == false ? true : stackB.getItemDamage() == stackA.getItemDamage());
    }

    public static boolean areItemStacksEqualWithNBT(ItemStack stackA, ItemStack stackB) {

        if (stackB == null) return false;

        if (!doNBTsMatch(stackA.stackTagCompound, stackB.stackTagCompound)) return false;

        return stackA.getItem() == stackB.getItem() && (stackA.getItemDamage() == OreDictionary.WILDCARD_VALUE ? true : stackB.getItemDamage() == OreDictionary.WILDCARD_VALUE ? true : stackA.getHasSubtypes() == false ? true : stackB.getItemDamage() == stackA.getItemDamage());
    }

    public static String getItemNBTString(ItemStack theItem, String nbtKey, String invalidReturn) {

        return theItem.stackTagCompound != null ? theItem.stackTagCompound.hasKey(nbtKey) ? theItem.stackTagCompound.getString(nbtKey) : invalidReturn : invalidReturn;
    }

    public static Item getItemFromStack(ItemStack theStack) {

        return theStack == null ? null : theStack.getItem();
    }

    public static boolean itemsEqualWithMetadata(ItemStack stackA, ItemStack stackB) {

        return stackA == null ? stackB == null ? true : false : stackB != null && stackA.getItem() == stackB.getItem() && (stackA.getHasSubtypes() == false || stackA.getItemDamage() == stackB.getItemDamage());
    }

    public static boolean itemsEqualWithoutMetadata(ItemStack stackA, ItemStack stackB) {

        return stackA == null ? stackB == null ? true : false : stackB != null && stackA.getItem() == stackB.getItem();
    }

    public static boolean itemsEqualWithMetadata(ItemStack stackA, ItemStack stackB, boolean checkNBT) {

        return stackA == null ? stackB == null ? true : false : stackB != null && stackA.getItem() == stackB.getItem() && stackA.getItemDamage() == stackB.getItemDamage() && (!checkNBT || doNBTsMatch(stackA.stackTagCompound, stackB.stackTagCompound));
    }

    public static boolean itemsEqualWithoutMetadata(ItemStack stackA, ItemStack stackB, boolean checkNBT) {

        return stackA == null ? stackB == null ? true : false : stackB != null && stackA.getItem() == stackB.getItem() && (!checkNBT || doNBTsMatch(stackA.stackTagCompound, stackB.stackTagCompound));
    }

    public static boolean doOreIDsMatch(ItemStack stackA, ItemStack stackB) {

        return OreDictionary.getOreID(stackA) >= 0 && OreDictionary.getOreID(stackA) == OreDictionary.getOreID(stackB);
    }

    public static boolean doNBTsMatch(NBTTagCompound nbtA, NBTTagCompound nbtB) {

        return nbtA == null ? nbtB == null ? true : false : nbtB == null ? false : nbtA.equals(nbtB);
    }
}
