package mariculture.fishery.items;

import mariculture.api.fishery.Fishing;
import mariculture.core.items.ItemMariculture;
import mariculture.core.lib.BaitMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBait extends ItemMariculture {	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 8;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.eat;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));

		return stack;
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		--stack.stackSize;
		int quality = Fishing.bait.getBaitQuality(stack);
		double fill = ((double)quality/100) * 4.0D;
		float sat = -(float) (fill/5);
		player.getFoodStats().addStats(((fill >= 1) ? (int) fill: 1), sat);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

		return stack;
	}
	
	@Override
	public int getMetaCount() {
		return BaitMeta.COUNT;
	}

	@Override
	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case BaitMeta.WORM:
			return "worm";
		case BaitMeta.ANT:
			return "ant";
		case BaitMeta.MAGGOT:
			return "maggot";
		case BaitMeta.HOPPER:
			return "hopper";
		case BaitMeta.BEE:
			return "bee";
		default:
			return "bait";
		}
	}
}
