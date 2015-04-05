package mariculture.magic;

import mariculture.core.helpers.EnchantHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class MagicEntityEvents {
    private static final AttributeCustom blink = new AttributeCustom("blink", 3D);

    private static final int spider = 1;

    private static class AttributeCustom implements IAttribute {
        private final String name;
        private final double max;

        public AttributeCustom(String name, double max) {
            this.name = name;
            this.max = max;
        }

        @Override
        public String getAttributeUnlocalizedName() {
            return "blink";
        }

        @Override
        public double clampValue(double value) {
            return value >= max ? max : value <= 0 ? 0D : value;
        }

        @Override
        public double getDefaultValue() {
            return 3D;
        }

        @Override
        public boolean getShouldWatch() {
            return false;
        }
    }

    @SubscribeEvent
    public void onEntitySpawn(EntityJoinWorldEvent event) {
        if (!(event.entity instanceof EntityMob)) return;
        EntityMob mob = (EntityMob) event.entity;

        if (EnchantHelper.exists(Magic.blink)) 
            //mob.getAttributeMap().registerAttribute(blink);
        if (EnchantHelper.exists(Magic.speed)) return;
            //mob.getEntityAttribute(SharedMonsterAttributes.movementSpeed).applyModifier(new AttributeModifier());
            
    }

    @SubscribeEvent
    public void onAttacked(LivingAttackEvent event) {
        if (!(event.entity instanceof EntityMob)) return;
        EntityMob mob = (EntityMob) event.entity;
        if (mob.getEntityAttribute(blink) != null) {
            if (event.source.getSourceOfDamage() instanceof EntityPlayer) {
                mob.setPosition(event.source.getSourceOfDamage().posX + (mob.worldObj.rand.nextDouble() * 4D), mob.posY, event.source.getSourceOfDamage().posZ + (mob.worldObj.rand.nextDouble() * 4D));
            } else mob.setPosition(mob.posX + mob.worldObj.rand.nextDouble() * 16D, mob.posY, mob.posZ + mob.worldObj.rand.nextDouble() * 16D);
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (!(event.entity instanceof EntityMob)) return;
        EntityLivingBase entity = event.entityLiving;

        //if (effect == spider) {
        if (entity.isCollidedHorizontally && !entity.isOnLadder()) {
            final float factor = 0.15F;

            if (entity.motionX < -factor) {
                entity.motionX = -factor;
            }

            if (entity.motionX > factor) {
                entity.motionX = factor;
            }

            if (entity.motionZ < -factor) {
                entity.motionZ = -factor;
            }

            if (entity.motionZ > factor) {
                entity.motionZ = factor;
            }

            entity.fallDistance = 0.0F;

            if (entity.motionY < -0.14999999999999999D) {
                entity.motionY = -0.14999999999999999D;
            }

            entity.motionY = 0.20000000000000001D;
        }
        //}
    }
}
