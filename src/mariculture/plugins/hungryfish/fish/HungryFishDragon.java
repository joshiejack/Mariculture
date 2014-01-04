package mariculture.plugins.hungryfish.fish;

import mariculture.fishery.fish.FishDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class HungryFishDragon extends FishDragon {
	public HungryFishDragon(int id) {
		super(id);
	}

	@Override
	public String getSpecies() {
		return (this.getClass().getSimpleName().toLowerCase()).substring(10);
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.getFoodStats().addStats(3, 0.7F);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 200, 1));
	}
}
