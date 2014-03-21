package mariculture.magic.jewelry.parts;

import mariculture.core.Core;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.lib.PearlColor;
import mariculture.core.util.Rand;
import mariculture.core.util.Text;
import mariculture.magic.jewelry.ItemJewelry.JewelryType;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class MaterialPearlGold extends JewelryMaterial {
	@Override
	public String getColor() {
		return Text.YELLOW;
	}
	
	@Override
	public int onKill(LivingDeathEvent event, EntityPlayer player) {
		if(Rand.nextInt(50)) {
			SpawnItemHelper.spawnItem(event.entity.worldObj, (int)event.entity.posX, (int)event.entity.posY, (int)event.entity.posZ, new ItemStack(Items.gold_nugget));
			return 2;
		} else return 0;
	}
	
	@Override
	public int getExtraEnchantments(JewelryType type) {
		return 2;
	}

	@Override
	public int getMaximumEnchantmentLevel (JewelryType type) {
		return 5;
	}

	@Override
	public float getRepairModifier(JewelryType type) {
		return 2.0F;
	}

	@Override
	public float getHitsModifier(JewelryType type) {
		return 1.0F;
	}
	
	@Override
	public float getDurabilityModifier(JewelryType type) {
		return 0.8F;
	}
	
	@Override
	public ItemStack getCraftingItem(JewelryType type) {
		return new ItemStack(Core.pearls, 1, PearlColor.GOLD);
	}
}
