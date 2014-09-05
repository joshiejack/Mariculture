package joshie.mariculture.core.util;

import java.util.Collections;

import joshie.maritech.tile.TileCustomPowered;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyHandler;

public class PowerHelper {

    public static Object[] getNextEnergyHandler(ForgeDirection from, World worldObj, int xCoord, int yCoord, int zCoord) {
        ForgeDirection facing = ForgeDirection.UNKNOWN;
        IEnergyHandler handler = null;
        Collections.shuffle(TileCustomPowered.handlers);
        for (int i = 0; i < TileCustomPowered.handlers.size(); i++) {
            Object[] obj = TileCustomPowered.handlers.get(i);
            int x = (Integer) obj[0];
            int y = (Integer) obj[1];
            int z = (Integer) obj[2];
            ForgeDirection direction = (ForgeDirection) obj[3];

            if (from != direction) if (PowerHelper.isEnergyHandler(worldObj, xCoord + x, yCoord + y, zCoord + z) != null) {
                handler = PowerHelper.isEnergyHandler(worldObj, xCoord + x, yCoord + y, zCoord + z);
                facing = direction;
            }
        }

        return new Object[] { handler, facing };
    }

    public static IEnergyHandler isEnergyHandler(World world, int x, int y, int z) {
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof IEnergyHandler) return (IEnergyHandler) tile;

        return null;
    }

}
