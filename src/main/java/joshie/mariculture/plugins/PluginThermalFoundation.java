package joshie.mariculture.plugins;

import static joshie.mariculture.core.helpers.RecipeHelper.addMelting;
import static joshie.mariculture.core.util.Fluids.getFluid;
import static joshie.mariculture.core.util.Fluids.getFluidStack;
import joshie.mariculture.core.util.Fluids;
import joshie.mariculture.plugins.Plugins.Plugin;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

public class PluginThermalFoundation extends Plugin {
    public PluginThermalFoundation(String name) {
        super(name);
    }

    @Override
    public void preInit() {
        Fluids.add("ender", FluidRegistry.getFluid("ender"), 25, true);
        Fluids.add("ice", FluidRegistry.getFluid("cryotheum"), 125, true);
        Fluids.add("redstone", FluidRegistry.getFluid("redstone"), 100);
        Fluids.add("glowstone", FluidRegistry.getFluid("glowstone"), 100);
    }

    @Override
    public void init() {
        //Redstone Melting Recipes
        if (getFluid("redstone") != null) {
            addMelting(new ItemStack(Blocks.redstone_torch), 1600, getFluidStack("redstone", 100), new ItemStack(Items.stick), 1);
            addMelting(new ItemStack(Blocks.redstone_block), 1600, getFluidStack("redstone", 900));
            addMelting(new ItemStack(Items.redstone), 1600, getFluidStack("redstone", 100));
        }

        //Glowstone Melting Recipes
        if (getFluid("glowstone") != null) {
            addMelting(new ItemStack(Items.glowstone_dust), 2000, getFluidStack("glowstone", 250));
            addMelting(new ItemStack(Blocks.glowstone), 2000, getFluidStack("glowstone", 1000));
        }
    }

    @Override
    public void postInit() {
        return;
    }
}
