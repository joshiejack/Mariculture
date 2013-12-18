package mariculture.core.handlers;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;

import mariculture.core.blocks.TileOyster;
import mariculture.core.lib.PacketIds;
import mariculture.core.util.PacketIntegerUpdate;
import mariculture.diving.DivingPackets;
import mariculture.factory.FactoryEvents;
import mariculture.factory.blocks.TileCustom;
import mariculture.factory.blocks.TileFLUDDStand;
import mariculture.factory.blocks.TileTurbineWater;
import mariculture.magic.EnchantPacket;
import mariculture.magic.enchantments.EnchantmentOneRing;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler {
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		EntityPlayer toWhom = (EntityPlayer) player;

		if (packet.channel.equals("Mariculture") && packet.data.length > 1) {
			handlePacket(packet, (EntityClientPlayerMP) toWhom);
		}
	}

	private void handlePacket(Packet250CustomPayload packet, EntityClientPlayerMP player) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		int id;

		try {
			id = inputStream.readInt();
		} catch (final IOException e) {

			LogHandler.log(Level.WARNING, "Mariculture Packet had an error when sending!");
			return;
		}

		switch (id) {
		case PacketIds.ONE_RING:
			EnchantmentOneRing.handlePacket(packet, player);
			break;
		case PacketIds.ENCHANT_CLIENT_UPDATE:
			EnchantPacket.handlePacket(packet, player);
			break;
		case PacketIds.AIR_PRESS_UPDATE:
			DivingPackets.handlePacketCompressor(packet, player.worldObj);
			break;
		case PacketIds.AIR_PRESS_POWER_UPDATE:
			DivingPackets.handlePacketCompressorPower(packet, player.worldObj);
			break;
		case PacketIds.RENDER_OYSTER_UPDATE:
			TileOyster.handleOysterUpdate(packet, player.worldObj);
			break;
		case PacketIds.AIR_PUMP_UPDATE:
			DivingPackets.handlePacketAirPump(packet, player.worldObj);
			break;
		case PacketIds.AIR_PRESS_POWER_UPDATE_ANIMATE:
			DivingPackets.handlePacketCompressorPowerAnimate(packet, player.worldObj);
			break;
		case PacketIds.INTEGER_PACKET:
			PacketIntegerUpdate.handle(packet, player);
			break;
		case PacketIds.TURBINE:
			TileTurbineWater.handlePacket(packet, player.worldObj);
			break;
		case PacketIds.FLUDD_WATER_ANIMATE:
			FactoryEvents.handleFLUDDAnimate(packet, player.worldObj);
			break;
		case PacketIds.FLUDD_STAND:
			TileFLUDDStand.handleLiquidQtyUpdate(packet, player.worldObj);
			break;
		case PacketIds.RENDER_CUSTOM_UPDATE:
			TileCustom.handleUpdateRender(packet, player.worldObj);
		case PacketIds.SPONGE:
			SpongePacket.displayPower(packet, player);
		}
	}
}
