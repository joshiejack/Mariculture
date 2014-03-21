package mariculture.magic.jewelry.parts;

import mariculture.core.Core;
import mariculture.core.lib.PearlColor;
import mariculture.core.util.Text;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class MaterialPearlBlack extends JewelryMaterial {
	@Override
	public String getColor() {
		return Text.GREY;
	}
	
	@Override
	public int onAttack(LivingAttackEvent event, EntityPlayer player) {
		if(!player.worldObj.isDaytime()) {
			if(event.entity != null) {
				event.entity.attackEntityFrom(DamageSource.magic, event.ammount * 1.25F);
				return 5;
			} else return 1;
		}
		
		return 0;
	}

	@Override
	public int getExtraEnchantments(JewelryType type) {
		return 1;
	}

	@Override
	public int getMaximumEnchantmentLevel (JewelryType type) {
		return 4;
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
		return 2.0F;
	}
	
	@Override
	public ItemStack getCraftingItem(JewelryType type) {
		return new ItemStack(Core.pearls, 1, PearlColor.BLACK);
	}
}
