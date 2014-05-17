package mariculture.fishery.fish;
import static mariculture.api.core.Environment.Salinity.BRACKISH;
import static mariculture.api.core.Environment.Salinity.SALINE;
import static mariculture.core.lib.ItemLib.dropletEarth;
import static mariculture.core.lib.ItemLib.dropletPoison;
import static mariculture.core.lib.ItemLib.dropletWater;
import mariculture.api.core.Environment.Salinity;
import mariculture.api.core.Environment.Time;
import mariculture.api.fishery.RodType;
import mariculture.api.fishery.fish.FishSpecies;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class FishBlaasop extends FishSpecies {
	public FishBlaasop(int id) {
		super(id);
	}
	
	@Override
	public int[] setSuitableTemperature() {
		return new int[] { -3, 20 };
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
		return 12;
	}

	@Override
	public int getFertility() {
		return 150;
	}

	@Override
	public int getWaterRequired() {
		return 40;
	}

	@Override
	public int getAreaOfEffectBonus(ForgeDirection dir) {
		return dir == ForgeDirection.DOWN? 3: 0;
	}

	@Override
	public void addFishProducts() {
		addProduct(dropletWater, 3D);
		addProduct(dropletPoison, 0.5D);
		addProduct(dropletEarth, 1D);
	}

	@Override
	public double getFishOilVolume() {
		return 0.450D;
	}
	
	@Override
	public boolean canAlwaysEat() {
		return true;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.harm.id, 1, 0));
		player.addPotionEffect(new PotionEffect(Potion.poison.id, 200, 5));
		player.addPotionEffect(new PotionEffect(Potion.confusion.id, 500, 0));
	}

	@Override
	public void affectLiving(EntityLivingBase entity) {
		entity.addPotionEffect(new PotionEffect(Potion.confusion.id, 150, 1, true));
		entity.addPotionEffect(new PotionEffect(Potion.wither.id, 250, 0, true));
	}

	@Override
	public boolean canWork(int time) {
		return !Time.isNoon(time);
	}

	@Override
	public RodType getRodNeeded() {
		return RodType.OLD;
	}

	@Override
	public double getCatchChance(int height, int time) {
		return height < 32? 40D: 0D;
	}

	@Override
	public double getCaughtAliveChance(int height, int time) {
		return 65D;
	}
}
