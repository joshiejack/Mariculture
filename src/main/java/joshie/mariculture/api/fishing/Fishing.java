package joshie.mariculture.api.fishing;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

public interface Fishing {
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

    /** Register a new fishing pole component
     * @param name          the name of the material
     * @param durability    the durability of the material
     * @param strength      the strength 0-10 with 10 being stronger: Wood = 3, Reed = 2, Polished Wood = 5
     * @return the component for you to manipulate */
    RodPart createPole(String name, int durability, int strength);

    /** Register a new fishing reel component
     * @param name              the name of the material
     * @param heightModifier    the height of casting is multiplied by this value
     * @param distanceBonus     this distance is added when casting
     * @param retractSpeed      the speed of retraction, vanilla = 0.1D
     * @return the component for you to manipulate */
    RodPart createReel(String name, float heightModifier, float distanceBonus, double retractSpeed);

    /** Register a new fishing string component
     * @param name                the name of the material
     * @param strengthModifier    the strength given by the poles gets multiplied by this value
     * @return the component for you to manipulate */
    RodPart createString(String name, float strengthModifier);

    /** Register a new fishing hook component
     * @param name           the name of the material
     * @param catchSpeed     how fast this rod catches fish, higher = faster, vanilla = 1
     * @param bestSizes       the sizes of fish, this is best at catching
     * @return the component for you to manipulate */
    RodPart createHook(String name, int catchSpeed, Size... bestSizes);

    /** Salinity of water **/
    enum Salinity {
        FRESH, BRACKISH, SALINE
    }

    /** Size of fish **/
    enum Size {
        TINY, SMALL, MEDIUM, LARGE, HUGE, GIGANTIC
    }
}
