package mariculture.fishery.fish.dna;

import java.util.Random;

import mariculture.api.fishery.fish.FishDNABase;
import mariculture.api.fishery.fish.FishSpecies;

public class FishDNASpecies extends FishDNABase {
    @Override
    public String getEggString() {
        return "SpeciesList";
    }

    @Override
    public String getHigherString() {
        return "SpeciesID";
    }

    @Override
    public String getLowerString() {
        return "lowerSpeciesID";
    }

    @Override
    public Integer getDNAFromSpecies(FishSpecies species) {
        return species.getID();
    }

    @Override
    public int[] getDominant(int option1, int option2, Random rand) {
        int[] ret = new int[2];

        boolean species1 = FishSpecies.species.get(option1).isDominant();
        boolean species2 = FishSpecies.species.get(option2).isDominant();

        if (species1 && !species2) {
            ret[0] = option1;
            ret[1] = option2;
        } else if (!species1 && species2) {
            ret[0] = option2;
            ret[1] = option1;
        } else if (rand.nextInt(2) == 0) {
            ret[0] = option2;
            ret[1] = option1;
        } else {
            ret[0] = option1;
            ret[1] = option2;
        }

        return ret;
    }
}
