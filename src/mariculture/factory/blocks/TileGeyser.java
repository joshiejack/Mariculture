package mariculture.factory.blocks;

import java.util.List;
import java.util.Random;

import mariculture.core.Mariculture;
import mariculture.core.blocks.base.TileStorageTank;
import mariculture.core.helpers.InventoHelper;
import mariculture.core.network.Packets;
import mariculture.core.util.FluidDictionary;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileGeyser extends TileStorageTank {
	public ForgeDirection orientation = ForgeDirection.UP;
	protected int tick;

	public TileGeyser() {
		this.inventory = new ItemStack[1];
		this.tank = new Tank(8000);
	}

	private void doSquirt(World world, int distance, ForgeDirection direction, int baseX, int baseY, int baseZ, int tick) {
		double x = baseX + direction.offsetX;
		double y = baseY + direction.offsetY;
		double z = baseZ + direction.offsetZ;
		
		for(int dist = 0; dist < distance; dist ++) {
			List list = this.worldObj.getEntitiesWithinAABB(Entity.class, Block.stone.getCollisionBoundingBoxFromPool(this.worldObj, 
					(int)xCoord + (direction.offsetX * dist), 
					(int)yCoord + (direction.offsetY * dist), 
					(int)zCoord + (direction.offsetZ * dist)));
			
			for(Object i: list) {
				Entity entity = (Entity) i;
				if(orientation != ForgeDirection.UP) {
					entity.addVelocity(this.orientation.offsetX * 0.15, this.orientation.offsetY * 0.15, this.orientation.offsetZ * 0.15);
				} else {
					entity.motionY+=0.045;
					if(entity instanceof EntityItem) {
						entity.motionX = 0;
						entity.motionZ = 0;
					}
				}
			}
		}
		
		/**
		float zPlus = 0F;
		float angleOfDecent = 1F / distance;

		if (direction == ForgeDirection.UP || direction == ForgeDirection.DOWN) {
			zPlus = 0.3F;
		}
		for (int count = 0; count < distance; count++) {
			List list = this.worldObj.getEntitiesWithinAABB(
					Entity.class,
					this.getBlockType().getCollisionBoundingBoxFromPool(this.worldObj,
							(int) (x + (direction.offsetX * count)), (int) (y + (direction.offsetY * count)),
							(int) (z + (direction.offsetZ * count))));

			if (count < distance - 1) {
				for (Object i : list) {
					((Entity) i).addVelocity(this.orientation.offsetX * 0.15, this.orientation.offsetY * 0.042,
							this.orientation.offsetZ * 0.15);

					if (((Entity) i) instanceof EntityItem) {
						EntityItem item = (EntityItem) ((Entity) i);
						item.motionX = this.orientation.offsetX * 0.15;
						item.motionZ = this.orientation.offsetZ * 0.15;
						item.motionY = this.orientation.offsetY * 0.042;
						item.moveEntity(item.motionX, item.motionY, item.motionZ);
					}
					
					if (direction == ForgeDirection.UP) {
						((Entity) i).fallDistance = 0F;
					}
				}
			}

			if (!this.worldObj.isRemote && count == 0) {
				for (Object i : list) {
					if (((Entity) i) instanceof EntityItem) {
						EntityItem item = (EntityItem) ((Entity) i);
						int itemX = (int) Math.ceil(item.posX);
						if ((this.xCoord + this.orientation.offsetX) + 1 == (int) Math.ceil(item.posX)) {
							if ((this.zCoord + this.orientation.offsetZ) + 1 == (int) Math.ceil(item.posZ)) {
								if ((this.yCoord + this.orientation.offsetY) + 1 == (int) Math.ceil(item.posY)) {
									ItemStack stack = item.getEntityItem();
									if (InventoryHelper.addToInventory(0, this.worldObj, this.xCoord, this.yCoord,
											this.zCoord, stack, null)) {
										item.setDead();
									}
								}

							}
						}
					}
				}
			}

			for (float half = -0.5F; half < 0.5F; half = half + 0.25F) {
				if(tick == 0 && half % 0.5 == 0)
					this.worldObj.spawnParticle("cloud", xCoord + 0.5F, yCoord + 0.5F, zCoord + 0.5F, 0, 0, 0);
				
				if (this.worldObj.isAirBlock((int) (x + (direction.offsetX * count)),
						(int) (y + (direction.offsetY * count)), (int) (z + (direction.offsetZ * count)))) {
					if (tick == 0) {
						this.worldObj.spawnParticle("cloud", x + (direction.offsetX * count) + 0.5F
								+ (half * direction.offsetX), y + (direction.offsetY * count) + 0.5F
								- (count * angleOfDecent) + (half * direction.offsetY), z + (direction.offsetZ * count)
								+ 0.5F + (half * direction.offsetZ), 0, 0, 0);
					}

					if (!this.worldObj.isAirBlock((int) (x + (direction.offsetX * (count + 1))),
							(int) (y + (direction.offsetY * (count + 1))),
							(int) (z + (direction.offsetZ * (count + 1))))) {
						this.worldObj.spawnParticle("splash", x + (direction.offsetX * count) + 0.5F, y
								+ (direction.offsetY * count) + 0.5F - (count * 0.1F), z + (direction.offsetZ * count)
								+ 0.5F + zPlus, 0, 0, 0);
					}
				} else {
					count = distance;
				}
			}
		} **/
	}

	public boolean onTick(int i) {
		return tick % i == 0;
	}
	
	public boolean canActivate() {
		return tank.getFluidAmount() > 4 && 
				(tank.getFluidID() == FluidRegistry.getFluidID("water") || 
				tank.getFluidID() == FluidRegistry.getFluidID(FluidDictionary.hp_water));
	}

	@Override
	public void updateEntity() {
		tick++;
		if (this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord) && canActivate()) {
			doSquirt(this.worldObj, 8, orientation, this.xCoord, this.yCoord, this.zCoord, tick %4);
			if (onTick(20)) {
				this.drain(ForgeDirection.UP, new FluidStack(tank.getFluidID(), 5), true);
				Packets.updateTile(this, 32, getDescriptionPacket());
			}
			
			if(onTick(100)) {
				pullFromInventory();
			}
		}
	}
	
	public void pullFromInventory() {
		IInventory invent = InventoHelper.getNextInventory(0, worldObj, xCoord, yCoord, zCoord, null);
		int side = InventoHelper.getSide(0, worldObj, xCoord, yCoord, zCoord, null);
		if(invent != null)
			pullFromInventory(invent, InventoHelper.getSlot(invent, null, side), side);
	}
	
	private boolean canExtractItemFromInventory(IInventory inventory, ItemStack stack, int slot, int side) {
        return !(inventory instanceof ISidedInventory) || ((ISidedInventory)inventory).canExtractItem(slot, stack, side);
    }
	
	private boolean pullFromInventory(IInventory inventory, int slot, int side) {
		if(slot < 0 || slot > inventory.getSizeInventory())
			return false;
		
        ItemStack stack = inventory.getStackInSlot(slot);
        System.out.println("stack");

        if (stack != null && canExtractItemFromInventory(inventory, stack, slot, side)) {
            ItemStack itemstack1 = stack.copy();
            InventoHelper.spawnItem(worldObj, xCoord, yCoord, zCoord, itemstack1, false);
            worldObj.spawnParticle("cloud", xCoord, yCoord, zCoord, 0, 0, 0);
            inventory.setInventorySlotContents(slot, null);
            System.out.println("debug");
            return true;
        }

        return false;
    }
	
	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return from == ForgeDirection.DOWN;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.orientation = ForgeDirection.getOrientation(tagCompound.getInteger("Orientation"));
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setInteger("Orientation", orientation.ordinal());
	}

	@Override
	public Packet getDescriptionPacket() {		
		NBTTagCompound tagCompound = new NBTTagCompound();
		this.writeToNBT(tagCompound);
		return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 2, tagCompound);
	}

	@Override
	public void onDataPacket(INetworkManager netManager, Packet132TileEntityData packet) {
		this.readFromNBT(packet.data);
	}
}
