package mariculture.factory;

import java.util.ArrayList;
import java.util.Arrays;
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

    public static void load() {
        ArrayList<ItemStack> itemList = new ArrayList();
        //Item Loader borrowed from Chickenbones' NEI
        try {
            ArrayList<ItemStack> items = new ArrayList<ItemStack>();
            ArrayList<ItemStack> permutations = new ArrayList<ItemStack>();
            ListMultimap<Item, ItemStack> itemMap = ArrayListMultimap.create();

            for (Item item : (Iterable<Item>) Item.itemRegistry) {
                if (item == null) continue;

                permutations.clear();
                if (permutations.isEmpty()) {
                    try {
                        item.getSubItems(item, null, permutations);
                    } catch (Exception e) {
                        continue;
                    }
                }

                if (permutations.isEmpty()) {
                    HashSet<String> damageIconSet = new HashSet<String>();
                    for (int damage = 0; damage < 16; damage++) {
                        ItemStack itemstack = new ItemStack(item, 1, damage);
                        try {
                            IIcon icon = item.getIconIndex(itemstack);
                            String name = GuiContainerManager.concatenatedDisplayName(itemstack, false);
                            String s = name + "@" + (icon == null ? 0 : icon.hashCode());
                            if (!damageIconSet.contains(s)) {
                                damageIconSet.add(s);
                                permutations.add(itemstack);
                            }

                        } catch (Throwable t) {

                        }
                    }
                }

                items.addAll(permutations);
            }

            itemList = items;
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        for (ItemStack stack : itemList) {
            DummyCrafting crafting = new DummyCrafting(1, 1);
            crafting.setInventorySlotContents(0, stack);
            for (IRecipe irecipe : (List<IRecipe>) CraftingManager.getInstance().getRecipeList()) {
                try {
                    if (irecipe.matches(crafting, null)) {
                        String key = ItemHelper.getName(stack);
                        ItemStack out = irecipe.getCraftingResult(crafting);
                        cache.put(key, out);
                    }
                } catch (Exception e) {

                }
            }
        }
    }

   
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
