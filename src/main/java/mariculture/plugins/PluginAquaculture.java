package mariculture.plugins;

import static mariculture.core.helpers.RecipeHelper.addShapeless;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map.Entry;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.core.util.Fluids;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;
import rebelkeithy.mods.aquaculture.items.AquacultureItems;
import rebelkeithy.mods.aquaculture.items.ItemFish.Fish;

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
            Fishing.fishing.addBait(new ItemStack(AquacultureItems.fish, 1, 19), 25);
            Fishing.fishing.addBaitForQuality(new ItemStack(AquacultureItems.fish, 1, 19), Arrays.asList(RodType.DIRE, RodType.FLUX));
        }
    }

    @Override
    public void postInit() {
        if (Modules.isActive(Modules.fishery)) {
            //Remove Fish Fillet Recipes, We readd them later with a new recipe            
            for (Iterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().iterator(); iterator.hasNext();) {
                IRecipe recipe = iterator.next();
                if (recipe.getRecipeOutput() != null) {
                    if (recipe.getRecipeOutput().getItem() == AquacultureItems.metaLootItem && recipe.getRecipeOutput().getItemDamage() == 3) {
                        iterator.remove();
                    }
                }
            }

            for (Entry<Integer, FishSpecies> list : FishSpecies.species.entrySet()) {
                FishSpecies species = list.getValue();
                if (species.getFishMealSize() > 0) {
                    addShapeless(new ItemStack(getItem("item.loot"), species.getFishMealSize(), 3), new Object[] { species.getRawForm(1), "foodSalt" });
                }
            }

            //Add all the recipes back, melting, fish, registrations and all included
            for (int i = 0; i < AquacultureItems.fish.fish.size(); i++) {
                Fish f = AquacultureItems.fish.fish.get(i);
                if (f.filletAmount != 0) {
                    int amount = f.filletAmount;
                    ItemStack raw = new ItemStack(AquacultureItems.fish, 1, i);
                    addShapeless(AquacultureItems.fishFillet.getItemStack(amount), new Object[] { raw, "foodSalt" });
                    addShapeless(new ItemStack(Core.materials, amount, MaterialsMeta.FISH_MEAL), new Object[] { raw });
                    OreDictionary.registerOre("fish", raw);
                    RecipeHelper.addFishSushi(raw, amount);
                    RecipeHelper.addFishSoup(raw, amount);
                    RecipeHelper.addFishMeal(raw, amount);
                    RecipeHelper.addMelting(raw, 180, Fluids.getFluidStack("fish_oil", (int) (amount * 250)), new ItemStack(Items.bone), 10);
                }
            }
        }
    }
}
