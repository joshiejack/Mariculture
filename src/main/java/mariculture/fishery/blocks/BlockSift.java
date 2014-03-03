package mariculture.fishery.blocks;

import java.util.ArrayList;
import java.util.Random;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureRegistry;
import mariculture.api.core.MaricultureTab;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.RecipeSifter;
import mariculture.core.Core;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.lib.GuiIds;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.UpgradeMeta;
import mariculture.core.lib.UtilMeta;
import mariculture.core.util.IItemRegistry;
import mariculture.core.util.Rand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
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

public class BlockSift extends BlockContainer implements IItemRegistry {
	public BlockSift() {
		super(Material.wood);
		setCreativeTab(MaricultureTab.tabMariculture);
		setHarvestLevel("axe", 0, UtilMeta.INCUBATOR_BASE);
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
	public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
		BlockHelper.dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, block, meta);
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
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileSift();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
		TileEntity tile = world.getTileEntity(x, y, z);
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
			
			boolean played = false;
			if(Fishing.sifter.getResult(stack) != null) {
				if(!world.isRemote) {
					ArrayList<RecipeSifter> recipe = Fishing.sifter.getResult(stack);
					int stackSize = stack.stackSize;
					for (int j = 0; j <= stackSize; j++) {
						if(stack.stackSize > 0) {
							for(RecipeSifter bait: recipe) {
								int chance = Rand.rand.nextInt(100);
								if(chance < bait.chance) {
									ItemStack result = bait.bait.copy();
									result.stackSize = bait.minCount + Rand.rand.nextInt((bait.maxCount + 1) - bait.minCount);
									spawnItem(result, world, x, y, z);
								}
							}
							
							if(!played) {
								world.playSoundAtEntity(player, Mariculture.modid + ":sift", 1.5F, 1.0F);
								played = true;
							}
						}
						
						if(!player.capabilities.isCreativeMode)
							player.inventory.decrStackSize(player.inventory.currentItem, 1);
					}
				}
				
				return true;
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
			
			if(!world.isRemote && Fishing.sifter.getResult(item) != null) {
				ArrayList<RecipeSifter> recipe = Fishing.sifter.getResult(item);
				for (int j = 0; j < item.stackSize; j++) {
					for(RecipeSifter bait: recipe) {
						int chance = Rand.rand.nextInt(100);
						if(chance < bait.chance) {
							ItemStack result = bait.bait.copy();
							result.stackSize = bait.minCount + Rand.rand.nextInt((bait.maxCount + 1) - bait.minCount);
							spawnItem(result, world, x, y, z);
						}
					}
					
					if(!played) {
						world.playSoundAtEntity(entity, Mariculture.modid + ":sift", 1.5F, 1.0F);
						played = true;
					}
					
					Entityitem.setDead();
				}
			}
		}
	}

	private void spawnItem(ItemStack item, World world, int x, int y, int z) {
		boolean done = false;
		if (world.getBlockMetadata(x, y, z) > 1) {
		TileSift sift = (TileSift) world.getTileEntity(x, y, z);
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
		
		return world.isAirBlock(x, y, z) || world.getBlock(x, y, z).getMaterial().isReplaceable();
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this));
		if(meta > 1) {
			ret.add(new ItemStack(Core.upgrade, 1, UpgradeMeta.BASIC_STORAGE));
		}
		
		return ret;
    }

	@Override
	public String getName(ItemStack stack) {
		return "sift";
	}

	@Override
	public int getMetaCount() {
		return 1;
	}

	@Override
	public void register() {
		MaricultureRegistry.register(getName(new ItemStack(this)), new ItemStack(this, 1));
	}
}
