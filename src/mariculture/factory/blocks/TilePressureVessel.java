package mariculture.factory.blocks;

import java.util.Random;

import mariculture.api.core.IBlacklisted;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.blocks.TileTankMachineDouble;
import mariculture.core.util.PacketIntegerUpdate;
import mariculture.factory.FactoryEvents;
import mariculture.factory.gui.ContainerPressureVessel;
import mariculture.factory.items.ItemArmorFLUDD;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

public class TilePressureVessel extends TileTankMachineDouble implements IBlacklisted, ISidedInventory {
	private int tick = 0;
	private int tickAll = 0;

	private int times = 20;
	private int transfer = 100;
	private int slot = 0;

	private Random rand = new Random();

	public TilePressureVessel() {
		super.setInventorySize(4);
	}

	@Override
	public TilePressureVessel master() {
		return (TilePressureVessel) this.getMasterBlock();
	}

	private void fillFLUDD() {
		if (inventory[0] != null && liquidQty >= 1) {
			if (FactoryEvents.handleFill(inventory[0], false, this.transfer) != null
					&& this.liquidQty - this.transfer >= 0) {
				this.liquidQty -= this.transfer;
				inventory[0] = FactoryEvents.handleFill(inventory[0], true, this.transfer);
			} else if (FactoryEvents.handleFill(inventory[0], false, 1) != null && this.liquidQty - 1 >= 0) {
				this.liquidQty--;
				inventory[0] = FactoryEvents.handleFill(inventory[0], true, 1);
			}

		}
	}

	@Override
	public void updateEntity() {
		if (this.isFormed()) {
			if (this == master() && !this.worldObj.isRemote) {
				super.updateEntity();

				tick++;
				if (tick > 20) {
					tick = 0;
					fillFLUDD();
				}
			}

			if (tickAll % 16 == 0) {
				transfer();
			}

			tickAll++;
		}
	}

	@Override
	protected int getMaxCalculation(int count) {
		return ((FluidContainerRegistry.BUCKET_VOLUME * 32) + (count * FluidContainerRegistry.BUCKET_VOLUME));
	}

	@Override
	protected void updateUpgrades() {
		super.updateUpgrades();

		if (master() != null) {
			int purityCount = MaricultureHandlers.upgrades.getData("purity", master());
			int heatAmount = MaricultureHandlers.upgrades.getData("temp", master());

			this.times = heatAmount + 1;
			this.transfer = (purityCount + 1) * 125;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		this.writeToNBT(tagCompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, tagCompound);
	}

	@Override
	public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	}

	public void getGUINetworkData(int i, int j) {
		super.getGUINetworkData(i, j);

		switch (i) {
		case 3:
			times = j;
			break;
		case 4:
			transfer = j;
			break;
		}
	}

	public void sendGUINetworkData(ContainerPressureVessel container, EntityPlayer player) {
		super.sendGUINetworkData(container, player);

		PacketIntegerUpdate.send(container, 3, this.times, player);
		PacketIntegerUpdate.send(container, 4, this.transfer, player);
	}

	private void transfer() {
		if (master() != null) {
			for (int i = 0; i < master().times; i++) {
				slot++;
				switch (slot) {
				case 0:
					transferTo(this.xCoord, this.yCoord, this.zCoord - 1);
					break;
				case 1:
					transferTo(this.xCoord + 1, this.yCoord, this.zCoord);
					break;
				case 2:
					transferTo(this.xCoord - 1, this.yCoord, this.zCoord);
					break;
				case 3:
					transferTo(this.xCoord, this.yCoord + 1, this.zCoord);
					break;
				case 4:
					transferTo(this.xCoord, this.yCoord - 1, this.zCoord);
					break;
				case 5:
					transferTo(this.xCoord, this.yCoord, this.zCoord + 1);
					break;
				}

				if (slot > 5) {
					slot = 0;
				}
			}
		}
	}

	private void transferTo(int x, int y, int z) {
		if (worldObj.getBlockTileEntity(x, y, z) == null) {
			return;
		}

		if (this.worldObj.getBlockTileEntity(x, y, z) instanceof IBlacklisted) {
			IBlacklisted block = (IBlacklisted) this.worldObj.getBlockTileEntity(x, y, z);
			if (block.isBlacklisted(worldObj, x, y, z)) {
				return;
			}
		}

		if (this.worldObj.getBlockTileEntity(x, y, z) instanceof IFluidHandler) {
			IFluidHandler tank = (IFluidHandler) this.worldObj.getBlockTileEntity(x, y, z);
			if (!attemptTransfer(tank, master().transfer)) {
				if (!attemptTransfer(tank, 100)) {
					if (!attemptTransfer(tank, 75)) {
						if (!attemptTransfer(tank, 50)) {
							if (!attemptTransfer(tank, 25)) {
								attemptTransfer(tank, 1);
							}
						}
					}
				}
			}
		}
	}

	private boolean attemptTransfer(IFluidHandler tank, int size) {
		if (tank.fill(ForgeDirection.UP, new FluidStack(master().liquidId, size), false) >= size) {
			if (master().liquidQty - size > 0) {
				tank.fill(ForgeDirection.UP, new FluidStack(master().liquidId, size), true);
				master().drain(ForgeDirection.UP, size, true);
				return true;
			}
		}

		return false;
	}

	/** Upgrade Stuff **/
	@Override
	public ItemStack[] getUpgrades() {
		return new ItemStack[] { inventory[1], inventory[2], inventory[3] };
	}

	@Override
	public boolean isBlacklisted(World world, int x, int y, int z) {
		return true;
	}

	private static final int[] slots = new int[] { 0 };

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return slots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return isFull(stack);
	}

	public boolean isFull(ItemStack stack) {
		if (stack == null) {
			return false;
		}

		if (stack.getItem() instanceof ItemArmorFLUDD) {
			int water = (stack.hasTagCompound()) ? stack.stackTagCompound.getInteger("water") : ItemArmorFLUDD.STORAGE;
			return water >= ItemArmorFLUDD.STORAGE;
		}

		return false;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot == 3 && !isFull(stack);
	}
}
