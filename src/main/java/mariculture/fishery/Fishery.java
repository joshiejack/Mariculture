package mariculture.fishery;

import static mariculture.core.helpers.RecipeHelper._;
import static mariculture.core.helpers.RecipeHelper.addBlockCasting;
import static mariculture.core.helpers.RecipeHelper.addFishingRodRecipe;
import static mariculture.core.helpers.RecipeHelper.addFluidAlloy;
import static mariculture.core.helpers.RecipeHelper.addFluidAlloyResultItem;
import static mariculture.core.helpers.RecipeHelper.addNuggetCasting;
import static mariculture.core.helpers.RecipeHelper.addShaped;
import static mariculture.core.helpers.RecipeHelper.addShapeless;
import static mariculture.core.helpers.RecipeHelper.addSmelting;
import static mariculture.core.helpers.RecipeHelper.addUpgrade;
import static mariculture.core.helpers.RecipeHelper.addVatItemRecipe;
import static mariculture.core.helpers.RecipeHelper.addVatItemRecipeResultFluid;
import static mariculture.core.lib.ItemLib.autofisher;
import static mariculture.core.lib.ItemLib.baseWood;
import static mariculture.core.lib.ItemLib.blueWool;
import static mariculture.core.lib.ItemLib.bowl;
import static mariculture.core.lib.ItemLib.bread;
import static mariculture.core.lib.ItemLib.calamari;
import static mariculture.core.lib.ItemLib.chest;
import static mariculture.core.lib.ItemLib.clay;
import static mariculture.core.lib.ItemLib.cobblestone;
import static mariculture.core.lib.ItemLib.compass;
import static mariculture.core.lib.ItemLib.cooling;
import static mariculture.core.lib.ItemLib.copperBattery;
import static mariculture.core.lib.ItemLib.diamond;
import static mariculture.core.lib.ItemLib.dirt;
import static mariculture.core.lib.ItemLib.dragonEgg;
import static mariculture.core.lib.ItemLib.dropletAny;
import static mariculture.core.lib.ItemLib.dropletEarth;
import static mariculture.core.lib.ItemLib.dropletEnder;
import static mariculture.core.lib.ItemLib.dropletFlux;
import static mariculture.core.lib.ItemLib.dropletFrozen;
import static mariculture.core.lib.ItemLib.dropletNether;
import static mariculture.core.lib.ItemLib.dropletPoison;
import static mariculture.core.lib.ItemLib.dropletWater;
import static mariculture.core.lib.ItemLib.enderPearl;
import static mariculture.core.lib.ItemLib.endstone;
import static mariculture.core.lib.ItemLib.fish;
import static mariculture.core.lib.ItemLib.fishFeeder;
import static mariculture.core.lib.ItemLib.fishTank;
import static mariculture.core.lib.ItemLib.fishingNet;
import static mariculture.core.lib.ItemLib.grass;
import static mariculture.core.lib.ItemLib.greyClay;
import static mariculture.core.lib.ItemLib.hatchery;
import static mariculture.core.lib.ItemLib.heating;
import static mariculture.core.lib.ItemLib.ice;
import static mariculture.core.lib.ItemLib.incubatorBase;
import static mariculture.core.lib.ItemLib.incubatorTop;
import static mariculture.core.lib.ItemLib.log;
import static mariculture.core.lib.ItemLib.pearls;
import static mariculture.core.lib.ItemLib.planks;
import static mariculture.core.lib.ItemLib.poisonPotato;
import static mariculture.core.lib.ItemLib.polishedLog;
import static mariculture.core.lib.ItemLib.polishedPlank;
import static mariculture.core.lib.ItemLib.polishedStick;
import static mariculture.core.lib.ItemLib.polishedTitanium;
import static mariculture.core.lib.ItemLib.potato;
import static mariculture.core.lib.ItemLib.reeds;
import static mariculture.core.lib.ItemLib.sand;
import static mariculture.core.lib.ItemLib.sifter;
import static mariculture.core.lib.ItemLib.snowball;
import static mariculture.core.lib.ItemLib.stick;
import static mariculture.core.lib.ItemLib.string;
import static mariculture.core.lib.ItemLib.sugar;
import static mariculture.core.lib.ItemLib.thermometer;
import static mariculture.core.lib.ItemLib.titaniumBattery;
import static mariculture.core.lib.ItemLib.titaniumRod;
import static mariculture.core.lib.ItemLib.titaniumSheet;
import static mariculture.core.lib.ItemLib.tnt;
import static mariculture.core.lib.ItemLib.whiteClay;
import static mariculture.core.lib.ItemLib.wicker;
import static mariculture.core.util.Fluids.getFluid;
import static mariculture.core.util.Fluids.getFluidName;
import static mariculture.core.util.Fluids.getFluidStack;

