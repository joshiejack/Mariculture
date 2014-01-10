package mariculture.factory.blocks;

import java.util.List;

import mariculture.core.Core;
import mariculture.core.blocks.BlockOyster;
import mariculture.core.blocks.base.TileTank;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.helpers.cofh.InventoryHelper;
import mariculture.core.lib.Extra;
import mariculture.core.network.Packets;
import mariculture.core.util.FluidDictionary;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLogic;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileGeyser extends TileTank {
	public ForgeDirection orientation = ForgeDirection.UP;
	private int machineTick;
	private boolean canWork;
	private int size;

	public TileGeyser() {
		tank = new Tank(8000);
	}

	public boolean onTick(int i) {
		return machineTick % i == 0;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	public boolean canWork() {
		return tank.getFluidAmount() > 0 && canUseFluid(tank.getFluidID()) && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}

	private boolean canUseFluid(int id) {
		if (id == FluidRegistry.getFluidID("water")) {
			size = 8;
			return true;
		} else if (id == FluidRegistry.getFluidID(FluidDictionary.hp_water)) {
			size = 16;
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isNet(int x, int y, int z) {
		return worldObj.getBlockId(x, y, z) == Core.oysterBlock.blockID && worldObj.getBlockMetadata(x, y, z) == BlockOyster.NET;
	}

	@Override
	public void updateEntity() {
		machineTick++;
		
		if(onTick(Extra.CAN_WORK_TICK)) {
			canWork = canWork();
		}
		
		if (canWork) {
			doSquirt();
				
			if(onTick(100)) {
				drain(ForgeDirection.UP, new FluidStack(tank.getFluidID(), 1), true);
				Packets.updateTile(this, 32, getDescriptionPacket());
				pullFromInventory();
			}
		}
	}

	private void doSquirt() {
		double x, y, z;
		for(int dist = 0; dist < size; dist ++) {
			x = xCoord + (orientation.offsetX * dist);
			y = yCoord + (orientation.offsetY * dist);
			z = zCoord + (orientation.offsetZ * dist);
			
			if(dist > 0) {
				Material mat = worldObj.getBlockMaterial((int)x, (int)y, (int)z);
				if(!(mat instanceof MaterialLogic || mat instanceof MaterialTransparent || isNet((int)x, (int)y , (int)z)))
					return;
			}
				
			List list = this.worldObj.getEntitiesWithinAABB(Entity.class, 
					Block.stone.getCollisionBoundingBoxFromPool(this.worldObj, (int)x, (int)y, (int)z));
			
			for(Object i: list) {
				Entity entity = (Entity) i;
				if(orientation != ForgeDirection.UP) {
					entity.addVelocity(orientation.offsetX * 0.1, orientation.offsetY * 0.1, orientation.offsetZ * 0.1);
				} else {
					if(entity.motionY <= 0.24) {
						entity.motionY+=0.25;
						if(entity instanceof EntityItem) {
							entity.motionY+=0.015;
							entity.motionX = 0;
							entity.motionZ = 0;
						}
					}
				}
			}
			
			if(Extra.GEYSER_ANIM && worldObj.isRemote) {
				if(onTick(4)) {
					for(float i = dist > 0? -0.45F: -0.1F; i <= 0.35F; i+=0.05F) {
						worldObj.spawnParticle("cloud", x + 0.5F + (i * orientation.offsetX), 
								y + 0.5F + (i * orientation.offsetY), z + 0.5F + (i * orientation.offsetZ), 0, 0, 0);
					}
				}
			}
		}
	}
	
	public void pullFromInventory() {
		TileEntity tile = worldObj.getBlockTileEntity(xCoord -orientation.offsetX, yCoord -orientation.offsetY, zCoord - orientation.offsetZ);
		if(tile != null && tile instanceof IInventory) {
			ItemStack stack = InventoryHelper.extractItemStackFromInventory((IInventory) tile, orientation.getOpposite().ordinal());
			if(stack != null) {
				SpawnItemHelper.spawnItem(worldObj, xCoord, yCoord, zCoord, stack, false);
			}
		}
	}
	
	@Override
	public Packet getDescriptionPacket() {		
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new Packet132TileEntityData(xCoord, yCoord, zCoord, 2, nbt);
	}

	@Override
	public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet) {
		readFromNBT(packet.data);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
		canWork = nbt.getBoolean("CanWork");
		size = nbt.getInteger("Size");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Orientation", orientation.ordinal());
		nbt.setBoolean("CanWork", canWork);
		nbt.setInteger("Size", size);
	}
}
