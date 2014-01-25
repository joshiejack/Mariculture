package mariculture.core.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.util.IItemRegistry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMariculture extends Item implements IItemRegistry {
	@SideOnly(Side.CLIENT)
	public Icon[] icons;

	public ItemMariculture(int id) {
		super(id);
		setCreativeTab(MaricultureTab.tabMariculture);
		setHasSubtypes(true);
	}

	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(getName(new ItemStack(this.itemID, 1, j)), new ItemStack(this.itemID, 1, j));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + "." + getName(itemstack);
	}

	public String getName(ItemStack stack) {
		return "error";
	}

	public int getMetaCount() {
		return 1;
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public Icon getIconFromDamage(int meta) {
		if (meta < getMetaCount()) {
			return icons[meta];
		}

		return icons[0];
	}

	public boolean isActive(int meta) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creative, List list) {
		for (int meta = 0; meta < getMetaCount(); ++meta) {
			if (isActive(meta)) {
				list.add(new ItemStack(id, 1, meta));
			} 
		}
		
		return;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			if(isActive(i)) {
				icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this.itemID, 1, i)));
			}
		}
	}
}
