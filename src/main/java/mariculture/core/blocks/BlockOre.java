package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.core.lib.Modules;
import mariculture.core.lib.OresMeta;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOre extends BlockDecorative {
	private IIcon[] retexture;

	public BlockOre() {
		super(Material.rock);
	}

	@Override
	public IIcon getIcon(IBlockAccess block, int x, int y, int z, int side) {
		int meta = block.getBlockMetadata(x, y, z);

		if (meta == OresMeta.ALUMINUM_BLOCK) {
			if (isSameBlock(block, new int[] { x, y, z }, new int[] { x, y + 1, z })) {
				if (isSameBlock(block, new int[] { x, y, z }, new int[] { x, y - 1, z })) {
					return retexture[0];
				}

				return retexture[1];
			}

			if (isSameBlock(block, new int[] { x, y, z }, new int[] { x, y - 1, z })) {
				if (isSameBlock(block, new int[] { x, y, z }, new int[] { x, y + 1, z })) {
					return retexture[0];
				}

				return retexture[2];
			}
		}

		return this.getIcon(side, block.getBlockMetadata(x, y, z));
	}

	private boolean isSameBlock(IBlockAccess world, int[] coords1, int[] coords2) {
		return world.getBlock(coords1[0], coords1[1], coords1[2]) == world.getBlock(coords2[0], coords2[1], coords2[2]);
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch (world.getBlockMetadata(x, y, z)) {
		case OresMeta.BAUXITE:
			return 3F;
		case OresMeta.RUTILE:
			return 10F;
		case OresMeta.LIMESTONE:
			return 1F;
		case OresMeta.LIMESTONE_BRICK:
			return 1.5F;
		case OresMeta.CORAL_ROCK:
			return 5F;
		case OresMeta.ALUMINUM_BLOCK:
			return 3.5F;
		case OresMeta.TITANIUM_BLOCK:
			return 15F;
		case OresMeta.MAGNESIUM_BLOCK:
			return 3F;
		case OresMeta.COPPER:
			return 1.5F;
		case OresMeta.COPPER_BLOCK:
			return 2F;
		case OresMeta.LIMESTONE_SMOOTH:
			return 1.5F;
		case OresMeta.LIMESTONE_CHISELED:
			return 1.5F;
		case OresMeta.BASE_BRICK:
			return 8F;
		case OresMeta.BASE_IRON:
			return 5F;
		case OresMeta.LIMESTONE_THIN:
			return 1.4F;
		}

		return 3F;
	}
	
	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		if(world.getBlockMetadata(x, y, z) == OresMeta.BASE_BRICK) {
			return 2000F;
		} else {
			return getBlockHardness(world, x, y, z) * 3;
		}
	}

	@Override
	public boolean isReplaceableOreGen(World world, int x, int y, int z, Block target) {
		return world.getBlockMetadata(x, y, z) == OresMeta.LIMESTONE;
	}
	
	public boolean isActive(int meta) {
		if(meta == OresMeta.CORAL_ROCK)
			return Modules.world.isActive();
		return meta != OresMeta.UNUSED;
	}
	
	@Override
	public int getMetaCount() {
		return OresMeta.COUNT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		super.registerBlockIcons(register);
		retexture = new IIcon[3];
		for (int i = 0; i < retexture.length; i++) {
			retexture[i] = register.registerIcon(Mariculture.modid + ":aluminumBlock" + i);
		}
	}
}
