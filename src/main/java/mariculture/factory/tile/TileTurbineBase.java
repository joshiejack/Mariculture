package mariculture.factory.tile;

import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.core.gui.ContainerMariculture;
import mariculture.core.gui.feature.Feature;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.cofh.BlockHelper;
import mariculture.core.lib.Extra;
import mariculture.core.network.PacketTurbine;
import mariculture.core.network.Packets;
import mariculture.core.tile.base.TileStorageTank;
import mariculture.core.util.IFaceable;
import mariculture.core.util.IMachine;
import mariculture.core.util.IPowered;
import mariculture.core.util.IRedstoneControlled;
import mariculture.core.util.Tank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;

public abstract class TileTurbineBase extends TileStorageTank implements IUpgradable, IMachine, IRedstoneControlled, IEnergyHandler, IPowered, ISidedInventory, IFaceable {
	protected int machineTick = 0;
	//Upgrade Stats
	protected int purity = 0;
	protected int heat = 0;
	protected int storage = 0;
	protected int speed = 0;
	protected int rf = 0;
	//RS Config
	protected RedstoneMode mode;
	//Facing
	public ForgeDirection orientation = ForgeDirection.UP;
	//Energy
	public EnergyStage energyStage = EnergyStage.BLUE;
	public EnergyStorage energyStorage;
	//GUI
	protected boolean hasGUI;
	//Anims
	public boolean isAnimating;
	public boolean isTransferringPower;
	public boolean isCreatingPower;
	public double angle = 0;
	public double angle_external = 0;
	
	//Rotor
	public static final int rotor = 6;
	
	public TileTurbineBase() {
		hasGUI = true;
		inventory = new ItemStack[7];
		tank = new Tank(getTankCapacity());
		mode = RedstoneMode.LOW;
		energyStorage = new EnergyStorage(getRFCapacity());
	}
	
	@Override
	public ItemStack[] getInventory() {
		return inventory;
	}
	
