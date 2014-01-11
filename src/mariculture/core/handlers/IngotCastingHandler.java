package mariculture.core.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import mariculture.api.core.IIngotCasterHandler;
import mariculture.api.core.RecipeIngotCasting;
import mariculture.core.helpers.OreDicHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class IngotCastingHandler implements IIngotCasterHandler {
	private final Map recipes = new HashMap();
	
	@Override
	public void addRecipe(RecipeIngotCasting recipe) {
		recipes.put(Arrays.asList(recipe.fluid.fluidID), recipe);
	}

	@Override
	public RecipeIngotCasting getResult(FluidStack fluid) {
		if(fluid == null)
			return null;
		RecipeIngotCasting result = (RecipeIngotCasting) recipes.get(Arrays.asList(fluid.fluidID));
		if(result == null)
			return null;
		if(fluid.amount < result.fluid.amount)
			return null;
		return result;
	}

	//Attempt to fetch the texture, using the ore dictionary
	public static Icon getTexture(ItemStack stack) {
		String name = OreDicHelper.getDictionaryName(stack);
		name = name.substring(5);
		name = "block" + name;
		
		if(OreDictionary.getOres(name).size() > 0) {
			ItemStack block = OreDictionary.getOres(name).get(0);
			if(Block.blocksList[block.itemID] != null) {
				return Block.blocksList[block.itemID].getIcon(0, block.getItemDamage());
			}
		}
		
		return Block.blockIron.getIcon(0, 0);
	}
}
