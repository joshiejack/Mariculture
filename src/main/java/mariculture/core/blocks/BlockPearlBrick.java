package mariculture.core.blocks;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.core.lib.PearlColor;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPearlBrick extends BlockDecorative {
	public BlockPearlBrick(final int i) {
		super(i, Material.rock);
		this.setResistance(20F);
		this.setHardness(2F);
	}
	
	@Override
	public void register() {
		for (int j = 0; j < this.getMetaCount(); j++) {
			MaricultureRegistry.register("pearlBrick." + getName(new ItemStack(this.blockID, 1, j)), new ItemStack(this.blockID, 1, j));
		}
	}
	
	@Override
	public int getMetaCount() {
		return PearlColor.COUNT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + "pearlBrick_" + getName(new ItemStack(this.blockID, 1, i)));
		}
	}
}
