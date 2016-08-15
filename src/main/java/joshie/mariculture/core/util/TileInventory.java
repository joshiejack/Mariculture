package joshie.mariculture.core.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public abstract class TileInventory extends TileMC {
    protected abstract INBTSerializable<NBTTagCompound> getInventory();

    @Override
    public void readFromNBT(NBTTagCompound compound)  {
        super.readFromNBT(compound);
        getInventory().deserializeNBT(compound.getCompoundTag("Inventory"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("Inventory", getInventory().serializeNBT());
        return super.writeToNBT(compound);
    }
}
