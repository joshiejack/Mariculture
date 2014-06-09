package mariculture.magic.jewelry.parts;

import mariculture.core.Core;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.util.Text;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BindingGold extends JewelryBinding {
    @Override
    public String getColor() {
        return Text.YELLOW;
    }

    @Override
    public int getHitsBase(JewelryType type) {
        switch (type) {
            case RING:
                return 50;
            case BRACELET:
                return 100;
            case NECKLACE:
                return 150;
            default:
                return 100;
        }
    }

    @Override
    public int getDurabilityBase(JewelryType type) {
        switch (type) {
            case RING:
                return 75;
            case BRACELET:
                return 200;
            case NECKLACE:
                return 450;
            default:
                return 150;
        }
    }

    @Override
    public ItemStack getCraftingItem(JewelryType type) {
        switch (type) {
            case RING:
                return new ItemStack(Items.gold_ingot);
            case BRACELET:
                return new ItemStack(Core.crafting, 1, CraftingMeta.GOLDEN_SILK);
            case NECKLACE:
                return new ItemStack(Core.crafting, 1, CraftingMeta.GOLDEN_THREAD);
            default:
                return null;
        }
    }

    @Override
    public int getKeepEnchantmentChance(JewelryType type) {
        switch (type) {
            case RING:
                return 95;
            case BRACELET:
                return 85;
            case NECKLACE:
                return 75;
            default:
                return 95;
        }
    }

    @Override
    public int getMaxEnchantmentLevel(JewelryType type) {
        switch (type) {
            case RING:
                return 3;
            case BRACELET:
                return 4;
            case NECKLACE:
                return 5;
            default:
                return 5;
        }
    }
}
