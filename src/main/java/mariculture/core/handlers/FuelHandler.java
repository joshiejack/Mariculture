package mariculture.core.handlers;

import mariculture.core.lib.Modules;
import mariculture.fishery.Fishery;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler {
	@Override
	public int getBurnTime(ItemStack fuel) {
		if (Modules.fishery.isActive()) {
			if (fuel.getItem() == Items.fish) {
				int speciesID = fuel.getItemDamage();
				if (fuel.getItemDamage() == Fishery.nether.fishID) {
					return 2500;
				}
			}
		}

		return 0;
	}
}
