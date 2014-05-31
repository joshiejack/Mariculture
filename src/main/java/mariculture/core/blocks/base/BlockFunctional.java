package mariculture.core.blocks.base;

import mariculture.Mariculture;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.network.PacketHandler;
import mariculture.core.tile.base.TileMultiBlock;
import mariculture.core.util.IFaceable;
import mariculture.core.util.IHasGUI;
import mariculture.factory.tile.TileCustom;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockFunctional extends BlockDecorative {
	public BlockFunctional(Material material) {
		super(material);
	}
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		if(player.capabilities.isCreativeMode)
			return world.setBlockToAir(x, y, z);
		if(doesDrop(world.getBlockMetadata(x, y, z))) {
			return super.removedByPlayer(world, player, x, y, z);
		} else {
			return onBlockDropped(world, x, y, z);
		}
    }
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof IFaceable) {
			((IFaceable) tile).setFacing(ForgeDirection.getOrientation(BlockPistonBase.determineOrientation(world, x, y, z, entity)));
			PacketHandler.updateRender(tile);
		}
	}
	
	@Override
	public boolean rotateBlock(World world, int x, int y, int z, ForgeDirection axis) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile != null && tile instanceof IFaceable) {
			((IFaceable)tile).rotate();
		}
		
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null || player.isSneaking() || tile instanceof TileCustom) {
			return false;
		}
		
		if(tile instanceof TileMultiBlock && tile instanceof IHasGUI) {
			TileMultiBlock multi = (TileMultiBlock) tile;
			if(multi.master != null) {
				player.openGui(Mariculture.instance, -1, world, multi.master.xCoord, multi.master.yCoord, multi.master.zCoord);
				return true;
			}
			
			return false;
		}
		
		if(tile instanceof IHasGUI) {
			player.openGui(Mariculture.instance, -1, world, x, y, z);
			return true;
		}

		return true;
	}
	
	//Whether this meta should drop or not
	public boolean doesDrop(int meta) {
		return true;
	}
	
	//Called when the block is trying to be destroyed
	public boolean onBlockDropped(World world, int x, int y, int z) { 
		return world.setBlockToAir(x, y, z);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		BlockHelper.dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	@Override
	public boolean hasTileEntity(int meta) {
        return true;
    }
	
	@Override
	public abstract TileEntity createTileEntity(World world, int meta);
}
