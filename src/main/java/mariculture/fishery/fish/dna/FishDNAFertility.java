package mariculture.fishery.fish.dna;

import java.util.List;
import java.util.Random;

import mariculture.api.fishery.fish.EnumFishFertility;
import mariculture.api.fishery.fish.EnumFishLifespan;
import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.fishery.FishHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class FishDNAFertility extends FishDNA {
	@Override
	public String getEggString() {
		return "FertilityList";
	}

	@Override
	public String getHigherString() {
		return "Fertility";
	}

	@Override
	public String getLowerString() {
		return "lowerFertility";
	}

	@Override
	public void getInformationDisplay(ItemStack stack, List list) {
		int fertility = stack.getTagCompound().getInteger("Fertility");

		EnumFishFertility[] enumFertility = EnumFishFertility.values();
		for (int i = 0; i < enumFertility.length; i++) {
			if (fertility >= enumFertility[i].getMin() && fertility <= enumFertility[i].getMax()) {
				list.add(StatCollector.translateToLocal("fish.data.fertility") + 
						": " + StatCollector.translateToLocal("fish.data.fertility." + enumFertility[i].toString().toLowerCase()));
			}
		}
	}
	
	@Override
	public int[] getDominant(int option1, int option2, Random rand) {
		int dominance1 = 0;
		int dominance2 = 0;

		EnumFishFertility[] fertility = EnumFishFertility.values();
		for (int i = 0; i < fertility.length; i++) {
			if (option1 >= fertility[i].getMin() && option1 <= fertility[i].getMax()) {
				dominance1 = (fertility[i].isDominant()) ? 0 : 1;
			}

			if (option2 >= fertility[i].getMin() && option2 <= fertility[i].getMax()) {
				dominance2 = (fertility[i].isDominant()) ? 0 : 1;
			}
		}

		return FishHelper.swapDominance(dominance1, dominance2, option1, option2, rand);
	}

	@Override
	public Integer getDNAFromSpecies(final FishSpecies species) {
		return (species.getFertility() * 128) / 100;
	}
}
