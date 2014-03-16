package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Packet105OneRing extends PacketMariculture {
	public int id;
	public boolean isInvisible;
	
	public Packet105OneRing() {}
	
	public Packet105OneRing(int id, boolean isInvisible) {
		this.id = id;
		this.isInvisible = isInvisible;
	}

	@Override
	public void handle(World world, EntityPlayer player) {
		for (int i = 0; i < player.worldObj.playerEntities.size(); i++) {
			EntityPlayer aPlayer = (EntityPlayer) player.worldObj.playerEntities.get(i);

			if (aPlayer.entityId == id)
				aPlayer.setInvisible(isInvisible);
		}
	}
	
	@Override
	public void read(DataInputStream os) throws IOException {
		id = os.readInt();
		isInvisible = os.readBoolean();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(id);
		os.writeBoolean(isInvisible);
	}
}
