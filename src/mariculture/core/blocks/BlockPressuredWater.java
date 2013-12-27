package mariculture.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPressuredWater extends BlockFluidClassic {
	public BlockPressuredWater(int id, Fluid fluid, Material material) {
		super(id, fluid, material);
		this.quantaPerBlock = 5;
		this.quantaPerBlockFloat = 5;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return meta == 0 ? Block.waterStill.getIcon(side, meta) : Block.waterMoving.getIcon(side, meta);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(final IconRegister iconRegister) {
	}
	
	@Override
    public FluidStack drain(World world, int x, int y, int z, boolean doDrain) {
        return null;
    }

	@Override
	public void velocityToAddToEntity(World world, int x, int y, int z, Entity entity, Vec3 vec) {
		Vec3 vec_flow = this.getFlowVector(world, x, y, z);

		double xSpeed = vec_flow.xCoord * 0.0845;
		double zSpeed = vec_flow.zCoord * 0.0845;

		entity.addVelocity(xSpeed, 0, zSpeed);
	}
}
