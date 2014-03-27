package mariculture.api.fishery;

import java.util.List;

import mariculture.core.util.Rand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBaseRod extends ItemFishingRod {
	protected final RodQuality quality;

	/**
	 * Any new rods you wish to add must extend this in order for it be
	 * recognised by Mariculture
	 **/
	public ItemBaseRod(int i, RodQuality quality) {
		super(i);
		this.setMaxStackSize(1);
		this.setMaxDamage(quality.getMaxUses());
		this.quality = quality;
	}
	
	@Override
	public int getItemEnchantability() {
		return quality.getEnchantability();
	}

	public RodQuality getQuality() {
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
	
	//This is called by the right click handler to check whether this rod is able tofish at this location, player can be null
	public boolean canFish(World world, int x, int y, int z, EntityPlayer player, ItemStack stack) {
		return true;
	}
	
	//This method is called when ever this item needs to be damaged, it can have various effects, player can be null
	public ItemStack damage(EntityPlayer player, ItemStack stack, int fish) {
		if(player != null) stack.damageItem(fish, player);
		else {
			if(stack.attemptDamageItem(1, Rand.rand)) {
				stack = null;
			}
		}
		
		return stack;
	}
}
