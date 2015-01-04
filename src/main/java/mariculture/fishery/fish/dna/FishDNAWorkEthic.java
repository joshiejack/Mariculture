package mariculture.fishery.fish.dna;

import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;

public class FishDNAWorkEthic extends FishDNA {
    @Override
    public Integer getDNAFromSpecies(FishSpecies species) {
        return species.getBaseProductivity();
    }
    
    @Override
    public int getCopyChance() {
        return 30;
    }
}
