package mariculture.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import mariculture.core.helpers.ItemHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import codechicken.nei.ItemList.TimeoutException;
import codechicken.nei.guihook.GuiContainerManager;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

public class UnpackerHelper {
    public static HashMap<String, ItemStack> cache = new HashMap();
    public static boolean canUnpack(World world, ItemStack stack) {
        return unpack(world, stack) != null;
    }

    public static ItemStack unpack(World world, ItemStack stack) {
        String key = ItemHelper.getName(stack);
        if (cache.containsKey(key)) {
            return cache.get(key).copy();
        }

        DummyCrafting crafting = new DummyCrafting(1, 1);
        crafting.setInventorySlotContents(0, stack);
        for (IRecipe irecipe : (List<IRecipe>) CraftingManager.getInstance().getRecipeList()) {
            try {
                if (irecipe.matches(crafting, world)) {
                    return irecipe.getCraftingResult(crafting).copy();
                }
            } catch (Exception e) {}
        }

        return null;
    }
}
