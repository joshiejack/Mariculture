package mariculture.sealife;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntitySealifeHostile extends EntitySealife {

	protected int predator;
	
	public EntitySealifeHostile(World world) {
		super(world);
	}
	
	/*
	public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && this.worldObj.difficultySetting > 0;
    }
	
	
	public Entity getTarget() {
		if ((this.worldObj.difficultySetting > 0)) {
			EntityPlayer player = this.worldObj.getClosestVulnerablePlayerToEntity(this, getRange());
			if ((player != null) && (player.isInWater())) {
				return player;
			}
			if(predator > 0) {
				if (rand.nextInt(predator) == 0) {
					EntityLivingBase entity = getWildTarget();
					if ((entity != null) && (entity.isInWater())) {
						return entity;
					}
				}
			}
		}
		return null;
	}

	private EntityLivingBase getWildTarget() {
		double d1 = -1.0D;
		EntityLivingBase entity = null;
		List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, 
				this.boundingBox.expand(getRange(), getRange(), getRange()));
		for (int i = 0; i < list.size(); i++) {
			Entity entity1 = (Entity) list.get(i);
			if ((entity1 instanceof EntityPlayer && !((EntityPlayer)entity1).capabilities.isCreativeMode) 
					|| entity1 instanceof EntityAnimal) {
				double d2 = entity1.getDistanceSq(this.posX, this.posY, this.posZ);
				if (((getRange() < 0.0D) || (d2 < getRange() * getRange())) && ((d1 == -1.0D) || (d2 < d1))
						&& (((EntityLivingBase) entity1) .canEntityBeSeen(this))) {
					d1 = d2;
					entity = (EntityLivingBase) entity1;
				}
			}
		}
		
		return entity;
	}
*/
}
