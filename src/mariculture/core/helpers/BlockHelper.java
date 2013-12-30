package mariculture.core.helpers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

	public static String getName(ItemStack stack) {
		if(stack != null) {
			return Item.itemsList[stack.itemID].getItemDisplayName(stack);
		}
		
		return "";
	}

	public static boolean chunkExists(World world, int x, int z) {
		return world.getChunkProvider().chunkExists(x >> 4, z >> 4);
	}
}
