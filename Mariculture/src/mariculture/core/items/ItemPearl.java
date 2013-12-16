package mariculture.core.items;

import java.util.List;

import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.PearlColor;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
		case PearlColor.WHITE: {
			name = "pearlWhite";
			break;
		}
		case PearlColor.GREEN: {
			name = "pearlGreen";
			break;
		}
		case PearlColor.YELLOW: {
			name = "pearlYellow";
			break;
		}
		case PearlColor.ORANGE: {
			name = "pearlOrange";
			break;
		}
		case PearlColor.RED: {
			name = "pearlRed";
			break;
		}
		case PearlColor.GOLD: {
			name = "pearlGold";
			break;
		}
		case PearlColor.BROWN: {
			name = "pearlBrown";
			break;
		}
		case PearlColor.PURPLE: {
			name = "pearlPurple";
			break;
		}
		case PearlColor.BLUE: {
			name = "pearlBlue";
			break;
		}
		case PearlColor.BLACK: {
			name = "pearlBlack";
			break;
		}
		case PearlColor.PINK: {
			name = "pearlPink";
			break;
		}
		case PearlColor.SILVER: {
			name = "pearlSilver";
			break;
		}

		default:
			name = "pearl";
		}

		return name;

	}
}
