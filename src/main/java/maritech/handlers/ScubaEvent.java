package maritech.handlers;

import mariculture.core.Core;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import mariculture.core.lib.WaterMeta;
import mariculture.lib.helpers.ClientHelper;
import maritech.extensions.modules.ExtensionDiving;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.fluids.IFluidBlock;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ScubaEvent {
	public static boolean canActivate(Material material, EntityPlayer player, int slot) {
		if (material == Material.water) return true;
		else if (material == Material.lava) {
            if(EnchantHelper.hasEnchantment(Enchantment.fireProtection, PlayerHelper.getArmorStack(player, slot))) {
            	return true;
            }
        }
		
		return false;
	}
	
    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onFogRender(FogDensity event) {
    	EntityPlayer client = ClientHelper.getPlayer();
        ScubaMask.init(client);

        if (canActivate(event.block.getMaterial(), client, ArmorSlot.HAT)) {
            if (event.entity instanceof EntityPlayer && event.entity == client) {
                if (PlayerHelper.hasArmor(client, ArmorSlot.HAT, ExtensionDiving.scubaMask)) {
                    event.density = 0.0F;
                    event.setCanceled(true);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if(event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
            ScubaFin.init((EntityPlayer) event.entity);
        }
    }
    
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
    	if(event.source == DamageSource.lava || event.source == DamageSource.onFire || event.source == DamageSource.inFire) {
    		if (event.entityLiving instanceof EntityPlayer) {
    			EntityPlayer player = (EntityPlayer) event.entityLiving;
	    		if(EnchantHelper.hasEnchantment(Enchantment.fireProtection, PlayerHelper.getArmorStack((EntityPlayer)event.entityLiving, ArmorSlot.TOP))) {
	    			if (PlayerHelper.hasArmor(player, ArmorSlot.TOP, ExtensionDiving.scubaTank)) {
	    				if (PlayerHelper.hasArmor(player, ArmorSlot.HAT, ExtensionDiving.scubaMask)) {
		    				ItemStack scuba = player.inventory.armorItemInSlot(ArmorSlot.TOP);
		    		        if (scuba.getItemDamage() < scuba.getMaxDamage()) {
		    		        	scuba.damageItem(1, player);
		    		        	player.extinguish();
		    		        	player.attackTime = 0;
		    		        	if (!player.isPotionActive(Potion.fireResistance)) {
		    		        		player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 120, 0, true));
		    		        	}
		    		        	
				    			event.setCanceled(true);
		    		        }
	    		        }
	    			}
	            }
    		}
    	}
    }
    
    public static Material getPlayerInsideOfMaterial(EntityPlayer player) {
    	double d0 = player.posY + (double)player.getEyeHeight();
        int i = MathHelper.floor_double(player.posX);
        int j = MathHelper.floor_float((float)MathHelper.floor_double(d0));
        int k = MathHelper.floor_double(player.posZ);
        Block block = player.worldObj.getBlock(i, j, k);
        double filled = 1.0f; //If it's not a liquid assume it's a solid block
        if (block instanceof IFluidBlock) {
                filled = ((IFluidBlock)block).getFilledPercentage(player.worldObj, i, j, k);
        }

        if (filled < 0) {
         	filled *= -1;
            return d0 > (double)(j + (1 - filled)) ? block.getMaterial(): Material.air;
        } else  {
        	return d0 < (double)(j + filled) ? block.getMaterial(): Material.air;
        }
    }

    @SubscribeEvent
    public void onBreaking(BreakSpeed event) {
        EntityPlayer player = event.entityPlayer;
        if (ForgeHooks.canHarvestBlock(event.block, player, event.metadata)) {
            if (canActivate(getPlayerInsideOfMaterial(player), player, ArmorSlot.LEG)) {
                // Scuba Suit
                if (PlayerHelper.hasArmor(player, ArmorSlot.LEG, ExtensionDiving.scubaSuit)) {
                    event.newSpeed = event.originalSpeed * 4;

                    if (event.block == Core.water && event.metadata == WaterMeta.OYSTER) {
                        event.newSpeed = event.originalSpeed * 128;
                    }
                }

                if (PlayerHelper.hasArmor(player, ArmorSlot.FEET, ExtensionDiving.swimfin) && !player.onGround) {
                    event.newSpeed *= 5;
                }
            }
        }
    }
}
