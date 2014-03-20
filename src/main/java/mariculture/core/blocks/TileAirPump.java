package mariculture.core.blocks;

import java.util.List;
import java.util.Random;

import mariculture.core.Core;
import mariculture.core.blocks.base.TileStorageTank;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.BlockTransferHelper;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.ArmorSlot;
import mariculture.core.lib.Extra;
import mariculture.core.lib.Modules;
import mariculture.core.network.PacketAirPump;
import mariculture.core.network.PacketOrientationSync;
import mariculture.core.network.Packets;
import mariculture.core.util.FluidDictionary;
import mariculture.core.util.IFaceable;
import mariculture.core.util.Tank;
import mariculture.diving.Diving;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class TileAirPump extends TileStorageTank implements IEnergyHandler, IFaceable {
	protected BlockTransferHelper helper;
	protected EnergyStorage storage = new EnergyStorage(100);
	public ForgeDirection orientation = ForgeDirection.WEST;
	public boolean animate;
	private double wheelAngle1 = 0;
	private double wheelAngle2 = 0;
	private Random rand = new Random();
	
	public enum Type {
		DISPLAY, CHECK, CLEAR, DISPLAY_GREEN, DISPLAY_RED
	}
	
	public TileAirPump() {
		tank = new Tank(8000);
		inventory = new ItemStack[1];
	}
	
	public boolean updateAirArea(Type type) {
		on = !on;
		
		int total = 0;
		for (int i = -2; i <= 2; i++) {
			for (int j = -2; j <= 2; j++) {
				for (int l = 0; l <= 2; l++) {
					if((i == 2 || i == -2) && (j == 2 || j == -2))
						continue;
					
					int x2 = xCoord + i;
					int y2 = yCoord + l;
					int z2 = zCoord + j;

					if (worldObj.isAirBlock(x2, y2, z2)) {
						total++;
					}
					
					if(!type.equals(Type.CHECK)) {
						if(type.equals(Type.DISPLAY_GREEN)) {
							worldObj.spawnParticle("happyVillager", x2 + 0.5D, y2 + 0.5D, z2 + 0.5D, 0, 0, 0);
						} else if(type.equals(Type.DISPLAY_RED)) {
							worldObj.spawnParticle("reddust", x2 + 0.5D, y2 + 0.5D, z2 + 0.5D, 0, 0, 0);
						}
					}
				}
			}
		}
			
		if(type.equals(Type.CHECK)) {
			return (total >= 52) ? true : false;
		} else if(type.equals(Type.DISPLAY)) {
			if(total >= 52)
				updateAirArea(Type.DISPLAY_GREEN);
			else
				updateAirArea(Type.DISPLAY_RED);
		}
		
		return true;
	}
	
	private int tick;
	public void supplyWithAir(int value, double x, double y, double z) {
		if (!worldObj.isRemote && worldObj.getTileEntity(xCoord, yCoord, zCoord) != null) {
			List playerList = worldObj.getEntitiesWithinAABB(EntityPlayer.class, worldObj.getTileEntity(xCoord, yCoord, zCoord).getBlockType()
					.getCollisionBoundingBoxFromPool(worldObj, xCoord, yCoord, zCoord).expand(x, y, z));
			if (!playerList.isEmpty()) {
				for (int i = 0; i < playerList.size(); i++) {
					EntityPlayer player = (EntityPlayer) playerList.get(i);
					if (PlayerHelper.hasArmor(player, ArmorSlot.TOP, Diving.divingTop)
							&& PlayerHelper.hasArmor(player, ArmorSlot.HAT, Diving.divingHelmet)) {
						if (player.isInsideOfMaterial(Material.water)) {
							if(value == 300) {
								player.setAir(300);
							} else {
								player.setAir(player.getAir() + 35);
							}
						}
					}
				}
			}
		}
	}
	
	public void doPoweredPump(boolean rf, int value, double x, double y, double z) {
		if(rf) {
			if (storage.extractEnergy(100, true) < 100) {
				return;
			}
	
			storage.extractEnergy(100, false);
		}
			
		if(updateAirArea(Type.CHECK)) {
			if(Modules.isActive(Modules.diving)) {
				supplyWithAir(300, 40.0D, 64.0D, 40.0D);
			}
				
			animate = true;
				
			if(canUpdate()) Packets.updateAround(this, new PacketAirPump(xCoord, yCoord, zCoord));
		}
			
		suckUpGas(4096);
		
		if(helper == null) helper = new BlockTransferHelper(this);
		helper.ejectFluid(new int[] { 8000, 4000, 2000, 1000, 100, 20, 1 });
	}

	public boolean suckUpGas(int chance) {
		boolean collected = false;
		for(int x = xCoord - 6; x < xCoord + 7; x++) {
			for (int z = zCoord - 6; z < zCoord + 7; z++) {
				for (int y = yCoord; y < yCoord + 10; y++) {
					if(rand.nextInt(1) == 0) {
						if(isNaturalGas(x, y, z)) {
							if(fill(ForgeDirection.UP, FluidRegistry.getFluidStack(FluidDictionary.natural_gas, 1000), false) >= 1000) {
								if(!worldObj.isRemote) {
									fill(ForgeDirection.UP, FluidRegistry.getFluidStack(FluidDictionary.natural_gas, 1000), true);
									worldObj.setBlockToAir(x, y, z);
								}
								
								collected = true;
							}
						}
					}
				}
			}
		}
		
		return collected;
	}
	
	private boolean isNaturalGas(int x, int y, int z) {
		return worldObj.getBlock(x, y, z) == Core.air && worldObj.getBlockMetadata(x, y, z) == AirMeta.NATURAL_GAS;
	}
	
	public double getWheelAngle(int which) {
		return which == 1? wheelAngle1: wheelAngle2;
	}
	
	@Override
	public boolean canUpdate() {
		return Extra.PUMP_ANIMATE;
	}

	@Override
	public void updateEntity() {
		if (animate) {
			wheelAngle1 = wheelAngle1 + 0.1;
			wheelAngle2 = wheelAngle2 + 0.1;

			if (wheelAngle1 > 6.2198) {
				wheelAngle1 = 0;
				animate = false;
			}

			if (wheelAngle2 > 6.2198) {
				wheelAngle2 = 0;
				animate = false;
			}

			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	public boolean on = true;
	
	public boolean onTick(int i) {
		return tick % i == 0;
	}
	
	@Override
	public void rotate() {
		orientation = BlockHelper.rotate(orientation, 4);
		Packets.updateAround(this, new PacketOrientationSync(xCoord, yCoord, zCoord, orientation));
	}
	
	@Override
	public void setFacing(ForgeDirection dir) {
		this.orientation = dir;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
		int receive = storage.receiveEnergy(maxReceive, simulate);		
		if(!simulate && (getEnergyStored(from) > 0)) {
			tick++;
			if(onTick(300)) {
				doPoweredPump(true, 300, 40.0D, 64.0D, 40.0D);		
			}
		}
		
		return receive;
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
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		storage.readFromNBT(nbt);
		on = nbt.getBoolean("DisplayOn");
		orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		storage.writeToNBT(nbt);
		nbt.setBoolean("DisplayOn", on);
		nbt.setInteger("Orientation", orientation.ordinal());
	}
}
