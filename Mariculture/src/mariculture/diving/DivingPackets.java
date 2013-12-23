package mariculture.diving;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.blocks.TileAirPump;
import mariculture.core.lib.PacketIds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class DivingPackets {
	public static void sendPacketCompressor(int x, int y, int z, int currentAir, int dimID) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream os = new DataOutputStream(bos);

		try {
			os.writeInt(PacketIds.AIR_PRESS_UPDATE);
			os.writeInt(x);
			os.writeInt(y);
			os.writeInt(z);
			os.writeInt(currentAir);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToAllAround(x, y, z, 32, dimID, packet);
	}

	public static void handlePacketCompressor(Packet250CustomPayload packet, World world) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		int id;
		int x;
		int y;
		int z;
		int air;

		try {
			id = inputStream.readInt();
			x = inputStream.readInt();
			y = inputStream.readInt();
			z = inputStream.readInt();
			air = inputStream.readInt();

		} catch (IOException e) {
			e.printStackTrace(System.err);
			return;
		}

		final TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileAirCompressor) {
			((TileAirCompressor) tile).currentAir = air;
		}
	}

	public static void sendPacketCompressorPower(int x, int y, int z, int charge, int dimID) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream os = new DataOutputStream(bos);

		try {
			os.writeInt(PacketIds.AIR_PRESS_POWER_UPDATE);
			os.writeInt(x);
			os.writeInt(y);
			os.writeInt(z);
			os.writeInt(charge);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToAllAround(x, y, z, 32, dimID, packet);
	}

	public static void handlePacketCompressorPower(Packet250CustomPayload packet, World world) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		int id;
		int x;
		int y;
		int z;
		int charge;

		try {
			id = inputStream.readInt();
			x = inputStream.readInt();
			y = inputStream.readInt();
			z = inputStream.readInt();
			charge = inputStream.readInt();

		} catch (final IOException e) {
			e.printStackTrace(System.err);
			return;
		}

		final TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileAirCompressorPower) {
			((TileAirCompressorPower) tile).currentCharge = charge;
		}
	}

	public static void handlePacketAirPump(Packet250CustomPayload packet, World world) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		int id;
		int x;
		int y;
		int z;
		final int charge;

		try {
			id = inputStream.readInt();
			x = inputStream.readInt();
			y = inputStream.readInt();
			z = inputStream.readInt();

		} catch (final IOException e) {
			e.printStackTrace(System.err);
			return;
		}

		final TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileAirPump) {
			((TileAirPump) tile).animate = true;
		}
	}

	public static void handlePacketCompressorPowerAnimate(Packet250CustomPayload packet, World world) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		int id;
		int x;
		int y;
		int z;
		final int charge;

		try {
			id = inputStream.readInt();
			x = inputStream.readInt();
			y = inputStream.readInt();
			z = inputStream.readInt();

		} catch (final IOException e) {
			e.printStackTrace(System.err);
			return;
		}

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileAirCompressorPower) {
			((TileAirCompressorPower) tile).animate = true;
		}
	}
}
