package mariculture.core.blocks;

import java.util.List;
import java.util.Random;

import mariculture.core.Core;
import mariculture.core.blocks.base.TileStorageTank;
import mariculture.core.helpers.BlockTransferHelper;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.ArmorSlot;
import mariculture.core.lib.Extra;
import mariculture.core.lib.Modules;
import mariculture.core.network.Packet102AirPump;
import mariculture.core.network.Packets;
import mariculture.core.util.FluidDictionary;
import mariculture.diving.Diving;
import mariculture.factory.blocks.Tank;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

public class TileAirPump extends TileStorageTank implements IEnergyHandler {
	protected BlockTransferHelper helper;
	protected EnergyStorage storage = new EnergyStorage(100);
	public boolean animate;
	private double wheelAngle1 = 0;
	private double wheelAngle2 = 0;
	private Random rand = new Random();
	
	public enum Type {
		DISPLAY, CHECK, CLEAR
	}
	
	public TileAirPump() {
		tank = new Tank(8000);
		inventory = new ItemStack[1];
	}

	public double getWheelAngle(int which) {
		if (which == 1) {
			return wheelAngle1;
		}

		return wheelAngle2;
	}
	
	public void supplyWithAir(int value, double x, double y, double z) {
		if (!worldObj.isRemote && worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != null) {
			List playerList = worldObj.getEntitiesWithinAABB(EntityPlayer.class, worldObj.getBlockTileEntity(xCoord, yCoord, zCoord).getBlockType()
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
	
	public void doPoweredPump() {
		if (Extra.BUILDCRAFT_PUMP) {
			if (storage.extractEnergy(100, true) < 100) {
				return;
			}
			
			storage.extractEnergy(100, false);
			
			if(updateAirArea(Type.CHECK)) {
				if(Modules.diving.isActive()) {
					supplyWithAir(300, 40.0D, 50.0D, 40.0D);
				}
				
				animate = true;
				
				Packets.updateTile(this, 32, new Packet102AirPump(xCoord, yCoord, zCoord).build());
			}
			
			suckUpGas(4096);
		}
	}

	public void suckUpGas(int chance) {
		for(int x = xCoord - 6; x < xCoord + 7; x++) {
			for (int z = zCoord - 6; z < zCoord + 7; z++) {
				for (int y = yCoord; y < yCoord + 10; y++) {
					if(rand.nextInt(1) == 0) {
						if(isNaturalGas(x, y, z)) {
							if(fill(ForgeDirection.UP, FluidRegistry.getFluidStack(FluidDictionary.natural_gas, 1000), false) >= 1000) {
								fill(ForgeDirection.UP, FluidRegistry.getFluidStack(FluidDictionary.natural_gas, 1000), true);
								worldObj.setBlockToAir(x, y, z);
							}
						}
					}
				}
			}
		}
	}
	
	private boolean isNaturalGas(int x, int y, int z) {
		return worldObj.getBlockId(x, y, z) == Core.airBlocks.blockID && worldObj.getBlockMetadata(x, y, z) == AirMeta.NATURAL_GAS;
	}
	
	private int tick;

	@Override
	public void updateEntity() {
		if(helper == null)
			helper = new BlockTransferHelper(this);
		
		tick++;
		if(tick %100 == 0) {
			doPoweredPump();
		}
		
		//Transfer internals to a nearby tank
		if(tick %20 == 0) {
			helper.ejectFluid(new int[] { 1000, 100, 20, 1 });
		}

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

			worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
		}

		if(Modules.diving.isActive()){
			if (worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && Extra.REDSTONE_PUMP) {
				supplyWithAir(30, 25.0D, 36.0D, 25.0D);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		storage.readFromNBT(nbt);
		on = nbt.getBoolean("DisplayOn");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		storage.writeToNBT(nbt);
		nbt.setBoolean("DisplayOn", on);
	}

	public boolean on = true;
	
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

					if (worldObj.isAirBlock(x2, y2, z2) && type.equals(Type.CHECK)) {
						total++;
					}
					
					if(!type.equals(Type.CHECK)) {
						if(worldObj.getBlockId(x2, y2, z2) == Core.airBlocks.blockID && worldObj.getBlockMetadata(x2, y2, z2) == AirMeta.DEMO && (on || type.equals(Type.CLEAR))) {
							worldObj.setBlockToAir(x2, y2, z2);
						} else if(type.equals(Type.DISPLAY) && worldObj.isAirBlock(x2, y2, z2) && !(worldObj.getBlockId(x2, y2, z2) == Core.airBlocks.blockID && worldObj.getBlockMetadata(x2, y2, z2) == AirMeta.NATURAL_GAS) && !on) {
							worldObj.setBlock(x2, y2, z2, Core.airBlocks.blockID, AirMeta.DEMO, 2);
						}
					}
				}
			}
		}
			
		if(type.equals(Type.CHECK)) {
			return (total >= 52) ? true : false;
		}
		
		return true;
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
