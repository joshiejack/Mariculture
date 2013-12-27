package mariculture.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.lib.PacketIds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketIntegerUpdate {
	public int id = PacketIds.INTEGER_PACKET;
	public int windowId;
	public int progressBar;
	public int progressBarValue;

	public PacketIntegerUpdate() {
	}

	public PacketIntegerUpdate(int id, int bar, int barVal) {
		this.windowId = id;
		this.progressBar = bar;
		this.progressBarValue = barVal;
	}

	public void readPacketData(DataInputStream par1DataInputStream) throws IOException {
		this.windowId = par1DataInputStream.readByte();
		this.progressBar = par1DataInputStream.readInt();
		this.progressBarValue = par1DataInputStream.readInt();
	}

	public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException {
		par1DataOutputStream.writeByte(this.windowId);
		par1DataOutputStream.writeShort(this.progressBar);
		par1DataOutputStream.writeShort(this.progressBarValue);
	}

	public static void handle(Packet250CustomPayload packet, EntityPlayer playerEntity) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		int id;
		int windowId;
		int progressBar;
		int progressBarValue;

		try {
			id = inputStream.readInt();
			windowId = inputStream.readByte();
			progressBar = inputStream.readInt();
			progressBarValue = inputStream.readInt();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		if (playerEntity.openContainer.windowId == windowId && playerEntity.openContainer.isPlayerNotUsingContainer(playerEntity)) {
			if (playerEntity.openContainer instanceof ContainerInteger) {
				ContainerInteger container = (ContainerInteger) playerEntity.openContainer;
				container.updateProgressBar(progressBar, progressBarValue);
			}
		}
	}

	public static void send(ContainerInteger container, int progressBar, int progressBarValue,
			EntityPlayer player) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream os = new DataOutputStream(bos);
		try {
			os.writeInt(PacketIds.INTEGER_PACKET);
			os.writeByte(container.windowId);
			os.writeInt(progressBar);
			os.writeInt(progressBarValue);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
	}
}
