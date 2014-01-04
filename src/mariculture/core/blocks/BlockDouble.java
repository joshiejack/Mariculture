package mariculture.core.blocks;

import java.util.Random;

import mariculture.core.Mariculture;
import mariculture.core.blocks.base.TileMultiBlock;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.diving.TileAirCompressor;
import mariculture.factory.blocks.TilePressureVessel;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDouble extends BlockMachine {
	public Icon bar1;
	public Icon bar2;

	public BlockDouble(int i) {
		super(i, Material.iron);
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileMultiBlock) {
			((TileMultiBlock)tile).onBlockBreak();
		}
		
		super.onBlockExploded(world, x, y, z, explosion);
    }

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileMultiBlock) {
			((TileMultiBlock)tile).onBlockBreak();
		}
		
		return super.removeBlockByPlayer(world, player, x, y, z);
    }
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		switch (world.getBlockMetadata(x, y, z)) {
		case DoubleMeta.COMPRESSOR_BASE:
			return 5F;
		case DoubleMeta.COMPRESSOR_TOP:
			return 3F;
		case DoubleMeta.PRESSURE_VESSEL:
			return 6F;
		case DoubleMeta.VAT:
			return 2F;
		}

		return 3F;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if(world.getBlockMetadata(x, y, z) != DoubleMeta.PRESSURE_VESSEL)
			return true;
		
		int count = 0;

		if (world.getBlockId(x - 1, y, z) == this.blockID)
			++count;
		if (world.getBlockId(x + 1, y, z) == this.blockID)
			++count;
		if (world.getBlockId(x, y, z - 1) == this.blockID)
			++count;
		if (world.getBlockId(x, y, z + 1) == this.blockID)
			++count;
		
		return count > 1 ? false : (this.isThereSameBlock(world, x - 1, y, z) ? false : (this
				.isThereSameBlock(world, x + 1, y, z) ? false : (this.isThereSameBlock(world, x, y, z - 1) ? false : !this
				.isThereSameBlock(world, x, y, z + 1))));
	}

	private boolean isThereSameBlock(World world, int x, int y, int z) {
		return world.getBlockId(x, y, z) != this.blockID ? false : (world.getBlockId(x - 1, y, z) == this.blockID ? true : (world
				.getBlockId(x + 1, y, z) == this.blockID ? true : (world.getBlockId(x, y, z - 1) == this.blockID ? true : world
				.getBlockId(x, y, z + 1) == this.blockID)));
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileMultiBlock) {
			((TileMultiBlock)tile).onBlockPlaced();
		}
		
		super.onBlockAdded(world, x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int j, float f, float g, float t) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile == null || player.isSneaking()) {
			return false;
		}
		
		if(tile instanceof TileMultiBlock) {
			TileMultiBlock multi = (TileMultiBlock) tile;
			if(multi.master != null) {
				player.openGui(Mariculture.instance, -1, world, multi.master.xCoord, multi.master.yCoord, multi.master.zCoord);
				return true;
			}
			
			return false;
		}
		
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
		final int meta = block.getBlockMetadata(x, y, z);

		switch (meta) {
		case DoubleMeta.COMPRESSOR_TOP:
			setBlockBounds(0F, 0F, 0F, 1F, 0.15F, 1F);
			break;
		default:
			setBlockBounds(0F, 0F, 0F, 1F, 0.95F, 1F);
		}
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		switch (meta) {
		case DoubleMeta.COMPRESSOR_BASE:
			return new TileAirCompressor();
		case DoubleMeta.COMPRESSOR_TOP:
			return new TileAirCompressor();
		case DoubleMeta.PRESSURE_VESSEL:
			return new TilePressureVessel();
		}

		return null;
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
	public int getRenderType() {
		return RenderIds.BLOCK_DOUBLE;
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
	public boolean isActive(int meta) {
		switch (meta) {
		case DoubleMeta.COMPRESSOR_BASE:
			return (Modules.diving.isActive());
		case DoubleMeta.COMPRESSOR_TOP:
			return (Modules.diving.isActive());
		case DoubleMeta.PRESSURE_VESSEL:
			return (Modules.factory.isActive());
		default:
			return true;
		}
	}
	
	@Override
	public int getMetaCount() {
		return DoubleMeta.COUNT;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		bar1 = iconRegister.registerIcon(Mariculture.modid + ":bar1");
		bar2 = iconRegister.registerIcon(Mariculture.modid + ":bar2");
		
		icons = new Icon[getMetaCount()];

		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(Mariculture.modid + ":" + getName(new ItemStack(this.blockID, 1, i)));
		}
	}
}
