package mariculture.factory;

import java.util.HashMap;
import java.util.List;

import mariculture.core.helpers.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class UnpackerHelper {
    public static HashMap<String, IRecipe> cache = new HashMap();

    public static boolean canUnpack(World world, ItemStack stack) {
        return unpack(world, stack) != null;
    }

    public static ItemStack unpack(World world, ItemStack stack) {
        String key = ItemHelper.getName(stack);
        
        DummyCrafting crafting = new DummyCrafting(1, 1);
        crafting.setInventorySlotContents(0, stack);
        IRecipe recipe = cache.get(key);
        if (recipe != null) {
            return recipe.getCraftingResult(crafting).copy();
        }

        for (IRecipe irecipe : (List<IRecipe>) CraftingManager.getInstance().getRecipeList()) {
            try {
                if (irecipe.matches(crafting, world)) {
                    cache.put(key, irecipe);
                    return irecipe.getCraftingResult(crafting).copy();
                }
            } catch (Exception e) {}
        }

        return null;
    }
}
