package mariculture.core.items;

import mariculture.core.lib.FoodMeta;
import mariculture.core.lib.Modules;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFood extends ItemMariculture {
	public ItemFood(int i) {
		super(i);
	}

	private int getFoodLevel(int dmg) {
		switch (dmg) {
		case FoodMeta.FISH_FINGER:
			return 2;
		case FoodMeta.CALAMARI:
			return 6;
		case FoodMeta.SMOKED_SALMON:
			return 10;
		case FoodMeta.CAVIAR:
			return 5;
		case FoodMeta.CUSTARD:
			return 1;
		case FoodMeta.FISH_N_CUSTARD:
			return 4;
		case FoodMeta.KELP_WRAP:
			return 2;
		case FoodMeta.SUSHI:
			return 6;
		case FoodMeta.MISO_SOUP:
			return 7;
		default:
			return 1;
		}
	}

	private float getFoodSaturation(int dmg) {
		switch (dmg) {
		case FoodMeta.FISH_FINGER:
			return 0.5F;
		case FoodMeta.CALAMARI:
			return 0.85F;
		case FoodMeta.SMOKED_SALMON:
			return 1F;
		case FoodMeta.CAVIAR:
			return 0.6F;
		case FoodMeta.CUSTARD:
			return 0.3F;
		case FoodMeta.FISH_N_CUSTARD:
			return 0.9F;
		case FoodMeta.KELP_WRAP:
			return 0.1F;
		case FoodMeta.SUSHI:
			return 0.7F;
		case FoodMeta.MISO_SOUP:
			return 1.2F;
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

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case FoodMeta.FISH_FINGER:
			return 16;
		case FoodMeta.FISH_N_CUSTARD:
			return 48;
		case FoodMeta.MISO_SOUP:
			return 64;
		default:
			return 32;
		}
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
	public int getMetaCount() {
		return FoodMeta.COUNT;
	}

	@Override
	public String getName(ItemStack stack) {
		switch (stack.getItemDamage()) {
		case FoodMeta.FISH_FINGER:
			return "fishFinger";
		case FoodMeta.CALAMARI:
			return "calamari";
		case FoodMeta.SMOKED_SALMON:
			return "smokedSalmon";
		case FoodMeta.CAVIAR:
			return "caviar";
		case FoodMeta.CUSTARD:
			return "custard";
		case FoodMeta.FISH_N_CUSTARD:
			return "fishNCustard";
		case FoodMeta.KELP_WRAP:
			return "kelpWrap";
		case FoodMeta.SUSHI:
			return "sushi";
		case FoodMeta.MISO_SOUP:
			return "misoSoup";
		default:
			return "food";
		}
	}

	@Override
	public boolean isActive(int meta) {
		if (meta < 7) {
			return Modules.fishery.isActive();
		}

		return Modules.world.isActive();
	}
}
