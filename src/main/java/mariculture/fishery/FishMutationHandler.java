package mariculture.fishery;

import java.util.ArrayList;

import mariculture.api.fishery.IMutation;
import mariculture.api.fishery.IMutationRequirement;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.config.FishMechanics;
import mariculture.fishery.fish.requirements.RequirementDefault;

public class FishMutationHandler implements IMutation {
    private final ArrayList<Mutation> mutations = new ArrayList();

    @Override
    public void addMutation(FishSpecies father, FishSpecies mother, FishSpecies baby, double chance) {
        chance *= FishMechanics.BREEDING_MULTIPLIER;
        mutations.add(new Mutation(father.getSpecies(), mother.getSpecies(), baby.getSpecies(), chance, RequirementDefault.INSTANCE));
    }

    @Override
    public void addMutation(FishSpecies father, FishSpecies mother, FishSpecies baby, double chance, IMutationRequirement requirement) {
        chance *= FishMechanics.BREEDING_MULTIPLIER;
        mutations.add(new Mutation(father.getSpecies(), mother.getSpecies(), baby.getSpecies(), chance, requirement));
    }

    @Override
    public ArrayList<Mutation> getMutations(FishSpecies mother, FishSpecies father) {
        ArrayList<Mutation> ret = new ArrayList();
        for (Mutation mute : mutations)
            if (mother.getSpecies().equals(mute.mother) && father.getSpecies().equals(mute.father)) {
                ret.add(mute);
            } else if (father.getSpecies().equals(mute.mother) && mother.getSpecies().equals(mute.father)) {
                ret.add(mute);
            }

        return ret;
    }

    @Override
    public ArrayList<Mutation> getMutations() {
        return mutations;
    }
}
