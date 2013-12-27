package mariculture.core.blocks;

import java.util.Random;

import mariculture.api.core.MaricultureTab;
import mariculture.core.Mariculture;
import mariculture.core.helpers.InventoryHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GuiIds;
import mariculture.core.lib.PearlColor;
import mariculture.core.lib.RenderIds;
import mariculture.core.util.IItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockOyster extends BlockMachine {
	private final Random rand = new Random();

	public BlockOyster(int i) {
		super(i, Material.ice);
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
	public int getRenderType() {
		return RenderIds.BLOCK_SINGLE;
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		if (world.getBlockMaterial(x, y + 1, z) != Material.water) {
			world.destroyBlock(x, y, z, true);
			return false;
		}

		if (!world.isBlockSolidOnSide(x, y - 1, z, ForgeDirection.UP)) {
			world.destroyBlock(x, y, z, true);
			return false;
		}

		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int par5) {
		if (!this.canBlockStay(world, x, y, z)) {
			if (rand.nextInt(10) == 0) {
				this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			}

			world.setBlock(x, y, z, 0);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
		setBlockBounds(0F, 0F, 0F, 1F, 0.1F, 1F);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
	}

	@Override
	public boolean isBlockReplaceable(World world, int x, int y, int z) {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		int facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (facing == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);
		}

		if (facing == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (facing == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
		}

		if (facing == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if (!Extra.DEBUG_ON) {
			return this.canBlockStay(world, x, y, z);
		}

		final int id = world.getBlockId(x, y, z);
		return id == 0 || blocksList[id].blockMaterial.isReplaceable();
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
		TileEntity tile_entity = world.getBlockTileEntity(x, y, z);

		if (tile_entity == null || (player.isSneaking() && !Extra.DEBUG_ON)) {
			return false;
		}

		if (tile_entity instanceof TileOyster) {
			TileOyster oyster = (TileOyster) tile_entity;

			if (!player.isSneaking()) {
				if (!world.isRemote) {
					if (player.getCurrentEquippedItem() != null
							&& player.getCurrentEquippedItem().itemID == Block.sand.blockID) {
						if (!oyster.hasSand()) {
							oyster.setInventorySlotContents(0, new ItemStack(Block.sand));
							if (!player.capabilities.isCreativeMode) {
								player.inventory.decrStackSize(player.inventory.currentItem, 1);
							}
							
							oyster.sendOysterUpdate();
							return true;
						}
					}

					InventoryHelper.dropItems(world, x, y, z);
					oyster.setInventorySlotContents(0, null);
					oyster.sendOysterUpdate();
					return true;

				}
			}

			if (player.isSneaking() && Extra.DEBUG_ON) {
				player.openGui(Mariculture.instance, GuiIds.OYSTER, world, x, y, z);

				return true;
			}
		}

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(final World world) {
		return new TileOyster();
	}

	@Override
	public String getName(ItemStack stack) {
		return "oyster";
	}
}