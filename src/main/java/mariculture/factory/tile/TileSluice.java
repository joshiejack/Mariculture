package mariculture.factory.tile;

import mariculture.api.core.IBlacklisted;
import mariculture.core.Core;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.network.PacketHandler;
import mariculture.core.util.IFaceable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileSluice extends TileEntity implements IBlacklisted, IFaceable {
    public ForgeDirection orientation = ForgeDirection.UP;
    private int height = 0;

    @Override
    public boolean isBlacklisted(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    public boolean onTick(int i) {
        return worldObj.getWorldTime() % i == 0;
    }

    @Override
    public void updateEntity() {
        if (onTick(200) && orientation.ordinal() > 1) {
            generateHPWater();
        }
        if (onTick(60)) {
            placeInTank();
            pullFromTank();
            switchTanks();
        }
    }

    public void placeInTank() {
        TileEntity tile = mariculture.core.helpers.cofh.BlockHelper.getAdjacentTileEntity(this, orientation);
        if (tile != null && tile instanceof IFluidHandler) {
            int x2 = xCoord - orientation.offsetX;
            int y2 = yCoord - orientation.offsetY;
            int z2 = zCoord - orientation.offsetZ;
            Block block = worldObj.getBlock(x2, y2, z2);
            if (block instanceof BlockFluidBase || block instanceof BlockLiquid) {
                FluidStack fluid = null;
                if (block instanceof BlockFluidBase) {
                    fluid = ((BlockFluidBase) block).drain(worldObj, x2, y2, z2, false);
                }
                if (BlockHelper.isWater(worldObj, x2, y2, z2)) {
                    fluid = FluidRegistry.getFluidStack("water", 1000);
                }
                if (BlockHelper.isLava(worldObj, x2, y2, z2)) {
                    fluid = FluidRegistry.getFluidStack("lava", 1000);
                }
                if (fluid != null) {
                    IFluidHandler tank = (IFluidHandler) tile;
                    if (tank.fill(orientation, fluid, false) >= fluid.amount) {
                        if (block instanceof BlockFluidBase) {
                            ((BlockFluidBase) block).drain(worldObj, x2, y2, z2, true);
                        } else {
                            worldObj.setBlockToAir(x2, y2, z2);
                        }
                        tank.fill(orientation, fluid, true);
                    }
                }
            }
        }
    }

    public void pullFromTank() {
        TileEntity tile = mariculture.core.helpers.cofh.BlockHelper.getAdjacentTileEntity(this, orientation.getOpposite());
        if (tile != null && tile instanceof IFluidHandler) {
            int x2 = xCoord + orientation.offsetX;
            int y2 = yCoord + orientation.offsetY;
            int z2 = zCoord + orientation.offsetZ;
            if (worldObj.isAirBlock(x2, y2, z2)) {
                IFluidHandler tank = (IFluidHandler) tile;
                FluidTankInfo[] info = tank.getTankInfo(orientation.getOpposite());
                if (info == null || info.length < 1) return;
                for (FluidTankInfo tanks : info)
                    if (tanks.fluid != null) {
                        Fluid fluid = tanks.fluid.getFluid();
                        if (fluid != null && fluid.canBePlacedInWorld()) {
                            int drain = FluidHelper.getRequiredVolumeForBlock(fluid);
                            FluidStack drainStack = tank.drain(orientation.getOpposite(), drain, false);
                            if (drainStack != null && drainStack.amount == drain) {
                                Block block = fluid.getBlock();
                                if (block != null) {
                                    FluidStack stack = tank.drain(orientation.getOpposite(), new FluidStack(fluid.getID(), drain), false);
                                    if (stack == null) return;
                                    if (block instanceof BlockFluidFinite) {
                                        if (worldObj.isAirBlock(x2, y2, z2)) {
                                            worldObj.setBlock(x2, y2, z2, block, 0, 2);
                                        } else {
                                            int meta = worldObj.getBlockMetadata(x2, y2, z2) + 1;
                                            if (meta < 7 && worldObj.getBlock(x2, y2, z2) == block) {
                                                worldObj.setBlockMetadataWithNotify(x2, y2, z2, meta, 2);
                                            } else return;
                                        }

                                        tank.drain(orientation.getOpposite(), new FluidStack(fluid.getID(), drain), true);
                                    } else if (worldObj.isAirBlock(x2, y2, z2)) {
                                        worldObj.setBlock(x2, y2, z2, block);
                                        tank.drain(orientation.getOpposite(), new FluidStack(fluid.getID(), drain), true);
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }

    public void switchTanks() {
        int x2 = xCoord + orientation.offsetX;
        int y2 = yCoord + orientation.offsetY;
        int z2 = zCoord + orientation.offsetZ;
        int x3 = xCoord + orientation.getOpposite().offsetX;
        int y3 = yCoord + orientation.getOpposite().offsetY;
        int z3 = zCoord + orientation.getOpposite().offsetZ;
        if (worldObj.getTileEntity(x2, y2, z2) instanceof IFluidHandler && worldObj.getTileEntity(x3, y3, z3) instanceof IFluidHandler) {
            IFluidHandler tankFrom = (IFluidHandler) worldObj.getTileEntity(x3, y3, z3);
            IFluidHandler tankTo = (IFluidHandler) worldObj.getTileEntity(x2, y2, z2);
            if (tankTo instanceof TileSluice) return;
            FluidStack fluid = tankFrom.drain(orientation, 1000, false);
            if (fluid == null) return;
            int drained = tankTo.fill(orientation.getOpposite(), fluid, false);
            if (drained > 0) {
                FluidStack drain = tankFrom.drain(orientation, drained, true);
                tankTo.fill(orientation.getOpposite(), drain, true);
            }
        }
    }

    public void generateHPWater() {
        int x = xCoord + orientation.offsetX;
        int z = zCoord + orientation.offsetZ;
        if (BlockHelper.isWater(worldObj, xCoord - orientation.offsetX, yCoord, zCoord - orientation.offsetZ)) {
            if (BlockHelper.isAir(worldObj, x, yCoord, z)) {
                worldObj.setBlock(x, yCoord, z, Core.hpWaterBlock);
            }
        } else if (BlockHelper.isHPWater(worldObj, x, yCoord, z)) {
            worldObj.setBlockToAir(x, yCoord, z);
        }
        
        if (BlockHelper.isHPWater(worldObj, x, yCoord, z)) {
            for (height = 0; BlockHelper.isWater(worldObj, xCoord - orientation.offsetX, yCoord + height, zCoord - orientation.offsetZ); height++) {}
        }
    }

    @Override
    public boolean rotate() {
        setFacing(BlockHelper.rotate(orientation));
        return true;
    }

    @Override
    public ForgeDirection getFacing() {
        return orientation;
    }

    @Override
    public void setFacing(ForgeDirection dir) {
        orientation = dir;
        if (!worldObj.isRemote) {
            PacketHandler.updateOrientation(this);
        }
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
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
        height = nbt.getInteger("Height");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Orientation", orientation.ordinal());
        nbt.setInteger("Height", height);
    }
}
