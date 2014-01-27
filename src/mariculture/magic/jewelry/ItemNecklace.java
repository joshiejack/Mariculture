package mariculture.magic.jewelry;

import mariculture.Mariculture;
import mariculture.core.lib.Jewelry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNecklace extends ItemJewelry {
	public ItemNecklace(int id) {
		super(id);
	}
	
	@Override
	public int getItemEnchantability() {
		return Jewelry.BONUS_NECKLACE;
	}

	@Override
	public int getType() {
		return Jewelry.NECKLACE;
	}

	@Override
	public String getTypeString() {
		return "necklace";
	}

	@Override
	public String getPart1() {
		return Jewelry.NECKLACE_PART1;
	}

	@Override
	public String getPart2() {
		return Jewelry.NECKLACE_PART2;
	}
}
