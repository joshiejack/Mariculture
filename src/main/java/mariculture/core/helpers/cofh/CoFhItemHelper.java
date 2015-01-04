package mariculture.core.helpers.cofh;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Contains various helper functions to assist with {@link Item} and {@link ItemStack} manipulation and interaction.
 * 
 * @author King Lemming
 * 
 */
public final class CoFhItemHelper {
    private CoFhItemHelper() {

    }

    public static ItemStack cloneStack(ItemStack stack, int stackSize) {

        if (stack == null) return null;
        ItemStack retStack = stack.copy();
        retStack.stackSize = stackSize;

        return retStack;
    }

    public static final boolean isPlayerHoldingItem(Item item, EntityPlayer player) {
        Item equipped = player.getCurrentEquippedItem() != null ? player.getCurrentEquippedItem().getItem() : null;
        return item == null ? equipped == null : item.equals(equipped);
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

    public static boolean itemsEqualWithMetadata(ItemStack stackA, ItemStack stackB, boolean checkNBT) {
        return stackA == null ? stackB == null ? true : false : stackB != null && stackA.getItem() == stackB.getItem() && stackA.getItemDamage() == stackB.getItemDamage() && (!checkNBT || doNBTsMatch(stackA.stackTagCompound, stackB.stackTagCompound));
    }

    public static boolean doNBTsMatch(NBTTagCompound nbtA, NBTTagCompound nbtB) {
        return nbtA == null ? nbtB == null ? true : false : nbtB == null ? false : nbtA.equals(nbtB);
    }
}
