package mariculture.plugins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import mariculture.plugins.Plugins.Plugin;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

public class PluginModdedTweaks extends Plugin {
    public PluginModdedTweaks(String name) {
        super(name);
    }

    @Override
    public void preInit() {
        return;
    }

    @Override
    public void init() {
        return;
    }

    private boolean isAluminum(String str) {
        return str.equals("oreAluminum") || str.equals("oreAluminium") || str.equals("oreNaturalAluminum") || str.equals("oreBauxite");
    }

    @Override
    public void postInit() {
        ArrayList<ItemStack> toRemove = new ArrayList();
        Iterator<Map.Entry<ItemStack, ItemStack>> iter = FurnaceRecipes.smelting().getSmeltingList().entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<ItemStack, ItemStack> entry = iter.next();
            int[] ids = OreDictionary.getOreIDs(entry.getKey());
            if (ids != null && ids.length > 0) {
                for (int i : ids) {
                    if (isAluminum(OreDictionary.getOreName(i))) {
                        toRemove.add(entry.getKey());
                    }
                }
            }
        }

        for (ItemStack stack : toRemove) {
            FurnaceRecipes.smelting().getSmeltingList().remove(stack);
        }
    }
}
