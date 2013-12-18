package mariculture.core.blocks;

import mariculture.core.lib.MetalRates;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFluids extends BlockFluidClassic {
	private boolean isMetal;
	private Icon[] theIcon;

	public BlockFluids(int id, Fluid fluid, Material material, boolean metal) {
		super(id, fluid, material);
		this.isMetal = metal;
	}
	
	@Override
	public FluidStack drain(World world, int x, int y, int z, boolean doDrain) {
		if (!isSourceBlock(world, x, y, z)) {
            return null;
        }

        if (doDrain) {
            world.setBlockToAir(x, y, z);
        }
        
        if(isMetal) {
        	return new FluidStack(stack.getFluid(), MetalRates.INGOT);
        } else {
        	return stack.copy();
        }
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return side != 0 && side != 1 ? this.theIcon[1] : this.theIcon[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		this.theIcon = new Icon[] { iconRegister.registerIcon("mariculture:liquids/" + fluidName + "_still"),
				iconRegister.registerIcon("mariculture:liquids/" + fluidName + "_flow") };
	}
}
