package mariculture.magic.jewelry;

import mariculture.Mariculture;
import mariculture.core.lib.Jewelry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRing extends ItemJewelry {
	public ItemRing(int id) {
		super(id);
		this.setMaxDamage(128);
	}
	
	@Override
	public int getItemEnchantability() {
		return Jewelry.BONUS_RING;
	}

	@Override
	public int getType() {
		return Jewelry.RING;
	}

	@Override
	public String getTypeString() {
		return "ring";
	}

	@Override
	public String getPart1() {
		return Jewelry.RING_PART1;
	}

	@Override
	public String getPart2() {
		return Jewelry.RING_PART2;
	}
}
