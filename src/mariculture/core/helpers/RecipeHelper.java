package mariculture.core.helpers;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeIngotCasting;
import mariculture.api.core.RecipeVat;
import mariculture.core.lib.MetalRates;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeHelper {
	public static void addShapedRecipe(ItemStack result, Object[] input) {
		CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(result, input));
	}
	
	public static void addShapelessRecipe(ItemStack result, Object[] input) {
		CraftingManager.getInstance().getRecipeList().add(new ShapelessOreRecipe(result, input));
	}

	public static void addSmelting(int id, int meta, ItemStack stack, float xp) {
		FurnaceRecipes.smelting().addSmelting(id, meta, stack, xp);
	}

	public static void addVatItemRecipe(ItemStack input, String fluid, int vol, ItemStack output, int time) {
		MaricultureHandlers.vat.addRecipe(new RecipeVat(input, FluidRegistry.getFluidStack(fluid, vol), output, time));
	}

	public static void addIngotCasting(String fluid, ItemStack stack) {
		MaricultureHandlers.casting.addRecipe(new RecipeIngotCasting(
				FluidRegistry.getFluidStack(fluid, MetalRates.INGOT), stack));
	}
	
	public static void addIngotCasting(String fluid, String metal) {
		String ingot = "ingot" + metal;
		if(OreDictionary.getOres(ingot).size() > 0) {
			RecipeHelper.addIngotCasting(fluid, OreDictionary.getOres("ingot" + metal).get(0));
		}
	}

	public static void addFluidAlloy(FluidStack fluid1, FluidStack fluid2, FluidStack result, int time) {
		MaricultureHandlers.vat.addRecipe(new RecipeVat(fluid1, fluid2, result, time));
	}
	
	public static void addFluidAlloyResultItem(FluidStack fluid1, FluidStack fluid2, ItemStack result, int time) {
		MaricultureHandlers.vat.addRecipe(new RecipeVat(fluid1, fluid2, result, time));
	}
	
	public static void add4x4Recipe(ItemStack result, Block block, int meta) {
		addShapedRecipe(result, new Object[] { "##", "##", '#', new ItemStack(block, 1, meta) });
	}
	
	public static void add4x4Recipe(ItemStack result, Object input) {
		addShapedRecipe(result, new Object[] { "##", "##", '#', input });
	}

	public static void add9x9Recipe(ItemStack result, Object input) {
		addShapedRecipe(result, new Object[] { "###", "###", "###", '#', input });
	}

	public static void add9x9RecipeUndo(ItemStack result, Object input) {
		addShapelessRecipe(result, new Object[] { input });
	}
}
