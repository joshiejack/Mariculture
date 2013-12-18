package mariculture.fishery;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import mariculture.api.fishery.IMutation;
import mariculture.api.fishery.fish.FishSpecies;

public class FishMutationHandler implements IMutation {
	private final Map mutationList = new HashMap();
	private final Map mutationChanceList = new HashMap();

	@Override
	public void addMutation(FishSpecies father, FishSpecies mother, FishSpecies baby, int chance) {
		mutationList.put((Arrays.asList(mother, father)), baby);
		mutationList.put((Arrays.asList(father, mother)), baby);
		mutationChanceList.put((Arrays.asList(mother, father)), 100 / chance);
		mutationChanceList.put((Arrays.asList(father, mother)), 100 / chance);

	}

	@Override
	public FishSpecies getMutation(FishSpecies mother, FishSpecies father) {
		FishSpecies ret = (FishSpecies) mutationList.get((Arrays.asList(mother, father)));

		return (ret == null) ? null : ret;
	}

	@Override
	public int getMutationChance(FishSpecies mother, FishSpecies father) {
		if (mutationChanceList.get((Arrays.asList(mother, father))) != null) {
			return (Integer) mutationChanceList.get((Arrays.asList(mother, father)));
		}

		return -1;
	}

}
