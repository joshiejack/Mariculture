package mariculture.fishery.blocks;

import java.util.List;
import java.util.Random;

import mariculture.Mariculture;
import mariculture.api.core.MaricultureTab;
import mariculture.core.blocks.base.BlockDecorative;
import mariculture.core.lib.PearlColor;
import mariculture.fishery.Fishery;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNeonLamp extends BlockDecorative {
	private final boolean powered;

	public BlockNeonLamp(boolean powered, String prefix) {
		super(Material.glass);
		this.powered = powered;
		this.prefix = prefix;

		if (!powered) {
			setLightLevel(1.0F);
		}

		this.setHardness(1F);
	}
	
	@Override
	public String getToolType(int meta) {
		return null;
	}

	@Override
	public int getToolLevel(int meta) {
		return 0;
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if (!world.isRemote) {
			if (this.powered && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.scheduleBlockUpdate(x, y, z, this, 4);
			} else if (!this.powered && world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.setBlock(x, y, z, Fishery.lampsOff, world.getBlockMetadata(x, y, z), 2);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if (!world.isRemote) {
			if (this.powered && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.scheduleBlockUpdate(x, y, z, this, 4);
			} else if (!this.powered && world.isBlockIndirectlyGettingPowered(x, y, z)) {
				world.setBlock(x, y, z, Fishery.lampsOff, world.getBlockMetadata(x, y, z), 2);
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (!world.isRemote && this.powered && !world.isBlockIndirectlyGettingPowered(x, y, z)) {
			world.setBlock(x, y, z, Fishery.lampsOn, world.getBlockMetadata(x, y, z), 2);
		}
	}
	
	@Override
	public Item getItemDropped(int meta, Random random, int j) {
		return Item.getItemFromBlock(Fishery.lampsOn);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return new ItemStack(Fishery.lampsOn, 1, world.getBlockMetadata(x, y, z));
	}
	
	 @SideOnly(Side.CLIENT)
	 public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
	 	Block block = world.getBlock(x, y, z);
	 	if (this == Fishery.lampsOff || this == Fishery.lampsOn) {
	 		if (world.getBlockMetadata(x, y, z) != world.getBlockMetadata(x - Facing.offsetsXForSide[side], y - Facing.offsetsYForSide[side], z - Facing.offsetsZForSide[side])) {
	                return true;
	            }

	            if (block == this) {
	                return false;
	            }
	        }

	        return block == this ? false : super.shouldSideBeRendered(world, x, y, z, side);
	    }

	@Override
	public int getRenderBlockPass() {
		return 1;
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
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		icons = new IIcon[PearlColor.COUNT];

		for (int i = 0; i < icons.length; i++) {
			String theName, name = getUnlocalizedName().substring(5) + i;
			String[] aName = name.split("\\.");
			if(aName.length == 2) theName = aName[0] + aName[1].substring(0, 1).toUpperCase() + aName[1].substring(1);
			else theName = name;
			
			icons[i] = register.registerIcon(Mariculture.modid + ":" + theName);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
		if(creativeTabs == MaricultureTab.tabCore) {
			if (item == Item.getItemFromBlock(Fishery.lampsOn)) {
				for (int meta = 0; meta < PearlColor.COUNT; ++meta) {
					if (!list.contains(new ItemStack(item, 1, meta))) {
						list.add(new ItemStack(item, 1, meta));
					}
				}
			}
		}
	}

	@Override
	public int getMetaCount() {
		return PearlColor.COUNT;
	}
}
