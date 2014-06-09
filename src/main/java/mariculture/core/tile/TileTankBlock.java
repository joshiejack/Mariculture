package mariculture.core.tile;

import java.util.List;

import mariculture.core.helpers.FluidHelper;
import mariculture.core.network.PacketHandler;
import mariculture.core.util.ITank;
import mariculture.core.util.Tank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileTankBlock extends TileEntity implements IFluidHandler, ITank {
    public Tank tank;

    public TileTankBlock() {
        tank = new Tank(16000);
    }

    public float getFluidAmountScaled() {
        return tank.getFluid().amount / (tank.getCapacity() * 1.01F);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int amount = tank.fill(resource, doFill);
        if (amount > 0 && doFill) {
            PacketHandler.syncFluids(this, getFluid());
        }
        return amount;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        FluidStack amount = tank.drain(maxDrain, doDrain);
        if (amount != null && doDrain) {
            PacketHandler.syncFluids(this, getFluid());
        }
        return amount;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return drain(ForgeDirection.UNKNOWN, resource.amount, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { tank.getInfo() };
    }

    @Override
    public FluidStack getFluid(int transfer) {
        if (tank.getFluid() == null) return null;

        if (tank.getFluidAmount() - transfer < 0) return null;

        return new FluidStack(tank.getFluidID(), transfer);
    }

    @Override
    public String getFluidName() {
        return FluidHelper.getFluidName(tank.getFluid());
    }

    @Override
    public List getFluidQty(List tooltip) {
        return FluidHelper.getFluidQty(tooltip, tank.getFluid(), tank.getCapacity());
    }

    @Override
    public FluidStack getFluid() {
        return tank.getFluid();
    }

    @Override
    public int getTankScaled(int i) {
        int qty = tank.getFluidAmount();
        int max = tank.getCapacity();

        return max != 0 ? qty * i / max : 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        tank.readFromNBT(tagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tank.writeToNBT(tagCompound);
    }

    @Override
    public void setFluid(FluidStack fluid) {
        tank.setFluid(fluid);
    }

    @Override
    public FluidStack getFluid(byte tank) {
        return getFluid();
    }

    @Override
    public void setFluid(FluidStack fluid, byte tank) {
        setFluid(fluid);
    }

    public double getCapacity() {
        return tank.getCapacity();
    }
}
