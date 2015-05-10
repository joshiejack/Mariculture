package mariculture.fishery;

import static mariculture.Mariculture.modid;

import java.util.Map.Entry;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishDNABase;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.config.FishMechanics;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.MCLib;
import mariculture.core.lib.Modules;
import mariculture.fishery.fish.FishAluminum;
import mariculture.fishery.fish.FishAngel;
import mariculture.fishery.fish.FishAngler;
import mariculture.fishery.fish.FishArdite;
import mariculture.fishery.fish.FishBass;
import mariculture.fishery.fish.FishBlaasop;
import mariculture.fishery.fish.FishBlaze;
import mariculture.fishery.fish.FishBoneless;
import mariculture.fishery.fish.FishBowfin;
import mariculture.fishery.fish.FishBrownshroom;
import mariculture.fishery.fish.FishButterfly;
import mariculture.fishery.fish.FishCatfish;
import mariculture.fishery.fish.FishChub;
import mariculture.fishery.fish.FishClown;
import mariculture.fishery.fish.FishCobalt;
import mariculture.fishery.fish.FishCod;
import mariculture.fishery.fish.FishCopper;
import mariculture.fishery.fish.FishDamsel;
import mariculture.fishery.fish.FishDragon;
import mariculture.fishery.fish.FishElectricRay;
import mariculture.fishery.fish.FishEnder;
import mariculture.fishery.fish.FishGlow;
import mariculture.fishery.fish.FishGold;
import mariculture.fishery.fish.FishGoldMetal;
import mariculture.fishery.fish.FishHerring;
import mariculture.fishery.fish.FishIron;
import mariculture.fishery.fish.FishJelly;
import mariculture.fishery.fish.FishKoi;
import mariculture.fishery.fish.FishLamprey;
import mariculture.fishery.fish.FishLead;
import mariculture.fishery.fish.FishLungfish;
import mariculture.fishery.fish.FishMagnesium;
import mariculture.fishery.fish.FishManOWar;
import mariculture.fishery.fish.FishMantaRay;
import mariculture.fishery.fish.FishMinecraft;
import mariculture.fishery.fish.FishMinnow;
import mariculture.fishery.fish.FishNether;
import mariculture.fishery.fish.FishNickel;
import mariculture.fishery.fish.FishNight;
import mariculture.fishery.fish.FishOsmium;
import mariculture.fishery.fish.FishPerch;
import mariculture.fishery.fish.FishPickerel;
import mariculture.fishery.fish.FishPike;
import mariculture.fishery.fish.FishPiranha;
import mariculture.fishery.fish.FishPlatinum;
import mariculture.fishery.fish.FishPuffer;
import mariculture.fishery.fish.FishPupfish;
import mariculture.fishery.fish.FishRedshroom;
import mariculture.fishery.fish.FishRutile;
import mariculture.fishery.fish.FishSalmon;
import mariculture.fishery.fish.FishSiamese;
import mariculture.fishery.fish.FishSilver;
import mariculture.fishery.fish.FishSpider;
import mariculture.fishery.fish.FishSquid;
import mariculture.fishery.fish.FishStargazer;
import mariculture.fishery.fish.FishStickleback;
import mariculture.fishery.fish.FishStingRay;
import mariculture.fishery.fish.FishTang;
import mariculture.fishery.fish.FishTetra;
import mariculture.fishery.fish.FishTin;
import mariculture.fishery.fish.FishTrout;
import mariculture.fishery.fish.FishTuna;
import mariculture.fishery.fish.FishUndead;
import mariculture.fishery.fish.FishWalleye;
import mariculture.fishery.fish.FishZinc;
import mariculture.fishery.fish.dna.FishDNAAreaOfEffect;
import mariculture.fishery.fish.dna.FishDNAFertility;
import mariculture.fishery.fish.dna.FishDNAFoodUsage;
import mariculture.fishery.fish.dna.FishDNAGender;
import mariculture.fishery.fish.dna.FishDNALifespan;
import mariculture.fishery.fish.dna.FishDNASalinityTolerance;
import mariculture.fishery.fish.dna.FishDNASpecies;
import mariculture.fishery.fish.dna.FishDNATemperatureTolerance;
import mariculture.fishery.fish.dna.FishDNAWaterRequired;
import mariculture.fishery.fish.dna.FishDNAWorkEthic;
import mariculture.fishery.fish.requirements.RequirementHasTag;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.commons.lang3.StringUtils;

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

    public static FishSpecies nether; //Netherwart
    public static FishSpecies glow; //Spawns glowstone, where there was stone
    public static FishSpecies blaze; //Spawns fire and blazes
    public static FishSpecies night; //Strength at Night
    public static FishSpecies ender; //Ender Pearls, Teleport randomly, Spawns Endermen
    public static FishSpecies dragon; //Dragon eggs
    public static FishSpecies minnow; //Great Bait
    public static FishSpecies salmon; //Shed leather
    public static FishSpecies bass; //Causes explosions
    public static FishSpecies tetra; //Neon lamps
    public static FishSpecies catfish; //Spawns ocelot 
    public static FishSpecies piranha; //Atacks Entities and players
    public static FishSpecies cod; //Leather (When melted)
    public static FishSpecies perch; //Spawns bats
    public static FishSpecies tuna; //Add Speed Potion
    public static FishSpecies stingRay; //Poisons those in water
    public static FishSpecies mantaRay; //Regen 2 when eaten, and Heals you when in water 
    public static FishSpecies electricRay; //Produce 10RF/t per connection lots of power
    public static FishSpecies damsel; //Breeds animals
    public static FishSpecies angel; //Teleport stuff in to inventories
    public static FishSpecies puffer; //Nothing
    public static FishSpecies squid; //Ink Sacks
    public static FishSpecies jelly; //Produce slimeballs
    public static FishSpecies manOWar; //Blinds Enemies
    public static FishSpecies goldfish; //Produce gold nuggets when melted
    public static FishSpecies siamese; //Attack entities (not players)
    public static FishSpecies koi; //Spawns ocean chest and vanilla chest loot in the water
    public static FishSpecies butterfly; //Shed feathers
    public static FishSpecies tang; //Produces lapis
    public static FishSpecies clown; //Spawns Cows, Chickens, Sheep, Pig

    //1.2.2 Species
    public static FishSpecies blaasop; //Nothing
    public static FishSpecies stargazer; //Prevent Mob Spawns //TODO:
    public static FishSpecies lamprey; //Withers players, Converts skeletons to wither skeletons //TODO:
    public static FishSpecies spider; //Spawns spiders //TODO:
    public static FishSpecies boneless; //Spawns skeletons/wither skeletons/Speeds up crops /TODO:
    public static FishSpecies undead; //Spawns zombies
    public static FishSpecies minecraft; //Gives XP to player /TODO:
    public static FishSpecies angler; //Fish feeder light, places as 'spotlight lights' /TODO:
    public static FishSpecies trout; //Weapon, Dyes
    public static FishSpecies herring; //Redstone

    //1.2.5 new Species
    public static FishSpecies brown; //BrownShroom, Spawns Mycelium /TODO:
    public static FishSpecies red; //RedShroom, Spawns Mycelium /TODO:
    public static FishSpecies pup; //Spawns Wolves
    public static FishSpecies chub; //Fills Hunger Bar /TODO:
    public static FishSpecies bowfin; //Generates Arrows, Bows
    public static FishSpecies lung; //Applies Water Breathing, Gives it when eaten /TODO:
    public static FishSpecies walleye; //Eyesight, Zooms in like bow /TODO:
    public static FishSpecies pike; //Generates flint /TODO:
    public static FishSpecies stickleback; //Grows Kelp
    public static FishSpecies pickerel; //Harvests Crops /TODO:

    //1.2.5 Metal Species
    public static FishSpecies iron;
    public static FishSpecies gold;
    public static FishSpecies copper;
    public static FishSpecies aluminum;
    public static FishSpecies rutile;
    public static FishSpecies magnesium;

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
        goldfish = Fishing.fishHelper.registerFish(modid, FishGold.class, 24);
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

        //New Species
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

        //Metal species
        iron = Fishing.fishHelper.registerFish(modid, FishIron.class, 50);
        gold = Fishing.fishHelper.registerFish(modid, FishGoldMetal.class, 51);
        copper = Fishing.fishHelper.registerFish(modid, FishCopper.class, 52);
        aluminum = Fishing.fishHelper.registerFish(modid, FishAluminum.class, 53);
        rutile = Fishing.fishHelper.registerFish(modid, FishRutile.class, 54);
        magnesium = Fishing.fishHelper.registerFish(modid, FishMagnesium.class, 55);
    }

    public static FishSpecies silver; //Iron + Magnesium
    public static FishSpecies lead; //Angler + Copper
    public static FishSpecies tin; //Squid + Stargazer
    public static FishSpecies platinum; //Koi + Rutile
    public static FishSpecies nickel; //Magnesium + Angler
    public static FishSpecies cobalt; //Cat + Rutile
    public static FishSpecies ardite; //Glow + Rutile
    public static FishSpecies osmium; //Electric + Rutile
    public static FishSpecies zinc; //Aluminum + Puffer
    public static FishSpecies mana;//Mana = Koi + GoldenEye

    public static FishSpecies registerFish(String ingot, FishSpecies father, FishSpecies mother, double chance, Class clazz, int id) {
        if (OreDictionary.getOres("ingot" + StringUtils.capitalize(ingot)).size() > 0) {
            FishSpecies fish = Fishing.fishHelper.registerFish(modid, clazz, id);
            if (FishMechanics.ENABLE_METAL_FISH) {
                Fishing.mutation.addMutation(father, mother, fish, chance, new RequirementHasTag("block" + StringUtils.capitalize(ingot)));
            }

            return fish;
        }

        return null;
    }

    public static void addOptionalFish() {
        silver = registerFish("silver", magnesium, stickleback, 15D, FishSilver.class, 56);
        lead = registerFish("lead", magnesium, angler, 15D, FishLead.class, 57);
        tin = registerFish("tin", brown, puffer, 15D, FishTin.class, 58);
        platinum = registerFish("platinum", gold, iron, 10D, FishPlatinum.class, 59);
        nickel = registerFish("nickel", copper, goldfish, 15D, FishNickel.class, 60);
        cobalt = registerFish("cobalt", gold, tang, 10D, FishCobalt.class, 61);
        ardite = registerFish("ardite", gold, glow, 10D, FishArdite.class, 62);
        osmium = registerFish("osmium", herring, pickerel, 10D, FishOsmium.class, 63);
        zinc = registerFish("zinc", chub, lung, 15D, FishZinc.class, 64);
    }

    private static void addMutations() {
        Fishing.mutation.addMutation(nether, tetra, angler, 25D);
        Fishing.mutation.addMutation(night, walleye, stickleback, 25D);
        Fishing.mutation.addMutation(stingRay, squid, manOWar, 30D);
        Fishing.mutation.addMutation(cod, blaasop, tuna, 30D);
        Fishing.mutation.addMutation(minnow, goldfish, salmon, 35D);
        Fishing.mutation.addMutation(angler, manOWar, pike, 20D);
        Fishing.mutation.addMutation(stickleback, manOWar, bowfin, 20D);
        Fishing.mutation.addMutation(manOWar, tuna, puffer, 20D);
        Fishing.mutation.addMutation(salmon, tuna, lung, 22D);
        Fishing.mutation.addMutation(pike, stingRay, mantaRay, 18D);
        Fishing.mutation.addMutation(pike, bowfin, piranha, 18D);
        Fishing.mutation.addMutation(puffer, nether, red, 17.5D);
        Fishing.mutation.addMutation(puffer, cod, brown, 17.5D);
        Fishing.mutation.addMutation(puffer, lung, chub, 19D);
        Fishing.mutation.addMutation(tetra, lung, angel, 18D);
        Fishing.mutation.addMutation(night, piranha, spider, 17D);
        Fishing.mutation.addMutation(mantaRay, brown, perch, 17.5D);
        Fishing.mutation.addMutation(piranha, angel, stargazer, 15D);
        Fishing.mutation.addMutation(red, blaasop, bass, 17.5D);
        Fishing.mutation.addMutation(squid, chub, jelly, 17.5D);
        Fishing.mutation.addMutation(chub, salmon, trout, 17D);
        Fishing.mutation.addMutation(angel, mantaRay, butterfly, 16D);
        Fishing.mutation.addMutation(bass, angler, glow, 14D);
        Fishing.mutation.addMutation(stargazer, spider, undead, 15D);
        Fishing.mutation.addMutation(bass, jelly, pup, 15D);
        Fishing.mutation.addMutation(perch, butterfly, siamese, 13.5D);
        Fishing.mutation.addMutation(jelly, trout, catfish, 15D);
        Fishing.mutation.addMutation(glow, minnow, herring, 13D);
        Fishing.mutation.addMutation(undead, stargazer, lamprey, 13.5D);
        Fishing.mutation.addMutation(pup, catfish, pickerel, 12D);
        Fishing.mutation.addMutation(glow, trout, tang, 13.5D);
        Fishing.mutation.addMutation(siamese, butterfly, damsel, 12D);
        Fishing.mutation.addMutation(herring, lamprey, electricRay, 10D);
        Fishing.mutation.addMutation(tang, damsel, clown, 10D);
        Fishing.mutation.addMutation(herring, perch, blaze, 12D);
        Fishing.mutation.addMutation(lamprey, night, ender, 12D);
        Fishing.mutation.addMutation(undead, pickerel, boneless, 11D);
        Fishing.mutation.addMutation(electricRay, clown, minecraft, 10D);
        Fishing.mutation.addMutation(clown, blaze, koi, 8D);
        Fishing.mutation.addMutation(ender, boneless, dragon, 9D);

        if (FishMechanics.ENABLE_METAL_FISH) {
            Fishing.mutation.addMutation(brown, bowfin, copper, 15D, new RequirementHasTag("blockCopper"));
            Fishing.mutation.addMutation(brown, pike, iron, 15D, new RequirementHasTag("blockIron"));
            Fishing.mutation.addMutation(red, lung, magnesium, 15D, new RequirementHasTag("blockMagnesium"));
            Fishing.mutation.addMutation(iron, copper, aluminum, 15D, new RequirementHasTag("blockAluminum"));
            Fishing.mutation.addMutation(aluminum, walleye, gold, 12D, new RequirementHasTag("blockGold"));
            Fishing.mutation.addMutation(gold, magnesium, rutile, 10D, new RequirementHasTag("blockRutile"));
            MinecraftForge.EVENT_BUS.register(new MetalFishEventHandler());
        }
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
