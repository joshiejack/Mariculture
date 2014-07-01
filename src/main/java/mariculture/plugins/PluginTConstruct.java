package mariculture.plugins;

import java.util.ArrayList;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCrucible;
import mariculture.core.Core;
import mariculture.core.handlers.LogHandler;
import mariculture.core.lib.LimestoneMeta;
import mariculture.core.lib.MetalMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.RockMeta;
import mariculture.core.util.Fluids;
import mariculture.plugins.Plugins.Plugin;
import mariculture.plugins.tconstruct.ModPearl;
import mariculture.plugins.tconstruct.TitaniumTools;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;

import tconstruct.library.TConstructRegistry;
import tconstruct.library.client.TConstructClientRegistry;
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.Smeltery;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.tools.HarvestTool;
import tconstruct.library.tools.ToolCore;

public class PluginTConstruct extends Plugin {
    @Override
    public void preInit() {
        try {
            Fluids.instance.addFluid("aluminum.molten", FluidType.Aluminum.fluid);
            Fluids.instance.addFluid("bronze.molten", FluidType.Bronze.fluid);
            Fluids.instance.addFluid("copper.molten", FluidType.Copper.fluid);
            Fluids.instance.addFluid("glass.molten", FluidType.Glass.fluid);
            Fluids.instance.addFluid("gold.molten", FluidType.Gold.fluid);
            Fluids.instance.addFluid("iron.molten", FluidType.Iron.fluid);
            Fluids.instance.addFluid("lead.molten", FluidType.Lead.fluid);
            Fluids.instance.addFluid("nickel.molten", FluidType.Nickel.fluid);
            Fluids.instance.addFluid("silver.molten", FluidType.Silver.fluid);
            Fluids.instance.addFluid("steel.molten", FluidType.Steel.fluid);
            Fluids.instance.addFluid("tin.molten", FluidType.Tin.fluid);
            Fluids.instance.addFluid("electrum.molten", FluidType.Electrum.fluid);
            Fluids.instance.addFluid("cobalt.molten", FluidType.Cobalt.fluid);
        } catch (Exception e) {
            LogHandler.log(Level.INFO, "Mariculture failed to sync up with TiC Fluids");
        }

        TitaniumTools.preInit();
    }

    @Override
    public void init() {
        return;
    }

    @Override
    public void postInit() {
        TitaniumTools.postInit();
        addAlloy();
        addModifiers();
        addMelting();
    }

    public static void addMelting() {
        // Melt Down Titanium
        PluginTConstruct.addMelting(Core.limestone, LimestoneMeta.RAW, "blockLimestone", Fluids.getStack(Fluids.quicklime, 900), 250);
        PluginTConstruct.addMelting(Core.rocks, RockMeta.RUTILE, "oreRutile", Fluids.getStack(Fluids.rutile, MetalRates.ORE), 800);
        PluginTConstruct.addMelting(Core.metals, MetalMeta.RUTILE_BLOCK, "dustRutile", Fluids.getStack(Fluids.rutile, MetalRates.INGOT), 800);
        PluginTConstruct.addMelting(Core.metals, MetalMeta.RUTILE_BLOCK, "ingotRutile", Fluids.getStack(Fluids.rutile, MetalRates.INGOT), 800);
        PluginTConstruct.addMelting(Core.metals, MetalMeta.RUTILE_BLOCK, "blockRutile", Fluids.getStack(Fluids.rutile, MetalRates.BLOCK), 800);
        PluginTConstruct.addMelting(Core.metals, MetalMeta.RUTILE_BLOCK, "nuggetRutile", Fluids.getStack(Fluids.rutile, MetalRates.NUGGET), 800);
        PluginTConstruct.addMelting(Core.metals, MetalMeta.TITANIUM_BLOCK, "dustTitanium", Fluids.getStack(Fluids.titanium, MetalRates.INGOT), 800);
        PluginTConstruct.addMelting(Core.metals, MetalMeta.TITANIUM_BLOCK, "ingotTitanium", Fluids.getStack(Fluids.titanium, MetalRates.INGOT), 800);
        PluginTConstruct.addMelting(Core.metals, MetalMeta.TITANIUM_BLOCK, "blockTitanium", Fluids.getStack(Fluids.titanium, MetalRates.BLOCK), 800);
        PluginTConstruct.addMelting(Core.metals, MetalMeta.TITANIUM_BLOCK, "nuggetTitanium", Fluids.getStack(Fluids.titanium, MetalRates.NUGGET), 800);
        // >> Form Ingot and Block
        PluginTConstruct.addCasting("ingotRutile", Fluids.getStack(Fluids.rutile, MetalRates.INGOT), 100);
        PluginTConstruct.addCasting("ingotTitanium", Fluids.getStack(Fluids.titanium, MetalRates.INGOT), 100);
        PluginTConstruct.addBlockCasting("blockTitanium", Fluids.getStack(Fluids.titanium, MetalRates.BLOCK), 100);
        // Melt Down Magnesium
        PluginTConstruct.addMelting(Core.rocks, RockMeta.BAUXITE, "oreMagnesium", Fluids.getStack(Fluids.magnesium, MetalRates.ORE), 300);
        PluginTConstruct.addMelting(Core.metals, MetalMeta.MAGNESIUM_BLOCK, "dustMagnesium", Fluids.getStack(Fluids.magnesium, MetalRates.INGOT), 300);
        PluginTConstruct.addMelting(Core.metals, MetalMeta.MAGNESIUM_BLOCK, "ingotMagnesium", Fluids.getStack(Fluids.magnesium, MetalRates.INGOT), 300);
        PluginTConstruct.addMelting(Core.metals, MetalMeta.MAGNESIUM_BLOCK, "blockMagnesium", Fluids.getStack(Fluids.magnesium, MetalRates.BLOCK), 300);
        PluginTConstruct.addMelting(Core.metals, MetalMeta.MAGNESIUM_BLOCK, "nuggetMagnesium", Fluids.getStack(Fluids.magnesium, MetalRates.NUGGET), 300);
        // >> Form Ingot and Block
        PluginTConstruct.addCasting("ingotMagnesium", Fluids.getStack(Fluids.magnesium, MetalRates.INGOT), 100);
        PluginTConstruct.addBlockCasting("blockMagnesium", Fluids.getStack(Fluids.magnesium, MetalRates.BLOCK), 100);

        if (FluidRegistry.getFluid("xpjuice") != null) {
            ItemStack xpberry = TConstructRegistry.getItemStack("oreberryEssence");
            ArrayList<FluidStack> fluids = new ArrayList<FluidStack>();
            ArrayList<Integer> chances = new ArrayList<Integer>();

            int j = 3;
            for (int i = 80; i <= 120; i += 10) {
                chances.add(j);
                fluids.add(FluidRegistry.getFluidStack("xpjuice", i));
                j++;
            }

            MaricultureHandlers.crucible.addRecipe(new RecipeCrucible(xpberry, 1000, fluids.toArray(new FluidStack[fluids.size()]), chances.toArray(new Integer[chances.size()]), null, 0));
        }
    }

