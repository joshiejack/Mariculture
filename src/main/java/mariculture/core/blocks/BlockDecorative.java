package mariculture.core.blocks;

import java.util.List;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.core.helpers.RegistryHelper;
import mariculture.core.util.IHasMeta;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockDecorative extends Block implements IHasMeta {
	@SideOnly(Side.CLIENT)
	protected IIcon[] icons;
	protected String prefix;
	
	public BlockDecorative(Material material) {
		super(material);
		setCreativeTab(MaricultureTab.tabMariculture);
		for(int i = 0; i < getMetaCount(); i++) {
			setHarvestLevel(getToolType(i), getToolLevel(i), i);
		}
	}
	
	public abstract String getToolType(int meta);
	public abstract int getToolLevel(int meta);
	
	public abstract int getMetaCount();
	public boolean isActive(int meta) {
		return true;
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
		if(blockIcon != null) return blockIcon;
		if(meta < getMetaCount()) {
			return icons[meta] != null? icons[meta]: Blocks.air.getIcon(side, meta);
		} else { 
			return icons[0] != null? icons[0]: Blocks.air.getIcon(side, meta);
		}
	}
	
	protected String getName(int i) {
		return ((IItemRegistry)Item.getItemFromBlock(this)).getName(new ItemStack(this, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		String name = prefix != null? prefix: "";
		icons = new IIcon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + name + getName(i));
		}
	}

	@Override
	public Class<? extends ItemBlock> getItemClass() {
		return null;
	}
}
