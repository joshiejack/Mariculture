package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.core.lib.PearlColor;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPearlBlock extends BlockDecorative {
	public String name;
	public BlockPearlBlock() {
		super(Material.rock);
		setResistance(20F);
		setHardness(2F);
	}
	
	public Block setBlockName(String name) {
		super.setBlockName(name);
		this.name = name;
        return this;
    }
	
	@Override
	public String getToolType(int meta) {
		return "pickaxe";
	}

	@Override
	public int getToolLevel(int meta) {
		return 1;
	}
	
	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register(name + "." + getName(new ItemStack(this, 1, j)), new ItemStack(this, 1, j));
		}
	}
	
	@Override
	public int getMetaCount() {
		return PearlColor.COUNT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icons = new IIcon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + name + "_" + getName(new ItemStack(this, 1, i)));
		}
	}
}
