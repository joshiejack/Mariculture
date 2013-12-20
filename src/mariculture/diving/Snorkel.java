package mariculture.diving;

import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import mariculture.core.lib.Extra;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;

public class Snorkel {
	public static void init(EntityPlayer player) {
		if (PlayerHelper.hasArmor(player, ArmorSlot.HAT, Diving.snorkel)) {
			if (player.isInsideOfMaterial(Material.water)) {
				if (player.worldObj.getWorldTime() % 2 == 0)
					player.setAir(player.getAir() + 1);
			}
		}
	}
}
