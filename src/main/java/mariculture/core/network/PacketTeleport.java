package mariculture.core.network;

import mariculture.Mariculture;
import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;

public class PacketTeleport extends PacketCoords {
	public PacketTeleport() {
	}

	public PacketTeleport(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public void handle(Side side, EntityPlayer player) {
		if (EnchantHelper.hasEnchantment(Magic.blink, player)) {
			if (player.worldObj.isAirBlock(x, y + 1, z) && player.worldObj.isAirBlock(x, y + 2, z)) {
				int distanceX, distanceZ, distanceY;

				if (y + 1 > player.posY) {
					distanceY = (int) (y + 1 - player.posY);
				} else {
					distanceY = (int) player.posY - y + 1;
				}

				distanceY = distanceY * distanceY;

				if (x > player.posX) {
					distanceX = (int) (x - player.posX);
				} else {
					distanceX = (int) (player.posX - x);
				}

				distanceX = distanceX * distanceX;

				if (z > player.posZ) {
					distanceZ = (int) (z - player.posZ);
				} else {
					distanceZ = (int) (player.posZ - z);
				}

				distanceZ = distanceZ * distanceZ;

				int sum = distanceZ + distanceX + distanceY;
				int distance = ((int) Math.cbrt(sum) + 6);
				int maxDistance = EnchantHelper.getEnchantStrength(Magic.blink, player) * 16;

				if (distance <= maxDistance) {
					player.worldObj.playSoundAtEntity(player, Mariculture.modid + ":blink", 1.0F, 1.0F);
					player.setPositionAndUpdate(x, y, z);
					player.worldObj.playSoundAtEntity(player, Mariculture.modid + ":blink", 1.0F, 1.0F);
					EnchantHelper.damageItems(Magic.blink, player, 1);
				}
			}
		}
	}
}
