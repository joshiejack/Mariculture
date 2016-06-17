package joshie.mariculture.api.fishing;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;

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

    /** Salinity of water **/
    enum Salinity {
        FRESH, BRACKISH, SALINE
    }
}
