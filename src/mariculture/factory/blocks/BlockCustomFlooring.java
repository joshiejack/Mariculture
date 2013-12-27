package mariculture.factory.blocks;

import mariculture.core.lib.PlansMeta;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomFlooring extends BlockCustomBase {
	public BlockCustomFlooring(final int par1) {
		super(par1, Material.materialCarpet);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
		this.setTickRandomly(true);
		this.func_111047_d(0);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(final World par1World, final int par2, final int par3, final int par4) {
		final byte b0 = 0;
		final float f = 0.0625F;
		return AxisAlignedBB.getAABBPool().getAABB(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX,
				par3 + b0 * f, par4 + this.maxZ);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void setBlockBoundsForItemRender() {
		this.func_111047_d(0);
	}

	@Override
	public void setBlockBoundsBasedOnState(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4) {
		this.func_111047_d(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}

	private void func_111047_d(final int par1) {
		final byte b0 = 0;
		final float f = 1 * (1 + b0) / 16.0F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
	}

	@Override
	public int getID() {
		return PlansMeta.FLOOR;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(final IBlockAccess par1IBlockAccess, final int par2, final int par3, final int par4,
			final int par5) {
		return par5 == 1 ? true : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
	}
}
