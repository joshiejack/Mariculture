package mariculture.core.blocks.base;

import java.util.List;

import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.FluidTransferHelper;
import mariculture.core.helpers.cofh.StringHelper;
import mariculture.core.util.ITank;
import mariculture.factory.blocks.Tank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileTank extends TileEntity implements IFluidHandler, ITank {	
	protected FluidTransferHelper transfer;
	public Tank tank;
	
	public TileTank() {
		tank = new Tank(getTankSize());
	}

	public int getTankSize() {
		return FluidContainerRegistry.BUCKET_VOLUME;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		 return drain(ForgeDirection.UNKNOWN, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
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
		if(tank.getFluid() == null) {
			return null;
		}
		
		if(tank.getFluidAmount() - transfer < 0) {
			return null;
		}
		
		return new FluidStack(tank.getFluidID(), transfer);
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
	public void updateEntity() {
		super.updateEntity();
		if(transfer == null)
			transfer = new FluidTransferHelper(this);
	}

	@Override
	public int getTankScaled(int i) {
		int qty = tank.getFluidAmount();
		int max = tank.getCapacity();
		
		return (max != 0) ? (qty * i) / max : 0;
	}

	@Override
	public FluidStack getFluid() {
		return tank.getFluid();
	}

	@Override
	public String getFluidName() {
		return StringHelper.getFluidName(tank.getFluid());
	}
	
	@Override
	public List getFluidQty(List tooltip) {
		return StringHelper.getFluidQty(tooltip, tank.getFluid(), tank.getCapacity());
	}
}
