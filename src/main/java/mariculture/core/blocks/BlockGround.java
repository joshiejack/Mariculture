package mariculture.core.blocks;

import java.util.Random;

import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.GroundMeta;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGround extends BlockDecorative {
	public BlockGround() {
		super(Material.sand);
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch (world.getBlockMetadata(x, y, z)) {
		case GroundMeta.BUBBLES:
			return 0.5F;
		}

		return 3F;
	}
	
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == GroundMeta.BUBBLES) {
			for(int i = y; BlockHelper.isWater(world, x, i + 1, z); i++) {
				for (int j = 0; j < 2; ++j)  {
	                float f = rand.nextFloat() - rand.nextFloat();
	                float f1 = rand.nextFloat() - rand.nextFloat();
	                float f2 = rand.nextFloat() - rand.nextFloat();
	                world.spawnParticle("bubble", x + 0.5D + (double)f, i + (double)f1, z + 0.5D + (double)f2, 0, 0, 0);
	            }
			}
		}
	}
	
	@Override
	public Item getItemDropped(int i, Random random, int j) {
        return Item.getItemFromBlock(Blocks.sand);
    }
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.sand.getIcon(side, meta);
	}

	@Override
	public int getMetaCount() {
		return GroundMeta.COUNT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		return;
	}
}
