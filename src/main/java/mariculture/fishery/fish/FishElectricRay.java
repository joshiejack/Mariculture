package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.FRESH;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.ItemLib.dropletFlux;
import static mariculture.core.lib.ItemLib.dropletRegen;
import static mariculture.core.lib.ItemLib.dropletWater;

import java.util.ArrayList;

import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.CachedCoords;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.util.PowerHelper;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

public class FishElectricRay extends FishSpecies {
	public FishElectricRay(int id) {
		super(id);
	}
	
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { 5, 21 };
	}
	
	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { SALINE, BRACKISH, FRESH };
	}
	
	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public int getLifeSpan() {
		return 20;
	}

	@Override
	public int getFertility() {
		return 4000;
	}

	@Override
	public int getFoodConsumption() {
		return 2;
	}

	@Override
	public int getWaterRequired() {
		return 175;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 5D);
		addProduct(dropletRegen, 1D);
		addProduct(dropletFlux, 4D);
	}

	@Override
	public double getFishOilVolume() {
		return 6.675D;
	}
	
	@Override
	public int getFishMealSize() {
		return 5;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		world.addWeatherEffect(new EntityLightningBolt(world, player.posX,player.posY, player.posZ));
	}

	@Override
	public void affectWorld(World world, int x, int y, int z, ArrayList<CachedCoords> coords) {
		if (world.rand.nextInt(512) == 0) {
			world.addWeatherEffect(new EntityLightningBolt(world, x + (world.rand.nextInt(5) - world.rand.nextInt(10)), y, z + (world.rand.nextInt(5) - world.rand.nextInt(10))));
		}
		
		if(PowerHelper.isEnergyHandler(world, x, y - 1, z) != null) {
			IEnergyHandler handler = PowerHelper.isEnergyHandler(world, x, y - 1, z);
			if(handler.canConnectEnergy(ForgeDirection.DOWN)) {
				(handler).receiveEnergy(ForgeDirection.UP, 200, false);
			}
		}
	}

	@Override
	public RodType getRodNeeded() {
		return RodType.FLUX;
	}

	@Override
	public int getCatchChance() {
		return 8;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return Time.isNoon(time) && Height.isUnderground(height)? 5D: 0D;
	}
}
