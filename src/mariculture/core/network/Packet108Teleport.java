package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.Mariculture;
import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Packet108Teleport extends PacketMariculture {

	boolean keyPressed;
	int x, y, z;

	public Packet108Teleport() {}
	
	public Packet108Teleport(int x, int y, int z, boolean keyPressed) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.keyPressed = keyPressed;
	}

	@Override
	public void handle(World world, EntityPlayer player) {
		if (EnchantHelper.hasEnchantment(Magic.blink, player)) {
			if (player.getCurrentEquippedItem() == null && keyPressed || 
					(keyPressed && EnchantHelper.getEnchantStrength(Magic.blink, player) > 2)) {
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

	@Override
	public void read(DataInputStream is) throws IOException {
		x = is.readInt();
		y = is.readInt();
		z = is.readInt();
		keyPressed = is.readBoolean();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(x);
		os.writeInt(y);
		os.writeInt(z);
		os.writeBoolean(keyPressed);
	}
}
