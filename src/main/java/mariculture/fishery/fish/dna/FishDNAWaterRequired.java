package mariculture.fishery.fish.dna;

import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.config.FishMechanics.FussyFish;
import net.minecraft.item.ItemStack;

public class FishDNAWaterRequired extends FishDNA {
    @Override
    public Integer getDNAFromSpecies(FishSpecies species) {
        return species.getWaterRequired();
    }
    
    @Override
    public int getCopyChance() {
        return 35;
    }
    
    public Integer sanitize(Integer i) {
        return Math.min(1000, Math.max(1, i));
    }

    @Override
    public Integer getDNA(ItemStack stack) {
        return sanitize(super.getDNA(stack) + FussyFish.WATER_COUNT_BOOSTER);
    }

    @Override
    public Integer getLowerDNA(ItemStack stack) {
        return sanitize(super.getLowerDNA(stack) + FussyFish.WATER_COUNT_BOOSTER);
    }
}
