package mariculture.fishery.fish.dna;

import java.util.List;
import java.util.Random;

import mariculture.api.fishery.fish.EnumFishFertility;
import mariculture.api.fishery.fish.EnumFishLifespan;
import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.fishery.FishHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class FishDNALifespan extends FishDNA {
	@Override
	public String getEggString() {
		return "LifespanList";
	}

	@Override
	public String getHigherString() {
		return "Lifespan";
	}

	@Override
	public String getLowerString() {
		return "lowerLifespan";
	}

	@Override
	public void getInformationDisplay(ItemStack stack, List list) {
		int lifespan = stack.getTagCompound().getInteger("Lifespan");

		EnumFishLifespan[] enumLifespan = EnumFishLifespan.values();
		for (int i = 0; i < enumLifespan.length; i++) {
			if (lifespan >= enumLifespan[i].getMin() && lifespan <= enumLifespan[i].getMax()) {
				list.add(StatCollector.translateToLocal("fish.data.lifespan") + 
						": " + StatCollector.translateToLocal("fish.data.lifespan." + enumLifespan[i].toString().toLowerCase()));
			}
		}
	}

	@Override
	public int[] getDominant(int option1, int option2, Random rand) {
		int dominance1 = 0;
		int dominance2 = 0;

		EnumFishLifespan[] enumLifespan = EnumFishLifespan.values();
		for (int i = 0; i < enumLifespan.length; i++) {
			if (option1 >= enumLifespan[i].getMin() && option1 <= enumLifespan[i].getMax()) {
				dominance1 = (enumLifespan[i].isDominant()) ? 0 : 1;
			}

			if (option2 >= enumLifespan[i].getMin() && option2 <= enumLifespan[i].getMax()) {
				dominance2 = (enumLifespan[i].isDominant()) ? 0 : 1;
			}
		}

		return FishHelper.swapDominance(dominance1, dominance2, option1, option2, rand);
	}

	@Override
	public Integer getDNAFromSpecies(FishSpecies species) {
		return (species.getLifeSpan() * 20) * 60;
	}

	@Override
	public ItemStack addDNA(ItemStack stack, Integer data) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}

		stack.stackTagCompound.setInteger(this.getHigherString(), data);
		stack.stackTagCompound.setInteger("CurrentLife", data);

		return stack;
	}
}
