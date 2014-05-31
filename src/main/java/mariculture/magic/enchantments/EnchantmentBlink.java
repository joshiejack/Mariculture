package mariculture.magic.enchantments;

import mariculture.core.helpers.ClientHelper;
import mariculture.core.network.PacketHandler;
import mariculture.core.network.PacketTeleport;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class EnchantmentBlink extends EnchantmentJewelry {
	public EnchantmentBlink(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		setName("blink");
		minLevel = 25;
		maxLevel = 40;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	public static void sendPacket(EntityClientPlayerMP player) {
		MovingObjectPosition lookAt = player.rayTrace(2000, 1);
		if (lookAt != null && lookAt.typeOfHit == MovingObjectType.BLOCK && ClientHelper.isActivateKeyPressed()) {
			PacketHandler.sendToServer(new PacketTeleport(lookAt.blockX, lookAt.blockY + 1, lookAt.blockZ));
		}
	}
}
