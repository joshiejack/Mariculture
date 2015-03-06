package maritech.tile;

import mariculture.api.core.IBlacklisted;
import mariculture.core.config.Machines.MachineSettings;
import mariculture.core.config.Machines.Ticks;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.network.PacketHandler;
import mariculture.core.tile.base.TileTank;
import mariculture.core.util.Fluids;
import mariculture.core.util.IFaceable;
import mariculture.core.util.Tank;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
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
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidHandler;

public class TileSluice extends TileTank implements IBlacklisted, IFaceable {
    public ForgeDirection orientation = ForgeDirection.UP;
    protected int height = 0;
    protected int distance;

    public TileSluice() {
        tank = new Tank(10000);
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
        return worldObj.getWorldTime() % i == 0;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (orientation.ordinal() > 1) {
                if (onTick(Ticks.SLUICE_TIMER)) {
                    pushToGenerator();
                }

                if (onTick(200)) {
                    generateHPWater();
                }
            }

            if (onTick(60)) {
                placeInTank();
                pullFromTank();
                switchTanks();
            }
        }
    }

    protected void removeFluid(IFluidHandler tank, int x2, int y2, int z2) {
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

    public void placeInTank() {
        TileEntity tile = mariculture.core.helpers.cofh.BlockHelper.getAdjacentTileEntity(this, orientation);
        if (tile != null && tile instanceof IFluidHandler) {
            removeFluid((IFluidHandler) tile, xCoord - orientation.offsetX, yCoord - orientation.offsetY, zCoord - orientation.offsetZ);
        }
    }

    protected boolean canBeReplaced(int x, int y, int z) {
        Block block = worldObj.getBlock(x, y, z);
        if (block instanceof IFluidBlock || block instanceof BlockStaticLiquid) {
            return worldObj.getBlockMetadata(x, y, z) != 0;
        } else return worldObj.isAirBlock(x, y, z);
    }

    protected void placeFluid(IFluidHandler tank, int x, int y, int z) {
        FluidStack stack = tank.drain(orientation.getOpposite(), 100000, false);
        if (stack != null && stack.getFluid() != null) {
            Fluid fluid = stack.getFluid();
            if (fluid != null && fluid.canBePlacedInWorld()) {
                int drain = FluidHelper.getRequiredVolumeForBlock(fluid);
                FluidStack drainStack = tank.drain(orientation.getOpposite(), drain, false);
                if (drainStack != null && drainStack.amount == drain) {
                    Block block = fluid.getBlock();
                    if (block != null) {
                        if (block instanceof BlockFluidFinite) {
                            if (worldObj.isAirBlock(x, y, z)) {
                                worldObj.setBlock(x, y, z, block, 0, 2);
                            } else {
                                BlockFluidFinite finite = (BlockFluidFinite) block;
                                int maxMeta = finite.getMaxRenderHeightMeta();
                                int meta = worldObj.getBlockMetadata(x, y, z) + 1;
                                if (meta < maxMeta && worldObj.getBlock(x, y, z) == block) {
                                    worldObj.setBlockMetadataWithNotify(x, y, z, meta, 2);
                                } else return;

                                tank.drain(orientation.getOpposite(), drain, true);
                            }
                        } else if (canBeReplaced(x, y, z)) {
                            worldObj.setBlock(x, y, z, block);
                            tank.drain(orientation.getOpposite(), drain, true);
                        }
                    }
                }
            }
        }
    }

    public void pullFromTank() {
        TileEntity tile = mariculture.core.helpers.cofh.BlockHelper.getAdjacentTileEntity(this, orientation.getOpposite());
        if (tile != null && tile instanceof IFluidHandler) {
            placeFluid((IFluidHandler) tile, xCoord + orientation.offsetX, yCoord + orientation.offsetY, zCoord + orientation.offsetZ);
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

    public boolean isValid(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z) == Fluids.getFluidBlock("hp_water")) {
            return true;
        } else return false;
    }

    public int getEnergyGenerated(int distance) {
        return (height * distance * MachineSettings.SLUICE_POWER_MULTIPLIER);
    }

    public void generateHPWater() {
        int x = xCoord + orientation.offsetX;
        int z = zCoord + orientation.offsetZ;
        if (BlockHelper.isWater(worldObj, xCoord - orientation.offsetX, yCoord, zCoord - orientation.offsetZ)) {
            if (BlockHelper.isAir(worldObj, x, yCoord, z)) {
                worldObj.setBlock(x, yCoord, z, Fluids.getFluidBlock("hp_water"));
            }
        } else if (BlockHelper.isHPWater(worldObj, x, yCoord, z)) {
            worldObj.setBlockToAir(x, yCoord, z);
        }

        if (BlockHelper.isHPWater(worldObj, x, yCoord, z)) {
            for (height = 0; BlockHelper.isWater(worldObj, xCoord - orientation.offsetX, yCoord + height, zCoord - orientation.offsetZ); height++) {}
        }

        if (height > 0) {
            for (distance = 1; isValid(worldObj, xCoord + (orientation.offsetX * distance), yCoord, zCoord + (orientation.offsetZ * distance)) && distance < 16; distance++) {}
        }
    }

    public void pushToGenerator() {
        if (height > 0) {
            TileEntity tile = worldObj.getTileEntity(xCoord + (orientation.offsetX * distance), yCoord, zCoord + (orientation.offsetZ * distance));
            if (tile instanceof TileRotor) {
                ((TileRotor) tile).addEnergy(orientation.getOpposite(), getEnergyGenerated(distance) >> 8, 200);
            }
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
