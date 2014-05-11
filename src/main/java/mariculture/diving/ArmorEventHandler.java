package mariculture.diving;

import mariculture.core.Core;
import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import mariculture.core.lib.Extra;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

public class ArmorEventHandler {
	private UnderwaterVision vision;
	
	@ForgeSubscribe
	public void onLivingUpdate(LivingUpdateEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entity;
			if (!player.worldObj.isRemote) {
				ScubaTank.init(player);
				ScubaMask.damage(player);
				if(Extra.HARDCORE_DIVING > 0)
					HardcoreDiving.init(player);
				Snorkel.init(player);
			} else {
				vision = (vision == null)? new UnderwaterVision(): vision.onUpdate(player);
				DivingBoots.init(player);
				ScubaFin.init(player);
			}
		}
	}

	@ForgeSubscribe
	public void onBreaking(BreakSpeed event) {
		EntityPlayer player = event.entityPlayer;
		boolean isValid = false;
		
		if(ForgeHooks.canHarvestBlock(event.block, player, event.metadata)) {
			if (player.isInsideOfMaterial(Material.water)) {
				// Scuba Suit
				if (PlayerHelper.hasArmor(player, ArmorSlot.LEG, Diving.scubaSuit)) {
					event.newSpeed = event.originalSpeed * 4;
					
					if (event.block.blockID == Core.oyster.blockID) {
						event.newSpeed = event.originalSpeed * 128;
					}
				}
				
				if(PlayerHelper.hasArmor(player, ArmorSlot.FEET, Diving.swimfin) && !player.onGround) {
					event.newSpeed*=5;
				}
	
				// Diving Pants
				if (PlayerHelper.hasArmor(player, ArmorSlot.LEG, Diving.divingPants)) {
					event.newSpeed = event.originalSpeed * 2;
					if (event.block.blockID == Core.oyster.blockID) {
						event.newSpeed = event.originalSpeed * 64;
					}
				}
			}
		}
	}
}
