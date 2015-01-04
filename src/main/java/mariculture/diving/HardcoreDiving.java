package mariculture.diving;

import mariculture.api.core.interfaces.IDisablesHardcoreDiving;
import mariculture.core.config.GeneralStuff;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
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
