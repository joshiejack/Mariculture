package mariculture.fishery.fish.dna;

import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;

public class FishDNAWaterRequired extends FishDNA {
    @Override
    public Integer getDNAFromSpecies(FishSpecies species) {
        return species.getWaterRequired();
    }
    
    @Override
    public int getCopyChance() {
        return 35;
    }
}
