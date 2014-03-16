package mariculture.plugins;

import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.FoodMeta;
import mariculture.core.util.RecipeRemover;
import mariculture.fishery.Fishery;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
public class PluginHungerOverhaul extends Plugin {

	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void postInit() {
		RecipeRemover.remove(new ItemStack(Core.food, 3, FoodMeta.CALAMARI));
		RecipeHelper.addShapelessRecipe(new ItemStack(Core.food, 1, FoodMeta.CALAMARI), new Object[] {
			new ItemStack(Items.fish, 1, Fishery.squid.fishID), Items.bowl
		});
	}
}
