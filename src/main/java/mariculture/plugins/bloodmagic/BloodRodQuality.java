package mariculture.plugins.bloodmagic;

import mariculture.api.fishery.RodQuality;
import mariculture.fishery.Fish;

public class BloodRodQuality extends RodQuality {
	public BloodRodQuality(int uses, int rank, int enchantability) {
		super(uses, rank, enchantability, 100);
	}

	@Override
	public boolean caughtAlive(String species) {
		if(species.equals(Fish.angel.getSpecies())) 	return true;
		if(species.equals(Fish.gold.getSpecies())) 		return true;
		if(species.equals(Fish.undead.getSpecies()))	return true;
		if(species.equals(Fish.perch.getSpecies()))		return true;
		if(species.equals(Fish.salmon.getSpecies()))	return true;
		if(species.equals(Fish.trout.getSpecies()))		return true;
		return false;
	}
}
