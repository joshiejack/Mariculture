package mariculture.core.items;

import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;
import cofh.api.energy.IEnergyContainerItem;

public class ItemMaterial extends ItemMariculture implements IEnergyContainerItem {
	public ItemMaterial(int i) {
		super(i);
	}

	@Override
	public int getMetaCount() {
		return MaterialsMeta.COUNT;
	}

	@Override
	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case MaterialsMeta.INGOT_ALUMINUM:
			return "ingotAluminum";
		case MaterialsMeta.INGOT_MAGNESIUM:
			return "ingotMagnesium";
		case MaterialsMeta.INGOT_TITANIUM:
			return "ingotTitanium";
		case MaterialsMeta.INGOT_COPPER:
			return "ingotCopper";
		case MaterialsMeta.FISH_MEAL:
			return "fishMeal";
		case MaterialsMeta.DYE_YELLOW:
			return "dyeYellow";
		case MaterialsMeta.DYE_RED:
			return "dyeRed";
		case MaterialsMeta.DYE_BROWN:
			return "dyeBrown";
		case MaterialsMeta.DROP_AQUA:
			return "dropletAqua";
		case MaterialsMeta.DROP_ATTACK:
			return "dropletAttack";
		case MaterialsMeta.DROP_ELECTRIC:
			return "dropletElectric";
		case MaterialsMeta.DROP_ENDER:
			return "dropletEnder";
		case MaterialsMeta.DROP_NETHER:
			return "dropletNether";
		case MaterialsMeta.DROP_HEALTH:
			return "dropletHealth";
		case MaterialsMeta.DROP_MAGIC:
			return "dropletMagic";
		case MaterialsMeta.DROP_POISON:
			return "dropletPoison";
		case MaterialsMeta.DROP_WATER:
			return "dropletWater";
		case MaterialsMeta.DUST_MAGNESITE:
			return "dustMagnesite";
		case MaterialsMeta.DUST_SALT:
			return "foodSalt";
		case MaterialsMeta.INGOT_RUTILE:
			return "ingotRutile";
		case MaterialsMeta.DUST_COPPEROUS:
			return "dustCopperous";
		case MaterialsMeta.DUST_GOLDEN:
			return "dustGolden";
		case MaterialsMeta.DUST_IRONIC:
			return "dustIronic";
		case MaterialsMeta.DUST_LEADER:
			return "dustLeader";
		case MaterialsMeta.DUST_SILVERY:
			return "dustSilvery";
		case MaterialsMeta.DUST_TINNIC:
			return "dustTinnic";
		case MaterialsMeta.DYE_GREEN:
			return "dyeGreen";
		case MaterialsMeta.DYE_WHITE:
			return "dyeWhite";
		case MaterialsMeta.DYE_BLACK:
			return "dyeBlack";
		default:
			return "dropletWater";
		}
	}

	@Override
	public boolean isActive(int meta) {
		switch (meta) {
		case MaterialsMeta.FISH_MEAL:
			return (Modules.fishery.isActive());
		case MaterialsMeta.DYE_RED:
			return (Modules.world.isActive());
		case MaterialsMeta.DYE_YELLOW:
			return (Modules.world.isActive());
		case MaterialsMeta.DYE_BROWN:
			return (Modules.world.isActive());
		case MaterialsMeta.DYE_GREEN:
			return Modules.world.isActive();
		case MaterialsMeta.DYE_WHITE:
			return Modules.world.isActive();
		case MaterialsMeta.DYE_BLACK:
			return false;
		case MaterialsMeta.UNUSED:
			return false;
		case MaterialsMeta.UNUSED2:
			return false;
		case MaterialsMeta.UNUSED3:
			return false;
		case MaterialsMeta.UNUSED4:
			return false;
		}

		if (meta >= MaterialsMeta.DROP_WATER && meta <= MaterialsMeta.DROP_HEALTH) {
			return (Modules.fishery.isActive());
		}

		return true;
	}
	
	@Override
	public boolean isPotionIngredient(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case MaterialsMeta.DROP_POISON:
			return true;
		default:
			return false;
		}
	}
	
	@Override
	public String getPotionEffect(ItemStack stack)
    {
		switch (stack.getItemDamage()) {
		case MaterialsMeta.DROP_POISON:
			return PotionHelper.spiderEyeEffect;
		default:
	        return getPotionEffect();
		}
    }

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7,
			float par8, float par9, float par10) {
		if (!player.canPlayerEdit(x, y, z, par7, stack)) {
			return false;
		} else {
			int blockID;
			int var12;
			int var13;

			if (stack.getItemDamage() == MaterialsMeta.FISH_MEAL) {
				if (ItemDye.applyBonemeal(stack, world, x, y, z, player)) {
					if (!world.isRemote) {
						world.playAuxSFX(2005, x, y, z, 0);
					}

					return true;
				}
			}

			return false;
		}
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		if(container.getItemDamage() == MaterialsMeta.DROP_ELECTRIC) {
			container.stackSize--;
			return 5000;
		}
		
		return 0;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		if(container.getItemDamage() == MaterialsMeta.DROP_ELECTRIC) {
			return 5000;
		}
		
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		if(container.getItemDamage() == MaterialsMeta.DROP_ELECTRIC) {
			return 5000;
		}
		
		return 0;
	}
}
