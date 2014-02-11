package mariculture.core.network;

import ibxm.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.factory.blocks.TileSponge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.client.FMLClientHandler;

public class Packet101Sponge extends PacketMariculture {

	public int x, y, z, stored, max;
	boolean isClient;
	
	public Packet101Sponge() {}
	
	public Packet101Sponge(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.isClient = false;
	}
	
	public Packet101Sponge(int stored, int max) {
		this.stored = stored;
		this.max = max;
		this.isClient = true;
	}
	
	@Override
	public void handle(World world, EntityPlayer player) {
		if(isClient) {
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(stored + " / " + max + " RF");
		} else {
			if(world.getTileEntity(x, y, z) instanceof TileSponge) {
				TileSponge sponge = (TileSponge) world.getTileEntity(x, y, z);
				stored = sponge.getEnergyStored(ForgeDirection.UNKNOWN);
				max = sponge.getMaxEnergyStored(ForgeDirection.UNKNOWN);
				PacketDispatcher.sendPacketToPlayer(new Packet101Sponge(stored, max).build(), (Player) player);
			}
		}
	}

	@Override
	public void read(DataInputStream is) throws IOException {
		isClient = is.readBoolean();
		if(!isClient) {
			x = is.readInt();
			y = is.readInt();
			z = is.readInt();
		} else {
			stored = is.readInt();
			max = is.readInt();
		}
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		if(!isClient) {
			os.writeBoolean(isClient);
			os.writeInt(x);
			os.writeInt(y);
			os.writeInt(z);
		} else {
			os.writeBoolean(isClient);
			os.writeInt(max);
			os.writeInt(stored);
		}
	}
}
