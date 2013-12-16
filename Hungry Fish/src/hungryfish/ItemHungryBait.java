package hungryfish;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import mariculture.api.fishery.Fishing;
import mariculture.fishery.items.ItemBait;

public class ItemHungryBait extends ItemBait {
	public ItemHungryBait(int i) {
		super(i);
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		--stack.stackSize;

		int fill = (((Fishing.bait.getEffectiveness(stack)) + 1)/2 > 0)? ((Fishing.bait.getEffectiveness(stack)) + 1)/2: 1;
		float sat = (fill/5)/10;
		
		player.getFoodStats().addStats(0, sat);

		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

		return stack;
	}
}
