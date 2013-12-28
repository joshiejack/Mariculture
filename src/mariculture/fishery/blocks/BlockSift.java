package mariculture.fishery.blocks;

import java.util.Random;

import mariculture.api.fishery.Fishing;
import mariculture.core.Core;
import mariculture.core.Mariculture;
import mariculture.core.blocks.BlockMachine;
import mariculture.core.helpers.InventoHelper;
import mariculture.core.items.ItemUpgrade;
import mariculture.core.lib.GuiIds;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.UpgradeMeta;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSift extends BlockMachine {
	public BlockSift(int i) {
		super(i, Material.wood);
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
		return RenderIds.BLOCK_SINGLE;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, final ItemStack stack) {
		int facing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (facing == 0 || facing == 2)
			world.setBlockMetadataWithNotify(x, y, z, 0, 2);
		else if (facing == 1 || facing == 3)
			world.setBlockMetadataWithNotify(x, y, z, 1, 2);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileSift();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if (tile == null || player.isSneaking()) {
			return false;
		}

		if (player.getCurrentEquippedItem() != null) {
			ItemStack stack = player.getCurrentEquippedItem();
			if(stack.getItem() == Core.upgrade) {
				if(stack.getItemDamage() == UpgradeMeta.BASIC_STORAGE) {
					if (world.getBlockMetadata(x, y, z) < 2) {
						world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) + 2, 2);
						player.inventory.decrStackSize(player.inventory.currentItem, 1);
						return false;
					}
				}
			}
		}

		if (tile instanceof TileSift && tile.getBlockMetadata() > 1) {
			player.openGui(Mariculture.instance, GuiIds.SIFT, world, x, y, z);
			return true;
		}
		return true;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		float var5 = 0.0625F;
		return AxisAlignedBB.getAABBPool().getAABB(x + var5, y, z + var5, x + 1 - var5, y + 1 - var5, z + 1 - var5);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (entity instanceof EntityItem && !world.isRemote) {
			Random random = new Random();
			EntityItem Entityitem = (EntityItem) entity;
			ItemStack item = Entityitem.getEntityItem();
			boolean played = false;

			for (int i = 0; i < item.stackSize; i++) {
				ItemStack bait = Fishing.bait.getBaitForStack(random, item);

				if (bait != null) {
					if(!played) {
						world.playSoundAtEntity(entity, Mariculture.modid + ":sift", 1.5F, 1.0F);
						played = true;
					}
					
					Entityitem.setDead();

					int chance = 26 - bait.stackTagCompound.getInteger("Chance");

					if (random.nextInt(chance) == 0) {
						int max = bait.stackTagCompound.getInteger("Max");
						int min = bait.stackTagCompound.getInteger("Min");
						chance = (max - min) + 1;
						bait.stackTagCompound = null;
						ItemStack newBait = bait.copy();
						newBait.stackSize = min;
						newBait.stackSize += (chance > 0) ? random.nextInt(chance) : 0;
						spawnItem(newBait, world, x, y, z);
					}
				}
			}
		}
	}

	private void spawnItem(ItemStack item, World world, int x, int y, int z) {
		boolean done = false;
		if (world.getBlockMetadata(x, y, z) > 1) {
		TileSift sift = (TileSift) world.getBlockTileEntity(x, y, z);
			if (sift.getSuitableSlot(item) != 10) {
				int slot = sift.getSuitableSlot(item);
				ItemStack newStack = item;
				if (sift.getStackInSlot(slot) != null) {
					newStack.stackSize = newStack.stackSize + sift.getStackInSlot(slot).stackSize;
				}

				sift.setInventorySlotContents(slot, newStack);

				done = true;
			}
		}

		if (done == false) {
			Random rand = new Random();
			float rx = rand.nextFloat() * 0.6F + 0.1F;
			float ry = rand.nextFloat() * 0.6F + 0.1F;
			float rz = rand.nextFloat() * 0.6F + 0.1F;

			EntityItem dropped = new EntityItem(world, x + rx, y + ry + 0.5F, z + rz, item);
			world.spawnEntityInWorld(dropped);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
		if (block.getBlockMetadata(x, y, z) == 1 || block.getBlockMetadata(x, y, z) == 3) {
			setBlockBounds(-0.3F, 0F, -0.085F, 1.3F, 0.8F, 1.085F);
		} else if (block.getBlockMetadata(x, y, z) == 0 || block.getBlockMetadata(x, y, z) == 2) {
			setBlockBounds(-0.05F, 0F, -0.15F, 1.15F, 0.8F, 1.45F);
		}
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		for (int X = x - 1; X <= x + 1; X++) {
			for (int Z = z - 1; Z <= z + 1; Z++) {
				if (!world.isAirBlock(X, y, Z)) {
					return false;
				}
			}
		}

		int id = world.getBlockId(x, y, z);
		return id == 0 || blocksList[id].blockMaterial.isReplaceable();
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z) {
		if (!world.isRemote) {
			if (world.getBlockMetadata(x, y, z) > 1) {
				if (!player.capabilities.isCreativeMode) {
					world.spawnEntityInWorld(new EntityItem(world, (x), (float) y + 1, (z), new ItemStack(Core.upgrade,
							1, UpgradeMeta.BASIC_STORAGE)));
				}
			}
		}

		return world.setBlockToAir(x, y, z);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int i,  int j) {
		InventoHelper.dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, i, j);
	}

	@Override
	public String getName(ItemStack stack) {
		return "sift";
	}
}
