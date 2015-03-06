package mariculture.fishery.fish.dna;

import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.config.FishMechanics.FussyFish;
import net.minecraft.item.ItemStack;

public class FishDNASalinityTolerance extends FishDNA {
    @Override
    public Integer getDNAFromSpecies(FishSpecies species) {
        return Math.max(0, species.getSalinityTolerance());
    }

    @Override
    public int getCopyChance() {
        return 40;
    }

    public Integer sanitize(Integer i) {
        return Math.min(2, Math.max(0, i));
    }

    @Override
    public Integer getDNA(ItemStack stack) {
        return sanitize(super.getDNA(stack) + FussyFish.SALINITY_TOLERANCE_BOOSTER);
    }

    @Override
    public Integer getLowerDNA(ItemStack stack) {
        return sanitize(super.getLowerDNA(stack) + FussyFish.SALINITY_TOLERANCE_BOOSTER);
    }
}
