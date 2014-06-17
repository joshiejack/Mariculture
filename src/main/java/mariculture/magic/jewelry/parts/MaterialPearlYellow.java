package mariculture.magic.jewelry.parts;

import mariculture.api.util.Text;
import mariculture.core.Core;
import mariculture.core.lib.PearlColor;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class MaterialPearlYellow extends JewelryMaterial {
    @Override
    public String getColor() {
        return Text.YELLOW;
    }

    @Override
    public int onAttacked(LivingAttackEvent event, EntityPlayer player) {
        if (event.source == DamageSource.cactus || event.source == DamageSource.fallingBlock || event.source == DamageSource.inWall) {
            event.setCanceled(true);
        }

        return 0;
    }

    @Override
    public int onHurt(LivingHurtEvent event, EntityPlayer player) {
        if (event.source == DamageSource.cactus || event.source == DamageSource.fallingBlock || event.source == DamageSource.inWall) {
            event.setCanceled(true);
            return 16;
        } else return 0;
    }

    @Override
    public int getExtraEnchantments(JewelryType type) {
        return 0;
    }

    @Override
    public int getMaximumEnchantmentLevel(JewelryType type) {
        return 4;
    }

    @Override
    public float getRepairModifier(JewelryType type) {
        return 0.5F;
    }

    @Override
    public float getHitsModifier(JewelryType type) {
        return 2.0F;
    }

    @Override
    public float getDurabilityModifier(JewelryType type) {
        return 0.8F;
    }

    @Override
    public ItemStack getCraftingItem(JewelryType type) {
        return new ItemStack(Core.pearls, 1, PearlColor.YELLOW);
    }
}
