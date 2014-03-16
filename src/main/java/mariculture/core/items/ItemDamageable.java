package mariculture.core.items;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.util.IItemRegistry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDamageable extends Item implements IItemRegistry {
	public ItemDamageable(int i, int dmg) {
		super(i);
		setCreativeTab(MaricultureTab.tabMariculture);
		setMaxStackSize(1);
		setMaxDamage(dmg);
	}
	
	@Override
	public void register() {
		MaricultureRegistry.register(getName(new ItemStack(this.itemID, 1, 0)), new ItemStack(this.itemID, 1, 0));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this.itemID, 1, 0)));
	}

	@Override
	public int getMetaCount() {
		return 1;
	}

	@Override
	public String getName(ItemStack stack) {
		return this.getUnlocalizedName().substring(5);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creative, List list) {
		list.add(new ItemStack(id, 1, 0));
	}
}
