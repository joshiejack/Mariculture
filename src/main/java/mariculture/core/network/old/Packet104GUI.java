package mariculture.core.network.old;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.gui.ContainerMariculture;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Packet104GUI extends PacketMariculture {
	public int windowID;
	public int dataID;
	public int dataVal;
	
	public Packet104GUI() {}
	
	public Packet104GUI(int windowID, int id, int val) {
		this.windowID = windowID;
		this.dataID = id;
		this.dataVal = val;
	}

	@Override
	public void handle(World world, EntityPlayer player) {		
		if (player.openContainer.windowId == windowID && player.openContainer.isPlayerNotUsingContainer(player)) {
			if (player.openContainer instanceof ContainerMariculture) {
				ContainerMariculture container = (ContainerMariculture) player.openContainer;
				container.updateProgressBar(dataID, dataVal);
			}
		}
	}
	
	@Override
	public void read(DataInputStream os) throws IOException {
		windowID = os.readInt();
		dataID = os.readInt();
		dataVal = os.readInt();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(windowID);
		os.writeInt(dataID);
		os.writeInt(dataVal);
	}
}
