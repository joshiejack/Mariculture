package mariculture.core.items;

import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.Modules;
import mariculture.fishery.Fishery;
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
		String name = "";
		switch (stack.getItemDamage()) {
		case MaterialsMeta.INGOT_ALUMINUM: {
			name = "ingotAluminum";
			break;
		}
		case MaterialsMeta.INGOT_MAGNESIUM: {
			name = "ingotMagnesium";
			break;
		}
		case MaterialsMeta.INGOT_TITANIUM: {
			name = "ingotTitanium";
			break;
		}
		case MaterialsMeta.INGOT_COPPER: {
			name = "ingotCopper";
			break;
		}
		case MaterialsMeta.FISH_MEAL: {
			name = "fishMeal";
			break;
		}
		case MaterialsMeta.DYE_YELLOW: {
			name = "dyeYellow";
			break;
		}
		case MaterialsMeta.DYE_RED: {
			name = "dyeRed";
			break;
		}
		case MaterialsMeta.DYE_BROWN: {
			name = "dyeBrown";
			break;
		}
		case MaterialsMeta.DROP_AQUA: {
			name = "dropletAqua";
			break;
		}
		case MaterialsMeta.DROP_ATTACK: {
			name = "dropletAttack";
			break;
		}
		case MaterialsMeta.DROP_ELECTRIC: {
			name = "dropletElectric";
			break;
		}
		case MaterialsMeta.DROP_ENDER: {
			name = "dropletEnder";
			break;
		}
		case MaterialsMeta.DROP_NETHER: {
			name = "dropletNether";
			break;
		}
		case MaterialsMeta.DROP_HEALTH: {
			name = "dropletHealth";
			break;
		}
		case MaterialsMeta.DROP_MAGIC: {
			name = "dropletMagic";
			break;
		}
		case MaterialsMeta.DROP_POISON: {
			name = "dropletPoison";
			break;
		}

		case MaterialsMeta.DROP_WATER: {
			name = "dropletWater";
			break;
		}

		default:
			name = "dropletWater";
		}

		return name;

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
		case MaterialsMeta.UNUSED0:
			return false;
		case MaterialsMeta.UNUSED1:
			return false;
		case MaterialsMeta.UNUSED2:
			return false;
		case MaterialsMeta.UNUSED3:
			return false;
		case MaterialsMeta.UNUSED4:
			return false;
		case MaterialsMeta.UNUSED5:
			return false;
		case MaterialsMeta.UNUSED6:
			return false;
		case MaterialsMeta.UNUSED7:
			return false;
		case MaterialsMeta.UNUSED8:
			return false;
		case MaterialsMeta.UNUSED9:
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
