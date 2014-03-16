package mariculture.fishery.fish.dna;

import java.util.List;
import java.util.Random;

import mariculture.api.fishery.fish.EnumFishWorkEthic;
import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.fishery.FishHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class FishDNAWorkEthic extends FishDNA {
	@Override
	public String getEggString() {
		return "WorkList";
	}

	@Override
	public String getHigherString() {
		return "WorkEthic";
	}

	@Override
	public String getLowerString() {
		return "lowerWorkEthic";
	}
	
	@Override
	public int[] getDominant(int option1, int option2, Random rand) {
		int dominance1 = 0;
		int dominance2 = 0;

		EnumFishWorkEthic[] enumWork = EnumFishWorkEthic.values();
		for (int i = 0; i < enumWork.length; i++) {
			if (option1 == enumWork[i].getMultiplier()) {
				dominance1 = (enumWork[i].isDominant()) ? 0 : 1;
			}

			if (option2 == enumWork[i].getMultiplier()) {
				dominance2 = (enumWork[i].isDominant()) ? 0 : 1;
			}
		}

		return FishHelper.swapDominance(dominance1, dominance2, option1, option2, rand);
	}

	@Override
	public void getInformationDisplay(ItemStack stack, List list) {
		int ethic = stack.getTagCompound().getInteger("WorkEthic");

		EnumFishWorkEthic[] enumWork = EnumFishWorkEthic.values();
		for (int i = 0; i < enumWork.length; i++) {
			if (ethic == enumWork[i].getMultiplier()) {
				list.add(StatCollector.translateToLocal("fish.data.work") + 
						": " + StatCollector.translateToLocal("fish.data.work." + enumWork[i].toString().toLowerCase()));
			}
		}
	}

	@Override
	public Integer getDNAFromSpecies(FishSpecies species) {
		return species.getBaseProductivity();
	}
}
