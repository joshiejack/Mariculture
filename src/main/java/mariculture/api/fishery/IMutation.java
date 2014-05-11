package mariculture.api.fishery;

import java.util.ArrayList;

import mariculture.api.fishery.fish.FishSpecies;
import mariculture.fishery.FishMutationHandler.Mutation;

public interface IMutation {
	/**
	 * Add a mutation, specify the mother, father and baby and the chance of the
	 * mutation **/
	public void addMutation(FishSpecies father, FishSpecies mother, FishSpecies baby, double chance);
	public ArrayList<Mutation> getMutations(FishSpecies mother, FishSpecies father);
	public ArrayList<Mutation> getMutations();
}
