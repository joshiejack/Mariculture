package joshie.mariculture.api.fishing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Random;

/** Fishing Traits, attached to fishing rods **/
public abstract class FishingTrait {
    public static final HashMap<ResourceLocation, FishingTrait> TRAIT_REGISTRY = new HashMap<>();
    private final ResourceLocation resource;

    public FishingTrait(ResourceLocation resource) {
        this.resource = resource;
        TRAIT_REGISTRY.put(resource, this);
    }

    /** Return the resource location for this trait **/
    public ResourceLocation getResource() {
        return resource;
    }

    /** This is called after enchantment and potion effects take place,
     *  This can be used so that a trait is able to modify the luck amount when fishing
     *
     * @param player    the player who is fishing
     * @param rand      random instance
     * @param original  the original value
     * @return  the new value
     */
    public float modifyLuck(EntityPlayer player, Random rand, float original) {
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
    public int modifyXP(EntityPlayer player, ItemStack loot, Random rand, int original) {
        return original;
    }
}
