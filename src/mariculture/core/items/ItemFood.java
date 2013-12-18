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

	@Override
	public int getMaxItemUseDuration(final ItemStack stack) {
		switch (stack.getItemDamage()) {
		case FoodMeta.FISH_FINGER:
			return 16;
		default:
			return 32;
		}
	}

	@Override
	public EnumAction getItemUseAction(final ItemStack par1ItemStack) {
		return EnumAction.eat;
	}

	@Override
	public ItemStack onItemRightClick(final ItemStack stack, final World world, final EntityPlayer player) {
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
		String name = "";
		switch (stack.getItemDamage()) {
		case FoodMeta.FISH_FINGER: {
			name = "fishFinger";
			break;
		}
		case FoodMeta.CALAMARI: {
			name = "calamari";
			break;
		}
		case FoodMeta.SMOKED_SALMON: {
			name = "smokedSalmon";
			break;
		}

		default:
			name = "unnamed";
		}

		return name;
	}

	@Override
	public boolean isActive(int meta) {
		switch (meta) {
		case FoodMeta.CALAMARI:
			return (Modules.fishery.isActive());
		case FoodMeta.FISH_FINGER:
			return (Modules.fishery.isActive());
		case FoodMeta.SMOKED_SALMON:
			return (Modules.fishery.isActive());

		default:
			return true;
		}
	}
}
