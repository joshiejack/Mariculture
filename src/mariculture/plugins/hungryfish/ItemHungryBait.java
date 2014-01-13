package mariculture.plugins.hungryfish;

import mariculture.api.fishery.Fishing;
import mariculture.fishery.items.ItemBait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemHungryBait extends ItemBait {
	public ItemHungryBait(int i) {
		super(i);
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		--stack.stackSize;
		int fill = (((Fishing.bait.getEffectiveness(stack)) + 1)/2 > 0)? ((Fishing.bait.getEffectiveness(stack)) + 1)/2: 1;
		float sat = (fill/5)/100;
		player.getFoodStats().addStats(1, sat);
		player.addPotionEffect(new PotionEffect(Potion.hunger.id, 20, 0));
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		return stack;
	}
}
