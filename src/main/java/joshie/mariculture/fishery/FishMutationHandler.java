package joshie.mariculture.fishery;

import java.util.ArrayList;

import joshie.mariculture.api.fishery.fish.FishSpecies;
import joshie.mariculture.api.fishery.interfaces.IMutation;
import joshie.mariculture.core.config.FishMechanics;

public class FishMutationHandler implements IMutation {
    private final ArrayList<Mutation> mutations = new ArrayList();

    @Override
    public void addMutation(FishSpecies father, FishSpecies mother, FishSpecies baby, double chance) {
        chance *= FishMechanics.BREEDING_MULTIPLIER;
        mutations.add(new Mutation(father.getSpecies(), mother.getSpecies(), baby.getSpecies(), chance));
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
