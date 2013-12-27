package mariculture.core.helpers;

import mariculture.core.blocks.TileTankMachine;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public class FluidInventoryHelper {	
	public static void transferTo(int x, int y, int z, TileTankMachine machine) {
		World world = machine.worldObj;
		if (world.getBlockTileEntity(x, y, z) == null) {
			return;
		}
	
		if (world.getBlockTileEntity(x, y, z) instanceof IFluidHandler) {
			IFluidHandler tank = (IFluidHandler) world.getBlockTileEntity(x, y, z);
			if(!FluidInventoryHelper.attemptTransfer(tank, machine.getTransferRate(), machine)) {
				if(!FluidInventoryHelper.attemptTransfer(tank, 50, machine)) {
					if(!FluidInventoryHelper.attemptTransfer(tank, 25, machine)) {
						if(!FluidInventoryHelper.attemptTransfer(tank, 5, machine)) {
							FluidInventoryHelper.attemptTransfer(tank, 1, machine);
						}
					}
				}
			}
		}
	}

	public static boolean attemptTransfer(IFluidHandler tank, int size, TileTankMachine machine) {
		if(machine.getFluid() == null) {
			return false;
		}
		
		try {
			if (tank.fill(ForgeDirection.UP, new FluidStack(machine.getFluid().fluidID, size), false) >= size) {
				if(machine.getFluid() != null) {
					if (machine.getFluid().amount - size >= 0) {
						tank.fill(ForgeDirection.UP, new FluidStack(machine.getFluid().fluidID, size), true);
						machine.drain(ForgeDirection.DOWN, size, true);
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return false;
	}
	
	public static boolean addTo(World world, int x, int y, int z, FluidStack fluid, boolean doClear) {
		if (world.getBlockTileEntity(x, y, z) == null) {
			return false;
		}
		
		if(world.getBlockTileEntity(x, y, z) instanceof IFluidHandler) {
			IFluidHandler tank = (IFluidHandler) world.getBlockTileEntity(x, y, z);
			if(tank.fill(ForgeDirection.UP, fluid, false) >= fluid.amount) {
				if(doClear) {
					return tank.fill(ForgeDirection.UP, fluid, true) >= fluid.amount;
				} else {
					return true;
				}
			}
		}
		
		return false;
	}

}
