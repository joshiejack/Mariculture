package mariculture.factory.blocks;

import mariculture.core.lib.PlansMeta;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockCustomHelper {
	public static Icon getBlockTexture(IBlockAccess block, int x, int y, int z, int side) {
		TileCustom tile = (TileCustom) block.getBlockTileEntity(x, y, z);

		if (tile != null) {
			try {
				return Block.blocksList[tile.theBlockIDs(side)].getIcon(side, tile.theBlockMetas(side));
			} catch (Exception e) {
			}
		}

		return Block.stone.getIcon(side, 0);
	}
	
	public static void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		if (stack.hasTagCompound()) {
			int[] id = stack.stackTagCompound.getIntArray("BlockIDs");
			int[] meta = stack.stackTagCompound.getIntArray("BlockMetas");
			String name = stack.stackTagCompound.getString("Name");
			TileCustom tile = (TileCustom) world.getBlockTileEntity(x, y, z);
			if (tile != null) {
				tile.set(id, meta, name);
			}
		}
	}
	
	public static boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
		TileCustom tile = (TileCustom) world.getBlockTileEntity(x, y, z);
		if (tile != null) {
			if (axis == ForgeDirection.EAST || axis == ForgeDirection.WEST || axis == ForgeDirection.NORTH || axis == ForgeDirection.SOUTH) {
				int[] ids = new int[] 	{ tile.theBlockIDs(0), tile.theBlockIDs(1), tile.theBlockIDs(4), 
										  tile.theBlockIDs(5), tile.theBlockIDs(3), tile.theBlockIDs(2) };
				int[] metas = new int[] { tile.theBlockMetas(0), tile.theBlockMetas(1), tile.theBlockMetas(4),
										  tile.theBlockMetas(5), tile.theBlockMetas(3), tile.theBlockMetas(2) };
				tile.set(ids, metas, tile.name());
				return true;
			} else if (axis == ForgeDirection.DOWN || axis == ForgeDirection.UP) {
				int[] ids = new int[] { 	tile.theBlockIDs(1), tile.theBlockIDs(0), tile.theBlockIDs(2), 
											tile.theBlockIDs(3), tile.theBlockIDs(4), tile.theBlockIDs(5) };
				int[] metas = new int[] { 	tile.theBlockMetas(1), tile.theBlockMetas(0), tile.theBlockMetas(2),
											tile.theBlockMetas(3), tile.theBlockMetas(4), tile.theBlockMetas(5) };
				tile.set(ids, metas, tile.name());
				return true;
			}
		}

		return false;
	}
	
	public static float getBlockHardness(World world, int x, int y, int z) {
		float hardness = 0F;

		if (world.getBlockTileEntity(x, y, z) != null && world.getBlockTileEntity(x, y, z) instanceof TileCustom) {
			TileCustom tile = (TileCustom) world.getBlockTileEntity(x, y, z);
			if (tile.size() == 6) {
				for (int i = 0; i < 6; i++) {
					if (Block.blocksList[tile.theBlockIDs(i)] != null) {
						hardness += Block.blocksList[tile.theBlockIDs(i)].blockHardness;
					}
				}
			}
		}

		hardness /= 6F;

		return (hardness > 0F) ? hardness : 0.1F;
	}
	
	public static float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		float resistance = 0F;

		if (world.getBlockTileEntity(x, y, z) != null && world.getBlockTileEntity(x, y, z) instanceof TileCustom) {
			TileCustom tile = (TileCustom) world.getBlockTileEntity(x, y, z);
			if (tile.size() == 6) {
				for (int i = 0; i < 6; i++) {
					if (Block.blocksList[tile.theBlockIDs(i)] != null) {
						resistance = resistance + (Block.blocksList[tile.theBlockIDs(i)].blockResistance / 3);
					}
				}
			}
		}

		resistance /= 6F;

		return (resistance > 0F) ? resistance : 0.1F;
	}
	
	
	public static boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z, int plan) {
		if (!player.capabilities.isCreativeMode) {
			TileCustom tile = (TileCustom) world.getBlockTileEntity(x, y, z);
			ItemStack drop = PlansMeta.getBlockStack(plan);
			if (!drop.hasTagCompound()) {
				drop.setTagCompound(new NBTTagCompound());
			}

			if (tile != null) {
				drop.stackTagCompound.setIntArray("BlockIDs", tile.theBlockIDs());
				drop.stackTagCompound.setIntArray("BlockMetas", tile.theBlockMetas());
				drop.stackTagCompound.setString("Name", tile.name());
			}

			dropBlock(world, x, y, z, drop);
		}
		
		return world.setBlockToAir(x, y, z);
	}
	
	private static void dropBlock(World world, int x, int y, int z, ItemStack stack) {
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            float f = 0.7F;
            double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, stack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }

	public static ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, int plan) {
		TileCustom tile = (TileCustom) world.getBlockTileEntity(x, y, z);
		ItemStack drop = PlansMeta.getBlockStack(plan);
		if (!drop.hasTagCompound()) {
			drop.setTagCompound(new NBTTagCompound());
		}

		if (tile != null) {
			drop.stackTagCompound.setIntArray("BlockIDs", tile.theBlockIDs());
			drop.stackTagCompound.setIntArray("BlockMetas", tile.theBlockMetas());
			drop.stackTagCompound.setString("Name", tile.name());
		}

        return drop;
	}
}
