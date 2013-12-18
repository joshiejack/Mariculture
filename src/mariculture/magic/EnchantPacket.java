package mariculture.magic;

import mariculture.core.helpers.EnchantHelper;
import mariculture.core.network.Packet105OneRing;
import mariculture.core.network.Packet111UpdateEnchants;
import mariculture.core.network.Packets;
import mariculture.magic.enchantments.EnchantmentFlight;
import mariculture.magic.enchantments.EnchantmentGlide;
import mariculture.magic.enchantments.EnchantmentHealth;
import mariculture.magic.enchantments.EnchantmentJump;
import mariculture.magic.enchantments.EnchantmentNeverHungry;
import mariculture.magic.enchantments.EnchantmentPoison;
import mariculture.magic.enchantments.EnchantmentRestore;
import mariculture.magic.enchantments.EnchantmentSpeed;
import mariculture.magic.enchantments.EnchantmentSpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class EnchantPacket {
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

				PacketDispatcher.sendPacketToPlayer(new Packet111UpdateEnchants(speed, jump, glide, flight, spider).build(), (Player) player);
				Packets.updatePlayer(player, 128, new Packet105OneRing(player.entityId, player.isInvisible()).build());

				// Activate fire resistance
				EnchantmentNeverHungry.activate(player);
				EnchantmentHealth.activate(player);
				EnchantmentPoison.activate(player);
				EnchantmentRestore.activate(player);
			}
		}
	}

	public static void activateEnchants(EntityPlayer player) {
		EnchantmentFlight.activate(player);
	}
}
