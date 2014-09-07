package joshie.mariculture.fishery.fish.dna;

import joshie.mariculture.api.fishery.fish.FishDNA;
import joshie.mariculture.api.fishery.fish.FishSpecies;

public class FishDNATemperatureTolerance extends FishDNA {
    @Override
    public Integer getDNAFromSpecies(FishSpecies species) {
        return Math.max(0, species.getTemperatureTolerance());
    }
    
    @Override
    public int getCopyChance() {
        return 35;
    }
}
