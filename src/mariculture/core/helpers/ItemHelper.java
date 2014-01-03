package mariculture.core.helpers;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHelper {
	public static void spawnItem(World world, int x, int y, int z, ItemStack stack) {
		spawnItem(world, x, y, z, stack, true);
	}

	public static void spawnItem(World world, int x, int y, int z, ItemStack stack, boolean random) {
		spawnItem(world, x, y, z, stack, random, 0);
	}

	public static void spawnItem(World world, int x, int y, int z, ItemStack stack, boolean random, int lifespan) {
		spawnItem(world, x, y, z, stack, random, lifespan, 10, 0);
	}
	
	public static ItemStack spawnItem(BlockTransferHelper helper, ItemStack stack) {
		return spawnItem(helper.world, helper.x, helper.y, helper.z, stack, true, 0, 10, 0.25D);
	}
	
	public static ItemStack spawnItem(World world, int x, int y, int z, ItemStack stack, boolean random, int lifespan, int delay, double motion) {
		if (!world.isRemote) {
            float f = 0.7F;
            double d0 = (random)? (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D: 0.5D;
            double d1 = (random)? (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D: 0.5D;
            double d2 = (random)? (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D: 0.5D;
            EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, stack);
            if(motion > 0D) {
	            entityitem.motionX = world.rand.nextFloat() * f * motion;
	            entityitem.motionY = world.rand.nextFloat() * f * motion;
	            entityitem.motionZ = world.rand.nextFloat() * f * motion;
            }
            
            if (lifespan > 0)
				entityitem.lifespan = lifespan;
            
            entityitem.delayBeforeCanPickup = delay;
            world.spawnEntityInWorld(entityitem);
        }
		
		return null;
	}
}
