package mariculture.core.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mariculture.Mariculture;
import mariculture.core.helpers.DirectionHelper;
import mariculture.core.lib.RenderIds;
import mariculture.core.render.RenderOyster;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWater extends BlockMachine {
	public BlockWater() {
		super(Material.water);
		setTickRandomly(true);
	}

	@Override
	public String getToolType(int meta) {
		return "pickaxe";
	}

	@Override
	public int getToolLevel(int meta) {
		return 0;
	}
	
	@Override
	public int getRenderType() {
		return RenderIds.RENDER_ALL;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		if(stack.getItemDamage() == 0) ((TileOyster)world.getTileEntity(x, y, z)).orientation = DirectionHelper.getFacingFromEntity(entity);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileOyster();
	}
	
	@Override
	public int getMetaCount() {
		return 1;
	}

	@Override
	public boolean isActive(int meta) {
		return true;
	}
	
	public int getMixedBrightnessForBlock(IBlockAccess world, int x, int y, int z) {
		return world.getLightBrightnessForSkyBlocks(x, y, z, 0);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		super.registerBlockIcons(iconRegister);
		
		//Register Oyster icons
		RenderOyster.lid_blur = iconRegister.registerIcon(Mariculture.modid + ":" + "oysterLidBlur");
		RenderOyster.lid = iconRegister.registerIcon(Mariculture.modid + ":" + "oysterLid");
		RenderOyster.tongue = iconRegister.registerIcon(Mariculture.modid + ":" + "oysterTongue");
	}
}
