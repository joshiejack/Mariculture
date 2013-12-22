package mariculture.core.items;

import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.fishery.Fishery;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemWorked extends ItemDamageable {
	public ItemWorked(int i) {
		super(i, 100);
	}
	
	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		if (stack.hasTagCompound()) {
			ItemStack worked = ItemStack.loadItemStackFromNBT(stack.stackTagCompound.getCompoundTag("WorkedItem"));
			if(worked.itemID != this.itemID)
				return worked.getItem().getIcon(worked, pass);
		}

		return Core.hammer.getIcon(stack, pass);
	}
}
