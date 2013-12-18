package mariculture.factory.blocks;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.blocks.TileTankMachine;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.PacketIds;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import buildcraft.api.core.Position;
import buildcraft.api.power.IPowerEmitter;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile;
import buildcraft.api.transport.IPipeTile.PipeType;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileTurbineBase extends TileTankMachine implements ISidedInventory, IEnergyHandler, IPipeConnection, IPowerReceptor, IPowerEmitter {
	protected int tick;
	protected EnergyStorage storage;
	protected boolean isActive;
	public ForgeDirection direction = ForgeDirection.UP;
	public double angle = 0;
	public double angle_external = 0;
	
	protected PowerHandler provider = new PowerHandler(this, PowerHandler.Type.ENGINE);
	public EnergyStage energyStage = EnergyStage.BLUE;
	Random rand = new Random();
	protected int purity;
	protected int heat;

	public TileTurbineBase() {
		super.setInventorySize(5);
		storage = new EnergyStorage(maxEnergyStored());
		provider.configure(2.0F, 10.0F, 1.0F, 500F);
		provider.configurePowerPerdition(0, 0);
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
	
	private void update() {
		this.isActive = this.isActive();
		
		if(!worldObj.isRemote) {
			if(isActive()) {
				generatePower();
				transferPower();
			}
			
			if (tick %Extra.REFRESH_CLIENT_RATE == 0) {
				sendUpdateToClient();
			}
			
			energyStage = computeEnergyStage();
		}
	}
	
	protected EnergyStage computeEnergyStage() {
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
	
	private void generatePower() {
		int liquidUse = (int) (heat * purity);
				
		if (purity > 0 && rand.nextInt(purity) == 0 || purity < 0) {
			if(tick %10 == 0) {
				drain(ForgeDirection.UP, liquidUse, true);
			}
		}
			
		this.storage.modifyEnergyStored(heat * 20);
	}
	
	private void transferPower() {
		TileEntity tile = this.worldObj.getBlockTileEntity(this.xCoord + direction.offsetX, 
									this.yCoord + direction.offsetY, this.zCoord + direction.offsetZ);
		
		if (tile instanceof IEnergyHandler) {
            if(((IEnergyHandler) tile).canInterface(this.direction.getOpposite())) {
            	this.storage.modifyEnergyStored(-((IEnergyHandler)tile).receiveEnergy(direction.getOpposite(), Math.min(maxEnergyExtracted(), this.storage.getEnergyStored()), false));
            	return;
            }
		}
		
		if (isPoweredTile(tile, direction)) {
			if(this.storage.getEnergyStored() > 0) {
				PowerReceiver receptor = ((IPowerReceptor) tile).getPowerReceiver(direction.getOpposite());
				float extracted = extractEnergy(receptor.getMinEnergyReceived(), receptor.getMaxEnergyReceived());
				if (extracted > 0) {
					if(this.storage.extractEnergy((int) (extracted * 10), true) >= extracted) {
						float needed = receptor.receiveEnergy(PowerHandler.Type.ENGINE, extracted, direction.getOpposite());
						this.storage.extractEnergy((int) (extracted * 10), false);
					}
				}
			}
		}
	}
	
	public void addEnergy() {
		if(!worldObj.isRemote) {
			if(provider.getEnergyStored() > 0) {
				storage.receiveEnergy((int) (provider.getEnergyStored() * 10), false);
				provider.setEnergy(0F);
			}
		}
	}

	public void updateEntity() {
		super.updateEntity();

		tick++;

		if (tick %20 == 0) {
			processContainers();
		}
		
		addEnergy();
		update();
		animate();
	}

	@Override
	protected int getMaxCalculation(int count) {
		return ((FluidContainerRegistry.BUCKET_VOLUME / 2) + (count * 128));
	}

	@Override
	protected void updateUpgrades() {
		super.updateUpgrades();
		int purityCount = MaricultureHandlers.upgrades.getData("purity", this);
		int heatCount = MaricultureHandlers.upgrades.getData("temp", this);
		this.purity = purityCount + 1;
		this.heat = heatCount + 1;
	}
	
	public boolean switchOrientation() {
		for (int i = direction.ordinal() + 1; i <= direction.ordinal() + 6; ++i) {
			ForgeDirection o = ForgeDirection.VALID_DIRECTIONS[i % 6];

			Position pos = new Position(xCoord, yCoord, zCoord, o);
			pos.moveForwards(1);
			TileEntity tile = worldObj.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);

			if (tile instanceof IEnergyHandler) {
				if(((IEnergyHandler) tile).canInterface(o)) {
					direction = o;
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord));
	
					return true;
				}
			}
			
			//BC Legacy Support
			if (isPoweredTile(tile, o)) {
				direction = o;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, worldObj.getBlockId(xCoord, yCoord, zCoord));

				return true;
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
		this.purity = tagCompound.getInteger("Purity");
		this.heat = tagCompound.getInteger("Heat");
		storage.readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("Orientation", direction.ordinal());
		tagCompound.setInteger("Heat", this.heat);
		tagCompound.setInteger("Purity", this.purity);
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

	protected void sendUpdateToClient() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		final DataOutputStream os = new DataOutputStream(bos);
		try {
			os.writeInt(PacketIds.TURBINE);
			os.writeInt(xCoord);
			os.writeInt(yCoord);
			os.writeInt(zCoord);
			os.writeBoolean(isActive);
			os.writeInt(purity);
			os.writeInt(heat);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToAllAround(xCoord, yCoord, zCoord, 25, worldObj.provider.dimensionId, packet);
	}

	public static void handlePacket(Packet250CustomPayload packet, World world) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));

		int id;
		int x;
		int y;
		int z;
		boolean isActive;
		float energy;
		int heat;
		int purity;

		try {
			id = inputStream.readInt();
			x = inputStream.readInt();
			y = inputStream.readInt();
			z = inputStream.readInt();
			isActive = inputStream.readBoolean();
			heat = inputStream.readInt();
			purity = inputStream.readInt();

		} catch (IOException e) {
			e.printStackTrace(System.err);
			return;
		}

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileTurbineBase) {
			((TileTurbineBase) tile).heat = heat;
			((TileTurbineBase) tile).purity = purity;
			((TileTurbineBase) tile).isActive = isActive;
		}
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
	
	//BC Legacy Support
	public float extractEnergy(float min, float max) {
		float energy = this.storage.getEnergyStored()/10;

		if (energy < min) {
			return 0;
		}

		float actualMax;
		int trueMax = maxEnergyExtracted();

		if (max > trueMax) {
			actualMax = trueMax;
		} else {
			actualMax = max;
		}

		if (actualMax < min)
			return 0;

		float extracted;

		if (energy >= actualMax) {
			extracted = actualMax;
		} else {
			extracted = energy;
		}

		return extracted;
	}
	
	protected boolean isPoweredTile(TileEntity tile, ForgeDirection side) {
		if (tile instanceof IPowerReceptor) {
			return ((IPowerReceptor) tile).getPowerReceiver(side.getOpposite()) != null;
		}

		return false;
	}
	
	@Override
	public boolean canEmitPowerFrom(ForgeDirection side) {
		return side == this.direction;
	}

	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return this.provider.getPowerReceiver();
	}

	@Override
	public void doWork(PowerHandler workProvider) {
		
	}

	@Override
	public World getWorld() {
		return this.worldObj;
	}

	@Override
	public ConnectOverride overridePipeConnection(PipeType type, ForgeDirection with) {
		if (type == IPipeTile.PipeType.POWER)
			return IPipeConnection.ConnectOverride.DEFAULT;
		if (with == this.direction)
			return IPipeConnection.ConnectOverride.DISCONNECT;
		return IPipeConnection.ConnectOverride.DEFAULT;
	}
}