package joshie.mariculture.diving;

import joshie.lib.helpers.EntityHelper;
import joshie.mariculture.core.helpers.PlayerHelper;
import joshie.mariculture.core.lib.ArmorSlot;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;

public class LifeJacket {
    public static void init(EntityPlayer player) {
        if (player.motionY > -0.15D) {
            if (PlayerHelper.hasArmor(player, ArmorSlot.TOP, Diving.lifejacket)) {
                if (player.isInsideOfMaterial(Material.water)) {
                    player.motionY = 0.25D;
                } else if (EntityHelper.isInWater(player) && !player.isSneaking()) {
                    player.motionY = 0.0D;
                }
            }
        }
    }
}
