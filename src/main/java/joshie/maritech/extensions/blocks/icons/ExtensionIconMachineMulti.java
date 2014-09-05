package joshie.maritech.extensions.blocks.icons;

import static joshie.maritech.lib.SpecialIcons.incubatorIcons;
import joshie.mariculture.core.Core;
import joshie.mariculture.core.lib.MachineMultiMeta;
import joshie.maritech.tile.TileIncubator;
import joshie.maritech.util.IIconExtension;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ExtensionIconMachineMulti implements IIconExtension {
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getInventoryIcon(int meta, int side, IIcon icon) {
        return icon;
    }

    @SideOnly(Side.CLIENT)
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
