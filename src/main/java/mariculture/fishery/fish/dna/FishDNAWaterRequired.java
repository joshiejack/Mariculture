package mariculture.fishery.fish.dna;

import java.util.Random;

import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;

public class FishDNAWaterRequired extends FishDNA {
	@Override
	public Integer getDNAFromSpecies(FishSpecies species) {
		return species.getWaterRequired();
	}
}
