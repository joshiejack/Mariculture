package mariculture.world;

import mariculture.Mariculture;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.Modules;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import rebelkeithy.mods.aquaculture.items.ItemFish;

public class EntityRockhopper extends EntityTameable {
    public short rotationBody = 0;
    public short rotationWing = 570;
    public boolean upWing = false;
    public boolean upBody = false;

    public EntityRockhopper(World world) {
        super(world);
        setSize(0.3F, 0.7F);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(2, aiSit);
        tasks.addTask(2, new EntityAIMate(this, 1.0D));
        tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.fish, false));
        tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
        tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
        tasks.addTask(5, new EntityAIWander(this, 1.0D));
        tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        tasks.addTask(7, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntitySquid.class, 750, false));
        setTamed(false);
        stepHeight = 0.25F;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.15000001192092896D);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 10.0F);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (worldObj.isRemote) {
            if (this.posX != this.prevPosX || this.posZ != this.prevPosZ) {
                if (upWing) {
                    if (rotationWing < 575) rotationWing++;
                    else upWing = false;
                } else {
                    if (rotationWing > 570) rotationWing--;
                    else upWing = true;
                }

                if (upBody) {
                    if (rotationBody < 6) rotationBody += 2;
                    else upBody = false;
                } else {
                    if (rotationBody > -6) rotationBody -= 2;
                    else upBody = true;
                }
            } else {
                if (rotationWing > 578D) {
                    rotationWing--;
                } else if (rotationWing < 578D) {
                    rotationWing++;
                }

                if (rotationBody > 0) {
                    rotationBody--;
                } else if (rotationBody < 0) {
                    rotationBody++;
                }
            }
        }
    }
    
    @Override
    protected String getLivingSound() {
        return Mariculture.modid + ":rockhopper_ambient";
    }
    
    @Override
    protected String getDeathSound() {
        return Mariculture.modid + ":rockhopper_death";
    }

    @Override
    protected String getHurtSound() {
        return Mariculture.modid + ":rockhopper_hurt";
    }

    @Override
    public boolean attackEntityFrom(DamageSource soruce, float damage) {
        if (this.isEntityInvulnerable()) {
            return false;
        } else {
            Entity entity = soruce.getEntity();
            this.aiSit.setSitting(false);

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
                damage = (damage + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(soruce, damage);
        }
    }

    @Override
    public boolean interact(EntityPlayer player) {
        ItemStack itemstack = player.inventory.getCurrentItem();

        if (this.isTamed()) {
            if (itemstack != null) {
                if (itemstack.getItem() instanceof ItemFish) {
                    this.heal(5F);

                    if (itemstack.stackSize <= 0) {
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
                    }

                    return true;
                }
            }

            if (this.func_152114_e(player) && !this.worldObj.isRemote && !this.isBreedingItem(itemstack)) {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.setPathToEntity((PathEntity) null);
                this.setTarget((Entity) null);
                this.setAttackTarget((EntityLivingBase) null);
            }
        }

        return super.interact(player);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }

    @Override
    protected boolean canDespawn() {
        return !this.isTamed() && this.ticksExisted > 2400;
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable) {
        return new EntityRockhopper(worldObj);
    }

    @Override
    public boolean isAIEnabled() {
        return true;
    }

    @Override
    protected Item getDropItem() {
        return Items.fish;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() instanceof ItemFish || stack.getItem() instanceof ItemFishy;
    }

    @Override
    protected void dropFewItems(boolean playerKill, int looting) {
        int j = this.rand.nextInt(3) + this.rand.nextInt(1 + looting);

        if (j > 0) {
            if (Modules.isActive(Modules.fishery)) {
                ItemStack loot = Fishing.fishing.getCatch(worldObj, (int) posX, (int) posY, (int) posZ, null, null);
                if (loot.getItem() == Items.fish) {
                    this.entityDropItem(loot, 1F);
                } else if (loot.getItem() instanceof ItemFishy) {
                    FishSpecies species = Fishing.fishHelper.getSpecies(loot);
                    this.entityDropItem(new ItemStack(Items.fish, j, species.getID()), 1F);
                }
            } else {
                this.entityDropItem(new ItemStack(Items.fish, j, rand.nextInt(4)), 1F);
            }
        } else this.entityDropItem(new ItemStack(Items.fish, j, rand.nextInt(4)), 1F);

        if (playerKill && j > 1) {
            if (j > 2) {
                this.entityDropItem(new ItemStack(Core.crafting, 1, CraftingMeta.NEOPRENE), 1F);
            } else {
                if (Modules.isActive(Modules.fishery)) {
                    this.entityDropItem(Fishing.fishing.getCatch(worldObj, (int) posX, (int) posY, (int) posZ, null, null), 1F);
                } else this.entityDropItem(new ItemStack(Items.fish, j, rand.nextInt(4)), 1F);
            }
        }
    }
}
