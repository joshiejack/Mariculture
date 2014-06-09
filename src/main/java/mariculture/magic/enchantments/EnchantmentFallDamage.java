package mariculture.magic.enchantments;

import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingFallEvent;

public class EnchantmentFallDamage extends EnchantmentJewelry {
    public EnchantmentFallDamage(final int i, final int weight, final EnumEnchantmentType type) {
        super(i, weight, type);
        setName("fall");
        minLevel = 1;
        maxLevel = 35;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    public static void activate(LivingFallEvent event, EntityPlayer player) {
        int damage = (int) (event.distance - 3);
        if (EnchantHelper.hasEnchantment(Magic.fall, player)) {
            int strength = EnchantHelper.getEnchantStrength(Magic.fall, player);
            int maxDistanceAbsorbed = strength * 10;
            if (damage < maxDistanceAbsorbed) {
                if (damage > 0) {
                    EnchantHelper.damageItems(Magic.fall, player, damage / 5);
                }
                event.setCanceled(true);
            } else {
                event.distance = event.distance - maxDistanceAbsorbed;
                if (damage > 0) {
                    EnchantHelper.damageItems(Magic.fall, player, (int) (event.distance / 5));
                }
            }
        }
    }
}
