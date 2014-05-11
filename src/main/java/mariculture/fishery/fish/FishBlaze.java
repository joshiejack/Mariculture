package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.core.lib.Items.blazePowder;
import static mariculture.core.lib.Items.dropletNether;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishBlaze extends FishSpecies {
	public FishBlaze(int id) {
		super(id);
	}
	
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 50, 100 };
	}
	
	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { FRESH };
	}
	
	@Override
	public boolean isLavaFish() {
		return true;
	}

	@Override
	public boolean isDominant() {
		return false;
	}

	@Override
	public int getLifeSpan() {
		return 15;
	}

	@Override
	public int getFertility() {
		return 350;
	}

	@Override
	public int getWaterRequired() {
		return 125;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletNether, 10D);
		addProduct(blazePowder, 5.0D);
	}

	@Override
	public double getFishOilVolume() {
		return 1.0D;
	}

	@Override
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(Item.blazeRod);
	}

	@Override
	public int getLiquifiedProductChance() {
		return 20;
	}

	@Override
	public int getFishMealSize() {
		return 2;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.setFire(7);
	}

	@Override
	public int getLightValue() {
		return 1;
	}

	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.SUPER;
	}

	@Override
	public boolean isWorldCorrect(World world) {
		return world.provider.isHellWorld;
	}

	@Override
	public int getCatchChance() {
		return 20;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return height >= 98 && height <= 102? 5.0D: 0D;
	}
}
