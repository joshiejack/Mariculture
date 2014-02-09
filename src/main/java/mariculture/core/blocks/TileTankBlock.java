package mariculture.core.blocks;

import java.util.List;

import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.cofh.StringHelper;
import mariculture.core.network.Packet118FluidUpdate;
import mariculture.core.network.Packets;
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
	public Tank tank;
	
	public TileTankBlock() {
		tank = new Tank(16000);
	}

	public float getFluidAmountScaled() {
		return (float) (tank.getFluid().amount) / (float) (tank.getCapacity() * 1.01F);
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
	}

	public boolean canUpdate() {
		return false;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		int amount =  tank.fill(resource, doFill);
        if (amount > 0 && doFill)
        	Packets.updateTile(this, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid()).build());
        return amount;
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		FluidStack amount = tank.drain(maxDrain, doDrain);
        if (amount != null && doDrain)
        	Packets.updateTile(this, 64, new Packet118FluidUpdate(xCoord, yCoord, zCoord, getFluid()).build());
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
		if(tank.getFluid() == null) {
			return null;
		}
		
		if(tank.getFluidAmount() - transfer < 0) {
			return null;
		}
		
		return new FluidStack(tank.getFluidID(), transfer);
	}
	
	@Override
	public String getFluidName() {
		return StringHelper.getFluidName(tank.getFluid());
	}
	
	@Override
	public List getFluidQty(List tooltip) {
		return StringHelper.getFluidQty(tooltip, tank.getFluid(), tank.getCapacity());
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
