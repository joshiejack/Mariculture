package mariculture.core.blocks;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import buildcraft.api.power.IPowerEmitter;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;
import buildcraft.api.transport.IPipeConnection;
import buildcraft.api.transport.IPipeTile;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.TileEnergyHandler;

public class TileConverter extends TileEnergyHandler implements IPipeConnection, IPowerEmitter {
	protected PowerHandler provider;
	
	public TileConverter() {
		storage = new EnergyStorage(20);
	}
	
	public void update() {
		if(!worldObj.isRemote) {
			Object[] obj = getNextTile();
			if(obj != null) {
				TileEntity tile = (TileEntity) obj[0];
				ForgeDirection side = (ForgeDirection) obj[1];

				int extract = storage.extractEnergy(storage.getEnergyStored(), true);
				if(storage.extractEnergy(extract, true) > 0) {
					storage.extractEnergy(extract, false);
					PowerReceiver receptor = ((IPowerReceptor) tile).getPowerReceiver(side.getOpposite());
					receptor.receiveEnergy(Type.STORAGE, (float) extract/10, side);
				}
			}
		}
	}

	private Object[] getNextTile() {
		if(worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord) != null && 
				isPoweredTile(worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord), ForgeDirection.WEST)) {
			return new Object[] { worldObj.getBlockTileEntity(xCoord + 1, yCoord, zCoord), ForgeDirection.WEST };
		} else if(worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord) != null &&
				isPoweredTile(worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord), ForgeDirection.EAST)) {
			return new Object[] { worldObj.getBlockTileEntity(xCoord - 1, yCoord, zCoord), ForgeDirection.EAST };
		} else if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1) != null &&
				isPoweredTile(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1), ForgeDirection.SOUTH)) {
			return new Object[] { worldObj.getBlockTileEntity(xCoord, yCoord, zCoord - 1), ForgeDirection.SOUTH };
		} else if(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1) != null &&
				isPoweredTile(worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1), ForgeDirection.NORTH)) {
			return new Object[] { worldObj.getBlockTileEntity(xCoord, yCoord, zCoord + 1), ForgeDirection.NORTH };
		} else if(worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord) != null &&
				isPoweredTile(worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord), ForgeDirection.UP)) {
			return new Object[] { worldObj.getBlockTileEntity(xCoord, yCoord - 1, zCoord), ForgeDirection.UP };
		} else if(worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord) != null &&
				isPoweredTile(worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord), ForgeDirection.DOWN)) {
			return new Object[] { worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord), ForgeDirection.DOWN };
		}
		
		return null;
	}

	public void updateEntity() {
		update();
	}

	protected boolean isPoweredTile(TileEntity tile, ForgeDirection side) {
		if (tile instanceof IPowerReceptor) {
			return ((IPowerReceptor) tile).getPowerReceiver(side.getOpposite()) != null && !(tile instanceof IEnergyHandler);
		}

		return false;
	}

	public PowerHandler.PowerReceiver getPowerReceiver(ForgeDirection side) {
		return this.provider.getPowerReceiver();
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
	}

	@Override
	public void writeToNBT(final NBTTagCompound tagCompound) {
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

	public IPipeConnection.ConnectOverride overridePipeConnection(IPipeTile.PipeType type, ForgeDirection with) {
		if (type == IPipeTile.PipeType.POWER)
			return IPipeConnection.ConnectOverride.DEFAULT;
		else
			return IPipeConnection.ConnectOverride.DISCONNECT;
	}

	public boolean canEmitPowerFrom(ForgeDirection side) {
		return true;
	}
}