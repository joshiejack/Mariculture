package mariculture.magic.jewelry;

import mariculture.core.lib.Jewelry;

public class ItemBracelet extends ItemJewelry {
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
