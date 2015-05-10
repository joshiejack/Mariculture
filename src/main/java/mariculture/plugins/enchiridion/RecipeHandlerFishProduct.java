package mariculture.plugins.enchiridion;

import java.util.ArrayList;
import java.util.List;

import joshie.enchiridion.api.EnchiridionAPI;
import joshie.enchiridion.api.IItemStack;
import joshie.enchiridion.api.IRecipeHandler;
import joshie.enchiridion.designer.recipe.RecipeHandlerBase;
import joshie.enchiridion.designer.recipe.WrappedStack;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishProduct;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.lib.helpers.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RecipeHandlerFishProduct extends RecipeHandlerBase {
    public RecipeHandlerFishProduct() {}
    public RecipeHandlerFishProduct(FishSpecies species, ArrayList<FishProduct> list) {
        int x = 0;
        int y = 0;
        for (FishProduct product: list) {
            stackList.add(new WrappedStack(product.product, (x * 24), -10D + (y * 60), 1.2F));
            x++;
            if (x > 5) {
                x = 0;
                y++;
            }
        }
        
        unique = species.getName();
    }

    @Override
    public void addRecipes(ItemStack output, List<IRecipeHandler> list) {
        FishSpecies theSpecies = Fishing.fishHelper.getSpecies(output);
        if (theSpecies == null && output.getItem() != Items.fish) return;
        if (theSpecies == null) theSpecies = Fishing.fishHelper.getSpecies(output.getItemDamage());
        for (FishSpecies species : FishSpecies.species.values()) {
            if (species == theSpecies) {
                if (species.getProductList().size() > 0) {
                    list.add(new RecipeHandlerFishProduct(species, species.getProductList()));
                }
                
                break;
            }
        }
    }

    @Override
    public String getRecipeName() {
        return "MaricultureFishProducts";
    }

    @Override
    public double getHeight(double width) {
        return width / 4D;
    }

    @Override
    public double getWidth(double width) {
        return width;
    }

    @Override
    public float getSize(double width) {
        return (float) (width / 110D);
    }

    protected void drawBackground() {}
}
