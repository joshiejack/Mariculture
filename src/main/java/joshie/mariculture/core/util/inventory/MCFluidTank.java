package joshie.mariculture.core.util.inventory;

import joshie.mariculture.core.util.tile.TileMC;
import net.minecraftforge.fluids.FluidTank;

public class MCFluidTank extends FluidTank {
    private final TileMC tile;

    public MCFluidTank(TileMC tile, int amount) {
        super(amount);
        this.tile = tile;
    }

    @Override
    protected void onContentsChanged() {
        tile.markDirty();
    }
}
