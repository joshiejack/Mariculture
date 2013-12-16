package hungryfish;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import mariculture.api.fishery.Fishing;
import mariculture.fishery.items.ItemFishyFood;

public class ItemHungryFish extends ItemFishyFood {

	public ItemHungryFish(int i) {
		super(i);
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		--stack.stackSize;

		Fishing.fishHelper.getSpecies(stack.getItemDamage()).onConsumed(world, player);

		return stack;
	}
}
