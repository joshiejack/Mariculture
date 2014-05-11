package mariculture.plugins;

import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.Modules;
import mariculture.core.util.RecipeRemover;
import mariculture.fishery.Fish;
import mariculture.fishery.Fishery;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginHungerOverhaul extends Plugin {
	@Override
	public void preInit() {
		return;
	}

	@Override
	public void init() {
		return;
	}

	@Override
	public void postInit() {
		if (Modules.isActive(Modules.fishery)) {
			RecipeRemover.remove(new ItemStack(Core.food, 1, FoodMeta.CALAMARI));
			RecipeHelper.addShapelessRecipe(new ItemStack(Core.food, 1, FoodMeta.CALAMARI_HALF), new Object[] {
				new ItemStack(Fishery.fishyFood, 1, Fish.squid.getID()), Item.bowlEmpty
			});
		}
	}
}
