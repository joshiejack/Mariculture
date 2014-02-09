package mariculture.diving;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.util.IItemRegistry;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemArmorSnorkel extends ItemArmor implements IItemRegistry, IDisablesHardcoreDiving {
	public ItemArmorSnorkel(int i, EnumArmorMaterial material, int j, int k) {
		super(i, material, j, k);
		this.setCreativeTab(MaricultureTab.tabMariculture);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, int layer) {
		return "mariculture:" + "textures/armor/snorkel.png";
	}

	@Override
	public void registerIcons(IconRegister iconRegister) {
		this.itemIcon = iconRegister.registerIcon(Mariculture.modid + ":" + (this.getUnlocalizedName().substring(5)));
	}

	@Override
	public int getMetaCount() {
		return 1;
	}

	@Override
	public String getName(ItemStack stack) {
		return stack.getUnlocalizedName().substring(5);
	}

	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(getName(new ItemStack(this.itemID, 1, j)), new ItemStack(this.itemID, 1, j));
		}
	}
}