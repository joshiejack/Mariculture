package mariculture.factory.tile;

import mariculture.api.core.MaricultureHandlers;
import mariculture.core.config.Machines.MachineSettings;
import mariculture.core.tile.TileTankBlock;
import mariculture.core.util.Fluids;
import mariculture.core.util.Tank;
import maritech.tile.TileRotor;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class TileGasTank extends TileTankBlock {
    private boolean hasGas = false;
    private TileRotor rotor = null;
    private ForgeDirection rotorDir = null;

    public TileGasTank() {
        tank = new Tank(512000);
    }

    @Override
    public boolean canUpdate() {
        return MaricultureHandlers.HIGH_TECH_ENABLED;
    }

    public boolean onTick(int i) {
        return worldObj.getTotalWorldTime() % i == 0;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource == null || Fluids.getFluid("natural_gas") != resource.getFluid()) {
            return 0;
        } else return super.fill(from, resource, doFill);
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (hasGas && rotor != null) {
                if (onTick(5)) {
                    pushToRotor();
                }
            } else {
                if (onTick(200)) {
                    if (!hasGas) {
                        updateHasGas();
                    } else updateRotor();
                }
            }
        }
    }

    public void updateHasGas() {
        FluidStack fluid = tank.getFluid();
        if (fluid == null || Fluids.getFluid("natural_gas") != fluid.getFluid() || tank.getFluidAmount() <= 0) {
            hasGas = false;
        } else hasGas = true;
    }
    
    public boolean isSolid(int x, int y, int z) {
        return worldObj.isBlockNormalCubeDefault(x, y, z, false);
    }

    public void updateRotor() {
        rotor = null;
        rotorDir = null;

        for (ForgeDirection orientation : ForgeDirection.values()) {
            TileEntity tile = worldObj.getTileEntity(xCoord + orientation.offsetX, yCoord, zCoord + orientation.offsetZ);
            if (tile instanceof TileRotor) {
                rotor = ((TileRotor) tile);
                rotorDir = orientation;
                break;
            }
        }
        
        if (rotor != null && rotorDir != null) {
            int airCount = 0;
            int solidCount = 0;
            if (rotorDir == ForgeDirection.WEST || rotorDir == ForgeDirection.EAST) {
                for (int z = -2; z <= 2; z++) {
                    for (int y = -2; y <= 2; y++) {
                        if (z >= -1 && z <= 1 && y >= -1 && y <= 1) {
                            if(worldObj.isAirBlock(rotor.xCoord, rotor.yCoord + y, rotor.zCoord + z)) {
                                airCount++;
                            }
                        } else {
                            if (isSolid(rotor.xCoord, rotor.yCoord + y, rotor.zCoord + z)) {
                                solidCount++;
                            }
                        }
                    }
                }
            } else if (rotorDir == ForgeDirection.SOUTH || rotorDir == ForgeDirection.NORTH) {
                for (int x = -2; x <= 2; x++) {
                    for (int y = -2; y <= 2; y++) {
                        if (x >= -1 && x <= 1 && y >= -1 && y <= 1) {
                            if(worldObj.isAirBlock(rotor.xCoord + x, rotor.yCoord + y, rotor.zCoord)) {
                                airCount++;
                            }
                        } else {
                            if (isSolid(rotor.xCoord + x, rotor.yCoord + y, rotor.zCoord)) {
                                solidCount ++;
                            }
                        }
                    }
                }
            }
                        
            if (airCount != 8 || solidCount != 16) {
                rotor = null;
                rotorDir = null;
            }
        }
    }

    public void pushToRotor() {
        rotor.addEnergy(rotorDir.getOpposite(), MachineSettings.GAS_TURBINE_POWER, MachineSettings.GAS_TURBINE_DAMAGE);
        tank.drain(1, true);
        updateHasGas();
        
        //Recheck the tank every 1000mB (works out as every 5000 ticks). Or every 4 minutes
        if (tank.getFluidAmount() % 1000 == 0) {
            updateRotor();
        }
    }
}
