package mariculture.plugins;

import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.cofh.ThermalExpansionHelper;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.util.FluidDictionary;
import mariculture.plugins.Plugins.Plugin;
import mariculture.world.WorldPlus;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class PluginThermalExpansion extends Plugin {
	@Override
	public void preInit() {
		
	}
	
	public static FluidStack glowstone(int vol) {
		return FluidRegistry.getFluidStack("glowstone", vol);
	}
	
	public static FluidStack redstone(int vol) {
		return FluidRegistry.getFluidStack("redstone", vol);
	}

	@Override
	public void init() {
		if(Modules.isActive(Modules.worldplus)) {
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_BLUE), new ItemStack(Item.dyePowder, 2, Dye.LIGHT_BLUE));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_BRAIN), new ItemStack(Core.materials, 2, MaterialsMeta.DYE_YELLOW));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_CANDYCANE), new ItemStack(Item.dyePowder, 2, Dye.MAGENTA));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_CUCUMBER), new ItemStack(Core.materials, 2, MaterialsMeta.DYE_BROWN));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_GREY), new ItemStack(Item.dyePowder, 2, Dye.GREY));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_LIGHT_GREY), new ItemStack(Item.dyePowder, 2, Dye.LIGHT_GREY));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_ORANGE), new ItemStack(Item.dyePowder, 2, Dye.ORANGE));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_PINK), new ItemStack(Item.dyePowder, 2, Dye.PINK));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_PURPLE), new ItemStack(Item.dyePowder, 2, Dye.PURPLE));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_RED), new ItemStack(Core.materials, 2, MaterialsMeta.DYE_RED));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_WHITE), new ItemStack(Core.materials, 2, MaterialsMeta.DYE_WHITE));
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.KELP), new ItemStack(Core.materials, 1, MaterialsMeta.DYE_GREEN));
			ThermalExpansionHelper.addReactantFuel(FluidDictionary.fish_oil, 300000);
		}
	}

	@Override
	public void postInit() {
		//Redstone Melting Recipes
		if(FluidRegistry.getFluid("redstone") != null) {
			RecipeHelper.addMelting(new ItemStack(Block.torchRedstoneActive), 1600, redstone(100), new ItemStack(Item.stick), 1);
			RecipeHelper.addMelting(new ItemStack(Block.blockRedstone), 1600, redstone(900));
			RecipeHelper.addMelting(new ItemStack(Item.redstone), 1600, redstone(100));
		}
				
		//Glowstone Melting Recipes
		if(FluidRegistry.getFluid("glowstone") != null) {
			RecipeHelper.addMelting(new ItemStack(Item.glowstone), 2000, glowstone(250));
			RecipeHelper.addMelting(new ItemStack(Block.glowStone), 2000, glowstone(1000));
		}
	}
}
