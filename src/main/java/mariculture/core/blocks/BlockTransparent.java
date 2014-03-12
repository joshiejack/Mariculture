package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.core.blocks.base.BlockConnected;
import mariculture.core.lib.TransparentMeta;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockTransparent extends BlockConnected {
	private static IIcon[] plastic = new IIcon[47];

	public BlockTransparent() {
		super(Material.glass);
		this.setHardness(0.5F);
	}
	
	//TODO: Check Plastic is harvestable
	@Override
	public String getToolType(int meta) {
		return null;
	}

	@Override
	public int getToolLevel(int meta) {
		return 0;
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
    public boolean renderAsNormalBlock() {
    	return false;
    }
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		Block block = world.getBlock(x, y, z);
        return block == this ? false : super.shouldSideBeRendered(world, x, y, z, side);
    }

	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register("transparent." + getName(new ItemStack(this, 1, j)), new ItemStack(this, 1, j));
		}
	}

	@Override
	public int getMetaCount() {
		return TransparentMeta.COUNT;
	}

	@Override
	public IIcon[] getTexture(int meta) {
		switch(meta) {
			case TransparentMeta.PLASTIC:
				return plastic;
			default: return null;
		}
	}

	@Override
	public void registerConnectedTextures(IIconRegister iconRegister) {
		for (int i = 0; i < 47; i++) {
			plastic[i] = iconRegister.registerIcon(Mariculture.modid + ":plastic/" + (i + 1));
		}
	}
}