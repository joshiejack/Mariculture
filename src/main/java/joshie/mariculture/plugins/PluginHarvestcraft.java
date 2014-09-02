package joshie.mariculture.plugins;

import static joshie.mariculture.core.helpers.RecipeHelper.addShaped;
import static joshie.mariculture.core.helpers.RecipeHelper.asStack;
import joshie.mariculture.core.lib.Modules;
import joshie.mariculture.plugins.Plugins.Plugin;

/** This class is to add my fish to pams fish recipes **/
public class PluginHarvestcraft extends Plugin {
    public PluginHarvestcraft(String name) {
        super(name);
    }

    @Override
    public void preInit() {
        return;
    }

    @Override
    public void init() {
        if (Modules.isActive(Modules.fishery)) {
            addShaped(asStack(getItem("fishsandwichItem")), new Object[] { "toolSkillet", "fish", "foodMayo" });
            addShaped(asStack(getItem("fishsticksItem")), new Object[] { "toolBakeware", "fish", "foodFlour" });
            addShaped(asStack(getItem("stockItem")), new Object[] { "toolPot", "fish", "toolMixingbowl" });
            addShaped(asStack(getItem("fishdinnerItem")), new Object[] { "toolSkillet", "cropLemon", "foodFlour", "fish", "foodMayo" });
            addShaped(asStack(getItem("sushiItem")), new Object[] { "toolCuttingboard", "fish", "cropRice" });
            addShaped(asStack(getItem("fishtacoItem")), new Object[] { "toolCuttingboard", "cropLettuce", "fish" });
        }
    }

    @Override
    public void postInit() {
        return;
    }
}
