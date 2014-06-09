package mariculture.magic.enchantments;

import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;

public class EnchantmentFlight extends EnchantmentJewelry {
    public EnchantmentFlight(int i, int weight, EnumEnchantmentType type) {
        super(i, weight, type);
        setName("flight");
        minLevel = 55;
        maxLevel = 60;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    public static int mode = 0;
    public static int maxMode = 0;
    private static int damageTicker = 0;

    public static void damage(EntityPlayer player) {
        if (player.getEntityData().hasKey("SupermanIsFlying")) if (player.capabilities.isFlying && !player.capabilities.isCreativeMode) {
            damageTicker++;
            if (damageTicker >= 300) {
                damageTicker = 0;
                player.fallDistance = 0F;
                EnchantHelper.damageItems(Magic.flight, player, 1);
            }
        }
    }

    public static void set(int flight, EntityPlayer player) {
        maxMode = flight - 1;
        if (!player.capabilities.isCreativeMode) if (maxMode > 0) {
            float flightSpeed = (mode + 1) * 0.025F;
            player.getEntityData().setBoolean("SupermanIsFlying", true);
            player.capabilities.allowFlying = true;
            player.capabilities.setFlySpeed(flightSpeed);
            player.fallDistance = 0F;
        } else // Deactivate
        if (player.getEntityData().hasKey("SupermanIsFlying")) {
            player.getEntityData().removeTag("SupermanIsFlying");
            player.capabilities.allowFlying = false;
            player.capabilities.isFlying = false;
            player.capabilities.setFlySpeed(0.05F);
            mode = 0;
        }
    }
}
