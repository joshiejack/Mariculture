package mariculture.fishery.fish;

import mariculture.api.fishery.RodQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaricultureDamage;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishPiranha extends FishSpecies {
	public FishPiranha(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.AMAZONIAN;
	}

	@Override
	public int getLifeSpan() {
		return 50;
	}

	@Override
	public int getFertility() {
		return 500;
	}

	@Override
	public boolean isDominant() {
		return false;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 3D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_AQUA), 2.5D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_ATTACK), 5D);
		addProduct(new ItemStack(Item.rottenFlesh), 15.0D);
	}
	
	@Override
	public int getFoodStat() {
		return 3;
	}

	@Override
	public float getFoodSaturation() {
		return 0.5F;
	}
	
	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.harm.id, 20, 0));
	}

	@Override
	public int getCatchChance() {
		return 8;
	}
	
	@Override
	public RodQuality getRodNeeded() {
		return RodQuality.SUPER;
	}

	@Override
	public double getFishOilVolume() {
		return 1.750;
	}
	
	@Override
	public void affectLiving(EntityLivingBase living) {
		if (living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) living;
			if (player.worldObj.difficultySetting > 0) {
				player.attackEntityFrom(MaricultureDamage.piranha, player.worldObj.difficultySetting);
			}
		} else {
			living.attackEntityFrom(MaricultureDamage.piranha, 2);
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
