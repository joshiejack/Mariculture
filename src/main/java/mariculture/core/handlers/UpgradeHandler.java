package mariculture.core.handlers;

import java.util.ArrayList;
import java.util.List;

import mariculture.api.core.IItemUpgrade;
import mariculture.api.core.IUpgradable;
import mariculture.api.core.IUpgradeHandler;
import mariculture.api.fishery.IMutationEffect;
import mariculture.core.tile.base.TileMultiBlock;
import net.minecraft.item.ItemStack;

public class UpgradeHandler implements IUpgradeHandler {
    @Override
    public int getData(String type, IUpgradable tile) {
        if (tile == null) return 0;

        int salinity = 0;
        int storage = 0;
        int purity = 0;
        int temp = 0;
        int speed = 1;
        int rf = 0;

        if (tile instanceof TileMultiBlock) {
            TileMultiBlock multi = (TileMultiBlock) tile;
            tile = (IUpgradable) ((TileMultiBlock) tile).getWorldObj().getTileEntity(multi.master.xCoord, multi.master.yCoord, multi.master.zCoord);
            if (tile == null) return 0;
        }

        ItemStack[] upgrades = tile.getUpgrades();
        for (ItemStack upgradeStack : upgrades) {
            if (upgradeStack != null && upgradeStack.getItem() instanceof IItemUpgrade) {
                IItemUpgrade upgrade = (IItemUpgrade) upgradeStack.getItem();
                storage += upgrade.getStorageCount(upgradeStack);
                purity += upgrade.getPurity(upgradeStack);
                temp += upgrade.getTemperature(upgradeStack);
                speed += upgrade.getSpeed(upgradeStack);
                rf += upgrade.getRFBoost(upgradeStack);
                salinity += upgrade.getSalinity(upgradeStack);
            }
        }

        return type.equalsIgnoreCase("salinity") ? salinity : type.equalsIgnoreCase("storage") ? storage : type.equalsIgnoreCase("temp") ? temp : type.equalsIgnoreCase("purity") ? purity : type.equalsIgnoreCase("speed") ? speed : type.equalsIgnoreCase("rf") ? rf : 0;
    }

    @Override
    public boolean hasUpgrade(String type, IUpgradable tile) {
        ItemStack[] upgrades = tile.getUpgrades();
        for (ItemStack upgradeStack : upgrades) {
            if (upgradeStack != null && upgradeStack.getItem() instanceof IItemUpgrade) {
                IItemUpgrade upgrade = (IItemUpgrade) upgradeStack.getItem();
                if (upgrade.getType(upgradeStack).equalsIgnoreCase(type)) return true;
            }
        }

        return false;
    }

    @Override
    public List<IMutationEffect> getMutationEffects(IUpgradable tile) {
        ArrayList<IMutationEffect> effects = new ArrayList();
        ItemStack[] upgrades = tile.getUpgrades();
        for (ItemStack upgradeStack : upgrades) {
            if (upgradeStack != null && upgradeStack.getItem() instanceof IItemUpgrade) {
                IItemUpgrade upgrade = (IItemUpgrade) upgradeStack.getItem();
                effects.addAll(upgrade.getMutationEffects(upgradeStack));
            }
        }
        
        return effects;
    }
}
