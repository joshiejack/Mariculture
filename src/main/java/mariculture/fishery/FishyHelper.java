package mariculture.fishery;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Level;

import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.IFishHelper;
import mariculture.api.fishery.IMutation.Mutation;
import mariculture.api.fishery.fish.FishDNABase;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.handlers.LogHandler;
import mariculture.core.helpers.AverageHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class FishyHelper implements IFishHelper {
	public static int MALE = 0;
	public static int FEMALE = 1;

	@Override
	public ItemStack makePureFish(FishSpecies species) {
		ItemStack fishStack = new ItemStack(Fishery.fishy);
		if (!fishStack.hasTagCompound()) {
			fishStack.setTagCompound(new NBTTagCompound());
		}

		for (int i = 0; i < FishDNABase.DNAParts.size(); i++) {
			FishDNABase.DNAParts.get(i).addDNA(fishStack, FishDNABase.DNAParts.get(i).getDNAFromSpecies(species));
			FishDNABase.DNAParts.get(i).addLowerDNA(fishStack, FishDNABase.DNAParts.get(i).getDNAFromSpecies(species));
		}

		return fishStack;
	}

	@Override
	public ItemStack makeBredFish(ItemStack egg, Random rand, double modifier) {
		ItemStack fish = new ItemStack(Fishery.fishy);
		for (int i = 0; i < FishDNABase.DNAParts.size(); i++) {
			if(!FishDNABase.DNAParts.get(i).hasEggData(egg)) {
				return null;
			}
		}

		for (int i = 0; i < FishDNABase.DNAParts.size(); i++) {
			int[] DNAlist = FishDNABase.DNAParts.get(i).getDNAList(egg);

			int parent1DNA = DNAlist[rand.nextInt(2)];
			int parent2DNA = DNAlist[rand.nextInt(2) + 2];

			int[] babyDNA = FishDNABase.DNAParts.get(i).attemptMutation(parent1DNA, parent2DNA);

			FishDNABase.DNAParts.get(i).addDNA(fish, babyDNA[0]);
			FishDNABase.DNAParts.get(i).addLowerDNA(fish, babyDNA[1]);
		}

		/* Mutate the fish species */
		int species1 = Fish.species.getDNA(fish);
		int species2 = Fish.species.getLowerDNA(fish);

		ArrayList<Mutation> mutations = Fishing.mutation.getMutations(getSpecies(species1), getSpecies(species2));
		if(species1 != species2 && mutations != null && mutations.size() > 0) {
			for(Mutation mute: mutations) {
				FishSpecies baby = Fishing.fishHelper.getSpecies(mute.baby);
				if(baby != null) {
					if(rand.nextInt(1000) < ((mute.chance * 10) * modifier)) {
						for (int i = 0; i < FishDNABase.DNAParts.size(); i++) {
							FishDNABase.DNAParts.get(i).addDNA(fish, FishDNABase.DNAParts.get(i).getDNAFromSpecies(baby));
						}
					}
					
					//Second attempt for a mutation
					if(rand.nextInt(1000) < ((mute.chance * 10)) * modifier) {
						for (int i = 0; i < FishDNABase.DNAParts.size(); i++) {
							FishDNABase.DNAParts.get(i).addDNA(fish, FishDNABase.DNAParts.get(i).getDNAFromSpecies(baby));
						}
					}
				}
			}
		}

		/* Reorder the fish's DNA to be sorted by dominance */
		for (int i = 0; i < FishDNABase.DNAParts.size(); i++) {
			int dna1 = FishDNABase.DNAParts.get(i).getDNA(fish);
			int dna2 = FishDNABase.DNAParts.get(i).getLowerDNA(fish);

			int[] dominance = FishDNABase.DNAParts.get(i).getDominant(dna1, dna2, rand);

			FishDNABase.DNAParts.get(i).addDNA(fish, dominance[0]);
			FishDNABase.DNAParts.get(i).addLowerDNA(fish, dominance[1]);
		}
		
		Fish.gender.addDNA(fish, rand.nextInt(2));
		
		return fish;
	}
	
	@Override
	public boolean canLive(World world, int x, int y, int z, ItemStack stack) {
		FishSpecies fish = FishSpecies.species.get(Fish.species.getDNA(stack));
		Salinity salt = MaricultureHandlers.environment.getSalinity(world, x, z);
		int temperature = MaricultureHandlers.environment.getTemperature(world, x, y, z);
		boolean worldCorrect = fish.isWorldCorrect(world);
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof IUpgradable) {
			IUpgradable upgradable = (IUpgradable) tile;
			temperature += MaricultureHandlers.upgrades.getData("temp", upgradable);
			int salinity = salt.ordinal() + MaricultureHandlers.upgrades.getData("salinity", upgradable);
			if(salinity <= 0) salinity = 0; if(salinity > 2) salinity = 2;
			salt = Salinity.values()[salinity];
			if(!worldCorrect) worldCorrect = MaricultureHandlers.upgrades.hasUpgrade("ethereal", upgradable);
		}
		
		if(!worldCorrect) return false;
		
		if(fish != null) {
			if(!fish.canWork(Time.getTime(world))) return false;
			else return MaricultureHandlers.environment.matches(salt, temperature, fish.salinity, fish.temperature);
		} else return false;
	}

	@Override
	public boolean isPure(ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (stack.stackTagCompound.getInteger("SpeciesID") == stack.stackTagCompound.getInteger("lowerSpeciesID")) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isMale(ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (!stack.stackTagCompound.hasKey("Gender")) {
				return false;
			}

			if (stack.stackTagCompound.getInteger("Gender") == MALE) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isFemale(ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (!stack.stackTagCompound.hasKey("Gender")) {
				return false;
			}

			if (Fish.gender.getDNA(stack) == FEMALE) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isEgg(ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (stack.stackTagCompound.hasKey("isEgg")) {
				return true;
			}
		}

		return false;
	}

	@Override
	public ItemStack generateEgg(ItemStack fish1, ItemStack fish2) {
		ItemStack egg = new ItemStack(Fishery.fishy);
		egg.setTagCompound(new NBTTagCompound());

		for (int i = 0; i < FishDNABase.DNAParts.size(); i++) {
			int[] DNAlist = new int[4];
			DNAlist[0] = FishDNABase.DNAParts.get(i).getDNA(fish1);
			DNAlist[1] = FishDNABase.DNAParts.get(i).getLowerDNA(fish1);
			DNAlist[2] = FishDNABase.DNAParts.get(i).getDNA(fish2);
			DNAlist[3] = FishDNABase.DNAParts.get(i).getLowerDNA(fish2);
			FishDNABase.DNAParts.get(i).addDNAList(egg, DNAlist);
		}

		egg.stackTagCompound.setBoolean("isEgg", true);
		int[] fertility = egg.stackTagCompound.getIntArray(Fish.fertility.getEggString());
		int eggLife = AverageHelper.getMode(fertility);
		egg.stackTagCompound.setInteger("currentFertility", eggLife);
		egg.stackTagCompound.setInteger("malesGenerated", 0);
		egg.stackTagCompound.setInteger("femalesGenerated", 0);

		return egg;
	}

	@Override
	public FishSpecies getSpecies(String species) {
		for (Entry<Integer, FishSpecies> speciesList : FishSpecies.species.entrySet()) {
			FishSpecies fish = speciesList.getValue();
			if (fish.getSpecies().equals(species)) {
				return fish;
			}
		}

		return Fish.cod;
	}
	
	@Override
	public FishSpecies getSpecies(ItemStack stack) {
		return getSpecies(Fish.species.getDNA(stack));
	}
	
	@Override
	public FishSpecies getSpecies(int id) {
		return FishSpecies.species.get((Integer)id);
	}

	@Override
	public int getSpeciesID(String species) {
		for (Entry<Integer, FishSpecies> speciesList : FishSpecies.species.entrySet()) {
			FishSpecies fish = speciesList.getValue();
			if (fish.getSpecies().equals(species)) {
				return speciesList.getKey();
			}
		}

		return 0;
	}

	@Override
	public Integer getDNA(String str, ItemStack stack) {
		for(FishDNABase dna: FishDNABase.DNAParts) {
			if(str.equals(dna.getName())) return dna.getDNA(stack);
		}
		
		return -1;
	}

	@Override
	public Integer getLowerDNA(String str, ItemStack stack) {
		for(FishDNABase dna: FishDNABase.DNAParts) {
			if(str.equals(dna.getName())) return dna.getLowerDNA(stack);
		}
		
		return -1;
	}
}
