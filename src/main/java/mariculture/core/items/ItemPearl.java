package mariculture.core.items;

import mariculture.core.lib.PearlColor;
import net.minecraft.item.ItemStack;

public class ItemPearl extends ItemMariculture {

	public ItemPearl(final int i) {
		super(i);
	}

	@Override
	public int getMetaCount() {
		return PearlColor.COUNT;
	}

	@Override
	public String getName(ItemStack stack) {
		String name = "";
		switch (stack.getItemDamage()) {
		case PearlColor.WHITE:
			return "pearlWhite";
		case PearlColor.GREEN:
			return "pearlGreen";
		case PearlColor.YELLOW:
			return "pearlYellow";
		case PearlColor.ORANGE:
			return "pearlOrange";
		case PearlColor.RED:
			return "pearlRed";
		case PearlColor.GOLD:
			return "pearlGold";
		case PearlColor.BROWN:
			return "pearlBrown";
		case PearlColor.PURPLE:
			return "pearlPurple";
		case PearlColor.BLUE:
			return "pearlBlue";
		case PearlColor.BLACK:
			return "pearlBlack";
		case PearlColor.PINK:
			return "pearlPink";
		case PearlColor.SILVER:
			return "pearlSilver";
		default:
			return "pearl";
		}
	}
}
