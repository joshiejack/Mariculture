package mariculture.core.blocks.base;

import mariculture.Mariculture;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFluid extends BlockFluidClassic {
    public IIcon still, flowing;
    protected Fluid fluid;

    public BlockFluid(Fluid fluid, Material material) {
        super(fluid, material);
        this.fluid = fluid;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        if (side == 0 || side == 1) return fluid.getStillIcon();
        return fluid.getFlowingIcon();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        still = iconRegister.registerIcon(Mariculture.modid + ":liquids/" + fluid.getName() + "_still");
        flowing = iconRegister.registerIcon(Mariculture.modid + ":liquids/" + fluid.getName() + "_flow");
    }
}
