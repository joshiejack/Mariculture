package mariculture.plugins.hungryfish;

import mariculture.core.items.ItemFood;
import mariculture.core.lib.FoodMeta;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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
		case FoodMeta.CAVIAR:
			return 2;
		case FoodMeta.CUSTARD:
			return 1;
		case FoodMeta.FISH_N_CUSTARD:
			return 3;
		case FoodMeta.KELP_WRAP:
			return 1;
		case FoodMeta.SUSHI:
			return 3;
		case FoodMeta.MISO_SOUP:
			return 5;
		case FoodMeta.OYSTER:
			return 8;
		default:
			return 1;
		}
	}

	private float getFoodSaturation(int dmg) {
		switch (dmg) {
		case FoodMeta.FISH_FINGER:
			return 0.025F;
		case FoodMeta.CALAMARI:
			return 0.1F;
		case FoodMeta.SMOKED_SALMON:
			return 0.15F;
		case FoodMeta.CAVIAR:
			return 0.05F;
		case FoodMeta.CUSTARD:
			return 0.015F;
		case FoodMeta.FISH_N_CUSTARD:
			return 0.1F;
		case FoodMeta.KELP_WRAP:
			return 0.025F;
		case FoodMeta.SUSHI:
			return 0.08F;
		case FoodMeta.MISO_SOUP:
			return 0.2F;
		case FoodMeta.OYSTER:
			return 0.8F;
		default:
			return 0.3F;
		}
	}
	
	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		--stack.stackSize;
		player.getFoodStats().addStats(getFoodLevel(stack.getItemDamage()), getFoodSaturation(stack.getItemDamage()));
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		return stack;
	}
}
