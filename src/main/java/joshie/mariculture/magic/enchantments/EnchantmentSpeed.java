package joshie.mariculture.magic.enchantments;

import joshie.lib.helpers.ClientHelper;
import joshie.mariculture.core.config.Enchantments;
import joshie.mariculture.core.helpers.EnchantHelper;
import joshie.mariculture.magic.Magic;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;

public class EnchantmentSpeed extends EnchantmentJewelry {
    public EnchantmentSpeed(int i, int weight, EnumEnchantmentType type) {
        super(i, weight, type);
        setName("speed");
        minLevel = 1;
        maxLevel = 35;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    private static float runSpeed = 0;
    private static int damageTicker = 0;

    public static void activate(EntityPlayer player) {
        if (runSpeed > 0 && player.onGround && !player.isInWater() && player.isSprinting() && ClientHelper.isForwardPressed()) {
            player.moveFlying(0F, 1.0F, runSpeed);

            damageTicker++;
            if (damageTicker % Enchantments.SPEED_TICKS == 0) {
                EnchantHelper.damageItems(Magic.speed, player, 1);
            }
        }
    }

    public static void set(int speed) {
        if (speed > 0) {
            runSpeed = (float) (Enchantments.SPEED_FACTOR * speed);
        } else {
            runSpeed = 0;
        }
    }
}
