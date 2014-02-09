package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.core.helpers.MirrorHelper;
import mariculture.magic.Magic;
import mariculture.magic.jewelry.ItemJewelry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Packet106JewelrySwap extends PacketMariculture {
	public int slot;
	
	public Packet106JewelrySwap() {}
	
	public Packet106JewelrySwap(int slot) {
		this.slot = slot;
	}

	@Override
	public void handle(World world, EntityPlayer player) {
		ItemStack[] mirror = MirrorHelper.instance().get(player);
		ItemStack equipped = player.getCurrentEquippedItem();

		if (equipped == null || (equipped != null && equipped.getItem() instanceof ItemJewelry)) {
			if (equipped != null) {
				slot = equipped.itemID == Magic.ring.itemID ? 0 : slot;
				slot = equipped.itemID == Magic.bracelet.itemID ? 1 : slot;
				slot = equipped.itemID == Magic.necklace.itemID ? 2 : slot;
			}

			if (slot > -1) {
				ItemStack inMirror = mirror[slot];
				mirror[slot] = equipped;
				player.setCurrentItemOrArmor(0, inMirror);
				MirrorHelper.instance().save(player, mirror);
			}
		}
	}
	
	@Override
	public void read(DataInputStream os) throws IOException {
		slot = os.readInt();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(slot);
	}
}
