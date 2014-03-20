package mariculture.factory.blocks;

import mariculture.core.blocks.base.TileTank;
import mariculture.core.handlers.FluidDicHandler;
import mariculture.core.util.Tank;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public class TileDictionaryFluid extends TileTank {
	public String toConvertTo;
	public TileDictionaryFluid() {
		tank = new Tank(0);
		toConvertTo = FluidRegistry.WATER.getName();
	}
	
	public double getCapacity() {
		return tank.getCapacity();
	}
	
	//RETURNS THE AMOUNT OF LIQUID THAT HAS BEEN TAKEN AWAYS!!!
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(FluidDicHandler.areSameDicNames(resource, toConvertTo)) {
			int newAmount = FluidDicHandler.getValue(toConvertTo);
			int oldAmount = FluidDicHandler.getValue(resource.getFluid().getName());
			
			//Setup the coordinates
			int x = xCoord + from.offsetX;
			int y = yCoord + from.offsetY;
			int z = zCoord + from.offsetZ;
			//Finding a tile Entity to drain to
			System.out.println("d");
			if(worldObj.getTileEntity(x, y, z) instanceof IFluidHandler && resource.amount >= oldAmount) {
				int drainAmount = Math.min(resource.amount, oldAmount);
				IFluidHandler tank = (IFluidHandler) worldObj.getTileEntity(x, y, z);
				FluidStack output = new FluidStack(FluidRegistry.getFluid(toConvertTo), newAmount);
				if(tank.fill(from.getOpposite(), output, false) == newAmount) {
					if(doFill) {
						tank.fill(from, output, true);
					}
					
					return drainAmount;
				} else return 0;
			} else return 0;
		} else return 0;
		
		/*
		if(FluidDicHandler.getDicName(resource).equals(FluidDicHandler.getDicName(toConvertTo))) {
			ArrayList<FluidDicEntry> entries = FluidDicHandler.entries.get(FluidDicHandler.getDicName(toConvertTo));
			int ret = 0;
			int swap = 0;
			
			if(entries != null) {
				for(FluidDicEntry entry: entries) {
					if(entry.fluid.equals(toConvertTo))swap = entry.ratio;
					if(entry.fluid.equals(resource.getFluid().getName())) ret = entry.ratio;
				}
				
				int x = xCoord + from.offsetX;
				int y = yCoord + from.offsetY;
				int z = zCoord + from.offsetZ;
				FluidStack fluid = new FluidStack(FluidRegistry.getFluid(toConvertTo), swap);
				if(worldObj.getTileEntity(x, y, z) instanceof IFluidHandler) {
					IFluidHandler tank = (IFluidHandler) worldObj.getTileEntity(x, y, z);
					if(tank.fill(from, fluid, false) == swap) {
						if(doFill) {
							tank.fill(from, fluid, true);
						}
						
						return ret;
					}
				}
				
				return 0;
			} else return 0;
		} else return 0; */
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		toConvertTo = nbt.getString("ToConvertTo");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setString("ToConvertTo", toConvertTo);
	}
}
