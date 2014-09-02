package joshie.mariculture.fishery.fish.dna;

import joshie.mariculture.api.fishery.fish.FishDNA;
import joshie.mariculture.api.fishery.fish.FishSpecies;
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
}
