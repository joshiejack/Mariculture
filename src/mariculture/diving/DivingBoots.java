package mariculture.diving;

import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class DivingBoots {
	public static void init(EntityPlayer player) {
		if(GameSettings.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindForward)) {
			if(player.isInWater() && !player.capabilities.isFlying) {
				if(PlayerHelper.hasArmor(player, ArmorSlot.FEET, Diving.divingBoots)) {
					player.motionX *= 1.13D;
			        player.motionY *= 0.98D;
			        player.motionZ *= 1.13D;
				}
			}
		}
	}
}
