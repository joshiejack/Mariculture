package mariculture.core.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mariculture.core.Mariculture;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.GroundMeta;
import mariculture.core.lib.SingleMeta;
import mariculture.fishery.Fishery;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockGround extends BlockDecorative {
	public BlockGround(int i) {
		super(i, Material.sand);
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch (world.getBlockMetadata(x, y, z)) {
		case GroundMeta.BUBBLES:
			return 0.5F;
		case GroundMeta.GEYSER:
			return 0.75F;
		}

		return 3F;
	}
	
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		int meta = world.getBlockMetadata(x, y, z);
		String type = (meta == GroundMeta.BUBBLES)? "bubble": "cloud";
		for(int i = y; BlockHelper.isWater(world, x, i + 1, z); i++) {
			for (int j = 0; j < 2; ++j)  {
                float f = rand.nextFloat() - rand.nextFloat();
                float f1 = rand.nextFloat() - rand.nextFloat();
                float f2 = rand.nextFloat() - rand.nextFloat();
                world.spawnParticle(type, x + 0.5D + (double)f, i + (double)f1, z + 0.5D + (double)f2, 0, 0, 0);
            }
		}
	}
	
	@Override
	public int idDropped(int i, Random random, int j) {
		return Block.sand.blockID;
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return Block.sand.getIcon(side, meta);
	}

	@Override
	public int getMetaCount() {
		return GroundMeta.COUNT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		return;
	}
}
