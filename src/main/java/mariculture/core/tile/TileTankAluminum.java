package mariculture.core.tile;

import mariculture.core.util.Tank;

public class TileTankAluminum extends TileTankBlock {
    public TileTankAluminum() {
        tank = new Tank(TileTankBlock.ALUMINUM_CAPACITY);
    }
}
