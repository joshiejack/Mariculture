package mariculture.fishery.fish;

import java.util.Random;

import cofh.api.energy.IEnergyHandler;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.util.PowerHelper;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class FishElectricRay extends FishSpecies {
	public FishElectricRay(int id) {
		super(id);
	}

	Random rand = new Random();

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.FLATFISH;
	}

	@Override
	public int getLifeSpan() {
		return 37;
	}

	@Override
	public int getFertility() {
		return 185;
	}

	@Override
	public boolean isDominant() {
		return true;
	}
	
	@Override
	public ItemStack getProduct(Random rand) {
		return (rand.nextInt(25) == 0)? new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ELECTRIC): null;
	}

	@Override
	public boolean canCatch(Random rand, World world, int x, int y, int z, EnumRodQuality quality) {
		return false;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.450;
	}

	@Override
	public void affectWorld(World world, int x, int y, int z, int tankType) {
		if (rand.nextInt(30) == 0) {
			world.addWeatherEffect(new EntityLightningBolt(world, x + (rand.nextInt(5) - rand.nextInt(10)), y, z
					+ (rand.nextInt(5) - rand.nextInt(10))));
		}
		
		if(PowerHelper.isEnergyHandler(world, x, y - 1, z) != null) {
			IEnergyHandler handler = PowerHelper.isEnergyHandler(world, x, y - 1, z);
			if(handler.canInterface(ForgeDirection.DOWN)) {
				(handler).receiveEnergy(ForgeDirection.UP, 200, false);
			}
		}
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 3 };
	}
	
	@Override
	public int getFishMealSize() {
		return 3;
	}
}
