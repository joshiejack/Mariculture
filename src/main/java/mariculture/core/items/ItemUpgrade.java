package mariculture.core.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.IItemUpgrade;
import mariculture.core.lib.Modules;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.util.MCTranslate;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUpgrade extends ItemMCMeta implements IItemUpgrade {
    public static int TEMPERATURE_BASIC;
    public static int TEMPERATURE_STANDARD;
    public static int TEMPERATURE_ADVANCED;
    public static int TEMPERATURE_ULTIMATE;
    public static int STORAGE_BASIC;
    public static int STORAGE_STANDARD;
    public static int STORAGE_ADVANCED;
    public static int STORAGE_ULTIMATE;
    public static int PURITY_BASIC;
    public static int PURITY_STANDARD;
    public static int PURITY_ADVANCED;
    public static int PURITY_ULTIMATE;
    public static int SPEED_BASIC;
    public static int SPEED_STANDARD;
    public static int SPEED_ADVANCED;
    public static int SPEED_ULTIMATE;
    public static int RF_BASIC;
    public static int RF_STANDARD;
    public static int RF_ADVANCED;
    public static int RF_ULTIMATE;
    
    public ItemUpgrade() {
        maxStackSize = 1;
    }

    @Override
    public int getTemperature(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case UpgradeMeta.BASIC_HEATING:
                return TEMPERATURE_BASIC;
            case UpgradeMeta.BASIC_COOLING:
                return -TEMPERATURE_BASIC;
            case UpgradeMeta.STANDARD_HEATING:
                return TEMPERATURE_STANDARD;
            case UpgradeMeta.STANDARD_COOLING:
                return -TEMPERATURE_STANDARD;
            case UpgradeMeta.ADVANCED_HEATING:
                return TEMPERATURE_ADVANCED;
            case UpgradeMeta.ADVANCED_COOLING:
                return -TEMPERATURE_ADVANCED;
            case UpgradeMeta.ULTIMATE_HEATING:
                return TEMPERATURE_ULTIMATE;
            case UpgradeMeta.ULTIMATE_COOLING:
                return -TEMPERATURE_ULTIMATE;
            case UpgradeMeta.DEBUG_HEATING:
                return 1000;
            case UpgradeMeta.DEBUG_ALL:
                return 2500;
            default:
                return 0;
        }
    }

    @Override
    public int getStorageCount(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case UpgradeMeta.BASIC_STORAGE:
                return STORAGE_BASIC;
            case UpgradeMeta.STANDARD_STORAGE:
                return STORAGE_STANDARD;
            case UpgradeMeta.ADVANCED_STORAGE:
                return STORAGE_ADVANCED;
            case UpgradeMeta.ULTIMATE_STORAGE:
                return STORAGE_ULTIMATE;
            case UpgradeMeta.DEBUG_STORAGE:
                return 1000;
            case UpgradeMeta.DEBUG_ALL:
                return 2500;
            default:
                return 0;
        }
    }

    @Override
    public int getPurity(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case UpgradeMeta.BASIC_PURITY:
                return PURITY_BASIC;
            case UpgradeMeta.BASIC_IMPURITY:
                return -PURITY_BASIC;
            case UpgradeMeta.STANDARD_PURITY:
                return PURITY_STANDARD;
            case UpgradeMeta.STANDARD_IMPURITY:
                return -PURITY_STANDARD;
            case UpgradeMeta.ADVANCED_PURITY:
                return PURITY_ADVANCED;
            case UpgradeMeta.ADVANCED_IMPURITY:
                return -PURITY_ADVANCED;
            case UpgradeMeta.ULTIMATE_PURITY:
                return PURITY_ULTIMATE;
            case UpgradeMeta.ULTIMATE_IMPURITY:
                return -PURITY_ULTIMATE;
            case UpgradeMeta.DEBUG_KILL:
                return -10000;
            default:
                return 0;
        }
    }

    @Override
    public int getSpeed(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case UpgradeMeta.BASIC_SPEED:
                return SPEED_BASIC;
            case UpgradeMeta.STANDARD_SPEED:
                return SPEED_STANDARD;
            case UpgradeMeta.ADVANCED_SPEED:
                return SPEED_ADVANCED;
            case UpgradeMeta.ULTIMATE_SPEED:
                return SPEED_ULTIMATE;
            case UpgradeMeta.DEBUG_SPEED:
                return 1024;
            case UpgradeMeta.DEBUG_ALL:
                return 1024;
            default:
                return 0;
        }
    }

    @Override
    public int getRFBoost(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case UpgradeMeta.BASIC_RF:
                return RF_BASIC;
            case UpgradeMeta.STANDARD_RF:
                return RF_STANDARD;
            case UpgradeMeta.ADVANCED_RF:
                return RF_ADVANCED;
            case UpgradeMeta.ULTIMATE_RF:
                return RF_ULTIMATE;
            case UpgradeMeta.DEBUG_ALL:
                return 25000000;
            case UpgradeMeta.DEBUG_STORAGE:
                return 5000000;
            default:
                return 0;
        }
    }

    @Override
    public int getSalinity(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case UpgradeMeta.SALINATOR:
                return 1;
            case UpgradeMeta.FILTER:
                return -1;
            case UpgradeMeta.SALINATOR_2:
                return 2;
            case UpgradeMeta.FILTER_2:
                return -2;
            default:
                return 0;
        }
    }

    @Override
    public int getMetaCount() {
        return UpgradeMeta.COUNT;
    }

    @Override
    public String getName(ItemStack stack) {
        String name = "";
        switch (stack.getItemDamage()) {
            case UpgradeMeta.BASIC_STORAGE:
                return "storage";
            case UpgradeMeta.BASIC_HEATING:
                return "heating";
            case UpgradeMeta.BASIC_COOLING:
                return "cooling";
            case UpgradeMeta.BASIC_PURITY:
                return "purity";
            case UpgradeMeta.BASIC_IMPURITY:
                return "impurity";
            case UpgradeMeta.ETERNAL_MALE:
                return "male";
            case UpgradeMeta.ETERNAL_FEMALE:
                return "female";
            case UpgradeMeta.STANDARD_STORAGE:
                return "storageStd";
            case UpgradeMeta.STANDARD_HEATING:
                return "heatingStd";
            case UpgradeMeta.STANDARD_COOLING:
                return "coolingStd";
            case UpgradeMeta.STANDARD_PURITY:
                return "purityStd";
            case UpgradeMeta.STANDARD_IMPURITY:
                return "impurityStd";
            case UpgradeMeta.ADVANCED_STORAGE:
                return "storageAdv";
            case UpgradeMeta.ADVANCED_HEATING:
                return "heatingAdv";
            case UpgradeMeta.ADVANCED_COOLING:
                return "coolingAdv";
            case UpgradeMeta.ADVANCED_PURITY:
                return "purityAdv";
            case UpgradeMeta.ADVANCED_IMPURITY:
                return "impurityAdv";
            case UpgradeMeta.ULTIMATE_STORAGE:
                return "storageUlt";
            case UpgradeMeta.ULTIMATE_HEATING:
                return "heatingUlt";
            case UpgradeMeta.ULTIMATE_COOLING:
                return "coolingUlt";
            case UpgradeMeta.ULTIMATE_PURITY:
                return "purityUlt";
            case UpgradeMeta.ULTIMATE_IMPURITY:
                return "impurityUlt";
            case UpgradeMeta.DEBUG_KILL:
                return "debugKiller";
            case UpgradeMeta.DEBUG_ALWAYS_LIVE:
                return "alwaysLive";
            case UpgradeMeta.ETHEREAL:
                return "ethereal";
            case UpgradeMeta.BASIC_SPEED:
                return "speed";
            case UpgradeMeta.STANDARD_SPEED:
                return "speedStd";
            case UpgradeMeta.ADVANCED_SPEED:
                return "speedAdv";
            case UpgradeMeta.ULTIMATE_SPEED:
                return "speedUlt";
            case UpgradeMeta.BASIC_RF:
                return "rf";
            case UpgradeMeta.STANDARD_RF:
                return "rfStd";
            case UpgradeMeta.ADVANCED_RF:
                return "rfAdv";
            case UpgradeMeta.ULTIMATE_RF:
                return "rfUlt";
            case UpgradeMeta.SALINATOR:
                return "salinator";
            case UpgradeMeta.FILTER:
                return "filter";
            case UpgradeMeta.DEBUG_EGG:
                return "incubator";
            case UpgradeMeta.DEBUG_SPEED:
                return "debugSpeed";
            case UpgradeMeta.DEBUG_HEATING:
                return "debugHeating";
            case UpgradeMeta.DEBUG_STORAGE:
                return "debugStorage";
            case UpgradeMeta.DEBUG_ALL:
                return "debugAll";
            case UpgradeMeta.SALINATOR_2:
                return "salinator2";
            case UpgradeMeta.FILTER_2:
                return "filter2";
            case UpgradeMeta.AQUASCUM:
                return "aquascum";
            default:
                return "upgrade";
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
        int temp = getTemperature(stack);
        if (temp != 0) {
            if (temp > 0) {
                list.add(mariculture.lib.util.Text.ORANGE + "+" + temp + mariculture.lib.util.Text.DEGREES);
            }
            if (temp < 0) {
                list.add(mariculture.lib.util.Text.INDIGO + temp + mariculture.lib.util.Text.DEGREES);
            }
        }
    }

    @Override
    public boolean isActive(int meta) {
        switch (meta) {
            case UpgradeMeta.ETERNAL_MALE:
                return Modules.isActive(Modules.fishery);
            case UpgradeMeta.ETERNAL_FEMALE:
                return Modules.isActive(Modules.fishery);
            default:
                return true;
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        int damage = stack.getItemDamage();
        if(damage == UpgradeMeta.ETERNAL_MALE || damage == UpgradeMeta.ETERNAL_FEMALE) {
            String format = MCTranslate.translate("upgrade.eternal.format");
            String eternal = MCTranslate.translate("upgrade.eternal.life");
            String upgrade = MCTranslate.translate("upgrade.eternal.upgrade");
            format = format.replace("%E", eternal);
            format = damage == UpgradeMeta.ETERNAL_MALE? format.replace("%S", "\u2642"): format.replace("%S", "\u2640");
            return format.replace("%U", upgrade);
        } else return super.getItemStackDisplayName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icons = new IIcon[getMetaCount()];

        for (int i = 0; i < icons.length; i++) {
            icons[i] = iconRegister.registerIcon(Mariculture.modid + ":upgrades/" + getName(new ItemStack(this, 1, i)));
        }
    }

    @Override
    public String getType(ItemStack stack) {
        switch (stack.getItemDamage()) {
            case UpgradeMeta.BASIC_STORAGE:
                return "storage";
            case UpgradeMeta.BASIC_HEATING:
                return "heating";
            case UpgradeMeta.BASIC_COOLING:
                return "cooling";
            case UpgradeMeta.BASIC_PURITY:
                return "purity";
            case UpgradeMeta.BASIC_IMPURITY:
                return "impurity";
            case UpgradeMeta.ETERNAL_MALE:
                return "male";
            case UpgradeMeta.ETERNAL_FEMALE:
                return "female";
            case UpgradeMeta.STANDARD_STORAGE:
                return "storage";
            case UpgradeMeta.STANDARD_HEATING:
                return "heating";
            case UpgradeMeta.STANDARD_COOLING:
                return "cooling";
            case UpgradeMeta.STANDARD_PURITY:
                return "purity";
            case UpgradeMeta.STANDARD_IMPURITY:
                return "impurity";
            case UpgradeMeta.ADVANCED_STORAGE:
                return "storage";
            case UpgradeMeta.ADVANCED_HEATING:
                return "heating";
            case UpgradeMeta.ADVANCED_COOLING:
                return "cooling";
            case UpgradeMeta.ADVANCED_PURITY:
                return "purity";
            case UpgradeMeta.ADVANCED_IMPURITY:
                return "impurity";
            case UpgradeMeta.ULTIMATE_STORAGE:
                return "storage";
            case UpgradeMeta.ULTIMATE_HEATING:
                return "heating";
            case UpgradeMeta.ULTIMATE_COOLING:
                return "cooling";
            case UpgradeMeta.ULTIMATE_PURITY:
                return "purity";
            case UpgradeMeta.ULTIMATE_IMPURITY:
                return "impurity";
            case UpgradeMeta.DEBUG_KILL:
                return "debugKill";
            case UpgradeMeta.DEBUG_ALWAYS_LIVE:
                return "debugLive";
            case UpgradeMeta.ETHEREAL:
                return "ethereal";
            case UpgradeMeta.BASIC_SPEED:
                return "speed";
            case UpgradeMeta.STANDARD_SPEED:
                return "speed";
            case UpgradeMeta.ADVANCED_SPEED:
                return "speed";
            case UpgradeMeta.ULTIMATE_SPEED:
                return "speed";
            case UpgradeMeta.BASIC_RF:
                return "rf";
            case UpgradeMeta.STANDARD_RF:
                return "rf";
            case UpgradeMeta.ADVANCED_RF:
                return "rf";
            case UpgradeMeta.ULTIMATE_RF:
                return "rf";
            case UpgradeMeta.SALINATOR:
                return "salinator";
            case UpgradeMeta.FILTER:
                return "filter";
            case UpgradeMeta.DEBUG_EGG:
                return "incubator";
            case UpgradeMeta.DEBUG_SPEED:
                return "speed";
            case UpgradeMeta.DEBUG_HEATING:
                return "heating";
            case UpgradeMeta.DEBUG_STORAGE:
                return "storage";
            case UpgradeMeta.DEBUG_ALL:
                return "debugMachine";
            case UpgradeMeta.SALINATOR_2:
                return "salinator";
            case UpgradeMeta.FILTER_2:
                return "filter";
            case UpgradeMeta.AQUASCUM:
                return "aquascum";
            default:
                return "null";
        }
    }
}
