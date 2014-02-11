package mariculture.core.helpers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import mariculture.core.Core;
import mariculture.core.blocks.base.TileMultiBlock;
import mariculture.core.util.IItemDropBlacklist;
import mariculture.core.util.Rand;
import mariculture.fishery.blocks.TileFishTank;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHelper {

	public static boolean isWater(World world, int x, int y, int z) {
		return world.getBlock(x, y, z) == Blocks.water;
	}
	
	public static boolean isHPWater(World world, int x, int y, int z) {
		return world.getBlock(x, y, z) == Core.highPressureWaterBlock;
	}
	
	public static boolean isLava(World world, int x, int y, int z) {
		return world.getBlock(x, y, z) == Blocks.lava;
	}
	
	public static boolean isFishLiveable(World world, int x, int y, int z) {
		if(world.provider.isHellWorld)
			return isLava(world, x, y, z);
		return isWater(world, x, y, z);
	}
	
	public static boolean isFishable(World world, int x, int y, int z) {
		return isWater(world, x, y, z) || (isLava(world, x, y, z) && world.provider.isHellWorld);
	}
	
	public static boolean isAir(World world, int x, int y, int z) {
		if (world.getChunkProvider().chunkExists(x >> 4, z >> 4)) {
        	try {
        		return world.isAirBlock(x, y, z);
            } catch(Exception e){ }
        }
		
		return false;
	}
	
	public static void setBlock(World world, int x, int y, int z, Block block, int meta) {
        if (world.getChunkProvider().chunkExists(x >> 4, z >> 4)) {
        	try {
        		world.setBlock(x, y, z, block, meta, 3);
            } catch(Exception e){ }
        }
	}
	
	public static Block getBlock(World world, int x, int y, int z) {
		 if (world.getChunkProvider().chunkExists(x >> 4, z >> 4)) {
			 try {
				 return world.getBlock(x, y, z);
			 } catch (Exception e) { }
		 }
		 
		 return null;
	}
	
	public static int getMeta(World world, int x, int y, int z) {
		if (world.getChunkProvider().chunkExists(x >> 4, z >> 4)) {
			 try {
				 return world.getBlockMetadata(x, y, z);
			 } catch (Exception e) { }
		 }
		 
		 return -1;
	}
	
	public static boolean chunkExists(World world, int x, int z) {
		return world.getChunkProvider().chunkExists(x >> 4, z >> 4);
	}

	public static String getName(ItemStack stack) {
		if(stack != null) {
			return Items.itemsList[stack.itemID].getItemDisplayName(stack);
		}
		
		return "";
	}
	
	public static String getName(TileEntity tile) {
		if(tile == null) {
			return "";
		}
				
		int id = tile.getBlockType().blockID;
		if(Items.itemsList[id] != null) {
			Item block = Items.itemsList[id];
			return StatCollector.translateToLocal(block.getUnlocalizedName(new ItemStack(id, 1, tile.getBlockMetadata())) + ".name");
		}
		
		return "";
	}
	
	public static ForgeDirection rotate(ForgeDirection dir) {
		if(dir == ForgeDirection.NORTH)
			return ForgeDirection.EAST;
		if(dir == ForgeDirection.EAST)
			return ForgeDirection.SOUTH;
		if(dir == ForgeDirection.SOUTH)
			return ForgeDirection.WEST;
		if(dir == ForgeDirection.WEST)
			return ForgeDirection.UP;
		if(dir == ForgeDirection.UP)
			return ForgeDirection.DOWN;
		return ForgeDirection.NORTH;
	}

	public static void dropItems(World world, int x, int y, int z) {
		Random rand = Rand.rand;
		TileEntity tile = world.getTileEntity(x, y, z);
	
		if (!(tile instanceof IInventory)) {
			return;
		}
		
		if(tile instanceof TileMultiBlock) {
			TileMultiBlock multi = (TileMultiBlock) tile;			
			if(multi.master != null) {				
				if(!multi.isMaster())
					return;
			}
		}
			
		IInventory inventory = (IInventory) tile;
	
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			boolean drop = true;
			if(tile instanceof IItemDropBlacklist) {
				drop = ((IItemDropBlacklist)tile).doesDrop(i);
			}
			
			if(drop) {
				ItemStack item = inventory.getStackInSlot(i);
	
				if (item != null && item.stackSize > 0) {
					float rx = rand.nextFloat() * 0.6F + 0.1F;
					float ry = rand.nextFloat() * 0.6F + 0.1F;
					float rz = rand.nextFloat() * 0.6F + 0.1F;
	
					EntityItem entity_item = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.itemID,
							item.stackSize, item.getItemDamage()));
	
					if (item.hasTagCompound()) {
						entity_item.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
					}
	
					float factor = 0.05F;
	
					entity_item.motionX = rand.nextGaussian() * factor;
					entity_item.motionY = rand.nextGaussian() * factor + 0.2F;
					entity_item.motionZ = rand.nextGaussian() * factor;
					world.spawnEntityInWorld(entity_item);
					item.stackSize = 0;
				}
			}
		}
	}

	public static void dropFish(World world, int x, int y, int z) {
		Random rand = Rand.rand;
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileFishTank) {
			HashMap fish = ((TileFishTank) tile).fish;
			Iterator it = fish.entrySet().iterator();
			
			 while (it.hasNext()) {
			 	 Map.Entry pairs = (Map.Entry)it.next();
			     ItemStack stack = (ItemStack) pairs.getValue();
			     
			     if (stack != null && stack.stackSize > 0) {
					float rx = rand.nextFloat() * 0.6F + 0.1F;
					float ry = rand.nextFloat() * 0.6F + 0.1F;
					float rz = rand.nextFloat() * 0.6F + 0.1F;
		
					EntityItem entity_item = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(stack.itemID,
							stack.stackSize, stack.getItemDamage()));
		
					if (stack.hasTagCompound()) {
						entity_item.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
					}
		
					float factor = 0.05F;
		
					entity_item.motionX = rand.nextGaussian() * factor;
					entity_item.motionY = rand.nextGaussian() * factor + 0.2F;
					entity_item.motionZ = rand.nextGaussian() * factor;
					world.spawnEntityInWorld(entity_item);
					stack.stackSize = 0;
				}
			 }
		}
		
		dropItems(world, x, y, z);
	}
}
