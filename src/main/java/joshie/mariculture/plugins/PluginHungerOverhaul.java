package joshie.mariculture.plugins;

import iguanaman.hungeroverhaul.IguanaConfig;

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

    //Called to add the tooltip about hunger
    public static void addInformation(int fill, float saturation, List list) {
        if (IguanaConfig.addFoodTooltips) {
            int hungerFill = fill;
            float satiation = saturation * 20 - hungerFill;

            String tooltip = "";

            if (satiation >= 3.0F) {
                tooltip += "hearty ";
            } else if (satiation >= 2.0F) {
                tooltip += "wholesome ";
            } else if (satiation > 0.0F) {
                tooltip += "nourishing ";
            } else if (satiation < 0.0F) {
                tooltip += "unfulfilling ";
            }

            if (hungerFill <= 1) {
                tooltip += "morsel";
            } else if (hungerFill <= 2) {
                tooltip += "snack";
            } else if (hungerFill <= 5) {
                tooltip += "light meal";
            } else if (hungerFill <= 8) {
                tooltip += "meal";
            } else if (hungerFill <= 11) {
                tooltip += "large meal";
            } else {
                tooltip += "feast";
            }

            list.add(tooltip.substring(0, 1).toUpperCase() + tooltip.substring(1));
        }
    }
}
