package mariculture.plugins;

import java.util.List;

import mariculture.core.Core;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.FoodMeta;
import mariculture.core.util.RecipeRemover;
import mariculture.fishery.Fish;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PluginAppleCore extends Plugin {
    public PluginAppleCore(String name) {
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

    }
}
