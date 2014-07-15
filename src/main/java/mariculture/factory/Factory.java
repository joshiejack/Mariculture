package mariculture.factory;

import static mariculture.core.helpers.RecipeHelper._;
import static mariculture.core.helpers.RecipeHelper.addMelting;
import static mariculture.core.helpers.RecipeHelper.addShaped;
import static mariculture.core.helpers.RecipeHelper.addShapeless;
import static mariculture.core.lib.ItemLib.autodictionary;
import static mariculture.core.lib.ItemLib.baseIron;
import static mariculture.core.lib.ItemLib.baseWood;
import static mariculture.core.lib.ItemLib.bookAndQuill;
import static mariculture.core.lib.ItemLib.craftingTable;
import static mariculture.core.lib.ItemLib.feather;
import static mariculture.core.lib.ItemLib.filterer;
import static mariculture.core.lib.ItemLib.fish;
import static mariculture.core.lib.ItemLib.fishSorter;
import static mariculture.core.lib.ItemLib.gasTurbine;
import static mariculture.core.lib.ItemLib.geyser;
import static mariculture.core.lib.ItemLib.goldPlastic;
import static mariculture.core.lib.ItemLib.handTurbine;
import static mariculture.core.lib.ItemLib.hopper;
import static mariculture.core.lib.ItemLib.ironAxe;
import static mariculture.core.lib.ItemLib.ironBars;
import static mariculture.core.lib.ItemLib.ironWheel;
import static mariculture.core.lib.ItemLib.life;
import static mariculture.core.lib.ItemLib.mechSponge;
import static mariculture.core.lib.ItemLib.paper;
import static mariculture.core.lib.ItemLib.pearls;
import static mariculture.core.lib.ItemLib.piston;
import static mariculture.core.lib.ItemLib.plan;
import static mariculture.core.lib.ItemLib.plasticLens;
import static mariculture.core.lib.ItemLib.pressureVessel;
import static mariculture.core.lib.ItemLib.pressurisedBucket;
import static mariculture.core.lib.ItemLib.quartzSlab;
import static mariculture.core.lib.ItemLib.sawmill;
import static mariculture.core.lib.ItemLib.sluice;
import static mariculture.core.lib.ItemLib.sluiceAdvanced;
import static mariculture.core.lib.ItemLib.sponge;
import static mariculture.core.lib.ItemLib.stoneSlab;
import static mariculture.core.lib.ItemLib.tank;
import static mariculture.core.lib.ItemLib.titaniumSheet;
import static mariculture.core.lib.ItemLib.transparent;
import static mariculture.core.lib.ItemLib.unpacker;
import static mariculture.core.lib.ItemLib.water;
import static mariculture.core.lib.ItemLib.waterTurbine;
import static mariculture.core.lib.ItemLib.wicker;
import static mariculture.core.lib.ItemLib.wool;
import mariculture.Mariculture;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.handlers.FluidDicHandler;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.lib.EntityIds;
import mariculture.core.lib.Modules.RegistrationModule;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.util.Fluids;
import mariculture.factory.blocks.BlockCustomBlock;
import mariculture.factory.blocks.BlockCustomFence;
import mariculture.factory.blocks.BlockCustomFlooring;
import mariculture.factory.blocks.BlockCustomGate;
import mariculture.factory.blocks.BlockCustomLight;
import mariculture.factory.blocks.BlockCustomPower;
import mariculture.factory.blocks.BlockCustomSlab;
import mariculture.factory.blocks.BlockCustomStairs;
import mariculture.factory.blocks.BlockCustomWall;
import mariculture.factory.items.ItemArmorFLUDD;
import mariculture.factory.items.ItemChalk;
import mariculture.factory.items.ItemFilter;
import mariculture.factory.items.ItemPaintbrush;
import mariculture.factory.items.ItemPlan;
import mariculture.factory.items.ItemRotor;
import mariculture.factory.tile.TileCustom;
import mariculture.factory.tile.TileCustomPowered;
import mariculture.factory.tile.TileDictionaryFluid;
import mariculture.factory.tile.TileDictionaryItem;
import mariculture.factory.tile.TileFLUDDStand;
import mariculture.factory.tile.TileFishSorter;
import mariculture.factory.tile.TileGenerator;
import mariculture.factory.tile.TileGeyser;
import mariculture.factory.tile.TilePressureVessel;
import mariculture.factory.tile.TileSawmill;
import mariculture.factory.tile.TileSluice;
import mariculture.factory.tile.TileSluiceAdvanced;
import mariculture.factory.tile.TileSponge;
import mariculture.factory.tile.TileTurbineGas;
import mariculture.factory.tile.TileTurbineHand;
import mariculture.factory.tile.TileTurbineWater;
import mariculture.factory.tile.TileUnpacker;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import cpw.mods.fml.common.registry.EntityRegistry;

