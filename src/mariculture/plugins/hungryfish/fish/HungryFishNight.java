package mariculture.plugins.hungryfish.fish;

import mariculture.fishery.fish.FishNight;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class HungryFishNight extends FishNight {
	public HungryFishNight(int id) {
		super(id);
	}

	@Override
	public String getSpecies() {
		return (this.getClass().getSimpleName().toLowerCase()).substring(10);
	}
	
	@Override
	public void onConsumed(World world, EntityPlayer player) {
		if (!world.isDaytime()) {
			player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 1800, 0));
		}

		player.getFoodStats().addStats(0, 0.05F);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
	}
}
