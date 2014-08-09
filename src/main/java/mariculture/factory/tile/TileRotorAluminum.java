package mariculture.factory.tile;

public class TileRotorAluminum extends TileRotor {
    @Override
    protected String getBlock() {
        return "blockAluminum";
    }

    @Override
    protected double getTier() {
        return 2D;
    }
    
    @Override
    protected int getMaxDamage() {
        return 720000;
    }
}
