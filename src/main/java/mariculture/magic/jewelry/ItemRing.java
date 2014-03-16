package mariculture.magic.jewelry;


public class ItemRing extends ItemJewelry {	
	@Override
	public JewelryType getType() {
		return JewelryType.RING;
	}

	@Override
	public int getMaxDurability() {
		return 30;
	}

	@Override
	public int getMaxLevel() {
		return 30;
	}

	@Override
	public boolean renderBinding() {
		return true;
	}
}
