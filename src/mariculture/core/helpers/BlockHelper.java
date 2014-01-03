package mariculture.core.helpers;

import java.util.Random;

import mariculture.core.blocks.base.TileMulti;
import mariculture.core.util.IItemDropBlacklist;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockHelper {

	public static boolean isWater(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		
		return id == Block.waterStill.blockID || id == Block.waterMoving.blockID;
	}
	
	public static boolean isLava(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		
		return id == Block.lavaStill.blockID || id == Block.lavaMoving.blockID;
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
	
	public static void setBlock(World world, int x, int y, int z, int id, int meta) {
        if (world.getChunkProvider().chunkExists(x >> 4, z >> 4)) {
        	try {
        		world.setBlock(x, y, z, id, meta, 3);
            } catch(Exception e){ }
        }
	}
	
	public static int getID(World world, int x, int y, int z) {
		 if (world.getChunkProvider().chunkExists(x >> 4, z >> 4)) {
			 try {
				 return world.getBlockId(x, y, z);
			 } catch (Exception e) { }
		 }
		 
		 return -1;
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
			return Item.itemsList[stack.itemID].getItemDisplayName(stack);
		}
		
		return "";
	}
	
	public static String getName(TileEntity tile) {
		if(tile == null) {
			return "";
		}
				
		int id = tile.getBlockType().blockID;
		if(Item.itemsList[id] != null) {
			Item block = Item.itemsList[id];
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
		Random rand = new Random();
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);
	
		if (!(tile_entity instanceof IInventory)) {
			return;
		}
	
		IInventory inventory = (IInventory) tile_entity;
	
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			boolean drop = true;
			if(tile_entity instanceof IItemDropBlacklist) {
				drop = ((IItemDropBlacklist)tile_entity).doesDrop(i);
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
			
			if(tile_entity instanceof TileMulti) {
				TileMulti tile = (TileMulti) tile_entity;
				if(!tile.isMaster()) {
					world.destroyBlock(tile.mstr.x, tile.mstr.y, tile.mstr.z, true);
				}
			}
		}
	}
}
