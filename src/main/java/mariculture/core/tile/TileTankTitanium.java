package mariculture.core.tile;

import mariculture.core.util.Tank;

public class TileTankTitanium extends TileTankBlock {
    public TileTankTitanium() {
        tank = new Tank(TileTankBlock.TITANIUM_CAPACITY);
    }
}
