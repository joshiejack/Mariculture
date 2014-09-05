package joshie.mariculture.magic.enchantments;

import java.util.HashMap;

import joshie.mariculture.core.helpers.EnchantHelper;
import joshie.mariculture.magic.Magic;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EnchantmentHealth extends EnchantmentJewelry {
    public EnchantmentHealth(int i, int weight, EnumEnchantmentType type) {
        super(i, weight, type);
        setName("health");
        minLevel = 30;
        maxLevel = 45;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    private static final HashMap<String, Integer> uses = new HashMap();

    public static void activate(LivingHurtEvent event, EntityPlayer player) {
        int level = EnchantHelper.getEnchantStrength(Magic.health, player);
        if (level > 0) if (player.getHealth() - event.ammount <= 0) {
            player.setHealth(level * 5);
            event.ammount = 0F;
            EnchantHelper.damageItems(Magic.health, player, level);
            event.setCanceled(true);
        }
    }
}