    private static void addAlloy() {
        FluidStack titanium = new FluidStack(FluidRegistry.getFluid(Fluids.titanium), 8);
        FluidStack rutile = new FluidStack(FluidRegistry.getFluid(Fluids.rutile), 8);
        FluidStack magnesium = new FluidStack(FluidRegistry.getFluid(Fluids.magnesium), 8);

        Smeltery.addAlloyMixing(titanium, new FluidStack[] { rutile, magnesium });
    }

    private static void addModifiers() {
        ToolBuilder tb = ToolBuilder.instance;
        ItemStack pearl = new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE);
        ItemStack pearlBlock = new ItemStack(Core.pearlBlock, 1, OreDictionary.WILDCARD_VALUE);
        ToolBuilder.registerToolMod(new ModPearl(new ItemStack[] { pearl }, 20, 1));
        ToolBuilder.registerToolMod(new ModPearl(new ItemStack[] { pearl, pearl }, 20, 2));
        ToolBuilder.registerToolMod(new ModPearl(new ItemStack[] { pearlBlock }, 20, 4));
        ToolBuilder.registerToolMod(new ModPearl(new ItemStack[] { pearl, pearlBlock }, 20, 5));
        ToolBuilder.registerToolMod(new ModPearl(new ItemStack[] { pearlBlock, pearlBlock }, 20, 8));

        for (ToolCore tool : TConstructRegistry.getToolMapping())
            if (tool instanceof HarvestTool) {
                TConstructClientRegistry.addEffectRenderMapping(tool, 20, "mariculture", "pearl", true);
            }
    }

    //Helper Methods
    public static void addMelting(Block block, int meta, String dic, FluidStack fluid, int temp) {
        if (fluid == null) return;
        if (OreDictionary.getOres(dic).size() > 0) {
            for (ItemStack ore : OreDictionary.getOres(dic))
                if (block != null) {
                    Smeltery.addMelting(ore, block, meta, temp, fluid);
                }
        }
    }

    public static void addCasting(String dic, FluidStack fluid, int delay) {
        if (OreDictionary.getOres(dic).size() > 0) {
            TConstructRegistry.getTableCasting().addCastingRecipe(OreDictionary.getOres(dic).get(0), fluid, TConstructRegistry.getItemStack("ingotCast"), delay);
        }
    }

    public static void addBlockCasting(String dic, FluidStack fluid, int delay) {
        if (OreDictionary.getOres(dic).size() > 0) {
            TConstructRegistry.getBasinCasting().addCastingRecipe(OreDictionary.getOres(dic).get(0), fluid, delay);
        }
    }

    public static void addPartCasting(ItemStack output, ItemStack cast, FluidStack fluid, int hardeningDelay) {
        TConstructRegistry.getTableCasting().addCastingRecipe(output, fluid, cast, hardeningDelay);
    }
}