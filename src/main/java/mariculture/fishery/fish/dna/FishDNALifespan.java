package mariculture.fishery.fish.dna;

import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.lib.Text;
import mariculture.fishery.Fish;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FishDNALifespan extends FishDNA {
	@Override
	public Integer getDNAFromSpecies(FishSpecies species) {
		return (species.getLifeSpan() * 20) * 60;
	}

	@Override
	public ItemStack addDNA(ItemStack stack, Integer data) {
		super.addDNA(stack, data);
		stack.stackTagCompound.setInteger("CurrentLife", data);
		return stack;
	}
}
