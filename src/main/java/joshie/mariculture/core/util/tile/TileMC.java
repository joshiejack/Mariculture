package joshie.mariculture.core.util.tile;

import joshie.mariculture.core.helpers.TileHelper;
import joshie.mariculture.core.util.interfaces.Faceable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class TileMC extends TileEntity {
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()  {
        return new SPacketUpdateTileEntity(getPos(), 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        NBTTagCompound nbt = packet.getNbtCompound();
        readFromNBT(nbt);
        if (nbt.hasKey("Render")) {
            worldObj.markBlockRangeForRenderUpdate(getPos(), getPos());
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void markDirty() {
        if (!worldObj.isRemote) {
            TileHelper.sendRenderUpdate(this);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)  {
        super.readFromNBT(compound);
        if (this instanceof Faceable) ((Faceable)this).readFacing(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        if (this instanceof Faceable) ((Faceable)this).writeFacing(compound);
        return super.writeToNBT(compound);
    }
}