public class Factory extends RegistrationModule {
    public static Block customFlooring;
    public static Block customBlock;
    public static Block customStairs;
    public static Block customSlabs;
    public static Block customSlabsDouble;
    public static Block customFence;
    public static Block customGate;
    public static Block customWall;
    public static Block customLight;
    public static Block customRFBlock;

    public static Item chalk;
    public static Item plans;
    public static Item fludd;
    public static Item paintbrush;
    public static Item filter;
    public static Item turbineAluminum;
    public static Item turbineTitanium;
    public static Item turbineCopper;

    private static ArmorMaterial armorFLUDD = EnumHelper.addArmorMaterial("FLUDD", 0, new int[] { 0, 0, 0, 0 }, 0);

    @Override
    public void registerHandlers() {
        MaricultureHandlers.turbine = new TileTurbineGas();
        MinecraftForge.EVENT_BUS.register(new FactoryEvents());
    }

    @Override
    public void registerBlocks() {
        customFlooring = new BlockCustomFlooring().setStepSound(Block.soundTypePiston).setBlockName("customFlooring");
        customBlock = new BlockCustomBlock().setStepSound(Block.soundTypePiston).setBlockName("customBlock");
        customStairs = new BlockCustomStairs(0).setStepSound(Block.soundTypePiston).setBlockName("customStairs");
        customSlabs = new BlockCustomSlab(false).setStepSound(Block.soundTypePiston).setBlockName("customSlabs");
        customSlabsDouble = new BlockCustomSlab(true).setStepSound(Block.soundTypePiston).setBlockName("customSlabsDouble");
        customFence = new BlockCustomFence().setStepSound(Block.soundTypePiston).setBlockName("customFence");
        customGate = new BlockCustomGate().setStepSound(Block.soundTypePiston).setBlockName("customGate");
        customWall = new BlockCustomWall().setStepSound(Block.soundTypePiston).setBlockName("customWall");
        customLight = new BlockCustomLight().setStepSound(Block.soundTypePiston).setBlockName("customLight").setLightLevel(1.0F);
        customRFBlock = new BlockCustomPower().setStepSound(Block.soundTypePiston).setBlockName("customRFBlock");
        RegistryHelper.registerBlocks(new Block[] { customFlooring, customBlock, customStairs, customSlabs, customFence, customGate, customWall, customLight, customRFBlock, customSlabsDouble });
        RegistryHelper.registerTiles(new Class[] { TileCustom.class, TileCustomPowered.class, TileSawmill.class, TileSluice.class, TileTurbineWater.class, TileFLUDDStand.class, TilePressureVessel.class, 
                TileDictionaryItem.class, TileTurbineGas.class, TileSponge.class, TileTurbineHand.class, TileFishSorter.class, TileGeyser.class, TileDictionaryFluid.class, TileUnpacker.class, 
                TileSluiceAdvanced.class, TileGenerator.class });
    }

    @Override
    public void registerItems() {
        chalk = new ItemChalk(64).setUnlocalizedName("chalk");
        plans = new ItemPlan().setUnlocalizedName("plans");
        fludd = new ItemArmorFLUDD(armorFLUDD, RenderIds.FLUDD, 1).setUnlocalizedName("fludd");
        paintbrush = new ItemPaintbrush(128).setUnlocalizedName("paintbrush");
        filter = new ItemFilter().setUnlocalizedName("filter");
        turbineCopper = new ItemRotor(900, 1).setUnlocalizedName("turbine.copper");
        turbineAluminum = new ItemRotor(3600, 2).setUnlocalizedName("turbine.aluminum");
        turbineTitanium = new ItemRotor(28800, 3).setUnlocalizedName("turbine.titanium");
        RegistryHelper.registerItems(new Item[] { chalk, plans, paintbrush, fludd, filter, turbineCopper, turbineAluminum, turbineTitanium });
    }

    @Override
    public void registerOther() {
        EntityRegistry.registerModEntity(EntityFLUDDSquirt.class, "WaterSquirt", EntityIds.FAKE_SQUIRT, Mariculture.instance, 80, 3, true);
        FluidDicHandler.register("water", "water", 2000);
        FluidDicHandler.register("xp", "xpjuice", 200);
        FluidDicHandler.register("xp", "immibis.liquidxp", 1000);
        FluidDicHandler.register("xp", "mobessence", 666);
    }

