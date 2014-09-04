package joshie.mariculture.factory;

import static joshie.mariculture.core.helpers.RecipeHelper.addMelting;
import static joshie.mariculture.core.helpers.RecipeHelper.addShaped;
import static joshie.mariculture.core.helpers.RecipeHelper.addShapeless;
import static joshie.mariculture.core.helpers.RecipeHelper.asStack;
import static joshie.mariculture.core.lib.ItemLib.autodictionary;
import static joshie.mariculture.core.lib.ItemLib.baseIron;
import static joshie.mariculture.core.lib.ItemLib.baseWood;
import static joshie.mariculture.core.lib.ItemLib.bookAndQuill;
import static joshie.mariculture.core.lib.ItemLib.comparator;
import static joshie.mariculture.core.lib.ItemLib.copperBattery;
import static joshie.mariculture.core.lib.ItemLib.craftingTable;
import static joshie.mariculture.core.lib.ItemLib.feather;
import static joshie.mariculture.core.lib.ItemLib.filterer;
import static joshie.mariculture.core.lib.ItemLib.fishSorter;
import static joshie.mariculture.core.lib.ItemLib.generator;
import static joshie.mariculture.core.lib.ItemLib.geyser;
import static joshie.mariculture.core.lib.ItemLib.goldPlastic;
import static joshie.mariculture.core.lib.ItemLib.hopper;
import static joshie.mariculture.core.lib.ItemLib.ironAxe;
import static joshie.mariculture.core.lib.ItemLib.ironBars;
import static joshie.mariculture.core.lib.ItemLib.ironWheel;
import static joshie.mariculture.core.lib.ItemLib.life;
import static joshie.mariculture.core.lib.ItemLib.mechSponge;
import static joshie.mariculture.core.lib.ItemLib.paper;
import static joshie.mariculture.core.lib.ItemLib.pearls;
import static joshie.mariculture.core.lib.ItemLib.plan;
import static joshie.mariculture.core.lib.ItemLib.plasticLens;
import static joshie.mariculture.core.lib.ItemLib.pressureVessel;
import static joshie.mariculture.core.lib.ItemLib.pressurisedBucket;
import static joshie.mariculture.core.lib.ItemLib.quartzSlab;
import static joshie.mariculture.core.lib.ItemLib.sawmill;
import static joshie.mariculture.core.lib.ItemLib.sluice;
import static joshie.mariculture.core.lib.ItemLib.sluiceAdvanced;
import static joshie.mariculture.core.lib.ItemLib.sponge;
import static joshie.mariculture.core.lib.ItemLib.stoneSlab;
import static joshie.mariculture.core.lib.ItemLib.tank;
import static joshie.mariculture.core.lib.ItemLib.titaniumSheet;
import static joshie.mariculture.core.lib.ItemLib.transparent;
import static joshie.mariculture.core.lib.ItemLib.unpacker;
import static joshie.mariculture.core.lib.ItemLib.water;
import static joshie.mariculture.core.lib.ItemLib.wicker;
import static joshie.mariculture.core.lib.ItemLib.wool;
import joshie.lib.helpers.RegistryHelper;
import joshie.mariculture.Mariculture;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.handlers.FluidDicHandler;
import joshie.mariculture.core.lib.EntityIds;
import joshie.mariculture.core.lib.MachineRenderedMeta;
import joshie.mariculture.core.lib.Modules.RegistrationModule;
import joshie.mariculture.core.lib.RenderIds;
import joshie.mariculture.core.lib.UpgradeMeta;
import joshie.mariculture.core.util.Fluids;
import joshie.mariculture.factory.blocks.BlockCustomBlock;
import joshie.mariculture.factory.blocks.BlockCustomFence;
import joshie.mariculture.factory.blocks.BlockCustomFlooring;
import joshie.mariculture.factory.blocks.BlockCustomGate;
import joshie.mariculture.factory.blocks.BlockCustomLight;
import joshie.mariculture.factory.blocks.BlockCustomPower;
import joshie.mariculture.factory.blocks.BlockCustomSlab;
import joshie.mariculture.factory.blocks.BlockCustomStairs;
import joshie.mariculture.factory.blocks.BlockCustomWall;
import joshie.mariculture.factory.items.ItemChalk;
import joshie.mariculture.factory.items.ItemFilter;
import joshie.mariculture.factory.items.ItemPaintbrush;
import joshie.mariculture.factory.items.ItemPlan;
import joshie.mariculture.factory.items.ItemRotor;
import joshie.mariculture.factory.tile.TileCustom;
import joshie.mariculture.factory.tile.TileCustomPowered;
import joshie.mariculture.factory.tile.TileDictionaryFluid;
import joshie.mariculture.factory.tile.TileDictionaryItem;
import joshie.mariculture.factory.tile.TileFLUDDStand;
import joshie.mariculture.factory.tile.TileFishSorter;
import joshie.mariculture.factory.tile.TileGenerator;
import joshie.mariculture.factory.tile.TileGeyser;
import joshie.mariculture.factory.tile.TilePressureVessel;
import joshie.mariculture.factory.tile.TileRotor;
import joshie.mariculture.factory.tile.TileRotorAluminum;
import joshie.mariculture.factory.tile.TileRotorCopper;
import joshie.mariculture.factory.tile.TileRotorTitanium;
import joshie.mariculture.factory.tile.TileSawmill;
import joshie.mariculture.factory.tile.TileSluice;
import joshie.mariculture.factory.tile.TileSluiceAdvanced;
import joshie.mariculture.factory.tile.TileSponge;
import joshie.mariculture.factory.tile.TileUnpacker;
import joshie.maritech.items.ItemFLUDD;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
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
        RegistryHelper.registerBlocks(new Block[] { customStairs, customSlabs, customFence, customGate, customWall, customSlabsDouble });
        RegistryHelper.registerTiles("Mariculture", TileCustom.class, TileCustomPowered.class, TileSawmill.class, TileSluice.class, TileFLUDDStand.class, TilePressureVessel.class, TileDictionaryItem.class, TileSponge.class, TileFishSorter.class, TileGeyser.class, TileDictionaryFluid.class, TileUnpacker.class, TileSluiceAdvanced.class, TileGenerator.class, TileRotor.class, TileRotorCopper.class, TileRotorAluminum.class, TileRotorTitanium.class);
    }

    @Override
    public void registerItems() {
        chalk = new ItemChalk(64).setUnlocalizedName("chalk");
        plans = new ItemPlan().setUnlocalizedName("plans");
        fludd = new ItemFLUDD(armorFLUDD, RenderIds.FLUDD, 1).setUnlocalizedName("fludd");
        paintbrush = new ItemPaintbrush(128).setUnlocalizedName("paintbrush");
        filter = new ItemFilter().setUnlocalizedName("filter");
        turbineCopper = new ItemRotor(900, MachineRenderedMeta.ROTOR_COPPER).setUnlocalizedName("turbine.copper");
        turbineAluminum = new ItemRotor(3600, MachineRenderedMeta.ROTOR_ALUMINUM).setUnlocalizedName("turbine.aluminum");
        turbineTitanium = new ItemRotor(28800, MachineRenderedMeta.ROTOR_TITANIUM).setUnlocalizedName("turbine.titanium");
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
        addShaped(generator, new Object[] { " B ", "CMC", "RIR", 'B', copperBattery, 'C', comparator, 'M', "ingotMagnesium", 'R', "dustRedstone", 'I', baseIron });
        addShaped(unpacker, new Object[] { "LLL", "LCL", "LBL", 'L', "logWood", 'B', baseWood, 'C', craftingTable });
        addShaped(sawmill, new Object[] { " A ", "DWD", "IMI", 'A', ironAxe, 'D', "slabWood", 'M', baseWood, 'W', "logWood", 'I', "ingotCopper" });
        addShaped(autodictionary, new Object[] { " B ", "FPF", "IMI", 'F', feather, 'P', pearls, 'M', baseWood, 'B', bookAndQuill, 'I', "ingotCopper" });
        addShaped(mechSponge, new Object[] { " D ", "ATA", "SCS", 'D', "fish", 'S', sponge, 'C', baseIron, 'A', water, 'T', "ingotAluminum" });
        addShaped(asStack(sluice, 4), new Object[] { " H ", "WBW", "IMI", 'H', hopper, 'W', ironWheel, 'M', baseIron, 'B', ironBars, 'I', "ingotAluminum" });
        addShaped(sluiceAdvanced, new Object[] { "TPT", "TST", "TBT", 'T', "ingotTitanium", 'P', pressurisedBucket, 'S', asStack(Core.upgrade, UpgradeMeta.ADVANCED_STORAGE), 'B', sluice });
        addShaped(pressureVessel, new Object[] { "WLW", "PTP", "PSP", 'W', ironWheel, 'L', "blockLapis", 'P', titaniumSheet, 'T', tank, 'S', sluice });
        addShaped(fishSorter, new Object[] { "BPY", "GFA", "RCW", 'B', "dyeBlack", 'P', pearls, 'Y', "dyeYellow", 'G', "dyeGreen", 'F', "fish", 'A', "dyeCyan", 'R', "dyeRed", 'C', baseWood, 'W', "dyeWhite" });
        addShaped(asStack(geyser, 16), new Object[] { " W ", " G ", "RCR", 'W', water, 'G', "blockGlass", 'R', "dustRedstone", 'C', baseIron, });
        addShaped(((ItemFLUDD) Factory.fludd).build(0), new Object[] { " E ", "PGP", "LUL", 'E', plasticLens, 'P', goldPlastic, 'G', transparent, 'L', tank, 'U', life });
        addShaped(asStack(chalk), new Object[] { "LLN", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(asStack(chalk), new Object[] { "L  ", "L  ", "N  ", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(asStack(chalk), new Object[] { " L ", " L ", " N ", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(asStack(chalk), new Object[] { " N ", " L ", " L ", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(asStack(chalk), new Object[] { "N  ", "L  ", "L  ", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(asStack(chalk), new Object[] { "N  ", " L ", "  L", 'L', "blockLimestone", 'N', "dyeWhite" });
        addShaped(asStack(chalk), new Object[] { "L  ", " L ", "  N", 'L', "blockLimestone", 'N', "dyeWhite" });
        addMelting(asStack(chalk), 825, Fluids.getFluidStack("quicklime", 2500));
        addShapeless(plan, new Object[] { "dyeBlue", "dyeBlack", paper, "dyeBlue" });
        addShaped(asStack(filter), new Object[] { "W W", "WNW", " W ", 'W', wicker, 'N', filterer });
        addShaped(asStack(paintbrush), new Object[] { " WW", " IW", "S  ", 'W', wool, 'I', "blockAluminum", 'S', sawmill });
        addShaped(asStack(turbineCopper), new Object[] { " I ", "ISI", " I ", 'I', "ingotCopper", 'S', "slabWood" });
        addShaped(asStack(turbineAluminum), new Object[] { " I ", "ISI", " I ", 'I', "ingotAluminum", 'S', stoneSlab });
        addShaped(asStack(turbineTitanium), new Object[] { " I ", "ISI", " I ", 'I', "ingotTitanium", 'S', quartzSlab });
    }
}
