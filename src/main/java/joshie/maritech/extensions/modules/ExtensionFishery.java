package joshie.maritech.extensions.modules;

import joshie.mariculture.api.fishery.Fishing;
import joshie.mariculture.api.fishery.Loot;
import joshie.mariculture.api.fishery.Loot.Rarity;
import joshie.mariculture.api.fishery.RodType;
import joshie.mariculture.core.lib.Modules;
import joshie.maritech.util.IModuleExtension;
import net.minecraft.item.ItemStack;

public class ExtensionFishery implements IModuleExtension {
    @Override
    public void preInit() {
        return;
    }

    @Override
    public void init() {
        return;
    }

    @Override
    public void postInit() {
        if (Modules.isActive(Modules.factory)) {
            Fishing.fishing.addLoot(new Loot(new ItemStack(ExtensionFactory.fludd), 1D, Rarity.RARE, 0, RodType.SUPER));
        }
    }
}
