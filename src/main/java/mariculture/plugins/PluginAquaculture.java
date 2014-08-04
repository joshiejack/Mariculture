package mariculture.plugins;

import static mariculture.core.helpers.RecipeHelper.addShapeless;

import java.util.Iterator;
import java.util.Map.Entry;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.plugins.Plugins.Plugin;
import mariculture.plugins.aquaculture.FishBluegill;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class PluginAquaculture extends Plugin {
    public static FishSpecies bluegill;
    
    public PluginAquaculture(String name) {
        super(name);
    }

    @Override
    public void preInit() {
        System.out.println(name);
        
        bluegill = Fishing.fishHelper.registerFish(name, FishBluegill.class);
    }

    @Override
    public void init() {
        //Remove Fish Fillet Recipes, We readd them later with a new recipe
        remove(getItem("item.loot"), 3);
    }

    @Override
    public void postInit() {
        for (Entry<Integer, FishSpecies> list : FishSpecies.species.entrySet()) {
            FishSpecies species = list.getValue();
            if (species.getFishMealSize() > 0) {
                addShapeless(new ItemStack(getItem("item.loot"), species.getFishMealSize(), 3), new Object[] { species.getRawForm(1), "foodSalt" });
            }
        }
    }

    public void remove(Item item, int meta) {
        for (Iterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().iterator(); iterator.hasNext();) {
            IRecipe recipe = iterator.next();
            if (recipe instanceof ShapelessOreRecipe && recipe.getRecipeOutput() != null) {
                if (recipe.getRecipeOutput().getItem() == item && recipe.getRecipeOutput().getItemDamage() == meta) {
                    iterator.remove();
                }
            }
        }
    }
}
