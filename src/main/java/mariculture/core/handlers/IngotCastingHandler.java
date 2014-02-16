package mariculture.core.handlers;

import java.util.HashMap;

import mariculture.api.core.IIngotCasterHandler;
import mariculture.api.core.RecipeIngotCasting;
import mariculture.core.helpers.OreDicHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class IngotCastingHandler implements IIngotCasterHandler {
	private final HashMap <String, RecipeIngotCasting>recipes = new HashMap();
	
	@Override
	public void addRecipe(RecipeIngotCasting recipe) {
		recipes.put(recipe.fluid.getFluid().getName(), recipe);
	}

	@Override
	public RecipeIngotCasting getResult(FluidStack fluid) {
		if(fluid == null)
			return null;
		RecipeIngotCasting result = recipes.get(fluid.getFluid().getName());
		if(result == null)
			return null;
		if(fluid.amount < result.fluid.amount)
			return null;
		return result;
	}

	//Attempt to fetch the texture, using the ore dictionary
	public static IIcon getTexture(ItemStack stack) {
		String name = OreDicHelper.getDictionaryName(stack);
		name = name.substring(5);
		name = "block" + name;
		
		if(OreDictionary.getOres(name).size() > 0) {
			ItemStack item = OreDictionary.getOres(name).get(0);
			if((Block.getBlockFromItem(stack.getItem())) != null) {
				return Block.getBlockFromItem(item.getItem()).getIcon(0, item.getItemDamage());
			}
		}
		
		return Blocks.iron_block.getIcon(0, 0);
	}

	@Override
	public HashMap<String, RecipeIngotCasting> getRecipes() {
		return recipes;
	}
}
