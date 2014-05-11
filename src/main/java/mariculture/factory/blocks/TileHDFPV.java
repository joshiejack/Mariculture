package mariculture.factory.blocks;

import java.util.List;

import mariculture.core.network.Packet118FluidUpdate;
import mariculture.core.network.Packets;
import mariculture.core.util.ITank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileHDFPV extends TileEntity implements IFluidHandler, ITank {
	public FluidStack fluid;
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(fluid == null || resource.getFluid() == fluid.getFluid()) {
			if(doFill) {
				if(fluid == null) fluid = new FluidStack(resource.fluidID, 0);
				fluid.amount += resource.amount;
				Packets.updateTile(this, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid()).build());
			}
			return resource.amount;
		} else return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack ret = null;
		if(fluid != null) {
			ret = new FluidStack(fluid, Math.max(fluid.amount - maxDrain, 0));
		} else return ret;
		
		if(doDrain) {
			fluid.amount -= ret.amount;
			if(fluid.amount <= 0) fluid = null;
			Packets.updateTile(this, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid()).build());
		}
			
		return ret;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return this.fluid.amount < Integer.MAX_VALUE;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return fluid != null;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { new FluidTankInfo(fluid, Integer.MAX_VALUE)} ;
	}

	@Override
	public FluidStack getFluid(int transfer) {
		return fluid;
	}

	@Override
	public int getTankScaled(int i) {
		return 0;
	}

	@Override
	public FluidStack getFluid() {
		return fluid;
	}

	@Override
	public FluidStack getFluid(byte tank) {
		return getFluid();
	}

	@Override
	public String getFluidName() {
		return "";
	}

	@Override
	public List getFluidQty(List tooltip) {
		return null;
	}

	@Override
	public void setFluid(FluidStack fluid) {
		this.fluid = fluid;
	}

	@Override
	public void setFluid(FluidStack fluid, byte tank) {
		setFluid(fluid);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		fluid = FluidStack.loadFluidStackFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		if(fluid != null) fluid.writeToNBT(tagCompound);
	}
}
