package mariculture.fishery;

import java.util.Map.Entry;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishDNABase;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.Dye;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.Modules;
import mariculture.fishery.fish.FishAngel;
import mariculture.fishery.fish.FishAngler;
import mariculture.fishery.fish.FishBass;
import mariculture.fishery.fish.FishLamprey;
import mariculture.fishery.fish.FishBlaze;
import mariculture.fishery.fish.FishBoneless;
import mariculture.fishery.fish.FishButterfly;
import mariculture.fishery.fish.FishCatfish;
import mariculture.fishery.fish.FishClown;
import mariculture.fishery.fish.FishCod;
import mariculture.fishery.fish.FishDamsel;
import mariculture.fishery.fish.FishDragon;
import mariculture.fishery.fish.FishElectricRay;
import mariculture.fishery.fish.FishEnder;
import mariculture.fishery.fish.FishGlow;
import mariculture.fishery.fish.FishGold;
import mariculture.fishery.fish.FishHerring;
import mariculture.fishery.fish.FishJelly;
import mariculture.fishery.fish.FishKoi;
import mariculture.fishery.fish.FishBlaasop;
import mariculture.fishery.fish.FishManOWar;
import mariculture.fishery.fish.FishMantaRay;
import mariculture.fishery.fish.FishMinecraft;
import mariculture.fishery.fish.FishMinnow;
import mariculture.fishery.fish.FishNether;
import mariculture.fishery.fish.FishNight;
import mariculture.fishery.fish.FishPerch;
import mariculture.fishery.fish.FishPiranha;
import mariculture.fishery.fish.FishPuffer;
import mariculture.fishery.fish.FishSalmon;
import mariculture.fishery.fish.FishSiamese;
import mariculture.fishery.fish.FishSpider;
import mariculture.fishery.fish.FishSquid;
import mariculture.fishery.fish.FishStargazer;
import mariculture.fishery.fish.FishStingRay;
import mariculture.fishery.fish.FishTang;
import mariculture.fishery.fish.FishTetra;
import mariculture.fishery.fish.FishTrout;
import mariculture.fishery.fish.FishTuna;
import mariculture.fishery.fish.FishUndead;
import mariculture.fishery.fish.dna.FishDNAAreaOfEffect;
import mariculture.fishery.fish.dna.FishDNAFertility;
import mariculture.fishery.fish.dna.FishDNAFoodUsage;
import mariculture.fishery.fish.dna.FishDNAGender;
import mariculture.fishery.fish.dna.FishDNALifespan;
import mariculture.fishery.fish.dna.FishDNASpecies;
import mariculture.fishery.fish.dna.FishDNAWaterRequired;
import mariculture.fishery.fish.dna.FishDNAWorkEthic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class Fish {
	public static FishDNABase species;
	public static FishDNABase gender;
	
	public static FishDNA foodUsage;
	public static FishDNA tankSize;
	public static FishDNA lifespan;
	public static FishDNA fertility;
	public static FishDNA production;
	public static FishDNA east;
	public static FishDNA west;
	public static FishDNA up;
	public static FishDNA down;
	public static FishDNA south;
	public static FishDNA north;
	
	public static FishSpecies nether;
	public static FishSpecies glow;
	public static FishSpecies blaze;
	public static FishSpecies night;
	public static FishSpecies ender;
	public static FishSpecies dragon;
	public static FishSpecies minnow;
	public static FishSpecies salmon;
	public static FishSpecies bass;
	public static FishSpecies tetra;
	public static FishSpecies catfish;
	public static FishSpecies piranha;
	public static FishSpecies cod;
	public static FishSpecies perch;
	public static FishSpecies tuna;
	public static FishSpecies stingRay;
	public static FishSpecies mantaRay;
	public static FishSpecies electricRay;
	public static FishSpecies damsel;
	public static FishSpecies angel;
	public static FishSpecies puffer;
	public static FishSpecies squid;
	public static FishSpecies jelly;
	public static FishSpecies manOWar;
	public static FishSpecies gold;
	public static FishSpecies siamese;
	public static FishSpecies koi;
	public static FishSpecies butterfly;
	public static FishSpecies tang;
	public static FishSpecies clown;
	//1.2.2 Species
	public static FishSpecies blaasop;
	public static FishSpecies stargazer;
	public static FishSpecies lamprey;
	public static FishSpecies spider;
	public static FishSpecies boneless;
	public static FishSpecies undead;
	public static FishSpecies minecraft;
	public static FishSpecies angler;
	public static FishSpecies trout;
	public static FishSpecies herring;
	
	public static void init() {
		addDNA();
		addFish();
		addMutations();
	}
	
	private static void addDNA() {
		//Exceptions to 'DNA', They have their own special conditions
		species = new FishDNASpecies().register();
		gender = new FishDNAGender().register();
		
		//Lifespan
		lifespan = new FishDNALifespan().register();
		lifespan.add("miniscule", 0, 3600, true);
		lifespan.add("tiny", 3601, 6001, false);
		lifespan.add("short", 6002, 12002, true);
		lifespan.add("medium", 12003, 18003, true);
		lifespan.add("long", 18004, 24004, false);
		lifespan.add("epic", 24005, 36005, true);
		lifespan.add("marathon", 36006, 54006, false);
		lifespan.add("insane", 54007, 100000, true);
		
		//Fertility
		fertility = new FishDNAFertility().register();
		fertility.add("extremehigh", 3000, 5000, false);
		fertility.add("veryhigh", 2000, 2999, true);
		fertility.add("high", 1000, 1999, false);
		fertility.add("normal", 500, 999, true);
		fertility.add("low", 250, 499, false);
		fertility.add("verylow", 100, 249, false);
		fertility.add("extremelow", 25, 99, true);
		fertility.add("pathetic", 0, 24, true);
		
		//Production
		production = new FishDNAWorkEthic().register();
		production.add("lazy", 0, 0, false);
		production.add("normal", 1, 1, true);
		production.add("hardworker", 2, 2, false);
		production.add("overclocker", 3, 3, false);
		
		//Tank Size
		tankSize = new FishDNAWaterRequired().setHidden().register();
		tankSize.add("preschool", 0, 14, false);
		tankSize.add("beginner", 15, 24, false);
		tankSize.add("basic", 25, 44, false);
		tankSize.add("standard", 45, 63, true);
		tankSize.add("intermediate", 64, 124, true);
		tankSize.add("advanced", 125, 199, true);
		tankSize.add("ultimate", 200, 319, false);
		tankSize.add("draconic", 320, 10000, true);
		
		//Food Usage
		foodUsage = new FishDNAFoodUsage().setHidden().register();
		foodUsage.add("none", 0, 0, false);
		foodUsage.add("normal", 1, 1, false);
		foodUsage.add("hungry", 2, 2, true);
		foodUsage.add("greedy", 3, 3, true);
		
		//Area of Effect
		up = new FishDNAAreaOfEffect(ForgeDirection.UP).setHidden().register();
		down = new FishDNAAreaOfEffect(ForgeDirection.DOWN).setHidden().register();
		north = new FishDNAAreaOfEffect(ForgeDirection.NORTH).setHidden().register();
		east = new FishDNAAreaOfEffect(ForgeDirection.EAST).setHidden().register();
		south = new FishDNAAreaOfEffect(ForgeDirection.SOUTH).setHidden().register();
		west = new FishDNAAreaOfEffect(ForgeDirection.WEST).setHidden().register();
		addEffectNames(new FishDNA[] { up, down, north, east, south, west });
	}
	
	private static void addEffectNames(FishDNA[] dnas) {
		for(FishDNA dna: dnas) {
			dna.add("negative", -100, -1, false);
			dna.add("neutral", 0, 0, true);
			dna.add("positive", 1, 100, false);
		}
	}
	
	private static void addFish() {
		cod = new FishCod(0);
		perch = new FishPerch(1);
		tuna = new FishTuna(2);
		nether = new FishNether(3);
		glow = new FishGlow(4);
		blaze = new FishBlaze(5);
		night = new FishNight(6);
		ender = new FishEnder(7);
		dragon = new FishDragon(8);
		minnow = new FishMinnow(9);
		salmon = new FishSalmon(10);
		bass = new FishBass(11);
		tetra = new FishTetra(12);
		catfish = new FishCatfish(13);
		piranha = new FishPiranha(14);
		stingRay = new FishStingRay(15);
		mantaRay = new FishMantaRay(16);
		electricRay = new FishElectricRay(17);
		damsel = new FishDamsel(18);
		angel = new FishAngel(19);
		puffer = new FishPuffer(20);
		squid = new FishSquid(21);
		jelly = new FishJelly(22);
		manOWar = new FishManOWar(23);
		gold = new FishGold(24);
		siamese = new FishSiamese(25);
		koi = new FishKoi(26);
		butterfly = new FishButterfly(27);
		tang = new FishTang(28);
		clown = new FishClown(29);
		blaasop = new FishBlaasop(30);
		stargazer = new FishStargazer(31);
		lamprey = new FishLamprey(32);
		spider = new FishSpider(33);
		undead = new FishUndead(34);
		boneless = new FishBoneless(35);
		angler = new FishAngler(36);
		trout = new FishTrout(37);
		herring = new FishHerring(38);
		minecraft = new FishMinecraft(39);
	}
	
	private static void addMutations() {
		Fishing.mutation.addMutation(spider, night, undead, 8D);
		Fishing.mutation.addMutation(cod, minnow, gold, 20D);
		Fishing.mutation.addMutation(tetra, damsel, angel, 10D);
		Fishing.mutation.addMutation(spider, blaasop, stargazer, 7.5D);
		Fishing.mutation.addMutation(night, blaasop, stargazer, 7.5D);
		Fishing.mutation.addMutation(damsel, gold, perch, 10D);
		Fishing.mutation.addMutation(gold, tetra, salmon, 12D);
		Fishing.mutation.addMutation(gold, tetra, trout, 8D);
		Fishing.mutation.addMutation(angel, squid, butterfly, 10D);
		Fishing.mutation.addMutation(angel, stingRay, butterfly, 8D);
		Fishing.mutation.addMutation(angel, stingRay, mantaRay, 12D);
		Fishing.mutation.addMutation(stargazer, undead, lamprey, 8.5D);
		Fishing.mutation.addMutation(undead, perch, bass, 8.5D);
		Fishing.mutation.addMutation(salmon, butterfly, tang, 8D);
		Fishing.mutation.addMutation(butterfly, mantaRay, puffer, 8D);
		Fishing.mutation.addMutation(nether, puffer, glow, 7.5D);
		Fishing.mutation.addMutation(perch, salmon, tuna, 7.5D);
		Fishing.mutation.addMutation(perch, trout, tuna, 7.5D);
		Fishing.mutation.addMutation(lamprey, puffer, angler, 7.5D);
		Fishing.mutation.addMutation(puffer, squid, jelly, 10D);
		Fishing.mutation.addMutation(undead, glow, blaze, 12D);
		Fishing.mutation.addMutation(trout, glow, herring, 8.5D);
		Fishing.mutation.addMutation(bass, tuna, catfish, 7.5D);
		Fishing.mutation.addMutation(angler, jelly, manOWar, 7.5D);
		Fishing.mutation.addMutation(blaze, night, ender, 7.5D);
		Fishing.mutation.addMutation(lamprey, catfish, piranha, 7D);
		Fishing.mutation.addMutation(herring, mantaRay, electricRay, 6.5D);
		Fishing.mutation.addMutation(catfish, manOWar, siamese, 7.5D);
		Fishing.mutation.addMutation(piranha, electricRay, boneless, 6.5D);
		Fishing.mutation.addMutation(siamese, tang, koi, 6D);
		Fishing.mutation.addMutation(ender, boneless, dragon, 6D);
		Fishing.mutation.addMutation(ender, koi, clown, 5.5D);
		Fishing.mutation.addMutation(clown, dragon, minecraft, 5D);
	}
	
	private static void addRecipe(FishSpecies species) {
		species.temperature = species.setSuitableTemperature();
		species.salinity = species.setSuitableSalinity();
		
		species.addFishProducts();
		ItemStack raw = new ItemStack(Fishery.fishyFood, 1, species.getID());
		ItemStack kelp = Modules.isActive(Modules.worldplus)? new ItemStack(Core.food, 1, FoodMeta.KELP_WRAP): new ItemStack(Item.dyePowder, 1, Dye.GREEN);
		if(species.getFishOilVolume() > 0 && species.getLiquifiedProduct() != null && species.getLiquifiedProductChance() > 0) {
			RecipeHelper.addFishMelting(raw, species.getFishOilVolume(), species.getLiquifiedProduct(), species.getLiquifiedProductChance());
		}
		
		int meal = species.getFishMealSize();
		if(meal > 0) {
			RecipeHelper.addFishSushi(raw, meal);
			RecipeHelper.addFishSoup(raw, meal);
			RecipeHelper.addFishMeal(raw, meal);
		}
		
		
		//Re-Register now that the biomes and salinity have been set
		FishSpecies.species.put(species.getID(), species);
	}
	
	public static void addRecipes() {
		for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
			addRecipe(species.getValue());
		}
	}
}
