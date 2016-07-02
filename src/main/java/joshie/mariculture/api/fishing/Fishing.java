package joshie.mariculture.api.fishing;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

public interface Fishing {
    /** Returns the strength of this fishing tool
     * Reference:
     *      Vanilla:                2
     *      Sugar Cane Handle:      1
     *      Polished Wood Handle:   5
     * @param item  the item you want the stats of
     * @return the stats themself*/
    int getFishingRodStrength(ItemStack item);

    /** Returns the salinity for the passed in biome, if it's not found, a best guess is made
     *  @param biome the biome to check
     *  @return the salinity of the biome**/
    Salinity getSalinityForBiome(Biome biome);

    /** Register a biome as a specific salinity
     * @param biome the biome you're registering
     * @param salinity the salinity of this biome**/
    void registerBiomeAsSalinity(Biome biome, Salinity salinity);

    /** Register a trait
     *  @param trait the trait itself>**/
    void registerFishingTrait(FishingTrait trait);

    /** Get a trait from the resource
     *  @param resource the resource for the trait you wish to obtain
     *  @return the fishing trait **/
    FishingTrait getTraitFromResource(ResourceLocation resource);

    /** Register a bait
     * @param resource  the resource location for this baits loot table
     * @param stack     the bait item itself **/
    void registerBait(ResourceLocation resource, ItemStack stack);

    /** Gets the loot table from the bait used
     * @param stack the bait item, can be null
     * @return the loot table location */
    ResourceLocation getLootTableFromBait(@Nullable ItemStack stack);

    /** Salinity of water **/
    enum Salinity {
        FRESH, BRACKISH, SALINE
    }
}
