package mariculture.core.util;

import java.util.List;

import mariculture.core.helpers.FluidHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;

public class Tank implements IFluidTank, ITank {
    protected FluidStack fluid;
    protected int capacity;
    protected TileEntity tile;

    public Tank(int capacity) {
        this(null, capacity);
    }

    public Tank(FluidStack stack, int capacity) {
        fluid = stack;
        this.capacity = capacity;
    }

    public Tank readFromNBT(NBTTagCompound nbt) {
        if (!nbt.hasKey("Empty")) {
            FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt);

            if (fluid != null) {
                setFluid(fluid);
            }
        }
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if (fluid != null) {
            fluid.writeToNBT(nbt);
        } else {
            nbt.setString("Empty", "");
        }
        return nbt;
    }

    public void setFluid(FluidStack fluid) {
        this.fluid = fluid;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setFluidAmount(int amount) {
        if (fluid == null) return;

        fluid.amount = amount;
    }

    public int getFluidID() {
        if (fluid == null) return -1;

        return fluid.getFluidID();
    }

    public void setFluidID(int id) {
        if (id == -1) {
            fluid = null;
        } else {
            if (fluid == null) {
                fluid = new FluidStack(id, 0);
            }

            fluid = new FluidStack(id, fluid.amount);
        }
    }

    /* IFluidTank */
    @Override
    public FluidStack getFluid() {
        return fluid;
    }

    @Override
    public int getFluidAmount() {
        if (fluid == null) return 0;
        return fluid.amount;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public FluidTankInfo getInfo() {
        return new FluidTankInfo(this);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource == null) return 0;
        if (!doFill) {
            if (fluid == null) return Math.min(capacity, resource.amount);

            if (!fluid.isFluidEqual(resource)) return 0;

            return Math.min(capacity - fluid.amount, resource.amount);
        }

        if (fluid == null) {
            fluid = new FluidStack(resource, Math.min(capacity, resource.amount));

            if (tile != null) {
                FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(fluid, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, this));
            }
            return fluid.amount;
        }

        if (!fluid.isFluidEqual(resource)) return 0;
        int filled = capacity - fluid.amount;

        if (resource.amount < filled) {
            fluid.amount += resource.amount;
            filled = resource.amount;
        } else {
            fluid.amount = capacity;
        }

        if (tile != null) {
            FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(fluid, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, this));
        }
        return filled;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (fluid == null) return null;

        int drained = maxDrain;
        if (fluid.amount < drained) {
            drained = fluid.amount;
        }

        FluidStack stack = new FluidStack(fluid, drained);
        if (doDrain) {
            fluid.amount -= drained;
            if (fluid.amount <= 0) {
                fluid = null;
            }

            if (tile != null) {
                FluidEvent.fireEvent(new FluidEvent.FluidDrainingEvent(fluid, tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord, this));
            }
        }
        return stack;
    }

    public int fill(FluidStack resource, boolean doFill, Tank tank) {
        if (tank.getFluid() != null && resource.getFluid() == tank.getFluid().getFluid()) return 0;
        return fill(resource, doFill);
    }

    @Override
    public FluidStack getFluid(int transfer) {
        if (getFluid() == null) return null;
        if (getFluidAmount() - transfer < 0) return null;
        return new FluidStack(getFluid(), transfer);
    }

    @Override
    public int getTankScaled(int i) {
        int qty = getFluidAmount();
        int max = getCapacity();

        return max != 0 ? qty * i / max : 0;
    }

    @Override
    public FluidStack getFluid(byte tank) {
        return getFluid();
    }

    @Override
    public String getFluidName() {
        return FluidHelper.getFluidName(getFluid());
    }

    @Override
    public List getFluidQty(List tooltip) {
        return FluidHelper.getFluidQty(tooltip, getFluid(), getCapacity());
    }

    @Override
    public void setFluid(FluidStack fluid, byte tank) {
        setFluid(fluid);
    }
}
