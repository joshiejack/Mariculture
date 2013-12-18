package mariculture.core.handlers;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.lib.Modules;
import mariculture.fishery.Fishery;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class FuelHandler implements IFuelHandler {
	@Override
	public int getBurnTime(ItemStack fuel) {
		if (Modules.fishery.isActive()) {
			if (fuel.itemID == Fishery.fishyFood.itemID) {
				int speciesID = fuel.getItemDamage();
				if (fuel.getItemDamage() == Fishery.nether.fishID) {
					return 2500;
				}
			}
		}

		return 0;
	}
}
