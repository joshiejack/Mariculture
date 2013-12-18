package mariculture.core.handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import mariculture.core.Mariculture;
import mariculture.core.lib.PacketIds;
import mariculture.factory.blocks.TileSponge;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class SpongePacket {
	public static void requestPowerUpdate(EntityClientPlayerMP player, int x, int y, int z) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		try {
			outputStream.writeInt(PacketIds.SPONGE);
			outputStream.writeInt(x);
			outputStream.writeInt(y);
			outputStream.writeInt(z);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
	
		player.sendQueue.addToSendQueue(packet);
	}

	public static void handleRequest(Packet250CustomPayload oldPacket, EntityPlayerMP player) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(oldPacket.data));
		World world = player.worldObj;
		int mode, x, y, z, powerStored = 0, powerMax = 0;

		try {
			inputStream.readInt();
			x = inputStream.readInt();
			y = inputStream.readInt();
			z = inputStream.readInt();
		} catch (IOException e) {
			LogHandler.log(Level.WARNING, "Slight issue when processing Mariculture Sponge request packet");
			return;
		}
		
		if(world.getBlockTileEntity(x, y, z) instanceof TileSponge) {
			TileSponge sponge = (TileSponge) world.getBlockTileEntity(x, y, z);
			powerStored = sponge.getEnergyStored(ForgeDirection.UP);
			powerMax = sponge.getMaxEnergyStored(ForgeDirection.UP);
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream os = new DataOutputStream(bos);
		try {
			os.writeInt(PacketIds.SPONGE);
			os.writeInt(powerStored);
			os.writeInt(powerMax);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
	}

	public static void displayPower(Packet250CustomPayload packet, EntityClientPlayerMP player) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		World world = player.worldObj;
		int powerStored = 0, powerMax = 0;

		try {
			inputStream.readInt();
			powerStored = inputStream.readInt();
			powerMax = inputStream.readInt();
		} catch (IOException e) {
			LogHandler.log(Level.WARNING, "Mariculture Failed at deliving client power!");
			return;
		}
		
		FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(powerStored + " / " + powerMax + " RF");
	}
}
