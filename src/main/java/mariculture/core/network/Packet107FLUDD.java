package mariculture.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mariculture.Mariculture;
import mariculture.factory.EntityFLUDDSquirt;
import mariculture.factory.FactoryEvents;
import mariculture.factory.items.ItemArmorFLUDD;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class Packet107FLUDD extends PacketMariculture {

	public enum PacketType {
		ANIMATE(0), SQUIRT(1), DAMAGE(2);
		
		public int id;
		PacketType(int id) {
			this.id = id;
		}
	}
	
	public int mode, id, type;
	boolean isClient;
	
	public Packet107FLUDD() {}
	
	public Packet107FLUDD(boolean isClient, int mode, int id, PacketType type) {
		this.mode = mode;
		this.id = id;
		this.isClient = isClient;
		this.type = type.id;
	}
	
	@Override
	public void handle(World world, EntityPlayer player) {
		if(type == PacketType.ANIMATE.id) {
			if(isClient) {
				EntityPlayer entity = (EntityPlayer) world.getEntityByID(id);
				entity.fallDistance *= 0.5;
				FactoryEvents.playSmoke(mode, player, false);
			} else {
				Packets.updatePlayer(player, 128, new Packet107FLUDD(true, id, mode, PacketType.ANIMATE).build());
			}
		} else if(type == PacketType.SQUIRT.id) {
			world.playSoundAtEntity(player, Mariculture.modid + ":fludd", 1.0F, 1.0F);
			EntityFLUDDSquirt rocket = new EntityFLUDDSquirt(world, player, true);
			rocket.posY += 0.35F;
			world.spawnEntityInWorld(rocket);
			FactoryEvents.damageFLUDD(player, ItemArmorFLUDD.SQUIRT);
		} else if (type == PacketType.DAMAGE.id) {
			FactoryEvents.damageFLUDD(player, mode);
		}
	}

	@Override
	public void read(DataInputStream is) throws IOException {
		isClient = is.readBoolean();
		mode = is.readInt();
		id = is.readInt();
		type = is.readInt();
	}

	@Override
	public void write(DataOutputStream os) throws IOException {
		os.writeBoolean(isClient);
		os.writeInt(mode);
		os.writeInt(id);
		os.writeInt(type);
	}
}
