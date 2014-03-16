package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.magic.gui.ContainerMirror;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Packet112Enchant extends PacketMariculture {
	public int windowID;
	public int level;
	
	public Packet112Enchant() {}
	
	public Packet112Enchant(int windowID, int level) {
		this.windowID = windowID;
		this.level = level;
	}

	@Override
	public void handle(World world, EntityPlayer player) {		
		if (player.openContainer.windowId == windowID && player.openContainer.isPlayerNotUsingContainer(player)) {
			ContainerMirror mirror = (ContainerMirror) player.openContainer;
			mirror.windowId = windowID;
			mirror.enchantItem(player, level);
			mirror.detectAndSendChanges();
		}
	}
	
	@Override
	public void read(DataInputStream os) throws IOException {
		windowID = os.readInt();
		level = os.readInt();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(windowID);
		os.writeInt(level);
	}
}
