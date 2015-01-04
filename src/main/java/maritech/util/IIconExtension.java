package maritech.util;

import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface IIconExtension {
    @SideOnly(Side.CLIENT)
    public IIcon getInventoryIcon(int meta, int side, IIcon icon);
    @SideOnly(Side.CLIENT)
    public IIcon getWorldIcon(IBlockAccess block, int x, int y, int z, int side, IIcon icon);
}
