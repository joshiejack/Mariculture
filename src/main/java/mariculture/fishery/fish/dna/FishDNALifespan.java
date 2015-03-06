package mariculture.fishery.fish.dna;

import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.config.FishMechanics.FussyFish;
import net.minecraft.item.ItemStack;

public class FishDNALifespan extends FishDNA {
    @Override
    public Integer getDNAFromSpecies(FishSpecies species) {
        return species.getLifeSpan() * 20 * 60;
    }

    @Override
    public ItemStack addDNA(ItemStack stack, Integer data) {
        super.addDNA(stack, data);
        stack.stackTagCompound.setInteger("CurrentLife", data);
        return stack;
    }
    
    @Override
    public int getCopyChance() {
        return 12;
    }
    
    public Integer sanitize(Integer i) {
        return Math.min(300, Math.max(1, i));
    }

    @Override
    public Integer getDNA(ItemStack stack) {
        return sanitize(super.getDNA(stack) + FussyFish.LIFESPAN_BOOSTER);
    }

    @Override
    public Integer getLowerDNA(ItemStack stack) {
        return sanitize(super.getLowerDNA(stack) + FussyFish.LIFESPAN_BOOSTER);
    }
}
