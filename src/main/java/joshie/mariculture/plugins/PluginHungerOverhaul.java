package joshie.mariculture.plugins;

import java.util.List;

import joshie.mariculture.core.Core;
import joshie.mariculture.core.helpers.RecipeHelper;
import joshie.mariculture.core.lib.Extra;
import joshie.mariculture.core.lib.FoodMeta;
import joshie.mariculture.core.util.RecipeRemover;
import joshie.mariculture.fishery.Fish;
import joshie.mariculture.plugins.Plugins.Plugin;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PluginHungerOverhaul extends Plugin {
    public PluginHungerOverhaul(String name) {
        super(name);
    }

    @Override
    public void preInit() {
        Extra.NERF_FOOD = true;
    }

    @Override
    public void init() {

    }

    @Override
    public void postInit() {
        RecipeRemover.remove(new ItemStack(Core.food, 1, FoodMeta.CALAMARI));
        RecipeHelper.addShapeless(new ItemStack(Core.food, 1, FoodMeta.CALAMARI_HALF), new Object[] { new ItemStack(Items.fish, 1, Fish.squid.getID()), Items.bowl });
    }
}
