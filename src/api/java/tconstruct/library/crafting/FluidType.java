package tconstruct.library.crafting;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.*;
import tconstruct.common.TContent;

public enum FluidType
{
    /** Vanilla Water Smelting **/
    Water(Blocks.snow.blockID, 0, 20, FluidRegistry.getFluid("water"), false),
    /** Iron Smelting **/
    Iron(Blocks.blockIron.blockID, 0, 600, TContent.moltenIronFluid, true),
    /** Gold  Smelting **/
    Gold(Blocks.blockGold.blockID, 0, 400, TContent.moltenGoldFluid, false),
    /** Tin  Smelting **/
    Tin(TContent.metalBlocks.blockID, 5, 400, TContent.moltenTinFluid, false),
    /** Copper  Smelting **/
    Copper(TContent.metalBlocks.blockID, 3, 550, TContent.moltenCopperFluid, true),
    /** Aluminum Smelting **/
    Aluminum(TContent.metalBlocks.blockID, 6, 350, TContent.moltenAluminumFluid, false),
    /** Natural Aluminum Smelting **/
    NaturalAluminum(TContent.oreSlag.blockID, 6, 350, TContent.moltenAluminumFluid, false),
    /** Cobalt Smelting **/
    Cobalt(TContent.metalBlocks.blockID, 0, 650, TContent.moltenCobaltFluid, true),
    /** Ardite Smelting **/
    Ardite(TContent.metalBlocks.blockID, 1, 650, TContent.moltenArditeFluid, true),
    /** AluminumBrass Smelting **/
    AluminumBrass(TContent.metalBlocks.blockID, 7, 350, TContent.moltenAlubrassFluid, false),
    /** Alumite Smelting **/
    Alumite(TContent.metalBlocks.blockID, 8, 800, TContent.moltenAlumiteFluid, true),
    /** Manyullyn Smelting **/
    Manyullyn(TContent.metalBlocks.blockID, 2, 750, TContent.moltenManyullynFluid, true),
    /** Bronze Smelting **/
    Bronze(TContent.metalBlocks.blockID, 4, 500, TContent.moltenBronzeFluid, true),
    /** Steel Smelting **/
    Steel(TContent.metalBlocks.blockID, 9, 700, TContent.moltenSteelFluid, true),
    /** Nickel Smelting **/
    Nickel(TContent.metalBlocks.blockID, 0, 400, TContent.moltenNickelFluid, false),
    /** Lead Smelting **/
    Lead(TContent.metalBlocks.blockID, 0, 400, TContent.moltenLeadFluid, false),
    /** Silver Smelting **/
    Silver(TContent.metalBlocks.blockID, 0, 400, TContent.moltenSilverFluid, false),
    /** Platinum Smelting **/
    Platinum(TContent.metalBlocks.blockID, 0, 400, TContent.moltenShinyFluid, false),
    /** Invar Smelting **/
    Invar(TContent.metalBlocks.blockID, 0, 400, TContent.moltenInvarFluid, false),
    /** Electrum Smelting **/
    Electrum(TContent.metalBlocks.blockID, 0, 400, TContent.moltenElectrumFluid, false),
    /** Obsidian Smelting **/
    Obsidian(Blocks.obsidian.blockID, 0, 750, TContent.moltenObsidianFluid, true),
    /** Ender Smelting **/
    Ender(TContent.metalBlocks.blockID, 10, 500, TContent.moltenEnderFluid, false),
    /** Glass Smelting **/
    Glass(Blocks.sand.blockID, 0, 625, TContent.moltenGlassFluid, false),
    /** Stone Smelting **/
    Stone(Blocks.stone.blockID, 0, 800, TContent.moltenStoneFluid, true),
    /** Emerald Smelting **/
    Emerald(Blocks.oreEmerald.blockID, 0, 575, TContent.moltenEmeraldFluid, false),
    /** Slime Smelting **/
    Slime(TContent.slimeGel.blockID, 0, 250, TContent.blueSlimeFluid, false),
    /** Pigiron Smelting **/
    PigIron(TContent.meatBlocks.blockID, 0, 610, TContent.pigIronFluid, true),
    /** Glue Smelting **/
    Glue(TContent.glueBlocks.blockID, 0, 125, TContent.glueFluid, false);

    public final int renderBlockID;
    public final int renderMeta;
    public final int baseTemperature;
    public final Fluid fluid;
    public final boolean isToolpart;

    FluidType(int blockID, int meta, int baseTemperature, Fluid fluid, boolean isToolpart)
    {
        this.renderBlockID = blockID;
        this.renderMeta = meta;
        this.baseTemperature = baseTemperature;
        this.fluid = fluid;
        this.isToolpart = isToolpart;
    }

    public static FluidType getFluidType (Fluid searchedFluid)
    {
        for (FluidType ft : values())
        {
            if (ft.fluid.getBlockID() == searchedFluid.getBlockID())
                return ft;
        }
        return null;
    }

    public static int getTemperatureByFluid (Fluid searchedFluid)
    {
        for (FluidType ft : values())
        {
            if (ft.fluid.getBlockID() == searchedFluid.getBlockID())
                return ft.baseTemperature;
        }
        return 800;
    }
}