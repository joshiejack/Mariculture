package mariculture.core.blocks;

import java.util.Random;

import mariculture.Mariculture;
import mariculture.core.Core;
import mariculture.core.blocks.base.BlockFunctional;
import mariculture.core.handlers.PearlGenHandler;
import mariculture.core.helpers.BlockHelper;
import mariculture.core.helpers.DirectionHelper;
import mariculture.core.lib.Extra;
import mariculture.core.lib.GuideMeta;
import mariculture.core.lib.RenderIds;
import mariculture.core.lib.WaterMeta;
import mariculture.core.render.RenderOyster;
import mariculture.core.tile.TileOyster;
import mariculture.plugins.enchiridion.BookSpawnHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWater extends BlockFunctional {	
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
		return 1;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		return 25F;
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
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
		if(!BlockHelper.isWater(world, x, y + 1, z)) {
			dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
	        world.setBlockToAir(x, y, z);
		}
			
		if (!world.isSideSolid(x, y - 1, z, ForgeDirection.UP)) {
			dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
	        world.setBlockToAir(x, y, z);
		}
    }
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		if(!BlockHelper.isWater(world, x, y, z) || !world.isSideSolid(x, y - 1, z, ForgeDirection.UP)) {
			return false;
		} else return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		if(stack.getItemDamage() == WaterMeta.OYSTER) {
			((TileOyster)world.getTileEntity(x, y, z)).orientation = DirectionHelper.getFacingFromEntity(entity);
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile == null || player.isSneaking()) {
			return false;
		}

		if (tile instanceof TileOyster) {
			TileOyster oyster = (TileOyster) tile;
			if(Extra.SPAWN_BOOKS) {
                if(Loader.isModLoaded("Enchiridion")) {
                	if(oyster.getStackInSlot(0) != null && oyster.getStackInSlot(0).getItem() == Core.pearls) {
                		BookSpawnHelper.spawn(player, GuideMeta.ENCHANTS);
                	}
                }
            }

			if (!player.isSneaking()) {
				if (!world.isRemote) {
					if(player.getCurrentEquippedItem() != null) {
						if(player.getCurrentEquippedItem().getItem() == Item.getItemFromBlock(Blocks.sand)) {
							if(oyster.getStackInSlot(0) == null) {
								oyster.setInventorySlotContents(0, new ItemStack(Blocks.sand));
								if (!player.capabilities.isCreativeMode) {
									player.inventory.decrStackSize(player.inventory.currentItem, 1);
								}
	
								return true;
							}
						}
					}

					BlockHelper.dropItems(world, x, y, z);
					oyster.setInventorySlotContents(0, null);
					return true;
				}
			}
		}

		return true;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess block, int x, int y, int z) {
		setBlockBounds(0.05F, 0.0F, 0.05F, 0.95F, 0.1F, 0.95F);
	}

	@Override
	public TileEntity createTileEntity(World world, int meta) {
		return new TileOyster();
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {		
		if(world.getBlockMetadata(x, y, z) == WaterMeta.OYSTER) {
			TileOyster oyster = (TileOyster) world.getTileEntity(x, y, z);
			if(!world.isRemote) {
				if(oyster.hasSand() && BlockHelper.isWater(world, x, y + 1, z)) {
					if(rand.nextInt(Extra.PEARL_GEN_CHANCE) == 0) {
						Block block = world.getBlock(x, y - 1, z);
						if(world.provider.dimensionId == 1) {
							oyster.setInventorySlotContents(0, new ItemStack(Items.ender_pearl));
						} if(block instanceof BlockPearlBlock) {
							oyster.setInventorySlotContents(0, new ItemStack(Core.pearls, 1, world.getBlockMetadata(x, y - 1, z)));
						} else {
							oyster.setInventorySlotContents(0, PearlGenHandler.getRandomPearl(rand));
						}
					}
				}
			}
		}
	}
	
	@Override
	public int getMetaCount() {
		return WaterMeta.COUNT;
	}
	
	public int getMixedBrightnessForBlock(IBlockAccess world, int x, int y, int z) {
		return world.getLightBrightnessForSkyBlocks(x, y, z, 0);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		super.registerBlockIcons(register);
		RenderOyster.lid_blur = register.registerIcon(Mariculture.modid + ":" + "oysterLidBlur");
		RenderOyster.lid = register.registerIcon(Mariculture.modid + ":" + "oysterLid");
		RenderOyster.tongue = register.registerIcon(Mariculture.modid + ":" + "oysterTongue");
	}
}
