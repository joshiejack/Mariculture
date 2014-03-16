package mariculture.core.blocks.base;

import mariculture.Mariculture;
import mariculture.core.blocks.BlockDecorative;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockConnected extends BlockDecorative {
	private static int[] textureRefByID = { 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13,
		13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1, 1, 18, 18, 1, 1, 13,
		13, 2, 2, 23, 31, 2, 2, 27, 14, 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 22, 26, 17, 17, 22, 26, 16, 16, 20, 20, 16,
		16, 28, 28, 21, 21, 46, 42, 21, 21, 43, 38, 4, 4, 5, 5, 4, 4, 5, 5, 9, 9, 30, 12, 9, 9, 30, 12, 16, 16, 20,
		20, 16, 16, 28, 28, 25, 25, 45, 37, 25, 25, 40, 32, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1,
		1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 0, 0, 6, 6, 0, 0, 6, 6, 3, 3, 19, 15, 3, 3, 19, 15, 1,
		1, 18, 18, 1, 1, 13, 13, 2, 2, 23, 31, 2, 2, 27, 14, 4, 4, 5, 5, 4, 4, 5, 5, 17, 17, 22, 26, 17, 17, 22,
		26, 7, 7, 24, 24, 7, 7, 10, 10, 29, 29, 44, 41, 29, 29, 39, 33, 4, 4, 5, 5, 4, 4, 5, 5, 9, 9, 30, 12, 9, 9,
		30, 12, 7, 7, 24, 24, 7, 7, 10, 10, 8, 8, 36, 35, 8, 8, 34, 11 };
	
	public BlockConnected(int i, Material material) {
		super(i, material);
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side) {
		int meta = world.getBlockMetadata(x, y, z);
		Icon[] connected = getTexture(meta);
		if(connected != null) {
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
			
			return idBuilder > 255 || idBuilder < 0 ? connected[0] : connected[textureRefByID[idBuilder]];
		}
		
		return getIcon(side, world.getBlockMetadata(x, y, z));
	}
	
	private boolean isSameBlock(IBlockAccess block, int[] coords1, int[] coords2) {
		if (block.getBlockId(coords1[0], coords1[1], coords1[2]) == this.blockID) {
			if (block.getBlockMetadata(coords1[0], coords1[1], coords1[2]) == block.getBlockMetadata(coords2[0], coords2[1], coords2[2])) {
				return true;
			}
		}
		return false;
	}
	
	public Icon getIcon(int side, int meta) {
		Icon[] connected = getTexture(meta);
		if(connected != null) {
			return connected[0]; 
		} else {
			if(meta < getMetaCount()) {
				return icons[meta];
			} else { 
				return icons[0];
			}
		}
	}
	
	public abstract Icon[] getTexture(int meta);
	public abstract void registerConnectedTextures(IconRegister iconRegister);
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount()];
		
		registerConnectedTextures(iconRegister);

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + "glass_" + getName(new ItemStack(this.blockID, 1, i)));
		}
	}
}
