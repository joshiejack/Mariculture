package mariculture.plugins;

import static mariculture.core.helpers.RecipeHelper.addShapeless;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import rebelkeithy.mods.aquaculture.items.AquacultureItems;

public class PluginAquaculture extends Plugin {
    public PluginAquaculture(String name) {
        super(name);
    }

    @Override
    public void preInit() {
        return;
    }

    @Override
    public void init() {
        if (Modules.isActive(Modules.fishery)) {
            Fishing.fishing.addBait(new ItemStack(AquacultureItems.fish, 1, 19), 75);
            Fishing.fishing.addBaitForQuality(new ItemStack(AquacultureItems.fish, 1, 19), Arrays.asList(RodType.DIRE, RodType.FLUX));
        }
    }

    @Override
    public void postInit() {
        if (Modules.isActive(Modules.fishery)) {
            //Remove Fish Fillet Recipes, We readd them later with a new recipe
            remove(getItem("item.loot"), 3);

            for (Entry<Integer, FishSpecies> list : FishSpecies.species.entrySet()) {
                FishSpecies species = list.getValue();
                if (species.getFishMealSize() > 0) {
                    addShapeless(new ItemStack(getItem("item.loot"), species.getFishMealSize(), 3), new Object[] { species.getRawForm(1), "foodSalt" });
                }
            }
        }
    }

    public void remove(Item item, int meta) {
        List<ItemStack> list = new ArrayList();
        for (Iterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().iterator(); iterator.hasNext();) {
            IRecipe recipe = iterator.next();
            if (recipe.getRecipeOutput() != null) {
                if (recipe.getRecipeOutput().getItem() == item && recipe.getRecipeOutput().getItemDamage() == meta) {
                    list.add(recipe.getRecipeOutput().copy());
                    iterator.remove();
                }
            }
        }

        for (ItemStack output : list) {
            for (int i = 0; i < AquacultureItems.fish.fish.size(); i++) {
                if ((i >= 14 && i <= 19) || i == 38) continue;
                addShapeless(output, new Object[] { new ItemStack(AquacultureItems.fish, 1, i), "foodSalt" });
                addShapeless(new ItemStack(Core.materials, output.stackSize, MaterialsMeta.FISH_MEAL), new Object[] { new ItemStack(AquacultureItems.fish, 1, i) });
            }
        }
    }
}
