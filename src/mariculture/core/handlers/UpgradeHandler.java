package mariculture.core.handlers;

import mariculture.api.core.IItemUpgrade;
import mariculture.api.core.IUpgradable;
import mariculture.api.core.IUpgradeHandler;
import mariculture.core.blocks.base.TileMulti;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class UpgradeHandler implements IUpgradeHandler {
	@Override
	public int getData(String type, IUpgradable tile) {
		if(tile == null) {
			return 0;
		}
		
		int storage = 0;
		int purity = 0;
		int temp = 0;
		int speed = 1;
		int rf = 0;
		if(tile instanceof TileMulti) {
			TileMulti multi = (TileMulti) tile;
			tile = (IUpgradable) ((TileMulti) tile).worldObj.getBlockTileEntity(multi.mstr.x, multi.mstr.y, multi.mstr.z);
			if(tile == null) {
				return 0;
			}
		}
		ItemStack[] upgrades = tile.getUpgrades();
		for (int i = 0; i < upgrades.length; i++) {
			ItemStack upgradeStack = upgrades[i];
			if (upgradeStack != null && upgradeStack.getItem() instanceof IItemUpgrade) {
				IItemUpgrade upgrade = (IItemUpgrade) upgradeStack.getItem();
				storage+= (upgrade.getStorageCount(upgradeStack.getItemDamage()));
				purity+= (upgrade.getPurity(upgradeStack.getItemDamage()));
				temp+=(upgrade.getTemperature(upgradeStack.getItemDamage()));
				speed+= (upgrade.getSpeed(upgradeStack.getItemDamage()));
				rf+= (upgrade.getRFBoost(upgradeStack.getItemDamage()));
			}
		}

		return type.equalsIgnoreCase("storage") ? storage : type.equalsIgnoreCase("temp") ? temp : 
			   type.equalsIgnoreCase("purity") ? purity : type.equalsIgnoreCase("speed")? speed:
				   type.equalsIgnoreCase("rf")? rf: 0;
	}

	@Override
	public boolean hasUpgrade(String type, IUpgradable tile) {
		ItemStack[] upgrades = tile.getUpgrades();
		for (int i = 0; i < upgrades.length; i++) {
			ItemStack upgradeStack = upgrades[i];
			if (upgradeStack != null && upgradeStack.getItem() instanceof IItemUpgrade) {
				IItemUpgrade upgrade = (IItemUpgrade) upgradeStack.getItem();
				if(upgrade.getType(upgradeStack.getItemDamage()).equalsIgnoreCase(type)) {
					return true;
				}
			}
		}

		return false;
	}
}
