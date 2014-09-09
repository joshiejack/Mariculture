package joshie.mariculture.fishery;

import static joshie.mariculture.Mariculture.modid;

import java.util.Map.Entry;

import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.api.fishery.fish.FishDNA;
import joshie.mariculture.api.fishery.fish.FishDNABase;
import joshie.mariculture.api.fishery.fish.FishSpecies;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.helpers.RecipeHelper;
import joshie.mariculture.core.lib.FoodMeta;
import joshie.mariculture.core.lib.MCLib;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.fishery.fish.FishAngel;
import joshie.mariculture.fishery.fish.FishAngler;
import joshie.mariculture.fishery.fish.FishBass;
import joshie.mariculture.fishery.fish.FishBlaasop;
import joshie.mariculture.fishery.fish.FishBlaze;
import joshie.mariculture.fishery.fish.FishBoneless;
import joshie.mariculture.fishery.fish.FishBowfin;
import joshie.mariculture.fishery.fish.FishBrownshroom;
import joshie.mariculture.fishery.fish.FishButterfly;
import joshie.mariculture.fishery.fish.FishCatfish;
import joshie.mariculture.fishery.fish.FishChub;
import joshie.mariculture.fishery.fish.FishClown;
import joshie.mariculture.fishery.fish.FishCod;
import joshie.mariculture.fishery.fish.FishDamsel;
import joshie.mariculture.fishery.fish.FishDragon;
import joshie.mariculture.fishery.fish.FishElectricRay;
import joshie.mariculture.fishery.fish.FishEnder;
import joshie.mariculture.fishery.fish.FishGlow;
import joshie.mariculture.fishery.fish.FishGold;
import joshie.mariculture.fishery.fish.FishHerring;
import joshie.mariculture.fishery.fish.FishJelly;
import joshie.mariculture.fishery.fish.FishKoi;
import joshie.mariculture.fishery.fish.FishLamprey;
import joshie.mariculture.fishery.fish.FishLungfish;
import joshie.mariculture.fishery.fish.FishManOWar;
import joshie.mariculture.fishery.fish.FishMantaRay;
import joshie.mariculture.fishery.fish.FishMinecraft;
import joshie.mariculture.fishery.fish.FishMinnow;
import joshie.mariculture.fishery.fish.FishNether;
import joshie.mariculture.fishery.fish.FishNight;
import joshie.mariculture.fishery.fish.FishPerch;
import joshie.mariculture.fishery.fish.FishPickerel;
import joshie.mariculture.fishery.fish.FishPike;
import joshie.mariculture.fishery.fish.FishPiranha;
import joshie.mariculture.fishery.fish.FishPuffer;
import joshie.mariculture.fishery.fish.FishPupfish;
import joshie.mariculture.fishery.fish.FishRedshroom;
import joshie.mariculture.fishery.fish.FishSalmon;
import joshie.mariculture.fishery.fish.FishSiamese;
import joshie.mariculture.fishery.fish.FishSpider;
import joshie.mariculture.fishery.fish.FishSquid;
import joshie.mariculture.fishery.fish.FishStargazer;
import joshie.mariculture.fishery.fish.FishStickleback;
import joshie.mariculture.fishery.fish.FishStingRay;
import joshie.mariculture.fishery.fish.FishTang;
import joshie.mariculture.fishery.fish.FishTetra;
import joshie.mariculture.fishery.fish.FishTrout;
import joshie.mariculture.fishery.fish.FishTuna;
import joshie.mariculture.fishery.fish.FishUndead;
import joshie.mariculture.fishery.fish.FishWalleye;
import joshie.mariculture.fishery.fish.dna.FishDNAAreaOfEffect;
import joshie.mariculture.fishery.fish.dna.FishDNAFertility;
import joshie.mariculture.fishery.fish.dna.FishDNAFoodUsage;
import joshie.mariculture.fishery.fish.dna.FishDNAGender;
import joshie.mariculture.fishery.fish.dna.FishDNALifespan;
import joshie.mariculture.fishery.fish.dna.FishDNASalinityTolerance;
import joshie.mariculture.fishery.fish.dna.FishDNASpecies;
import joshie.mariculture.fishery.fish.dna.FishDNATemperatureTolerance;
import joshie.mariculture.fishery.fish.dna.FishDNAWaterRequired;
import joshie.mariculture.fishery.fish.dna.FishDNAWorkEthic;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

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
    public static FishDNA salinity;
    public static FishDNA temperature;

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
    //1.2.4 Species
    public static FishSpecies brown; //BrownShroom
    public static FishSpecies red; //RedShroom
    public static FishSpecies pup; //Wolves
    public static FishSpecies chub; //Food
    public static FishSpecies bowfin; //Bows
    public static FishSpecies lung; //Breathing
    public static FishSpecies walleye; //Eyesight
    public static FishSpecies pike; //Weapons
    public static FishSpecies stickleback; //Spikes
    public static FishSpecies pickerel; //Picky Something

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

        //Salinity Tolerance
        salinity = new FishDNASalinityTolerance().setHidden().register();
        salinity.add("intolerant", 0, 0, true);
        salinity.add("tolerant", 1, 1, false);
        salinity.add("hightolerant", 2, 2, false);

        //Temperature Tolerance
        temperature = new FishDNATemperatureTolerance().setHidden().register();
        temperature.add("fussy", 0, 3, true);
        temperature.add("normal", 4, 9, true);
        temperature.add("sltolerant", 10, 13, false); //slightly
        temperature.add("tolerant", 14, 17, false);
        temperature.add("vtolerant", 18, 25, false); //very
        temperature.add("itolerant", 26, 39, false); //incredibly
        temperature.add("etolerant", 40, 100, false); //extremely
    }

    private static void addEffectNames(FishDNA[] dnas) {
        for (FishDNA dna : dnas) {
            dna.add("negative", -100, -1, false);
            dna.add("neutral", 0, 0, true);
            dna.add("positive", 1, 100, false);
        }
    }

    private static void addFish() {
        cod = Fishing.fishHelper.registerFish(modid, FishCod.class, 0);
        salmon = Fishing.fishHelper.registerFish(modid, FishSalmon.class, 1);
        clown = Fishing.fishHelper.registerFish(modid, FishClown.class, 2);
        puffer = Fishing.fishHelper.registerFish(modid, FishPuffer.class, 3);
        glow = Fishing.fishHelper.registerFish(modid, FishGlow.class, 4);
        blaze = Fishing.fishHelper.registerFish(modid, FishBlaze.class, 5);
        night = Fishing.fishHelper.registerFish(modid, FishNight.class, 6);
        ender = Fishing.fishHelper.registerFish(modid, FishEnder.class, 7);
        dragon = Fishing.fishHelper.registerFish(modid, FishDragon.class, 8);
        minnow = Fishing.fishHelper.registerFish(modid, FishMinnow.class, 9);
        perch = Fishing.fishHelper.registerFish(modid, FishPerch.class, 10);
        bass = Fishing.fishHelper.registerFish(modid, FishBass.class, 11);
        tetra = Fishing.fishHelper.registerFish(modid, FishTetra.class, 12);
        catfish = Fishing.fishHelper.registerFish(modid, FishCatfish.class, 13);
        piranha = Fishing.fishHelper.registerFish(modid, FishPiranha.class, 14);
        stingRay = Fishing.fishHelper.registerFish(modid, FishStingRay.class, 15);
        mantaRay = Fishing.fishHelper.registerFish(modid, FishMantaRay.class, 16);
        electricRay = Fishing.fishHelper.registerFish(modid, FishElectricRay.class, 17);
        damsel = Fishing.fishHelper.registerFish(modid, FishDamsel.class, 18);
        angel = Fishing.fishHelper.registerFish(modid, FishAngel.class, 19);
        nether = Fishing.fishHelper.registerFish(modid, FishNether.class, 20);
        squid = Fishing.fishHelper.registerFish(modid, FishSquid.class, 21);
        jelly = Fishing.fishHelper.registerFish(modid, FishJelly.class, 22);
        manOWar = Fishing.fishHelper.registerFish(modid, FishManOWar.class, 23);
        gold = Fishing.fishHelper.registerFish(modid, FishGold.class, 24);
        siamese = Fishing.fishHelper.registerFish(modid, FishSiamese.class, 25);
        koi = Fishing.fishHelper.registerFish(modid, FishKoi.class, 26);
        butterfly = Fishing.fishHelper.registerFish(modid, FishButterfly.class, 27);
        tang = Fishing.fishHelper.registerFish(modid, FishTang.class, 28);
        tuna = Fishing.fishHelper.registerFish(modid, FishTuna.class, 29);
        blaasop = Fishing.fishHelper.registerFish(modid, FishBlaasop.class, 30);
        stargazer = Fishing.fishHelper.registerFish(modid, FishStargazer.class, 31);
        lamprey = Fishing.fishHelper.registerFish(modid, FishLamprey.class, 32);
        spider = Fishing.fishHelper.registerFish(modid, FishSpider.class, 33);
        undead = Fishing.fishHelper.registerFish(modid, FishUndead.class, 34);
        boneless = Fishing.fishHelper.registerFish(modid, FishBoneless.class, 35);
        angler = Fishing.fishHelper.registerFish(modid, FishAngler.class, 36);
        trout = Fishing.fishHelper.registerFish(modid, FishTrout.class, 37);
        herring = Fishing.fishHelper.registerFish(modid, FishHerring.class, 38);
        minecraft = Fishing.fishHelper.registerFish(modid, FishMinecraft.class, 39);
        brown = Fishing.fishHelper.registerFish(modid, FishBrownshroom.class, 40);
        red = Fishing.fishHelper.registerFish(modid, FishRedshroom.class, 41);
        pup = Fishing.fishHelper.registerFish(modid, FishPupfish.class, 42);
        chub = Fishing.fishHelper.registerFish(modid, FishChub.class, 43);
        bowfin = Fishing.fishHelper.registerFish(modid, FishBowfin.class, 44);
        lung = Fishing.fishHelper.registerFish(modid, FishLungfish.class, 45);
        walleye = Fishing.fishHelper.registerFish(modid, FishWalleye.class, 46);
        pike = Fishing.fishHelper.registerFish(modid, FishPike.class, 47);
        stickleback = Fishing.fishHelper.registerFish(modid, FishStickleback.class, 48);
        pickerel = Fishing.fishHelper.registerFish(modid, FishPickerel.class, 49);
    }

    private static void addMutations() {
        Fishing.mutation.addMutation(dragon, clown, minecraft, 5D);
        Fishing.mutation.addMutation(boneless, ender, dragon, 6D);
        Fishing.mutation.addMutation(tang, blaze, clown, 5.5D);
        Fishing.mutation.addMutation(night, koi, ender, 6.5D);
        Fishing.mutation.addMutation(glow, pike, blaze, 6.5D);
        Fishing.mutation.addMutation(manOWar, electricRay, boneless, 6D);
        Fishing.mutation.addMutation(siamese, catfish, koi, 7D);
        Fishing.mutation.addMutation(lung, siamese, pike, 7.5D);
        Fishing.mutation.addMutation(piranha, jelly, undead, 6.75D);
        Fishing.mutation.addMutation(nether, puffer, glow, 8.5D);
        Fishing.mutation.addMutation(stingRay, herring, electricRay, 8D);
        Fishing.mutation.addMutation(angler, pickerel, piranha, 7.75D);
        Fishing.mutation.addMutation(walleye, trout, catfish, 8.5D);
        Fishing.mutation.addMutation(bowfin, salmon, lung, 8.5D);
        Fishing.mutation.addMutation(bowfin, pickerel, siamese, 8.25D);
        Fishing.mutation.addMutation(walleye, pickerel, siamese, 8.25D);
        Fishing.mutation.addMutation(tang, bass, pickerel, 8.5);
        Fishing.mutation.addMutation(perch, chub, walleye, 9D);
        Fishing.mutation.addMutation(brown, stickleback, bowfin, 9D);
        Fishing.mutation.addMutation(lamprey, bass, angler, 8.5D);
        Fishing.mutation.addMutation(stickleback, jelly, puffer, 9.5D);
        Fishing.mutation.addMutation(red, tuna, herring, 9.25D);
        Fishing.mutation.addMutation(stargazer, squid, jelly, 10D);
        Fishing.mutation.addMutation(lamprey, tuna, stickleback, 9.5D);
        Fishing.mutation.addMutation(nether, mantaRay, red, 8D);
        Fishing.mutation.addMutation(night, mantaRay, brown, 8D);
        Fishing.mutation.addMutation(spider, mantaRay, brown, 8D);
        Fishing.mutation.addMutation(mantaRay, butterfly, tang, 8D);
        Fishing.mutation.addMutation(stargazer, pup, chub, 10.5D);
        Fishing.mutation.addMutation(perch, undead, bass, 11D);
        Fishing.mutation.addMutation(trout, undead, bass, 10D);
        Fishing.mutation.addMutation(salmon, undead, bass, 10D);
        Fishing.mutation.addMutation(undead, blaasop, lamprey, 12D);
        Fishing.mutation.addMutation(blaasop, pup, stargazer, 12D);
        Fishing.mutation.addMutation(minnow, pup, perch, 15D);
        Fishing.mutation.addMutation(stingRay, angel, mantaRay, 8D);
        Fishing.mutation.addMutation(angel, cod, tuna, 15D);
        Fishing.mutation.addMutation(angel, gold, butterfly, 12D);
        Fishing.mutation.addMutation(gold, tetra, trout, 15D);
        Fishing.mutation.addMutation(gold, damsel, trout, 12.5D);
        Fishing.mutation.addMutation(gold, tetra, salmon, 12.5D);
        Fishing.mutation.addMutation(gold, damsel, salmon, 15D);
        Fishing.mutation.addMutation(night, spider, undead, 15D);
        Fishing.mutation.addMutation(blaasop, tetra, pup, 17.5D);
        Fishing.mutation.addMutation(tetra, damsel, angel, 20D);
        Fishing.mutation.addMutation(cod, minnow, gold, 25D);
    }

    private static void addRecipe(FishSpecies species) {
        ItemStack raw = species.getRawForm(1);
        ItemStack kelp = Modules.isActive(Modules.worldplus) ? new ItemStack(Core.food, 1, FoodMeta.KELP_WRAP) : MCLib.cactusGreen;
        if (species.getFishOilVolume() > 0 && species.getLiquifiedProduct() != null && species.getLiquifiedProductChance() > 0) {
            RecipeHelper.addFishMelting(raw, species.getFishOilVolume(), species.getLiquifiedProduct(), species.getLiquifiedProductChance());
        }

        int meal = species.getFishMealSize();
        if (meal > 0) {
            RecipeHelper.addFishSushi(raw, meal);
            RecipeHelper.addFishSoup(raw, meal);
            RecipeHelper.addFishMeal(raw, meal);
        }

        species.addFishProducts();
        OreDictionary.registerOre("fish", raw);
    }

    public static void addRecipes() {
        for (Entry<Integer, FishSpecies> species : FishSpecies.species.entrySet()) {
            addRecipe(species.getValue());
        }
    }
}
