package joshie.maritech.extensions.items;

import joshie.mariculture.core.lib.CraftingMeta;

public class ExtensionCrafting extends ExtensionItemsBase {
    @Override
    public String getName(int meta, String name) {
        switch (meta) {
            case CraftingMeta.NEOPRENE:
                return "neoprene";
            case CraftingMeta.PLASTIC:
                return "plastic";
            case CraftingMeta.LENS:
                return "scubaLens";
            case CraftingMeta.PLASTIC_YELLOW:
                return "plasticYellow";
            case CraftingMeta.DRAGON_EGG:
                return "dragonEgg";
            case CraftingMeta.LIFE_CORE:
                return "lifeCore";
            case CraftingMeta.CREATIVE_BATTERY:
                return "batteryCreative";
        }

        return name;
    }

    @Override
    public String getMod(int meta, String mod) {
        switch (meta) {
            case CraftingMeta.NEOPRENE:
            case CraftingMeta.PLASTIC:
            case CraftingMeta.LENS:
            case CraftingMeta.PLASTIC_YELLOW:
            case CraftingMeta.DRAGON_EGG:
            case CraftingMeta.LIFE_CORE:
            case CraftingMeta.CREATIVE_BATTERY:
                return "maritech";
        }
        
        return mod;
    }
}
