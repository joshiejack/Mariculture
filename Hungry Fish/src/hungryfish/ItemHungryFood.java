package hungryfish;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import mariculture.core.items.ItemFood;
import mariculture.core.lib.FoodMeta;

public class ItemHungryFood extends ItemFood {
	public ItemHungryFood(int i) {
		super(i);
	}

	private int getFoodLevel(int dmg) {
		switch (dmg) {
		case FoodMeta.FISH_FINGER:
			return 2;
		case FoodMeta.CALAMARI:
			return 2;
		case FoodMeta.SMOKED_SALMON:
			return 3;
		default:
			return 1;
		}
	}

	private float getFoodSaturation(int dmg) {
		switch (dmg) {
		case FoodMeta.FISH_FINGER:
			return 0.05F;
		case FoodMeta.CALAMARI:
			return 0.1F;
		case FoodMeta.SMOKED_SALMON:
			return 0.15F;
		default:
			return 0.3F;
		}
	}
	
	@Override
	public ItemStack onEaten(final ItemStack stack, final World world, final EntityPlayer player) {
		--stack.stackSize;

		player.getFoodStats().addStats(getFoodLevel(stack.getItemDamage()), getFoodSaturation(stack.getItemDamage()));

		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

		return stack;
	}
}
