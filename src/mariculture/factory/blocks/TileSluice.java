package mariculture.factory.blocks;

import java.util.Random;

import mariculture.api.core.IBlacklisted;
import mariculture.core.Core;
import mariculture.core.blocks.base.TileTank;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.FluidTransferHelper;
import mariculture.core.util.Rand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidHandler;

public class TileSluice extends TileTank implements IBlacklisted {
	private int rate[] = new int[] { 1000, 500, 250, 100, 25, 1 }; 

	protected int machineTick;
	private short facing;
	private int height;
	
	@Override
	public int getTankSize() {
		return FluidContainerRegistry.BUCKET_VOLUME * 8;
	}
	
	public boolean onTick(int i) {
		return machineTick % i == 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		this.facing = tagCompound.getShort("Facing");
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		tagCompound.setShort("Facing", this.facing);
	}

	private void moveFluids() {
		int x = this.xCoord;
		int y = this.yCoord;
		int z = this.zCoord;
		int x2 = x;
		int y2 = y;
		int z2 = z;
		ForgeDirection direction = ForgeDirection.UNKNOWN;

		switch (facing) {
		case 0:
			z++;
			z2--;
			direction = ForgeDirection.SOUTH;
			break;
		case 1:
			z--;
			z2++;
			direction = ForgeDirection.NORTH;
			break;
		case 2:
			x++;
			x2--;
			direction = ForgeDirection.EAST;
			break;
		case 3:
			x--;
			x2++;
			direction = ForgeDirection.WEST;
			break;
		}

		if (this.worldObj.getBlockTileEntity(x, y, z) != null
				&& this.worldObj.getBlockTileEntity(x, y, z) instanceof IFluidHandler) {
			pullFromTank(direction, this.worldObj, (IFluidHandler) this.worldObj.getBlockTileEntity(x, y, z), x2, y2,
					z2);
		}

		if (this.worldObj.getBlockTileEntity(x2, y2, z2) != null
				&& this.worldObj.getBlockTileEntity(x2, y2, z2) instanceof IFluidHandler) {
			if(!pushToTank(direction, this.worldObj, (IFluidHandler) this.worldObj.getBlockTileEntity(x2, y2, z2), x, y, z)) {
				if(height < 2) {
					moveForward(this.worldObj, x2, y2, z2, x, y, z);
				}
			}
		} else if(height < 2) {
			moveForward(this.worldObj, x2, y2, z2, x, y, z);
		}
	}

	private void moveForward(World world, int x2, int y2, int z2, int x, int y, int z) {
		if(world.isAirBlock(x2, y2, z2)) {
			int id = world.getBlockId(x, y, z);
			if(Block.blocksList[id] != null) {
				if(Block.blocksList[id] instanceof IFluidBlock || Block.blocksList[id] instanceof BlockFluid) {
					if (Block.blocksList[id] instanceof BlockFluidClassic) {
						BlockFluidClassic block = (BlockFluidClassic) Block.blocksList[id];
						if(block.drain(world, x, y, z, false) == null) {
							return;
						}
					}
					
					if(Block.blocksList[id] instanceof BlockFluid) {
						if(world.getBlockMetadata(x, y, z) != 0) {
							return;
						}
					}
					
					world.setBlock(x2, y2, z2, world.getBlockId(x, y, z), world.getBlockMetadata(x, y, z), 2);
					world.setBlockToAir(x, y, z);
				}
			}
		}
	}

