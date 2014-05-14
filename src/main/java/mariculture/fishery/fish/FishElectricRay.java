package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.fishery.ILootHandler.LootQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.util.PowerHelper;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

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
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 5D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_HEALTH), 1D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ELECTRIC), 4D);
	}
	
	@Override
	public void onConsumed(World world, EntityPlayer player) {
		world.addWeatherEffect(new EntityLightningBolt(world, player.posX,player.posY, player.posZ));
	}

	@Override
	public int getCatchChance() {
		return 5;
	}
	
	@Override
	public LootQuality getLootQuality() {
		return LootQuality.RARE;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.450;
	}
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return from == from.DOWN;
	}

	@Override
	public void affectWorld(World world, int x, int y, int z, int tankType) {
		if (rand.nextInt(30) == 0) {
			world.addWeatherEffect(new EntityLightningBolt(world, x + (rand.nextInt(5) - rand.nextInt(10)), y, z
					+ (rand.nextInt(5) - rand.nextInt(10))));
		}
		
		if(PowerHelper.isEnergyHandler(world, x, y - 1, z) != null) {
			IEnergyHandler handler = PowerHelper.isEnergyHandler(world, x, y - 1, z);
			if(handler.canConnectEnergy(ForgeDirection.DOWN)) {
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
