package mariculture.core.blocks;

import java.util.List;
import java.util.Random;

import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Core;
import mariculture.core.Mariculture;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.OresMeta;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.SingleMeta;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTransparent extends BlockDecorative {
	private static Icon[] plastic = new Icon[47];
	private static Icon[] heatglass = new Icon[47];

	private static int[] textureRefByID = { 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13,
			13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13,
			13, 2, 2, 23, 31, 2, 2, 27, 14, 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 22, 26, 17, 17, 22, 26, 16, 16, 20, 20, 16,
			16, 28, 28, 21, 21, 46, 42, 21, 21, 43, 38, 4, 4, 5, 5, 4, 4, 5, 5, 9, 9, 30, 12, 9, 9, 30, 12, 16, 16, 20,
			20, 16, 16, 28, 28, 25, 25, 45, 37, 25, 25, 40, 32, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1,
			1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1,
			1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 22, 26, 17, 17, 22,
			26, 7, 7, 24, 24, 7, 7, 10, 10, 29, 29, 44, 41, 29, 29, 39, 33, 4, 4, 5, 5, 4, 4, 5, 5, 9, 9, 30, 12, 9, 9,
			30, 12, 7, 7, 24, 24, 7, 7, 10, 10, 8, 8, 36, 35, 8, 8, 34, 11 };

	public BlockTransparent(int i) {
		super(i, Material.glass);
		this.setHardness(0.5F);
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
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        int i1 = par1IBlockAccess.getBlockId(par2, par3, par4);
        return i1 == this.blockID ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }

	//Thanks to f1rSt1k25 for the Code.
	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == GlassMeta.PLASTIC || meta == GlassMeta.HEAT) {
			boolean[] bitMatrix = new boolean[8];
	
			if (side == 0 || side == 1) {
				bitMatrix[0] = isSameBlock(world, new int[] { x - 1, y, z - 1 }, new int[] { x, y, z });
				bitMatrix[1] = isSameBlock(world, new int[] { x, y, z - 1 }, new int[] { x, y, z });
				bitMatrix[2] = isSameBlock(world, new int[] { x + 1, y, z - 1 }, new int[] { x, y, z });
				bitMatrix[3] = isSameBlock(world, new int[] { x - 1, y, z }, new int[] { x, y, z });
				bitMatrix[4] = isSameBlock(world, new int[] { x + 1, y, z}, new int[] { x, y, z });
				bitMatrix[5] = isSameBlock(world, new int[] { x - 1, y, z + 1 }, new int[] { x, y, z });
				bitMatrix[6] = isSameBlock(world, new int[] { x, y, z + 1 }, new int[] { x, y, z });
				bitMatrix[7] = isSameBlock(world, new int[] { x + 1, y, z + 1 }, new int[] { x, y, z });
			}
			if (side == 2 || side == 3) {
				bitMatrix[0] = isSameBlock(world, new int[] { x + (side == 2 ? 1 : -1), y + 1, z }, new int[] { x, y, z });
				bitMatrix[1] = isSameBlock(world, new int[] { x, y + 1, z }, new int[] { x, y, z });
				bitMatrix[2] = isSameBlock(world, new int[] { x + (side == 3 ? 1 : -1), y + 1, z }, new int[] { x, y, z });
				bitMatrix[3] = isSameBlock(world, new int[] { x + (side == 2 ? 1 : -1), y, z }, new int[] { x, y, z });
				bitMatrix[4] = isSameBlock(world, new int[] { x + (side == 3 ? 1 : -1), y, z }, new int[] { x, y, z });
				bitMatrix[5] = isSameBlock(world, new int[] { x + (side == 2 ? 1 : -1), y - 1, z }, new int[] { x, y, z });
				bitMatrix[6] = isSameBlock(world, new int[] { x, y - 1, z }, new int[] { x, y, z });
				bitMatrix[7] = isSameBlock(world, new int[] { x + (side == 3 ? 1 : -1), y - 1, z }, new int[] { x, y, z });
			}
			if (side == 4 || side == 5) {
				bitMatrix[0] = isSameBlock(world, new int[] { x, y + 1, z + (side == 5 ? 1 : -1) }, new int[] { x, y, z });
				bitMatrix[1] = isSameBlock(world, new int[] { x, y + 1, z }, new int[] { x, y, z });
				bitMatrix[2] = isSameBlock(world, new int[] { x, y + 1, z + (side == 4 ? 1 : -1) }, new int[] { x, y, z });
				bitMatrix[3] = isSameBlock(world, new int[] { x, y, z + (side == 5 ? 1 : -1) }, new int[] { x, y, z });
				bitMatrix[4] = isSameBlock(world, new int[] { x, y, z + (side == 4 ? 1 : -1) }, new int[] { x, y, z });
				bitMatrix[5] = isSameBlock(world, new int[] { x, y - 1, z + (side == 5 ? 1 : -1) }, new int[] { x, y, z });
				bitMatrix[6] = isSameBlock(world, new int[] { x, y - 1, z }, new int[] { x, y, z });
				bitMatrix[7] = isSameBlock(world, new int[] { x, y - 1, z + (side == 4 ? 1 : -1) }, new int[] { x, y, z });
			}
	
			int idBuilder = 0;
	
			for (int i = 0; i <= 7; i++)
				idBuilder = idBuilder
						+ (bitMatrix[i] ? (i == 0 ? 1 : (i == 1 ? 2 : (i == 2 ? 4 : (i == 3 ? 8 : (i == 4 ? 16
								: (i == 5 ? 32 : (i == 6 ? 64 : 128))))))) : 0);
	
			if(meta == GlassMeta.PLASTIC)
				return idBuilder > 255 || idBuilder < 0 ? plastic[0] : plastic[textureRefByID[idBuilder]];
			if(meta == GlassMeta.HEAT)
				return idBuilder > 255 || idBuilder < 0 ? heatglass[0] : heatglass[textureRefByID[idBuilder]];
		}
		
		return getIcon(side, world.getBlockMetadata(x, y, z));
	}

	public Icon getIcon(int side, int meta) {
		if(meta == GlassMeta.PLASTIC) {
			return plastic[0]; 
		} else if(meta == GlassMeta.HEAT) {
			return heatglass[0]; 
		} else {
			if(meta < getMetaCount()) {
				return icons[meta];
			} else { 
				return icons[0];
			}
		}
	}

	private boolean isSameBlock(IBlockAccess block, int[] coords1, int[] coords2) {
		if (block.getBlockId(coords1[0], coords1[1], coords1[2]) == this.blockID) {
			if (block.getBlockMetadata(coords1[0], coords1[1], coords1[2]) == block.getBlockMetadata(coords2[0], coords2[1], coords2[2])) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register("glass." + getName(new ItemStack(this.blockID, 1, j)), new ItemStack(this.blockID, 1, j));
		}
	}

	@Override
	public int getMetaCount() {
		return GlassMeta.COUNT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount()];
		
		for (int i = 0; i < 47; i++) {
			plastic[i] = iconRegister.registerIcon(Mariculture.modid + ":plastic/glass_plastic_" + (i + 1));
			heatglass[i] = iconRegister.registerIcon(Mariculture.modid + ":heatglass/glass_plastic_" + (i + 1));
		}

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + "glass_" + getName(new ItemStack(this.blockID, 1, i)));
		}
	}
}