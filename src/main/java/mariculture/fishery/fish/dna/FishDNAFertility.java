package mariculture.fishery.fish.dna;

import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.config.FishMechanics.FussyFish;
import net.minecraft.item.ItemStack;

public class FishDNAFertility extends FishDNA {
    @Override
    public Integer getDNAFromSpecies(FishSpecies species) {
        return species.getFertility();
    }
    
    @Override
    public int getCopyChance() {
        return 15;
    }
    
    public Integer sanitize(Integer i) {
        return Math.min(Integer.MAX_VALUE, Math.max(1, i));
    }

    @Override
    public Integer getDNA(ItemStack stack) {
        return sanitize(super.getDNA(stack) + FussyFish.FERTILITY_BOOSTER);
    }

    @Override
    public Integer getLowerDNA(ItemStack stack) {
        return sanitize(super.getLowerDNA(stack) + FussyFish.FERTILITY_BOOSTER);
    }
}
