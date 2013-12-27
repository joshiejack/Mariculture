package mariculture.magic;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.handlers.LogHandler;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.lib.PacketIds;
import mariculture.magic.enchantments.EnchantmentFlight;
import mariculture.magic.enchantments.EnchantmentGlide;
import mariculture.magic.enchantments.EnchantmentHealth;
import mariculture.magic.enchantments.EnchantmentJump;
import mariculture.magic.enchantments.EnchantmentNeverHungry;
import mariculture.magic.enchantments.EnchantmentPoison;
import mariculture.magic.enchantments.EnchantmentRestore;
import mariculture.magic.enchantments.EnchantmentSpeed;
import mariculture.magic.enchantments.EnchantmentSpider;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class EnchantPacket {
	public static void dispatchPacket(EntityClientPlayerMP player, int windowId, int level) {
		final ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		final DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(PacketIds.ENCHANT_MIRROR);
			outputStream.writeInt(windowId);
			outputStream.writeInt(level);
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

	public static void updateActiveEnchantments() {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		for (int j = 0; j < server.worldServers.length; j++) {
			World world = server.worldServers[j];

			for (int i = 0; i < world.playerEntities.size(); i++) {
				EntityPlayer player = (EntityPlayer) world.playerEntities.get(i);

				int speed = EnchantHelper.getEnchantStrength(Magic.speed, player);
				int jump = EnchantHelper.getEnchantStrength(Magic.jump, player);
				int glide = EnchantHelper.getEnchantStrength(Magic.glide, player);
				int flight = EnchantHelper.getEnchantStrength(Magic.flight, player);
				boolean spider = EnchantHelper.hasEnchantment(Magic.spider, player);

				EnchantmentSpeed.set(speed);
				EnchantmentJump.set(jump);
				EnchantmentGlide.set(glide);
				EnchantmentFlight.set(flight);
				EnchantmentSpider.set(spider);

				sendPacketToThisPlayer(player, speed, jump, glide, flight, spider);
				sendPacketSurroundingPlayers(player);

				// Activate fire resistance
				EnchantmentNeverHungry.activate(player);
				EnchantmentHealth.activate(player);
				EnchantmentPoison.activate(player);
				EnchantmentRestore.activate(player);
			}
		}
	}

	private static void sendPacketSurroundingPlayers(EntityPlayer player) {
		/** For the one ring **/
		int thisPlayer = player.entityId;
		boolean invisible = player.isInvisible();

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream os = new DataOutputStream(bos);
		try {
			os.writeInt(PacketIds.ONE_RING);
			os.writeInt(thisPlayer);
			os.writeBoolean(invisible);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToAllAround(player.posX, player.posY, player.posZ, 100, player.worldObj.provider.dimensionId,
				packet);
	}

	private static void sendPacketToThisPlayer(EntityPlayer player, int speed, int jump, int glide, int flight, boolean spider) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream os = new DataOutputStream(bos);
		try {
			os.writeInt(PacketIds.ENCHANT_CLIENT_UPDATE);
			os.writeInt(speed);
			os.writeInt(jump);
			os.writeInt(glide);
			os.writeInt(flight);
			os.writeBoolean(spider);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
	}

	public static void handlePacket(Packet250CustomPayload packet, EntityPlayer player) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		int id;
		int speed;
		int jump;
		int glide;
		int flight;
		boolean spider;

		try {
			id = inputStream.readInt();
			speed = inputStream.readInt();
			jump = inputStream.readInt();
			glide = inputStream.readInt();
			flight = inputStream.readInt();
			spider = inputStream.readBoolean();
		} catch (final IOException e) {
			LogHandler.log(Level.WARNING, "Mariculture ERROR while handling Enchantment Checker Packet");
			return;
		}

		EnchantmentSpeed.set(speed);
		EnchantmentJump.set(jump);
		EnchantmentGlide.set(glide);
		EnchantmentFlight.set(flight);
		EnchantmentSpider.set(spider);
	}

	public static void activateEnchants(EntityPlayer player) {
		EnchantmentFlight.activate(player);
	}
}
