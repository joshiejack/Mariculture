package mariculture.core.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import mariculture.core.helpers.MirrorHelper;
import mariculture.core.lib.PacketIds;
import mariculture.factory.FactoryEvents;
import mariculture.magic.MirrorHandler;
import mariculture.magic.enchantments.EnchantmentBlink;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ServerPacketHandler implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		EntityPlayer sender = (EntityPlayer) player;

		if (packet.channel.equals("Mariculture")) {
			handlePacket(packet, (EntityPlayerMP) sender);
		}
	}

	private void handlePacket(Packet250CustomPayload packet, EntityPlayerMP playerEntity) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		int id;

		try {
			id = inputStream.readInt();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		switch (id) {
		case PacketIds.ENCHANT_MIRROR:
			MirrorHelper.enchant(packet, playerEntity);
			break;
		case PacketIds.TELEPORT:
			EnchantmentBlink.handlePacket(packet, playerEntity);
			break;
		case PacketIds.DAMAGE_ITEM:
			MirrorHandler.handleDamagePacket(packet, playerEntity);
			break;
		case PacketIds.FLUDD_SQUIRT:
			FactoryEvents.handlePacket(playerEntity);
			break;
		case PacketIds.DAMAGE_FLUDD:
			FactoryEvents.handleDamagePacket(packet, playerEntity);
			break;
		case PacketIds.FLUDD_WATER_ANIMATE_SERVER:
			FactoryEvents.sendWaterAnimateClient(packet, playerEntity.worldObj);
			break;
		case PacketIds.SWAP_JEWELRY:
			MirrorHandler.switchJewelry(packet, playerEntity);
			break;
		case PacketIds.SPONGE:
			SpongePacket.handleRequest(packet, playerEntity);
		}
	}
}