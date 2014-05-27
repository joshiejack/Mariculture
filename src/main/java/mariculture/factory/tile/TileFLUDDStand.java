package mariculture.factory.tile;

import java.util.List;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.Core;
import mariculture.core.gui.feature.FeatureEject.EjectSetting;
import mariculture.core.gui.feature.FeatureNotifications.NotificationType;
import mariculture.core.gui.feature.FeatureRedstone.RedstoneMode;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.cofh.InventoryHelper;
import mariculture.core.lib.MaricultureDamage;
import mariculture.core.network.Packets;
import mariculture.core.tile.base.TileMachineTank;
import mariculture.core.util.Fluids;
import mariculture.core.util.IFaceable;
import mariculture.core.util.IHasNotification;
import mariculture.core.util.Rand;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLogic;
import net.minecraft.block.material.MaterialTransparent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileFLUDDStand extends TileMachineTank implements IHasNotification, IFaceable {
	
	public ForgeDirection orientation = ForgeDirection.UP;
	public static final int input = 3;
	public static final int output = 4;
	
	public TileFLUDDStand() {
		mode = RedstoneMode.HIGH;
	}
	
	@Override
	public int getTankCapacity(int count) {
		return ((FluidContainerRegistry.BUCKET_VOLUME * 20) + (count * (FluidContainerRegistry.BUCKET_VOLUME * 4)));
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { input, output };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return slot == input && FluidHelper.isFluidOrEmpty(stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return slot == 4;
	}

	@Override
	public void updateMachine() {
		if (canWork) {
			doSquirt();
				
			if(onTick(100)) {
				drain(ForgeDirection.UP, new FluidStack(tank.getFluidID(), getWaterUsage()), true);
				worldObj.func_147479_m(xCoord, yCoord, zCoord);
			}
		}
	}
	
	private void doSquirt() {
		float hardnessMax = (-heat) * 2;
		boolean reverse = hasEthereal();
		double boostXZ = getBoostXZ();
		double boostY = getBoostY();
		double x, y, z;
		for(int dist = 0; dist < getDistance(); dist ++) {
			x = xCoord + (orientation.offsetX * dist);
			y = yCoord + (orientation.offsetY * dist);
			z = zCoord + (orientation.offsetZ * dist);
			
			if(dist > 0) {
				Material mat = worldObj.getBlock((int)x, (int)y, (int)z).getMaterial();
				if(!(mat instanceof MaterialLogic || mat instanceof MaterialTransparent || isNet((int)x, (int)y , (int)z)))
					return;
			}
				
			List list = this.worldObj.getEntitiesWithinAABB(Entity.class, 
					Blocks.stone.getCollisionBoundingBoxFromPool(this.worldObj, (int)x, (int)y, (int)z));
			
			//Entity Stuff
			for(Object i: list) {
				Entity entity = (Entity) i;
				entity.fallDistance = 0F;
				if(dist == 1 && !worldObj.isRemote && onTick(20) && reverse && entity instanceof EntityItem) {
					EntityItem item = (EntityItem) entity;
					ItemStack stack = item.getEntityItem();
					TileEntity tile = worldObj.getTileEntity(xCoord - orientation.offsetX, yCoord - orientation.offsetY, zCoord - orientation.offsetZ);
					if(tile != null && tile instanceof IInventory) {
						ItemStack newStack = InventoryHelper.insertItemStackIntoInventory((IInventory)tile, stack, orientation.getOpposite().ordinal());
						if(newStack == null)
							item.setDead();
						else
							item.setEntityItemStack(newStack);
					}
				}
				
				if(orientation != ForgeDirection.UP) {
					if(reverse)
						entity.addVelocity(-orientation.offsetX * boostXZ, -orientation.offsetY * boostXZ, -orientation.offsetZ * boostXZ);
					else
						entity.addVelocity(orientation.offsetX * boostXZ, orientation.offsetY * boostXZ, orientation.offsetZ * boostXZ);
				} else {
					if(entity.motionY <= boostY - 0.1) {
						entity.motionY+=boostY;
						if(entity instanceof EntityItem) {
							entity.motionY+=0.015;
							entity.motionX = 0;
							entity.motionZ = 0;
						}
					}
				}
				
				if(heat > 0 && entity instanceof EntityLivingBase) {
					entity.attackEntityFrom(MaricultureDamage.scald, heat/2);
				}
			}
			
			//Block Stuff
			if (heat < 0) {
				int x2 = (int) (x + orientation.offsetX);
				int y2 = (int) (y + orientation.offsetY);
				int z2 = (int) (z + orientation.offsetZ);
				Block block = worldObj.getBlock(x2, y2, z2);
				if(block != null) {
					float blockHardness = block.getBlockHardness(worldObj, x2, y2, z2);
					if(worldObj.isRemote) {
						block.addHitEffects(worldObj, 
								new MovingObjectPosition(x2, y2, z2, orientation.getOpposite().ordinal(), 
										worldObj.getWorldVec3Pool().getVecFromPool(x2, y2, z2)), Minecraft.getMinecraft().effectRenderer);
					}
					int chance = (int) (((blockHardness * 10) > 0)? (blockHardness * 10): 10);
					if(Rand.nextInt(chance)) {
						if(blockHardness <= hardnessMax) {
							int meta = worldObj.getBlockMetadata(x2, y2, z2);
							if(worldObj.isRemote)
								block.addDestroyEffects(worldObj, x2, y2, z2, meta, Minecraft.getMinecraft().effectRenderer);
							BlockHelper.destroyBlock(worldObj, x2, y2, z2);
						}
					}
				}
			}
			
			if(worldObj.isRemote) {
				if(onTick(4)) {
					for(float i = dist > 0? -0.45F: 0.3F; i <= 0.35F; i+=0.05F) {
						worldObj.spawnParticle("cloud", x + 0.5F + (i * orientation.offsetX), 
								y + 0.7F + (i * orientation.offsetY), z + 0.5F + (i * orientation.offsetZ), 0, 0, 0);
					}
				}
			}
		}
	}
	
	@Override
	public boolean canMachineWork() {
		return tank.getFluidAmount() > 0 && tank.getFluidID() == FluidRegistry.getFluidID(Fluids.hp_water) && RedstoneMode.canWork(this, mode);
	}
	
	private boolean isNet(int x, int y, int z) {
		return worldObj.getBlock(x, y, z) == Core.ticking;
	}
	
	private boolean hasEthereal() {
		return MaricultureHandlers.upgrades.hasUpgrade("ethereal", this);
	}
	
	private int getWaterUsage() {
		return (getDistance() / 8) + ((heat >= 0)? heat: -heat) + (hasEthereal() ? 10: 0) + speed;
	}
	
	private int getDistance() {
		return (purity + 1) * 8;
	}
	
	private double getBoostXZ() {
		return speed * 0.025;
	}
	
	private double getBoostY() {
		double boost = (speed <= 4)? 0.15: (speed <= 8)? 0.1: (speed <= 12)? 0.05: 0.025;
		return speed * boost;
	}

	@Override
	public EjectSetting getEjectType() {
		return EjectSetting.FLUID;
	}

	@Override
	public boolean isNotificationVisible(NotificationType type) {
		return false;
	}
	
	@Override
	public boolean rotate() {
		setFacing(BlockHelper.rotate(orientation));
		return true;
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
		orientation = ForgeDirection.values()[nbt.getInteger("Orientation")];
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Orientation", orientation.ordinal());
	}
}
