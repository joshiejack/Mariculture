package joshie.mariculture.api.fishing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

/** Fishing Traits, attached to fishing rods **/
public interface FishingTrait {
    /** Return the resource location for this trait **/
    ResourceLocation getResource();

    /** This is called after enchantment and potion effects take place,
     *  This can be used so that a trait is able to modify the luck amount when fishing
     *
     * @param player    the player who is fishing
     * @param rand      random instance
     * @param original  the original value
     * @return  the new value
     */
    default float modifyLuck(EntityPlayer player, Random rand, float original) {
        return original;
    }

    /** This is called for each treat, when xp is generated, in order of the traits
     *
     * @param player    the player who is fishing
     * @param loot      the item the player caught
     * @param rand      random instance
     * @param original  the original value
     * @return  the new value
     */
    default int modifyXP(EntityPlayer player, ItemStack loot, Random rand, int original) {
        return original;
    }
}
