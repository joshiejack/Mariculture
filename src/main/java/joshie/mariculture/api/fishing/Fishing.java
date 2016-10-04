package joshie.mariculture.api.fishing;

import joshie.mariculture.api.fishing.rod.FishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nonnull;
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

    /** Register a bait
     * @param resource  the resource location for this baits loot table
     * @param stack     the bait item itself **/
    void registerBait(ResourceLocation resource, ItemStack stack);

    /** Gets the loot table from the bait used
     * @param stack the bait item, can be null
     * @return the loot table location */
    ResourceLocation getLootTableFromBait(@Nullable ItemStack stack);

    /** Gets the fishing rod states from the stack
     *  @param stack    the fishing rod **/
    @Nullable
    FishingRod getFishingRodFromStack(@Nonnull ItemStack stack);

    /** Salinity of water **/
    enum Salinity {
        FRESH, BRACKISH, SALINE
    }
}
