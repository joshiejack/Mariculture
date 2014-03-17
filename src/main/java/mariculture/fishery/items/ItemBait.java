package mariculture.fishery.items;

import mariculture.api.fishery.Fishing;
import mariculture.core.items.ItemMariculture;
import mariculture.core.lib.BaitMeta;
import mariculture.core.util.Rand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import cpw.mods.fml.common.Loader;

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
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));

		return stack;
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		--stack.stackSize;
		int quality = Fishing.bait.getBaitQuality(stack);
		
		int fill = (int)(((double)quality/100) * 4.0D);
		float sat = -(float) (fill/5);
		//Decrease food if hunger overhaul is installed
		if(Loader.isModLoaded("HungerOverhaul")) {
			fill = Math.max(1, fill/2);
			sat = Math.max(0.0F, sat/10);
			player.addPotionEffect(new PotionEffect(Potion.hunger.id, 30, 0));
			if(Rand.nextInt(64)) player.addPotionEffect(new PotionEffect(Potion.poison.id, 100, 0));
			if(Rand.nextInt(8))  player.addPotionEffect(new PotionEffect(Potion.confusion.id, 50, 0));
		}
		
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
