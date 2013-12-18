package mariculture.magic.enchantments;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Mariculture;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.KeyHelper;
import mariculture.core.lib.PacketIds;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;

public class EnchantmentBlink extends EnchantmentJewelry {
	public EnchantmentBlink(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		this.setName("blink");
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		if(stack.getItem() instanceof ItemJewelry) {
			return true;
		}
		
		if(EnumEnchantmentType.weapon.canEnchantItem(stack.getItem())) {
			return true;
		}
		
		return false;
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
		MovingObjectPosition lookAt;
		lookAt = player.rayTrace(2000, 1);

		if (lookAt != null && lookAt.typeOfHit == EnumMovingObjectType.TILE) {
			dispatchPacket((EntityClientPlayerMP) player, lookAt.blockX, lookAt.blockY, lookAt.blockZ);
		}
	}

	private static void dispatchPacket(EntityClientPlayerMP player, int x, int y, int z) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(PacketIds.TELEPORT);
			outputStream.writeInt(x);
			outputStream.writeInt(y + 1);
			outputStream.writeInt(z);
			outputStream.writeBoolean(KeyHelper.ACTIVATE_PRESSED);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		EntityClientPlayerMP client = player;
		client.sendQueue.addToSendQueue(packet);
	}

	public static void handlePacket(Packet250CustomPayload packet, EntityPlayerMP player) {
		if (EnchantHelper.hasEnchantment(Magic.blink, player)) {
			DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

			int id;
			int x;
			int y;
			int z;
			boolean isKeyPressed;

			try {
				id = inputStream.readInt();
				x = inputStream.readInt();
				y = inputStream.readInt();
				z = inputStream.readInt();
				isKeyPressed = inputStream.readBoolean();

			} catch (final IOException e) {
				e.printStackTrace();
				return;
			}

			if (player.getCurrentEquippedItem() == null
					&& isKeyPressed
					|| (isKeyPressed && EnchantHelper.getEnchantStrength(Magic.blink, player) > 2)) {
				if (player.worldObj.isAirBlock(x, y + 1, z) && player.worldObj.isAirBlock(x, y + 2, z)) {
					int distanceX;
					int distanceZ;
					int distanceY;

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
					int maxDistance = EnchantHelper.getEnchantStrength(Magic.blink, player) * 10;

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
}
