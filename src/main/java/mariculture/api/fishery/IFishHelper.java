package mariculture.api.fishery;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.fish.EnumSalinityType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.BiomeGenBase;

public interface IFishHelper {
	/**
	 * @param The input fish stack
	 * @param The fish type you want to make a pure bred of
	 * @return Returns a pure bred fish */
	public ItemStack makePureFish(FishSpecies fish);
	
	/** Same as above, but makes the specified gender **/
	public ItemStack makePureFish(FishSpecies fish, int gender);

	/**
	 * Biome can Live Helper, Multiple Biome
	 * @param salinity 
	 * 
	 * @param The biome the fish feeder is in
	 * @param The biome types you want to check if the current biome matches
	 * @param The tile entity of the fish feeder itself
	 * @return true or false whether the biome matches the the biome type **/
	public boolean canLive(BiomeGenBase biome, EnumBiomeType[] biomeTypes, EnumSalinityType[] salinity, TileEntity tile);

	/**
	 * Whether or not the specified biome matches any of the specified enum
	 * biome types
	 * 
	 * @param biome , the biome to compare
	 * @param biomeTypes, a list of different biome types to check
	 * @return true if it's a match, false if not */
	public boolean biomeMatches(BiomeGenBase biome, EnumBiomeType[] biomeTypes);

	/**
	 * Checks if Fish is pure
	 * 
	 * @param The itemstack of the fish
	 * @return true if the fish is a pure bred  **/
	public boolean isPure(ItemStack stack);

	/**
	 * Checks if Fish is male
	 * 
	 * @param The itemstack of the fish
	 * @return true if the fish is male  **/
	public boolean isMale(ItemStack stack);

	/**
	 * Checks if Fish is female
	 * 
	 * @param The itemstack of the fish
	 * @return true if the fish is female  **/
	public boolean isFemale(ItemStack stack);

	/** Checks whether the item stack is a fish egg **/
	public boolean isEgg(ItemStack stack);

	/**
	 * Will breed a fish from the egg that is input
	 * 
	 * @param Input egg item
	 * @param Random
	 * @return the finalised output fish */
	public ItemStack makeBredFish(ItemStack egg, Random rand);
	
	//Ignore, just a compatibility function for earlier version of mariculture
	public ItemStack makeBredFish(ItemStack input, ItemStack egg, Random rand);

	/**
	 * Will breed two fish together and return an egg
	 * 
	 * @param fish1
	 * @param fish2
	 * @return the egg */
	public ItemStack generateEgg(ItemStack fish1, ItemStack fish2);

	/** Retrieves the species based on it's id **/
	public FishSpecies getSpecies(int id);
	
	/** Retrieves a species from a string **/
	public FishSpecies getSpecies(String species);

	/** Retrieves the id for a species based on a string **/
	public int getSpeciesID(String species);
}
