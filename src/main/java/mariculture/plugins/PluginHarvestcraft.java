package mariculture.plugins;

import static mariculture.core.helpers.RecipeHelper._;
import static mariculture.core.helpers.RecipeHelper.addShaped;
import mariculture.core.lib.Modules;
import mariculture.plugins.Plugins.Plugin;

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
            addShaped(_(getItem("fishsandwichItem")), new Object[] { "toolSkillet", "fish", "foodMayo" });
            addShaped(_(getItem("fishsticksItem")), new Object[] { "toolBakeware", "fish", "foodFlour" });
            addShaped(_(getItem("stockItem")), new Object[] { "toolPot", "fish", "toolMixingbowl" });
            addShaped(_(getItem("fishdinnerItem")), new Object[] { "toolSkillet", "cropLemon", "foodFlour", "fish", "foodMayo" });
            addShaped(_(getItem("sushiItem")), new Object[] { "toolCuttingboard", "fish", "cropRice" });
            addShaped(_(getItem("fishtacoItem")), new Object[] { "toolCuttingboard", "cropLettuce", "fish" });
        }
    }

    @Override
    public void postInit() {
        return;
    }
}
