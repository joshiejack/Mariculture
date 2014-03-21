package mariculture.magic.jewelry.parts;

import mariculture.core.Core;
import mariculture.core.lib.PearlColor;
import mariculture.core.util.Text;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class MaterialPearlRed extends JewelryMaterial {
	@Override
	public String getColor() {
		return Text.RED;
	}
	
	@Override
	public int onAttacked(LivingAttackEvent event, EntityPlayer player) {
		if(event.source == DamageSource.inFire || event.source == DamageSource.onFire || event.source == DamageSource.lava) {
			event.setCanceled(true);
			player.extinguish();
		} 
		
		return 0;
	}
	
	@Override
	public int onHurt(LivingHurtEvent event, EntityPlayer player) {
		if(event.source == DamageSource.inFire || event.source == DamageSource.onFire || event.source == DamageSource.lava) {
			event.setCanceled(true);
			player.extinguish();
			return 5;
		} else return 0;
	}

	@Override
	public int getExtraEnchantments(JewelryType type) {
		return type == JewelryType.RING? 0: type == JewelryType.BRACELET? -1: -2;
	}

	@Override
	public int getMaximumEnchantmentLevel (JewelryType type) {
		return type == JewelryType.NECKLACE? 2: 1;
	}

	@Override
	public float getRepairModifier(JewelryType type) {
		return 2.5F;
	}

	@Override
	public float getHitsModifier(JewelryType type) {
		return type == JewelryType.RING? 2.0F: 3.5F;
	}
	
	@Override
	public float getDurabilityModifier(JewelryType type) {
		return type == JewelryType.RING? 5F: 7.5F;
	}
	
	@Override
	public ItemStack getCraftingItem(JewelryType type) {
		return new ItemStack(Core.pearls, 1, PearlColor.RED);
	}
}
