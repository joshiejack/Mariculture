package mariculture.api.fishery;

import java.util.Random;

import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IFishHelper {
    /** Registers a fish, with a default id, You must register your fish in PreInit, as Fish IDs are assigned in Init, So do not do any recipes on them until init, or to be extra safe postinit **/
    public FishSpecies registerFish(String modid, Class<? extends FishSpecies> species);

    public FishSpecies registerFish(String modid, Class<? extends FishSpecies> species, int default_id);

    /**
     * @param The input fish stack
     * @param The fish type you want to make a pure bred of
     * @return Returns a pure bred fish */
    public ItemStack makePureFish(FishSpecies fish);

    /** Makes a bred fish, using the mutation chance modifier, from an egg, returns a fish **/
    public ItemStack makeBredFish(ItemStack egg, Random rand, double modifier);

    /** Creates an egg from two fish passed in */
    public ItemStack generateEgg(ItemStack fish1, ItemStack fish2);
    
    /** Returns the egg, with 1 attempt at hatching it **/
    public ItemStack attemptToHatchEgg(ItemStack egg, Random rand, double mutation, IIncubator tile);

    /** Whether this fish can live at the current coordinates or not **/
    public boolean canLive(World world, int x, int y, int z, ItemStack fish);

    /** Returns whether a fish is pure bred or not  **/
    public boolean isPure(ItemStack stack);

    /** Returns whether a fish is male or nnot **/
    public boolean isMale(ItemStack stack);

    /** Returns whether a fish is female or not  **/
    public boolean isFemale(ItemStack stack);

    /** Checks whether the item stack is a fish egg **/
    public boolean isEgg(ItemStack stack);

    /** Returns the species of the fish, from either the item, the string or the id **/
    public FishSpecies getSpecies(ItemStack stack);

    public FishSpecies getSpecies(String species);

    public FishSpecies getSpecies(int id);

    /** Retrieves the dna you wish to retrieve **/
    public Integer getDNA(String dna, ItemStack stack);

    public Integer getLowerDNA(String dna, ItemStack stack);

    /** IGNORE **/
    public void registerFishies();
}
