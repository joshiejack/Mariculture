package mariculture.diving;

import mariculture.core.helpers.EntityHelper;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;

public class ScubaFin {
    private static final float SPEED_STANDARD = 0.025F;
    private static final float SPEED_WETSUIT = 0.05F;

    public static void init(EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            if (PlayerHelper.hasArmor(player, ArmorSlot.FEET, Diving.swimfin)) {
                if (EntityHelper.isInWater(player)) {
                    activate(player);
                } else {
                    deactivate(player);
                }
            } else {
                deactivate(player);
            }
        }
    }

    private static void activate(EntityPlayer player) {
        if (!player.capabilities.isFlying) {
            player.capabilities.allowFlying = true;
            player.capabilities.isFlying = true;
            player.getEntityData().setBoolean("scubaFinIsFlying", true);

            if (player.capabilities.getFlySpeed() != SPEED_STANDARD && player.capabilities.getFlySpeed() != SPEED_WETSUIT) {
                player.getEntityData().setFloat("speedBeforeScubaSpin", player.capabilities.getFlySpeed());
            }

            if (PlayerHelper.hasArmor(player, ArmorSlot.LEG, Diving.scubaSuit)) {
                player.capabilities.setFlySpeed(SPEED_WETSUIT);
            } else {
                player.capabilities.setFlySpeed(SPEED_STANDARD);
            }
        }
    }

    private static void deactivate(EntityPlayer player) {
        if (player.getEntityData().getBoolean("scubaFinIsFlying") == true) {
            player.capabilities.allowFlying = false;
            player.capabilities.isFlying = false;
            player.getEntityData().setBoolean("scubaFinIsFlying", false);
            player.capabilities.setFlySpeed(player.getEntityData().getFloat("speedBeforeScubaSpin"));
        }

        if (GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward)) {
            if (PlayerHelper.hasArmor(player, ArmorSlot.FEET, Diving.swimfin)) {
                if (!player.isInWater()) {
                    player.motionX *= 0.25D;
                    player.motionZ *= 0.25D;
                } else {
                    player.motionX *= 1.18D;
                    player.motionZ *= 1.18D;
                }
            }
        }
    }
}
