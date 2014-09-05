package joshie.mariculture.core.util;

import net.minecraftforge.common.util.ForgeDirection;

public interface IFaceable {
    public boolean rotate();

    public ForgeDirection getFacing();

    public void setFacing(ForgeDirection dir);
}
