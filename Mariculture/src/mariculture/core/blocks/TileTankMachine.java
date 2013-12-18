package mariculture.core.blocks;

import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.util.ContainerInteger;
import mariculture.core.util.PacketIntegerUpdate;
import mariculture.factory.blocks.Tank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileTankMachine extends TileEntity implements IFluidHandler, IInventory, IUpgradable {
	
	protected ItemStack[] inventory;
	protected Tank tank;
	
	private int tick = 0;
	
	public TileTankMachine() {
		tank = new Tank(getMaxCalculation(0));
	}

	// Tank Information
	public FluidStack getFluid() {
		return tank.getFluid();
	}

	protected void setInventorySize(int size) {
		inventory = new ItemStack[size];
	}

	public void updateFluid(FluidStack fluid) {
		tank.setFluid(fluid);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return tank.fill(resource, doFill);
	}

	public FluidStack drain(FluidStack res, boolean doDrain) {
		return drain(ForgeDirection.UP, res, doDrain);
	}
	
	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if (resource == null || !resource.isFluidEqual(getFluid())) {
			return null;
		}

		return drain(from, resource.amount, doDrain);
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

	public String getLiquidName() {
		FluidStack fluid = tank.getFluid();
		return ((fluid != null && FluidHelper.getName(fluid.getFluid()) != null))? FluidHelper.getName(fluid.getFluid()): StatCollector.translateToLocal("mariculture.string.empty");
	}

	public String getLiquidQty() {
		int qty = tank.getFluidAmount();
		int max = tank.getCapacity();
		
		return "" + qty + "/" + max;
	}

	public int getTankScaled(int i) {
		int qty = tank.getFluidAmount();
		int max = tank.getCapacity();
		
		return (max != 0) ? (qty * i) / max : 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		NBTTagList tagList = tagCompound.getTagList("Inventory");

		for (int i = 0; i < tagList.tagCount(); i++) {
			final NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);

			final byte slot = tag.getByte("Slot");

			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		
		tank.readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tank.writeToNBT(tagCompound);

		NBTTagList itemList = new NBTTagList();

		for (int i = 0; i < inventory.length; i++) {
			ItemStack stack = inventory[i];

			if (stack != null) {
				final NBTTagCompound tag = new NBTTagCompound();

				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				itemList.appendTag(tag);
			}
		}

		tagCompound.setTag("Inventory", itemList);
	}

	// Inventory Stuff

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = getStackInSlot(slot);

		if (stack != null) {
			if (stack.stackSize <= amount) {
				setInventorySlotContents(slot, null);
			}

			else {
				stack = stack.splitStack(amount);
				if (stack.stackSize == 0) {
					setInventorySlotContents(slot, null);
				}
			}
		}

		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);

		if (stack != null) {
			setInventorySlotContents(slot, null);
		}

		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();
		}
	}

	@Override
	public String getInvName() {
		return "";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this
				&& player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	protected int getMaxCalculation(int count) {
		int tankRate = FluidContainerRegistry.BUCKET_VOLUME;
		return (tankRate * 20) + (count * tankRate);
	}

	@Override
	public void updateEntity() {
		
		if (!this.worldObj.isRemote) {
			tick++;
			if (tick % 20 == 0) {
				updateUpgrades();
			}
		}
	}

	protected void updateUpgrades() {
		int storageCount = MaricultureHandlers.upgrades.getData("storage", this);
		tank.setCapacity(getMaxCalculation(storageCount));
	}

	public void getGUINetworkData(int i, int j) {
		int id = (tank.getFluid() != null)?tank.getFluid().fluidID: 0;
		switch (i) {
		case 0:
			tank.setFluidID(j);
			break;
		case 1:
			tank.setFluidAmount(j);
			break;
		case 2:
			tank.setCapacity(j);
			break;
		}
	}

	public void sendGUINetworkData(ContainerInteger container, EntityPlayer player) {
		PacketIntegerUpdate.send(container, 0, tank.getFluidID(), player);
		PacketIntegerUpdate.send(container, 1, tank.getFluidAmount(), player);
		PacketIntegerUpdate.send(container, 2, tank.getCapacity(), player);
	}

	@Override
	public ItemStack[] getUpgrades() {
		return null;
	}

	public int getTransferRate() {
		return 1;
	}
}
