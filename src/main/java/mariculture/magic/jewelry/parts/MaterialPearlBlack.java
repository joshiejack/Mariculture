package mariculture.magic.jewelry.parts;

import mariculture.core.Core;
import mariculture.core.lib.PearlColor;
import mariculture.core.util.Text;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.item.ItemStack;

public class MaterialPearlBlack extends JewelryMaterial {
	@Override
	public String getColor() {
		return Text.GREY;
	}

	@Override
	public float getHitsModifier(JewelryType type) {
		return 1.0F;
	}
	
	@Override
	public float getDurabilityModifier(JewelryType type) {
		return 1.0F;
	}
	
	@Override
	public ItemStack getCraftingItem(JewelryType type) {
		return new ItemStack(Core.pearls, 1, PearlColor.BLACK);
	}
}
