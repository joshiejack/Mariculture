package mariculture.fishery.fish.dna;

import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;

public class FishDNAFoodUsage extends FishDNA {
    @Override
    public Integer getDNAFromSpecies(FishSpecies species) {
        return species.getFoodConsumption();
    }
    
    @Override
    public int getCopyChance() {
        return 20;
    }
}
