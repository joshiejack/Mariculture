package mariculture.plugins;

import mariculture.core.helpers.cofh.ThermalExpansionHelper;
import mariculture.core.lib.CoralMeta;
import mariculture.core.lib.Dye;
import mariculture.core.lib.Modules;
import mariculture.plugins.Plugins.Plugin;
import mariculture.world.WorldPlus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PluginThermalExpansion extends Plugin {
	public PluginThermalExpansion(String name) {
		super(name);
	}

	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void postInit() {
		if(Modules.world.isActive()) {
			ThermalExpansionHelper.addPulverizerRecipe(4000, 
					new ItemStack(WorldPlus.coral, 1, CoralMeta.CORAL_BLUE), new ItemStack(Item.dyePowder, 2, Dye.LIGHT_BLUE));
		}
	}
}
