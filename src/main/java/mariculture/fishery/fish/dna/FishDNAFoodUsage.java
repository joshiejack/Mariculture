package mariculture.fishery.fish.dna;

import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.config.FishMechanics.FussyFish;
import net.minecraft.item.ItemStack;

public class FishDNAFoodUsage extends FishDNA {
    @Override
    public Integer getDNAFromSpecies(FishSpecies species) {
        return species.getFoodConsumption();
    }
    
    @Override
    public int getCopyChance() {
        return 20;
    }
    
    public Integer sanitize(Integer i) {
        return Math.min(10, Math.max(0, i));
    }

    @Override
    public Integer getDNA(ItemStack stack) {
        return sanitize(super.getDNA(stack) + FussyFish.FOOD_USAGE_BOOSTER);
    }

    @Override
    public Integer getLowerDNA(ItemStack stack) {
        return sanitize(super.getLowerDNA(stack) + FussyFish.FOOD_USAGE_BOOSTER);
    }
}
