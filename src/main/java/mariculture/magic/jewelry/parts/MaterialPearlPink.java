package mariculture.magic.jewelry.parts;

import mariculture.core.Core;
import mariculture.core.lib.PearlColor;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class MaterialPearlPink extends JewelryMaterial {
    @Override
    public String getColor() {
        return mariculture.lib.util.Text.PINK;
    }

    @Override
    public int onAttack(LivingAttackEvent event, EntityPlayer player) {
        if (player.shouldHeal()) {
            player.heal(event.ammount / 5);
            return 3;
        } else return 0;
    }

    @Override
    public int getExtraEnchantments(JewelryType type) {
        return 0;
    }

    @Override
    public int getMaximumEnchantmentLevel(JewelryType type) {
        return 3;
    }

    @Override
    public float getRepairModifier(JewelryType type) {
        return 1.5F;
    }

    @Override
    public float getHitsModifier(JewelryType type) {
        return 3.0F;
    }

    @Override
    public float getDurabilityModifier(JewelryType type) {
        return 0.75F;
    }

    @Override
    public ItemStack getCraftingItem(JewelryType type) {
        return new ItemStack(Core.pearls, 1, PearlColor.PINK);
    }
}
