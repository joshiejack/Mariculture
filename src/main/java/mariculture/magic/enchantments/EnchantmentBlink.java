package mariculture.magic.enchantments;

import mariculture.core.helpers.KeyHelper;
import mariculture.core.network.Packet108Teleport;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;

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

	public static void sendPacket(EntityPlayer player) {
		MovingObjectPosition lookAt = player.rayTrace(2000, 1);
		if (lookAt != null && lookAt.typeOfHit == EnumMovingObjectType.TILE) {
			if(player instanceof EntityClientPlayerMP)
				((EntityClientPlayerMP)player).sendQueue.addToSendQueue(new Packet108Teleport(lookAt.blockX, lookAt.blockY + 1, lookAt.blockZ, KeyHelper.ACTIVATE_PRESSED).build());
		}
	}
}
