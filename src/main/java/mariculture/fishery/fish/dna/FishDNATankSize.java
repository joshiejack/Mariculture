package mariculture.fishery.fish.dna;

import java.util.Random;

import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;

public class FishDNATankSize extends FishDNA {
	@Override
	public String getEggString() {
		return "TankSizeList";
	}

	@Override
	public String getHigherString() {
		return "TankSize";
	}

	@Override
	public String getLowerString() {
		return "lowerTankSize";
	}

	@Override
	public int[] getDominant(int option1, int option2, Random rand) {
		int[] ret = new int[2];

		if (option1 >= option2) {
			ret[0] = option1;
			ret[1] = option2;
		} else {
			ret[0] = option2;
			ret[1] = option1;
		}

		return ret;
	}

	@Override
	public Integer getDNAFromSpecies(FishSpecies species) {
		return species.getTankLevel();
	}
}
