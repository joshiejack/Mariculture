package mariculture.fishery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import mariculture.api.fishery.IMutation;
import mariculture.api.fishery.fish.FishSpecies;

public class FishMutationHandler implements IMutation {
	private final HashMap<List<FishSpecies>, FishSpecies> mutationList = new HashMap();
	private final Map mutationChanceList = new HashMap();

	@Override
	public void addMutation(FishSpecies father, FishSpecies mother, FishSpecies baby, double chance) {
		mutationList.put((Arrays.asList(mother, father)), baby);
		mutationChanceList.put((Arrays.asList(mother, father)), (int)(chance * 10));
	}

	@Override
	public FishSpecies getMutation(FishSpecies mother, FishSpecies father) {
		FishSpecies ret = (FishSpecies) mutationList.get((Arrays.asList(mother, father)));
		if(ret == null)
			ret = (FishSpecies) mutationList.get((Arrays.asList(father, mother)));

		return (ret == null) ? null : ret;
	}

	@Override
	public int getMutationChance(FishSpecies mother, FishSpecies father) {
		if (mutationChanceList.get((Arrays.asList(mother, father))) != null) {
			return (Integer) mutationChanceList.get((Arrays.asList(mother, father)));
		}
		
		if (mutationChanceList.get((Arrays.asList(father, mother))) != null) {
			return (Integer) mutationChanceList.get((Arrays.asList(father, mother)));
		}

		return -1;
	}

	@Override
	public HashMap<List<FishSpecies>, FishSpecies> getMutations() {
		return mutationList;
	}

}
