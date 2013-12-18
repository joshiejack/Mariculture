package mariculture.api.fishery;

import mariculture.api.fishery.fish.FishSpecies;

public interface IMutation {
	/**
	 * Add a mutation, specify the mother, father and baby and the chance of the
	 * mutation **/
	public void addMutation(FishSpecies father, FishSpecies mother, FishSpecies baby, int chance);

	/** Retrieve any possible mutation for this combo **/
	public FishSpecies getMutation(FishSpecies mother, FishSpecies father);

	/** Retrieve mutation chance for this combo **/
	public int getMutationChance(FishSpecies mother, FishSpecies father);
}
