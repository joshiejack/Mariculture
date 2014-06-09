package mariculture.magic.jewelry;

public class ItemBracelet extends ItemJewelry {
    @Override
    public JewelryType getType() {
        return JewelryType.BRACELET;
    }

    @Override
    public int getMaxDurability() {
        return 30;
    }

    @Override
    public boolean renderBinding() {
        return false;
    }
}
