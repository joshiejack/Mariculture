package mariculture.factory.blocks;

import java.util.Random;

import mariculture.core.blocks.core.TileMachineTank;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.Extra;
import mariculture.core.network.Packets;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import buildcraft.api.core.Position;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class TileTurbineBase extends TileMachineTank implements ISidedInventory, IEnergyHandler {
	protected EnergyStorage storage;
	public boolean isActive;
	public ForgeDirection direction = ForgeDirection.UP;
	public double angle = 0;
	public double angle_external = 0;
	
	public EnergyStage energyStage = EnergyStage.BLUE;
	Random rand = new Random();

	public TileTurbineBase() {
		this.inventory = new ItemStack[5];
		storage = new EnergyStorage(maxEnergyStored());
	}

	protected void processContainers() {
		ItemStack result = FluidHelper.getFluidResult(this, inventory[3], inventory[4]);
		if (result != null) {
			decrStackSize(3, 1);
			if (this.inventory[4] == null) {
				this.inventory[4] = result.copy();
			} else if (this.inventory[4].itemID == result.itemID) {
				++this.inventory[4].stackSize;
			}
		}
	}
	
	public boolean isPowered() {
		return this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord);
	}
	
	public boolean hasFuel() {
		int heat = this.heat + 1;
		int purity = this.purity + 1;
		int liquidUse = (int) (heat * purity);
		return this.tank.getFluidAmount() - liquidUse >= 0;
	}
	
	public boolean isActive() {
		return this.isPowered() && this.hasFuel();
	}
	
	public boolean canUseLiquid() {
		return true;
	}
	
	public void animate() {
		if (isActive) {
			this.angle = this.angle + 0.1;
			this.angle_external = this.angle_external + 0.01;
			if (energyStage == EnergyStage.GREEN) {
				this.angle = this.angle + 0.1;
			}

			if (energyStage == EnergyStage.YELLOW) {
				this.angle = this.angle + 0.15;
			}

			if (energyStage == EnergyStage.RED) {
				this.angle = this.angle + 0.15;
			}

			this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}
	
	@Override
	public void updateMachine() {
		if(onTick(20)) {
			processContainers();
		}
		
		this.isActive = this.isActive();
		
		if(!worldObj.isRemote) {
			if(isActive()) {
				generatePower();
				transferPower();
			}
			
			if (onTick(Extra.REFRESH_CLIENT_RATE)) {
				Packets.updateTile(this, 32, getDescriptionPacket());
			}
			
			energyStage = computeEnergyStage();
		}
		
		animate();
	}
	
	protected EnergyStage computeEnergyStage() {
		int heat = this.heat + 1;
		if(heat <= 2) {
			return EnergyStage.BLUE;
		} else if(heat <= 4) {
			return EnergyStage.GREEN;
		} else if(heat <= 8) {
			return EnergyStage.YELLOW;
		} else if(heat <= 20) {
			return EnergyStage.ORANGE;
		}
		
		return EnergyStage.RED;
	}
	
	public void generatePower() {
		int heat = this.heat + 1;
		int purity = this.purity + 1;
		int liquidUse = (int) (heat * purity);
				
		if (purity > 0 && rand.nextInt(purity) == 0 || purity < 0) {
			if(machineTick %10 == 0) {
				drain(ForgeDirection.UNKNOWN, liquidUse, true);
			}
		}
			
		this.storage.modifyEnergyStored(heat * 20);
	}
	
	protected void transferPower() {
		TileEntity tile = this.worldObj.getBlockTileEntity(this.xCoord + direction.offsetX, 
									this.yCoord + direction.offsetY, this.zCoord + direction.offsetZ);
		
		if (tile instanceof IEnergyHandler) {
            if(((IEnergyHandler) tile).canInterface(this.direction.getOpposite())) {
            	this.storage.modifyEnergyStored(-((IEnergyHandler)tile).receiveEnergy(direction.getOpposite(), Math.min(maxEnergyExtracted(), this.storage.getEnergyStored()), false));
            	return;
            }
		}
	}

	@Override
	public int getTankCapacity(int count) {
		return ((FluidContainerRegistry.BUCKET_VOLUME / 2) + (count * 128));
	}

	public boolean switchOrientation() {
		for (int i = direction.ordinal() + 1; i <= direction.ordinal() + 6; ++i) {
			ForgeDirection facing = ForgeDirection.VALID_DIRECTIONS[i % 6];

			Position pos = new Position(xCoord, yCoord, zCoord, facing);
			pos.moveForwards(1);
			TileEntity tile = worldObj.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);

			if (tile instanceof IEnergyHandler) {
				if(((IEnergyHandler) tile).canInterface(facing)) {
					direction = facing;
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord));
	
					return true;
				}
			}
		}
		return false;
	}

	public ForgeDirection getOrientation() {
		return this.direction;
	}

	public int maxEnergyStored() {
		return 750;
	}

	public int maxEnergyExtracted() {
		return 50;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.direction = ForgeDirection.getOrientation(tagCompound.getInteger("Orientation"));
		storage.readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("Orientation", direction.ordinal());
		storage.writeToNBT(tagCompound);
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

	@Override
	public ItemStack[] getUpgrades() {
		return new ItemStack[] { inventory[0], inventory[1], inventory[2] };
	}

	public double getAngle() {
		return angle;
	}
	
	public double getExternalAngle() {
		return angle_external;
	}

	public static enum EnergyStage {
		BLUE, GREEN, YELLOW, ORANGE, RED, OVERHEAT;
	}

	private static final int[] slots_top = new int[] { 3 };
	private static final int[] slots_bottom = new int[] { 4 };
	private static final int[] slots_sides = new int[] { 3, 4 };

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return side == 0 ? slots_bottom : (side == 1 ? slots_top : slots_sides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return this.isItemValidForSlot(slot, stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 4;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot == 3 && FluidHelper.isFluidOrEmpty(stack);
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public boolean canInterface(ForgeDirection from) {
		return true;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return storage.getMaxEnergyStored();
	}
}