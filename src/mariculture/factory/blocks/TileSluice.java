package mariculture.factory.blocks;

import mariculture.api.core.IBlacklisted;
import mariculture.core.Core;
import mariculture.core.blocks.base.TileTank;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.BlockTransferHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.util.FluidDictionary;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileSluice extends TileTank implements IBlacklisted {
	protected BlockTransferHelper helper;
	public ForgeDirection direction;
	protected int machineTick;
	private int height = 0;
	
	public TileSluice() {
		tank = new Tank(10000);
		direction = ForgeDirection.UP;
	}
	
	@Override
	public boolean isBlacklisted(World world, int x, int y, int z) {
		return true;
	}
	
	@Override
	public boolean canUpdate() {
		return true;
	}
	
	public boolean onTick(int i) {
		return machineTick % i == 0;
	}
	
	@Override
	public void updateEntity() {
		if(helper == null)
			helper = new BlockTransferHelper(this);
		
		machineTick++;
		if(onTick(200) && direction.ordinal() > 1)
			generateHPWater();
		if(onTick(60)) {
			placeInTank();
			pullFromTank();
		}
		
		if(onTick(30) && tank.getFluidAmount() > 0 && worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
			helper.ejectFluid(new int[] { 1000, 500, 100, 20, 10, 1 });
			
	}
	
	public void placeInTank() {
		TileEntity tile = mariculture.core.helpers.cofh.BlockHelper.getAdjacentTileEntity(this, direction);
		if(tile != null && tile instanceof IFluidHandler) {
			int x2 = xCoord - direction.offsetX;
			int y2 = yCoord - direction.offsetY;
			int z2 = zCoord - direction.offsetZ;
			Block block = Block.blocksList[worldObj.getBlockId(x2, y2, z2)];
			if(block instanceof BlockFluidBase || block instanceof BlockFluid) {
				FluidStack fluid = null;
				if(block instanceof BlockFluidBase)
					fluid = ((BlockFluidBase) block).drain(worldObj, x2, y2, z2, false);
				if(BlockHelper.isWater(worldObj, x2, y2, z2))
					fluid = FluidRegistry.getFluidStack("water", 1000);
				if(BlockHelper.isLava(worldObj, x2, y2, z2))
					fluid = FluidRegistry.getFluidStack("lava", 1000);
				if(fluid != null) {
					IFluidHandler tank = (IFluidHandler) tile;
					if(tank.fill(direction, fluid, false) >= fluid.amount) {
						if(block instanceof BlockFluidBase)
							((BlockFluidBase) block).drain(worldObj, x2, y2, z2, true);
						else
							worldObj.setBlockToAir(x2, y2, z2);
						tank.fill(direction, fluid, true);
					}
				}
			}
		}
	}
	
	public void pullFromTank() {
		TileEntity tile = mariculture.core.helpers.cofh.BlockHelper.getAdjacentTileEntity(this, direction.getOpposite());
		if(tile != null && tile instanceof IFluidHandler) {
			int x2 = xCoord + direction.offsetX;
			int y2 = yCoord + direction.offsetY;
			int z2 = zCoord + direction.offsetZ;
			if(worldObj.isAirBlock(x2, y2, z2)) {
				IFluidHandler tank = (IFluidHandler) tile;
				FluidTankInfo[] info = tank.getTankInfo(direction.getOpposite());
				for(FluidTankInfo tanks: info) {
					if(tanks.fluid != null) {
						Fluid fluid = tanks.fluid.getFluid();
						if(fluid.canBePlacedInWorld()) {
							int drain = FluidHelper.getRequiredVolumeForBlock(fluid);
							if (tank.drain(direction.getOpposite(), drain, false).amount == drain) {
								int id = fluid.getBlockID();
								if (Block.blocksList[id] != null) {
									Block block = Block.blocksList[id];
									if (block instanceof BlockFluidFinite) {
										if (worldObj.isAirBlock(x2, y2, z2)) {
											worldObj.setBlock(x2, y2, z2, id, 0, 2);
										} else {
											int meta = worldObj.getBlockMetadata(x2, y2, z2) + 1;
											if (meta < 7 && worldObj.getBlockId(x2, y2, z2) == id) {
												worldObj.setBlockMetadataWithNotify(x2, y2, z2, meta, 2);
											} else {
												return;
											}
										}

										tank.drain(direction.getOpposite(), new FluidStack(fluid.getID(), drain), true);
									} else if (worldObj.isAirBlock(x2, y2, z2)) {
										worldObj.setBlock(x2, y2, z2, id);
										tank.drain(direction.getOpposite(), new FluidStack(fluid.getID(), drain), true);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void swapFluids() {
		
	}
	
	public void generateHPWater() {
		int x = xCoord + direction.offsetX;
		int z = zCoord + direction.offsetZ;
		if(BlockHelper.isAir(worldObj, x, yCoord, z) && height > 1)
			worldObj.setBlock(x, yCoord, z, Core.highPressureWaterBlock.blockID);
		if(BlockHelper.isHPWater(worldObj, x, yCoord, z)) {
			for(height = 0; BlockHelper.isWater(worldObj, xCoord - direction.offsetX, yCoord + height, zCoord - direction.offsetZ); height++) {
			}
			
			tank.fill(FluidRegistry.getFluidStack(FluidDictionary.hp_water, height * 10), true);
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		direction = ForgeDirection.values()[nbt.getInteger("Orientation")];
		height = nbt.getInteger("Height");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Orientation", direction.ordinal());
		nbt.setInteger("Height", height);
	}
}
