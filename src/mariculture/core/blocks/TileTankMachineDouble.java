package mariculture.core.blocks;

import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.lib.MetalRates;
import mariculture.core.util.ContainerInteger;
import mariculture.core.util.PacketIntegerUpdate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileTankMachineDouble extends TileDoubleBlock implements IFluidHandler, IInventory, IUpgradable {
	protected int liquidMax;
	protected int liquidQty;
	protected int liquidId;
	protected ItemStack[] inventory;
	private int tick = 0;

	// Tank Information

	public TileTankMachineDouble master() {
		return (TileTankMachineDouble) this.getMasterBlock();
	}

	public FluidStack getFluid() {
		if (this.liquidId == 0) {
			return null;
		}

		return new FluidStack(this.liquidId, this.liquidQty);
	}

	protected void setInventorySize(int size) {
		inventory = new ItemStack[size];
	}

	public void updateFluid(FluidStack fluid) {
		this.liquidId = fluid.fluidID;
		this.liquidQty = fluid.amount;
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

		if (master() != null) {
			int res = 0;

			if (master().liquidQty > 0 && master().liquidId != resource.fluidID) {
				return 0;
			}

			if (master().liquidQty + resource.amount <= master().liquidMax) {
				if (doFill) {
					master().liquidQty += resource.amount;
				}
				res = resource.amount;
			} else {
				res = master().liquidMax - master().liquidQty;

				if (doFill) {
					master().liquidQty = master().liquidMax;
				}
			}

			if (doFill) {
				master().liquidId = resource.fluidID;
			}

			return res;
		}
		return 0;
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
		if (master() != null) {
			if (maxDrain == OreDictionary.WILDCARD_VALUE) {
				master().liquidQty = 0;
				master().liquidId = 0;
				return null;
			}

			if (master().liquidId <= 0) {
				return null;
			}

			if (master().liquidQty <= 0) {
				return null;
			}

			int used = maxDrain;

			if (master().liquidQty < used) {
				used = master().liquidQty;
			}

			if (doDrain) {
				master().liquidQty -= used;
			}

			FluidStack drained = new FluidStack(master().liquidId, used);

			if (master().liquidQty <= 0) {
				master().liquidId = 0;
			}

			return drained;
		}

		return null;
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
		if (master() != null) {
			return new FluidTankInfo[] { new FluidTankInfo(master().getFluid(), master().liquidMax) };
		}

		return null;
	}

	public String getLiquidName() {
		Fluid fluid = FluidRegistry.getFluid(liquidId);

		if (liquidId > 0) {
			String name = fluid.getLocalizedName();
			if (name.startsWith("fluid.")) {
				name = name.substring(6);
				if (name.startsWith("tile.")) {
					name = name.substring(5);
				}
			}

			return name.substring(0, 1).toUpperCase() + name.substring(1);
		}

		return StatCollector.translateToLocal("mariculture.string.empty");
	}

	public String getLiquidQty() {
		return "" + liquidQty + "/" + liquidMax;
	}

	public int getTankScaled(final int i) {
		return (liquidMax != 0) ? (liquidQty * i) / liquidMax : 0;
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

		liquidId = tagCompound.getInteger("LiquidId");
		liquidQty = tagCompound.getInteger("LiquidQty");
		liquidMax = tagCompound.getInteger("LiquidMax");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setInteger("LiquidId", this.liquidId);
		tagCompound.setInteger("LiquidQty", this.liquidQty);
		tagCompound.setInteger("LiquidMax", this.liquidMax);

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
		final ItemStack stack = getStackInSlot(slot);

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
		return ((MetalRates.INGOT * 10) + (count * MetalRates.ORE));
	}

	@Override
	public void updateEntity() {

		if (!this.worldObj.isRemote) {
			tick++;
			if (tick % 20 == 0) {
				updateUpgrades();
			}

			if (liquidQty > liquidMax) {
				this.liquidQty = liquidMax;
			} else if (liquidQty <= 0) {
				liquidQty = 0;
			}
		}
	}

	protected void updateUpgrades() {
		int storageCount = MaricultureHandlers.upgrades.getData("storage", this);

		liquidMax = getMaxCalculation(storageCount);
	}

	public void getGUINetworkData(int i, int j) {
		switch (i) {
		case 0:
			liquidId = j;
			break;
		case 1:
			liquidQty = j;
			break;
		case 2:
			liquidMax = j;
			break;
		}
	}

	public void sendGUINetworkData(ContainerInteger container, EntityPlayer player) {
		PacketIntegerUpdate.send(container, 0, this.liquidId, player);
		PacketIntegerUpdate.send(container, 1, this.liquidQty, player);
		PacketIntegerUpdate.send(container, 2, this.liquidMax, player);
	}

	@Override
	public ItemStack[] getUpgrades() {
		return null;
	}
}
