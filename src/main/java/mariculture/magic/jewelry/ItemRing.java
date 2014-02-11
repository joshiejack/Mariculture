package mariculture.magic.jewelry;

import mariculture.core.lib.Jewelry;

public class ItemRing extends ItemJewelry {	
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
