package mariculture.magic.jewelry.parts;

import mariculture.core.Core;
import mariculture.core.lib.PearlColor;
import mariculture.core.util.Text;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class MaterialPearlPurple extends JewelryMaterial {
	@Override
	public String getColor() {
		return Text.PURPLE;
	}
	
	@Override
	public int onAttacked(LivingAttackEvent event, EntityPlayer player) {
		if(event.source == DamageSource.magic) {
			if(!player.isPotionActive(Potion.poison)) {
				event.setCanceled(true);
			}
		} 
		
		return 0;
	}
	
	@Override
	public int onHurt(LivingHurtEvent event, EntityPlayer player) {
		if(event.source == DamageSource.magic) {
			if(!player.isPotionActive(Potion.poison)) {
				event.setCanceled(true);
			}
			
			return 80;
		} else return 0;
	}

	@Override
	public int getExtraEnchantments(JewelryType type) {
		return 1;
	}

	@Override
	public int getMaximumEnchantmentLevel (JewelryType type) {
		return 5;
	}

	@Override
	public float getRepairModifier(JewelryType type) {
		return 1.5F;
	}

	@Override
	public float getHitsModifier(JewelryType type) {
		return 0.75F;
	}
	
	@Override
	public float getDurabilityModifier(JewelryType type) {
		return 1.0F;
	}
	
	@Override
	public ItemStack getCraftingItem(JewelryType type) {
		return new ItemStack(Core.pearls, 1, PearlColor.PURPLE);
	}
}
