package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.blocks.base.TileMulti;
import mariculture.factory.blocks.TileSponge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class Packet113RequestMaster extends PacketMariculture {

	public int x, y, z, xCoord, yCoord, zCoord;
	boolean isClient;
	
	public Packet113RequestMaster() {}
	
	public Packet113RequestMaster(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.isClient = false;
	}
	
	public Packet113RequestMaster(int xCoord, int yCoord, int zCoord, int x, int y, int z) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.zCoord = zCoord;
		this.x = x;
		this.y = y;
		this.z = z;
		this.isClient = true;
	}
	
	@Override
	public void handle(World world, EntityPlayer player) {
		if(isClient) {
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if(tile != null && tile instanceof TileMulti) {
				TileMulti multi = (TileMulti) tile;
				multi.mstr.built = true;
				multi.mstr.x = xCoord;
				multi.mstr.y = yCoord;
				multi.mstr.z = zCoord;
			}
		} else {
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if(tile != null && tile instanceof TileMulti) {
				TileMulti multi = (TileMulti) tile;
				PacketDispatcher.sendPacketToPlayer(new Packet113RequestMaster(multi.mstr.x, multi.mstr.y, multi.mstr.z, x, y, z).build(), (Player) player);
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
			xCoord = is.readInt();
			yCoord = is.readInt();
			zCoord = is.readInt();
			x = is.readInt();
			y = is.readInt();
			z = is.readInt();
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
			os.writeInt(xCoord);
			os.writeInt(yCoord);
			os.writeInt(zCoord);
			os.writeInt(x);
			os.writeInt(y);
			os.writeInt(z);
		}
	}
}
