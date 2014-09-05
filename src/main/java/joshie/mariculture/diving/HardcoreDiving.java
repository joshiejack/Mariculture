package joshie.mariculture.diving;

import joshie.mariculture.api.util.IDisablesHardcoreDiving;
import joshie.mariculture.core.config.GeneralStuff;
import joshie.mariculture.core.helpers.PlayerHelper;
import joshie.mariculture.core.lib.ArmorSlot;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class HardcoreDiving {
    public static void init(EntityPlayer player) {
        if (player.getAir() > 0) {
            Item item = PlayerHelper.getArmor(player, ArmorSlot.HAT);
            if (item == null || item != null && !(item instanceof IDisablesHardcoreDiving)) if (player.isInsideOfMaterial(Material.water)) {
                player.setAir(player.getAir() - GeneralStuff.HARDCORE_DIVING);
            }
        }
    }
}
