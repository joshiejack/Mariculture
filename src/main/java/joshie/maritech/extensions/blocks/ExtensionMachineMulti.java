package joshie.maritech.extensions.blocks;

import static joshie.maritech.lib.SpecialIcons.incubatorIcons;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.lib.MachineMultiMeta;
import joshie.mariculture.core.lib.Modules;
import joshie.maritech.tile.TileIncubator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class ExtensionMachineMulti extends ExtensionBase {
    @Override
    public boolean isActive(int meta, boolean isActive) {
        switch (meta) {
            case MachineMultiMeta.INCUBATOR_BASE:
            case MachineMultiMeta.INCUBATOR_TOP:
                return Modules.isActive(Modules.fishery);
        }

        return isActive;
    }

    @Override
    public TileEntity getTileEntity(int meta, TileEntity tile) {
        switch (meta) {
            case MachineMultiMeta.INCUBATOR_BASE:
            case MachineMultiMeta.INCUBATOR_TOP:
                return new TileIncubator();
        }

        return tile;
    }

    @Override
    public IIcon getWorldIcon(IBlockAccess block, int x, int y, int z, int side, IIcon icon) {
        if (side > 1) {
            TileEntity tile = block.getTileEntity(x, y, z);
            if (tile instanceof TileIncubator && block.getBlockMetadata(x, y, z) != MachineMultiMeta.INCUBATOR_BASE) {
                TileIncubator incubator = (TileIncubator) tile;
                if (incubator.master == null) return Core.machinesMulti.getIcon(side, MachineMultiMeta.INCUBATOR_TOP);
                else if (incubator.facing == ForgeDirection.DOWN) return incubatorIcons[0];
                else return incubatorIcons[1];
            }
        }

        return icon;
    }
}
