package mariculture.core.blocks;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.List;
import java.util.Random;

import mariculture.core.Core;
import mariculture.core.helpers.FluidInventoryHelper;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.AirMeta;
import mariculture.core.lib.ArmorSlot;
import mariculture.core.lib.Extra;
import mariculture.core.lib.Modules;
import mariculture.core.lib.PacketIds;
import mariculture.diving.Diving;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;
import buildcraft.api.power.PowerHandler.Type;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.TileEnergyHandler;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileAirPump extends TileEnergyHandler implements IPowerReceptor {
	public boolean isPumping = false;
	public boolean animate;
	private double wheelAngle1 = 0;
	private double wheelAngle2 = 0;
	private Random rand = new Random();
	private PowerHandler powerHandler;

	public TileAirPump() {
		storage = new EnergyStorage(100);
		powerHandler = new PowerHandler(this, Type.MACHINE);
		powerHandler.configure(2, 10, 10, 10);
		powerHandler.configurePowerPerdition(0, 0);
	}

	public double getWheelAngle(int which) {
		if (which == 1) {
			return wheelAngle1;
		}

		return wheelAngle2;
	}

	private void sendAnimatePacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream os = new DataOutputStream(bos);
		try {
			os.writeInt(PacketIds.AIR_PUMP_UPDATE);
			os.writeInt(this.xCoord);
			os.writeInt(this.yCoord);
			os.writeInt(this.zCoord);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "Mariculture";
		packet.data = bos.toByteArray();
		packet.length = bos.size();

		PacketDispatcher.sendPacketToAllAround(this.xCoord, this.yCoord, this.zCoord, 30, this.worldObj.provider.dimensionId, packet);
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
	
	public void addEnergy() {
		if(!worldObj.isRemote) {
			if(powerHandler.getEnergyStored() > 0) {
				storage.receiveEnergy((int) (powerHandler.getEnergyStored() * 10), false);
				powerHandler.setEnergy(0F);
			}
		}
	}

	public void doPoweredPump() {
		if (Extra.BUILDCRAFT_PUMP) {
			this.addEnergy();
			
			if (storage.extractEnergy(100, true) < 100) {
				isPumping = false;
				return;
			}
			
			storage.extractEnergy(100, false);

			isPumping = true;
			animate = true;

			this.sendAnimatePacket();
			if(Modules.diving.isActive()) {
				if(isValidLocationToActivate(this.worldObj, this.xCoord, this.yCoord, this.zCoord)) {
					supplyWithAir(300, 35.0D, 40.0D, 35.0D);
				}
			}
			
			suckUpGas(4096);
		}
	}

	public void suckUpGas(int chance) {
		for(int x = xCoord - 6; x < xCoord + 7; x++) {
			for (int z = zCoord - 6; z < zCoord + 7; z++) {
				for (int y = yCoord; y < yCoord + 10; y++) {
					if(rand.nextInt(chance) == 0) {
						if(isNaturalGas(x, y, z) && attemptTransfer(xCoord + 1, yCoord, zCoord)) {
							worldObj.setBlockToAir(x, y, z);
						}
						
						if(isNaturalGas(x, y, z) && attemptTransfer(xCoord - 1, yCoord, zCoord)) {
							worldObj.setBlockToAir(x, y, z);
						}
						
						if(isNaturalGas(x, y, z) && attemptTransfer(xCoord, yCoord, zCoord + 1)) {
							worldObj.setBlockToAir(x, y, z);
						}
						
						if(isNaturalGas(x, y, z) && attemptTransfer(xCoord, yCoord, zCoord - 1)) {
							worldObj.setBlockToAir(x, y, z);
						}
					}
				}
			}
		}
	}
	
	private boolean isNaturalGas(int x, int y, int z) {
		return worldObj.getBlockId(x, y, z) == Core.airBlocks.blockID && worldObj.getBlockMetadata(x, y, z) == AirMeta.NATURAL_GAS;
	}

	private boolean attemptTransfer(int x, int y, int z) {	
		if(FluidInventoryHelper.addTo(worldObj, x, y, z, new FluidStack(Core.naturalGas, 1000), false)) {
			FluidInventoryHelper.addTo(worldObj, x, y, z, new FluidStack(Core.naturalGas, 1000), true);
			return true;
		}
		
		return false;
	}
	
	private int tick;

	@Override
	public void updateEntity() {

		tick++;
		if(tick %100 == 0) {
			doPoweredPump();
		}

		if (animate) {
			this.wheelAngle1 = this.wheelAngle1 + 0.1;
			this.wheelAngle2 = this.wheelAngle2 + 0.1;

			if (this.wheelAngle1 > 6.2198) {
				this.wheelAngle1 = 0;
				animate = false;
			}

			if (this.wheelAngle2 > 6.2198) {
				this.wheelAngle2 = 0;
				animate = false;
			}

			this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
		}

		if(Modules.diving.isActive()){
			if (worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord) && Extra.REDSTONE_PUMP) {
				supplyWithAir(30, 20.0D, 25.0D, 20.0D);
			}
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

	public boolean isValidLocationToActivate(World world, int x, int y, int z) {
		int total = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				for (int l = 0; l < 3; l++) {
					if (world.isAirBlock(x + i, y + l, z + j)) {
						total++;
					}
				}
			}
		}
	
		return (total == 26) ? true : false;
	}
	
	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection side) {
		return powerHandler.getPowerReceiver();
	}

	@Override
	public World getWorld() {
		return this.worldObj;
	}

	@Override
	public void doWork(PowerHandler workProvider) {
		return;
	}
}
