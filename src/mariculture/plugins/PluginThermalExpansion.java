package mariculture.plugins;

import mariculture.core.helpers.RecipeHelper;
import mariculture.core.helpers.cofh.ThermalExpansionHelper;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.Modules;
import mariculture.plugins.Plugins.Plugin;
import mariculture.world.WorldPlus;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class PluginThermalExpansion extends Plugin {
	public PluginThermalExpansion(String name) {
		super(name);
	}

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
		//Redstone Melting Recipes
		RecipeHelper.addMelting(new ItemStack(Block.torchRedstoneActive), 1600, redstone(100), new ItemStack(Item.stick), 1);
		RecipeHelper.addMelting(new ItemStack(Block.blockRedstone), 1600, redstone(900));
		RecipeHelper.addMelting(new ItemStack(Item.redstone), 1600, redstone(100));
		
		//Glowstone Melting Recipes
		RecipeHelper.addMelting(new ItemStack(Item.glowstone), 2000, glowstone(250));
		RecipeHelper.addMelting(new ItemStack(Block.glowStone), 2000, glowstone(1000));
	}

	@Override
	public void postInit() {
		if(Modules.world.isActive()) {
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_BLUE), new ItemStack(Item.dyePowder, 2, Dye.LIGHT_BLUE));
		}
	}
}
