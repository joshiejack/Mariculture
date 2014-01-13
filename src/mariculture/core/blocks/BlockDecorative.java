package mariculture.core.blocks;

import java.util.List;
import java.util.Random;

import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.Mariculture;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDecorative extends Block implements IItemRegistry {
	@SideOnly(Side.CLIENT)
	protected Icon[] icons;
	
	public BlockDecorative(int i, Material material) {
		super(i, material);
		this.setCreativeTab(MaricultureTab.tabMariculture);
	}
	
	public boolean isActive(int meta) {
		return true;
	}

	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(getName(new ItemStack(this.blockID, 1, j)), new ItemStack(this.blockID, 1, j));
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
	public int idDropped(int i, Random random, int j) {
		return this.blockID;
	}
	
	@Override
	public int damageDropped(int i) {
		return i;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creative, List list) {
		for (int meta = 0; meta < getMetaCount(); ++meta) {
			if (isActive(meta)) {
				list.add(new ItemStack(id, 1, meta));
			}
		}
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
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
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this.blockID, 1, i)));
		}
	}
}
