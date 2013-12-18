package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.magic.enchantments.EnchantmentFlight;
import mariculture.magic.enchantments.EnchantmentGlide;
import mariculture.magic.enchantments.EnchantmentJump;
import mariculture.magic.enchantments.EnchantmentSpeed;
import mariculture.magic.enchantments.EnchantmentSpider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Packet111UpdateEnchants extends PacketMariculture {
	public int speed, jump, glide, flight;
	public boolean spider;
	
	public Packet111UpdateEnchants() {}
	
	public Packet111UpdateEnchants(int speed, int jump, int glide, int flight, boolean spider) {
		this.speed = speed;
		this.jump = jump;
		this.glide = glide;
		this.flight = flight;
		this.spider = spider;
	}

	@Override
	public void handle(World world, EntityPlayer player) {		
		EnchantmentSpeed.set(speed);
		EnchantmentJump.set(jump);
		EnchantmentGlide.set(glide);
		EnchantmentFlight.set(flight);
		EnchantmentSpider.set(spider);
	}
	
	@Override
	public void read(DataInputStream os) throws IOException {
		speed = os.readInt();
		jump = os.readInt();
		glide = os.readInt();
		flight = os.readInt();
		spider = os.readBoolean();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeInt(speed);
		os.writeInt(jump);
		os.writeInt(glide);
		os.writeInt(flight);
		os.writeBoolean(spider);
	}
}
