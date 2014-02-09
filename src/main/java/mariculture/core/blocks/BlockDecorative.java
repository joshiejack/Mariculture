package mariculture.core.blocks;

import java.util.List;
import java.util.Random;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecorative extends Block implements IItemRegistry {
	@SideOnly(Side.CLIENT)
	protected IIcon[] icons;
	
	public BlockDecorative(Material material) {
		super(material);
		this.setCreativeTab(MaricultureTab.tabMariculture);
	}
	
	public boolean isActive(int meta) {
		return true;
	}

	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(getName(new ItemStack(this, 1, j)), new ItemStack(this, 1, j));
		}
	}

	@Override
	public int getMetaCount() {
		return 1;
	}

	@Override
	public String getName(ItemStack stack) {
		return RegistryHelper.getName(stack);
	}
	
	@Override
	public int damageDropped(int i) {
		return i;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creative, List list) {
		for (int meta = 0; meta < getMetaCount(); ++meta) {
			if (isActive(meta)) {
				list.add(new ItemStack(item, 1, meta));
			}
		}
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		if(blockIcon != null)
			return blockIcon;
		if(meta < getMetaCount()) {
			return icons[meta];
		} else { 
			return icons[0];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons = new IIcon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this, 1, i)));
		}
	}
}
