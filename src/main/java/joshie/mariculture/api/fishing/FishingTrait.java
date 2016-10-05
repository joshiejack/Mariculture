package joshie.mariculture.api.fishing;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

import java.util.HashMap;
import java.util.Random;

/** Fishing Traits, attached to fishing rods **/
public abstract class FishingTrait {
    public static final HashMap<ResourceLocation, FishingTrait> TRAIT_REGISTRY = new HashMap<>();
    private final ResourceLocation resource;

    public FishingTrait(String string) {
        this.resource = new ResourceLocation(Loader.instance().activeModContainer().getModId(), string);
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

    /** Called to modify the speed
     * @param original  the original speed
     * @return the modified speed**/
    public int modifySpeed(int original) {
        return original;
    }

    /** Called to modify the damage
     *  @param rand     the Random object
     *  @param damage   the original damage
     *  @return the new damage value**/
    public int modifyDamage(Random rand, int damage) {
        return rand.nextFloat() < 0.3 ? damage / 2 : damage;
    }
}
