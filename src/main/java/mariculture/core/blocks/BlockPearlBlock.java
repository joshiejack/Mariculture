package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.core.lib.PearlColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPearlBlock extends BlockDecorative {
	public BlockPearlBlock() {
		super(Material.rock);
		this.setResistance(20F);
		this.setHardness(2F);
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
			MaricultureRegistry.register("pearlBrick." + getName(new ItemStack(this, 1, j)), new ItemStack(this, 1, j));
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
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + "pearlBrick_" + getName(new ItemStack(this, 1, i)));
		}
	}
}
