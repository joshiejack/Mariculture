package mariculture.factory.tile;

public class TileRotorTitanium extends TileRotor {
    @Override
    protected String getBlock() {
        return "blockTitanium";
    }

    @Override
    protected double getTier() {
        return 5D;
    }
    
    @Override
    protected int getMaxDamage() {
        return 5760000;
    }
}
