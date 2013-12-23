package hungryfish.fish;

import mariculture.fishery.fish.FishTang;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class HungryFishTang extends FishTang {
	public HungryFishTang(int id) {
		super(id);
	}

	@Override
	public String getSpecies() {
		return (this.getClass().getSimpleName().toLowerCase()).substring(10);
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.getFoodStats().addStats(1, 0.05F);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
	}
}