    @Override
    public void registerRecipes() {
        addShaped(unpacker, new Object[] { "LLL", "LCL", "LBL", 'L', "logWood", 'B', baseWood, 'C', craftingTable });
        addShaped(sawmill, new Object[] { " A ", "DWD", "IMI", 'A', ironAxe, 'D', "slabWood", 'M', baseWood, 'W', "logWood", 'I', "ingotCopper" });
        addShaped(autodictionary, new Object[] { " B ", "FPF", "IMI", 'F', feather, 'P', pearls, 'M', baseWood, 'B', bookAndQuill, 'I', "ingotCopper" });
        addShaped(mechSponge, new Object[] { " D ", "ATA", "SCS", 'D', fish, 'S', sponge, 'C', baseIron, 'A', water, 'T', "ingotAluminum" });
        addShaped(_(sluice, 4), new Object[] { " H ", "WBW", "IMI", 'H', hopper, 'W', ironWheel, 'M', baseIron, 'B', ironBars, 'I', "ingotAluminum" });
        addShaped(sluiceAdvanced, new Object[] { "TPT", "TST", "TBT", 'T', "ingotTitanium", 'P', pressurisedBucket, 'S', new ItemStack(Core.upgrade, 1, UpgradeMeta.ADVANCED_STORAGE), 'B', sluice });
        addShaped(handTurbine, new Object[] { " T ", "IBI", "SPS", 'T', turbineCopper, 'I', "ingotCopper", 'B', baseWood, 'S', "slabWood", 'P', piston });
        addShaped(waterTurbine, new Object[] { " T ", "IBI", "SPS", 'T', turbineAluminum, 'I', "ingotAluminum", 'B', baseIron, 'S', stoneSlab, 'P', piston });
        addShaped(gasTurbine, new Object[] { " T ", "IBI", "SPS", 'T', turbineTitanium, 'I', "ingotTitanium", 'B', baseIron, 'S', quartzSlab, 'P', piston });
        addShaped(pressureVessel, new Object[] { "WLW", "PTP", "PSP", 'W', ironWheel, 'L', "blockLapis", 'P', titaniumSheet, 'T', tank, 'S', sluice });
        addShaped(fishSorter, new Object[] { "BPY", "GFA", "RCW", 'B', "dyeBlack", 'P', pearls, 'Y', "dyeYellow", 'G', "dyeGreen", 'F', fish, 'A', "dyeCyan", 'R', "dyeRed", 'C', baseWood, 'W', "dyeWhite" });
        addShaped(_(geyser, 16), new Object[] { " W ", " G ", "RCR", 'W', water, 'G', "blockGlass", 'R', "dustRedstone", 'C', baseIron, });
        addShaped(((ItemArmorFLUDD) Factory.fludd).build(), new Object[] { " E ", "PGP", "LUL", 'E', plasticLens, 'P', goldPlastic, 'G', transparent, 'L', tank, 'U', life });
        addShaped(_(chalk), new Object[] { "LLN", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(_(chalk), new Object[] { "L  ", "L  ", "N  ", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(_(chalk), new Object[] { " L ", " L ", " N ", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(_(chalk), new Object[] { " N ", " L ", " L ", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(_(chalk), new Object[] { "N  ", "L  ", "L  ", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(_(chalk), new Object[] { "N  ", " L ", "  L", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(_(chalk), new Object[] { "L  ", " L ", "  N", 'L', "blockLimestone", 'N', "dyeWhite" });
        addMelting(_(chalk), 825, Fluids.getStack(Fluids.quicklime, 2500));
        addShapeless(plan, new Object[] { "dyeBlue", "dyeBlack", paper, "dyeBlue" });
        addShaped(_(filter), new Object[] { "W W", "WNW", " W ", 'W', wicker, 'N', filterer });
        addShaped(_(paintbrush), new Object[] { " WW", " IW", "S  ", 'W', wool, 'I', "blockAluminum", 'S', sawmill });
        addShaped(_(turbineCopper), new Object[] { " I ", "ISI", " I ", 'I', "ingotCopper", 'S', "slabWood" });
        addShaped(_(turbineAluminum), new Object[] { " I ", "ISI", " I ", 'I', "ingotAluminum", 'S', stoneSlab });
        addShaped(_(turbineTitanium), new Object[] { " I ", "ISI", " I ", 'I', "ingotTitanium", 'S', quartzSlab });
        MaricultureHandlers.turbine.add(Fluids.natural_gas);
        MaricultureHandlers.turbine.add("gascraft_naturalgas");
    }
}
