package mariculture.core.items;

import mariculture.Mariculture;
import mariculture.api.core.IItemUpgrade;
import mariculture.api.core.MaricultureRegistry;
import mariculture.core.lib.Modules;
import mariculture.core.lib.UpgradeMeta;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemUpgrade extends ItemMariculture implements IItemUpgrade {
	public ItemUpgrade() {
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
	public int getSpeed(int meta) {
		switch(meta) {
		case UpgradeMeta.BASIC_SPEED:
			return 1;
		case UpgradeMeta.STANDARD_SPEED:
			return 2;
		case UpgradeMeta.ADVANCED_SPEED:
			return 4;
		case UpgradeMeta.ULTIMATE_SPEED:
			return 8;
		default:
			return 0;
		}
	}

	@Override
	public int getRFBoost(int meta) {
		switch(meta) {
		case UpgradeMeta.BASIC_RF:
			return 5000;
		case UpgradeMeta.STANDARD_RF:
			return 20000;
		case UpgradeMeta.ADVANCED_RF:
			return 50000;
		case UpgradeMeta.ULTIMATE_RF:
			return 100000;
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
		default:
			return "upgrade";
		}
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
	public String getItemStackDisplayName(ItemStack stack) {
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
	public void register(Item item) {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register("upgrade." + getName(new ItemStack(item, 1, j)), new ItemStack(item, 1, j));
		}
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
		default:
			return "null";
		}
	}
}
