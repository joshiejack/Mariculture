package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.magic.MirrorHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Packet109DamageJewelry extends PacketMariculture {

	public int enchant, amount;
	
	public Packet109DamageJewelry() {}
	
	public Packet109DamageJewelry(int enchant, int amount) {
		this.enchant = enchant;
		this.amount = amount;
	}
	
	@Override
	public void handle(World world, EntityPlayer player) {
		MirrorHandler.handleDamage(player, enchant, amount);
	}

	@Override
	public void read(DataInputStream is) throws IOException {
		enchant = is.readInt();
		amount = is.readInt();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(enchant);
		os.writeInt(amount);
	}
}
