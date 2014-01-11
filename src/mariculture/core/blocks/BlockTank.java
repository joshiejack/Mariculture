package mariculture.core.blocks;

import java.util.Iterator;
import java.util.Map;

import mariculture.core.Mariculture;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.TankMeta;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTank extends BlockDecorative {

	public BlockTank(int i) {
		super(i, Material.piston);
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return world.getBlockId(x, y, z) == this.blockID ? false : super.shouldSideBeRendered(world, x, y, z, side);
	}

	@Override
	public int getRenderType() {
		return RenderIds.BLOCK_TANKS;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float clickX, float clickY, float clickZ) {
		Map map = FluidRegistry.getRegisteredFluids();
		
		Iterator it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        System.out.println(pairs.getKey() + " = " + pairs.getValue());
	    }
		
		return FluidHelper.handleFillOrDrain((IFluidHandler) world.getBlockTileEntity(x, y, z), player);
	}

	@Override
	public boolean hasTileEntity(int meta) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return new TileTankBlock();
	}

	@Override
	public int getMetaCount() {
		return TankMeta.COUNT;
	}

	@Override
	public boolean isActive(int meta) {
		if(meta == TankMeta.FISH)
			return Modules.fishery.isActive();
		return meta != TankMeta.BOTTLE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icons = new Icon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":"
					+ getName(new ItemStack(this.blockID, 1, i)) + "Tank");
		}
	}
}
