package mariculture.api.fishery;

import java.util.HashMap;
import java.util.List;

import mariculture.api.fishery.fish.FishSpecies;

public interface IMutation {
	/**
	 * Add a mutation, specify the mother, father and baby and the chance of the
	 * mutation **/
	public void addMutation(FishSpecies father, FishSpecies mother, FishSpecies baby, double chance);

	/** Retrieve any possible mutation for this combo **/
	public FishSpecies getMutation(FishSpecies mother, FishSpecies father);

	/** Retrieve mutation chance for this combo **/
	public int getMutationChance(FishSpecies mother, FishSpecies father);
	
	public HashMap<List<FishSpecies>, FishSpecies> getMutations();
}
