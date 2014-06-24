package mariculture.factory.tile;

import java.util.LinkedList;

import mariculture.api.util.CachedCoords;
import mariculture.core.config.Machines.MachineSettings;
import mariculture.core.config.Machines.Ticks;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.FluidHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidHandler;

public class TileSluiceAdvanced extends TileSluice {
    public LinkedList<CachedCoords> todo;

    @Override
    public void updateEntity() {
        if (onTick(150) && orientation.ordinal() > 1) {
            generateHPWater();
        }

        if (onTick(Ticks.ADVANCED_SLUICE_TICK)) {
            if (!worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)) placeInTank();
            else pullFromTank();

            //Do the TankSwitching Always
            switchTanks();
        }
    }

    private void addToList(int x, int y, int z) {
        CachedCoords coord = new CachedCoords(x, y, z);
        if (!todo.contains(coord)) todo.add(coord);
    }

    private boolean isValid(World world, int x, int y, int z, boolean drain) {
        if (drain) {
            Block block = world.getBlock(x, y, z);
            if (block instanceof IFluidBlock || block instanceof BlockStaticLiquid) {
                return world.getBlockMetadata(x, y, z) == 0;
            } else return false;
        } else {
            return canBeReplaced(x, y, z);
        }
    }

    private void updateArea(boolean drain) {
        todo = new LinkedList();
        int x = xCoord;
        int y = yCoord;
        int z = zCoord;

        if (drain) {
            x = xCoord + orientation.getOpposite().offsetX;
            y = yCoord + orientation.getOpposite().offsetY;
            z = zCoord + orientation.getOpposite().offsetZ;
        } else {
            x = xCoord + orientation.offsetX;
            y = yCoord + orientation.offsetY;
            z = zCoord + orientation.offsetZ;
        }

        if (isValid(worldObj, x, y, z, drain)) {
            addToList(x, y, z);
            int loop = !drain? MachineSettings.ADVANCED_SLUICE_RADIUS/2: MachineSettings.ADVANCED_SLUICE_RADIUS;
            for (int j = 0; j < loop; j++) {
                LinkedList<CachedCoords> temp = (LinkedList<CachedCoords>) todo.clone();
                for (CachedCoords coord : temp) {
                    if (isValid(worldObj, coord.x + 1, coord.y, coord.z, drain)) {
                        addToList(coord.x + 1, coord.y, coord.z);
                    }

                    if (isValid(worldObj, coord.x - 1, coord.y, coord.z, drain)) {
                        addToList(coord.x - 1, coord.y, coord.z);
                    }

                    if (isValid(worldObj, coord.x, coord.y, coord.z + 1, drain)) {
                        addToList(coord.x, coord.y, coord.z + 1);
                    }

                    if (isValid(worldObj, coord.x, coord.y, coord.z - 1, drain)) {
                        addToList(coord.x, coord.y, coord.z - 1);
                    }

                    if (drain) {
                        if (isValid(worldObj, coord.x, coord.y + 1, coord.z, drain)) {
                            addToList(coord.x, coord.y + 1, coord.z);
                        }
                    } else {
                        if (isValid(worldObj, coord.x, coord.y - 1, coord.z, drain)) {
                            addToList(coord.x, coord.y - 1, coord.z);
                        }
                    }
                }
            }
        }
    }

    private CachedCoords getNextCoordinates(boolean drain) {
        CachedCoords cord = null;
        if (todo == null || todo.size() <= 0) {
            updateArea(drain);
        }

        if (todo != null && todo.size() > 0) {
            cord = todo.getLast();
            todo.removeLast();
        }

        return cord;
    }

    @Override
    public void placeInTank() {
        TileEntity tile = mariculture.core.helpers.cofh.BlockHelper.getAdjacentTileEntity(this, orientation);
        if (tile != null && tile instanceof IFluidHandler) {
            CachedCoords cord = getNextCoordinates(true);
            if (cord != null) {
                int x2 = cord.x;
                int y2 = cord.y;
                int z2 = cord.z;
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
    }

    private boolean canBeReplaced(int x, int y, int z) {
        Block block = worldObj.getBlock(x, y, z);
        if (block instanceof IFluidBlock || block instanceof BlockStaticLiquid) {
            return worldObj.getBlockMetadata(x, y, z) != 0;
        } else return worldObj.isAirBlock(x, y, z);
    }

    @Override
    public void pullFromTank() {
        TileEntity tile = mariculture.core.helpers.cofh.BlockHelper.getAdjacentTileEntity(this, orientation.getOpposite());
        if (tile != null && tile instanceof IFluidHandler) {
            CachedCoords cord = getNextCoordinates(false);
            if (cord != null) {
                int x2 = cord.x;
                int y2 = cord.y;
                int z2 = cord.z;
                if (canBeReplaced(x2, y2, z2)) {
                    IFluidHandler tank = (IFluidHandler) tile;
                    FluidTankInfo[] info = tank.getTankInfo(orientation.getOpposite());
                    if (info == null || info.length < 1) return;
                    for (FluidTankInfo tanks : info) {
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
                                        } else if (canBeReplaced(x2, y2, z2)) {
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
        }
    }
}
