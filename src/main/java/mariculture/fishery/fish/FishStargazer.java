package mariculture.fishery.fish;
import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.Items.dropletEarth;
import static mariculture.core.lib.Items.dropletPoison;
import static mariculture.core.lib.Items.dropletWater;
import static mariculture.core.lib.Items.fishMeal;
import mariculture.api.core.Environment.Height;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ForgeDirection;

public class FishStargazer extends FishSpecies {
	public FishStargazer(int id) {
		super(id);
	}

	@Override
	public int[] setSuitableTemperature() {
		return new int[] { -1, 5 };
	}

	@Override
	public Salinity[] setSuitableSalinity() {
		return new Salinity[] { SALINE, BRACKISH };
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
		return 750;
	}

	@Override
	public int getWaterRequired() {
		return 85;
	}

	@Override
	public int getAreaOfEffectBonus(ForgeDirection dir) {
		return dir == ForgeDirection.DOWN ? 7 : 0;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 2D);
		addProduct(dropletPoison, 7.5D);
		addProduct(dropletEarth, 1.5D);
		addProduct(fishMeal, 3D);
	}

	@Override
	public double getFishOilVolume() {
		return 4.725D;
	}

	@Override
	public void affectLiving(EntityLivingBase entity) {
		if (entity.worldObj.rand.nextInt(100) == 0) entity.attackEntityFrom(DamageSource.wither, 1);
	}

	@Override
	public boolean canWork(int time) {
		return !Time.isNoon(time);
	}

	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.GOOD;
	}

	@Override
	public double getCatchChance(int height, int time) {
		return Height.isDeep(height)? 10D: 0D;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return height < 8 && Time.isMidnight(time)? 5D: 0D;
	}
}
