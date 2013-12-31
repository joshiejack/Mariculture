package mariculture.core.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mariculture.api.core.MaricultureRegistry;
import mariculture.core.Mariculture;
import mariculture.core.lib.GuideMeta;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ItemGuide extends ItemMariculture {
	public ItemGuide(int id) {
		super(id);
	}
	
	public String getName(ItemStack stack) {
		switch(stack.getItemDamage()) {
			case GuideMeta.FISHING:
				return "fishing";
			default:
				return "guide";
		}
	}
	
	@Override
	public int getMetaCount() {
		return GuideMeta.COUNT;
	}
	
	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(getName(new ItemStack(this.itemID, 1, j)) + "Guide", new ItemStack(this.itemID, 1, j));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack) {
		return getUnlocalizedName() + ".guide." + getName(itemstack);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			if(isActive(i)) {
				icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this.itemID, 1, i)) + "Guide");
			}
		}
	}
}
