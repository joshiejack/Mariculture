package mariculture.api.fishery;

import java.util.Random;

import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IFishHelper {
	/**
	 * @param The input fish stack
	 * @param The fish type you want to make a pure bred of
	 * @return Returns a pure bred fish */
	public ItemStack makePureFish(FishSpecies fish);
	
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

	/** Makes a bred fish, using the mutation chance modifier, from an egg, returns a fish **/
	public ItemStack makeBredFish(ItemStack egg, Random rand, double modifier);

	/** Creates an egg from two fish passed in */
	public ItemStack generateEgg(ItemStack fish1, ItemStack fish2);

	/** Retrieves the species based on it's id **/
	public FishSpecies getSpecies(int id);
	
	/** Retrieves a species from a string **/
	public FishSpecies getSpecies(String species);
	
	/** Retrieves the dna you wish to retrieve **/
	public Integer getDNA(String dna, ItemStack stack);
	public Integer getLowerDNA(String dna, ItemStack stack);

	/** Retrieves the id for a species based on a string **/
	public int getSpeciesID(String species);

	public FishSpecies getSpecies(ItemStack stack);
}
