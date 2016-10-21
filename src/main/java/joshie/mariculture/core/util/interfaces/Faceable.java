package joshie.mariculture.core.util.interfaces;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public interface Faceable {
    EnumFacing getFacing();
    void setFacing(EnumFacing facing);

    default void readFacing(NBTTagCompound tag) {
        setFacing(EnumFacing.valueOf(tag.getString("Facing")));
    }

    default void writeFacing(NBTTagCompound tag) {
        tag.setString("Facing", getFacing().name());
    }
}
