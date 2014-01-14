package mariculture.core.blocks;

import java.util.Random;

import mariculture.core.Mariculture;
import mariculture.core.blocks.base.TileMultiBlock;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.FluidHelper;
import mariculture.core.helpers.SpawnItemHelper;
import mariculture.core.helpers.cofh.ItemHelper;
import mariculture.core.lib.DoubleMeta;
import mariculture.core.lib.Modules;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.UtilMeta;
import mariculture.core.util.IHasGUI;
import mariculture.diving.TileAirCompressor;
import mariculture.factory.blocks.TilePressureVessel;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.FakePlayer;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDouble extends BlockMachine {
	public Icon bar1;
	public Icon bar2;

	public BlockDouble(int i) {
		super(i, Material.iron);
		setLightOpacity(0);
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
		if (tile == null) {
			return false;
		}
		
		if(tile instanceof TileMultiBlock && tile instanceof IHasGUI) {
			if(player.isSneaking())
				return false;
			TileMultiBlock multi = (TileMultiBlock) tile;
			if(multi.master != null) {
				player.openGui(Mariculture.instance, -1, world, multi.master.xCoord, multi.master.yCoord, multi.master.zCoord);
				return true;
			}
			
			return false;
		}
		
		if(tile instanceof TileVat) {
			TileVat vat = (TileVat) tile;
			ItemStack held = player.getCurrentEquippedItem();
			ItemStack input = vat.getStackInSlot(0);
			ItemStack output = vat.getStackInSlot(1);
			if(FluidHelper.isFluidOrEmpty(player.getCurrentEquippedItem())) {
				return FluidHelper.handleFillOrDrain((IFluidHandler) world.getBlockTileEntity(x, y, z), player, ForgeDirection.UP);
			}
			
			if(output != null) {
				if (!player.inventory.addItemStackToInventory(vat.getStackInSlot(1))) {
					if(!world.isRemote) {
						SpawnItemHelper.spawnItem(world, x, y + 1, z, vat.getStackInSlot(1));
					}
				}
				
				vat.setInventorySlotContents(1, null);
				
				return true;
			} else if(player.isSneaking() && input != null) {				
				if (!player.inventory.addItemStackToInventory(vat.getStackInSlot(0))) {
					if(!world.isRemote) {
						SpawnItemHelper.spawnItem(world, x, y + 1, z, vat.getStackInSlot(0));
					}
				}
				
				vat.setInventorySlotContents(0, null);
								
				return true;
			} else if(held != null && !player.isSneaking()) {
				if(input == null) {
					if(!world.isRemote) {
						ItemStack copy = held.copy();
						copy.stackSize = 1;
						vat.setInventorySlotContents(0, copy);
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
					}
					
					return true;
				} else if(ItemHelper.areItemStacksEqualNoNBT(input, held)) {
					if(input.stackSize + 1 < input.getMaxStackSize()) {
						if(!world.isRemote) {
							ItemStack stack = input.copy();
							stack.stackSize++;
							vat.setInventorySlotContents(0, stack);
							player.inventory.decrStackSize(player.inventory.currentItem, 1);
						}
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		
		return false;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
		int meta = block.getBlockMetadata(x, y, z);
		switch (meta) {
		case DoubleMeta.COMPRESSOR_TOP:
			setBlockBounds(0.05F, 0F, 0.05F, 0.95F, 0.15F, 0.95F);
			break;
		case DoubleMeta.COMPRESSOR_BASE:
			setBlockBounds(0.05F, 0F, 0.05F, 0.95F, 1F, 0.95F);
			break;
		default:
			setBlockBounds(0F, 0F, 0F, 1F, 0.95F, 1F);
		}
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		if(world.getBlockMetadata(x, y, z) == DoubleMeta.VAT) {
			return AxisAlignedBB.getAABBPool().getAABB((double) x + this.minX, (double) y + this.minY,		
					(double) z + this.minZ, (double) x + this.maxX, (double) y + 0.50001F, (double) z + this.maxZ);
		}
		
		return AxisAlignedBB.getAABBPool().getAABB((double) x + this.minX, (double) y + this.minY,		
			(double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
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
		case DoubleMeta.VAT:
			return new TileVat();
		}

		return null;
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int i, int meta) {
		BlockHelper.dropItems(world, x, y, z);
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if(tile instanceof TileMultiBlock) {
			((TileMultiBlock)tile).onBlockBreak();
		}
		
		super.breakBlock(world, x, y, z, i, meta);
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
