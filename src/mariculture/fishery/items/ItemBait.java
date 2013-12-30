package mariculture.fishery.items;

import mariculture.api.fishery.Fishing;
import mariculture.core.items.ItemMariculture;
import mariculture.core.lib.BaitMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBait extends ItemMariculture {
	public ItemBait(int i) {
		super(i);
	}
	
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
		if (player.canEat(false)) {
			player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		}

		return stack;
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		--stack.stackSize;
		int fill = (((Fishing.bait.getEffectiveness(stack)) + 1)/2 > 0)? ((Fishing.bait.getEffectiveness(stack)) + 1)/2: 1;
		float sat = fill/5;
		player.getFoodStats().addStats(fill, sat);
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
		default:
			return "bait";
		}
	}
}
