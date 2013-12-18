package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.blocks.TileOyster;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Packet103Oyster extends PacketMariculture {
	public int x, y, z, id, meta;
	
	public Packet103Oyster() {}
	
	public Packet103Oyster(int x, int y, int z, int id, int meta) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = id;
		this.meta = meta;
	}
	
	@Override
	public void handle(World world, EntityPlayer player) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileOyster) {
			ItemStack stack = (id > -1) ? new ItemStack(id, 1, meta) : null;
			((TileOyster) tile).setInventorySlotContents(0, stack);;
		}
	}
	
	@Override
	public void read(DataInputStream os) throws IOException {
		x = os.readInt();
		y = os.readInt();
		z = os.readInt();
		id = os.readInt();
		meta = os.readInt();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(x);
		os.writeInt(y);
		os.writeInt(z);
		os.writeInt(id);
		os.writeInt(meta);
	}
}
