package mariculture.core.blocks;

import mariculture.core.helpers.FluidHelper;
import mariculture.core.util.ITank;
import mariculture.factory.blocks.Tank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileTankBlock extends TileEntity implements IFluidHandler, ITank {
	public int renderOffset;
	
	public Tank tank;
	
	public TileTankBlock() {
		tank = new Tank(32000);
	}

	public float getFluidAmountScaled() {
		return (float) (tank.getFluid().amount - renderOffset) / (float) (tank.getCapacity() * 1.01F);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}

	/* Updating */
	public boolean canUpdate() {
		return true;
	}

	@Override
	public void updateEntity() {
		if (renderOffset > 0) {
			renderOffset -= 6;
			worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
		}
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		int amount =  tank.fill(resource, doFill);
        if (amount > 0 && doFill) {
            renderOffset = resource.amount;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }

        return amount;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		 return drain(ForgeDirection.UNKNOWN, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack amount = tank.drain(maxDrain, doDrain);
        if (amount != null && doDrain) {
            renderOffset = -maxDrain;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
        
        return amount;
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
}
