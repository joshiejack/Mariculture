package mariculture.core.items;

import mariculture.api.core.IItemUpgrade;
import mariculture.api.core.MaricultureRegistry;
import mariculture.core.Mariculture;
import mariculture.core.lib.Modules;
import mariculture.core.lib.UpgradeMeta;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUpgrade extends ItemMariculture implements IItemUpgrade {

	public ItemUpgrade(int i) {
		super(i);
		this.maxStackSize = 1;
	}

	@Override
	public int getTemperature(int meta) {
		switch (meta) {
		case UpgradeMeta.BASIC_HEATING:
			return 1;
		case UpgradeMeta.BASIC_COOLING:
			return -1;
		case UpgradeMeta.STANDARD_HEATING:
			return 2;
		case UpgradeMeta.STANDARD_COOLING:
			return -2;
		case UpgradeMeta.ADVANCED_HEATING:
			return 5;
		case UpgradeMeta.ADVANCED_COOLING:
			return -5;
		case UpgradeMeta.ULTIMATE_HEATING:
			return 14;
		case UpgradeMeta.ULTIMATE_COOLING:
			return -14;
		default:
			return 0;
		}
	}

	@Override
	public int getStorageCount(int meta) {
		switch (meta) {
		case UpgradeMeta.BASIC_STORAGE:
			return 1;
		case UpgradeMeta.STANDARD_STORAGE:
			return 3;
		case UpgradeMeta.ADVANCED_STORAGE:
			return 7;
		case UpgradeMeta.ULTIMATE_STORAGE:
			return 15;
		default:
			return 0;
		}
	}

	@Override
	public int getPurity(int meta) {
		switch (meta) {
		case UpgradeMeta.BASIC_PURITY:
			return 1;
		case UpgradeMeta.BASIC_IMPURITY:
			return -1;
		case UpgradeMeta.STANDARD_PURITY:
			return 2;
		case UpgradeMeta.STANDARD_IMPURITY:
			return -2;
		case UpgradeMeta.ADVANCED_PURITY:
			return 3;
		case UpgradeMeta.ADVANCED_IMPURITY:
			return -3;
		case UpgradeMeta.ULTIMATE_PURITY:
			return 4;
		case UpgradeMeta.ULTIMATE_IMPURITY:
			return -4;
		case UpgradeMeta.DEBUG_KILL:
			return -10000;
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
		case UpgradeMeta.BASIC_STORAGE: {
			name = "storage";
			break;
		}
		case UpgradeMeta.BASIC_HEATING: {
			name = "heating";
			break;
		}
		case UpgradeMeta.BASIC_COOLING: {
			name = "cooling";
			break;
		}
		case UpgradeMeta.BASIC_PURITY: {
			name = "purity";
			break;
		}
		case UpgradeMeta.BASIC_IMPURITY: {
			name = "impurity";
			break;
		}
		case UpgradeMeta.ETERNAL_MALE: {
			name = "male";
			break;
		}
		case UpgradeMeta.ETERNAL_FEMALE: {
			name = "female";
			break;
		}
		case UpgradeMeta.STANDARD_STORAGE: {
			name = "storageStd";
			break;
		}
		case UpgradeMeta.STANDARD_HEATING: {
			name = "heatingStd";
			break;
		}
		case UpgradeMeta.STANDARD_COOLING: {
			name = "coolingStd";
			break;
		}
		case UpgradeMeta.STANDARD_PURITY: {
			name = "purityStd";
			break;
		}
		case UpgradeMeta.STANDARD_IMPURITY: {
			name = "impurityStd";
			break;
		}
		case UpgradeMeta.ADVANCED_STORAGE: {
			name = "storageAdv";
			break;
		}
		case UpgradeMeta.ADVANCED_HEATING: {
			name = "heatingAdv";
			break;
		}
		case UpgradeMeta.ADVANCED_COOLING: {
			name = "coolingAdv";
			break;
		}
		case UpgradeMeta.ADVANCED_PURITY: {
			name = "purityAdv";
			break;
		}
		case UpgradeMeta.ADVANCED_IMPURITY: {
			name = "impurityAdv";
			break;
		}
		case UpgradeMeta.ULTIMATE_STORAGE: {
			name = "storageUlt";
			break;
		}
		case UpgradeMeta.ULTIMATE_HEATING: {
			name = "heatingUlt";
			break;
		}
		case UpgradeMeta.ULTIMATE_COOLING: {
			name = "coolingUlt";
			break;
		}
		case UpgradeMeta.ULTIMATE_PURITY: {
			name = "purityUlt";
			break;
		}
		case UpgradeMeta.ULTIMATE_IMPURITY: {
			name = "impurityUlt";
			break;
		}
		case UpgradeMeta.DEBUG_KILL: {
			name = "debugKiller";
			break;
		}
		case UpgradeMeta.DEBUG_ALWAYS_LIVE: {
			name = "alwaysLive";
			break;
		}
		case UpgradeMeta.ETHEREAL: {
			name = "ethereal";
			break;
		}
		default:
			name = "upgrade";
		}

		return name;

	}

	@Override
	public boolean isActive(int meta) {
		switch (meta) {
		case UpgradeMeta.ETERNAL_MALE:
			return (Modules.fishery.isActive());
		case UpgradeMeta.ETERNAL_FEMALE:
			return (Modules.fishery.isActive());

		default:
			return true;
		}
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case UpgradeMeta.ETERNAL_MALE:
			return StatCollector.translateToLocal("item.upgrade.eternal.life") + "\u2642" + " "
					+ StatCollector.translateToLocal("item.upgrade.eternal.upgrade");
		case UpgradeMeta.ETERNAL_FEMALE:
			return StatCollector.translateToLocal("item.upgrade.eternal.life") + "\u2640" + " "
					+ StatCollector.translateToLocal("item.upgrade.eternal.upgrade");
		default:
			 return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
		}
	}

	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register("upgrade." + getName(new ItemStack(this.itemID, 1, j)), new ItemStack(
					this.itemID, 1, j));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":upgrades/"
					+ getName(new ItemStack(this.itemID, 1, i)));
		}
	}

	@Override
	public String getType(int meta) {
		switch (meta) {
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
		default:
			return "null";
		}
	}
}
