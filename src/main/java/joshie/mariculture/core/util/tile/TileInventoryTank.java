package joshie.mariculture.core.util.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidTank;

import static net.minecraftforge.fluids.capability.CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;

public abstract class TileInventoryTank extends TileInventory {
    protected abstract FluidTank getTank();

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        return capability == FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (facing != null && capability == FLUID_HANDLER_CAPABILITY)
            return (T) getTank();
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)  {
        super.readFromNBT(compound);
        getTank().readFromNBT(compound.getCompoundTag("Tank"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("Tank", getTank().writeToNBT(new NBTTagCompound()));
        return super.writeToNBT(compound);
    }
}
