package mariculture.core.blocks.base;

import java.util.List;

import mariculture.core.helpers.cofh.StringHelper;
import mariculture.core.util.ITank;
import mariculture.factory.blocks.Tank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileMultiStorageTank extends TileMultiStorage implements IFluidHandler, ITank {
	public Tank tank;

	@Override
	public FluidStack getFluid(int transfer) {
		if(master == null)
			return null;
		
		TileMultiStorageTank mstr = ((TileMultiStorageTank)worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord));
		if(mstr.tank.getFluid() == null)
			return null;
		if(mstr.tank.getFluidAmount() - transfer < 0)
			return null;
		
		return new FluidStack(mstr.tank.getFluidID(), transfer);
	}

	@Override
	public int getTankScaled(int i) {
		int qty = tank.getFluidAmount();
		int max = tank.getCapacity();
		
		return (max != 0) ? (qty * i) / max : 0;
	}

	@Override
	public FluidStack getFluid() {
		return master != null? ((TileMultiStorageTank)worldObj
									.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord)).tank.getFluid(): null;
	}
	
	@Override
	public void setFluid(FluidStack fluid) {
		if(master != null)
			((TileMultiStorageTank)worldObj.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord)).tank.setFluid(fluid);
	}

	@Override
	public String getFluidName() {
		return StringHelper.getFluidName(tank.getFluid());
	}
	
	@Override
	public List getFluidQty(List tooltip) {
		return StringHelper.getFluidQty(tooltip, tank.getFluid(), tank.getCapacity());
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return master != null? ((TileMultiStorageTank)worldObj
									.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord)).tank.fill(resource, doFill): 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return drain(ForgeDirection.UNKNOWN, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return master != null? ((TileMultiStorageTank)worldObj
									.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord)).tank.drain(maxDrain, doDrain): null;
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
		return master != null? new FluidTankInfo[] { ((TileMultiStorageTank)worldObj
												.getBlockTileEntity(master.xCoord, master.yCoord, master.zCoord)).tank.getInfo() }: null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tank.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tank.writeToNBT(nbt);
	}

	@Override
	public FluidStack getFluid(byte tank) {
		return getFluid();
	}

	@Override
	public void setFluid(FluidStack fluid, byte tank) {
		setFluid(fluid);
	}
}
