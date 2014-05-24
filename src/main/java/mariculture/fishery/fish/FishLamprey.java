package mariculture.fishery.fish;

import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.Items.dropletAqua;
import static mariculture.core.lib.Items.dropletDestroy;
import static mariculture.core.lib.Items.dropletEarth;
import static mariculture.core.lib.Items.dropletPoison;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ForgeDirection;

public class FishLamprey extends FishSpecies {
	public FishLamprey(int id) {
		super(id);
	}
	
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { -3, 5 };
	}
	
	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { SALINE, BRACKISH };
	}
	
	@Override
	public boolean isDominant() {
		return true;
	}

	@Override
	public int getLifeSpan() {
		return 17;
	}

	@Override
	public int getFertility() {
		return 3000;
	}

	@Override
	public int getFoodConsumption() {
		return 2;
	}

	@Override
	public int getWaterRequired() {
		return 350;
	}

	@Override
	public int getAreaOfEffectBonus(ForgeDirection dir) {
		return dir == ForgeDirection.DOWN ? 5: 0;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletAqua, 7.5D);
		addProduct(dropletPoison, 6.5D);
		addProduct(dropletDestroy, 4.5D);
		addProduct(dropletEarth, 2D);
	}

	@Override
	public double getFishOilVolume() {
		return 2.250D;
	}

	@Override
	public void affectLiving(EntityLivingBase entity) {
		if(entity.worldObj.rand.nextInt(100) == 0) entity.attackEntityFrom(DamageSource.wither, 1);
	}

	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.SUPER;
	}

	@Override
	public double getCatchChance(int height, int time) {
		return Height.isDeep(height)? 15D: 0D;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return height>= 10 && height <= 15? 4D: 0D;
	}
}
