package mariculture.magic.enchantments;

import mariculture.Mariculture;
import mariculture.core.helpers.ClientHelper;
import mariculture.core.network.PacketTeleport;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;

public class EnchantmentBlink extends EnchantmentJewelry {
	public EnchantmentBlink(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("blink");
	}

	@Override
	public int getMinEnchantability(int level) {
		return 36;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return super.getMinEnchantability(level) + 50;
	}

	@Override
	public int getMaxLevel() {
		return 3;
	}

	@Override
	public boolean canApplyTogether(Enchantment enchantment) {
		return false;
	}

	public static void sendPacket(EntityClientPlayerMP player) {
		MovingObjectPosition lookAt = player.rayTrace(2000, 1);
		if (lookAt != null && lookAt.typeOfHit == MovingObjectType.BLOCK && ClientHelper.isActivateKeyPressed()) {
			Mariculture.packets.sendToServer(new PacketTeleport(lookAt.blockX, lookAt.blockY + 1, lookAt.blockZ));
		}
	}
}
