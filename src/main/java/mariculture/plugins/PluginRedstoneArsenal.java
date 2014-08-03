package mariculture.plugins;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.RodType.RodTypeFlux;
import mariculture.core.lib.BaitMeta;
import mariculture.core.lib.Modules;
import mariculture.fishery.Fishery;
import mariculture.plugins.Plugins.Plugin;
import net.minecraft.item.ItemStack;

public class PluginRedstoneArsenal extends Plugin {
    public static RodType INFUSED;

    public PluginRedstoneArsenal(String name) {
        super(name);
    }

    @Override
    public void preInit() {
        return;
    }

    @Override
    public void init() {
        if (Modules.isActive(Modules.fishery)) {
            INFUSED = new RodTypeInfused(75, 1D, 12D, 4.5D, 0);
            Fishing.fishing.registerRod(getItem("tool.fishingRodFlux"), INFUSED);
            Fishing.fishing.addBait(getItem("material", 0), 80);
            Fishing.fishing.addBaitForQuality(new ItemStack(Fishery.bait, 1, BaitMeta.ANT), INFUSED);
            Fishing.fishing.addBaitForQuality(new ItemStack(Fishery.bait, 1, BaitMeta.MAGGOT), INFUSED);
            Fishing.fishing.addBaitForQuality(new ItemStack(Fishery.bait, 1, BaitMeta.BEE), INFUSED);
            Fishing.fishing.addBaitForQuality(getItem("material", 0), INFUSED);
        }
    }

    @Override
    public void postInit() {
        return;
    }

    private static class RodTypeInfused extends RodTypeFlux {
        public RodTypeInfused(int quality, double junk, double good, double rare, int enchantment) {
            super(quality, junk, good, rare, enchantment);
        }

        @Override
        public boolean canUseBaitManually() {
            return false;
        }
    }
}