	private void pullFromTank(ForgeDirection direction, World world, IFluidHandler tank, int x, int y, int z) {
		FluidTankInfo[] info = tank.getTankInfo(direction);
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i] != null) {
					if (info[i].fluid != null) {
						Fluid fluid = FluidRegistry.getFluid(info[i].fluid.fluidID);
						if(fluid == null) {
							return;
						}
						
						if (fluid.getBlockID() == Core.highPressureWaterBlock.blockID) {
							return;
						}

						if (fluid.canBePlacedInWorld()) {
							int drain = FluidHelper.getRequiredVolumeForBlock(fluid);
							if (tank.drain(ForgeDirection.UNKNOWN, drain, false) == null) {
								return;
							}
							if (tank.drain(ForgeDirection.UNKNOWN, drain, false).amount == drain) {
								int id = fluid.getBlockID();
								if (Block.blocksList[id] != null) {
									Block block = Block.blocksList[id];
									if (block instanceof BlockFluidFinite) {
										if (world.isAirBlock(x, y, z)) {
											world.setBlock(x, y, z, id, 0, 2);
										} else {
											int meta = world.getBlockMetadata(x, y, z) + 1;
											if (meta < 7 && world.getBlockId(x, y, z) == id) {
												world.setBlockMetadataWithNotify(x, y, z, meta, 2);
											} else {
												return;
											}
										}

										tank.drain(ForgeDirection.UNKNOWN, new FluidStack(fluid.getID(), drain), true);
									} else if (world.isAirBlock(x, y, z)) {
										world.setBlock(x, y, z, id);
										tank.drain(ForgeDirection.UNKNOWN, new FluidStack(fluid.getID(), drain), true);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private boolean pushToTank(ForgeDirection direction, World world, IFluidHandler tank, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		if (Block.blocksList[id] != null && (Block.blocksList[id] instanceof IFluidBlock || Block.blocksList[id] instanceof BlockFluid)) {
			Fluid fluid = null;
			IFluidBlock block = null;

			if (id > 20) {
				block = (IFluidBlock) Block.blocksList[id];
				fluid = block.getFluid();
			} else {
				if (id == Block.waterStill.blockID) {
					fluid = FluidRegistry.WATER;
				} else {
					fluid = FluidRegistry.LAVA;
				}
			}
			
			if(Block.blocksList[id] instanceof BlockFluid) {
				if(world.getBlockMetadata(x, y, z) != 0) {
					return false;
				}
			}
			
			if(block != null) {
				if(block instanceof BlockFluidClassic) {
					FluidStack fill = block.drain(world, x, y, z, false);
					if(fill != null) {
						int id2 = fluid.getID();
						if(id2 == Core.highPressureWater.getID()) {
							id2 = FluidRegistry.WATER.getID();
						}
						
						if (tank.fill(ForgeDirection.UNKNOWN, new FluidStack(id2, fill.amount), false) >= fill.amount) {
							tank.fill(ForgeDirection.UNKNOWN, new FluidStack(id2, fill.amount), true);
							block.drain(world, x, y, z, true);
							return true;
						}
					}
					
					return false;
				}
			}

			int fill = FluidHelper.getRequiredVolumeForBlock(fluid);
			if (tank.fill(ForgeDirection.UNKNOWN, new FluidStack(fluid.getID(), fill), false) == fill) {
				tank.fill(ForgeDirection.UNKNOWN, new FluidStack(fluid.getID(), fill), true);
				if (block != null && block instanceof BlockFluidFinite) {
					int meta = world.getBlockMetadata(x, y, z) - 1;
					if (meta >= 0) {
						world.setBlockMetadataWithNotify(x, y, z, meta, 2);
					}
				}

				world.setBlockToAir(x, y, z);
				return true;
			}
		}
		
		return false;
	}

	private boolean waterIsThere() {
		boolean result;
		int x = (facing == 2) ? this.xCoord + 1 : (facing == 3) ? this.xCoord - 1 : this.xCoord;
		int z = (facing == 0) ? this.zCoord + 1 : (facing == 1) ? this.zCoord - 1 : this.zCoord;

		int x2 = (facing == 2) ? this.xCoord + 1 : (facing == 3) ? this.xCoord - 1 : this.xCoord;
		int z2 = (facing == 0) ? this.zCoord + 1 : (facing == 1) ? this.zCoord - 1 : this.zCoord;
		
		return (BlockHelper.isWater(worldObj, x, yCoord, z) && BlockHelper.isWater(worldObj, x2, yCoord, z2));
	}

	public int getHeight() {
		int x = (facing == 2) ? this.xCoord + 1 : (facing == 3) ? this.xCoord - 1 : this.xCoord;
		int y = this.yCoord;
		int z = (facing == 0) ? this.zCoord + 1 : (facing == 1) ? this.zCoord - 1 : this.zCoord;
		int topY = y;
		for (topY = y; worldObj.getBlockMaterial(x, topY, z) == Material.water; topY++) {
			;
		}

		return topY - y;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		machineTick++;
		if (!this.worldObj.isRemote) {
			if (onTick(32))
				updatePlacedBlock();
			if (!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) {
				if(onTick(16))
					transfer();
				
				if (onTick(32)) {
					height = getHeight();
					moveFluids();
				}

				if (onTick(64)) {
					if (waterIsThere()) {
						fillSluice();
					}
				}
			}
		}
	}

	private void transfer() {
		if(facing != 3)
			transfer.transfer(ForgeDirection.WEST, rate);
		if(facing != 2)
			transfer.transfer(ForgeDirection.WEST, rate);
		if(facing != 1)
			transfer.transfer(ForgeDirection.NORTH, rate);
		if(facing != 0)
			transfer.transfer(ForgeDirection.SOUTH, rate);
	}

	private void fillSluice() {
		float fill = 1F;
		for (int i = 0; i < height; i++) {
			fill += (0.0845F * i);
		}

		if (fill(ForgeDirection.UNKNOWN, new FluidStack(Core.highPressureWater.getID(), (int) fill), false) >= (int) fill) {
			fill(ForgeDirection.UNKNOWN, new FluidStack(Core.highPressureWater.getID(), (int) fill), true);
		} else if (fill(ForgeDirection.UNKNOWN, new FluidStack(Core.highPressureWater.getID(), 1), false) >= 1) {
			fill(ForgeDirection.UNKNOWN, new FluidStack(Core.highPressureWater.getID(), 1), true);
		}
	}

	private void updatePlacedBlock() {
		int x = (facing == 2) ? this.xCoord - 1 : (facing == 3) ? this.xCoord + 1 : this.xCoord;
		int y = this.yCoord;
		int z = (facing == 0) ? this.zCoord - 1 : (facing == 1) ? this.zCoord + 1 : this.zCoord;
		if (this.worldObj.getBlockId(x, y, z) == Core.highPressureWaterBlock.blockID) {
			if (height <= 1 || worldObj.isBlockIndirectlyGettingPowered(x, y, z)) {
				worldObj.setBlockToAir(x, y, z);
			} else if (height < 10) {
				worldObj.setBlock(x, y, z, Block.waterStill.blockID);
			}
		} else if (worldObj.isAirBlock(x, y, z) && waterIsThere()) {
			if (height >= 10) {
				worldObj.setBlock(x, y, z, Core.highPressureWaterBlock.blockID);
			} else {
				worldObj.setBlock(x, y, z, Block.waterStill.blockID);
			}
		}
	}

	private int getTopWaterBlock(int x, int y, int z) {
		int i = 0;
		while (isWater(this.worldObj.getBlockId(x, y + i, z))) {
			if (this.worldObj.getBlockId(x, y + i, z) == Core.highPressureWaterBlock.blockID) {
				i++;
			}
		}

		return ((y + i) - 1);
	}

	public int getPressure() {
		int x = this.xCoord;
		final int y = this.yCoord;
		int z = this.zCoord;

		switch (facing) {
		case 0:
			z++;
			break;
		case 1:
			z--;
			break;
		case 2:
			x++;
			break;
		case 3:
			x--;
			break;
		}

		return ((getTopWaterBlock(x, y, z) - y));
	}

	private boolean isWater(int blockId) {
		return (blockId == Block.waterStill.blockID) ? true : false;
	}

	public int getFacing() {
		return this.facing;
	}

	public void setFacing(int i) {
		this.facing = (short) i;
	}

	@Override
	public boolean isBlacklisted(World world, int x, int y, int z) {
		return true;
	}
}
