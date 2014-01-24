package mariculture.core.helpers;

import cpw.mods.fml.common.registry.GameRegistry;
import mariculture.api.core.FuelInfo;
import mariculture.api.core.IAnvilHandler.RecipeAnvil;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeIngotCasting;
import mariculture.api.core.RecipeSmelter;
import mariculture.api.core.RecipeVat;
import mariculture.core.Core;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.util.FluidDictionary;
import mariculture.world.WorldPlus;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
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
	
	public static void addVatItemRecipeResultFluid(ItemStack input, FluidStack inputFluid, FluidStack output, int time) {
		MaricultureHandlers.vat.addRecipe(new RecipeVat(input, inputFluid, output, time));
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
	
	public static void addFluidAlloyResultItemNFluid(FluidStack fluid1, FluidStack fluid2, FluidStack newFluid, ItemStack result, int time) {
		MaricultureHandlers.vat.addRecipe(new RecipeVat(fluid1, fluid2, newFluid, result, time));
	}
	
	public static void addFluidAlloyNItemResultItem(FluidStack fluid1, FluidStack fluid2, ItemStack input, ItemStack result, int time) {
		MaricultureHandlers.vat.addRecipe(new RecipeVat(input, fluid1, fluid2, result, time));
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

	public static void addUncraftingRecipe(ItemStack result, Object input) {
		addShapelessRecipe(result, new Object[] { input });
	}

	public static void addAnvilRecipe(ItemStack input, ItemStack output, int hits) {
		MaricultureHandlers.anvil.addRecipe(new RecipeAnvil(input, output, hits));
	}

	public static void addFishingRodRecipe(ItemStack output, Object mat) {
		addShapedRecipe(output ,new Object[] { "  S", " SW", "S W", 'S', mat, 'W', Item.silk });
	}

	public static void addMelting(ItemStack stack, int temp, FluidStack fluid) {
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(stack, null, temp, fluid, null, 0));
	}
	
	public static void addMelting(ItemStack stack, int temp, FluidStack fluid, ItemStack output, int chance) {
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(stack, null, temp, fluid, output, chance));
	}

	public static void addMelting(ItemStack stack, int temp, String fluid, int vol) {
		addMelting(stack, temp, FluidRegistry.getFluidStack(fluid, vol));
	}

	/** @param output - ItemStack
	 * @param item1 - 5 x this
	 * @param item2 - 4 x this */
	public static void addCrossHatchRecipe(ItemStack output, Object item1, Object item2) {
		addShapedRecipe(output, new Object[] { "CAC", "ACA", "CAC", 'C', item1, 'A', item2 });
	}
	
	/** @param output - ItemStack
	 * @param wheel - Outer Rim
	 * @param spoke - Inner Piece */
	public static void addWheelRecipe(ItemStack output, Object wheel, Object spoke) {
		addShapedRecipe(output, new Object[] { " W ", "WSW", " W ", 'W', wheel, 'S', spoke });
	}

	/** Adds a fuel to be used by the smelter
	 * 
	 * @param obj - Can be FluidStack, ItemStack or String
	 * @param fuelInfo
	 */
	public static void addFuel(Object obj, FuelInfo fuelInfo) {
		MaricultureHandlers.smelter.addFuel(obj, fuelInfo);
	}

	public static void addMeltingAlloy(ItemStack stack1, ItemStack stack2, int temp, FluidStack fluid) {
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(stack1, stack2, temp, fluid, null, 0));
	}

	public static void addCrushRecipe(ItemStack stack, Object string, boolean needAnvil) {
		if(!needAnvil)
			addShapelessRecipe(stack, new Object[] { string });
		ItemStack result = null;
		if(string instanceof String) {
			if(OreDictionary.getOres((String)string).size() > 0) {
				result = OreDictionary.getOres((String)string).get(0);
			}
		}
		
		if(string instanceof ItemStack) {
			result = (ItemStack) string;
		}
		
		if(result != null) {
			if(!needAnvil)
				stack.stackSize*=2;
			addAnvilRecipe(result, stack, 10);
		}
	}

	public static void addBleachRecipe(ItemStack input, ItemStack output, int time) {
		addVatItemRecipe(input, FluidDictionary.quicklime, 50, output, time);
	}
}
