package joshie.maritech.nei;

import joshie.mariculture.core.lib.Modules;
import joshie.maritech.extensions.modules.ExtensionFactory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

public class MTNEIConfig implements IConfigureNEI {
    @Override
    public void loadConfig() {
        if (Modules.isActive(Modules.factory)) {
            API.hideItem(new ItemStack(ExtensionFactory.customRFBlock, 1, OreDictionary.WILDCARD_VALUE));
        }
    }

    @Override
    public String getName() {
        return "MariTech NEI";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }
}
