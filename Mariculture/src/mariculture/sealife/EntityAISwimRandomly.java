package mariculture.sealife;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class EntityAISwimRandomly extends EntityAIBase {
	Random rand = new Random();
	private EntityLiving theEntity;
	private int courseChangeCooldown;
    private double waypointX;
    private double waypointY;
    private double waypointZ;
	
	public EntityAISwimRandomly(EntityLiving entity) {
        this.theEntity = entity;
        this.setMutexBits(1);
    }
	
	public boolean shouldExecute() {
        return this.theEntity.isInWater() || this.theEntity.handleLavaMovement();
    }
	
	public void updateTask() {
		double d0 = waypointX - theEntity.posX;
        double d1 = waypointY - theEntity.posY;
        double d2 = waypointZ - theEntity.posZ;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
    	
	    if (d3 < 1.0D || d3 > 3600.0D) {
	    	waypointX = theEntity.posX + (double)((rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
	        waypointY = theEntity.posY + (double)((rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
	        waypointZ = theEntity.posZ + (double)((rand.nextFloat() * 2.0F - 1.0F) * 16.0F);
    	}

        if (courseChangeCooldown-- <= 0) {
            this.courseChangeCooldown += this.rand.nextInt(5) + 2;
            d3 = (double)MathHelper.sqrt_double(d3);

            if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, d3)) {
            	theEntity.motionX += d0 / d3 * 0.1D;
            	theEntity.motionY += d1 / d3 * 0.1D;
            	theEntity.motionZ += d2 / d3 * 0.1D; 
            } else {
                this.waypointX = theEntity.posX;
                this.waypointY = theEntity.posY;
                this.waypointZ = theEntity.posZ;
            }
        }

        theEntity.renderYawOffset = theEntity.rotationYaw = -((float)Math.atan2(theEntity.motionX, theEntity.motionZ)) * 180.0F / (float)Math.PI;
    }
	
	private boolean isCourseTraversable(double x, double y, double z, double par7) {
    	if(theEntity.worldObj.getBlockMaterial((int)x, (int)y, (int)z) != Material.water) {
    		return false;
    	}
    	
        double d4 = (this.waypointX - theEntity.posX) / par7;
        double d5 = (this.waypointY - theEntity.posY) / par7;
        double d6 = (this.waypointZ - theEntity.posZ) / par7;
        AxisAlignedBB axisalignedbb = theEntity.boundingBox.copy();

        for (int i = 1; (double)i < par7; ++i) {
            axisalignedbb.offset(d4, d5, d6);

            if (!theEntity.worldObj.getCollidingBoundingBoxes(theEntity, axisalignedbb).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
