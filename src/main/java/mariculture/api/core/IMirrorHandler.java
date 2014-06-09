package mariculture.api.core;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IMirrorHandler {
    /** Returns whether the player has any enchanted items at all in their mirror inventory */
    public boolean containsEnchantedItems(EntityPlayer player);

    /** Will damage any items in the mirror inventory with the specified enchantment ID */
    public void damageItemsWithEnchantment(EntityPlayer player, int enchant, int amount);

    /** Will cause the player to drop all items from inside their mirror inventory at the specified coords*/
    public void dropItems(EntityPlayer player, World world, double posX, double posY, double posZ);

    /** Will return the total stacked strength of an enchantment in all possible locations for mirror enchantments */
    public int getEnchantmentStrength(EntityPlayer player, int enchant);

    /** returns whether or not the player has the specified enchantment or not */
    public boolean hasEnchantment(EntityPlayer player, int enchant);

    /**Will return array of all three items in the players mirror inventory */
    public ItemStack[] getMirrorContents(EntityPlayer player);

    /** Saves the mirror contents that you specify to the player, ensure the array is 3 long **/
    public void setMirrorContents(EntityPlayer player, ItemStack[] inventory);

    /** Will return true if the itemstack is a piece of jewelry **/
    public boolean isJewelry(ItemStack stack);
}
