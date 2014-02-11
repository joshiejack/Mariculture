package mariculture.api.fishery;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBaseRod extends ItemFishingRod {
	private final EnumRodQuality quality;

	/**
	 * Any new rods you wish to add must extend this in order for it be
	 * recognised by Mariculture
	 **/
	public ItemBaseRod(EnumRodQuality quality) {
		this.setMaxStackSize(1);
		this.setMaxDamage(quality.getMaxUses());
		this.quality = quality;
	}
	
	@Override
	public int getItemEnchantability() {
		return quality.getEnchantability();
	}

	public EnumRodQuality getQuality() {
		return this.quality;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return Fishing.rodHandler.handleRightClick(stack, world, player, this.quality, itemRand);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) {
		Fishing.rodHandler.addBaitList(list, this.quality);
	}
}
