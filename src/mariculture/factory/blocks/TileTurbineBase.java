package mariculture.factory.blocks;

import mariculture.core.blocks.base.TileMachineTankPowered;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.helpers.cofh.BlockHelper;
import mariculture.core.lib.Extra;
import mariculture.core.network.Packets;
import mariculture.core.util.Rand;
import mariculture.factory.items.ItemRotor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;

public abstract class TileTurbineBase extends TileMachineTankPowered {
	public static enum EnergyStage {
		BLUE, GREEN, YELLOW, ORANGE, RED, OVERHEAT;
	}
	
	public EnergyStage energyStage = EnergyStage.BLUE;
	public ForgeDirection direction = ForgeDirection.UP;
	public static final int rotor = 6;
	
	public double angle = 0;
	public double angle_external = 0;
	
	public TileTurbineBase() {
		inventory = new ItemStack[7];
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { rotor };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return slot == rotor;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public EjectSetting getEjectType() {
		return EjectSetting.NONE;
	}

	@Override
	public int getRFCapacity() {
		return 1000;
	}

	@Override
	public boolean canWork() {
		return canDrain() && mode.canWork(this, mode) && energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored() && hasTurbineWheel();
	}
	
	public boolean hasTurbineWheel() {
		if(inventory[rotor] != null && inventory[rotor].getItem() instanceof ItemRotor) {
			return canUseRotor();
		}
		
		return false;
	}

	@Override
	public void updateMachine() {
		if(canWork) {
			generatePower();
			if(Extra.TURBINE_ANIM)
				animate();
		}
		
		transferPower();
		energyStage = computeEnergyStage();
	}
	
	public void animate() {
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

		worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
	}
	
	@Override
	public void doBattery() {
		if(inventory[5] != null && inventory[5].getItem() instanceof IEnergyContainerItem) {
			int rf = extractEnergy(ForgeDirection.UP, 10000, true);
			if(rf > 0) {
				int drain = ((IEnergyContainerItem)inventory[5].getItem()).receiveEnergy(inventory[5], rf, true);
				if(drain > 0) {
					extractEnergy(ForgeDirection.UP, drain, false);
					((IEnergyContainerItem)inventory[5].getItem()).receiveEnergy(inventory[5], drain, false);
				}
			}
		}
	}
	
	public float getAngle() {
		return (float) angle;
	}
	
	public void transferPower() {
		TileEntity tile = BlockHelper.getAdjacentTileEntity(worldObj, xCoord, yCoord, zCoord, direction);

		if (tile instanceof IEnergyHandler) {
			if(((IEnergyHandler) tile).canInterface(direction.getOpposite())) {
				int extract = -((IEnergyHandler)tile).receiveEnergy(direction.getOpposite(), Math.min(maxEnergyExtracted(), energyStorage.getEnergyStored()), false);
				if(extract < 0)
					animate();
				energyStorage.modifyEnergyStored(extract);
			}
		}
	}
	
	public void generatePower() {
		if(canDrain() && Rand.rand.nextInt((purity >= 1)? purity: 1) < 1) {
			tank.drain(getDrainAmount(), true);
			if(inventory[rotor] == null || (onTick(20) && inventory[rotor].attemptDamageItem(1, Rand.rand))) {
				inventory[rotor] = null;
				return;
			}
		}
		
		energyStorage.modifyEnergyStored(getEnergyGenerated());
	}
	
	public boolean canDrain() {
		int drain = getDrainAmount();
		FluidStack fluid = tank.drain(drain, false);
		return fluid != null && fluid.amount >= drain && canUseFluid();
	}
	
	public int getDrainAmount() {
		return speed;
	}
	
	//RF Generated per tick
	public abstract int getEnergyGenerated();
	//RF Maximum Output per tick
	public abstract int maxEnergyExtracted();
	//Whether the Engine can use the current internal fluid
	public abstract boolean canUseFluid();
	//Whether the Engine can use the Rotor
	public abstract boolean canUseRotor();
	
	public boolean switchOrientation() {
		for (int i = direction.ordinal() + 1; i <= direction.ordinal() + 6; ++i) {
			ForgeDirection facing = ForgeDirection.VALID_DIRECTIONS[i % 6];
			TileEntity tile = BlockHelper.getAdjacentTileEntity(worldObj, xCoord, yCoord, zCoord, facing);
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

	public ForgeDirection getOrientation() {
		return this.direction;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		direction = ForgeDirection.getOrientation(tagCompound.getInteger("Orientation"));
		energyStorage.readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("Orientation", direction.ordinal());
		energyStorage.writeToNBT(tagCompound);
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
}
