package mariculture.fishery;

import mariculture.api.fishery.Fishing;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

public class EntityAIAvoidCatfish extends EntityAIBase {
	/** The entity we are attached to */
	private EntityCreature theEntity;
	private float farSpeed;
	private float nearSpeed;
	private Entity closestLivingEntity;
	private float distanceFromEntity;

	/** The PathEntity of our entity */
	private PathEntity entityPathEntity;

	/** The PathNavigate of our entity */
	private PathNavigate entityPathNavigate;

	/** The class of the entity we should avoid */
	private Class targetEntityClass;

	public EntityAIAvoidCatfish(EntityCreature par1EntityCreature, Class par2Class, float par3, float par4, float par5) {
		this.theEntity = par1EntityCreature;
		this.targetEntityClass = par2Class;
		this.distanceFromEntity = par3;
		this.farSpeed = par4;
		this.nearSpeed = par5;
		this.entityPathNavigate = par1EntityCreature.getNavigator();
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute() {
		if (this.targetEntityClass == EntityPlayer.class) {
			this.closestLivingEntity = this.theEntity.worldObj.getClosestPlayerToEntity(this.theEntity, this.distanceFromEntity);

			if (this.closestLivingEntity == null) {
				return false;
			}

			if (closestLivingEntity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) closestLivingEntity;
				if (!player.inventory
						.hasItemStack(new ItemStack(Fishery.fishyFood, 1, Fishery.catfish.fishID))) {
					return false;
				}
			}
		}

		if (!this.theEntity.getEntitySenses().canSee(this.closestLivingEntity)) {
			return false;
		} else {
			Vec3 var2 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(
					this.theEntity,
					16,
					7,
					this.theEntity.worldObj.getWorldVec3Pool().getVecFromPool(this.closestLivingEntity.posX,
							this.closestLivingEntity.posY, this.closestLivingEntity.posZ));

			if (var2 == null) {
				return false;
			} else if (this.closestLivingEntity.getDistanceSq(var2.xCoord, var2.yCoord, var2.zCoord) < this.closestLivingEntity
					.getDistanceSqToEntity(this.theEntity)) {
				return false;
			} else {
				this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(var2.xCoord, var2.yCoord, var2.zCoord);
				return this.entityPathEntity == null ? false : this.entityPathEntity.isDestinationSame(var2);
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean continueExecuting() {
		return !this.entityPathNavigate.noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting() {
		this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask() {
		this.closestLivingEntity = null;
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask() {
		if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0D) {
			this.theEntity.getNavigator().setSpeed(this.nearSpeed);
		} else {
			this.theEntity.getNavigator().setSpeed(this.farSpeed);
		}
	}
}
