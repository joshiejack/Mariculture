package joshie.mariculture.modules.fishery.tile;

import joshie.mariculture.core.helpers.InventoryHelper;
import joshie.mariculture.core.util.interfaces.Faceable;
import joshie.mariculture.core.util.capabilities.MCFluidTank;
import joshie.mariculture.core.util.capabilities.SingleStackHandler;
import joshie.mariculture.core.util.tile.TileInventoryTank;
import joshie.mariculture.modules.fishery.Fishery;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileGutter extends TileInventoryTank implements Faceable {
    private EnumFacing facing;

    private final SingleStackHandler itemHandler = new SingleStackHandler(this, 1) {
        @Override
        public boolean isValidForInsertion(int slot, ItemStack stack) {
            return InventoryHelper.oreStartsWith(stack, "fish");
        }

        @Override
        public boolean isValidForExtraction(int slot, ItemStack stack) {
            return false;
        }
    };

    private final FluidTank fluidHandler = new MCFluidTank(this, Fluid.BUCKET_VOLUME * 10) {
        @Override
        public boolean canFillFluidType(FluidStack fluid) {
            return fluid.getFluid() == Fishery.FISH_OIL;
        }
    };

    @Override
    public EnumFacing getFacing() {
        return facing;
    }

    @Override
    public void setFacing(EnumFacing facing) {
        this.facing = facing;
    }

    @Override
    protected SingleStackHandler getInventory() {
        return itemHandler;
    }

    @Override
    protected FluidTank getTank() {
        return fluidHandler;
    }
}