	@Override
	public ItemStack[] getUpgrades() {
		return new ItemStack[] {
				inventory[0], inventory[1], inventory[2]
		};
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	public boolean onTick(int i) {
		return machineTick % i == 0;
	}

	@Override
	public void updateUpgrades() {
		purity = MaricultureHandlers.upgrades.getData("purity", this);
		heat = MaricultureHandlers.upgrades.getData("temp", this);
		storage = MaricultureHandlers.upgrades.getData("storage", this);
		speed = MaricultureHandlers.upgrades.getData("speed", this);
		rf = MaricultureHandlers.upgrades.getData("rf", this);
		
		tank.setCapacity(getTankCapacity());
		if(tank.getFluidAmount() > tank.getCapacity())
			tank.setFluidAmount(tank.getCapacity());
		
		energyStorage.setCapacity(getRFCapacity());
		if(energyStorage.getEnergyStored() > energyStorage.getMaxEnergyStored())
			energyStorage.setEnergyStored(energyStorage.getMaxEnergyStored());
	}
	
	//Turbine Type Based Values
	public int getRFCapacity() {
		return 1000 + rf;
	}
	
	public int getTankCapacity() {
		int tankRate = FluidContainerRegistry.BUCKET_VOLUME;
		return (tankRate * 20) + (storage * tankRate);
	}
	
	//Energy GENERATED Per Tick
	public abstract int getEnergyGenerated();
	//Energy Transferred Total Maximum!
	public abstract int getEnergyTransferMax();
	
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
	
//Updates
	@Override
	public void updateEntity() {
		super.updateEntity();
		
		machineTick++;
		if(!worldObj.isRemote) {
			if(onTick(20)) {
				if(hasGUI) {
					FluidHelper.process(this, 3, 4);
					updateUpgrades();
					doBattery();
				}
				
				if(isAnimating)
					energyStage = computeEnergyStage();
				else
					energyStage = EnergyStage.BLUE;
			}
		}
		
		updateTurbine();
	}
	
//Turbine Updates
	public void updateTurbine() {
		if(!worldObj.isRemote) {
			if(canOperate())
				addPower();
			else
				isCreatingPower = false;
			transferPower();
		}
		
		animate();
	}
	
//Add More Power
	public abstract boolean canOperate();
	public abstract void addPower();

//Transfer The Power
	public void transferPower() {
		TileEntity tile = BlockHelper.getAdjacentTileEntity(worldObj, xCoord, yCoord, zCoord, orientation);
		if (tile instanceof IEnergyHandler && energyStorage.getEnergyStored() > 0) {
			if(((IEnergyHandler) tile).canConnectEnergy(orientation.getOpposite())) {
				int extract = -((IEnergyHandler)tile).receiveEnergy(orientation.getOpposite(), Math.min(getEnergyTransferMax(), energyStorage.getEnergyStored()), false);
				energyStorage.modifyEnergyStored(extract);
				
				if(tile.toString().contains("conduit") && extract == -75) {
					isTransferringPower = false;
				} else {
					isTransferringPower = true;
				}
			} else {
				isTransferringPower = false;
			}
		} else {
			isTransferringPower = false;
		}
	}
	
//Animation Data
	public float getAngle() {
		return (float) angle;
	}
	
	public float getExternalAngle() {
		return (float) angle_external;
	}
	
	public void animate() {
		if(Extra.TURBINE_ANIM) {
			if(!worldObj.isRemote && onTick(Extra.TURBINE_RATE)) {
				isAnimating = isCreatingPower || isTransferringPower;
				Packets.updateAround(this, new PacketTurbine(xCoord, yCoord, zCoord, isAnimating));
			} else if(worldObj.isRemote) {
				if(isAnimating) {
					angle = angle + 0.1;
					angle_external = angle_external + 0.01;
					if (energyStage == EnergyStage.GREEN) {
						angle = angle + 0.1;
					}
		
					if (energyStage == EnergyStage.YELLOW) {
						angle = angle + 0.15;
					}
		
					if (energyStage == EnergyStage.RED) {
						angle = angle + 0.15;
					}
		
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
		}
	}
	
	public static enum EnergyStage {
		BLUE, GREEN, YELLOW, ORANGE, RED;
	}
	
	protected EnergyStage computeEnergyStage() {
		if(speed <= 2) {
			return EnergyStage.BLUE;
		} else if(speed <= 4) {
			return EnergyStage.GREEN;
		} else if(speed <= 8) {
			return EnergyStage.YELLOW;
		} else if(speed <= 20) {
			return EnergyStage.ORANGE;
		}
		
		return EnergyStage.RED;
	}
	
//Facing
	public boolean rotate() {
		for (int i = orientation.ordinal() + 1; i <= orientation.ordinal() + 6; ++i) {
			ForgeDirection facing = ForgeDirection.VALID_DIRECTIONS[i % 6];
			TileEntity tile = BlockHelper.getAdjacentTileEntity(worldObj, xCoord, yCoord, zCoord, facing);
			if (tile instanceof IEnergyHandler) {
				if(((IEnergyHandler) tile).canConnectEnergy(facing)) {
					setFacing(facing);
					worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord));
	
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public ForgeDirection getFacing() {
		return this.orientation;
	}
	
	@Override
	public void setFacing(ForgeDirection dir) {
		this.orientation = dir;
		if(!worldObj.isRemote) {
			Packets.updateOrientation(this);
		}
	}
	
//Energy
	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
		return energyStorage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}
	
	@Override
	public int getEnergyStored(ForgeDirection from) {
		return energyStorage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return energyStorage.getMaxEnergyStored();
	}
	
	@Override
	public String getPowerText() {
		return getEnergyStored(ForgeDirection.UNKNOWN) + " / " + getMaxEnergyStored(ForgeDirection.UNKNOWN) + " RF";
	}

	@Override
	public int getPowerScaled(int i) {
		return (energyStorage.getEnergyStored() * i) / energyStorage.getMaxEnergyStored();
	}
	
//Packets
	@Override
	public Packet getDescriptionPacket()  {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbttagcompound);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.func_148857_g());
    }
	
//GUI Stuff	
	@Override
	public void getGUINetworkData(int id, int value) {
		switch (id) {
		case 0:
			mode = RedstoneMode.values()[value];
			break;
		case 1:
			tank.setFluidID(value);;
			break;
		case 2:
			tank.setFluidAmount(value);
			break;
		case 3:
			tank.setCapacity(value);;
			break;
		case 4:
			energyStorage.setEnergyStored(value);
			break;
		case 5: 
			energyStorage.setCapacity(value);
			break;
		}
	}
	
	@Override
	public void sendGUINetworkData(ContainerMariculture container, EntityPlayer player) {
		Packets.updateGUI(player, container, 0, mode.ordinal());
		Packets.updateGUI(player, container, 1, tank.getFluidID());
		Packets.updateGUI(player, container, 2, tank.getFluidAmount());
		Packets.updateGUI(player, container, 3, tank.getCapacity());
		Packets.updateGUI(player, container, 4, energyStorage.getEnergyStored());
		Packets.updateGUI(player, container, 5, energyStorage.getMaxEnergyStored());
	}
	
	@Override
	public void handleButtonClick(int id) {
		if(id == Feature.REDSTONE) setRSMode(RedstoneMode.toggle(getRSMode()));
	}
	
	@Override
	public RedstoneMode getRSMode() {
		return mode != null? mode: RedstoneMode.DISABLED;
	}

	@Override
	public void setRSMode(RedstoneMode mode) {
		this.mode = mode;
	}
	
	public boolean isNotificationVisible(NotificationType type) {
		switch(type) {
			default:
				return false;
		}
	}
	
//Read and Write
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		energyStorage.readFromNBT(nbt);
		mode = RedstoneMode.readFromNBT(nbt);
		purity = nbt.getInteger("Purity");
		heat = nbt.getInteger("Heat");
		storage = nbt.getInteger("Storage");
		speed = nbt.getInteger("Speed");
		rf = nbt.getInteger("RF");
		orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		energyStorage.writeToNBT(nbt);
		RedstoneMode.writeToNBT(nbt, mode);
		nbt.setInteger("Purity", purity);
		nbt.setInteger("Heat", heat);
		nbt.setInteger("Storage", storage);
		nbt.setInteger("Speed", speed);
		nbt.setInteger("RF", rf);
		nbt.setInteger("Orientation", orientation.ordinal());
	}
}
