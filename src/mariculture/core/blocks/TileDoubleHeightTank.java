package mariculture.core.blocks;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.lib.UtilMeta;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileDoubleHeightTank extends TileTankMachine {
	public TileDoubleHeightTank master() {
		if (isThis(xCoord, yCoord, zCoord)) {
			if (isThis(xCoord, yCoord + 1, zCoord)) {
				if (!isThis(xCoord, yCoord - 1, zCoord)) {
					if (!isThis(xCoord, yCoord + 2, zCoord)) {
						return this;
					}
				}
			}
		}

		if (isThis(xCoord, yCoord - 1, zCoord)) {
			if (isThis(xCoord, yCoord, zCoord)) {
				if (!isThis(xCoord, yCoord + 1, zCoord)) {
					if (!isThis(xCoord, yCoord - 2, zCoord)) {
						return (TileDoubleHeightTank) worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord);
					}
				}
			}
		}

		return null;
	}
	
	public boolean isThis(int x, int y, int z) {
		return false;
	}
	
	@Override
	public int getSizeInventory() {
		return master() != null ? master().inventory.length : 0;
	}

	@Override
	public ItemStack getStackInSlot(int slotIndex) {
		return master() != null ? master().inventory[slotIndex] : null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if (master() != null) {
			master().inventory[slot] = stack;
			if (stack != null && stack.stackSize > getInventoryStackLimit()) {
				stack.stackSize = getInventoryStackLimit();
			}
		}
	}

	public boolean isBuilt() {
		return master() != null;
	}
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(master() == null) {
			return 0;
		}
		
		return master().tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if(master() == null) {
			return null;
		}
		
		if (resource == null || !resource.isFluidEqual(master().getFluid())) {
			return null;
		}

		return master().drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(master() == null) {
			return null;
		}
		
		return master().tank.drain(maxDrain, doDrain);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if(master() == null) {
			return null;
		}
		
		return new FluidTankInfo[] { master().tank.getInfo() };
	}
}
