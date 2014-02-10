package mariculture.core.lib;

import mariculture.factory.Factory;
import mariculture.factory.blocks.TileCustom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockWall;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cofh.api.energy.IEnergyHandler;

public class PlansMeta {
	public static final int COUNT = 9;
	public static final int FLOOR = 0;
	public static final int BLOCK = 1;
	public static final int STAIRS = 2;
	public static final int SLABS = 3;
	public static final int FENCE = 4;
	public static final int GATE = 5;
	public static final int WALL = 6;
	public static final int LIGHT = 7;
	public static final int RF = 8;

	public static int getType(ItemStack stack) {
		
		
		if (stack.hasTagCompound()) {
			return stack.stackTagCompound.getInteger("type");
		}

		return 0;
	}

	public static ItemStack setType(ItemStack stack, int type) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}

		stack.stackTagCompound.setInteger("type", type);

		return stack;
	}

	public static ItemStack getBlockStack(int type) {
		switch (type) {
		case PlansMeta.FLOOR:
			return new ItemStack(Factory.customFlooring);
		case PlansMeta.BLOCK:
			return new ItemStack(Factory.customBlock);
		case PlansMeta.STAIRS:
			return new ItemStack(Factory.customStairs);
		case PlansMeta.SLABS:
			return new ItemStack(Factory.customSlabs);
		case PlansMeta.FENCE:
			return new ItemStack(Factory.customFence);
		case PlansMeta.GATE:
			return new ItemStack(Factory.customGate);
		case PlansMeta.WALL:
			return new ItemStack(Factory.customWall);
		case PlansMeta.LIGHT:
			return new ItemStack(Factory.customLight);
		case PlansMeta.RF:
			return new ItemStack(Factory.customRFBlock);
		}

		return null;
	}
	
	public static int getPlanType(Block block, World world, int x, int y, int z) {
		if(block instanceof BlockFence) {
			return PlansMeta.FENCE;
		} else if(block instanceof BlockFenceGate) {
			return PlansMeta.GATE;
		} else if(block instanceof BlockSlab) {
			return PlansMeta.SLABS;
		} else if(block instanceof BlockStairs) {
			return PlansMeta.STAIRS;
		} else if(block instanceof BlockWall) {
			return PlansMeta.WALL;
		} else if(block instanceof BlockCarpet) {
			return PlansMeta.FLOOR;
		} else if(block.getLightValue(world, x, y, z) > 0F) {
			return PlansMeta.LIGHT;
		} else if(world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof IEnergyHandler) {
			return PlansMeta.RF;
		} else if(world.getTileEntity(x, y, z) == null && !world.isAirBlock(x, y, z)) {
			return PlansMeta.BLOCK;
		}
		
		return -1;
	}

	public static boolean isSame(ItemStack stack, ItemStack result) {
		if (stack.hasTagCompound() && result.hasTagCompound()) {
			int[] ids1 = stack.stackTagCompound.getIntArray("BlockIDs");
			int[] ids2 = result.stackTagCompound.getIntArray("BlockIDs");
			int[] meta1 = stack.stackTagCompound.getIntArray("BlockMetas");
			int[] meta2 = result.stackTagCompound.getIntArray("BlockMetas");
			String name1 = stack.stackTagCompound.getString("Name");
			String name2 = result.stackTagCompound.getString("Name");
			if (name1.equals(name2)) {
				int ids = 0;
				int metas = 0;
				for(int i = 0; i < 6; i++) {
					if(ids1[i] == ids2[i]) {
						ids++;
					}
					
					if(meta1[i] == meta2[i]) {
						metas++;
					}
				}
				if (ids == 6 && metas == 6) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isTheSame(World world, int x, int y, int z, ItemStack stack) {
		if(world.getTileEntity(x, y, z) != null) {
			if(world.getTileEntity(x, y, z) instanceof TileCustom) {
				TileCustom tile = (TileCustom) world.getTileEntity(x, y, z);
				ItemStack ret = new ItemStack(tile.getBlockType());
				ret.setTagCompound(new NBTTagCompound());
				ret.stackTagCompound.setIntArray("BlockIDs", tile.theBlockIDs());
				ret.stackTagCompound.setIntArray("BlockMetas", tile.theBlockMetas());
				ret.stackTagCompound.setString("Name", tile.name());
				
				return isSame(ret, stack);
			}
		}
		
		return false;
	}
}
