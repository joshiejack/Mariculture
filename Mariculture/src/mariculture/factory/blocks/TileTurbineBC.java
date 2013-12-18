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
import cofh.api.energy.IEnergyHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileTurbineBC extends TileTankMachine implements IPowerReceptor, IPipeConnection, IPowerEmitter, ISidedInventory {
	public ForgeDirection direction = ForgeDirection.UP;
	public double angle = 0;
	public double angle_external = 0;
	protected boolean isActive;
	protected int purity;
	protected int heat;
	public float energy;
	public float extraEnergy;
	public EnergyStage energyStage = EnergyStage.BLUE;
	protected PowerHandler provider = new PowerHandler(this, PowerHandler.Type.ENGINE);
	protected int tick;
	Random rand = new Random();

	public float getEnergy() {
		return this.energy;
	}

	public TileTurbineBC() {
		super.setInventorySize(5);
		provider.configure(2.0F, maxEnergyReceived(), 1.0F, maxEnergy());
		provider.configurePowerPerdition(1, 100);
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
	
	public void update() {
		if(!worldObj.isRemote) {
			if(energyStage == EnergyStage.OVERHEAT) {
				overheat();
			} else {
				if(worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
					TileEntity tile = this.worldObj.getBlockTileEntity(this.xCoord + direction.offsetX, 
											this.yCoord + direction.offsetY, this.zCoord + direction.offsetZ);
					if (tile != null) {
						if (isPoweredTile(tile, direction)) {
							if (energy < maxEnergy()) {
								int liquidUse = (int) (heat * purity);
								if (canUseLiquid() && tank.getFluidAmount() - liquidUse >= 0) {
									addEnergy(heat);
									
									if (purity > 0 && rand.nextInt(purity) == 0 || purity < 0) {
										if(tick %5 == 0) {
											drain(ForgeDirection.UP, liquidUse, true);
										}
									}
								}
							}
							
							if(energy > 0) {
								PowerReceiver receptor = ((IPowerReceptor) tile).getPowerReceiver(direction.getOpposite());

								float extracted = getPowerToExtract();
								if (extracted > 0) {
									isActive = true;
									float needed = receptor.receiveEnergy(PowerHandler.Type.ENGINE, extracted, direction.getOpposite());
									isActive = extractEnergy(receptor.getMinEnergyReceived(), needed, false) > 0.0F;
									extractEnergy(receptor.getMinEnergyReceived(), needed, true);
								}
							}
						} else {
							if(tick %10 == 0) {
								isActive = false;
							}
						}
					} else {
						if(tick %10 == 0) {
							isActive = false;
						}
					}
				} else {
					if(tick %10 == 0) {
						isActive = false;
					}
				}
			}
			
			if (tick >= Extra.REFRESH_CLIENT_RATE) {
				sendUpdateToClient();

				tick = 0;
			}
			
			energyStage = computeEnergyStage();
		}
	}

	public void updateEntity() {
		super.updateEntity();

		tick++;

		if (tick == 20) {
			processContainers();
		}
		
		animate();
		update();
	}

	protected float getPowerToExtract() {
		TileEntity tile = this.worldObj.getBlockTileEntity(this.xCoord + direction.offsetX, this.yCoord
				+ direction.offsetY, this.zCoord + direction.offsetZ);
		PowerReceiver receptor = ((IPowerReceptor) tile).getPowerReceiver(direction.getOpposite());
		return extractEnergy(receptor.getMinEnergyReceived(), receptor.getMaxEnergyReceived(), false);
	}

	protected boolean isPoweredTile(TileEntity tile, ForgeDirection side) {
		if (tile instanceof IPowerReceptor) {
			return ((IPowerReceptor) tile).getPowerReceiver(side.getOpposite()) != null;
		}

		return false;
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

	protected void overheat() {
		subtractEnergy(5.0F);
	}

	private void setActive(boolean isActive) {
		if (this.isActive != isActive) {
			this.isActive = isActive;
		}

		sendUpdateToClient();
	}

	public boolean isActive() {
		return this.isActive;
	}

	public boolean switchOrientation() {
		for (int i = direction.ordinal() + 1; i <= direction.ordinal() + 6; ++i) {
			ForgeDirection o = ForgeDirection.VALID_DIRECTIONS[i % 6];

			Position pos = new Position(xCoord, yCoord, zCoord, o);
			pos.moveForwards(1);
			TileEntity tile = worldObj.getBlockTileEntity((int) pos.x, (int) pos.y, (int) pos.z);

			if (isPoweredTile(tile, o)) {
				direction = o;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,
						worldObj.getBlockId(xCoord, yCoord, zCoord));

				return true;
			}
		}
		return false;
	}

	public ForgeDirection getOrientation() {
		return this.direction;
	}

	public PowerHandler.PowerReceiver getPowerReceiver(ForgeDirection side) {
		return this.provider.getPowerReceiver();
	}

	public void doWork(PowerHandler workProvider) {
		if (worldObj.isRemote)
			return;
		float e = this.provider.useEnergy(1.0F, maxEnergyReceived(), true) * 0.95F;
		this.extraEnergy += e;
		addEnergy(e);
	}

	public float getEnergyLevel() {
		return this.energy / maxEnergy();
	}

	protected EnergyStage computeEnergyStage() {
		float energyLevel = getEnergyLevel();
		if (energyLevel < 0.2F)
			return EnergyStage.BLUE;
		if (energyLevel < 0.4F)
			return EnergyStage.GREEN;
		if (energyLevel < 0.6F)
			return EnergyStage.YELLOW;
		if (energyLevel < 0.8F)
			return EnergyStage.ORANGE;
		if (energyLevel < 1.0F) {
			return EnergyStage.RED;
		}
		return EnergyStage.OVERHEAT;
	}

	public final EnergyStage getEnergyStage() {
		if (worldObj.isRemote) {
			if (this.energyStage == EnergyStage.OVERHEAT)
				return this.energyStage;
			EnergyStage newStage = computeEnergyStage();

			if (this.energyStage != newStage) {
				this.energyStage = newStage;
			}
		}

		return this.energyStage;
	}

	public void addEnergy(float addition) {
		this.energy += addition;

		if (this.energy > maxEnergy())
			this.energy = maxEnergy();
	}

	public void subtractEnergy(float subtraction) {
		this.energy -= subtraction;
		if (this.energy < 0.0F)
			this.energy = 0.0F;
	}

	public float extractEnergy(float min, float max, boolean doExtract) {
		if (energy < min)
			return 0;

		float actualMax;

		if (max > maxEnergyExtracted())
			actualMax = maxEnergyExtracted();
		else
			actualMax = max;

		if (actualMax < min)
			return 0;

		float extracted;

		if (energy >= actualMax) {
			extracted = actualMax;
			if (doExtract)
				energy -= actualMax;
		} else {
			extracted = energy;
			if (doExtract)
				energy = 0;
		}

		return extracted;
	}

	public int maxEnergy() {
		return 750;
	}

	public int maxEnergyExtracted() {
		return 50;
	}

	public int maxEnergyReceived() {
		return 450;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.direction = ForgeDirection.getOrientation(tagCompound.getInteger("Orientation"));
		this.energy = tagCompound.getFloat("PowerStored");
		this.purity = tagCompound.getInteger("Purity");
		this.heat = tagCompound.getInteger("Heat");
	}

	@Override
	public void writeToNBT(final NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("Orientation", direction.ordinal());
		tagCompound.setFloat("PowerStored", this.energy);
		tagCompound.setInteger("Heat", this.heat);
		tagCompound.setInteger("Purity", this.purity);
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

	public IPipeConnection.ConnectOverride overridePipeConnection(IPipeTile.PipeType type, ForgeDirection with) {
		if (type == IPipeTile.PipeType.POWER)
			return IPipeConnection.ConnectOverride.DEFAULT;
		if (with == this.direction)
			return IPipeConnection.ConnectOverride.DISCONNECT;
		return IPipeConnection.ConnectOverride.DEFAULT;
	}

	public boolean canEmitPowerFrom(ForgeDirection side) {
		return side == this.direction;
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

	@Override
	public World getWorld() {
		return worldObj;
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
			os.writeFloat(energy);
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
			energy = inputStream.readFloat();
			heat = inputStream.readInt();
			purity = inputStream.readInt();

		} catch (IOException e) {
			e.printStackTrace(System.err);
			return;
		}

		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile instanceof TileTurbineBC) {
			((TileTurbineBC) tile).heat = heat;
			((TileTurbineBC) tile).purity = purity;
			((TileTurbineBC) tile).energy = energy;
			((TileTurbineBC) tile).isActive = isActive;
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
}