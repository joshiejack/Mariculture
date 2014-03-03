package mariculture.core.lib;

import mariculture.factory.Factory;
import mariculture.factory.blocks.BlockCustomHelper;
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
	
	public static boolean matches(NBTTagCompound nbt1, NBTTagCompound nbt2) {
		Block[] blocks1 = new Block[6];
		Block[] blocks2 = new Block[6];
		float resist1 = nbt1.getFloat("BlockResistance");
		float resist2 = nbt2.getFloat("BlockResistance");
		float hardness1 = nbt1.getFloat("BlockHardness");
		float hardness2 = nbt2.getFloat("BlockHardness");
		int[] theBlockMetas1 = nbt1.getIntArray("BlockMetas");
		int[] theBlockMetas2 = nbt2.getIntArray("BlockMetas");
		int[] theSides1 = nbt1.getIntArray("BlockSides");
		int[] theSides2 = nbt2.getIntArray("BlockSides");
		
		if(resist1 != resist2 || hardness1 != hardness2) {
			return false;
		}
		
		for(int i = 0; i < 6; i++) {
			blocks1[i] = (Block) Block.blockRegistry.getObject(nbt1.getString("BlockIdentifier" + i));
			blocks2[i] = (Block) Block.blockRegistry.getObject(nbt2.getString("BlockIdentifier" + i));
			if(blocks1[i] != blocks2[i])
				return false;
			if(theBlockMetas1[i] != theBlockMetas2[i] || theSides1[i] != theSides2[i])
				return false;
		}
		
		return true;
	}
}