import java.util.Arrays;

import mariculture.Mariculture;
import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RecipeSifter;
import mariculture.api.fishery.RodType;
import mariculture.core.Core;
import mariculture.core.config.FishMechanics;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.items.ItemBattery;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.BottleMeta;
import mariculture.core.lib.BucketMeta;
import mariculture.core.lib.DropletMeta;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.ItemLib;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.core.lib.UpgradeMeta;
import mariculture.fishery.blocks.BlockItemNet;
import mariculture.fishery.blocks.BlockNeonLamp;
import mariculture.fishery.blocks.fluids.BlockBlood;
import mariculture.fishery.blocks.fluids.BlockChlorophyll;
import mariculture.fishery.blocks.fluids.BlockCustard;
import mariculture.fishery.blocks.fluids.BlockEnder;
import mariculture.fishery.blocks.fluids.BlockFishOil;
import mariculture.fishery.blocks.fluids.BlockFlux;
import mariculture.fishery.blocks.fluids.BlockGunpowder;
import mariculture.fishery.blocks.fluids.BlockIce;
import mariculture.fishery.blocks.fluids.BlockMana;
import mariculture.fishery.blocks.fluids.BlockPoison;
import mariculture.fishery.items.ItemBait;
import mariculture.fishery.items.ItemDroplet;
import mariculture.fishery.items.ItemEgg;
import mariculture.fishery.items.ItemFishy;
import mariculture.fishery.items.ItemFluxRod;
import mariculture.fishery.items.ItemRod;
import mariculture.fishery.items.ItemScanner;
import mariculture.fishery.items.ItemTemperatureControl;
import mariculture.fishery.tile.TileAutofisher;
import mariculture.fishery.tile.TileFeeder;
import mariculture.fishery.tile.TileFishTank;
import mariculture.fishery.tile.TileHatchery;
import mariculture.fishery.tile.TileIncubator;
import mariculture.fishery.tile.TileSifter;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class Fishery extends RegistrationModule {
    public static Block lampsOff;
    public static Block lampsOn;

    public static Item fishEggs;
    public static Item bait;
    public static Item rodWood;
    public static Item rodTitanium;
    public static Item rodReed;
    public static Item rodFlux;
    public static Item fishy;
    public static Item net;
    public static Item scanner;
    public static Item tempControl;
    public static Item droplet;

    @Override
    public void registerHandlers() {
        if (Fishing.fishing == null) {
            Fishing.fishing = new FishingHandler();
        }

        if (Fishing.fishHelper == null) {
            Fishing.fishHelper = new FishyHelper();
        }

        Fishing.mutation = new FishMutationHandler();
        Fishing.food = new FishFoodHandler();
        Fishing.sifter = new SifterHandler();
        MinecraftForge.EVENT_BUS.register(new FisheryEventHandler());
        MinecraftForge.EVENT_BUS.register(new BaitListingsHandler());
    }

    @Override
    public void registerItems() {
        bait = new ItemBait().setUnlocalizedName("bait");
        rodReed = new ItemRod(96, 24).setUnlocalizedName("rod.reed");
        rodWood = new ItemRod(320, 10).setUnlocalizedName("rod.wood");
        rodTitanium = new ItemRod(640, 16).setUnlocalizedName("rod.titanium");
        rodFlux = new ItemFluxRod().setUnlocalizedName("rod.flux");
        fishy = new ItemFishy().setUnlocalizedName("fishy").setCreativeTab(MaricultureTab.tabFishery);
        net = new BlockItemNet().setUnlocalizedName("net");
        scanner = new ItemScanner().setUnlocalizedName("scanner");
        fishEggs = new ItemEgg().setUnlocalizedName("eggs.fish");
        tempControl = new ItemTemperatureControl().setUnlocalizedName("temperature.control");
        droplet = new ItemDroplet().setUnlocalizedName("droplet");

        RegistryHelper.registerItems(new Item[] { bait, rodWood, rodReed, rodTitanium, fishy, net, rodFlux, scanner, fishEggs, tempControl, droplet });
    }

    @Override
    public void registerFluids() {
        FluidHelper.addFluid("fish_food", "fishfood", 512, BottleMeta.FISH_FOOD, 12);
        FluidHelper.addFluid("fish_oil", "fishoil", 2000, BottleMeta.FISH_OIL, 100);
        FluidHelper.addFluid("milk", 2000, BottleMeta.MILK, 100);
        FluidHelper.addFluid("custard", 2000, BottleMeta.CUSTARD, 100);
        FluidHelper.addFluid("dirt", 100);
        FluidHelper.addFluid("gunpowder", 125);
        FluidHelper.addFluid("flux", 100);
        FluidHelper.addFluid("ender", 25);
        FluidHelper.addFluid("ice", 125);
        FluidHelper.addFluid("blood", 100);
        FluidHelper.addFluid("mana", 100);
        FluidHelper.addFluid("poison", 250);
        FluidHelper.addFluid("chlorophyll", 500);
        FluidHelper.registerBucket(getFluid("dirt"), 1000, BucketMeta.DIRT);
        FluidHelper.registerBucket(getFluid("custard"), 1000, BucketMeta.CUSTARD);
        FluidHelper.registerBucket(getFluid("fish_oil"), 1000, BucketMeta.FISH_OIL);
        FluidHelper.registerBucket(getFluid("gunpowder"), 1000, BucketMeta.GUNPOWDER);
        FluidHelper.registerBucket(getFluid("flux"), 1000, BucketMeta.FLUX);
        FluidHelper.registerBucket(getFluid("ender"), 1000, BucketMeta.ENDER);
        FluidHelper.registerBucket(getFluid("ice"), 1000, BucketMeta.ICE);
        FluidHelper.registerBucket(getFluid("blood"), 1000, BucketMeta.BLOOD);
        FluidHelper.registerBucket(getFluid("mana"), 1000, BucketMeta.MANA);
        FluidHelper.registerBucket(getFluid("poison"), 1000, BucketMeta.POISON);
        FluidHelper.registerBucket(getFluid("chlorophyll"), 1000, BucketMeta.CHLOROPHYLL);
        FluidHelper.registerVanillaBottle(getFluid("fish_food"), 256, BottleMeta.FISH_FOOD_BASIC);
        FluidHelper.registerVanillaBottle(getFluid("fish_oil"), 1000, BottleMeta.FISH_OIL_BASIC);
        FluidHelper.registerVanillaBottle(getFluid("milk"), 1000, BottleMeta.MILK_BASIC);
        FluidHelper.registerVanillaBottle(getFluid("custard"), 1000, BottleMeta.CUSTARD_BASIC);
        FluidContainerRegistry.registerFluidContainer(getFluidStack("custard", 250), new ItemStack(Core.food, 1, FoodMeta.CUSTARD), new ItemStack(Items.bowl));
        FluidContainerRegistry.registerFluidContainer(getFluidStack("milk", 1000), new ItemStack(Items.milk_bucket), new ItemStack(Items.bucket));
    }

    @Override
    public void registerBlocks() {
        FluidHelper.setBlock(BlockFishOil.class, "fish_oil", "fish.oil");
        FluidHelper.setBlock(BlockCustard.class, "custard");
        FluidHelper.setBlock(BlockGunpowder.class, "gunpowder", "gunpowder.molten");
        FluidHelper.setBlock(BlockFlux.class, "flux", "flux.molten");
        FluidHelper.setBlock(BlockEnder.class, "ender", "ender.molten");
        FluidHelper.setBlock(BlockIce.class, "ice", "ice.molten");
        FluidHelper.setBlock(BlockBlood.class, "blood");
        FluidHelper.setBlock(BlockMana.class, "mana");
        FluidHelper.setBlock(BlockPoison.class, "poison");
        FluidHelper.setBlock(BlockChlorophyll.class, "chlorophyll");
        lampsOff = new BlockNeonLamp(true, "lamp_on_").setBlockName("lamps.off");
        lampsOn = new BlockNeonLamp(false, "lamp_off_").setBlockName("lamps.on");
        RegistryHelper.registerBlocks(new Block[] { lampsOff, lampsOn });
        RegistryHelper.registerTiles(new Class[] { TileAutofisher.class, TileIncubator.class, TileFeeder.class, TileFishTank.class, TileSifter.class, TileHatchery.class });
    }

    @Override
    public void registerOther() {
        RecipeSorter.register("mariculture:caviar", ShapelessFishRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
        registerEntities();
        Fish.init();
        registerRods();
        if (MaricultureTab.tabFishery != null) {
            MaricultureTab.tabFishery.setIcon(new ItemStack(rodWood), true);
        }
    }

    private void registerEntities() {
        EntityRegistry.registerModEntity(EntityBass.class, "BassBomb", EntityIds.BASS, Mariculture.instance, 80, 3, true);
        EntityRegistry.registerModEntity(EntityHook.class, "FishingHook", EntityIds.FISHING, Mariculture.instance, 80, 3, true);
        EntityRegistry.registerModEntity(EntityItemFireImmune.class, "EntityItemImmune", EntityIds.ITEM, Mariculture.instance, 80, 30, true);
    }

    private void registerRods() {
        Fishing.fishing.registerRod(Items.fishing_rod, RodType.DIRE);
        Fishing.fishing.registerRod(rodReed, RodType.OLD);
        Fishing.fishing.registerRod(rodWood, RodType.GOOD);
        Fishing.fishing.registerRod(rodTitanium, RodType.SUPER);
        Fishing.fishing.registerRod(rodFlux, RodType.FLUX);
    }

    @Override
    public void registerRecipes() {
        addBait();
        addDropletRecipes();
        addFishRecipes();
        FishingLoot.add();

        ((ItemRod) rodReed).setRepairMaterial(_(reeds));
        ((ItemRod) rodWood).setRepairMaterial(polishedStick);
        ((ItemRod) rodTitanium).setRepairMaterial(polishedTitanium);
        addFishingRodRecipe(_(rodReed), reeds);
        addFishingRodRecipe(_(rodWood), polishedStick);
        addFishingRodRecipe(_(rodTitanium), polishedTitanium);
        addShaped(ItemBattery.make(_(rodFlux), 0), new Object[] { "  R", " RS", "B S", 'R', rodTitanium, 'S', string, 'B', titaniumBattery });
        addVatItemRecipe("logWood", getFluidName("fish_oil"), 30000, polishedLog, 45);
        addVatItemRecipe("plankWood", getFluidName("fish_oil"), 10000, polishedPlank, 30);
        addVatItemRecipe("stickWood", getFluidName("fish_oil"), 5000, polishedStick, 15);
        addShapeless(_(polishedPlank, 4), new Object[] { polishedLog });
        addShaped(_(polishedStick, 4), new Object[] { "S", "S", 'S', polishedPlank });
        addVatItemRecipe(titaniumRod, getFluidName("fish_oil"), 6500, polishedTitanium, 30);
        addShapeless(thermometer, new Object[] { "fish", compass });
        addBlockCasting(getFluidStack("dirt", 1000), new ItemStack(dirt));
        addShaped(_(fishingNet, 4), new Object[] { "SWS", "WWW", "SWS", 'S', "stickWood", 'W', string });
        addShaped(_(sifter, 2), new Object[] { "PNP", "S S", 'S', "stickWood", 'P', "plankWood", 'N', net });
        addShaped(autofisher, new Object[] { " F ", "RPR", "WBW", 'W', "logWood", 'R', _(rodWood), 'F', "fish", 'B', baseWood, 'P', "plankWood" });
        addShaped(hatchery, new Object[] { "WWW", "WEW", "WWW", 'W', wicker, 'E', _(_(fishEggs), OreDictionary.WILDCARD_VALUE, 1) });
        addShaped(fishFeeder, new Object[] { "WFW", "WCW", "WFW", 'F', "fish", 'W', wicker, 'C', chest });
        addShaped(incubatorTop, new Object[] { "DFD", "CHC", 'F', "fish", 'D', "dyeBrown", 'C', greyClay, 'H', heating });
        addShaped(incubatorBase, new Object[] { "DBD", "CHC", 'C', whiteClay, 'B', copperBattery, 'D', "dyeLightBlue", 'H', heating });
        addShaped(fishTank, new Object[] { "AGA", "GFG", "AGA", 'A', "ingotAluminum", 'G', "blockGlass", 'F', "fish" });
        addShaped(_(tempControl), new Object[] { " H ", "CTC", " H ", 'H', heating, 'C', cooling, 'T', titaniumSheet });
        addVatItemRecipeResultFluid(_(_(sugar), 2), getFluidStack("milk", 1000), getFluidStack("custard", 1000), 15);
        GameRegistry.addRecipe(new ShapelessFishRecipe(new ItemStack(Core.food, 1, FoodMeta.CAVIAR), new ItemStack(fishEggs)));

        if (FishMechanics.EASY_SCANNER) {
            addShaped(_(scanner), new Object[] { "WPW", "WFW", "WBW", 'P', pearls, 'W', dropletAny, 'F', "fish", 'B', copperBattery });
        } else {
            addShaped(_(scanner), new Object[] { "WPE", "NFR", "JBO", 'N', dropletNether, 'P', pearls, 'W', dropletWater, 'R', dropletEarth, 'F', "fish", 'O', dropletEnder, 'E', dropletFrozen, 'B', copperBattery, 'J', dropletPoison });
        }
    }

 

    private void addBait() {
        Fishing.fishing.addBait(new ItemStack(bait, 1, BaitMeta.ANT), 10);
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.ANT), new ItemStack(Blocks.dirt, 1, OreDictionary.WILDCARD_VALUE), 2, 3, 40));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.ANT), new ItemStack(Blocks.grass), 1, 2, 50));

        Fishing.fishing.addBait(new ItemStack(Items.bread), 30);

        Fishing.fishing.addBait(new ItemStack(bait, 1, BaitMeta.HOPPER), 40);
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.grass), 1, 2, 35));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), "treeSapling", 2, 3, 35));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), "treeLeaves", 1, 2, 15));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.tallgrass, 0, 1), 4, 5, 45));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.tallgrass, 0, 2), 2, 3, 40));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.tallgrass, 0, 0), 1, 2, 10));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.yellow_flower, 1, OreDictionary.WILDCARD_VALUE), 2, 5, 20));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.HOPPER), new ItemStack(Blocks.red_flower, 1, OreDictionary.WILDCARD_VALUE), 3, 4, 25));

        Fishing.fishing.addBait(new ItemStack(bait, 1, BaitMeta.MAGGOT), 55);
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Items.rotten_flesh), 1, 2, 60));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Items.beef), 14, 22, 20));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Items.chicken), 6, 14, 30));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), new ItemStack(Items.porkchop), 10, 18, 25));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), ItemLib.zombie, 8, 15, 80));

        Fishing.fishing.addBait(new ItemStack(bait, 1, BaitMeta.BEE), 70);
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.BEE), new ItemStack(Blocks.yellow_flower, 1, OreDictionary.WILDCARD_VALUE), 2, 3, 25));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.BEE), new ItemStack(Blocks.red_flower, 1, OreDictionary.WILDCARD_VALUE), 1, 2, 30));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.BEE), new ItemStack(Blocks.double_plant, 1, OreDictionary.WILDCARD_VALUE), 3, 5, 30));

        Fishing.fishing.addBait(new ItemStack(bait, 1, BaitMeta.WORM), 75);
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.WORM), new ItemStack(Items.apple), 1, 3, 15));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.WORM), new ItemStack(Blocks.dirt, 1, OreDictionary.WILDCARD_VALUE), 2, 3, 10));
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.WORM), new ItemStack(Blocks.grass), 3, 5, 20));

        Fishing.fishing.addBait(new ItemStack(Items.fish, 1, Fish.minnow.getID()), 100);

        Fishing.fishing.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.ANT), Arrays.asList(RodType.DIRE, RodType.OLD, RodType.FLUX));
        Fishing.fishing.addBaitForQuality(new ItemStack(bread), Arrays.asList(RodType.DIRE, RodType.OLD, RodType.FLUX));
        Fishing.fishing.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.HOPPER), Arrays.asList(RodType.OLD, RodType.GOOD, RodType.FLUX));
        Fishing.fishing.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.MAGGOT), Arrays.asList(RodType.GOOD, RodType.FLUX));
        Fishing.fishing.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.WORM), Arrays.asList(RodType.GOOD, RodType.SUPER, RodType.FLUX));
        Fishing.fishing.addBaitForQuality(new ItemStack(bait, 1, BaitMeta.BEE), Arrays.asList(RodType.SUPER, RodType.FLUX));
        Fishing.fishing.addBaitForQuality(new ItemStack(Items.fish, 1, Fish.minnow.getID()), Arrays.asList(RodType.SUPER, RodType.FLUX));
    }

    private void addDropletRecipes() {
        for (int i = 0; i < DropletMeta.COUNT; i++) {
            FluidStack stack = ((ItemDroplet) droplet).getFluidStack(i);
            if (stack != null) FluidContainerRegistry.registerFluidContainer(stack, new ItemStack(droplet, 1, i));
        }

        addNuggetCasting(getFluidStack("ice", 250), _(snowball));
        addBlockCasting(getFluidStack("ice", 1000), _(ice));
        addVatItemRecipe(_(potato), getFluidName("poison"), 1000, _(poisonPotato), 15);
        addVatItemRecipe(pearls, getFluidName("ender"), 250, _(enderPearl), 25);
        addVatItemRecipe(_(_(cobblestone), 0, 8), getFluidName("ender"), 250, _(endstone), 8);
        addVatItemRecipe(_(_(sand), 0, 2), getFluidName("gunpowder"), 250, _(tnt), 10);
        addFluidAlloyResultItem(getFluidStack("dirt", 200), new FluidStack(FluidRegistry.WATER, 1000), _(clay), 15);
        addFluidAlloyResultItem(getFluidStack("dirt", 600), getFluidStack("chlorophyll", 1000), _(grass), 15);
        addFluidAlloy(getFluidStack("flux", 100), new FluidStack(FluidRegistry.WATER, 1000), getFluidStack("magnesium", 72), 10);
    }

    @Override
    public void postInit() {
        super.postInit();
        Fish.addRecipes();
    }

    private void addFishRecipes() {
        //Food to feed a fish in a tank
        Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.ANT), 1);
        Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.MAGGOT), 2);
        Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.HOPPER), 2);
        Fishing.food.addFishFood(new ItemStack(Items.bread), 8);
        Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.WORM), 3);
        Fishing.food.addFishFood(new ItemStack(bait, 1, BaitMeta.BEE), 3);
        Fishing.food.addFishFood(new ItemStack(Core.materials, 1, MaterialsMeta.FISH_MEAL), 12);
        addShapeless(calamari, new Object[] { _(fish, Fish.squid.getID(), 1), bowl });
        addSmelting(new ItemStack(Core.food, 1, FoodMeta.SMOKED_SALMON), _(fish, Fish.salmon.getID(), 1), 0.1F);

        // Cod > Fish Finger
        addShaped(new ItemStack(Core.food, 16, FoodMeta.FISH_FINGER), new Object[] { " B ", "BFB", " B ", 'F', new ItemStack(Items.fish, 1, Fish.cod.getID()), 'B', Items.bread });
        addShapeless(new ItemStack(Core.food, 1, FoodMeta.FISH_N_CUSTARD), new Object[] { new ItemStack(Core.food, 1, FoodMeta.CUSTARD), new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER), new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER), new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER) });

        for (int i = 0; i < 12; i++) {
            addShaped(new ItemStack(lampsOn, 8, i), new Object[] { "PGP", "GFG", "PGP", 'P', new ItemStack(Core.pearls, 1, i), 'G', "blockGlassColorless", 'F', new ItemStack(Items.fish, 1, Fish.tetra.getID()) });
            addShaped(new ItemStack(lampsOn, 4, i), new Object[] { "PGP", "GFG", "PGP", 'P', new ItemStack(Core.pearls, 1, i), 'G', "blockGlassColorless", 'F', dropletFlux });
        }

        addUpgrade(UpgradeMeta.ETERNAL_MALE, new Object[] { "WEW", "FRF", "DWD", 'W', blueWool, 'E', "blockEmerald", 'F', new ItemStack(Items.fish, 1, Fish.dragon.getID()), 'R', dragonEgg, 'D', diamond });
        addUpgrade(UpgradeMeta.ETERNAL_FEMALE, new Object[] { "WEW", "FRF", "DWD", 'W', new ItemStack(Blocks.wool, 1, 6), 'E', "blockEmerald", 'F', new ItemStack(Items.fish, 1, Fish.dragon.getID()), 'R', dragonEgg, 'D', diamond });
        MaricultureHandlers.crucible.addFuel(new ItemStack(Items.fish, 1, Fish.nether.getID()), new FuelInfo(2000, 16, 2400));
    }
}
