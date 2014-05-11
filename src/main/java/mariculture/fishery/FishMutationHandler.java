package mariculture.fishery;

import java.util.ArrayList;

import mariculture.api.fishery.IMutation;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.lib.Extra;

public class FishMutationHandler implements IMutation {
	private final ArrayList<Mutation> mutations = new ArrayList();
	public static class Mutation {
		public String father;
		public String mother;
		public String baby;
		public double chance;
		public Mutation(String father, String mother, String baby, double chance) {
			this.father = father;
			this.mother = mother;
			this.baby = baby;
			this.chance = chance;
		}
	}

	@Override
	public void addMutation(FishSpecies father, FishSpecies mother, FishSpecies baby, double chance) {
		if(father.getClass().getName().contains("SkyFish")) return;
		chance *= Extra.BREEDING_MULTIPLIER;
		mutations.add(new Mutation(father.getSpecies(), mother.getSpecies(), baby.getSpecies(), chance));
	}
	
	@Override
	public ArrayList<Mutation> getMutations(FishSpecies mother, FishSpecies father) {
		ArrayList<Mutation> ret = new ArrayList();
		for(Mutation mute: mutations) {
			if(mother.getSpecies().equals(mute.mother) && father.getSpecies().equals(mute.father)) {
				ret.add(mute);
			} else if(father.getSpecies().equals(mute.mother) && mother.getSpecies().equals(mute.father)) {
				ret.add(mute);
			}
		}
		
		return ret;
	}

	@Override
	public ArrayList<Mutation> getMutations() {
		return mutations;
	}
}
