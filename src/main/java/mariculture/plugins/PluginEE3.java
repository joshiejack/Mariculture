package mariculture.plugins;

import static com.pahimar.ee3.api.exchange.EnergyValueRegistryProxy.addPreAssignedEnergyValue;
import static com.pahimar.ee3.api.exchange.RecipeRegistryProxy.addRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.IAnvilHandler.RecipeAnvil;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeCasting;
import mariculture.api.core.RecipeSmelter;
import mariculture.api.core.RecipeVat;
import mariculture.core.Core;
import mariculture.core.lib.DropletMeta;
import mariculture.core.lib.GroundMeta;
import mariculture.core.lib.LimestoneMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RockMeta;
import mariculture.core.lib.WaterMeta;
import mariculture.core.util.Fluids;
import mariculture.fishery.Fishery;
import mariculture.fishery.items.ItemDroplet;
import mariculture.plugins.Plugins.Plugin;
import mariculture.world.WorldPlus;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.pahimar.ee3.exchange.OreStack;

public class PluginEE3 extends Plugin {
    public PluginEE3(String name) {
        super(name);
    }

    @Override
    public void preInit() {}

    @Override
    public void init() {}

    @Override
    public void postInit() {
        Mariculture.EE3 = true;
    }

    public static void load() {
        try {
            addPreAssignedEnergyValue(new ItemStack(Blocks.sponge), 1F);
            addPreAssignedEnergyValue(new ItemStack(Core.water, 1, WaterMeta.OYSTER), 24576F);
            addPreAssignedEnergyValue(new ItemStack(Core.sands, 1, GroundMeta.ANCIENT), 1F);
            addPreAssignedEnergyValue(new ItemStack(Core.rocks, 1, RockMeta.CORAL_ROCK), 1F);
            //addPreAssignedEnergyValue(new OreStack("oreCopper"), 135F);
            addPreAssignedEnergyValue(new ItemStack(Core.rocks, 1, RockMeta.COPPER), 135F);
           // addPreAssignedEnergyValue(new OreStack("oreAluminum"), 315F);
           // addPreAssignedEnergyValue(new OreStack("oreAluminium"), 315F);
            addPreAssignedEnergyValue(new ItemStack(Core.rocks, 1, RockMeta.BAUXITE), 315F);
           // addPreAssignedEnergyValue(new OreStack("oreRutile"), 6144F);
            addPreAssignedEnergyValue(new ItemStack(Core.rocks, 1, RockMeta.RUTILE), 6144F);
            //addPreAssignedEnergyValue(new OreStack("limestone"), 1F);
            //addPreAssignedEnergyValue(new OreStack("blockLimestone"), 1F);
            addPreAssignedEnergyValue(new ItemStack(Core.limestone, 1, LimestoneMeta.RAW), 1F);
            addPreAssignedEnergyValue(new ItemStack(Core.limestone, 1, LimestoneMeta.SMOOTH), 1F);
            addPreAssignedEnergyValue(new ItemStack(Core.pearls, 1, OreDictionary.WILDCARD_VALUE), 24F);
            //addPreAssignedEnergyValue(new OreStack("ingotCopper"), 135F);
            addPreAssignedEnergyValue(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_COPPER), 135F);
            //addPreAssignedEnergyValue(new OreStack("ingotAluminum"), 315F);
           // addPreAssignedEnergyValue(new OreStack("ingotAluminium"), 315F);
            addPreAssignedEnergyValue(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_ALUMINUM), 315F);
            //addPreAssignedEnergyValue(new OreStack("ingotRutile"), 6144F);
            addPreAssignedEnergyValue(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_RUTILE), 6144F);
            //addPreAssignedEnergyValue(new OreStack("ingotMagnesium"), 32F);
            addPreAssignedEnergyValue(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_MAGNESIUM), 32F);
            //addPreAssignedEnergyValue(new OreStack("ingotTitanium"), 8192F);
            addPreAssignedEnergyValue(new ItemStack(Core.materials, 1, MaterialsMeta.INGOT_TITANIUM), 8192F);

            if (Modules.isActive(Modules.fishery)) {
                addPreAssignedEnergyValue(new ItemStack(Fishery.bait, 1, OreDictionary.WILDCARD_VALUE), 0.25F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.MAGIC), 8192F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.fishEggs, 1, OreDictionary.WILDCARD_VALUE), 48F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.AIR), 0.25F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.EARTH), 0.25F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.WATER), 1F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.ALUMINUM), 35F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.AQUA), 0.5F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.ARDITE), 60F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.ATTACK), 481F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.COBALT), 60F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.COPPER), 15F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.ELECTRIC), 15F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.ENDER), 128F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.FROZEN), 0.1F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.GOLD), 113.7F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.HEALTH), 16F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.IRON), 25F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.LEAD), 24F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.MAGNESIUM), 3.5F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.NETHER), 6.4F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.NICKEL), 32F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.OSMIUM), 250F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.PLANT), 0.25F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.PLATINUM), 250F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.POISON), 2F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.RUTILE), 650F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.SILVER), 56.85F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.TIN), 16F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.XP), 1024F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.ZINC), 16F);
                addPreAssignedEnergyValue(new ItemStack(Fishery.droplet, 1, DropletMeta.USELESS), 0F);

                ItemDroplet item = (ItemDroplet) Fishery.droplet;
                for (int i = 0; i < DropletMeta.COUNT; i++) {
                    ItemStack droplet = new ItemStack(Fishery.droplet, 1, i);
                    FluidStack fluid = item.getFluidStack(i);
                    if (droplet != null && fluid != null) addRecipe(fluid, Arrays.asList(droplet));
                }
            }

            addPreAssignedEnergyValue(Fluids.getFluidStack("natural_gas", 1000), 1024F);
            addPreAssignedEnergyValue(Fluids.getFluidStack("hp_water", 1000), 1F);
            addPreAssignedEnergyValue(Fluids.getFluidStack("quicklime", 1000), 1F);
            addPreAssignedEnergyValue(Fluids.getFluidStack("salt", 1000), 1F);
            addPreAssignedEnergyValue(Fluids.getFluidStack("xp", 1000), 1024F);

            if (Modules.isActive(Modules.fishery)) {
                addPreAssignedEnergyValue(Fluids.getFluidStack("fish_oil", 1000), 8F);
            }

            if (Modules.isActive(Modules.worldplus)) {
                addPreAssignedEnergyValue(new ItemStack(WorldPlus.plantGrowable, 1, OreDictionary.WILDCARD_VALUE), 8F);
                addPreAssignedEnergyValue(new ItemStack(WorldPlus.plantStatic, 1, OreDictionary.WILDCARD_VALUE), 8F);
            }

            /** Register all nugget casting recipes **/
            for (RecipeCasting nugget : MaricultureHandlers.casting.getNuggetRecipes().values()) {
                if (nugget.output != null && nugget.fluid != null) addRecipe(nugget.output, Arrays.asList(nugget.fluid));
            }

            /** Register all ingot casting recipes **/
            for (RecipeCasting ingot : MaricultureHandlers.casting.getIngotRecipes().values()) {
                if (ingot.output != null && ingot.fluid != null) addRecipe(ingot.output, Arrays.asList(ingot.fluid));
            }

            /** Register all block casting recipes **/
            for (RecipeCasting block : MaricultureHandlers.casting.getBlockRecipes().values()) {
                if (block.output != null && block.fluid != null) addRecipe(block.output, Arrays.asList(block.fluid));
            }

            /** Register all crucible recipes **/
            for (RecipeSmelter crucible : MaricultureHandlers.crucible.getRecipeList()) {
                if (crucible.input != null) {
                    ItemStack outItem = crucible.output;
                    FluidStack outFluid = crucible.fluid;
                    FluidStack[] outFluids = crucible.random;
                    List input = Arrays.asList(crucible.input);
                    if (outItem != null) addRecipe(outItem, input);
                    if (outFluid != null) addRecipe(outFluid, input);
                    if (outFluids != null && outFluids.length > 0) {
                        for (FluidStack fluid : outFluids) {
                            addRecipe(fluid, input);
                        }
                    }
                }
            }

            /** Register all anvil recipes **/
            for (RecipeAnvil anvil : MaricultureHandlers.anvil.getRecipes().values()) {
                if (anvil.output != null && anvil.input != null) addRecipe(anvil.output, Arrays.asList(anvil.input));
            }

            /** Register all vat recipes with output items **/
            for (RecipeVat vat : MaricultureHandlers.vat.getRecipes()) {
                ItemStack outItem = vat.outputItem;
                FluidStack outFluid = vat.outputFluid;
                ArrayList input = new ArrayList();
                if (vat.inputItem instanceof String) {
                    input.add(OreDictionary.getOres((String) vat.inputItem));
                } else if (vat.inputItem instanceof ItemStack) {
                    input.add(vat.inputItem);
                }

                if (vat.inputFluid1 != null) input.add(vat.inputFluid1);
                if (vat.inputFluid2 != null) input.add(vat.inputFluid2);
                if (outItem != null) addRecipe(outItem, input);
                if (outFluid != null) addRecipe(outFluid, input);
            }
        } catch (Exception e) {}
    }
}
