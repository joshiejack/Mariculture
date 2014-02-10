package mariculture.factory.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.core.items.ItemMariculture;
import mariculture.core.lib.PlansMeta;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPlan extends ItemMariculture {
	public ItemPlan(int i) {
		super(i);
		this.setMaxDamage(16);
		this.setMaxStackSize(1);
		setHasSubtypes(false);
	}

	public int getStackSize(ItemStack stack) {
		switch (PlansMeta.getType(stack)) {
		case PlansMeta.FLOOR:
			return 9;
		case PlansMeta.BLOCK:
			return 6;
		case PlansMeta.STAIRS:
			return 4;
		case PlansMeta.SLABS:
			return 12;
		case PlansMeta.FENCE:
			return 6;
		case PlansMeta.GATE:
			return 2;
		case PlansMeta.WALL:
			return 6;
		case PlansMeta.LIGHT:
			return 6;
		case PlansMeta.RF:
			return 1;
		default:
			return 1;
		}
	}
	
	@Override
	public void register() {
		for(int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(getName(PlansMeta.setType(new ItemStack(this.itemID, 1, 0), j)), PlansMeta.setType(new ItemStack(this.itemID, 1, 0), j));
		}
	}

	@Override
	public int getMetaCount() {
		return PlansMeta.COUNT;
	}

	@Override
	public String getName(ItemStack stack) {		
		String name = "";
		if (stack.hasTagCompound()) {
			switch (PlansMeta.getType(stack)) {
			case PlansMeta.FLOOR: {
				name = "floor";
				break;
			}
			case PlansMeta.BLOCK: {
				name = "block";
				break;
			}
			case PlansMeta.STAIRS: {
				name = "stairs";
				break;
			}
			case PlansMeta.SLABS: {
				name = "slabs";
				break;
			}
			case PlansMeta.FENCE: {
				name = "fence";
				break;
			}
			case PlansMeta.GATE: {
				name = "gate";
				break;
			}
			case PlansMeta.WALL: {
				name = "wall";
				break;
			}
			case PlansMeta.LIGHT: {
				name = "light";
				break;
			}
			case PlansMeta.RF: {
				name ="redstone";
				break;
			}

			default:
				name = "unnamed";
			}
		}

		return "plan_" + name;

	}

	@Override
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public IIcon getIconFromDamage(int dmg) {
		return icons[0];
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if (stack.hasTagCompound()) {
			return icons[PlansMeta.getType(stack)];
		}

		return icons[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int j, CreativeTabs creative, List list) {
		for (int i = 0; i < getMetaCount(); ++i) {
			list.add(PlansMeta.setType(new ItemStack(j, 1, 0), i));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(PlansMeta.setType((new ItemStack(this.itemID, 1, 0)), i)));
		}
	}
}
