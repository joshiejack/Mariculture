package maritech.tile;

import mariculture.api.core.CachedCoords;
import mariculture.core.helpers.BlockTransferHelper;
import mariculture.core.helpers.cofh.BlockHelper;
import mariculture.core.util.IFaceable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;

public class TileGenerator extends TileEntity implements IEnergyConnection, IFaceable {
    private static final int MAX_TRANSFER = 5000;

    public ForgeDirection orientation = ForgeDirection.NORTH;

    public boolean onTick(int i) {
        return worldObj.getWorldTime() % i == 0;
    }

    //This needs to be called when this block is placed, and everytime something affects a rotor
    public void reset() {
        if (orientation != null) {
            for (int i = 1; isRotor(worldObj, xCoord + (orientation.getOpposite().offsetX * i), yCoord, zCoord + (orientation.getOpposite().offsetZ * i)); i++) {
                ((TileRotor) worldObj.getTileEntity(xCoord + (orientation.getOpposite().offsetX * i), yCoord, zCoord + (orientation.getOpposite().offsetZ * i))).setMaster(new CachedCoords(xCoord, yCoord, zCoord));
            }
        }
    }

    private boolean isRotor(World world, int x, int y, int z) {
        return world.getTileEntity(x, y, z) instanceof TileRotor;
    }

    private TileRotor getRotorFromCoords(CachedCoords cord) {
        return (TileRotor) worldObj.getTileEntity(cord.x, cord.y, cord.z);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    @Override
    public boolean canUpdate() {
        return false;
    }

    public void addEnergy(int energy) {
        if (energy >= 0) {
            //Take the power inside of me and pass it to all 'sides'
            for (Integer i : BlockTransferHelper.getSides()) {
                ForgeDirection dir = ForgeDirection.values()[i];
                //Don't output the front or back of the generator, any other side is valid
                if (orientation != null) {
                    if (dir != orientation && dir != orientation.getOpposite()) {
                        TileEntity tile = BlockHelper.getAdjacentTileEntity(worldObj, xCoord, yCoord, zCoord, dir);
                        if (tile instanceof IEnergyHandler && energy > 0) {
                            if (((IEnergyHandler) tile).canConnectEnergy(dir.getOpposite())) {
                                int extract = -((IEnergyHandler) tile).receiveEnergy(dir.getOpposite(), Math.min(energy, MAX_TRANSFER), false);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean rotate() {
        setFacing(mariculture.core.helpers.BlockHelper.rotate(orientation));
        return true;
    }

    @Override
    public ForgeDirection getFacing() {
        return orientation;
    }

    @Override
    public void setFacing(ForgeDirection dir) {        
        orientation = dir;
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
        orientation = ForgeDirection.getOrientation(nbt.getInteger("Orientation"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Orientation", orientation.ordinal());
    }
}
