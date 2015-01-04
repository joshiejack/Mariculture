package maritech.extensions.blocks.icons;

import mariculture.core.Core;
import mariculture.core.lib.MachineRenderedMeta;
import mariculture.core.lib.MetalMeta;
import maritech.util.IIconExtension;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ExtensionIconRenderedMachine implements IIconExtension {
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getInventoryIcon(int meta, int side, IIcon icon) {
        if (meta == MachineRenderedMeta.ROTOR_COPPER) return Core.metals.getIcon(0, MetalMeta.COPPER_BLOCK);
        else if (meta == MachineRenderedMeta.ROTOR_ALUMINUM) return Core.metals.getIcon(0, MetalMeta.ALUMINUM_BLOCK);
        else if (meta == MachineRenderedMeta.ROTOR_TITANIUM) return Core.metals.getIcon(0, MetalMeta.TITANIUM_BLOCK);
        return icon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getWorldIcon(IBlockAccess block, int x, int y, int z, int side, IIcon icon) {
        return icon;
    }
}
