package mariculture.magic.jewelry.parts;

import mariculture.core.util.Text;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class MaterialDummy extends JewelryMaterial {
	public MaterialDummy() {
		ignore = true;
	}
	
	@Override
	public String getColor() {
		return Text.WHITE;
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
		return new ItemStack(Blocks.fire);
	}
}
