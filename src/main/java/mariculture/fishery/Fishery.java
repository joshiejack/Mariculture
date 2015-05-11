package mariculture.fishery;

import static mariculture.core.helpers.RecipeHelper.add2x2Recipe;
import static mariculture.core.helpers.RecipeHelper.addAlchemy2x2Alternating;
import static mariculture.core.helpers.RecipeHelper.addAlchemy2x2TopBottom;
import static mariculture.core.helpers.RecipeHelper.addAlchemyLoop;
import static mariculture.core.helpers.RecipeHelper.addBlockCasting;
import static mariculture.core.helpers.RecipeHelper.addFishingRodRecipe;
import static mariculture.core.helpers.RecipeHelper.addFluidAlloy;
import static mariculture.core.helpers.RecipeHelper.addNuggetCasting;
import static mariculture.core.helpers.RecipeHelper.addShaped;
import static mariculture.core.helpers.RecipeHelper.addShapeless;
import static mariculture.core.helpers.RecipeHelper.addSmelting;
import static mariculture.core.helpers.RecipeHelper.addUpgrade;
import static mariculture.core.helpers.RecipeHelper.addVatItemRecipe;
import static mariculture.core.helpers.RecipeHelper.addVatItemRecipeResultFluid;
import static mariculture.core.helpers.RecipeHelper.asStack;
import static mariculture.core.lib.MCLib.blockClay;
import static mariculture.core.lib.MCLib.blueWool;
import static mariculture.core.lib.MCLib.bowl;
import static mariculture.core.lib.MCLib.bread;
import static mariculture.core.lib.MCLib.calamari;
import static mariculture.core.lib.MCLib.chest;
import static mariculture.core.lib.MCLib.clay;
import static mariculture.core.lib.MCLib.cobblestone;
import static mariculture.core.lib.MCLib.compass;
import static mariculture.core.lib.MCLib.cooling;
import static mariculture.core.lib.MCLib.diamond;
import static mariculture.core.lib.MCLib.dirt;
import static mariculture.core.lib.MCLib.dragonEgg;
import static mariculture.core.lib.MCLib.dropletAir;
import static mariculture.core.lib.MCLib.dropletAny;
import static mariculture.core.lib.MCLib.dropletAqua;
import static mariculture.core.lib.MCLib.dropletEarth;
import static mariculture.core.lib.MCLib.dropletEnder;
import static mariculture.core.lib.MCLib.dropletFlux;
import static mariculture.core.lib.MCLib.dropletFrozen;
import static mariculture.core.lib.MCLib.dropletNether;
import static mariculture.core.lib.MCLib.dropletPlant;
import static mariculture.core.lib.MCLib.dropletPoison;
import static mariculture.core.lib.MCLib.dropletWater;
import static mariculture.core.lib.MCLib.enderPearl;
import static mariculture.core.lib.MCLib.fish;
import static mariculture.core.lib.MCLib.fishFeeder;
import static mariculture.core.lib.MCLib.fishTank;
import static mariculture.core.lib.MCLib.fishingNet;
import static mariculture.core.lib.MCLib.goldThread;
import static mariculture.core.lib.MCLib.grass;
import static mariculture.core.lib.MCLib.hatchery;
import static mariculture.core.lib.MCLib.heating;
import static mariculture.core.lib.MCLib.ice;
import static mariculture.core.lib.MCLib.leatherCap;
import static mariculture.core.lib.MCLib.mossyCobble;
import static mariculture.core.lib.MCLib.netherrack;
import static mariculture.core.lib.MCLib.packedIce;
import static mariculture.core.lib.MCLib.pearls;
import static mariculture.core.lib.MCLib.poisonPotato;
import static mariculture.core.lib.MCLib.polishedLog;
import static mariculture.core.lib.MCLib.polishedPlank;
import static mariculture.core.lib.MCLib.polishedStick;
import static mariculture.core.lib.MCLib.polishedTitanium;
import static mariculture.core.lib.MCLib.potato;
import static mariculture.core.lib.MCLib.reeds;
import static mariculture.core.lib.MCLib.sand;
import static mariculture.core.lib.MCLib.sifter;
import static mariculture.core.lib.MCLib.snowball;
import static mariculture.core.lib.MCLib.stone;
import static mariculture.core.lib.MCLib.stonebrick;
import static mariculture.core.lib.MCLib.string;
import static mariculture.core.lib.MCLib.sugar;
import static mariculture.core.lib.MCLib.thermometer;
import static mariculture.core.lib.MCLib.titaniumRod;
import static mariculture.core.lib.MCLib.titaniumSheet;
import static mariculture.core.lib.MCLib.tnt;
import static mariculture.core.lib.MCLib.wicker;
import static mariculture.core.lib.UpgradeMeta.AQUASCUM;
import static mariculture.core.lib.UpgradeMeta.FILTER_2;
import static mariculture.core.lib.UpgradeMeta.SALINATOR_2;
import static mariculture.core.lib.UpgradeMeta.ULTIMATE_COOLING;
import static mariculture.core.lib.UpgradeMeta.ULTIMATE_HEATING;
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
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.BottleMeta;
import mariculture.core.lib.BucketMeta;
import mariculture.core.lib.DropletMeta;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.MCLib;
import mariculture.core.lib.MCModInfo;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.RenderIds;
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
import mariculture.fishery.blocks.fluids.BlockWind;
import mariculture.fishery.items.ItemArmorFishingHat;
import mariculture.fishery.items.ItemBait;
import mariculture.fishery.items.ItemDroplet;
import mariculture.fishery.items.ItemEgg;
import mariculture.fishery.items.ItemFishy;
import mariculture.fishery.items.ItemRod;
import mariculture.fishery.items.ItemScanner;
import mariculture.fishery.items.ItemTemperatureControl;
import mariculture.fishery.tile.TileFeeder;
import mariculture.fishery.tile.TileFishTank;
import mariculture.fishery.tile.TileHatchery;
import mariculture.fishery.tile.TileSifter;
import mariculture.lib.helpers.RegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
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
    public static Item fishy;
    public static Item net;
    public static Item scanner;
    public static Item tempControl;
    public static Item droplet;
    public static Item fishinghat;

    private static ArmorMaterial armorFishing = EnumHelper.addArmorMaterial("FISHING", 8, new int[] { 0, 0, 0, 0 }, 0);

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
        rodReed = new ItemRod(MCModInfo.MODPATH, 96, 24).setUnlocalizedName("rod.reed");
        rodWood = new ItemRod(MCModInfo.MODPATH, 320, 10).setUnlocalizedName("rod.wood");
        rodTitanium = new ItemRod(MCModInfo.MODPATH, 640, 16).setUnlocalizedName("rod.titanium");
        fishy = new ItemFishy().setUnlocalizedName("fishy").setCreativeTab(MaricultureTab.tabFishery);
        net = new BlockItemNet().setUnlocalizedName("net");
        scanner = new ItemScanner().setUnlocalizedName("scanner");
        fishEggs = new ItemEgg().setUnlocalizedName("eggs.fish");
        tempControl = new ItemTemperatureControl().setUnlocalizedName("temperature.control");
        droplet = new ItemDroplet().setUnlocalizedName("droplet");
        fishinghat = new ItemArmorFishingHat(armorFishing, RenderIds.FISHING, 0).setUnlocalizedName("fishinghat");
    }

    @Override
    public void registerFluids() {
        FluidHelper.addFluid("fish_food", "fishfood", 512, BottleMeta.FISH_FOOD, 12, BucketMeta.FISH_FOOD);
        FluidHelper.addFluid("fish_oil", "fishoil", 2000, BottleMeta.FISH_OIL, 100, BucketMeta.FISH_OIL);
        FluidHelper.addFluid("milk", 2000, BottleMeta.MILK, 100, -1);
        FluidHelper.addFluid("custard", 2000, BottleMeta.CUSTARD, 100, BucketMeta.CUSTARD);
        FluidHelper.addFluid("dirt", 100, BucketMeta.DIRT);
        FluidHelper.addFluid("gunpowder", 125, BucketMeta.GUNPOWDER);
        FluidHelper.addFluid("flux", 100, BucketMeta.FLUX);
        FluidHelper.addFluid("ender", 25, BucketMeta.ENDER);
        FluidHelper.addFluid("ice", 125, BucketMeta.ICE);
        FluidHelper.addFluid("blood", 100, BucketMeta.BLOOD);
        FluidHelper.addFluid("mana", 100, BucketMeta.MANA);
        FluidHelper.addFluid("poison", 250, BucketMeta.POISON);
        FluidHelper.addFluid("chlorophyll", 500, BucketMeta.CHLOROPHYLL);
        FluidHelper.addFluid("wind", 100, BucketMeta.WIND);
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
        FluidHelper.setBlock(BlockWind.class, "wind");
        lampsOff = new BlockNeonLamp(true, "lamp_on_").setBlockName("lamps.off");
        lampsOn = new BlockNeonLamp(false, "lamp_off_").setBlockName("lamps.on");
        RegistryHelper.registerTiles("Mariculture", new Class[] { TileFeeder.class, TileFishTank.class, TileSifter.class, TileHatchery.class });
    }

    @Override
    public void registerOther() {
        RecipeSorter.register("mariculture:caviar", ShapelessFishRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("mariculture:dnaRest", ShapelessResetFishRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");
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
    }

    @Override
    public void registerRecipes() {
        addBait();
        addDropletRecipes();
        addFishRecipes();
        FishingLoot.add();

        ((ItemRod) rodReed).setRepairMaterial(asStack(reeds));
        ((ItemRod) rodWood).setRepairMaterial(polishedStick);
        ((ItemRod) rodTitanium).setRepairMaterial(polishedTitanium);
        addFishingRodRecipe(asStack(rodReed), reeds);
        addFishingRodRecipe(asStack(rodWood), polishedStick);
        addFishingRodRecipe(asStack(rodTitanium), polishedTitanium);
        addVatItemRecipe(asStack(leatherCap), getFluidName("fish_oil"), 16000, asStack(fishinghat), 25);
        addShapeless(asStack(fishinghat), new Object[] { asStack(fishinghat, OreDictionary.WILDCARD_VALUE) });
        for (int i = 0; i < PearlColor.COUNT; i++) {
            addShaped(ItemArmorFishingHat.getDyed(i), new Object[] { "DDD", "DHD", "DDD", 'D', asStack(Core.pearls, i), 'H', asStack(fishinghat, OreDictionary.WILDCARD_VALUE) });
        }

        addVatItemRecipe("logWood", getFluidName("fish_oil"), 30000, polishedLog, 30);
        addVatItemRecipe("plankWood", getFluidName("fish_oil"), 10000, polishedPlank, 15);
        addVatItemRecipe("stickWood", getFluidName("fish_oil"), 5000, polishedStick, 5);
        addShapeless(asStack(polishedPlank, 4), new Object[] { polishedLog });
        addShaped(asStack(polishedStick, 4), new Object[] { "S", "S", 'S', polishedPlank });
        addVatItemRecipe(titaniumRod, getFluidName("fish_oil"), 6500, polishedTitanium, 20);
        addShapeless(thermometer, new Object[] { "fish", compass });
        addBlockCasting(getFluidStack("dirt", 1000), new ItemStack(dirt));
        addShaped(asStack(fishingNet, 4), new Object[] { "SWS", "WWW", "SWS", 'S', "stickWood", 'W', string });
        addShaped(asStack(sifter, 2), new Object[] { "PNP", "S S", 'S', "stickWood", 'P', "plankWood", 'N', net });
        addShaped(hatchery, new Object[] { "WWW", "WEW", "WWW", 'W', wicker, 'E', asStack(asStack(fishEggs), OreDictionary.WILDCARD_VALUE, 1) });
        addShaped(fishFeeder, new Object[] { "WFW", "WCW", "WFW", 'F', "fish", 'W', wicker, 'C', chest });
        addShaped(fishTank, new Object[] { "AGA", "GFG", "AGA", 'A', "ingotAluminum", 'G', "blockGlass", 'F', "fish" });
        addShaped(asStack(tempControl), new Object[] { " H ", "CTC", " H ", 'H', heating, 'C', cooling, 'T', titaniumSheet });
        if (FishMechanics.EASY_SCANNER) {
            addShaped(asStack(scanner), new Object[] { "WPW", "WFW", "WBW", 'P', pearls, 'W', dropletAny, 'F', "fish", 'B', "dustRedstone" });
        } else {
            addShaped(asStack(scanner), new Object[] { "WPE", "NFR", "JBO", 'N', dropletNether, 'P', pearls, 'W', dropletWater, 'R', dropletEarth, 'F', "fish", 'O', dropletEnder, 'E', dropletFrozen, 'B', "dustRedstone", 'J', dropletPoison });
        }

        if (!MaricultureHandlers.HIGH_TECH_ENABLED) {
            Item upgrade = Core.upgrade;
            addShaped(asStack(upgrade, AQUASCUM), new Object[] { "DFD", "CTH", "DSD", 'D', goldThread, 'F', asStack(upgrade, FILTER_2), 'C', asStack(upgrade, ULTIMATE_COOLING), 'T', asStack(tempControl), 'H', asStack(upgrade, ULTIMATE_HEATING), 'S', asStack(upgrade, SALINATOR_2) });
        }

        addVatItemRecipeResultFluid(asStack(asStack(sugar), 2), getFluidStack("milk", 1000), getFluidStack("custard", 1000), 15);
        GameRegistry.addRecipe(new ShapelessFishRecipe(new ItemStack(Core.food, 1, FoodMeta.CAVIAR), new ItemStack(fishEggs)));
        GameRegistry.addRecipe(new ShapelessResetFishRecipe(new ItemStack(fishy), new ItemStack(fishy)));
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
        Fishing.sifter.addRecipe(new RecipeSifter(new ItemStack(bait, 1, BaitMeta.MAGGOT), MCLib.zombie, 8, 15, 80));

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
            if (stack != null) {
                FluidContainerRegistry.registerFluidContainer(stack, new ItemStack(droplet, 1, i), new ItemStack(droplet, 1, DropletMeta.USELESS));
            }
        }

        addNuggetCasting(getFluidStack("ice", 250), asStack(snowball));
        addBlockCasting(getFluidStack("ice", 1000), asStack(ice));
        addVatItemRecipe(asStack(potato), getFluidName("poison"), 1000, asStack(poisonPotato), 10);
        addVatItemRecipe(asStack(asStack(sand), 0, 2), getFluidName("gunpowder"), 250, asStack(tnt), 10);
        addVatItemRecipe(asStack(ice), getFluidName("ice"), 1000, asStack(packedIce), 10);
        addVatItemRecipe("treeLeaves", getFluidName("dirt"), 800, asStack(dirt, 2), 10);
        addVatItemRecipe("sand", getFluidName("hp_water"), 800, asStack(clay), 10);
        addVatItemRecipe("cobblestone", getFluidName("chlorophyll"), 4000, asStack(mossyCobble), 10);
        addVatItemRecipe(asStack(stonebrick), getFluidName("chlorophyll"), 4000, asStack(stonebrick, 1), 10);
        addVatItemRecipe(asStack(stonebrick), getFluidName("lava"), 800, asStack(stonebrick, 2), 10);
        addFluidAlloy(getFluidStack("flux", 100), new FluidStack(FluidRegistry.WATER, 1000), getFluidStack("magnesium", 72), 10);
        
        if (FishMechanics.ADD_ALCHEMY_RECIPES) {
            addAlchemy2x2Alternating(asStack(stone), dropletNether, dropletEarth);
            addAlchemy2x2Alternating(asStack(cobblestone), dropletEarth, dropletAir);
            addAlchemy2x2Alternating(asStack(sand), dropletEarth, dropletWater);
            addAlchemy2x2Alternating(asStack(blockClay), dropletEarth, dropletAqua);
            addAlchemy2x2TopBottom(asStack(grass), dropletPlant, dropletEarth);
            addAlchemyLoop(asStack(enderPearl), dropletEnder);
            add2x2Recipe(asStack(netherrack), dropletNether);
        }
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
        addShapeless(calamari, new Object[] { asStack(fish, Fish.squid.getID(), 1), bowl });
        addSmelting(new ItemStack(Core.food, 1, FoodMeta.SMOKED_SALMON), asStack(fish, Fish.salmon.getID(), 1), 0.1F);

        // Cod > Fish Finger
        addShaped(new ItemStack(Core.food, 16, FoodMeta.FISH_FINGER), new Object[] { " B ", "BFB", " B ", 'F', new ItemStack(Items.fish, 1, Fish.cod.getID()), 'B', Items.bread });
        addShapeless(new ItemStack(Core.food, 1, FoodMeta.FISH_N_CUSTARD), new Object[] { new ItemStack(Core.food, 1, FoodMeta.CUSTARD), new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER), new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER), new ItemStack(Core.food, 1, FoodMeta.FISH_FINGER) });

        //Mushroom Fish > Mushroom Stew
        addShapeless(new ItemStack(Items.mushroom_stew), new Object[] { new ItemStack(Items.fish, 1, Fish.brown.getID()), new ItemStack(Items.fish, 1, Fish.red.getID()), Items.bowl });
        
        for (int i = 0; i < 12; i++) {
            addShaped(new ItemStack(lampsOn, 8, i), new Object[] { "PGP", "GFG", "PGP", 'P', new ItemStack(Core.pearls, 1, i), 'G', "blockGlassColorless", 'F', new ItemStack(Items.fish, 1, Fish.tetra.getID()) });
            addShaped(new ItemStack(lampsOn, 4, i), new Object[] { "PGP", "GFG", "PGP", 'P', new ItemStack(Core.pearls, 1, i), 'G', "blockGlassColorless", 'F', dropletFlux });
        }

        addUpgrade(UpgradeMeta.ETERNAL_MALE, new Object[] { "WEW", "FRF", "DWD", 'W', blueWool, 'E', "blockEmerald", 'F', new ItemStack(Items.fish, 1, Fish.dragon.getID()), 'R', dragonEgg, 'D', diamond });
        addUpgrade(UpgradeMeta.ETERNAL_FEMALE, new Object[] { "WEW", "FRF", "DWD", 'W', new ItemStack(Blocks.wool, 1, 6), 'E', "blockEmerald", 'F', new ItemStack(Items.fish, 1, Fish.dragon.getID()), 'R', dragonEgg, 'D', diamond });
        MaricultureHandlers.crucible.addFuel(new ItemStack(Items.fish, 1, Fish.nether.getID()), new FuelInfo(2000, 256, 2400));
    }
}
