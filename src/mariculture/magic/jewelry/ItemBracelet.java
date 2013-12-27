package mariculture.magic.jewelry;

import mariculture.core.Mariculture;
import mariculture.core.lib.Jewelry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBracelet extends ItemJewelry {
	public ItemBracelet(int id) {
		super(id);
		this.setMaxDamage(512);
	}
	
	@Override
	public int getItemEnchantability() {
		return Jewelry.BONUS_BRACELET;
	}

	@Override
	public int getType() {
		return Jewelry.BRACELET;
	}

	@Override
	public String getTypeString() {
		return "bracelet";
	}

	@Override
	public String getPart1() {
		return Jewelry.BRACELET_PART1;
	}

	@Override
	public String getPart2() {
		return Jewelry.BRACELET_PART2;
	}
}
