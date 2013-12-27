package mariculture.core.blocks.base;

import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.FluidTransferHelper;
import mariculture.core.util.ITank;
import mariculture.factory.blocks.Tank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileStorageTank extends TileStorage implements IFluidHandler, ITank {
	
	protected FluidTransferHelper transfer;
	public Tank tank;

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
	
	public String getLiquidName() {
		FluidStack fluid = tank.getFluid();
		return ((fluid != null && FluidHelper.getName(fluid.getFluid()) != null))? FluidHelper.getName(fluid.getFluid()): StatCollector.translateToLocal("mariculture.string.empty");
	}
	
	public String getLiquidQty() {
		int qty = tank.getFluidAmount();
		int max = tank.getCapacity();
		
		return "" + qty + "/" + max;
	}
	
	public FluidStack getFluid() {
		return tank.getFluid();
	}
	
	public int getTankScaled(int i) {
		int qty = tank.getFluidAmount();
		int max = tank.getCapacity();
		
		return (max != 0) ? (qty * i) / max : 0;
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
	
	public void updateEntity() {
		super.updateEntity();
		if(transfer == null)
			transfer = new FluidTransferHelper(this);
	}
}
