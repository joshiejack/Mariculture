package mariculture.fishery;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.IMutation;
import mariculture.api.fishery.fish.FishSpecies;

public class FishMutationHandler implements IMutation {
	private final HashMap<List<FishSpecies>, FishSpecies> fakeList = new HashMap();
	private final HashMap<List<String>, String> mutationList = new HashMap();
	private final Map mutationChanceList = new HashMap();

	@Override
	public void addMutation(FishSpecies father, FishSpecies mother, FishSpecies baby, double chance) {
		mutationList.put(Arrays.asList(mother.getSpecies(), father.getSpecies()), baby.getSpecies());
		mutationChanceList.put((Arrays.asList(mother.getSpecies(), father.getSpecies())), (int)(chance * 10));
		fakeList.put((Arrays.asList(mother, father)), baby);
	}

	@Override
	public FishSpecies getMutation(FishSpecies mother, FishSpecies father) {
		String ret = mutationList.get(Arrays.asList(mother.getSpecies(), father.getSpecies()));
		if(ret == null) ret = mutationList.get(Arrays.asList(father.getSpecies(), mother.getSpecies()));
		return ret == null? null: Fishing.fishHelper.getSpecies(ret);
	}

	@Override
	public int getMutationChance(FishSpecies mother, FishSpecies father) {
		if (mutationChanceList.get((Arrays.asList(mother.getSpecies(), father.getSpecies()))) != null) {
			return (Integer) mutationChanceList.get((Arrays.asList(mother.getSpecies(), father.getSpecies())));
		}
		
		if (mutationChanceList.get((Arrays.asList(father.getSpecies(), mother.getSpecies()))) != null) {
			return (Integer) mutationChanceList.get((Arrays.asList(father.getSpecies(), mother.getSpecies())));
		}

		return -1;
	}

	@Override
	public HashMap<List<FishSpecies>, FishSpecies> getMutations() {
		return fakeList;
	}
}
