package mariculture.fishery.fish.dna;

import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;

public class FishDNASalinityTolerance extends FishDNA {
    @Override
    public Integer getDNAFromSpecies(FishSpecies species) {
        return Math.max(0, species.getSalinityTolerance());
    }
    
    @Override
    public int getCopyChance() {
        return 40;
    }
}
