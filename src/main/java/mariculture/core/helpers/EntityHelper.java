package mariculture.core.helpers;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

public class EntityHelper {

	public static boolean isInWater(EntityLivingBase entity) {
	    double d0 = entity.posY - 0.35F;
	    int i = MathHelper.floor_double(entity.posX);
	    int j = MathHelper.floor_float((float)MathHelper.floor_double(d0));
	    int k = MathHelper.floor_double(entity.posZ);
	    int l = entity.worldObj.getBlockId(i, j, k);
	
	    Block block = Blocks.blocksList[l];
	    if (block != null && block.blockMaterial == Material.water)
	    {
	        double filled = 1;
	        if (filled < 0) {
	            filled *= -1;
	            return d0 > (double)(j + (1 - filled));
	        }
	        else
	        {
	            return d0 < (double)(j + filled);
	        }
	    }
	    else
	    {
	        return false;
	    }
	}

	public static boolean isInAir(EntityLivingBase entity) {
	    double d0 = entity.posY - 0.35F;
	    int i = MathHelper.floor_double(entity.posX);
	    int j = MathHelper.floor_float((float)MathHelper.floor_double(d0));
	    int k = MathHelper.floor_double(entity.posZ);
	    int l = entity.worldObj.getBlockId(i, j, k);
	
	    Block block = Blocks.blocksList[l];
	    if (block != null && block.blockMaterial == Material.air) {
	        double filled = 1;
	        if (filled < 0) {
	            filled *= -1;
	            return d0 > (double)(j + (1 - filled));
	        }
	        else
	        {
	            return d0 < (double)(j + filled);
	        }
	    }
	    else  {
	        return false;
	    }
	}

}
