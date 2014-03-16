package mariculture.magic.enchantments;

import mariculture.core.helpers.EnchantHelper;
import mariculture.magic.Magic;
import mariculture.magic.MirrorData;
import mariculture.magic.jewelry.parts.JewelryMaterial;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EnchantmentElemental extends EnchantmentJewelry {
	public EnchantmentElemental(int i, int weight, EnumEnchantmentType type) {
		super(i, weight, type);
		setName("elemental");
		minLevel = 35;
		maxLevel = 50;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	public static void onHurt(LivingHurtEvent event, EntityPlayer player) {
		if(EnchantHelper.hasEnchantment(Magic.elemental, player)) {
			for(JewelryMaterial material: MirrorData.getMaterials(player)) {
				material.onHurt(event, player);
			}
		}
	}

	public static boolean hasElement(int enchant, int type, ItemStack stack) {
		if(!EnchantHelper.exists(Magic.elemental))
			return false;
		if(enchant == Magic.elemental.effectId) {
			if(stack.hasTagCompound()) {
				if(stack.stackTagCompound.getInteger("Part1") == type) {
					return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
}
